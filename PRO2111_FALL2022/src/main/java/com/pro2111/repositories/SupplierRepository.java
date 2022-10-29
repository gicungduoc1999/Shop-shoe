/**
 * DATN_FALL2022, 2022
 * SuppilerRepository.java, BUI_QUANG_HIEU
 */
package com.pro2111.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro2111.entities.Supplier;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

}
