package com.zzc.modules.sysmgr.product.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 搜索大类
 * 
 * @author apple
 *
 */
@Entity
@Table(name = "ES_VIEW_PRODUCT_CATE_ORG")
public class ProductCategoryMainViewProxy {

	@EmbeddedId
	private ProductCategoryMainView productCategoryMainView;

	@Column(name = "status")
	private Integer status;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "create_time")
	private Date createTime = new Date();

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "modified_time")
	private Date modifiedTime = new Date();

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public ProductCategoryMainView getProductCategoryMainView() {
		return productCategoryMainView;
	}

	public void setProductCategoryMainView(ProductCategoryMainView productCategoryMainView) {
		this.productCategoryMainView = productCategoryMainView;
	}

}
