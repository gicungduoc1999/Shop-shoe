/**
 * Luvina Software JSC, 2022
 * ProductService.java, Bui Quang Hieu
 */
package com.pro2111.service;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.JsonNode;
import com.pro2111.beans.ProAndOpAndOv;
import com.pro2111.beans.PvAndOv;
import com.pro2111.beans.PvAndVv;
import com.pro2111.beans.SaleAndProduct;
import com.pro2111.beans.SaleAndProductVariants;
import com.pro2111.entities.BillDetail;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;
import com.pro2111.entities.VariantValue;

/**
 * @author BUI_QUANG_HIEU
 */
public interface ProductVariantService {
	
	public List<ProductVariant> findAll();

	/**
	 * @param id
	 * @return ProductVariant
	 */
	public ProductVariant findById(Long id);

	/**
	 * @param productVariant
	 * @return ProductVariant
	 */
	public ProductVariant create(ProductVariant productVariant);

	/**
	 * @param productVariant
	 * @return ProductVariant
	 */
	public ProductVariant update(ProductVariant productVariant);

	public List<ProductVariant> findByProduct(Product product);

	public List<ProductVariant> getProductVariantOfSale();
	
	public ProductVariant createProductVariantAndVariantValue(ProductVariant productVariant, List<VariantValue> variantValue);

	/**
	 * @param name
	 * @return List<ProductVariant>
	 */
	public List<ProductVariant> findByApproximateProductName(String name);

	/**
	 * @param inputString
	 * @return List<ProductVariant>
	 */
	List<ProductVariant> dynamicSearchByKey(String inputString);

	/**
	 * @param proAndOpAndOv
	 * @return
	 */
	public ProductVariant findByProductAndOptionAndOptionValue(ProAndOpAndOv proAndOpAndOv);
	

	public BigDecimal findMinPriceByProduct(Product product);
	
	public BigDecimal findMaxPriceByProduct(Product product);

	/**
	 * @param pvAndVvs
	 * @return
	 */
	public List<ProductVariant> createV2(List<PvAndOv> pvAndOvs);

	/**
	 * @param productVariant
	 * @return
	 */
	
//	public List<ProductVariant> findProductVariantAllowedAndNotBySale(SaleAndProduct saleAndProduct);
	
}
