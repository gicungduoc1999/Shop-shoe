/**
 * DATN_FALL2022, 2022
 * BillRepository.java, BUI_QUANG_HIEU
 */
package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Bill;


/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface BillRepository extends JpaRepository<Bill, String> {

	@Query("SELECT COUNT(b) FROM Bill b WHERE b.billId LIKE %:billId%")
	Long countBillId(@Param("billId") String billId);

	List<Bill> findByBillIdLike(String id);

	/**
	 * @param inputString
	 * @return List<Bill>
	 */
	@Query("SELECT b FROM Bill b WHERE b.billId LIKE %:input%")
	List<Bill> searchApproximateBill(@Param("input") String inputString);

	/**
	 * @param inputString
	 * @return List<Bill>
	 */
	@Query("SELECT b FROM Bill b WHERE b.customer.fullName LIKE %:input%")
	List<Bill> searchApproximateCustomer(@Param("input") String inputString);

	/**
	 * @param inputString
	 * @return List<Bill>
	 */
	@Query("SELECT b FROM Bill b WHERE b.phone LIKE %:input%")
	List<Bill> searchApproximatePhone(@Param("input") String inputString);

	/**
	 * @param inputString
	 * @return List<Bill>
	 */
	@Query("SELECT b FROM Bill b WHERE b.address LIKE %:input%")
	List<Bill> searchApproximateAddress(@Param("input") String inputString);

	/**
	 * @param inputString
	 * @return List<Bill>
	 */
	@Query("SELECT b FROM Bill b WHERE b.user.fullName LIKE %:input%")
	List<Bill> searchApproximateSeller(@Param("input") String inputString);

	/**
	 * @return
	 */
	@Query("SELECT b FROM Bill b WHERE datediff(now(), b.successDate) <=10 AND b.status = 4 AND EXISTS (SELECT 1 FROM BillDetail bd WHERE bd.bills = b AND bd.status = 0)")
	List<Bill> findBillEligibleForReturn();

	/**
	 * @return
	 */
	@Query("SELECT b FROM Bill b WHERE EXISTS (SELECT 1 FROM BillDetail bd WHERE bd.bills = b AND bd.status != 0)")
	List<Bill> findBillReturnInvoices();

	List<Bill> findAllByCustomer_UserId(Integer id);

}
