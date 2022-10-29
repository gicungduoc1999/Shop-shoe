/**
 * Luvina Software JSC, 2022
 * OptionValueService.java, Bui Quang Hieu
 */
package com.pro2111.service;

import java.util.List;

import javax.validation.Valid;

import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;

/**
 * @author BUI_QUANG_HIEU
 */
public interface OptionValueService {
	/**
	 * @param
	 * @return list optionValue
	 */
	public List<OptionValue> findAll();

	/**
	 * @param id
	 * @return optionValue
	 */
	public OptionValue findById(Integer id);

	/**
	 * @param optionValue
	 * @return optionValue
	 */
	public OptionValue create(@Valid OptionValue optionValue);

	/**
	 * @param optionValue
	 * @return optionValue
	 */
	public OptionValue update(@Valid OptionValue optionValue);

	/**
	 * @param value
	 * @return
	 */
	public OptionValue findByName(String valueName);

	public List<OptionValue> findByOption(Option option);

	public List<OptionValue> findOptionValueTrueByOption(Option option);

	/**
	 * @param product
	 * @param option
	 * @return
	 */
	public List<OptionValue> findNotExistsVariantValue(Product product, Option option);

	/**
	 * @param name
	 * @return
	 */
	public List<OptionValue> findByApproximateName(String name);

	public List<OptionValue> fullTextSearch(String input);

	/**
	 * @param optionValue
	 * @return
	 */
	public Boolean checkDeleteOptionValue(OptionValue optionValue);

	/**
	 * @param optionValue
	 * @return
	 */
	public OptionValue deleteOptionValue(OptionValue optionValue);
}
