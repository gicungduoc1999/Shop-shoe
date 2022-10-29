package com.pro2111.restcontrollers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pro2111.beans.PvAndImage;
import com.pro2111.entities.Image;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.service.ImageService;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("admin/rest/image")
public class ImageRestController {

	@Autowired
	ImageService imageService;

	/**
	 * Lấy danh sách image
	 * 
	 * @return list image
	 */
	@GetMapping()
	public ResponseEntity<List<Image>> getAll() {
		// sử dụng phương thức findAll bên service
		//
		return ResponseEntity.ok(imageService.findAll());
	}

	@PostMapping()
	public ResponseEntity<Image> create(@Valid @RequestBody Image image) {
		try {
			image.setStatus(1);
			return ResponseEntity.ok(imageService.create(image));
		} catch (Exception e) {
			return ResponseEntity.ok().build();
		}
	}

	/**
	 * Phương thức này dùng để làm gì?
	 * 
	 * @param id: dùng như thế nào ?
	 * @return nếu tìm thấy thì trả về 1 đối tượng image, còn không thì rỗng
	 */
	@GetMapping("{id}")
	public ResponseEntity<Image> getOne(@PathVariable("id") Integer id) {
		try {
			return ResponseEntity.ok(imageService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.ok().build();
		}

	}

	@PutMapping("{id}")
	public ResponseEntity<Image> update(@PathVariable("id") Integer id, @Valid @RequestBody Image image) {
		try {
			image.setImagesId(id);
			return ResponseEntity.ok(imageService.create(image));
		} catch (Exception e) {
			return ResponseEntity.ok().build();
		}
	}

	@GetMapping("find-by-product/{idProduct}")
	public ResponseEntity<List<Image>> findByProduct(@PathVariable("idProduct") ProductVariant productVariant) {
		try {
			return ResponseEntity.ok(imageService.findByProduct(productVariant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Image> delete(@PathVariable("id") Integer id) {
		try {
			return ResponseEntity.ok(imageService.delete(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping("delete-by-product-variant")
	public ResponseEntity<Image> deleteByProductVariant(@PathVariable PvAndImage pvAndImage) {
		try {
			return ResponseEntity.ok(imageService.deleteByProductVariant(pvAndImage));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
