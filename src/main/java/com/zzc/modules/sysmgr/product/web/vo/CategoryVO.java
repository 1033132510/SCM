/**
 * 
 */
package com.zzc.modules.sysmgr.product.web.vo;

import java.util.List;

/**
 * 類別
 * @author zhangyong
 *
 */
public class CategoryVO {

	private String id;
	
	private String cateName;
	
	private String parentId;
	
	private List<CategoryVO> childs;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public List<CategoryVO> getChilds() {
		return childs;
	}
	public void setChilds(List<CategoryVO> childs) {
		this.childs = childs;
	}
	
}