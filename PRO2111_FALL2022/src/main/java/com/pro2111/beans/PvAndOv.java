/**
 * DATN_FALL2022, 2022
 * PvAndOv.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pro2111.entities.OptionValue;
import com.pro2111.entities.ProductVariant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PvAndOv {

	@NotNull
	private ProductVariant productVariant;

	@NotEmpty
	private List<OptionValue> optionValues;

	private List<String> images;
}
