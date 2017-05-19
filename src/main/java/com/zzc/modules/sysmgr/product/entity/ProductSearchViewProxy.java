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
@Table(name = "ES_VIEW_PRODUCT_SEARCH")
public class ProductSearchViewProxy {

	@EmbeddedId
	private ProductSearchView brandCategoryView;

	@Column(name = "cate_code")
	private String cateCode;

	@Column(name = "supplier_org_id")
	private String supplierOrgId;

	@Column(name = "status")
	private Integer status;

	@Column(name = "level")
	private Integer level;

	@Column(name = "cate_name")
	private String cateName;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "brand_zh_name")
	private String brandZHName;

	@Column(name = "brand_en_name")
	private String brandENName;

	@Column(name = "brand_id")
	private String brandId;

	@Column(name = "item_code")
	private String itemCode;

	@Column(name = "value")
	private String value;
	
	@Column(name = "has_tax")
    private Integer hasTax;
	
	@Column(name = "has_transportation")
    private Integer hasTransportation;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "create_time")
	private Date createTime = new Date();

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "modified_time")
	private Date modifiedTime = new Date();

	public ProductSearchView getBrandCategoryView() {
		return brandCategoryView;
	}

	public void setBrandCategoryView(ProductSearchView brandCategoryView) {
		this.brandCategoryView = brandCategoryView;
	}

	public String getSupplierOrgId() {
		return supplierOrgId;
	}

	public void setSupplierOrgId(String supplierOrgId) {
		this.supplierOrgId = supplierOrgId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrandZHName() {
		return brandZHName;
	}

	public void setBrandZHName(String brandZHName) {
		this.brandZHName = brandZHName;
	}

	public String getBrandENName() {
		return brandENName;
	}

	public void setBrandENName(String brandENName) {
		this.brandENName = brandENName;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCateCode() {
		return cateCode;
	}

	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
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

    public Integer getHasTax() {
        return hasTax;
    }

    public void setHasTax(Integer hasTax) {
        this.hasTax = hasTax;
    }

    public Integer getHasTransportation() {
        return hasTransportation;
    }

    public void setHasTransportation(Integer hasTransportation) {
        this.hasTransportation = hasTransportation;
    }

}
