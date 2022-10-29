package com.pro2111.PDF;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pro2111.entities.BillDetail;
import com.pro2111.entities.VariantValue;
import com.pro2111.service.VariantValueService;

@Service
public class PdfService {
	private static final String FONT_ARIAL = "/font/arial.ttf";

	@Autowired
	VariantValueService vvService;

	private String _customNamePro = "";

	/**
	 * Tốn Bộ nhớ !
	 */
//	public PdfService() {
//		
//	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
		PdfPTable table = new PdfPTable(7);
		BaseFont newFont = BaseFont.createFont(FONT_ARIAL, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		document.open();

		setBackgroundImg(writer);
		setLogoToPdf(document);
		writeHeaderPage(document, newFont);
		designTable(table, document, newFont);
		writeEndPage(document, newFont);

		document.close();
	}

	private void designTable(PdfPTable table, Document document, BaseFont airal) throws DocumentException {

//		List<Product> listProduct = Arrays.asList(new Product(40, 1, "Giày adidas", 1, "blue-white", 20),
//				new Product(40, 2, "Giày adidasaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 1, "blue-white", 20),
//				new Product(40, 3, "Giày adidas", 1, "blue-white", 20),
//				new Product(40, 5, "Giày adidas", 1, "blue-white", 20));
		List<BillDetail> lstBillDetails = PdfRestController.staticBill().getBillDetails();

		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3f, 4f, 2f, 3.5f,1.5f, 4f });
		table.setSpacingBefore(10);

		writeTableHeader(table, airal);
		writeTableData(table, lstBillDetails, airal);
		document.add(table);
	}

	private void writeTableHeader(PdfPTable table, BaseFont arial) {
//		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
//		font.setColor(BaseColor.BLACK);
		PdfPCell cell = new PdfPCell();

		cell.setPadding(5);
		cell.setPhrase(new Phrase("STT", new Font(arial, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell.setPhrase(new Phrase("SKU-ID", new Font(arial, 11, Font.BOLD)));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Sản phẩm", new Font(arial, 11, Font.BOLD)));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Số lượng", new Font(arial, 11, Font.BOLD)));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Đơn giá", new Font(arial, 11, Font.BOLD)));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Thuế", new Font(arial, 11, Font.BOLD)));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Thành tiền", new Font(arial, 11, Font.BOLD)));
		table.addCell(cell);
	}

	private void writeTableData(PdfPTable table, List<BillDetail> details, BaseFont arial) {
		PdfPCell cell = new PdfPCell();
		PdfPCell cellTotal = new PdfPCell();
		Double total = 0.0;

		for (int i = 0; i < details.size(); i++) {
			_customNamePro = "";
			BillDetail detail = details.get(i);

			// Nối chuỗi
			
			List<VariantValue> variantValues = vvService.findByProductVariant(detail.getProductVariants());
			variantValues.forEach(vv -> {
				if (vv.getOptionValues().getIsShow() == 1) {
					_customNamePro = _customNamePro.concat(vv.getOptionValues().getValueName() + "-");
				}
			});

			_customNamePro = _customNamePro.substring(0, _customNamePro.length() - 1);
			Double intoMoney = detail.getPrice().doubleValue() * detail.getQuantity();
			cell.setPhrase(new Phrase((i + 1) + ""));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell.setPhrase(new Phrase(detail.getProductVariants().getSkuId(), new Font(arial, 10)));
			table.addCell(cell);

			cell.setPhrase(
					new Phrase(detail.getProductVariants().getProducts().getProductName() + " [" + _customNamePro + "]",
							new Font(arial, 10)));
			table.addCell(cell);

			cell.setPhrase(new Phrase(detail.getQuantity() + "", new Font(arial, 10)));
			table.addCell(cell);

			cell.setPhrase(new Phrase(String.format("%,.0f", detail.getPrice()) + "(VND)", new Font(arial, 10)));
			table.addCell(cell);

			cell.setPhrase(new Phrase(String.format("%,.0f", detail.getTax()) + "(%)", new Font(arial, 10)));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(String.format("%,.0f", intoMoney) + "(VND)", new Font(arial, 10)));
			table.addCell(cell);

			total += intoMoney;
		}
		cellTotal.setPhrase(new Phrase("Tổng tiền:", new Font(arial, 10, Font.BOLD)));
		cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellTotal.setColspan(5);
		cell.setPhrase(new Phrase(String.format("%,.0f", total), new Font(arial, 10)));

		table.addCell(cellTotal);
		table.addCell(cell);
	}

	private void writeHeaderPage(Document document, BaseFont arial)
			throws DocumentException, MalformedURLException, IOException {

		String customer = "";
		String addressString = "";
		String phoneCus = "";
		if (PdfRestController.staticBill().getBill().getCustomers() == null) {
			customer = "Null";
		} else {
			customer = PdfRestController.staticBill().getBill().getCustomers().getFullName();
		}

		if (PdfRestController.staticBill().getBill().getAddress() == null) {
			addressString = "Null";
		} else {
			addressString = PdfRestController.staticBill().getBill().getAddress();
		}

		if (PdfRestController.staticBill().getBill().getPhone() == null) {
			phoneCus = "Null";
		} else {
			phoneCus = PdfRestController.staticBill().getBill().getPhone();
		}

		SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");

		Paragraph titleName = new Paragraph("Bluv.snk", new Font(arial, 25, Font.BOLD));
		Paragraph address = new Paragraph("Address: Đường Trịnh Văn Bô, Nam Từ Liêm, Hà Nội",
				new Font(arial, 11, Font.BOLD));
		Paragraph phoneNumber = new Paragraph("Số điện thoại: 0395.96.2002", new Font(arial, 11, Font.BOLD));
		Paragraph email = new Paragraph("Email: horsesoftware002@gmail.com", new Font(arial, 11, Font.BOLD));
		Paragraph billName = new Paragraph("HÓA ĐƠN BÁN HÀNG", new Font(arial, 20, Font.BOLD));
		Paragraph maHoaDon = new Paragraph(PdfRestController.staticBill().getBill().getBillId(), new Font(arial));
		Paragraph dayCreate = new Paragraph(
				"Ngày mua: " + PdfRestController.staticBill().getBill().getCreatedDate(), new Font(arial, 11));
		Paragraph employeeName = new Paragraph("Khách hàng: " + customer, new Font(arial, 11));
		Paragraph addressEmployee = new Paragraph("Địa chỉ: " + addressString, new Font(arial, 11));
		Paragraph phoneNumEmplye = new Paragraph("Số điện thoại: " + phoneCus, new Font(arial, 11));

		titleName.setAlignment(Paragraph.ALIGN_CENTER);
		address.setAlignment(Paragraph.ALIGN_CENTER);
		phoneNumber.setAlignment(Paragraph.ALIGN_CENTER);
		email.setAlignment(Paragraph.ALIGN_CENTER);
		billName.setAlignment(Paragraph.ALIGN_CENTER);
		billName.setSpacingBefore(10);
		maHoaDon.setAlignment(Paragraph.ALIGN_CENTER);
		maHoaDon.setSpacingBefore(10);
		employeeName.setAlignment(Paragraph.ALIGN_LEFT);
		addressEmployee.setAlignment(Paragraph.ALIGN_LEFT);
		phoneNumEmplye.setAlignment(Paragraph.ALIGN_LEFT);
		dayCreate.setAlignment(Paragraph.ALIGN_LEFT);
		dayCreate.setSpacingBefore(20);

		document.add(titleName);
		document.add(phoneNumber);
		document.add(email);
		document.add(address);
		document.add(billName);
		document.add(maHoaDon);
		document.add(dayCreate);
		document.add(employeeName);
		document.add(addressEmployee);
		document.add(phoneNumEmplye);
	}

	private void writeEndPage( Document document, BaseFont arial) throws DocumentException {
		PdfPTable table = new PdfPTable(6);
		PdfPCell cell = new PdfPCell();
		
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1f, 3f, 4f, 2f, 3.5f, 4f });
		table.setSpacingBefore(10);
	
		cell.setBorder(0);
		cell.setPhrase(new Phrase("Chiết khấu: ", new Font(arial, 13)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(5);
		table.addCell(cell);
		cell.setPhrase(new Phrase(PdfRestController.staticBill().getBill().getDiscount() + " %", new Font(arial, 13)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Phí ship: " ,  new Font(arial, 13)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(5);
		table.addCell(cell);
		cell.setPhrase(new Phrase(String.format("%,.0f",PdfRestController.staticBill().getBill().getShippingFee()) + " VNĐ",  new Font(arial, 13)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Tổng tiền phải thanh toán: " , new Font(arial, 13)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(5);
		table.addCell(cell);
		cell.setPhrase(new Phrase(String.format("%,.0f", PdfRestController.staticBill().getBill().getTotalMoney()) + " VNĐ", new Font(arial, 13)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);
		
		Paragraph askThank = new Paragraph("----Cảm ơn quý khách !----", new Font(arial, 12, Font.ITALIC));
		
		askThank.setSpacingBefore(50);
		askThank.setAlignment(Element.ALIGN_CENTER);
		
		document.add(table);
		document.add(askThank);
	}

	private void setLogoToPdf(Document document) throws MalformedURLException, IOException, DocumentException {
//		Image logo = Image.getInstance("classpath:font/bill.png");
//
//		logo.scaleAbsolute(70, 70);
//		logo.setAlignment(Image.LEFT | Image.TEXTWRAP);
//
//		document.add(logo);
	}

	private void setBackgroundImg(PdfWriter writer) throws MalformedURLException, IOException, DocumentException {
//		PdfContentByte canvas = writer.getDirectContentUnder();
//		Image image = Image
//				.getInstance("classpath:font/bill.png");
//		image.scaleAbsolute(420, 595);
//		image.setAbsolutePosition(0, 0);
//
//		canvas.addImage(image);
		Image image = Image.getInstance("classpath:font/logo.jpg");
		image.scaleToFit(200, 200);
		image.setAbsolutePosition((PageSize.A4.getWidth() - 200) / 2, (PageSize.A4.getHeight() - 200) / 2);

		writer.setPageEvent(new ImageBackgroundHelper(image));
	}

	public final PrintService findPrintService(String printerName) {
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		for (PrintService printService : printServices) {
			if (printService.getName().trim().equals(printerName)) {
				return printService;
			}
		}
		return null;
	}

	public PDDocument exportPDdocument(HttpServletResponse response) throws DocumentException, IOException {
		PDDocument document = new PDDocument();
		export(response);

		document.save(response.getOutputStream());
		document.close();
		return document;
	}
}
