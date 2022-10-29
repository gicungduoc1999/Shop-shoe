/**
 * Luvina Software JSC, 2022
 * OptionService.java, Bui Quang Hieu
 */
package com.pro2111.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.pro2111.entities.Option;
import com.pro2111.entities.Product;

/**
 * Khai báo các method của tầng service
 * @author BUI_QUANG_HIEU
 */
public interface OptionService {

	/**
	 * @param 
	 * @return list option
	 */
	public List<Option> findAll();

	/**
	 * @param id
	 * @return option
	 */
	public Option findById(Integer id);

	/**
	 * @param option
	 * @return option
	 */
	public Option create(Option option);

	/**
	 * @param option
	 * @return option
	 */
	public Option update(Option option);

	/**
	 * @param product
	 * @return list option
	 */
	public List<Option> findOptionNotExistsProduct(Product product);

	/**
	 * @param i
	 * @return
	 */
	public List<Option> findByStatusLike(int status);

	/**
	 * @param value
	 * @return
	 */
	public Option findByName(String name);

	public List<Option> findByApproximateName(String name);

	/**
	 * @param option
	 * @return
	 */
	public Boolean checkDeleteOption(Option option);

	/**
	 * @param option
	 * @return
	 */
	public Option deleteOption(Option option);
	
}
