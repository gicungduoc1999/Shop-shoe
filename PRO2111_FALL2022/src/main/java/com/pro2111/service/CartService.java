package com.pro2111.service;

import java.util.List;

import com.pro2111.entities.Cart;
import com.pro2111.entities.CartDetail;
import com.pro2111.entities.User;

public interface CartService {

	public List<Cart> findByUser(User user);

	public List<Cart> findAll();

	public Cart create(Cart cart);

	/**
	 * 
	 * @param id
	 * @return Cart
	 */
	public Cart findById(Integer id);

	/**
	 * 
	 * @param cart
	 * @return cart
	 */
	public Cart update(Cart cart);

	public Cart delete(Cart product);

}
