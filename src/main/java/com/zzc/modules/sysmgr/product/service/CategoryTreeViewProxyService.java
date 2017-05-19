package com.zzc.modules.sysmgr.product.service;

import java.util.List;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.product.entity.CategoryTreeViewProxy;

public interface CategoryTreeViewProxyService extends BaseCrudService<CategoryTreeViewProxy> {

	/**
	 * 通过类别得到类别的树关系
	 * @param lev1Id
	 * @param lev2Id
	 * @param lev3Id
	 * @return
	 */
	public List<CategoryTreeViewProxy> findCategoryTreeViewProxyByCateId(String lev1Id, String lev2Id, String lev3Id);

}
