/**
 * Luvina Software JSC, 2022
 * ProductServiceIml.java, Bui Quang Hieu
 */
package com.pro2111.serviceimpl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.entities.Product;
import com.pro2111.repositories.ProductOptionRepository;
import com.pro2111.repositories.ProductRepository;
import com.pro2111.service.ProductService;

/**
 * @author BUI_QUANG_HIEU
 */

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductOptionRepository productOptionRepository;

	/**
	 * Description của method là làm gì
	 * 
	 * @param [name] [giải thích]: mô tả ý nghĩa và giải thích cách dùng
	 * @return [giải thích]: Mô tả giá trị và về của nó
	 */
	@Override
	public synchronized List<Product> findAll() {
		return productRepository.findAll();
	}

	/**
	 * Description của method là làm gì
	 * 
	 * @param [name] [giải thích]: mô tả ý nghĩa và giải thích cách dùng
	 * @return [giải thích]: Mô tả giá trị và về của nó
	 */
	@Override
	public synchronized Product create(Product product) {
		return productRepository.save(product);
	}

	/**
	 * Description của method là làm gì
	 * 
	 * @param [name] [giải thích]: mô tả ý nghĩa và giải thích cách dùng
	 * @return [giải thích]: Mô tả giá trị và về của nó
	 */
	@Override
	public synchronized Product findById(Integer id) {
		return productRepository.findById(id).get();
	}

	/**
	 * Description của method là làm gì
	 * 
	 * @param [name] [giải thích]: mô tả ý nghĩa và giải thích cách dùng
	 * @return [giải thích]: Mô tả giá trị và về của nó
	 */
	@Override
	public synchronized Product update(@Valid Product product) {
		return productRepository.save(product);
	}

	/**
	 * Description của method là làm gì
	 * 
	 * @param [name] [giải thích]: mô tả ý nghĩa và giải thích cách dùng
	 * @return [giải thích]: Mô tả giá trị và về của nó
	 */
	@Override
	public synchronized Product findByName(String name) {
		// TODO Auto-generated method stub
		return productRepository.findByProductNameLike(name);
	}

	/**
	 * Description của method là làm gì
	 * 
	 * @param [name] [giải thích]: mô tả ý nghĩa và giải thích cách dùng
	 * @return [giải thích]: Mô tả giá trị và về của nó
	 */
	@Override
	public synchronized List<Product> findByStatusLike(int status) {
		return productRepository.findByStatusLike(status);
	}

	/**
	 * Description của method là làm gì
	 * 
	 * @param [name] [giải thích]: mô tả ý nghĩa và giải thích cách dùng
	 * @return [giải thích]: Mô tả giá trị và về của nó
	 */
	@Override
	public synchronized List<Product> findByApproximateName(String name) {
		return productRepository.findByApproximateName(name);
	}

	@Override
	public synchronized Product delete(Product product) {
		productOptionRepository.deletePOByProduct(product);
		productRepository.delete(product);
		return product;
	}

//	@Override
//	public List<Product> findByAllName(String name) {
//		return productRepository.findByAllName(name);
//	}

}
