/**
 * DATN_FALL2022, 2022
 * Login.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import javax.validation.constraints.NotBlank;

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
public class AuthRequest {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
}
