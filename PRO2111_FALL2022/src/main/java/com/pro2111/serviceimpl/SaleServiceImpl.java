package com.pro2111.serviceimpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.pro2111.beans.PvAndVv;
import com.pro2111.beans.SaleAndProductVariants;
import com.pro2111.beans.SaleAndSaleChild;
import com.pro2111.beans.VariantValueViewSaleBean;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductSale;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;
import com.pro2111.entities.VariantValue;
import com.pro2111.example.ProductTest;
import com.pro2111.repositories.ProductSaleRepository;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.repositories.SaleRepository;
import com.pro2111.repositories.VariantValueRepository;
import com.pro2111.service.SaleService;
import com.pro2111.utils.Common;

@Service
public class SaleServiceImpl implements SaleService {
	@Autowired
	private SaleRepository saleRepo;
	@Autowired
	private ProductVariantRepository productVariantRepository;
	@Autowired
	private ProductSaleRepository productSaleRepository;
	@Autowired
	private VariantValueRepository valueRepository;

	private BigDecimal discount;

	@Override
	public List<Sale> findAll() {
		// TODO Auto-generated method stub
		return saleRepo.findAll();
	}

	@Override
	public SaleAndProductVariants findSaleAndProductById(String id) {
		SaleAndProductVariants saleAndProduct = new SaleAndProductVariants();
		Sale sale = saleRepo.findById(id).get();
		saleAndProduct.setSale(sale);
		List<ProductVariant> lisProductVariant = productSaleRepository.findProductVariantBySale(sale);
		saleAndProduct.setListProductVariants(lisProductVariant);
		return saleAndProduct;
	}

	@Override
	@Transactional
	public synchronized Sale create(SaleAndProductVariants saleAndProduct) {
		Sale sale = saleAndProduct.getSale();
		if (Common.checkStartDateSaleCreate(sale.getStartDate())) {
			sale.setStatus(1);
		} else {
			sale.setStatus(0);
		}
		sale.setCreatedDate(LocalDateTime.now());
		saleRepo.save(sale);

		List<ProductVariant> listProducts = saleAndProduct.getListProductVariants();
		for (int i = 0; i < listProducts.size(); i++) {
			ProductVariant variant = listProducts.get(i);
			ProductSale productSale = new ProductSale();
			productSale.setProductVariants(variant);
			productSale.setSales(sale);
			productSale.setStatus(1);
			productSaleRepository.save(productSale);
		}
		return sale;
	}

	@Override
	@Transactional
	public synchronized SaleAndProductVariants update(SaleAndProductVariants saleAndProduct) {
		SaleAndProductVariants saleAndProductUpdate = new SaleAndProductVariants();
		Sale saleUpdate = saleRepo.save(saleAndProduct.getSale());
		if (Common.checkStartDateSaleCreate(saleUpdate.getStartDate())) {
			saleUpdate.setStatus(1);
		} else {
			saleUpdate.setStatus(0);
		}
		saleAndProductUpdate.setSale(saleUpdate);
		List<ProductVariant> listProductVariantOld = productSaleRepository.findProductVariantBySale(saleUpdate);
		List<ProductVariant> listProductVariantNew = saleAndProduct.getListProductVariants();
		if (checkList(listProductVariantOld, listProductVariantNew)) {
			saleAndProductUpdate.setListProductVariants(listProductVariantOld);
		} else {
			List<ProductVariant> listStayTheSame = getListStayTheSame(listProductVariantNew, listProductVariantOld);
			List<ProductVariant> listRemove = listProductVariantOld.stream().filter(
					del -> !listStayTheSame.stream().anyMatch(old -> old.getVariantId().equals(del.getVariantId())))
					.collect(Collectors.toList());
			for (ProductVariant pv : listRemove) {
				ProductSale productSale = productSaleRepository.findByProductVariantsLikeAndSalesLike(pv, saleUpdate);
				productSaleRepository.delete(productSale);
			}
			List<ProductVariant> listAdd = listProductVariantNew.stream().filter(
					add -> !listStayTheSame.stream().anyMatch(news -> news.getVariantId().equals(add.getVariantId())))
					.collect(Collectors.toList());
			for (ProductVariant pv : listAdd) {
				ProductSale productSale = new ProductSale();
				productSale.setSales(saleUpdate);
				productSale.setProductVariants(pv);
				productSale.setStatus(1);
				productSaleRepository.save(productSale);
			}
		}
		return saleAndProductUpdate;

	}

