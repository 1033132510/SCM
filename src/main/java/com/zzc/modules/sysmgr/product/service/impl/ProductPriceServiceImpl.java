package com.zzc.modules.sysmgr.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.product.dao.ProductPriceDao;
import com.zzc.modules.sysmgr.product.entity.ProductPrice;
import com.zzc.modules.sysmgr.product.service.ProductPriceService;

@Service("productPriceService")
public class ProductPriceServiceImpl extends BaseCrudServiceImpl<ProductPrice>
		implements ProductPriceService {

	private ProductPriceDao productPriceDao;

	@Autowired
	public ProductPriceServiceImpl(BaseDao<ProductPrice> productPriceDao) {
		super(productPriceDao);
		this.productPriceDao = (ProductPriceDao) productPriceDao;
	}

	/**
	 * IN 不影响性能
	 */
	@Override
	public List<ProductPrice> getProductPriceByProductId(String productId,
			Integer status, List<String> priceKindeModelIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("AND_EQ_productSKUId", productId);
		params.put("AND_EQ_status", status);
		if (priceKindeModelIds != null && priceKindeModelIds.size() > 0) {
			params.put("AND_IN_priceKindModel.id", priceKindeModelIds);
		}
		return findAll(params, ProductPrice.class, "asc", "priceKindModel.priceKindType");
	}

	@Override
	public void disableProductPrice(String productId) {
		productPriceDao.disableProductPrice(productId);
	}
}
