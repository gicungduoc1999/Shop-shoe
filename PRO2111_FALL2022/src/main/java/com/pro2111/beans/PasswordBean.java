/**
 * DATN_FALL2022, 2022
 * ChangePassword.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
@NoArgsConstructor
public class PasswordBean {

	@NotBlank
	@Length(min = 8)
	private String passwordOld;
	@NotBlank
	@Length(min = 8)
	private String passwordNew;
	@NotBlank
	@Length(min = 8)
	private String confirmPassword;
}