	@Override
	public void delete(String id) {
		saleRepo.deleteById(id);
	}

	@Override
	public List<ProductVariant> findProductVariantsPromotionIsAllowed(List<Product> listProduct) {
		return productVariantRepository.findProductVariantsPromotionIsAllowed(listProduct);
	}

	public static Boolean checkList(List<ProductVariant> listOne, List<ProductVariant> listTwo) {
		return listOne.equals(listTwo);
	}

	public static List<ProductVariant> getListStayTheSame(List<ProductVariant> listOne, List<ProductVariant> listTwo) {
		return listOne.stream()
				.filter(two -> listTwo.stream().anyMatch(one -> one.getVariantId().equals(two.getVariantId())))
				.collect(Collectors.toList());
	}

	@Override
	public Boolean checkSaleName(Sale saleNew, Sale saleOld, String method) {
		Boolean checkBoolean = true;
		if ("POST".equalsIgnoreCase(method)) {
			Sale sale = saleRepo.findSaleByName(saleNew.getSaleName());
			if (sale != null) {
				checkBoolean = false;
			}
		} else if ("PUT".equalsIgnoreCase(method)) {
			if (!saleNew.getSaleName().equalsIgnoreCase(saleOld.getSaleName())) {
				Sale sale = saleRepo.findSaleByName(saleNew.getSaleName());
				if (sale != null) {
					checkBoolean = false;
				}
			}
		}
		return checkBoolean;
	}

	@Override
	public Sale findSaleById(String id) {
		return saleRepo.findById(id).get();
	}

	@Override
	public List<Sale> findByStatus(int i) {
		return saleRepo.findByStatus(i);
	}

	@Override
	public List<Sale> updateSale(List<Sale> listSaleUpdate) {
		return saleRepo.saveAll(listSaleUpdate);
	}

	@Override
	public BigDecimal findDiscountSaleByProductVariant(ProductVariant productVariant) {
		List<Sale> sales = productSaleRepository.findSalesByProductVariant(productVariant);
		discount = new BigDecimal(0);
		sales.forEach(s -> {
			if (s.getDiscountType() == 0) {
				discount = discount.add(s.getDiscount());
			} else {
				BigDecimal discountSale = productVariant.getPrice().multiply(s.getDiscount())
						.divide(BigDecimal.valueOf(100));
				discount = discount.add(discountSale);
			}
		});
		return discount;
	}

	@Override
	public VariantValueViewSaleBean findVariantValueByProductVariant(ProductVariant productVariant) {
		VariantValueViewSaleBean bean = new VariantValueViewSaleBean();
		List<VariantValue> list = valueRepository.findByProductVariantsLike(productVariant);
		for (int i = list.size() - 1; i >= 0; i--) {
			VariantValue value = list.get(i);
			if (value.getOptionValues().getIsShow() == 0 && value.getProductOptions().getOptions().getIsShow() == 0) {
				list.remove(i);
			}
		}
		List<Sale> listSale = productSaleRepository.findSalesByProductVariant(productVariant);
		List<String> listName = new ArrayList<String>();
		listSale.forEach(s->{
			listName.add(s.getSaleName());
		});
		bean.setVariantValues(list);
		bean.setCountSale(listSale.size());
		bean.setSaleName(listName);
		return bean;
	}

