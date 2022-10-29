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

import com.pro2111.beans.ProAndOpAndOv;
import com.pro2111.beans.PvAndOv;
import com.pro2111.beans.PvAndVv;
import com.pro2111.beans.SaleAndProduct;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.service.ProductVariantService;

@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/product-variants")
public class ProductVariantRestControllerAdmin {
	@Autowired
	private ProductVariantService productVariantService;

	@GetMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<ProductVariant>> getAll() {
		try {
			return ResponseEntity.ok(productVariantService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<ProductVariant> findById(@PathVariable("id") Long id) {
		try {
			return ResponseEntity.ok(productVariantService.findById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-product/{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<ProductVariant>> findByProduct(@PathVariable("id") Product product) {
		try {
			return ResponseEntity.ok(productVariantService.findByProduct(product));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("get-product-variant-of-sale")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<ProductVariant>> getProductVariantOfSale() {
		try {
			return ResponseEntity.ok(productVariantService.getProductVariantOfSale());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("dynamic-search-by-key/{inputString}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<ProductVariant>> searchByKey(@PathVariable("inputString") String inputString) {
		try {
			return ResponseEntity.ok(productVariantService.dynamicSearchByKey(inputString));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-approximate-product-name/{name}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<ProductVariant>> findByApproximateProductName(@PathVariable("name") String name) {
		try {
			return ResponseEntity.ok(productVariantService.findByApproximateProductName(name));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<ProductVariant> create(@Valid @RequestBody ProductVariant productVariant) {
		try {
			return ResponseEntity.ok(productVariantService.create(productVariant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping("createv2")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<ProductVariant>> createV2(@Valid @RequestBody List<PvAndOv> pvAndOvs) {
		try {
			return ResponseEntity.ok(productVariantService.createV2(pvAndOvs));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("savePvAndVV")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<ProductVariant> createPvAndVv(@Valid @RequestBody PvAndVv pvAndVv) {
		try {
			return ResponseEntity.ok(productVariantService
					.createProductVariantAndVariantValue(pvAndVv.getProductVariant(), pvAndVv.getVariantValues()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("{variantId}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<ProductVariant> update(@PathVariable("variantId") Long variantId,
			@Valid @RequestBody ProductVariant productVariant) {
		try {
			ProductVariant pvOld = productVariantService.findById(variantId);

			pvOld.setSkuId(productVariant.getSkuId());
			pvOld.setStatus(productVariant.getStatus());
			pvOld.setProducts(productVariant.getProducts());
			pvOld.setPrice(productVariant.getPrice());
			pvOld.setQuantity(productVariant.getQuantity());
			pvOld.setIsSale(productVariant.getIsSale());
			pvOld.setImportPrice(productVariant.getImportPrice());
			return ResponseEntity.ok(productVariantService.update(pvOld));

		} catch (Exception e) {
			e.printStackTrace();
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
	
//	@PostMapping("findProductVariantAllowedAndNotBySale")
//	@PreAuthorize("@appAuthorizer.authorize(authentication)")
//	public ResponseEntity<List<ProductVariant>>findProductVariantAllowedAndNotBySale(@RequestBody SaleAndProduct saleAndProduct){
//		try {
//			return ResponseEntity.ok(productVariantService.findProductVariantAllowedAndNotBySale(saleAndProduct));
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		}
//	}
}
