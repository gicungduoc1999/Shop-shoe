/**
 * Luvina Software JSC, 2022
 * SendMail.java, Bui Quang Hieu
 */
package com.pro2111.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BUI_QUANG_HIEU
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMail {
     private String to;
     private String subject;
     private String content;
	/**
	 * Description của method là làm gì
	 * @param [name] [giải thích]: mô tả ý nghĩa và giải thích cách dùng
	 * @return [giải thích]: Mô tả giá trị và về của nó
	*/
	@Override
	public String toString() {
		return "SendMail [to=" + to + ", subject=" + subject + ", content=" + content + "]";
	}
     
     
}
