package com.zzc.modules.sysmgr.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zzc.core.entity.BaseEntity;

/**
 * 产品&&类别 属性表 将类别属性和产品属性抽成一张表
 * 
 * @author apple
 *
 */
@Entity
@Table(name = "ES_CATEGORY_ITEM")
public class CategoryItem extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2863590809892464605L;
	// 字段属性名称
	@Column(name = "item_name")
	private String itemName;

	// 字段属性数据源
	@Column(name = "item_sources")
	private String itemsSources;

	// 是否允许自定义
	@Column(name = "allowed_custom")
	private Integer allowedCustom;

	// 是否必填
	@Column(name = "allowed_not_null")
	private Integer allowedNotNull;

	// 是否允许多选
	@Column(name = "allowed_multi_select")
	private Integer allowedMultiSelect;

	// 是否参与类别检索
	@Column(name = "allowed_cate_filter")
	private Integer allowedCateFilter;

	// 类别过滤默认显示数量
	@Column(name = "default_show_number")
	private Integer defaultShowNumber;

	// 类别编码
	@Column(name = "item_code")
	private String itemCode;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cate_id")
	private Category category;

	// 类别编码 是否可以被修改 1可以被修改 0不可以被修改
	@Column(name = "can_be_chaned")
	private Integer canBeChanged;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemsSources() {
		return itemsSources;
	}

	public void setItemsSources(String itemsSources) {
		this.itemsSources = itemsSources;
	}

	public Integer getAllowedCustom() {
		return allowedCustom;
	}

	public void setAllowedCustom(Integer allowedCustom) {
		this.allowedCustom = allowedCustom;
	}

	public Integer getAllowedNotNull() {
		return allowedNotNull;
	}

	public void setAllowedNotNull(Integer allowedNotNull) {
		this.allowedNotNull = allowedNotNull;
	}

	public Integer getAllowedMultiSelect() {
		return allowedMultiSelect;
	}

	public void setAllowedMultiSelect(Integer allowedMultiSelect) {
		this.allowedMultiSelect = allowedMultiSelect;
	}

	public Integer getAllowedCateFilter() {
		return allowedCateFilter;
	}

	public void setAllowedCateFilter(Integer allowedCateFilter) {
		this.allowedCateFilter = allowedCateFilter;
	}

	public Integer getDefaultShowNumber() {
		return defaultShowNumber;
	}

	public void setDefaultShowNumber(Integer defaultShowNumber) {
		this.defaultShowNumber = defaultShowNumber;
	}
	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getCanBeChanged() {
		return canBeChanged;
	}

	public void setCanBeChanged(Integer canBeChanged) {
		this.canBeChanged = canBeChanged;
	}

}
