package com.zzc.modules.sysmgr.product.web.vo;

import java.io.Serializable;

/**
 * 搜索专用
 * 
 * @author apple
 *
 */
public class ItemSearchVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String itemCode;
	
	private String value;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
