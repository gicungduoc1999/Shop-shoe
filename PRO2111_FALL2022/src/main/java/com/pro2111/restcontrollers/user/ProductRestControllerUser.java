/**
 * DATN_FALL2022, 2022
 * ProductRestControllerUser.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.entities.Product;
import com.pro2111.service.ProductService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("user/rest/products")
public class ProductRestControllerUser {
	@Autowired
	private ProductService productService;

	@GetMapping("find-by-status-true")
	public ResponseEntity<List<Product>> findByStatusTrue() {
		try {
			return ResponseEntity.ok(productService.findByStatusLike(1));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-id/{idProduct}")
	public ResponseEntity<Product> findById(@PathVariable("idProduct") Integer id) {
		try {
			return ResponseEntity.ok(productService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
