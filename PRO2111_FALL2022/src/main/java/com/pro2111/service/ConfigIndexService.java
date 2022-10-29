/**
 * DATN_FALL2022, 2022
 * ConfigIndexes.java, BUI_QUANG_HIEU
 */
package com.pro2111.service;

import org.springframework.context.event.EventListener;

import com.pro2111.ServletInitializer;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface ConfigIndexService {
//	@EventListener(ServletInitializer.class)
	public void perform() throws Exception;
}