	@Override
	@Transactional
	public synchronized Sale createAndSaleChild(SaleAndSaleChild saleAndSaleChild) {
		Sale saleParent = saleAndSaleChild.getSaleParent();
		saleParent.setCreatedDate(LocalDateTime.now());
		saleParent.setWeekday(0);
		saleRepo.save(saleParent);
		List<Sale> lstSaleChild = saleAndSaleChild.getLstSaleChild();
		for(int i = 0; i < lstSaleChild.size(); i++) {
			Sale salechild = lstSaleChild.get(i);
			salechild.setDiscountType(0);
			salechild.setDiscount(null);
			salechild.setSaleParent(saleParent);
			saleRepo.save(salechild);
		}
		List<ProductVariant> listProducts = saleAndSaleChild.getLstProductVariants();
		for (int i = 0; i < listProducts.size(); i++) {
			ProductVariant variant = listProducts.get(i);
			ProductSale productSale = new ProductSale();
			productSale.setProductVariants(variant);
			productSale.setSales(saleParent);
			productSale.setStatus(1);
			productSaleRepository.save(productSale);
		}
		
		return saleParent;
	}

	@Override
	@Transactional
	public synchronized SaleAndSaleChild updateAndSaleChild(SaleAndSaleChild saleAndSaleChild) {
		SaleAndProductVariants saleAndProductUpdate = new SaleAndProductVariants();
		Sale saleUpdate = saleRepo.save(saleAndSaleChild.getSaleParent());
		if (Common.checkStartDateSaleCreate(saleUpdate.getStartDate())) {
			saleUpdate.setStatus(1);
		} else {
			saleUpdate.setStatus(0);
		}
		saleAndProductUpdate.setSale(saleUpdate);
		List<ProductVariant> listProductVariantOld = productSaleRepository.findProductVariantBySale(saleUpdate);
		List<ProductVariant> listProductVariantNew = saleAndSaleChild.getLstProductVariants();
		if (checkList(listProductVariantOld, listProductVariantNew)) {
			saleAndProductUpdate.setListProductVariants(listProductVariantOld);
		} else {
			List<ProductVariant> listStayTheSame = getListStayTheSame(listProductVariantNew, listProductVariantOld);
			List<ProductVariant> listRemove = listProductVariantOld.stream().filter(
					del -> !listStayTheSame.stream().anyMatch(old -> old.getVariantId().equals(del.getVariantId())))
					.collect(Collectors.toList());
			for (ProductVariant pv : listRemove) {
				ProductSale productSale = productSaleRepository.findByProductVariantsLikeAndSalesLike(pv, saleUpdate);
				productSaleRepository.delete(productSale);
			}
			List<ProductVariant> listAdd = listProductVariantNew.stream().filter(
					add -> !listStayTheSame.stream().anyMatch(news -> news.getVariantId().equals(add.getVariantId())))
					.collect(Collectors.toList());
			for (ProductVariant pv : listAdd) {
				ProductSale productSale = new ProductSale();
				productSale.setSales(saleUpdate);
				productSale.setProductVariants(pv);
				productSale.setStatus(1);
				productSaleRepository.save(productSale);
			}
		}
		return saleAndSaleChild;
	}

	@Override
	public List<Sale> findSaleParent() {
		return saleRepo.findAllSaleParent();
	}

	@Override
	public SaleAndSaleChild findSaleAndSaleChildBySaleParent(String idSale) {
		Sale saleParent = saleRepo.findById(idSale).get();
		List<Sale> lstSaleChild = saleRepo.findSaleChileBySaleParent(saleParent);
		List<ProductVariant> lstProductVariants = productSaleRepository.findProductVariantBySale(saleParent);
		SaleAndSaleChild saleAndSaleChild = new SaleAndSaleChild();
		saleAndSaleChild.setSaleParent(saleParent);
		saleAndSaleChild.setLstSaleChild(lstSaleChild);
		saleAndSaleChild.setLstProductVariants(lstProductVariants);
		return saleAndSaleChild;
	}

	@Override
	public Sale deleteSaleChild(Sale saleChild) {
		saleRepo.delete(saleChild);
		return saleChild;
	}

	
	

	
	
	

}
