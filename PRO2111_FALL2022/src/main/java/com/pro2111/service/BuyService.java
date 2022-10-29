package com.pro2111.service;

import com.pro2111.beans.BuyRequest;
import com.pro2111.entities.Bill;

import java.util.List;

public interface BuyService {
    Bill createBill(BuyRequest buyRequest);
    Long countBillId(String billId);
    List<Bill> findByBillIdLike(String id);
	List<Bill> getBillsByCusId(Integer id);
}
