package com.zzc.modules.sysmgr.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductCategoryMainView implements Serializable {

	/**11
	 * 
	 */
	private static final long serialVersionUID = 4786388803202815147L;

	//商品ＩＤ
	@Column(name = "product_id")
	private String productId;

	//一级类别
	@Column(name = "cate_id_grand")
	private String cateIdGrand;

	//二级类别
	@Column(name = "cate_id_parent")
	private String cateIdParent;
	
	//类别ID 
	@Column(name = "cate_id")
	private String cateId;

	//商品名称
	@Column(name = "product_name")
	private String productName;

	//供应商ＩＤ
	@Column(name = "supplier_org_id")
	private String supplierOrgId;
	
	// 品牌名中文
	@Column(name = "brand_zh_name")
	private String brandZHName;
	
	
	// 品牌名英文
	@Column(name = "brand_en_name")
	private String brandENName;
	
	//類別名稱
	@Column(name = "cate_name")
	private String cateName;
	
	// 商品编号
	@Column(name = "product_code")
	private String productCode;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSupplierOrgId() {
		return supplierOrgId;
	}

	public void setSupplierOrgId(String supplierOrgId) {
		this.supplierOrgId = supplierOrgId;
	}
	
	
	public String getCateIdGrand() {
		return cateIdGrand;
	}

	public void setCateIdGrand(String cateIdGrand) {
		this.cateIdGrand = cateIdGrand;
	}

	public String getCateIdParent() {
		return cateIdParent;
	}

	public void setCateIdParent(String cateIdParent) {
		this.cateIdParent = cateIdParent;
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

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brandENName == null) ? 0 : brandENName.hashCode());
		result = prime * result + ((brandZHName == null) ? 0 : brandZHName.hashCode());
		result = prime * result + ((cateId == null) ? 0 : cateId.hashCode());
		result = prime * result + ((cateIdGrand == null) ? 0 : cateIdGrand.hashCode());
		result = prime * result + ((cateIdParent == null) ? 0 : cateIdParent.hashCode());
		result = prime * result + ((cateName == null) ? 0 : cateName.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		result = prime * result + ((supplierOrgId == null) ? 0 : supplierOrgId.hashCode());
		result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductCategoryMainView other = (ProductCategoryMainView) obj;
		if (brandENName == null) {
			if (other.brandENName != null)
				return false;
		} else if (!brandENName.equals(other.brandENName))
			return false;
		if (brandZHName == null) {
			if (other.brandZHName != null)
				return false;
		} else if (!brandZHName.equals(other.brandZHName))
			return false;
		if (cateId == null) {
			if (other.cateId != null)
				return false;
		} else if (!cateId.equals(other.cateId))
			return false;
		if (cateIdGrand == null) {
			if (other.cateIdGrand != null)
				return false;
		} else if (!cateIdGrand.equals(other.cateIdGrand))
			return false;
		if (cateIdParent == null) {
			if (other.cateIdParent != null)
				return false;
		} else if (!cateIdParent.equals(other.cateIdParent))
			return false;
		if (cateName == null) {
			if (other.cateName != null)
				return false;
		} else if (!cateName.equals(other.cateName))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		if (supplierOrgId == null) {
			if (other.supplierOrgId != null)
				return false;
		} else if (!supplierOrgId.equals(other.supplierOrgId))
			return false;
		if (productCode == null) {
			if (other.productCode != null)
				return false;
		} else if (!productCode.equals(other.productCode))
			return false;
		return true;
	}

}
