/**
 * Luvina Software JSC, 2022
 * OptionServiceIml.java, Bui Quang Hieu
 */
package com.pro2111.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.entities.VariantValue;
import com.pro2111.repositories.OptionRepository;
import com.pro2111.repositories.OptionValueRepository;
import com.pro2111.repositories.ProductOptionRepository;
import com.pro2111.repositories.VariantValueRepository;
import com.pro2111.service.OptionService;

/**
 * Thực thi và xử lý logic các method của OptionService
 * 
 * @author BUI_QUANG_HIEU
 */
@Service
public class OptionServiceImpl implements OptionService {

	@Autowired
	private OptionRepository optionRepository;

	@Autowired
	private ProductOptionRepository productOptionRepository;

	@Autowired
	private OptionValueRepository optionValueRepository;

	@Autowired
	private VariantValueRepository variantValueRepository;

	private int count = 0;

	@Override
	public synchronized List<Option> findAll() {
		return optionRepository.findAll();
	}

	@Override
	public synchronized Option findById(Integer id) {
		return optionRepository.findById(id).get();
	}

	@Override
	public synchronized Option create(@Valid Option option) {
		return optionRepository.save(option);
	}

	@Override
	public synchronized Option update(Option option) {
		// TODO Auto-generated method stub
		return optionRepository.save(option);
	}

	@Override
	public synchronized List<Option> findOptionNotExistsProduct(Product product) {
		return optionRepository.findOptionNotExistsProductOption(product);
	}

	@Override
	public synchronized List<Option> findByStatusLike(int status) {
		return optionRepository.findByStatusLike(status);
	}

	@Override
	public synchronized Option findByName(String name) {
		return optionRepository.findByOptionNameLike(name);
	}

	@Override
	public synchronized List<Option> findByApproximateName(String name) {
		return optionRepository.findByApproximateName(name);
	}

	@Override
	public Boolean checkDeleteOption(Option option) {
		List<ProductOption> values = productOptionRepository.findByOptionsLike(option);
		if (values.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	@Transactional
	public Option deleteOption(Option option) {
		List<OptionValue> optionValues = optionValueRepository.findByOptionsLike(option);
		optionValues.forEach(ov -> {
			optionValueRepository.delete(ov);
		});

		List<ProductOption> productOptions = productOptionRepository.findByOptionsLike(option);
		productOptions.forEach(po -> {
			productOptionRepository.delete(po);
		});
		optionRepository.delete(option);
		return option;
	}

}
