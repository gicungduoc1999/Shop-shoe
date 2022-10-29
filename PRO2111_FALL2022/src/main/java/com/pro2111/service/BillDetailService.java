/**
 * DATN_FALL2022, 2022
 * BillDetailService.java, BUI_QUANG_HIEU
 */
package com.pro2111.service;

import java.math.BigDecimal;
import java.util.List;


import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface BillDetailService {

	BillDetail createdBillDetail(BillDetail billDetail);

	/**
	 * @param bill
	 * @return List<BillDetail>
	 */
	List<BillDetail> findByBill(Bill bill);

	/**
	 * @param billDetail
	 * @return
	 */
	BillDetail updateBillDetail(BillDetail billDetail);
	
	List<BillDetail> returnBillDetailOfAdmin(List<BillDetail> billDetails);

	/**
	 * @param billDetail
	 * @return
	 */
	BillDetail deleteBillDetail(BillDetail billDetail);

	/**
	 * @param bill
	 * @return
	 */
	List<BillDetail> findByBillDetailReturnInvoices(Bill bill);

	/**
	 * @param bill
	 * @return
	 */
	List<BillDetail> findByBillDetailEligibleForReturn(Bill bill);

	/**
	 * @param detailBillId
	 * @return
	 */
	BillDetail findById(String detailBillId);

}
