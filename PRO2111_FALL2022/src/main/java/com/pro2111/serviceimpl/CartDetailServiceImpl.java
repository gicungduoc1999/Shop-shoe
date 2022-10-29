package com.pro2111.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.entities.Cart;
import com.pro2111.entities.CartDetail;
import com.pro2111.entities.ProductVariant;
import com.pro2111.repositories.CartDetailRepository;
import com.pro2111.service.CartDetailService;

@Service
public class CartDetailServiceImpl implements CartDetailService {
	@Autowired
	CartDetailRepository cartDetailRepository;

	@Override
	public CartDetail create(CartDetail cartDetails) {
		cartDetails.setCreatedDate(LocalDateTime.now());
		return cartDetailRepository.save(cartDetails);
	}

	@Override
	public synchronized CartDetail update(CartDetail cartDetails) {
		return cartDetailRepository.save(cartDetails);
	}

	@Override
	public synchronized CartDetail delete(CartDetail cartDetails) {
		cartDetailRepository.delete(cartDetails);
		return cartDetails;
	}

	@Override
	public synchronized List<CartDetail> findAll() {
		return cartDetailRepository.findAll();
	}

	@Override
	public synchronized CartDetail findById(Integer id) {
		return cartDetailRepository.findById(id).get();
	}

	@Override
	public synchronized List<CartDetail> findByCart(Cart cart) {
		return cartDetailRepository.findByCart(cart);
	}

	@Override
	public synchronized List<CartDetail> findByProduct(ProductVariant productVariant) {
		return cartDetailRepository.findByProduct(productVariant);
	}

}
