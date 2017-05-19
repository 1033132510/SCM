/**
 * 
 */
package com.zzc.modules.shop.supplier.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.zzc.core.entity.BaseEntity;

/**
 * @author zhangyong
 * 供应商
 *
 */
@Entity
@Table(name = "ES_SUPPLIER")
public class ShopSupplier extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -5863186858998197150L;
	
	@Column(name = "mobile_phone")
	private String mobilePhone;

	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "user_name")
	private String username;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "brand_name")
	private String brandName;
	
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}