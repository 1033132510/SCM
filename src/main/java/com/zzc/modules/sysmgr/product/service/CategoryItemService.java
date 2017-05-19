package com.zzc.modules.sysmgr.product.service;

import java.util.List;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.product.entity.CategoryItem;
/**
 * 
 * @author famous
 *
 */
public interface CategoryItemService extends BaseCrudService<CategoryItem> {

	/**
	 * 通过类别Code找到类别
	 * @param code
	 * @return
	 */
	public List<CategoryItem> findCategoryItemByCode(String code);

	/**
	 *  通過類別找到類別屬性
	 * @param cateId
	 * @param status
	 * @param sortField
	 * @param sortType
	 * @return
	 */

	public List<CategoryItem> findCategoryItemByCateId(String cateId, Integer status, String sortField, String sortType);
}
