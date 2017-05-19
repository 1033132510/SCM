package com.zzc.modules.sysmgr.product.service;

import java.util.List;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.product.entity.ProductPrice;

public interface ProductPriceService extends BaseCrudService<ProductPrice> {

	/**
	 * 查询商品价格
	 * 
	 * @param productId
	 * @param status
	 * @return
	 */
	List<ProductPrice> getProductPriceByProductId(String productId,
			Integer status, List<String> priceKindeModelIds);

	/**
	 * 让商品价格失效
	 */
	public void disableProductPrice(String productId);
}
