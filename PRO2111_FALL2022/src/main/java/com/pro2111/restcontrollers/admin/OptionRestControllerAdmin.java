/**
 * Luvina Software JSC, 2022
 * OptionRestControllerAdmin.java, Bui Quang Hieu
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pro2111.entities.Option;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.service.OptionService;

/**
 * @author BUI_QUANG_HIEU
 */
@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/options")
public class OptionRestControllerAdmin {
	@Autowired
	private OptionService optionService;

	static Option optionStatic;

	@GetMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Option>> findAll() {
		try {
			return ResponseEntity.ok(optionService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("by-status-true")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Option>> findByStatusTrue() {
		try {
			return ResponseEntity.ok(optionService.findByStatusLike(1));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<Option> findById(@PathVariable("id") Integer id) {
		try {
			optionStatic = optionService.findById(id);
			return ResponseEntity.ok(optionService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-approximate-name/{name}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Option>> findByApproximateName(@PathVariable("name") String name) {
		try {
			return ResponseEntity.ok(optionService.findByApproximateName(name));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-option-not-exists-product/{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Option>> findOptionNotExistsProduct(@PathVariable("id") Product product) {
		try {
			return ResponseEntity.ok(optionService.findOptionNotExistsProduct(product));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<Option> create(@Valid @RequestBody Option option) {
		try {
			return ResponseEntity.ok(optionService.create(option));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<Option> update(@PathVariable("id") Integer id, @Valid @RequestBody Option option) {
		try {
			option.setOptionId(id);
			return ResponseEntity.ok(optionService.create(option));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping("check-delete-option/{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<JsonNode> checkDeleteOption(@PathVariable("id") Option option){
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			Boolean checkDelete = optionService.checkDeleteOption(option);
			node.put("value", checkDelete);
			return ResponseEntity.ok(node);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<Option> deleteOption(@PathVariable("id") Option option){
		try {
			return ResponseEntity.ok(optionService.deleteOption(option));
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

	public static Option getOptionStatic() {
		return optionStatic;
	}
}
