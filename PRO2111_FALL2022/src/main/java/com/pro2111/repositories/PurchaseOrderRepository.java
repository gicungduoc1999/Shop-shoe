/**
 * DATN_FALL2022, 2022
 * PurchaseOrderRepository.java, BUI_QUANG_HIEU
 */
package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Bill;
import com.pro2111.entities.PurchaseOrder;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String> {
	@Query("SELECT COUNT(p) FROM PurchaseOrder p WHERE p.purchaseId LIKE %:purchaseId%")
	Long countPurchaseId(@Param("purchaseId") String purchaseId);

	/**
	 * @param purchaseId
	 * @return
	 */
	List<Bill> findByPurchaseIdLike(String purchaseId);
}
