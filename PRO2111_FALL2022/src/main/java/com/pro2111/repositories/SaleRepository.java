package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, String> {

	@Query("SELECT s FROM Sale s WHERE s.saleName = :name")
	public Sale findSaleByName(@Param("name") String name);

	/**
	 * @param i
	 * @return
	 */
	@Query("SELECT s FROM Sale s WHERE s.status = :i")
	public List<Sale> findByStatus(int i);
	
	// Tìm kiếm saleParent
	@Query("SELECT s FROM Sale s WHERE s.saleParent is null")
	public List<Sale> findAllSaleParent();
	
	//Tìm kiếm saleChile theo saleParent
	@Query("SELECT s FROM Sale s WHERE s.saleParent = :sid")
	public List<Sale> findSaleChileBySaleParent(@Param("sid") Sale sale);
}
