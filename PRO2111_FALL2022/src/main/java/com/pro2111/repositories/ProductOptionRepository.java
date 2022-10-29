package com.pro2111.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Option;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {
	List<ProductOption> findByProductsLike(Product product);
	
	List<ProductOption> findByProductsLikeAndStatusLike(Product product, Integer status);

	@Modifying
	@Query(value = "INSERT INTO product_options (Product_id, Option_id, Status) VALUES (:products, :options, :status)", nativeQuery = true)
	@Transactional
	void insertPO(@Param("products") Product product, @Param("options") Option option, @Param("status") Integer status);

	ProductOption findByProductsLikeAndOptionsLike(Product product, Option option);

	@Modifying
	@Query(value = "UPDATE product_options SET Option_id = :options, Status = :status WHERE Product_id = :products AND Option_id = :optionOld", nativeQuery = true)
	@Transactional
	void updatePO(@Param("products") Product product, @Param("options") Option option,
			@Param("optionOld") Option optionOld, @Param("status") Integer status);

	@Modifying
	@Query(value = "UPDATE product_options SET Status = :status WHERE Product_id = :products AND Option_id = :options", nativeQuery = true)
	@Transactional
	void updateStatusPO(@Param("products") Product product, @Param("options") Option option,
			@Param("status") Integer status);

	@Modifying
	@Query(value = "DELETE FROM product_options WHERE Product_id = :products", nativeQuery = true)
	@Transactional
	void deletePOByProduct(@Param("products") Product product);

	@Modifying
	@Query(value = "DELETE FROM product_options WHERE Product_id = :product AND Option_id =:option", nativeQuery = true)
	@Transactional
	void deletePO(@Param("product") Product product, @Param("option") Option option);

//	@Modifying
//	@Query(value = "SELECT *  FROM product_options WHERE Option_id = :option", nativeQuery = true)
	List<ProductOption> findByOptionsLike(Option option);
}
