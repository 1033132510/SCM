package com.zzc.modules.product.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.CategoryItem;
import com.zzc.modules.sysmgr.product.service.CategoryService;

/**
 * Created by apple on 2015/11/1.
 */
public class CategoryServiceTest extends BaseServiceTest {

	@Autowired
	private CategoryService categoryService;

	/**
	 * 可以级联插入
	 */

	@Rollback(value = true)
	@Test
	public void getChild() {
		try {
			Category category = categoryService.findByPK("ff80808151565b2e015160b47e150009");
			List<Category> list = categoryService.getAllChildCate(category);
			System.err.println(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void getBase() {
		try {
			Category xx = categoryService.findByPK("8a808085510a7b7a01510a7b96690000");
			System.err.println(JSON.toJSONString(xx));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void createCate1() {
		Category productCategory = new Category();
		productCategory.setCateName(" level1");
		productCategory.setLevel(0);
		categoryService.create(productCategory);
	}

	@Rollback(value = false)
	// @Test
	public void createCate2() {
		Category productCategory = new Category();
		productCategory.setCateName("室内装饰1");
		productCategory.setLevel(1);
		Category parentCategory = categoryService.findByPK("1");
		productCategory.setParentCategory(parentCategory);
		CategoryItem itemKey = new CategoryItem();
		itemKey.setAllowedCustom(1);
		itemKey.setAllowedMultiSelect(0);
		itemKey.setDefaultShowNumber(2);
		itemKey.setItemName("patzheng");
		itemKey.setItemsSources("22,33,44,55");
		itemKey.setCategory(productCategory);
		List<CategoryItem> itemKeys = new ArrayList<CategoryItem>();
		itemKeys.add(itemKey);
		productCategory.setProductCategoryItemKey(itemKeys);
		categoryService.create(productCategory);
	}

	// @Test
	@Transactional
	public void getCate() {
		List<Category> list = categoryService.getProductCategoryTree("4028817b511509310151156a98c50009", null, 1);
		for (Category pc : list) {
			System.err.println(11 + pc.toString() + pc.getChildCategorys().size());
		}
	}

	// @Test
	public void updateCate() {
		Category category = categoryService.findByPK("8a8080855109f12b015109f151a80000");
		CategoryItem itemKey = new CategoryItem();
		itemKey.setAllowedCustom(1);
		itemKey.setAllowedMultiSelect(0);
		itemKey.setDefaultShowNumber(2);
		itemKey.setItemName("品牌22");
		itemKey.setItemsSources("22,33,44,55");
		// itemKey.setProductCategory(category);

		CategoryItem itemKey1 = new CategoryItem();
		itemKey1.setAllowedCustom(1);
		itemKey1.setAllowedMultiSelect(0);
		itemKey1.setDefaultShowNumber(2);
		itemKey1.setItemName("品牌33");
		itemKey1.setItemsSources("22,33,44,55");
		// itemKey1.setProductCategory(category);
		Set<CategoryItem> itemKeys = new HashSet<CategoryItem>();
		itemKeys.add(itemKey);
		itemKeys.add(itemKey1);
		categoryService.create(category);
	}

	// @Test
	public void updateCateExits() {
		Category category = categoryService.findByPK("8a8080855109f12b015109f151a80000");
		CategoryItem itemKey = new CategoryItem();
		itemKey.setAllowedCustom(1);
		itemKey.setAllowedMultiSelect(0);
		itemKey.setDefaultShowNumber(2);
		itemKey.setItemName("品牌22");
		itemKey.setItemsSources("22,33,44,55");
		// itemKey.setProductCategory(category);

		CategoryItem itemKey1 = new CategoryItem();
		itemKey1.setAllowedCustom(1);
		itemKey1.setAllowedMultiSelect(0);
		itemKey1.setDefaultShowNumber(2);
		itemKey1.setItemName("品牌33");
		itemKey1.setItemsSources("22,33,44,55");
		// itemKey1.setProductCategory(category);

		Set<CategoryItem> itemKeys = new HashSet<CategoryItem>();
		itemKeys.add(itemKey);
		itemKeys.add(itemKey1);

		categoryService.create(category);
	}

}
