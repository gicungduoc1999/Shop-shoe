package com.pro2111.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Bill;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
	List<ProductVariant> findByProductsLike(Product product);

	@Query("SELECT pv FROM ProductVariant pv WHERE pv.status = 1 AND EXISTS "
			+ "(SELECT vv FROM VariantValue vv WHERE vv.status = 1 AND pv = vv.productVariants)")
	List<ProductVariant> getProductVariantOfSale();
	
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.products.productName LIKE %:name%")
	List<ProductVariant> findByApproximateProductName(@Param("name") String name);

	/**
	 * @param product
	 * @return
	 */
	@Query("SELECT MIN(pv.price) FROM ProductVariant pv WHERE pv.products = :product")
	BigDecimal findMinPriceByProduct(@Param("product") Product product);
	
	/**
	 * @param product
	 * @return
	 */
	@Query("SELECT MAX(pv.price) FROM ProductVariant pv WHERE pv.products = :product")
	BigDecimal findMaxPriceByProduct(@Param("product") Product product);
	
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.variantId =:id")
	ProductVariant findByIdInHibernate(@Param("id") Long id);
	
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.products in :product AND pv.status = 1 AND pv.isSale = 1")
	List<ProductVariant> findProductVariantsPromotionIsAllowed(@Param("product") List<Product> listProduct);
//	
//	@Query("SELECT pv FROM ProductVariant pv WHERE pv.sales = :sale")
//	List<ProductVariant> findProductVariantBySale(@Param("sale") Sale sale);
//	
//	@Query("SELECT pv FROM ProductVariant pv JOIN Product p ON p = pv.products WHERE pv.products in :products AND pv.status=1 AND pv.isSale=1 AND NOT exists(SELECT 1 FROM Sale s WHERE s = pv.sales) OR exists(SELECT 1 FROM Sale s WHERE s = pv.sales AND s = :sale)")
//	List<ProductVariant> findProductVariantAllowedAndNotBySale(@Param("products") List<Product> listProduct, @Param("sale") Sale sale );
}
