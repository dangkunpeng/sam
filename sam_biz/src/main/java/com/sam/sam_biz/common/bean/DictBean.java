package com.sam.sam_biz.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictBean {
	private String itemName;
	private String itemValue;
	private String itemType;

	public static DictBean getDictOfNameEqValue(String val) {
		return DictBean.builder().itemValue(val).itemName(val).build();
	}
}
