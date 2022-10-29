package com.pro2111.service;

import java.util.List;

import com.pro2111.entities.Cart;
import com.pro2111.entities.CartDetail;
import com.pro2111.entities.ProductVariant;

public interface CartDetailService {

	public CartDetail create(CartDetail cartDetails);

	/**
	 * 
	 * @param cartDetails
	 * @return cartDetails
	 */
	public CartDetail update(CartDetail cartDetails);

	public CartDetail delete(CartDetail cartDetails);

	public List<CartDetail> findAll();

	/**
	 * 
	 * @param id
	 * @return cartDetails
	 */

	public CartDetail findById(Integer id);

	/**
	 * 
	 * @param cart
	 * @return CartDetails
	 */
	public List<CartDetail> findByCart(Cart cart);

	/**
	 * 
	 * @param value
	 * @return CartDetails
	 */
	public List<CartDetail> findByProduct(ProductVariant productVariant);

}
