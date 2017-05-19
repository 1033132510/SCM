package com.zzc.modules.sysmgr.product.entity;

import com.zzc.common.entityListeners.Creatable;
import com.zzc.common.entityListeners.CreateIdListener;
import com.zzc.common.entityListeners.Updatable;
import com.zzc.common.entityListeners.UpdateIdListener;
import com.zzc.core.entity.BaseEntity;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品
 * 
 * @author apple
 *
 */
@Entity
@Table(name = "ES_PRODUCT_SKU")
@EntityListeners({
		CreateIdListener.class,
		UpdateIdListener.class
})
public class ProductSKU extends BaseEntity implements Serializable, Creatable, Updatable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2348874696918658256L;
	// 产品唯一编码
	@Column(name = "product_code")
	private String productCode;
	// 产品数量
	@Column(name = "product_number")
	private Integer productNumber;
	// 存储单位 打 个 。。。
	@Column(name = "unit")
	private String unit;

	@Column(name = "min_order_count")
	private Integer minOrderCount;
	// 费用备注
	@Column(name = "fee_remark")
	private String feeRemark;
	// 物流费用
	@Column(name = "fee_logistics")
	private String feeLogistics;
	// 产品名称
	@Column(name = "product_name")
	private String productName;
	// 产品名称
	@Column(name = "product_desc", columnDefinition = "text")
	private String productDesc;
	
	// 是否含税
	@Column(name = "has_tax")
	private Integer hasTax;
	// 是否含运费
	@Column(name = "has_transportation")
	private Integer hasTransportation;
	
	// 是否含税(建议售价)
	@Column(name = "has_tax_for_supplier")
	private Integer hasTaxForSupplier;
	// 是否含运费(建议售价)
	@Column(name = "has_transportation_for_supplier")
	private Integer hasTransportationForSupplier;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "supplier_org_id")
	private SupplierOrg supplierOrg;
	// 品牌ID
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "brand_id")
	private Brand brand;

	// 创建人
	@Column(name = "create_id", updatable = false)
	private String createId;

	// 修改人
	@Column(name = "update_id")
	private String updateId;


	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFeeRemark() {
		return feeRemark;
	}

	public void setFeeRemark(String feeRemark) {
		this.feeRemark = feeRemark;
	}

	public String getFeeLogistics() {
		return feeLogistics;
	}

	public void setFeeLogistics(String feeLogistics) {
		this.feeLogistics = feeLogistics;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public SupplierOrg getSupplierOrg() {
		return supplierOrg;
	}

	public void setSupplierOrg(SupplierOrg supplierOrg) {
		this.supplierOrg = supplierOrg;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
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
	
	public Integer getHasTaxForSupplier() {
		return hasTaxForSupplier;
	}

	public void setHasTaxForSupplier(Integer hasTaxForSupplier) {
		this.hasTaxForSupplier = hasTaxForSupplier;
	}

	public Integer getHasTransportationForSupplier() {
		return hasTransportationForSupplier;
	}

	public void setHasTransportationForSupplier(Integer hasTransportationForSupplier) {
		this.hasTransportationForSupplier = hasTransportationForSupplier;
	}

	public Integer getMinOrderCount() {
		return minOrderCount;
	}

	public void setMinOrderCount(Integer minOrderCount) {
		this.minOrderCount = minOrderCount;
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
}
