package com.zzc.modules.sysmgr.product.service.impl;

import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.ProductSKU;
import com.zzc.modules.sysmgr.product.service.ProductSKUBussinessBuilder;

@Deprecated
public class Director {

	private ProductSKUBussinessBuilder builder;

	private ProductSKU sku;

	private Category category;

	public Director() {
	}

	public Director(ProductSKUBussinessBuilder builder, ProductSKU sku, Category category) {
		this.builder = builder;
		this.sku = sku;
		this.category = category;
	}

	/**
	 * 产品构造方法，负责调用各个零件建造方法
	 */
	public void construct() {
		builder.buildProductSKU(sku);
		builder.buildProductPrice(sku);
		builder.buildProductProperties(sku, category);
		builder.buildProductCategory(category);
		builder.buildProductImages(sku);
	}

}
