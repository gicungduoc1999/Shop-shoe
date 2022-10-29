/**
 * DATN_FALL2022, 2022
 * PurchaseOrderServiceIml.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.beans.PurchaseOrderBean;
import com.pro2111.entities.Bill;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.PurchaseOrder;
import com.pro2111.entities.PurchaseOrderDetail;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.repositories.PurchaseOrderDetailRepository;
import com.pro2111.repositories.PurchaseOrderRepository;
import com.pro2111.service.PurchaseOrderService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepo;
	@Autowired
	private PurchaseOrderDetailRepository purchaseOrderDetailRepo;
	@Autowired
	private ProductVariantRepository productVariantRepo;

	private BigDecimal _totalMoney;

	@Override
	public String getPurchaseId() {
		String purchaseId = "";
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
			Date d = new Date();
			purchaseId = dateFormat.format(d);

			long countPurchase = purchaseOrderRepo.countPurchaseId(purchaseId);

			boolean dup = false;
			do {
				if (countPurchase > 998) {
					purchaseId = purchaseId + (countPurchase + 1);
				} else if (countPurchase > 98) {
					purchaseId = purchaseId + "0" + (countPurchase + 1);
				} else if (countPurchase > 8) {
					purchaseId = purchaseId + "00" + (countPurchase + 1);
				} else {
					purchaseId = purchaseId + "000" + (countPurchase + 1);
				}
				List<Bill> list = purchaseOrderRepo.findByPurchaseIdLike(purchaseId);
				if (list.size() > 0) {
					dup = true;
					countPurchase++;
					purchaseId = dateFormat.format(d);
				} else {
					dup = false;
				}
				purchaseId = "PN" + purchaseId;
			} while (dup);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Lỗi số hóa đơn");
			purchaseId = "";
		}
		return purchaseId;
	}

	@Override
	@Transactional
	public PurchaseOrder createPurchaseOrder(PurchaseOrderBean bean) {
		try {
			List<PurchaseOrderDetail> details = new ArrayList<>();
			_totalMoney = new BigDecimal(0);

			bean.getPurchaseOrder().setCreatedDate(LocalDateTime.now());
			PurchaseOrder purchaseOrder = purchaseOrderRepo.save(bean.getPurchaseOrder());

			bean.getPurchaseOrderDetails().forEach(detail -> {
				detail.setPurchaseOrder(purchaseOrder);
				BigDecimal money = detail.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity()));
				BigDecimal tax = money.multiply(detail.getTax()).divide(BigDecimal.valueOf(100));
				money = money.add(tax);
				if (detail.getDiscountType() == 0) {
					money = money.subtract(BigDecimal.valueOf(detail.getDiscount()));
				} else {
					BigDecimal discount = money
							.multiply(BigDecimal.valueOf(detail.getDiscount()).divide(BigDecimal.valueOf(100)));
					money = money.subtract(discount);
				}
				detail.setTotalMoney(money);
				detail.setStatus(1);
				_totalMoney = _totalMoney.add(money);

				// update productVariant
				ProductVariant pvOld = productVariantRepo.findById(detail.getProductVariants().getVariantId()).get();
				pvOld.setImportPrice(money);
				pvOld.setTax(detail.getTax());
				pvOld.setQuantity(pvOld.getQuantity() + detail.getProductVariants().getQuantity());
				productVariantRepo.save(pvOld);

				// save PurchaseOrderDetails
				purchaseOrderDetailRepo.save(detail);

			});

			if (purchaseOrder.getDiscountType() == 0) {
				_totalMoney = _totalMoney.subtract(BigDecimal.valueOf(purchaseOrder.getDiscount()));
			} else {
				BigDecimal discount = _totalMoney
						.multiply(BigDecimal.valueOf(purchaseOrder.getDiscount()).divide(BigDecimal.valueOf(100)));
				_totalMoney = _totalMoney.subtract(discount);
			}

			purchaseOrder.setTotalMoney(_totalMoney);
			purchaseOrderRepo.save(purchaseOrder);

			return purchaseOrder;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
