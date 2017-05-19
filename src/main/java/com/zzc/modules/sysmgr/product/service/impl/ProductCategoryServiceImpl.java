package com.zzc.modules.sysmgr.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.product.dao.ProductCategoryDao;
import com.zzc.modules.sysmgr.product.entity.ProductCategory;
import com.zzc.modules.sysmgr.product.service.ProductCategoryService;

/**
 * 
 * @author apple
 *
 */
@Service("productCategoryService")
public class ProductCategoryServiceImpl extends BaseCrudServiceImpl<ProductCategory> implements ProductCategoryService {

	@SuppressWarnings("unused")
	private ProductCategoryDao productCategoryDao;

	@Autowired
	public ProductCategoryServiceImpl(BaseDao<ProductCategory> productCategoryDao) {
		super(productCategoryDao);
		this.productCategoryDao = (ProductCategoryDao) productCategoryDao;
	}

}
