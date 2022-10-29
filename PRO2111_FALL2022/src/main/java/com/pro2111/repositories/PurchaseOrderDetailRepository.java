/**
 * DATN_FALL2022, 2022
 * PurchaseOrderDetailRepository.java, BUI_QUANG_HIEU
 */
package com.pro2111.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro2111.entities.PurchaseOrderDetail;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, String> {

}
