/**
 * DATN_FALL2022, 2022
 * Common.java, BUI_QUANG_HIEU
 */
package com.pro2111.utils;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;
import com.pro2111.entities.VariantValue;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public class Common {

	public static Integer convertStringToInt(String input, Integer defaultValue) {
		Integer value = 0;
		try {
			value = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			value = defaultValue;
		}
		return value;
	}

	public static Boolean checkStartDateSale(LocalDateTime dateTime) {
		LocalDateTime dateTimeNow = LocalDateTime.now();
		long secondInput = dateTime.toEpochSecond(ZoneOffset.UTC);
		long secondNow = dateTimeNow.toEpochSecond(ZoneOffset.UTC);
		long minute = (secondNow - secondInput) / 60;
		if (minute <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public static Boolean checkStartDateSaleCreate(LocalDateTime dateTime) {
		LocalDateTime dateTimeNow = LocalDateTime.now();
		long secondInput = dateTime.toEpochSecond(ZoneOffset.UTC);
		long secondNow = dateTimeNow.toEpochSecond(ZoneOffset.UTC);
		long minute = (secondNow - secondInput) / 60;
		if (minute < 0) {
			return false;
		} else {
			return true;
		}
	}

	public static Boolean checkBigDecimal(String input) {
		Boolean check = false;
		try {
			new BigDecimal(input);
			check = true;
		} catch (Exception e) {
			check = false;
		}
		return check;
	}

	public static List<ProductVariant> customNameProductByProductVariant(List<ProductVariant> productVariantsReturn) {
		List<String> productName = new ArrayList<String>();
		for (int i = 0; i < productVariantsReturn.size(); i++) {
			productName.add(productVariantsReturn.get(i).getProducts().getProductName());
		}

		for (int i = 0; i < productVariantsReturn.size(); i++) {
			ProductVariant pv = productVariantsReturn.get(i);
			List<VariantValue> list = new ArrayList<>(pv.getVariantValues());
			Comparator<VariantValue> comparator = Comparator.comparing(h -> h.getOptionValues().getValueName());
			list.sort(comparator.reversed());
			StringJoiner name = new StringJoiner("-");
			for (int j = 0; j < list.size(); j++) {
				VariantValue value = list.get(j);
				if (value.getOptionValues().getIsShow() == 1) {
					name.add(value.getOptionValues().getValueName());
				}
			}
			Product product = new Product();
			product.setProductName(productName.get(i) + " [" + name.toString() + "]");
			pv.setProducts(product);
		}
		return productVariantsReturn;
	}

	public static Boolean checkEndDateSale(LocalDateTime start, LocalDateTime end) {

		long secondStart = start.toEpochSecond(ZoneOffset.UTC);
		long secondEnd = end.toEpochSecond(ZoneOffset.UTC);
		long minute = (secondEnd - secondStart) / 60;
		if (minute < 5) {
			return false;
		} else {
			return true;
		}
	}
	
	//check created user
	public static Boolean checkCreatedDateUser(LocalDateTime createDate) {
		LocalDateTime dateTimeNow = LocalDateTime.now();
		long secondStart = createDate.toEpochSecond(ZoneOffset.UTC);
		long secondEnd = dateTimeNow.toEpochSecond(ZoneOffset.UTC);
		long second = secondEnd - secondStart;
		if (second < 120) {
			return true;
		} else {
			return false;
		}
	}
	public static Boolean checkEndDateSaleChild(LocalDateTime start, LocalDateTime end) {

		long secondStart = start.toEpochSecond(ZoneOffset.UTC);
		long secondEnd = end.toEpochSecond(ZoneOffset.UTC);
		long day = (secondEnd - secondStart) / 60/60/24;
		if (day < 7) {
			return false;
		} else {
			return true;
		}
	}
	//check startAt and endAt
	public static Boolean checkEndAtSaleChild(LocalTime start, LocalTime end) {

		long secondStart = start.toSecondOfDay();
		long secondEnd = end.toSecondOfDay();
		long minute = (secondEnd - secondStart) / 60;
		if (minute < 5) {
			return false;
		} else {
			return true;
		}
	}

	public static String genarateSkuID(List<String> options, List<String> optionValues) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SKU-");
		for (int i = 0; i < options.size(); i++) {
			String option = removeAccentsText(options.get(i));
			String optionValue = removeAccentsText(optionValues.get(i));
			if (option.length() < 3) {
				buffer.append(option);
			} else if (option.length() >= 3) {
				buffer.append(option.substring(0, 3));
			}
			if (optionValue.length() < 3) {
				buffer.append(optionValue);
			} else if (optionValue.length() >= 3) {
				buffer.append(optionValue.substring(0, 3));
			}
		}
		return buffer.toString();
	}


	public static void main(String[] args) {
		List<Integer> list;
//		LocalDate start = LocalDate.of(2022, 10, 23);
//		LocalDate stop = LocalDate.of(2022, 11, 30);
//		List<LocalDate> mondays = new ArrayList<>();
//		LocalDate monday = start.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
//		System.out.println(monday);

//		while (monday.isBefore(stop)) {
//			mondays.add(monday);
//			// Set up the next loop.
//			monday = monday.plusWeeks(1);
//		}
//		mondays.forEach(m -> {
//			System.out.println(m);
//		});

		List<String> testSkuIDOption = Arrays.asList("Size", "Chất liệu", "màu sắc");
		List<String> testSkuIDOptionValue = Arrays.asList("36", "việt nam", "vải");
		StringBuffer buffer = new StringBuffer();
		buffer.append("SKU-");
		for (int i = 0; i < testSkuIDOption.size(); i++) {
			String option = removeAccentsText(testSkuIDOption.get(i));
			String optionValue = removeAccentsText(testSkuIDOptionValue.get(i));
			if (option.length() < 3) {
				buffer.append(option);
			} else if (option.length() >= 3) {
				buffer.append(option.substring(0, 3));
			}
			if (optionValue.length() < 3) {
				buffer.append(optionValue);
			} else if (optionValue.length() >= 3) {
				buffer.append(optionValue.substring(0, 3));
			}
		}
		System.out.println(buffer);

	}

	public static String removeAccentsText(String s) {
		s.trim();
		s = s.toUpperCase();
//		String temp1[] = s.split("\\s+");
//		s = temp1[temp1.length - 1];
//		for (int i = 0; i < temp1.length - 1; i++) {
//			s += String.valueOf(temp1[i].charAt(0));
//			if (i < temp1.length - 1) {
//				s += "";
//			}
//		}
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		temp = pattern.matcher(temp).replaceAll("");
		return temp.replaceAll("đ", "d");
	}
	
	//Check trùng saleChile
	public static Boolean checkSaleChild(Sale saleChid, List<Sale> listSaleChild) {
		listSaleChild.remove(saleChid);
		Boolean check = false;
		
		return check;
	}

}
