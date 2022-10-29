package com.pro2111.PDF;

import java.awt.print.PrinterJob;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.print.PrintException;
import javax.print.PrintService;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.pro2111.beans.BillAndBillDetail;
import com.pro2111.service.BillDetailService;
import com.pro2111.service.BillService;

@CrossOrigin("*")
@RestController
@RequestMapping("admin/rest/pdf")
public class PdfRestController {
	@Autowired
	PdfService pdfService;

	@Autowired
	BillService billService;

	@Autowired
	BillDetailService billDetailService;

	static BillAndBillDetail billAndBillDetail;

	@GetMapping("/export/{billId}")
	public String donwloadBill(HttpServletResponse response, @PathVariable("billId") String billId)
			throws DocumentException, IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		PdfService exporter = new PdfService(billId);
//		pdfService.getBillId(billId);
		billAndBillDetail = new BillAndBillDetail();
		billAndBillDetail.setBill(billService.findById(billId));
		billAndBillDetail.setBillDetails(billDetailService.findByBill(billService.findById(billId)));
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + billId + LocalDateTime.now().format(formatter) + ".pdf";
		response.setContentType("application/pdf");
		response.setHeader(headerKey, headerValue);
		pdfService.export(response);
		return "Đã xuất";
	}

	@GetMapping("/print/{billId}")
	public String PrintBillInShop(HttpServletResponse response, @PathVariable("billId") String billId)
			throws InvalidPasswordException, IOException, PrintException, DocumentException {
		try {
			billAndBillDetail = new BillAndBillDetail();
			billAndBillDetail.setBill(billService.findById(billId));
			billAndBillDetail.setBillDetails(billDetailService.findByBill(billService.findById(billId)));
//			PdfService exporter = new PdfService(billId);
			response.setContentType("application/pdf");

			PrintService myPrintService = pdfService.findPrintService("My Windows printer Name");
			PrinterJob job = PrinterJob.getPrinterJob();

			job.setPageable(new PDFPageable(pdfService.exportPDdocument(response)));
			job.printDialog();
			job.setPrintService(myPrintService);
			job.print();

		} catch (Exception e) {
			e.printStackTrace();
			return "Không có máy in";
		}
		return "Đã in";
	}

	public static BillAndBillDetail staticBill() {
		return billAndBillDetail;
	}
}
