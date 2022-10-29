/**
 * DATN_FALL2022, 2022
 * BillService.java, BUI_QUANG_HIEU
 */
package com.pro2111.service;

import java.util.List;

import com.pro2111.beans.BillAndBillDetail;
import com.pro2111.entities.Bill;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface BillService {

	/**
	 * 
	 * @param billId
	 * @return Long countBillId
	 */
	Long countBillId(String billId);

	/**
	 * 
	 * @param id
	 * @return List<Bill>
	 */
	List<Bill> findByBillIdLike(String id);

	/**
	 * 
	 * @param bill
	 * @return Bill
	 */
	Bill createBill(Bill bill);

	/**
	 * 
	 * @param billAndBillDetail
	 * @return Bill
	 */
	Bill createdBillAndBillDetail(BillAndBillDetail billAndBillDetail);

	/**
	 * @return List<Bill>
	 */
	List<Bill> getAllBill();

	Bill findById(String id);

	/**
	 * @return List<Bill>
	 */
	List<Bill> searchApproximateBill(String inputString);

	/**
	 * @param inputString
	 * @return
	 */
	List<Bill> searchApproximateCustomer(String inputString);

	/**
	 * @param inputString
	 * @return
	 */
	List<Bill> searchApproximatePhone(String inputString);

	/**
	 * @param inputString
	 * @return
	 */
	List<Bill> searchApproximateAddress(String inputString);

	/**
	 * @param inputString
	 * @return
	 */
	List<Bill> searchApproximateSeller(String inputString);

	/**
	 * @param idBill
	 * @return
	 */
	Bill updateBill(Bill bill);

	/**
	 * @return
	 */
	List<Bill> findBillEligibleForReturn();

	/**
	 * @return
	 */
	List<Bill> findBillReturnInvoices();
}
