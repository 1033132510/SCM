package com.zzc.modules.sysmgr.product.web.vo;

import java.util.List;

import com.zzc.modules.sysmgr.product.entity.CategoryItem;

/**
 * 
 * @author famous
 *
 */
public class CateSearchVO {

	private String cateId;

	private String cateName;

	private List<CategoryItem> items;

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public List<CategoryItem> getItems() {
		return items;
	}

	public void setItems(List<CategoryItem> items) {
		this.items = items;
	}

}
