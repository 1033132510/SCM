package com.zzc.modules.sysmgr.product.service.impl;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.product.dao.ProductPriceActionDao;
import com.zzc.modules.sysmgr.product.entity.ProductPriceAction;
import com.zzc.modules.sysmgr.product.service.ProductPriceActionService;
import com.zzc.modules.sysmgr.product.service.vo.ProductPriceActionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("productPriceActionService")
public class ProductPriceActionServiceImpl extends
		BaseCrudServiceImpl<ProductPriceAction> implements
		ProductPriceActionService {

	@SuppressWarnings("unused")
	private ProductPriceActionDao productPriceActionDao;

	@Autowired
	public ProductPriceActionServiceImpl(BaseDao<ProductPriceAction> baseDao) {
		super(baseDao);
		this.productPriceActionDao = (ProductPriceActionDao) baseDao;
	}


	public void saveProductPriceAction(String productSkuId,
									   String productCategoryId, ProductPriceActionVO productPriceActionVO) {
		ProductPriceAction action = new ProductPriceAction();
		action.setStatus(CommonStatusEnum.有效.getValue());
		action.setProductSkuId(productSkuId);
		action.setProductCategoryId(productCategoryId);
		productPriceActionVO.setCreateTime(new Date());
		productPriceActionVO.setModCount(getModCount(productSkuId,
				productCategoryId).intValue() + 1);
		action.setModifyRecord(JsonUtils.toJson(productPriceActionVO));
		create(action);
	}


	private Long getModCount(String productSkuId, String productCategoryId) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_productSkuId", productSkuId);
		params.put("AND_EQ_productCategoryId", productCategoryId);
		return findAllCount(params,
				ProductPriceAction.class);
	}
}
