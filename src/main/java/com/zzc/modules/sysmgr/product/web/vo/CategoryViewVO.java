package com.zzc.modules.sysmgr.product.web.vo;

import java.util.List;

import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.CategoryItem;

/**
 * 
 * @author famous
 *
 */
public class CategoryViewVO {

	/**
	 * 类别
	 */
	private Category category;
	
	/**
	 * 类别属性
	 */

	private List<CategoryItem> categoryItems;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<CategoryItem> getCategoryItems() {
		return categoryItems;
	}

	public void setCategoryItems(List<CategoryItem> categoryItems) {
		this.categoryItems = categoryItems;
	}

}
