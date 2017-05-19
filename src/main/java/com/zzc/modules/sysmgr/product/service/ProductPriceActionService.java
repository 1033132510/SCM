package com.zzc.modules.sysmgr.product.service;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.product.entity.ProductPriceAction;
import com.zzc.modules.sysmgr.product.service.vo.ProductPriceActionVO;

public interface ProductPriceActionService extends
		BaseCrudService<ProductPriceAction> {

	void saveProductPriceAction(String productSkuId,
								String productCategoryId, ProductPriceActionVO productPriceActionVO);
}
