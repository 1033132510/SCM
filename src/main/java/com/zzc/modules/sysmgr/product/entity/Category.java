package com.zzc.modules.sysmgr.product.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zzc.common.entityListeners.Creatable;
import com.zzc.common.entityListeners.CreateIdListener;
import com.zzc.common.entityListeners.Updatable;
import com.zzc.common.entityListeners.UpdateIdListener;
import com.zzc.core.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品类别 0为顶层
 * 11
 * @author apple
 *
 */
@Entity
@Table(name = "ES_CATEGORY")
@EntityListeners({
		CreateIdListener.class,
		UpdateIdListener.class
})
public class Category extends BaseEntity implements Serializable, Creatable, Updatable {

	private static final long serialVersionUID = -4157592796178196785L;
	// 类别中文名称
	@Column(name = "cate_name")
	private String cateName;
	// 等级
	@Column(name = "level")
	private Integer level;

	@Column(name = "cate_code")
	private String categoryCode;

	// 父类类别
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "parent_category_id")
	private Category parentCategory;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_category_id")
	@JsonIgnore
	private List<Category> childCategorys;
	/**
	 * 类别 产品
	 */
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "category")
	private List<CategoryItem> productCategoryItemKeys = new ArrayList<CategoryItem>();

	// @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "categories")
	// private List<Employee> employees = new ArrayList<Employee>();

	// 创建人
	@Column(name = "create_id", updatable = false)
	private String createId;

	// 修改人
	@Column(name = "update_id")
	private String updateId;


	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	@JsonBackReference
	public List<Category> getChildCategorys() {
		return childCategorys;
	}

	public void setChildCategorys(List<Category> childCategorys) {
		this.childCategorys = childCategorys;
	}

	public void setProductCategoryItemKeys(List<CategoryItem> productCategoryItemKeys) {
		this.productCategoryItemKeys = productCategoryItemKeys;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public List<CategoryItem> getProductCategoryItemKeys() {
		return productCategoryItemKeys;
	}

	public void setProductCategoryItemKey(List<CategoryItem> productCategoryItemKeys) {
		this.productCategoryItemKeys = productCategoryItemKeys;
	}

	public String getCreateId() {
		return createId;
	}

	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getUpdateId() {
		return updateId;
	}

	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	// public List<Employee> getEmployees() {
	// return employees;
	// }
	//
	// public void setEmployees(List<Employee> employees) {
	// this.employees = employees;
	// }

}
