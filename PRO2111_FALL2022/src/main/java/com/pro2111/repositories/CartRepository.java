package com.pro2111.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Cart;
import com.pro2111.entities.User;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	@Query("SELECT c FROM Cart c WHERE c.users = :user")
	List<Cart> findByUser(@Param("user") User user);

}
