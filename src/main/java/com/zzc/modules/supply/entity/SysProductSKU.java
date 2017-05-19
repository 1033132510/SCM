package com.zzc.modules.supply.entity;

import com.zzc.common.entityListeners.Creatable;
import com.zzc.common.entityListeners.CreateIdListener;
import com.zzc.common.entityListeners.Updatable;
import com.zzc.common.entityListeners.UpdateIdListener;
import com.zzc.core.entity.BaseEntity;
import com.zzc.modules.sysmgr.product.entity.Brand;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by chenjiahai on 16/1/18.
 */
@Entity
@Table(name = "SYS_PRODUCT_SKU")
@EntityListeners({
		CreateIdListener.class,
		UpdateIdListener.class
})
public class SysProductSKU extends BaseEntity implements Creatable, Updatable {

	private static final long serialVersionUID = 2490870983151523893L;

	@Lob
	@Column(name = "product_infos")
	private String productInfos;

	// 标价
	@Column(name = "standard")
	private BigDecimal standard;

	// 产品名称
	@Column(name = "product_name")
	private String productName;

	// 创建人
	@Column(name = "create_id")
	private String createId;

	// 修改人
	@Column(name = "update_id")
	private String updateId;

	// 供应商
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "supply_org_id", referencedColumnName = "id")
	private SupplierOrg supplierOrg;

	// 产品编号
	@Column(name = "product_code")
	private String productCode;

	// 品牌
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "brand_id", referencedColumnName = "id")
	private Brand brand;

	// 关联ES_PRODUCT_SKU表中id
	@Column(name = "product_sku_id")
	private String productSKUId;
	
	@Column(name = "second_level_cate_id")
	private String secondLevelCateId;

	public String getProductInfos() {
		return productInfos;
	}

	public void setProductInfos(String productInfos) {
		this.productInfos = productInfos;
	}

	public BigDecimal getStandard() {
		return standard;
	}

	public void setStandard(BigDecimal standard) {
		this.standard = standard;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public SupplierOrg getSupplierOrg() {
		return supplierOrg;
	}

	public void setSupplierOrg(SupplierOrg supplierOrg) {
		this.supplierOrg = supplierOrg;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getProductSKUId() {
		return productSKUId;
	}

	public void setProductSKUId(String productSKUId) {
		this.productSKUId = productSKUId;
	}

	public String getSecondLevelCateId() {
		return secondLevelCateId;
	}

	public void setSecondLevelCateId(String secondLevelCateId) {
		this.secondLevelCateId = secondLevelCateId;
	}
	
}
