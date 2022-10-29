/**
 * DATN_FALL2022, 2022
 * PurchaseOrderService.java, BUI_QUANG_HIEU
 */
package com.pro2111.service;

import javax.validation.Valid;

import com.pro2111.beans.PurchaseOrderBean;
import com.pro2111.entities.PurchaseOrder;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface PurchaseOrderService {
	public String getPurchaseId();

	/**
	 * @param bean
	 * @return
	 */
	public PurchaseOrder createPurchaseOrder(PurchaseOrderBean bean);
}
