package com.pro2111.service;

import java.util.List;
import java.util.Optional;

import com.pro2111.beans.PvAndImage;
import com.pro2111.entities.Image;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;

public interface ImageService {
	public List<Image> findAll();

	public Image findById(Integer id);

	public Image create(Image images);

	public Image update(Image images);

	public Image delete(Integer id);

	public List<Image> findByProduct(ProductVariant productVariant);

	/**
	 * @param pvAndImage
	 */
	public Image deleteByProductVariant(PvAndImage pvAndImage);

}
