/**
 * Luvina Software JSC, 2022
 * VariantValueService.java, Bui Quang Hieu
 */
package com.pro2111.service;

import java.util.List;

import javax.validation.Valid;

import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.VariantValue;

/**
 * @author BUI_QUANG_HIEU
 */
public interface VariantValueService {

	public List<VariantValue> findAll();

	public List<VariantValue> findByProductOption(ProductOption productOption);

	public ProductOption findByProductsLikeAndOptionsLike(Product product, Option option);

	public List<VariantValue> findByProductVariant(ProductVariant productVariant);
	public List<VariantValue> findByProductVariantsLikeAndStatusLike(ProductVariant productVariant, Integer status);

//	public void updateStatus(ProductVariant productVariant, Product product, Option option, Integer status) throws Exception;

	public void createVariantValue(ProductVariant productVariants, Product products, Option options,
			OptionValue optionValues);
	
	public List<VariantValue> findByProduct(Product product);

	/**
	 * @param productVariant
	 * @return
	 */
	public List<VariantValue> findByProductVariantOrigin(ProductVariant productVariant);

	/**
	 * @param variantValues
	 * @return
	 */
	public List<VariantValue> create(List<VariantValue> variantValues);


}