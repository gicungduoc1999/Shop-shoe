/**
 * Luvina Software JSC, 2022
 * OptionValueServiceIml.java, Bui Quang Hieu
 */
package com.pro2111.serviceimpl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;
import com.pro2111.repositories.OptionValueRepository;
import com.pro2111.service.OptionValueService;

/**
 * @author BUI_QUANG_HIEU
 */
@Service
public class OptionValueServiceImpl implements OptionValueService {
	@Autowired
	OptionValueRepository optionValueRepository;

	@Override
	public synchronized List<OptionValue> findAll() {
		return optionValueRepository.findAll();
	}

	@Override
	public synchronized OptionValue findById(Integer id) {
		return optionValueRepository.findById(id).get();
	}

	@Override
	public synchronized OptionValue create(@Valid OptionValue optionValue) {
		return optionValueRepository.save(optionValue);
	}

	@Override
	public synchronized OptionValue update(@Valid OptionValue optionValue) {
		return optionValueRepository.save(optionValue);
	}

	@Override
	public synchronized OptionValue findByName(String valueName) {
		return optionValueRepository.findByValueNameLike(valueName);
	}

	@Override
	public synchronized List<OptionValue> findByOption(Option option) {
		return optionValueRepository.findByOptionsLike(option);
	}

	@Override
	public synchronized List<OptionValue> findOptionValueTrueByOption(Option option) {
		return optionValueRepository.findByStatusLikeAndOptionsLike(1, option);
	}

	@Override
	public synchronized List<OptionValue> findNotExistsVariantValue(Product product, Option option) {
		return optionValueRepository.findNotExistsVariantValue(product, option);
	}

	@Override
	public synchronized List<OptionValue> findByApproximateName(String name) {
		return optionValueRepository.findByApproximateName(name);
	}

	@Override
	public synchronized List<OptionValue> fullTextSearch(String input) {
		return optionValueRepository.fullTextSearch(input);
	}

	@Override
	public Boolean checkDeleteOptionValue(OptionValue optionValue) {
		if (optionValue.getVariantValues().size() > 0) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public OptionValue deleteOptionValue(OptionValue optionValue) {
		optionValueRepository.delete(optionValue);
		return optionValue;
	}

}
