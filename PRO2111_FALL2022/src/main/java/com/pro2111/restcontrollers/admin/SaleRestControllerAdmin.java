package com.pro2111.restcontrollers.admin;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections4.MapUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
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
import com.pro2111.beans.SaleAndProductVariants;
import com.pro2111.beans.SaleAndSaleChild;
import com.pro2111.beans.VariantValueViewSaleBean;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;
import com.pro2111.entities.VariantValue;
import com.pro2111.service.ProductVariantService;
import com.pro2111.service.SaleService;
import com.pro2111.utils.Common;

import net.bytebuddy.description.ModifierReviewable.OfAbstraction;

@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/sales")
public class SaleRestControllerAdmin {

	@Autowired
	private SaleService saleService;
	@Autowired
	HttpServletRequest request;

	static final int MINUTES_PER_HOUR = 60;
	static final int SECONDS_PER_MINUTES = 60;
	static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTES * MINUTES_PER_HOUR;

	@GetMapping
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Sale>> finAll() {
		try {
			return ResponseEntity.ok(saleService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("{idSale}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<SaleAndProductVariants> findById(@PathVariable("idSale") String id) {
		try {

			return ResponseEntity.ok(saleService.findSaleAndProductById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<?> createSaleAndProduct(@Valid @RequestBody SaleAndProductVariants saleAndProduct) {
		try {
			Map<String, String> errorsMap = new HashMap<>();
			// Validate saleName
			if (saleAndProduct.getSale().getSaleName().isBlank()) {
				errorsMap.put("saleName", "T??n ch????ng tr??nh khuy???n m??i kh??ng ???????c ????? tr???ng !");
			} else {
				if (!saleService.checkSaleName(saleAndProduct.getSale(), null, request.getMethod())) {
					errorsMap.put("saleName", "T??n ch????ng tr??nh khuy???n m??i ???? t???n t???i");
				}
			}
			// Validate discount
			if (saleAndProduct.getSale().getDiscount() == null) {
				errorsMap.put("discount", "Discount kh??ng ???????c ????? tr???ng !");
			} else if (!Common.checkBigDecimal(saleAndProduct.getSale().getDiscount().toString())) {
				errorsMap.put("discount", "Discount ph???i l?? s??? !");
			} else if (saleAndProduct.getSale().getDiscountType() == 0
					&& saleAndProduct.getSale().getDiscount().doubleValue() < 10000) {
				errorsMap.put("discount", "Discount ph???i l???n h??n 10,000 !");

			} else if (saleAndProduct.getSale().getDiscountType() == 1
					&& ((saleAndProduct.getSale().getDiscount().doubleValue() > 100
							|| saleAndProduct.getSale().getDiscount().doubleValue() <= 0))) {
				errorsMap.put("discount", "Discount ph???i l???n h??n 0 nh??? h??n 100 !");
			}

			// Validate startDate
			if (saleAndProduct.getSale().getStartDate() == null) {
				errorsMap.put("startDate", "Ng??y b???t ?????u kh??ng ???????c ????? tr???ng !");
			} else {
				if (!Common.checkStartDateSale(saleAndProduct.getSale().getStartDate())) {
					errorsMap.put("startDate", "Ng??y gi??? b???t ?????u ph???i l???n h??n ng??y gi??? hi???n t???i !");
				}
			}

			// Validate endDate
			if (saleAndProduct.getSale().getEndDate() == null) {
				errorsMap.put("endDate", "Ng??y k???t th??c kh??ng ???????c ????? tr???ng !");
			} else if (saleAndProduct.getSale().getStartDate() != null) {
				if (!Common.checkEndDateSale(saleAndProduct.getSale().getStartDate(),
						saleAndProduct.getSale().getEndDate())) {
					errorsMap.put("endDate", "Ng??y gi??? k???t th??c ph???i l???n h??n ng??y gi??? b???t ?????u 5' !");
				}
			}

			// Validate listProductVariant
			if (saleAndProduct.getListProductVariants() == null
					|| saleAndProduct.getListProductVariants().size() == 0) {
				errorsMap.put("listProductVariants", "B???n ch??a ch???n s???n ph???m khuy???n m??i !");
			}

			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				return ResponseEntity.ok().body(saleService.create(saleAndProduct));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	@PutMapping("{idSale}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<?> updateSaleAndProduct(@Valid @RequestBody SaleAndProductVariants saleAndProduct,
			@PathVariable("idSale") String id) {
		try {
			Sale saleOld = saleService.findSaleById(id);
			Map<String, String> errorsMap = new HashMap<>();
			// Validate saleName
			if (saleAndProduct.getSale().getSaleName().isBlank()) {
				errorsMap.put("saleName", "T??n ch????ng tr??nh khuy???n m??i kh??ng ???????c ????? tr???ng !");
			} else {
				if (!saleService.checkSaleName(saleAndProduct.getSale(), saleOld, request.getMethod())) {
					errorsMap.put("saleName", "T??n ch????ng tr??nh khuy???n m??i ???? t???n t???i !");
				}
			}
			// Validate discount
			if (saleAndProduct.getSale().getDiscount() == null) {
				errorsMap.put("discount", "Discount kh??ng ???????c ????? tr???ng !");
			} else if (!Common.checkBigDecimal(saleAndProduct.getSale().getDiscount().toString())) {
				errorsMap.put("discount", "Discount ph???i l?? s??? !");
			} else if (saleAndProduct.getSale().getDiscountType() == 0
					&& saleAndProduct.getSale().getDiscount().doubleValue() < 10000) {
				errorsMap.put("discount", "Discount ph???i l???n h??n 10,000 !");

			} else if (saleAndProduct.getSale().getDiscountType() == 1
					&& ((saleAndProduct.getSale().getDiscount().doubleValue() > 100
							|| saleAndProduct.getSale().getDiscount().doubleValue() <= 0))) {
				errorsMap.put("discount", "Discount ph???i l???n h??n 0 nh??? h??n 100 !");
			}

			// Validate startDate
			if (saleAndProduct.getSale().getStartDate() == null) {
				errorsMap.put("startDate", "Ng??y b???t ?????u kh??ng ???????c ????? tr???ng !");
			} else {
				if (!saleOld.getStartDate().equals(saleAndProduct.getSale().getStartDate())) {
					if (!Common.checkStartDateSale(saleAndProduct.getSale().getStartDate())) {
						errorsMap.put("startDate", "Ng??y gi??? b???t ?????u ph???i l???n h??n ng??y gi??? hi???n t???i !");
					}
				}
			}

			// Validate endDate
			if (saleAndProduct.getSale().getEndDate() == null) {
				errorsMap.put("endDate", "Ng??y k???t th??c kh??ng ???????c ????? tr???ng !");
			} else if (saleAndProduct.getSale().getStartDate() != null) {
				if (!saleOld.getEndDate().equals(saleAndProduct.getSale().getEndDate())
						&& !saleOld.getStartDate().equals(saleAndProduct.getSale().getStartDate())) {
					if (!Common.checkEndDateSale(saleAndProduct.getSale().getStartDate(),
							saleAndProduct.getSale().getEndDate())) {
						errorsMap.put("endDate", "Ng??y gi??? k???t th??c ph???i l???n h??n ng??y gi??? b???t ?????u 5' !");
					}
				}
			}

			// Validate listProductVariant
			if (saleAndProduct.getListProductVariants() == null
					|| saleAndProduct.getListProductVariants().size() == 0) {
				errorsMap.put("listProductVariants", "B???n ch??a ch???n s???n ph???m khuy???n m??i");
			}

			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				return ResponseEntity.ok().body(saleService.update(saleAndProduct));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("find-product-variants-promotion-is-allowed")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<ProductVariant>> findProductVariantsPromotionIsAllowed(
			@RequestBody List<Product> listProduct) {
		try {
			return ResponseEntity.ok(saleService.findProductVariantsPromotionIsAllowed(listProduct));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-discount-sale-by-product-variant/{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<JsonNode> findDiscountSaleByProductVariant(
			@PathVariable("id") ProductVariant productVariant) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			node.put("discount", saleService.findDiscountSaleByProductVariant(productVariant));
			return ResponseEntity.ok(node);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-variant-value-by-product-variant/{idProductVariant}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<VariantValueViewSaleBean> findByProductVariant(
			@PathVariable("idProductVariant") ProductVariant productVariant) {
		try {
			return ResponseEntity.ok(saleService.findVariantValueByProductVariant(productVariant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("saleparent-and-salechild")
	public ResponseEntity<?> createSaleAndSaleChild(@RequestBody SaleAndSaleChild saleAndSaleChild) {
		try {
			Map<String, String> errorsMap = new HashMap<>();
			// Validate name
			if (saleAndSaleChild.getSaleParent().getSaleName().isBlank()) {
				errorsMap.put("saleName", "T??n ch????ng tr??nh khuy???n m??i kh??ng ???????c ????? tr???ng !");
			} else {
				if (!saleService.checkSaleName(saleAndSaleChild.getSaleParent(), null, request.getMethod())) {
					errorsMap.put("saleName", "T??n ch????ng tr??nh khuy???n m??i ???? t???n t???i");
				}
			}
			// Validate discount
			if (saleAndSaleChild.getSaleParent().getDiscount() == null) {
				errorsMap.put("discount", "Discount kh??ng ???????c ????? tr???ng !");
			} else if (!Common.checkBigDecimal(saleAndSaleChild.getSaleParent().getDiscount().toString())) {
				errorsMap.put("discount", "Discount ph???i l?? s??? !");
			} else if (saleAndSaleChild.getSaleParent().getDiscountType() == 0
					&& saleAndSaleChild.getSaleParent().getDiscount().doubleValue() < 10000) {
				errorsMap.put("discount", "Discount ph???i l???n h??n 10,000 !");

			} else if (saleAndSaleChild.getSaleParent().getDiscountType() == 1
					&& ((saleAndSaleChild.getSaleParent().getDiscount().doubleValue() > 100
							|| saleAndSaleChild.getSaleParent().getDiscount().doubleValue() <= 0))) {
				errorsMap.put("discount", "Discount ph???i l???n h??n 0 nh??? h??n 100 !");
			}

			// Validate SaleType = 1
			if (saleAndSaleChild.getSaleParent().getSaleType() == 1) {
				if (saleAndSaleChild.getSaleParent().getStartDate() == null) {
					errorsMap.put("startDate", "Ng??y b???t ?????u kh??ng ???????c ????? tr???ng !");
				} else {
					if (!Common.checkStartDateSale(saleAndSaleChild.getSaleParent().getStartDate())) {
						errorsMap.put("startDate", "Ng??y gi??? b???t ?????u ph???i l???n h??n ng??y gi??? hi???n t???i !");
					}
				}

				// Validate endDate
				if (saleAndSaleChild.getSaleParent().getEndDate() == null) {
					errorsMap.put("endDate", "Ng??y k???t th??c kh??ng ???????c ????? tr???ng !");
				} else if (saleAndSaleChild.getSaleParent().getStartDate() != null) {
					if (!Common.checkEndDateSale(saleAndSaleChild.getSaleParent().getStartDate(),
							saleAndSaleChild.getSaleParent().getEndDate())) {
						errorsMap.put("endDate", "Ng??y gi??? k???t th??c ph???i l???n h??n ng??y gi??? b???t ?????u 5' !");
					}
				}

			}
			// Validate SaleType = 2
			else if (saleAndSaleChild.getSaleParent().getSaleType() == 2) {
				if (saleAndSaleChild.getLstSaleChild().size() == 0 || saleAndSaleChild.getLstSaleChild() == null) {
					errorsMap.put("lstSaleChild", "B???n ch??a ch???n th??? khuy???n m??i !");
				} else if (saleAndSaleChild.getLstSaleChild().size() > 5) {
					errorsMap.put("lstSaleChild", "Danh s??ch th??? khuy???n m??i nh??? h??n b???ng 5 !");
				}
				List<Sale> lstSaleChildList = saleAndSaleChild.getLstSaleChild();
				for (int i = 0; i < lstSaleChildList.size(); i++) {
					// Validate weekDay
					if (lstSaleChildList.get(i).getWeekday() == 0) {
						errorsMap.put("weekDay", "Th??? kh??ng ???????c ????? tr???ng !");
					} else if (lstSaleChildList.get(i).getWeekday() < 1 || lstSaleChildList.get(i).getWeekday() > 8) {
						errorsMap.put("weekDay", "Th??? ph???i n???m trong kho???ng t??? th??? 2 ?????n ch??? nh???t !");
					}
					// validate startDate and EndDate
					else if (lstSaleChildList.get(i).getStartDate() == null) {
						if (lstSaleChildList.get(i).getEndDate() != null) {
							errorsMap.put("startDate", "Ng??y b???t ?????u kh??ng ???????c ????? tr???ng !");
						} else {
							if (lstSaleChildList.get(i).getEndDate() == null) {
								errorsMap.put("endDate", "Ng??y k???t th??c kh??ng ???????c ????? tr???ng !");
							} else {
								if (!Common.checkEndDateSaleChild(lstSaleChildList.get(i).getStartDate(),
										lstSaleChildList.get(i).getEndDate())) {
									errorsMap.put("endDate",
											"Ng??y gi??? k???t th??c ph???i l???n h??n ng??y gi??? b???t ?????u 7 ng??y !");
								}
							}
						}
					}
					// Validate StartAt and EndAt
					else if (lstSaleChildList.get(i).getStartAt() == null) {
						if (lstSaleChildList.get(i).getEndAt() != null) {
							errorsMap.put("startAt", "Gi??? b???t ?????u kh??ng ???????c ????? tr???ng !");
						}
					} else {
						if (lstSaleChildList.get(i).getEndAt() == null) {
							errorsMap.put("endAt", "Gi??? k???t th??c ?????u kh??ng ???????c ????? tr???ng !");
						} else {
							if (!Common.checkEndAtSaleChild(lstSaleChildList.get(i).getStartAt(),
									lstSaleChildList.get(i).getEndAt())) {
								errorsMap.put("endAt", "Gi??? k???t th??c ph???i l???n h??n ng??y gi??? b???t ?????u 5 ph??t !");
							}
						}
					}
					// Validte tr??ng khung gi???
					for (int j = i + 1; i < lstSaleChildList.size(); j++) {
						if (lstSaleChildList.get(i).getWeekday() == lstSaleChildList.get(j).getWeekday()) {
							if (lstSaleChildList.get(i).getStartAt().isBefore(lstSaleChildList.get(j).getEndAt())
									&& lstSaleChildList.get(i).getEndAt()
											.isAfter(lstSaleChildList.get(j).getStartAt())) {
								errorsMap.put("weekDay", "Th??? ???????c ch???n b??? tr??ng khung gi??? !");
							}
						}
					}
				}
			}

			// Validate listProductVariant
			if (saleAndSaleChild.getLstProductVariants() == null
					|| saleAndSaleChild.getLstProductVariants().size() == 0) {
				errorsMap.put("listProductVariants", "B???n ch??a ch???n s???n ph???m khuy???n m??i !");
			}
			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				return ResponseEntity.ok().body(saleService.createAndSaleChild(saleAndSaleChild));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("saleparent-and-salechild")
	public ResponseEntity<List<Sale>> findAllSaleParent() {
		try {
			return ResponseEntity.ok().body(saleService.findSaleParent());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("saleparent-and-salechild/{idSale}")
	public ResponseEntity<SaleAndSaleChild> getSaleAndSaleChild(@PathVariable("idSale") String id) {
		try {
			return ResponseEntity.ok().body(saleService.findSaleAndSaleChildBySaleParent(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("saleparent-and-salechild/{idSale}")
	public ResponseEntity<?> updateSaleAndChild(@RequestBody SaleAndSaleChild saleAndSaleChild,
			@PathVariable("idSale") String id) {
		try {
			Sale saleParentOld = saleService.findSaleById(id);
			Map<String, String> errorsMap = new HashMap<>();
			// Validate name
			if (saleAndSaleChild.getSaleParent().getSaleName().isBlank()) {
				errorsMap.put("saleName", "T??n ch????ng tr??nh khuy???n m??i kh??ng ???????c ????? tr???ng !");
			} else {
				if (!saleService.checkSaleName(saleAndSaleChild.getSaleParent(), null, request.getMethod())) {
					errorsMap.put("saleName", "T??n ch????ng tr??nh khuy???n m??i ???? t???n t???i");
				}
			}
			// Validate discount
			if (saleAndSaleChild.getSaleParent().getDiscount() == null) {
				errorsMap.put("discount", "Discount kh??ng ???????c ????? tr???ng !");
			} else if (!Common.checkBigDecimal(saleAndSaleChild.getSaleParent().getDiscount().toString())) {
				errorsMap.put("discount", "Discount ph???i l?? s??? !");
			} else if (saleAndSaleChild.getSaleParent().getDiscountType() == 0
					&& saleAndSaleChild.getSaleParent().getDiscount().doubleValue() < 10000) {
				errorsMap.put("discount", "Discount ph???i l???n h??n 10,000 !");

			} else if (saleAndSaleChild.getSaleParent().getDiscountType() == 1
					&& ((saleAndSaleChild.getSaleParent().getDiscount().doubleValue() > 100
							|| saleAndSaleChild.getSaleParent().getDiscount().doubleValue() <= 0))) {
				errorsMap.put("discount", "Discount ph???i l???n h??n 0 nh??? h??n 100 !");
			}

			// Validate SaleType = 1
			if (saleAndSaleChild.getSaleParent().getSaleType() == 1) {
				if (saleAndSaleChild.getSaleParent().getStartDate() == null) {
					errorsMap.put("startDate", "Ng??y b???t ?????u kh??ng ???????c ????? tr???ng !");
				} else {
					if (!Common.checkStartDateSale(saleAndSaleChild.getSaleParent().getStartDate())) {
						errorsMap.put("startDate", "Ng??y gi??? b???t ?????u ph???i l???n h??n ng??y gi??? hi???n t???i !");
					}
				}

				// Validate endDate
				if (saleAndSaleChild.getSaleParent().getEndDate() == null) {
					errorsMap.put("endDate", "Ng??y k???t th??c kh??ng ???????c ????? tr???ng !");
				} else if (saleAndSaleChild.getSaleParent().getStartDate() != null) {
					if (!Common.checkEndDateSale(saleAndSaleChild.getSaleParent().getStartDate(),
							saleAndSaleChild.getSaleParent().getEndDate())) {
						errorsMap.put("endDate", "Ng??y gi??? k???t th??c ph???i l???n h??n ng??y gi??? b???t ?????u 5' !");
					}
				}

			}
			// Validate SaleType = 2
			else if (saleAndSaleChild.getSaleParent().getSaleType() == 2) {
				if (saleAndSaleChild.getLstSaleChild().size() == 0 || saleAndSaleChild.getLstSaleChild() == null) {
					errorsMap.put("lstSaleChild", "B???n ch??a ch???n th??? khuy???n m??i !");
				}
				List<Sale> lstSaleChildList = saleAndSaleChild.getLstSaleChild();
				for (Sale sale : lstSaleChildList) {
					// Validate weekDay
					if (sale.getWeekday() == 0) {
						errorsMap.put("weekDay", "Th??? kh??ng ???????c ????? tr???ng !");
					} else if (sale.getWeekday() < 1 || sale.getWeekday() > 8) {
						errorsMap.put("weekDay", "Th??? ph???i n???m trong kho???ng t??? th??? 2 ?????n ch??? nh???t !");
					}
					// validate startDate and EndDate
					if (sale.getStartDate() == null) {
						if (sale.getEndDate() != null) {
							errorsMap.put("startDate", "Ng??y b???t ?????u kh??ng ???????c ????? tr???ng !");
						} else {
							if (sale.getEndDate() == null) {
								errorsMap.put("endDate", "Ng??y k???t th??c kh??ng ???????c ????? tr???ng !");
							} else {
								if (!Common.checkEndDateSaleChild(sale.getStartDate(), sale.getEndDate())) {
									errorsMap.put("endDate",
											"Ng??y gi??? k???t th??c ph???i l???n h??n ng??y gi??? b???t ?????u 7 ng??y !");
								}
							}
						}
					}
					// Validate StartAt and EndAt
					if (sale.getStartAt() == null) {
						if (sale.getEndAt() != null) {
							errorsMap.put("startAt", "Gi??? b???t ?????u kh??ng ???????c ????? tr???ng !");
						}
					} else {
						if (sale.getEndAt() == null) {
							errorsMap.put("endAt", "Gi??? k???t th??c ?????u kh??ng ???????c ????? tr???ng !");
						} else {
							if (!Common.checkEndAtSaleChild(sale.getStartAt(), sale.getEndAt())) {
								errorsMap.put("endAt", "Gi??? k???t th??c ph???i l???n h??n ng??y gi??? b???t ?????u 5 ph??t !");
							}
						}
					}
				}
			}
			// Validate productVariant
			if (saleAndSaleChild.getLstProductVariants() == null
					|| saleAndSaleChild.getLstProductVariants().size() == 0) {
				errorsMap.put("listProductVariants", "B???n ch??a ch???n s???n ph???m khuy???n m??i !");
			}
			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				return ResponseEntity.ok().body(saleService.updateAndSaleChild(saleAndSaleChild));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	// X??a sale con
	@DeleteMapping("find-variant-value-by-product-variant/{idSale}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<Sale> deleteSaleChild(@PathVariable("idSale") Sale saleChild) {
		try {

			return ResponseEntity.ok(saleService.deleteSaleChild(saleChild));
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

}
