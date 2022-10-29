package com.pro2111.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.beans.PvAndImage;
import com.pro2111.entities.Image;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.repositories.ImageRepository;
import com.pro2111.service.ImageService;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
	@Autowired
	ImageRepository imageRepository;

	@Override
	public synchronized List<Image> findAll() {
		return imageRepository.findAll();
	}

	@Override
	public synchronized Image findById(Integer id) {
		return imageRepository.findById(id).get();
	}

	@Override
	public synchronized Image create(Image images) {
		return imageRepository.save(images);
	}

	@Override
	public synchronized Image update(Image images) {
		return imageRepository.save(images);
	}

	@Override
	public synchronized Image delete(Integer id) {
		Image image = imageRepository.findById(id).get();
		imageRepository.delete(image);
		return image;
	}

	@Override
	public synchronized List<Image> findByProduct(ProductVariant productVariant) {
		return imageRepository.findByProduct(productVariant);
	}

	@Override
	public synchronized Image deleteByProductVariant(PvAndImage pvAndImage) {
		Image image = imageRepository.findByProductVariantsLikeAndImagePathLike(pvAndImage.getProductVariant(),
				pvAndImage.getImagePath());
		imageRepository.delete(image);
		return image;
	}

}
