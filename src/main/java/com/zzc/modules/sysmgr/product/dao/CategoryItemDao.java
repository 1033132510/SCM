package com.zzc.modules.sysmgr.product.dao;

import java.util.List;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.product.entity.CategoryItem;

/**
 * 11
 * @author apple
 *
 */
public interface CategoryItemDao extends BaseDao<CategoryItem> {

	/**
	 * 通过code查找cateItem
	 * 
	 * @param itemCode
	 * @return
	 */
	public List<CategoryItem> findCategoryItemByItemCode(String itemCode);
}
