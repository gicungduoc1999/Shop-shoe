package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Cart;
import com.pro2111.entities.CartDetail;
import com.pro2111.entities.ProductVariant;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
	@Query("SELECT cd FROM CartDetail cd WHERE cd.carts = :carts")
	List<CartDetail> findByCart(@Param("carts") Cart cart);

	@Query("SELECT cd FROM CartDetail cd WHERE cd.productVariants = :productVariants")
	List<CartDetail> findByProduct(@Param("productVariants") ProductVariant ProductVariant);	
}
