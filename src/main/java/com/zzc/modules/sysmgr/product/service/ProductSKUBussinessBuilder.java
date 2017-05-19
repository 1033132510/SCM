package com.zzc.modules.sysmgr.product.service;

import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.ProductSKU;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;

@Deprecated
public interface ProductSKUBussinessBuilder {

	public void buildProductSKU(ProductSKU sku);

	public void buildProductCategory(Category category);

	public void buildProductPrice(ProductSKU sku);

	public void buildProductProperties(ProductSKU sku, Category category);

	public void buildProductImages(ProductSKU productSKU);

	public ProductSKUBussinessVO retrieveResult();

}
