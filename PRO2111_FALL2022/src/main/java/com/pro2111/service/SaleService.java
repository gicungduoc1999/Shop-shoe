package com.pro2111.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.pro2111.beans.SaleAndProductVariants;
import com.pro2111.beans.SaleAndSaleChild;
import com.pro2111.beans.VariantValueViewSaleBean;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;

public interface SaleService {
	List<Sale> findAll();

	SaleAndProductVariants findSaleAndProductById(String id);

	Sale findSaleById(String id);

	Sale create(SaleAndProductVariants saleAndProduct);

	SaleAndProductVariants update(SaleAndProductVariants saleAndProduct);

	void delete(String id);

	List<ProductVariant> findProductVariantsPromotionIsAllowed(List<Product> lisProduct);

	Boolean checkSaleName(Sale saleNew, Sale saleOld, String method);
	
	Sale createAndSaleChild(SaleAndSaleChild saleAndSaleChild);
	
	SaleAndSaleChild updateAndSaleChild(SaleAndSaleChild saleAndSaleChild);
	
	List<Sale> findSaleParent();
	
	SaleAndSaleChild findSaleAndSaleChildBySaleParent(String id);
	
	Sale deleteSaleChild(Sale saleChild);
	

	/**
	 * @param i
	 * @return
	 */
	List<Sale> findByStatus(int i);

	/**
	 * @param listSaleUpdate
	 */
	List<Sale> updateSale(List<Sale> listSaleUpdate);

	/**
	 * @param productVariant
	 * @return
	 */
	BigDecimal findDiscountSaleByProductVariant(ProductVariant productVariant);

	/**
	 * @param productVariant
	 * @return
	 */
	VariantValueViewSaleBean findVariantValueByProductVariant(ProductVariant productVariant);
}
