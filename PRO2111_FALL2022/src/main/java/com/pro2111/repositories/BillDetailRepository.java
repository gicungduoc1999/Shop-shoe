/**
 * DATN_FALL2022, 2022
 * BillDetailRepository.java, BUI_QUANG_HIEU
 */
package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;
import com.pro2111.entities.ProductVariant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface BillDetailRepository extends JpaRepository<BillDetail, String> {

	List<BillDetail> findByBillsLike(Bill bill);

	List<BillDetail> findByProductVariantsLikeAndBillsLike(ProductVariant productVariant, Bill bill);

	@Query("SELECT bd FROM BillDetail bd WHERE bd.bills =:bill AND bd.status != 0")
	List<BillDetail> findByBillDetailReturnInvoices(@Param("bill") Bill bill);

	@Query("SELECT bd FROM BillDetail bd WHERE bd.bills =:bill AND bd.status = 0")
	List<BillDetail> findByBillDetailEligibleForReturn(@Param("bill") Bill bill);

}
