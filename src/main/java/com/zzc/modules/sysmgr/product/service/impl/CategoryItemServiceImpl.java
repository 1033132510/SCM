package com.zzc.modules.sysmgr.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.product.dao.CategoryItemDao;
import com.zzc.modules.sysmgr.product.entity.CategoryItem;
import com.zzc.modules.sysmgr.product.service.CategoryItemService;

@Service("categoryItemService")
public class CategoryItemServiceImpl extends BaseCrudServiceImpl<CategoryItem> implements CategoryItemService {

	private CategoryItemDao itemKeyDao;

	@Autowired
	public CategoryItemServiceImpl(BaseDao<CategoryItem> itemKeyDao) {
		super(itemKeyDao);
		this.itemKeyDao = (CategoryItemDao) itemKeyDao;
	}

	@Override
	public List<CategoryItem> findCategoryItemByCode(String code) {
		return itemKeyDao.findCategoryItemByItemCode(code);
	}

	@Override
	public List<CategoryItem> findCategoryItemByCateId(String cateId, Integer status, String sortField, String sortType) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (cateId != null) {
			params.put("AND_EQ_category.id", cateId);
		}
		if (status != null) {
			params.put("AND_EQ_status", status);
		}
		return findAll(params, CategoryItem.class, sortType, sortField);
	}

}
