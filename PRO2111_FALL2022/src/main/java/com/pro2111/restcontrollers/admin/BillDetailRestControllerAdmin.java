/**
 * DATN_FALL2022, 2022
 * BillDetailRestControllerAdmin.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.admin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pro2111.entities.Bill;
import com.pro2111.entities.BillDetail;
import com.pro2111.service.BillDetailService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/bill-details")
public class BillDetailRestControllerAdmin {

	@Autowired
	private BillDetailService billDetailService;

	private int errorNote;
	private int errorQuantity;

	@PostMapping
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<BillDetail> createBillDetail(@Valid @RequestBody BillDetail billDetail) {
		try {
			return ResponseEntity.ok(billDetailService.createdBillDetail(billDetail));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-bill/{idBill}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetail>> findByBill(@PathVariable("idBill") Bill bill) {
		try {
			return ResponseEntity.ok(billDetailService.findByBill(bill));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-bill-detail-eligible-for-return/{idBill}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetail>> findByBillDetailEligibleForReturn(@PathVariable("idBill") Bill bill) {
		try {
			List<BillDetail> billDetails = billDetailService.findByBillDetailEligibleForReturn(bill);
//			for (int i = billDetails.size() - 1; i >= 0; i--) {
//				if (billDetails.get(i).getStatus() != 0) {
//					billDetails.remove(i);
//				}
//			}
			return ResponseEntity.ok(billDetails);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-bill-detail-return-invoices/{idBill}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetail>> findByBillDetailReturnInvoices(@PathVariable("idBill") Bill bill) {
		try {
			List<BillDetail> billDetails = billDetailService.findByBillDetailReturnInvoices(bill);
//			for (int i = billDetails.size() - 1; i >= 0; i--) {
//				if (billDetails.get(i).getStatus() == 0) {
//					billDetails.remove(i);
//				}
//			}
			return ResponseEntity.ok(billDetails);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<BillDetail> updateBillDetail(@Valid @RequestBody BillDetail billDetail) {
		try {
			if (billDetail.getBills().getStatus() == 4 || billDetail.getBills().getStatus() == 6) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			return ResponseEntity.ok(billDetailService.updateBillDetail(billDetail));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("return-bill-detail-of-admin")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<BillDetail>> returnBillDetailOfAdmin(@RequestBody List<BillDetail> billDetails) {
		try {
			if (billDetails.size() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			} else {
				for (int i = 0; i < billDetails.size(); i++) {
					BillDetail detail = billDetails.get(i);
					BillDetail detailOld = billDetailService.findById(detail.getDetailBillId());
					if (detail.getNote() == null || detail.getNote() == "" || detail.getNote().length() > 255) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
					} else if (detail.getQuantity() < 0 || detail.getQuantity() > detailOld.getQuantity()) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
					}
				}
				return ResponseEntity.ok(billDetailService.returnBillDetailOfAdmin(billDetails));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping("{idBillDetail}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<BillDetail> deleteBillDetail(@PathVariable("idBillDetail") BillDetail billDetail) {
		try {
			List<BillDetail> lstDetails = billDetailService.findByBill(billDetail.getBills());
			if (lstDetails.size() == 1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			return ResponseEntity.ok(billDetailService.deleteBillDetail(billDetail));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
