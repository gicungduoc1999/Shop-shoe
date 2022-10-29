package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;

public interface OptionValueRepository extends JpaRepository<OptionValue, Integer> {

	List<OptionValue> findByOptionsLike(Option option);

	OptionValue findByValueNameLike(String name);

	List<OptionValue> findByStatusLike(Integer status);

	List<OptionValue> findByStatusLikeAndOptionsLike(Integer status, Option option);

	/**
	 * @param product
	 * @param option
	 * @return
	 */
	@Query("SELECT ov FROM OptionValue ov WHERE ov.options = :option AND NOT EXISTS ("
			+ "SELECT vv FROM VariantValue vv WHERE vv.optionValues = ov AND vv.productOptions.products = :product) AND ov.status = 1")
	List<OptionValue> findNotExistsVariantValue(@Param("product") Product product, @Param("option") Option option);

	/**
	 * @param name
	 * @return
	 */
	@Query("SELECT ov FROM OptionValue ov  WHERE ov.valueName LIKE %:name%")
	List<OptionValue> findByApproximateName(String name);

	/**
	 * @param input
	 * @return
	 */
	@Query(value = "SELECT * FROM option_values WHERE match(Value_name) against(:input)", nativeQuery = true)
	List<OptionValue> fullTextSearch(@Param("input") String input);
}
