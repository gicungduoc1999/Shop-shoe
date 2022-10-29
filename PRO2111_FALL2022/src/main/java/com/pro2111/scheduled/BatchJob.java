/**
 * DATN_FALL2022, 2022
 * BatchJob.java, BUI_QUANG_HIEU
 */
package com.pro2111.scheduled;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pro2111.entities.Sale;
import com.pro2111.repositories.SaleRepository;
import com.pro2111.service.ConfigIndexService;
import com.pro2111.service.SaleService;
import com.pro2111.utils.Common;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Component
public class BatchJob {
	@Autowired
	private ConfigIndexService configIndexes;
	@Autowired
	private SaleService saleService;

	private Integer count = 0;

	/**
	 * Mỗi phút chạy 1 lần từ thứ 2 đến chủ nhật
	 */
	@Scheduled(cron = "0 * * * * MON-SUN")
	public void perform() throws Exception {
		System.out.println("BatchJob: " + new Date());
		if (count == 0) {
			configIndexes.perform();
		}
		count++;
	}

	@Scheduled(cron = "0 * * * * MON-SUN")
	public void updateStatusSale() throws Exception {
		LocalDateTime dateTimeNow = LocalDateTime.now();
		long secondNow = dateTimeNow.toEpochSecond(ZoneOffset.UTC);
		List<Sale> listSaleUpdate = new ArrayList<Sale>();
		List<Sale> listSaleStatus0 = saleService.findByStatus(0);
		List<Sale> listSaleStatus1 = saleService.findByStatus(1);

		listSaleStatus0.forEach(sale -> {
			long secondInput = sale.getStartDate().toEpochSecond(ZoneOffset.UTC);
			long minute = (secondNow - secondInput) / 60;
			if (minute >= 0) {
				sale.setStatus(1);
				listSaleUpdate.add(sale);
			}
		});

		listSaleStatus1.forEach(sale -> {
			long secondInput = sale.getEndDate().toEpochSecond(ZoneOffset.UTC);
			long minute = (secondInput - secondNow) / 60;
			if (minute <= 0) {
				sale.setStatus(2);
				listSaleUpdate.add(sale);
			}
		});

		if (!listSaleUpdate.isEmpty()) {
			saleService.updateSale(listSaleUpdate);
		}
	}
}
