package com.zzc.modules.sysmgr.product.web.vo;

import java.io.Serializable;
import java.util.List;

import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.product.entity.ProductPrice;
import com.zzc.modules.sysmgr.product.entity.ProductSKU;

/**
 * 
 * @author apple
 * 
 *         产品供应商信息
 *
 */
public class ProductForSupplierOrg implements Serializable {

	private static final long serialVersionUID = 58893820345399087L;
	// SKU
	private ProductSKU product;
	// 图片列表
	private List<Image> images;
	// 价格体系
	private List<ProductPrice> prices;

	public ProductSKU getProduct() {
		return product;
	}

	public void setProduct(ProductSKU product) {
		this.product = product;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<ProductPrice> getPrices() {
		return prices;
	}

	public void setPrices(List<ProductPrice> prices) {
		this.prices = prices;
	}

}
