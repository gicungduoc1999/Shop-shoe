/**
 * Luvina Software JSC, 2022
 * ProductOptionRestControllerAdmin.java, Bui Quang Hieu
 */
package com.pro2111.restcontrollers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.entities.Option;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.service.ProductOptionService;

/**
 * @author BUI_QUANG_HIEU
 */
@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/product-options")
public class ProductOptionRestControllerAdmin {

	@Autowired
	private ProductOptionService productOptionService;

	@GetMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<ProductOption>> getAll() {
		try {
			return ResponseEntity.ok(productOptionService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-product/{idProduct}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<ProductOption>> findByProduct(@PathVariable("idProduct") Product product) {
		try {
			return ResponseEntity.ok(productOptionService.findByProduct(product));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping("find-by-product-and-status-true/{idProduct}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<ProductOption>> findByProductAndStatusTrue(@PathVariable("idProduct") Product product) {
		try {
			return ResponseEntity.ok(productOptionService.findByProductAndStatusTrue(product));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<ProductOption> create(@Valid @RequestBody ProductOption productOption) {
		try {
			productOptionService.create(productOption.getProducts(), productOption.getOptions(),
					productOption.getStatus());
			return ResponseEntity.ok(productOption);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("{idOptionOld}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<ProductOption> update(@PathVariable("idOptionOld") Option optionOld,
			@Valid @RequestBody ProductOption productOption) {
		try {
			productOptionService.update(productOption.getProducts(), productOption.getOptions(), optionOld,
					productOption.getStatus());
			return ResponseEntity.ok(productOption);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("update-status")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<ProductOption> updateStatus(@Valid @RequestBody ProductOption productOption) {
		try {
			productOptionService.updateStatus(productOption.getProducts(), productOption.getOptions(),
					productOption.getStatus());
			return ResponseEntity.ok(productOption);
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
