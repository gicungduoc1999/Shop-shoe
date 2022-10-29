/**
 * DATN_FALL2022, 2022
 * BillServiceIml.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pro2111.beans.BillAndBillDetail;
import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.VariantValue;
import com.pro2111.repositories.BillDetailRepository;
import com.pro2111.repositories.BillRepository;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.repositories.VariantValueRepository;
import com.pro2111.service.BillService;
import com.pro2111.service.SmtpMailSender;
import com.pro2111.utils.FormateNumber;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Service
public class BillServiceImpl implements BillService {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private BillDetailRepository billDetailRepository;

	@Autowired
	private ProductVariantRepository productVariantRepository;

	@Autowired
	private SmtpMailSender smtpMailSender;

	private Bill _billReturn;

	private BigDecimal _totalMoneyBigDecimal;
	private BigDecimal _totalMoneySend;

	private Integer _countRun = 0;

	private String _to = "";
	private String _subject = "";
	private String _content = "";

	@Override
	public synchronized Long countBillId(String billId) {
		return billRepository.countBillId(billId);
	}

	@Override
	public synchronized List<Bill> findByBillIdLike(String id) {
		return billRepository.findByBillIdLike(id);
	}

	@Override
	public synchronized Bill createBill(Bill bill) {
		return billRepository.save(bill);
	}

	@Override
	@Transactional
	public synchronized Bill createdBillAndBillDetail(BillAndBillDetail billAndBillDetail) {
		try {
			List<BillDetail> lstBillDetails = new ArrayList<BillDetail>();
			_totalMoneyBigDecimal = new BigDecimal("0.0");
			// set date in bill
			billAndBillDetail.getBill().setCreatedDate(LocalDateTime.now());
			// create bill
			Bill bill = billRepository.save(billAndBillDetail.getBill());

			// duyệt mảng BillDetails
			billAndBillDetail.getBillDetails().forEach(detail -> {
				// set bill in billDetail
				detail.setBills(bill);
				BigDecimal money = BigDecimal.valueOf(detail.getQuantity()).multiply(detail.getPrice());
				money = money.add(money.multiply(detail.getTax()).divide(BigDecimal.valueOf(100)));
				detail.setTotalMoney(money);
				detail.setStatus(0);
				// create billDetail
				BillDetail billDetail = billDetailRepository.save(detail);
				_totalMoneyBigDecimal = _totalMoneyBigDecimal.add(billDetail.getTotalMoney());

				lstBillDetails.add(billDetail);
				// set quantity productVariant
				Long pvId = detail.getProductVariants().getVariantId();
				Integer quantityBill = detail.getQuantity();
				ProductVariant pv = productVariantRepository.findById(pvId).get();
				Integer quantityPv = pv.getQuantity();
				pv.setQuantity(quantityPv - quantityBill);
				productVariantRepository.save(pv);
			});

			// update total_money bill
			_totalMoneySend = _totalMoneyBigDecimal;
			BigDecimal totalMoney = _totalMoneyBigDecimal
					.subtract((_totalMoneyBigDecimal.multiply(BigDecimal.valueOf(bill.getDiscount())))
							.divide(BigDecimal.valueOf(100)));
			totalMoney = totalMoney.add(bill.getShippingFee());

			bill.setTotalMoney(totalMoney);

			if (bill.getStatus() == 4 && bill.getSuccessDate() == null) {
				bill.setSuccessDate(new Date());
			}
			_billReturn = billRepository.save(bill);
			// SendMail
			if (_billReturn.getCustomers() != null && bill.getStatus() == 4) {
				_countRun = 0;
				run();
			}
			return bill;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public synchronized List<Bill> getAllBill() {
		return billRepository.findAll();
	}

	@Override
	public synchronized Bill findById(String id) {
		return billRepository.findById(id).get();
	}

	@Override
	public synchronized List<Bill> searchApproximateBill(String inputString) {
		return billRepository.searchApproximateBill(inputString);
	}

	@Override
	public synchronized List<Bill> searchApproximateCustomer(String inputString) {
		return billRepository.searchApproximateCustomer(inputString);
	}

	@Override
	public synchronized List<Bill> searchApproximatePhone(String inputString) {
		return billRepository.searchApproximatePhone(inputString);
	}

	@Override
	public synchronized List<Bill> searchApproximateAddress(String inputString) {
		return billRepository.searchApproximateAddress(inputString);
	}

	@Override
	public synchronized List<Bill> searchApproximateSeller(String inputString) {
		return billRepository.searchApproximateSeller(inputString);
	}

	@Override
	public synchronized Bill updateBill(Bill bill) {
		if (bill.getStatus() == 4 && bill.getSuccessDate() == null) {
			bill.setSuccessDate(new Date());
		} else if (bill.getStatus() == 6) {
			List<BillDetail> billDetails = billDetailRepository.findByBillsLike(bill);
			billDetails.forEach(d -> {
				ProductVariant pv = d.getProductVariants();
				pv.setQuantity(pv.getQuantity() + d.getQuantity());
				productVariantRepository.save(pv);
			});
		}
		return billRepository.save(bill);
	}

	@Override
	public synchronized List<Bill> findBillEligibleForReturn() {
		return billRepository.findBillEligibleForReturn();
	}

	@Override
	public List<Bill> findBillReturnInvoices() {
		List<Bill> bills = billRepository.findBillReturnInvoices();
		bills.forEach(b -> {
			List<BillDetail> billDetails = new ArrayList<>(b.getDetailBills());
			_totalMoneyBigDecimal = new BigDecimal("0.0");
			billDetails.forEach(detail -> {
				if (detail.getStatus() != 0) {
					_totalMoneyBigDecimal = _totalMoneyBigDecimal.add(detail.getTotalMoney());
				}
			});
			b.setTotalMoney(_totalMoneyBigDecimal);
		});
		return bills;
	}

	@Scheduled(fixedRate = 1000, initialDelay = 2000)
	public void run() throws MessagingException {
		try {
			if (_countRun < 0) {
				_to = "";
				return;
			}
			_countRun = -1;
			_to = _billReturn.getCustomers().getEmail();
			_subject = "[ĐẶT HÀNG THÀNH CÔNG]";
			StringBuffer text = new StringBuffer();
			text.append("<p>Xin chào, <strong>" + _billReturn.getCustomers().getFullName() + "</strong></p>");
			text.append("<p>Cảm ơn bạn ghé thăm và mua sản phẩm bên Shop !</p>");
			text.append("<p>Mã hóa đơn: <strong>" + _billReturn.getBillId() + "</strong></p>");
			text.append("<p>Trạng thái hóa đơn: <strong>Hoàn thành</strong></p>");
			text.append(
					"<p>Dưới đây là danh sách sản phẩm bạn mua. Nếu có vấn đề liên quan đến sản phẩm và giá cả. Bạn vui lòng liên hệ lại cho Shop !</p>");
			text.append("<p><i>Xin chân thành cảm ơn !</i></p><br>");
			text.append("<table width='100%' border='1' align='center' style='font-size: 11pt;'>");
			// thead
			text.append("<thead>");
			text.append("<tr>");
			text.append("<th>Sản phẩm</th>");
			text.append("<th>Số lượng</th>");
			text.append("<th>Đơn giá (VNĐ)</th>");
			text.append("<th>VAT (%)</th>");
			text.append("<th>Thành tiền (VNĐ)</th>");
			text.append("</tr>");
			text.append("</thead>");
			// tbody
			text.append("<tbody>");
			List<BillDetail> details = billDetailRepository.findByBillsLike(_billReturn);

			for (int i = 0; i < details.size(); i++) {
				ProductVariant pv = details.get(i).getProductVariants();

				text.append("<tr>");

				text.append("<td>");
				text.append(pv.getProducts().getProductName());
				text.append("</td>");

				text.append("<td align='right'>");
				text.append(details.get(i).getQuantity());
				text.append("</td>");

				text.append("<td align='right'>");
				text.append(FormateNumber.formateBigDecimal(details.get(i).getPrice()));
				text.append("</td>");

				text.append("<td align='right'>");
				text.append(FormateNumber.formateBigDecimal(details.get(i).getTax()));
				text.append("</td>");

				text.append("<td align='right'>");
				text.append(FormateNumber.formateBigDecimal(details.get(i).getTotalMoney()));
				text.append("</td>");

				text.append("</tr>");

			}

			text.append("<tr>");
			text.append("<td colspan='4'><b>Tổng tiền:</b></td>");
			text.append("<td align='right'>");
			text.append(FormateNumber.formateBigDecimal(_totalMoneySend));
			text.append("</td>");
			text.append("</tr>");

			text.append("<tr>");
			text.append("<td colspan='4'><b>Phí ship:</b></td>");
			text.append("<td align='right'>");
			text.append(FormateNumber.formateBigDecimal(_billReturn.getShippingFee()));
			text.append("</td>");
			text.append("</tr>");

			text.append("<tr>");
			text.append("<td colspan='4'><b>Giảm giá:</b></td>");
			text.append("<td align='right'>");
			text.append(_billReturn.getDiscount());
			text.append("%");
			text.append("</td>");
			text.append("</tr>");

			text.append("<tr>");
			text.append("<td colspan='4'><b>Thành tiền:</b></td>");
			text.append("<td align='right'>");
			text.append(FormateNumber.formateBigDecimal(_billReturn.getTotalMoney()));
			text.append("</td>");
			text.append("</tr>");

			text.append("</tbody>");
			text.append("</table>");
			_content = text.toString();
			smtpMailSender.sendMail(_to, _subject, _content);

		} catch (Exception e) {

		}

	}

}
