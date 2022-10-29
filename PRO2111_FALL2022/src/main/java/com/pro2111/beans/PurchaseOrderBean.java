/**
 * DATN_FALL2022, 2022
 * PurchaseOrderBean.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pro2111.entities.PurchaseOrder;
import com.pro2111.entities.PurchaseOrderDetail;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
@NoArgsConstructor
public class PurchaseOrderBean {
	@NotNull
	private PurchaseOrder purchaseOrder;

	@NotEmpty
	private List<PurchaseOrderDetail> purchaseOrderDetails;
}
