/**
 * DATN_FALL2022, 2022
 * ProductSaleRepository.java, BUI_QUANG_HIEU
 */
package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.ProductSale;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface ProductSaleRepository extends JpaRepository<ProductSale, String> {

	/**
	 * @param sale
	 * @return
	 */
	@Query("SELECT ps.productVariants FROM ProductSale ps WHERE ps.sales =:sale")
	List<ProductVariant> findProductVariantBySale(@Param("sale") Sale sale);

	/**
	 * @param pv
	 * @param saleNew
	 * @return
	 */
	ProductSale findByProductVariantsLikeAndSalesLike(ProductVariant pv, Sale saleNew);

	@Query("SELECT ps.sales FROM ProductSale ps WHERE ps.productVariants =:pv AND ps.sales.status=1")
	List<Sale> findSalesByProductVariant(@Param("pv") ProductVariant pv);
	}
