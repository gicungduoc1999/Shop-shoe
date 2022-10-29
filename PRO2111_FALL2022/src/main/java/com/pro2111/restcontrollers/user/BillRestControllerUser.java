package com.pro2111.restcontrollers.user;

import com.pro2111.beans.BuyRequest;
import com.pro2111.entities.Bill;
import com.pro2111.service.BuyService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("user/rest/bills")
public class BillRestControllerUser {
	@Autowired
    private BuyService buyService;
    @PostMapping
    public ResponseEntity<Bill> createBill(@Valid @RequestBody BuyRequest buyRequest) {
        try {
        	buyRequest.getBill().setBillId(getBillIdString());
            return ResponseEntity.ok(buyService.createBill(buyRequest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<List<Bill>> findBill(@PathVariable Integer id){
    	return ResponseEntity.ok(buyService.getBillsByCusId(id));
    }
    
    public String getBillIdString() {
        String billId = "";
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
            Date d = new Date();
            billId = dateFormat.format(d);

            long countBill = buyService.countBillId(billId);

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
                List<Bill> list = buyService.findByBillIdLike(billId);
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
