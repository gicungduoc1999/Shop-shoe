/**
 * DATN_FALL2022, 2022
 * BillRestControllerAdmin.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pro2111.beans.BillAndBillDetail;
import com.pro2111.entities.Bill;
import com.pro2111.entities.ProductVariant;
import com.pro2111.service.BillService;
import com.pro2111.service.ProductVariantService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/bills")
public class BillRestControllerAdmin {

	@Autowired
	private BillService billService;

	@GetMapping
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Bill>> getAllBill() {
		try {
			return ResponseEntity.ok(billService.getAllBill());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("{billId}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<Bill> getBillByBillId(@PathVariable("billId") String billId) {
		try {
			return ResponseEntity.ok(billService.findById(billId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("search-approximate-bill/{inputString}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Bill>> searchApproximateBill(@PathVariable("inputString") String inputString) {
		try {
			return ResponseEntity.ok(billService.searchApproximateBill(inputString));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("search-approximate-customer/{inputString}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Bill>> searchApproximateCustomer(@PathVariable("inputString") String inputString) {
		try {
			return ResponseEntity.ok(billService.searchApproximateCustomer(inputString));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("search-approximate-phone/{inputString}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Bill>> searchApproximatePhone(@PathVariable("inputString") String inputString) {
		try {
			return ResponseEntity.ok(billService.searchApproximatePhone(inputString));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("search-approximate-address/{inputString}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Bill>> searchApproximateAddress(@PathVariable("inputString") String inputString) {
		try {
			return ResponseEntity.ok(billService.searchApproximateAddress(inputString));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-bill-eligible-for-return")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Bill>> findBillEligibleForReturn() {
		try {
			return ResponseEntity.ok(billService.findBillEligibleForReturn());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-bill-return-invoices")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Bill>> findBillReturnInvoices() {
		try {
			return ResponseEntity.ok(billService.findBillReturnInvoices());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("search-approximate-seller/{inputString}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Bill>> searchApproximateSeller(@PathVariable("inputString") String inputString) {
		try {
			return ResponseEntity.ok(billService.searchApproximateSeller(inputString));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@RequestMapping(value = "get-bill-id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public JsonNode getBillId() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		String billId = getBillIdString();
		node.put("billId", billId);
		return node;
	}

	@PostMapping("create-bill-and-billdetail")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<Bill> createBillAndBillDetail(@Valid @RequestBody BillAndBillDetail billAndBillDetail) {
		try {
			billAndBillDetail.getBill().setBillId(getBillIdString());
			return ResponseEntity.ok(billService.createdBillAndBillDetail(billAndBillDetail));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("{idBill}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<Bill> updateBill(@PathVariable("idBill") String idBill, @Valid @RequestBody Bill bill) {
		try {
			Bill billOld = billService.findById(idBill);
			if (idBill == null || "".equals(idBill)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			} else {
				if (billOld.getStatus() > bill.getStatus()) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				} else if (billOld.getStatus() == 6) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				} else if (billOld.getStatus() > 2) {
					billOld.setStatus(bill.getStatus());
					return ResponseEntity.ok(billService.updateBill(billOld));
				} else {
					bill.setBillId(idBill);
					return ResponseEntity.ok(billService.updateBill(bill));
				}
			}
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

	public String getBillIdString() {
		String billId = "";
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
			Date d = new Date();
			billId = dateFormat.format(d);

			long countBill = billService.countBillId(billId);

			boolean dup = false;
			do {
				if (countBill > 998) {
					billId = billId + (countBill + 1);
				} else if (countBill > 98) {
					billId = billId + "0" + (countBill + 1);
				} else if (countBill > 8) {
					billId = billId + "00" + (countBill + 1);
				} else {
					billId = billId + "000" + (countBill + 1);
				}
				List<Bill> list = billService.findByBillIdLike(billId);
				if (list.size() > 0) {
					dup = true;
					countBill++;
					billId = dateFormat.format(d);
				} else {
					dup = false;
				}
				billId = "HD" + billId;
			} while (dup);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Lỗi số hóa đơn");
			billId = "";
		}
		return billId;
	}
}