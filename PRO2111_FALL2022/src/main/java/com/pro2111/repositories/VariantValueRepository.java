package com.pro2111.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.VariantValue;

public interface VariantValueRepository extends JpaRepository<VariantValue, Integer> {

	List<VariantValue> findByProductOptionsLike(ProductOption productOption);

	List<VariantValue> findByStatusLike(Integer status);

	List<VariantValue> findByProductVariantsLike(ProductVariant productVariant);

	List<VariantValue> findByProductVariantsLikeAndStatusLike(ProductVariant productVariant, Integer status);

	@Transactional
	@Modifying
	@Query(value = "UPDATE variant_values vv SET vv.status = :status WHERE vv.Variant_id = :productVariant AND "
			+ "vv.Product_id = :product AND vv.Option_id = :option", nativeQuery = true)
	void updateStatus(@Param("productVariant") ProductVariant productVariant, @Param("product") Product product,
			@Param("option") Option option, @Param("status") Integer status);

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO variant_values (Variant_id, Product_id, Option_id, Value_id) VALUES "
			+ "(:productVariant, :product, :option, :optionValue)", nativeQuery = true)
	void createVariantValue(@Param("productVariant") ProductVariant pv, @Param("product") Product p,
			@Param("option") Option o, @Param("optionValue") OptionValue ov);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM variant_values WHERE Variant_id = :productVariant", nativeQuery = true)
	void deleteAll(@Param("productVariant") ProductVariant pv);

	/**
	 * @param product
	 * @return
	 */
	@Query("SELECT vv FROM VariantValue vv WHERE vv.productOptions.products = :product")
	List<VariantValue> findByProduct(@Param("product") Product product);

	/**
	 * @param option
	 * @return
	 */
	@Query("SELECT vv FROM VariantValue vv WHERE vv.productOptions.options = :option")
	List<VariantValue> findByOption(@Param("option") Option option);

}
