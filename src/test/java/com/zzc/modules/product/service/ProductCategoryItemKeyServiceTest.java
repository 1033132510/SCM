package com.zzc.modules.product.service;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.CategoryItem;
import com.zzc.modules.sysmgr.product.service.CategoryService;

public class ProductCategoryItemKeyServiceTest extends BaseServiceTest {

	@Autowired
	private CategoryService productCategoryService;

	@Test
	public void createSupplierTest() {
		try {
			Category productCategory = new Category();
			productCategory.setCateName("内室面材料333");

			CategoryItem itemKey = new CategoryItem();

			itemKey.setItemName("cacsed saved1111");

			Set<CategoryItem> itemKeys = new HashSet<CategoryItem>();

			itemKeys.add(itemKey);

			productCategoryService.create(productCategory);

			System.err.println(productCategoryService.findAll().size() + "patzheng");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
