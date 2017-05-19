package com.zzc.modules.sysmgr.product.service;

import java.util.List;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.product.entity.ProductProperties;

public interface ProductPropertiesService extends BaseCrudService<ProductProperties> {

	
	/**
	 * 根据产品和类别得到所有属性 主要为了商品展示使用
	 * @param productId
	 * @param cateId
	 * @param status
	 * @return
	 */
	List<ProductProperties> getProductCategoryItemValueByProductAndCategory(String productId, String cateId, Integer status);

	/**
	 * 商品属性失效
	 * @param productId
	 */
	void disableProductProperties( String productId);

}