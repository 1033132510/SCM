package com.zzc.modules.sysmgr.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzc.common.entityListeners.Creatable;
import com.zzc.common.entityListeners.CreateIdListener;
import com.zzc.common.entityListeners.Updatable;
import com.zzc.common.entityListeners.UpdateIdListener;
import com.zzc.core.entity.BaseEntity;
import com.zzc.core.security.XssIgnoreSerializer;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 品牌
 * 11
 *
 * @author apple
 */
@Entity
@Table(name = "ES_BRAND")
@EntityListeners({
		CreateIdListener.class,
		UpdateIdListener.class
})
public class Brand extends BaseEntity implements Serializable, Creatable, Updatable {

	private static final long serialVersionUID = -1075015312479217691L;

	// 品牌名中文
	@Column(name = "brand_zh_name", unique = true)
	private String brandZHName;
	// 品牌名英文
	@Column(name = "brand_en_name", unique = true)
	private String brandENName;
	// 品牌描述
	@JsonSerialize(using = XssIgnoreSerializer.class)
	@Column(name = "brand_desc", columnDefinition = "text")
	private String brandDesc;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "org_id", referencedColumnName = "id")
	private SupplierOrg supplierOrg;

	// 创建人
	@Column(name = "create_id", updatable = false)
	private String createId;

	// 修改人
	@Column(name = "update_id")
	private String updateId;


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

	public String getBrandDesc() {
		return brandDesc;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}

	public SupplierOrg getSupplierOrg() {
		return supplierOrg;
	}

	public void setSupplierOrg(SupplierOrg supplierOrg) {
		this.supplierOrg = supplierOrg;
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
