package com.zzc.modules.sysmgr.product.enums;

/**
 * 产品类别种类
 * 
 * @author apple
 *
 */
public enum ProductCateTypeEnum {
	/**
	 * 主类别 辅助类别（主要因为一个商品有多个类别） 父级类别包含爷爷
	 */
	mainKind(0), assistKind(1), parent(2);

	private Integer value;

	public Integer getValue() {
		return this.value;
	}

	private ProductCateTypeEnum(Integer value) {
		this.value = value;
	}
}
