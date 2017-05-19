package com.zzc.modules.sysmgr.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.product.dao.CategoryTreeViewProxyDao;
import com.zzc.modules.sysmgr.product.entity.CategoryTreeViewProxy;
import com.zzc.modules.sysmgr.product.service.CategoryTreeViewProxyService;

/**
 * 
 * @author apple
 *
 */
@Service("categoryTreeViewProxyService")
public class CategoryTreeViewProxyServiceImpl extends BaseCrudServiceImpl<CategoryTreeViewProxy> implements CategoryTreeViewProxyService {

	@SuppressWarnings("unused")
	private CategoryTreeViewProxyDao categoryTreeViewProxyDao;

	@Autowired
	public CategoryTreeViewProxyServiceImpl(BaseDao<CategoryTreeViewProxy> categoryTreeViewProxyDao) {
		super(categoryTreeViewProxyDao);
		this.categoryTreeViewProxyDao = (CategoryTreeViewProxyDao) categoryTreeViewProxyDao;
	}

	@Override
	public List<CategoryTreeViewProxy> findCategoryTreeViewProxyByCateId(String lev1Id, String lev2Id, String lev3Id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (lev1Id != null) {
			map.put("AND_EQ_categoryTreeView.lev1Id", lev1Id);
		}
		if (lev2Id != null) {
			map.put("AND_EQ_categoryTreeView.lev2Id", lev2Id);
		}
		if (lev3Id != null) {
			map.put("AND_EQ_categoryTreeView.lev3Id", lev3Id);
		}
		return findAll(map, CategoryTreeViewProxy.class);
	}

}
