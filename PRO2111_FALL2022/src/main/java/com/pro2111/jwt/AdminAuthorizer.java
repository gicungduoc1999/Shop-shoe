package com.pro2111.jwt;

import org.springframework.security.core.Authentication;

public interface AdminAuthorizer {
	boolean authorize(Authentication authentication);
}
