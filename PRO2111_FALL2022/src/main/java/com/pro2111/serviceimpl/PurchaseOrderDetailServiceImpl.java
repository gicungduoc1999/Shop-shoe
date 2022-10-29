/**
 * DATN_FALL2022, 2022
 * PurchaseOrderDetailServiceIml.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro2111.repositories.PurchaseOrderDetailRepository;
import com.pro2111.service.PurchaseOrderDetailService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public class PurchaseOrderDetailServiceImpl implements PurchaseOrderDetailService {

	@Autowired
	private PurchaseOrderDetailRepository purchaseOrderDetailRepository;
}
