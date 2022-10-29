package com.pro2111.restcontrollers.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.entities.Cart;
import com.pro2111.entities.CartDetail;
import com.pro2111.entities.ProductVariant;
import com.pro2111.repositories.CartDetailRepository;
import com.pro2111.service.CartDetailService;

@CrossOrigin("*")
@RestController
@RequestMapping("user/rest/cartdetail")
public class CartDetailRestController {

	@Autowired
	private CartDetailService cartDetailService;

	@PostMapping()
	public ResponseEntity<CartDetail> create(@RequestBody CartDetail cartDetails) {
		try {
			return ResponseEntity.ok(cartDetailService.create(cartDetails));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<CartDetail> update(@PathVariable("id") CartDetail detailOld, @RequestBody CartDetail cartDetails) {
		try {
			cartDetails.setCartDetailId(detailOld.getCartDetailId());
			return ResponseEntity.ok(cartDetailService.update(cartDetails));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	
	}

	@DeleteMapping("{id}")
	public ResponseEntity<CartDetail> delete(@PathVariable("id") CartDetail cartDetails) {
		try {
			return ResponseEntity.ok(cartDetailService.delete(cartDetails));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping()
	public ResponseEntity<List<CartDetail>> findAll() {
		try {
			return ResponseEntity.ok(cartDetailService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<CartDetail> findById(@PathVariable("id") Integer id) {
		try {
			return ResponseEntity.ok(cartDetailService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-cart/{idCart}")
	public ResponseEntity<List<CartDetail>> findByCart(@PathVariable("idCart") Cart cart) {
		try {
			return ResponseEntity.ok(cartDetailService.findByCart(cart));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-productvariant/{idValue}")
	public ResponseEntity<List<CartDetail>> findByVarian(@PathVariable("idValue") ProductVariant productVariant) {
		try {
			return ResponseEntity.ok(cartDetailService.findByProduct(productVariant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}