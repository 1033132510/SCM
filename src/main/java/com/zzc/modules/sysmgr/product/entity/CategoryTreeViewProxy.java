package com.zzc.modules.sysmgr.product.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 类别树实体
 * 
 * @author apple
 *11
 */
@Entity
@Table(name = "ES_VIEW_CATEGORY_TREE")
public class CategoryTreeViewProxy {

	@EmbeddedId
	private CategoryTreeView categoryTreeView;

	public CategoryTreeView getCategoryTreeView() {
		return categoryTreeView;
	}

	public void setCategoryTreeView(CategoryTreeView categoryTreeView) {
		this.categoryTreeView = categoryTreeView;
	}

}
