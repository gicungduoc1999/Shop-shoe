package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Product findByProductNameLike(String nameProduct);

	List<Product> findByStatusLike(int status);

	@Query("SELECT p FROM Product p WHERE p.productName LIKE %:name%")
	List<Product> findByApproximateName(@Param("name") String name);

//	@Query("SELECT vv.productOptions.products FROM VariantValue vv WHERE (vv.productOptions.products.productName LIKE %:name% "
//			+ "OR vv.productOptions.options.optionName LIKE %:name% OR vv.optionValues.valueName LIKE %:name% "
//			+ "OR vv.productVariants.quantity LIKE %:name% OR vv.productVariants.price LIKE %:name% OR vv.productVariants.skuId LIKE %:name% )"
//			+ "AND vv.status = 1 GROUP BY vv.productOptions.products")
//	List<Product> findByAllName(@Param("name") String name);
}
