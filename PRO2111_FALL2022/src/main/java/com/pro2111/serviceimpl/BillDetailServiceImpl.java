/**
 * DATN_FALL2022, 2022
 * BillDetailServiceIml.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;
import com.pro2111.entities.ProductVariant;
import com.pro2111.repositories.BillDetailRepository;
import com.pro2111.repositories.BillRepository;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.service.BillDetailService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Service
public class BillDetailServiceImpl implements BillDetailService {

	@Autowired
	private BillDetailRepository billDetailRepository;
	@Autowired
	private ProductVariantRepository productVariantRepository;
	@Autowired
	BillRepository billRepository;
	private BigDecimal totalMoney;

	@Override
	@Transactional
	public synchronized BillDetail createdBillDetail(BillDetail billDetail) {
		List<BillDetail> details = billDetailRepository
				.findByProductVariantsLikeAndBillsLike(billDetail.getProductVariants(), billDetail.getBills());
		if (details.size() == 0) {
			BigDecimal money = BigDecimal.valueOf(billDetail.getQuantity()).multiply(billDetail.getPrice());
			money = money.add(money.multiply(billDetail.getTax()).divide(BigDecimal.valueOf(100)));
			billDetail.setTotalMoney(money);

			// update quantity ProductVariant
			ProductVariant productVariant = productVariantRepository
					.findById(billDetail.getProductVariants().getVariantId()).get();
			productVariant.setQuantity(productVariant.getQuantity() - billDetail.getQuantity());
			productVariantRepository.save(productVariant);

			// update totalmoney bill
			Bill bill = billDetail.getBills();
			List<BillDetail> details2 = billDetailRepository.findByBillsLike(bill);
			totalMoney = new BigDecimal("0.0");
			details2.forEach(d -> {
				totalMoney = totalMoney.add(d.getTotalMoney());
			});
			BigDecimal totalMoneyNew = totalMoney.subtract(
					(totalMoney.multiply(BigDecimal.valueOf(bill.getDiscount()))).divide(BigDecimal.valueOf(100)));
			totalMoneyNew = totalMoneyNew.add(bill.getShippingFee());
			bill.setTotalMoney(totalMoneyNew);

			billRepository.save(bill);
			return billDetailRepository.save(billDetail);
		} else {
			BillDetail detailOld = details.get(0);
			detailOld.setQuantity(detailOld.getQuantity() + billDetail.getQuantity());
			BigDecimal money = detailOld.getPrice().multiply(BigDecimal.valueOf(detailOld.getQuantity()));
			money = money.add(money.multiply(detailOld.getTax()).divide(BigDecimal.valueOf(100)));
			detailOld.setTotalMoney(money);

			// update quantity ProductVariant
			ProductVariant productVariant = productVariantRepository
					.findById(detailOld.getProductVariants().getVariantId()).get();
			productVariant.setQuantity(productVariant.getQuantity() - billDetail.getQuantity());
			productVariantRepository.save(productVariant);

			// update totalmoney bill
			Bill bill = detailOld.getBills();
			List<BillDetail> details2 = billDetailRepository.findByBillsLike(bill);
			totalMoney = new BigDecimal("0.0");
			details2.forEach(d -> {
				totalMoney = totalMoney.add(d.getTotalMoney());
			});
			BigDecimal totalMoneyNew = totalMoney.subtract(
					(totalMoney.multiply(BigDecimal.valueOf(bill.getDiscount()))).divide(BigDecimal.valueOf(100)));
			totalMoneyNew = totalMoneyNew.add(bill.getShippingFee());
			bill.setTotalMoney(totalMoneyNew);
			billRepository.save(bill);
			return billDetailRepository.save(detailOld);
		}
	}

	@Override
	public synchronized List<BillDetail> findByBill(Bill bill) {
		return billDetailRepository.findByBillsLike(bill);
	}

	@Override
	@Transactional
	public synchronized BillDetail updateBillDetail(BillDetail billDetail) {
		BillDetail detailOld = billDetailRepository.findById(billDetail.getDetailBillId()).get();
		int quantityOld = detailOld.getQuantity();
		int quantityNew = billDetail.getQuantity();

		billDetail.setTax(detailOld.getTax());
		BigDecimal money = billDetail.getPrice().multiply(BigDecimal.valueOf(quantityNew));
		money = money.add(money.multiply(billDetail.getTax()).divide(BigDecimal.valueOf(100)));
		billDetail.setTotalMoney(money);
		// update billDetail
		billDetailRepository.save(billDetail);

//		// Tìm list BillDetail theo productVarinat của billDetail
//		List<BillDetail> lstDetail = new ArrayList<BillDetail>();
//		lstDetail = billDetailRepository.findByProductVariantsLikeAndBillsLike(billDetail.getProductVariants(),
//				billDetail.getBills());
//		int quantityOld = 0;
//		int quantityNew = 0;
//		// Nếu lst.size ==1 thì update trực tiếp
//		if (lstDetail.size() == 1) {
//			quantityOld = lstDetail.get(0).getQuantity();
//			quantityNew = billDetail.getQuantity();
//			billDetail.setTotalMoney(new BigDecimal(billDetail.getPrice().floatValue() * billDetail.getQuantity()));
//			billDetailRepository.save(billDetail);
//
//		} else {
//			// kiểm tra từng pv có trong list
//			// giá billDetail update trùng với giá billDetail đã có thì tăng số lượng đã
//			// billDetail đã có,
//			// Xóa billDetail cần update,
//			// Còn không thì update trực tiếp
//			for (int i = 0; i < lstDetail.size(); i++) {
//				BillDetail detail = lstDetail.get(i);
//				if (detail.getDetailBillId() != billDetail.getDetailBillId()) {
//					if (!detail.getPrice().equals(billDetail.getPrice())) {
//						quantityOld = detail.getQuantity();
//						quantityNew = billDetail.getQuantity();
//						billDetail.setTotalMoney(
//								new BigDecimal(billDetail.getPrice().floatValue() * billDetail.getQuantity()));
//						// update billDetail
//						billDetailRepository.save(billDetail);
//
//					} else {
//						BillDetail detailOld = billDetailRepository.findById(billDetail.getDetailBillId()).get();
//						quantityOld = detailOld.getQuantity() + detail.getQuantity();
//						detail.setQuantity(detail.getQuantity() + billDetail.getQuantity());
//						detail.setTotalMoney(new BigDecimal(detail.getPrice().floatValue() * detail.getQuantity()));
//						quantityNew = detail.getQuantity();
//						// update billDetail
//						billDetailRepository.save(detail);
//						billDetailRepository.delete(billDetail);
//					}
//				}
//			}
//		}

		// update quantity ProductVariant
		ProductVariant productVariant = productVariantRepository
				.findById(billDetail.getProductVariants().getVariantId()).get();
		productVariant.setQuantity(productVariant.getQuantity() - (quantityNew - quantityOld));
		productVariantRepository.save(productVariant);

		// update totalmoney bill
		Bill bill = billDetail.getBills();
		List<BillDetail> details = billDetailRepository.findByBillsLike(bill);
		totalMoney = new BigDecimal("0.0");
		details.forEach(d -> {
			totalMoney = totalMoney.add(d.getTotalMoney());
		});
		BigDecimal totalMoneyNew = totalMoney.subtract(
				(totalMoney.multiply(BigDecimal.valueOf(bill.getDiscount()))).divide(BigDecimal.valueOf(100)));
		totalMoneyNew = totalMoneyNew.add(bill.getShippingFee());
		bill.setTotalMoney(totalMoneyNew);
		billRepository.save(bill);

		return billDetail;
	}

	@Override
	@Transactional
	public synchronized List<BillDetail> returnBillDetailOfAdmin(List<BillDetail> billDetails) {
		List<BillDetail> detailReturns = new ArrayList<BillDetail>();
		billDetails.forEach(billDetail -> {
			// Lấy lại billDetail trước đó
			BillDetail detailOld = billDetailRepository.findById(billDetail.getDetailBillId()).get();

			if (billDetail.getQuantity() <= detailOld.getQuantity()) {
				if (billDetail.getQuantity() == detailOld.getQuantity()) {
					// Nếu 2 quantity bằng nhau thì update status
					detailOld.setNote(billDetail.getNote());
					detailOld.setStatus(1);
					billDetailRepository.save(detailOld);
				} else if (billDetail.getQuantity() < detailOld.getQuantity()) {
					// Nếu quantity trả < quantity cũ thì tạo detail mới với số lượng còn lại
					// và update lại số lượng detail cũ
					// Tạo billDetail sản phẩm mà khách không hoàn trả
					BillDetail detailNew = new BillDetail();
					detailNew.setBills(detailOld.getBills());
					detailNew.setPrice(detailOld.getPrice());
					detailNew.setTax(detailOld.getTax());
					detailNew.setProductVariants(detailOld.getProductVariants());
					detailNew.setQuantity(detailOld.getQuantity() - billDetail.getQuantity());
					BigDecimal money = detailNew.getPrice().multiply(BigDecimal.valueOf(detailNew.getQuantity()));
					money = money.add(money.multiply(detailNew.getTax()).divide(BigDecimal.valueOf(100)));
					detailNew.setTotalMoney(money);
					detailNew.setStatus(0);

					// Update lại billDetail cũ với số lượng khách hoàn trả trạng thái hoàn trả
					detailOld.setQuantity(billDetail.getQuantity());
					BigDecimal totalMoney = detailOld.getPrice().multiply(BigDecimal.valueOf(detailOld.getQuantity()));
					totalMoney = totalMoney
							.add(totalMoney.multiply(detailOld.getTax()).divide(BigDecimal.valueOf(100)));
					detailOld.setTotalMoney(totalMoney);
					detailOld.setStatus(1);
					detailOld.setNote(billDetail.getNote());

					// Update billDetail
					billDetailRepository.save(detailNew);
					billDetailRepository.save(detailOld);

				}
				// Add detailOld to detailReturns
				detailReturns.add(detailOld);

				// Update quantity productVariant
				ProductVariant pv = detailOld.getProductVariants();
				pv.setQuantity(pv.getQuantity() + detailOld.getQuantity());
				productVariantRepository.save(pv);

				// Update totalMoney Bill
				Bill bill = billRepository.findById(detailOld.getBills().getBillId()).get();
				List<BillDetail> details = billDetailRepository.findByBillDetailEligibleForReturn(bill);
				totalMoney = new BigDecimal("0.0");
				details.forEach(d -> {
					if (d.getStatus() == 0) {
						totalMoney = totalMoney.add(d.getTotalMoney());
					}
				});
				totalMoney = totalMoney.subtract(
						(totalMoney.multiply(BigDecimal.valueOf(bill.getDiscount()))).divide(BigDecimal.valueOf(100)));
				totalMoney = totalMoney.add(bill.getShippingFee());
				bill.setTotalMoney(totalMoney);
				billRepository.save(bill);
			}
		});
		return detailReturns;
	}

	@Override
	@Transactional
	public synchronized BillDetail deleteBillDetail(BillDetail billDetail) {
		billDetailRepository.delete(billDetail);

		// update quantity ProductVariant
		ProductVariant productVariant = productVariantRepository
				.findById(billDetail.getProductVariants().getVariantId()).get();
		productVariant.setQuantity(productVariant.getQuantity() + billDetail.getQuantity());
		productVariantRepository.save(productVariant);
		return billDetail;
	}

	@Override
	public List<BillDetail> findByBillDetailReturnInvoices(Bill bill) {
		return billDetailRepository.findByBillDetailReturnInvoices(bill);
	}

	@Override
	public List<BillDetail> findByBillDetailEligibleForReturn(Bill bill) {
		return billDetailRepository.findByBillDetailEligibleForReturn(bill);
	}

	@Override
	public BillDetail findById(String detailBillId) {
		return billDetailRepository.findById(detailBillId).get();
	}

}
