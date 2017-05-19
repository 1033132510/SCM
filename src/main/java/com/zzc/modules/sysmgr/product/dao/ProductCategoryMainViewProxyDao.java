package com.zzc.modules.sysmgr.product.dao;

import java.util.List;
import java.util.Map;

import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.ProductCategoryMainViewProxy;
import com.zzc.modules.sysmgr.product.web.vo.CategoryChargeVO;

/**
 * 
 * @author apple
 *
 */
public interface ProductCategoryMainViewProxyDao {
	
	public List<ProductCategoryMainViewProxy> findListByParamsForManager(Map<String, Object> map,
			Category category, Integer status, List<CategoryChargeVO> categoryChargeVOList,
			Integer curPage, Integer pageSize);
	
	public long countByParamsForManager(Map<String, Object> map,
			Category category, Integer status, List<CategoryChargeVO> categoryChargeVOList);
	
	public List<Object[]> findListByParamsForSupplier(Map<String, Object> map,
			Integer status, Integer curPage, Integer pageSize);
	
	public long countByParamsForSupplier(Map<String, Object> map, Integer status);
	
}