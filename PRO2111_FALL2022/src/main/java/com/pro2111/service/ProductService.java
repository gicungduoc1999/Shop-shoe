/**
 * Luvina Software JSC, 2022
 * ProductService.java, Bui Quang Hieu
 */
package com.pro2111.service;

import java.util.List;

import com.pro2111.entities.Product;

/**
 * @author BUI_QUANG_HIEU
 */
public interface ProductService {
	
	public List<Product> findAll();
	
	public Product create(Product product);

	/**
	 * @param id
	 * @return Product
	 */
	public Product findById(Integer id);

	/**
	 * @param product
	 * @return product
	 */
	public Product update(Product product);

	/**
	 * @param name
	 * @return product
	 */
	public Product findByName(String name);

	/**
	 * @param int status
	 * @return
	 */
	public List<Product> findByStatusLike(int status);

	/**
	 * @param name
	 * @return
	 */
	public List<Product> findByApproximateName(String name);

	public Product delete(Product product);

//	public List<Product> findByAllName(String name);

}
