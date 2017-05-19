package com.zzc.modules.sysmgr.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.product.dao.ProductPropertiesDao;
import com.zzc.modules.sysmgr.product.entity.ProductProperties;
import com.zzc.modules.sysmgr.product.service.ProductPropertiesService;

/**
 * 
 * @author apple
 *
 */
@Service("productPropertiesService")
public class ProductPropertiesServiceImpl extends BaseCrudServiceImpl<ProductProperties> implements ProductPropertiesService {

	private ProductPropertiesDao productPropertiesDao;

	@Autowired
	public ProductPropertiesServiceImpl(BaseDao<ProductProperties> productPropertiesDao) {
		super(productPropertiesDao);
		this.productPropertiesDao = (ProductPropertiesDao) productPropertiesDao;
	}

	@Override
	public List<ProductProperties> getProductCategoryItemValueByProductAndCategory(String productId, String cateId, Integer status) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (productId != null) {
			params.put("AND_EQ_productSKUId", productId);
		}
		if (cateId != null) {
			params.put("AND_EQ_productCategoryId", cateId);
		}
		if (status != null) {
			params.put("AND_EQ_status", status);
		}
		return findAll(params, ProductProperties.class, "ASC", "orderNumber");
	}

	@Override
	public void disableProductProperties(String productId) {
		productPropertiesDao.disableProductProperties(productId);
	}

}