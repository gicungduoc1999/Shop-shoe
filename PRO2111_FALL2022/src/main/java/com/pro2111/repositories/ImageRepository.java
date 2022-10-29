package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Image;

import com.pro2111.entities.ProductVariant;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	@Query("SELECT i FROM Image i WHERE i.productVariants = :productVariant")
	List<Image> findByProduct(@Param("productVariant") ProductVariant productVariant);

	/**
	 * @param productVariant
	 * @param imagePath
	 * @return
	 */
	Image findByProductVariantsLikeAndImagePathLike(ProductVariant productVariant, String imagePath);

}
