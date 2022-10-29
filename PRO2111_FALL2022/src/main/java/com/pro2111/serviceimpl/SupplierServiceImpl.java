/**
 * DATN_FALL2022, 2022
 * SupplierServiceImpl.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro2111.repositories.SupplierRepository;
import com.pro2111.service.SupplierService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public class SupplierServiceImpl implements SupplierService {
	@Autowired
	private SupplierRepository supplierRepository;
}
