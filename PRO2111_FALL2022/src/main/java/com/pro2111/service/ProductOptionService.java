/**
 * Luvina Software JSC, 2022
 * ProductOptionService.java, Bui Quang Hieu
 */
package com.pro2111.service;

import java.util.List;

import com.pro2111.entities.Option;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;

/**
 * @author BUI_QUANG_HIEU
 */
public interface ProductOptionService {

	public List<ProductOption> findAll();

	/**
	 * @param product
	 * @return List<ProductOption>
	 */
	public List<ProductOption> findByProduct(Product product);

	/**
	 * @param productOption
	 * @return
	 */
	public void create(Product product, Option option, Integer status);

	public void update(Product products, Option options, Option optionOld, Integer status);
	
	public void updateStatus(Product products, Option options, Integer status);

	public List<ProductOption> findByProductAndStatusTrue(Product product);
}
