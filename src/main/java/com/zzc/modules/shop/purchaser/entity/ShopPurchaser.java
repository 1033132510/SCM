/**
 * 
 */
package com.zzc.modules.shop.purchaser.entity;

import com.zzc.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zhangyong
 * 采购商
 */
@Entity
@Table(name = "ES_PURCHASER")
public class ShopPurchaser extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -9188133719202558792L;

	@Column(name = "mobile_phone")
	private String mobilePhone;
	
	@Column(name = "username")
	private String username;
	
	// 身份证
	@Column(name = "id_card")
	private String idCard;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "department_name")
	private String departmentName;

	// 职位
	@Column(name = "brand_en_name")
	private String position;
/*	// 等级
	@Column(name = "grade")
	private String grade;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}*/

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}