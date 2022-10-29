/**
 * Luvina Software JSC, 2022
 * ProductServiceIml.java, Bui Quang Hieu
 */
package com.pro2111.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.entities.Option;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.repositories.ProductOptionRepository;
import com.pro2111.service.ProductOptionService;

/**
 * @author BUI_QUANG_HIEU
 */

@Service
public class ProductOptionServiceImpl implements ProductOptionService {

	@Autowired
	private ProductOptionRepository productOptionRepository;

	/**
	 * Description của method là làm gì
	 * 
	 * @param [name] [giải thích]: mô tả ý nghĩa và giải thích cách dùng
	 * @return [giải thích]: Mô tả giá trị và về của nó
	 */
	@Override
	public synchronized List<ProductOption> findAll() {
		return productOptionRepository.findAll();
	}

	/**
	 * Description của method là làm gì
	 * 
	 * @param [name] [giải thích]: mô tả ý nghĩa và giải thích cách dùng
	 * @return [giải thích]: Mô tả giá trị và về của nó
	 */
	@Override
	public synchronized List<ProductOption> findByProduct(Product product) {
		return productOptionRepository.findByProductsLike(product);
	}

	/**
	 * Description của method là làm gì
	 * 
	 * @param [name] [giải thích]: mô tả ý nghĩa và giải thích cách dùng
	 * @return [giải thích]: Mô tả giá trị và về của nó
	 */
	@Override
	public synchronized void create(Product product, Option option, Integer status) {
		productOptionRepository.insertPO(product, option, status);
	}

	@Override
	public synchronized void update(Product product, Option option, Option optionOld, Integer status) {
		productOptionRepository.updatePO(product, option, optionOld, status);
	}

	@Override
	public synchronized void updateStatus(Product products, Option options, Integer status) {
		productOptionRepository.updateStatusPO(products, options, status);
	}

	@Override
	public synchronized List<ProductOption> findByProductAndStatusTrue(Product product) {
		return productOptionRepository.findByProductsLikeAndStatusLike(product, 1);
	}

}
