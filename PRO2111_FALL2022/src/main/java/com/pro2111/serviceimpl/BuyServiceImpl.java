package com.pro2111.serviceimpl;

import com.pro2111.beans.BuyRequest;
import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;
import com.pro2111.entities.ProductVariant;
import com.pro2111.repositories.BillDetailRepository;
import com.pro2111.repositories.BillRepository;
import com.pro2111.repositories.CartDetailRepository;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyServiceImpl implements BuyService {
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private BillDetailRepository billDetailRepository;
	@Autowired
	private ProductVariantRepository productVariantRepository;
	@Autowired
	private CartDetailRepository cartDetailRepository;
	private BigDecimal totalMoneyBill;

	@Override
	@Transactional
	public Bill createBill(BuyRequest buyRequest) {
		// Tạo mới Bill
		Bill bill = billRepository.save(setBill(buyRequest.getBill()));
		// Convert qua billDetail
		totalMoneyBill = new BigDecimal(0);
		List<BillDetail> billDetail = buyRequest.getCartDetails().stream().map(cartDetail -> {
			ProductVariant productVariant = getProductVariant(cartDetail.getProductVariants().getVariantId());
			BigDecimal money = BigDecimal.valueOf(cartDetail.getQuantity()).multiply(productVariant.getPrice());
			BigDecimal totalBillDetail = money
					.add(money.multiply(productVariant.getTax()).divide(BigDecimal.valueOf(100)));
			totalMoneyBill = totalMoneyBill.add(totalBillDetail);
			return new BillDetail(cartDetail.getQuantity(), productVariant.getPrice(), totalBillDetail, bill, 0,
					productVariant.getTax(), productVariant);
		}).collect(Collectors.toList());
		// Thêm mới billDetails
		billDetailRepository.saveAll(billDetail);
		// Cập nhật productVariant và xóa CartDetail
		buyRequest.getCartDetails().forEach(cartDetail -> {
			ProductVariant productVariant = productVariantRepository
					.findById(cartDetail.getProductVariants().getVariantId()).get();
			productVariant.setQuantity(productVariant.getQuantity() - cartDetail.getQuantity());
			productVariantRepository.save(productVariant);
			cartDetailRepository.delete(cartDetail);
		});
		// Set toTalMoney cho bill
		BigDecimal discount = totalMoneyBill.multiply(BigDecimal.valueOf(bill.getDiscount()))
				.divide(BigDecimal.valueOf(100));
		totalMoneyBill = totalMoneyBill.subtract(discount);
		totalMoneyBill = totalMoneyBill.add(bill.getShippingFee());
		bill.setTotalMoney(totalMoneyBill);
		// cập nhật bill
		billRepository.save(bill);
		return bill;
	}

	@Override
	public synchronized Long countBillId(String billId) {
		return billRepository.countBillId(billId);
	}

	@Override
	public synchronized List<Bill> findByBillIdLike(String id) {
		return billRepository.findByBillIdLike(id);
	}

	private ProductVariant getProductVariant(Long id) {
		return productVariantRepository.findById(id).get();
	}
    
    @Override
	public List<Bill> getBillsByCusId(Integer id) {
		return billRepository.findAllByCustomer_UserId(id);
	}

	private Bill setBill(Bill bill) {
		bill.setStatus(0);
		bill.setCreatedDate(LocalDateTime.now());
		return bill;
	}

}
