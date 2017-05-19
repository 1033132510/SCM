package com.zzc.modules.sysmgr.product.web.vo;

/**
 * Brand simple info for ajax request
 * 
 * @author apple
 *
 */
public class BrandSimpleView {

	// ID
	private String brandId;

	// 中文名称
	private String brandZHName;

	// 英文名称
	private String brandENName;

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandZHName() {
		return brandZHName;
	}

	public void setBrandZHName(String brandZHName) {
		this.brandZHName = brandZHName;
	}

	public String getBrandENName() {
		return brandENName;
	}

	public void setBrandENName(String brandENName) {
		this.brandENName = brandENName;
	}

}
