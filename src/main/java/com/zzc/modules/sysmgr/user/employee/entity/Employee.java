package com.zzc.modules.sysmgr.user.employee.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysql.fabric.xmlrpc.base.Array;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.CategoryCharge;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;

/**
 * 公司员工信息 Created by hadoop on 2015/11/10.
 */
@Entity
@Table(name = "SYS_EMPLOYEE")
// @PrimaryKeyJoinColumn(name = "EMPLOYEE_ID")
public class Employee extends BaseUser implements Serializable {

	private static final long serialVersionUID = -333679122696496289L;

	/** 姓名 */
	@NotBlank(message = "用户姓名不能为空")
	@Length(min = 2, max = 20, message = "长度不能小于2个字符，不能超过20个字符")
	@Column(name = "name", length = 20)
	private String employeeName;
	/** 性别 0：女，1：男 */
	@Column(name = "sex", length = 1)
	private String sex;
	/** 手机 */
	@NotBlank(message = "手机号不能为空")
	@Column(name = "mobile", length = 20)
	private String mobile;
	/** 座机 */
	@Column(name = "telephone", length = 20)
	private String telephone
	/** 邮箱 */
	;
	@Column(name = "email", length = 50)
	private String email;
	/** 工作地点 */
	@Column(name = "workplace", length = 100)
	private String workplace;
	/** 工号 */
	@Column(name = "employee_no", length = 50)
	private String employeeNO;
	/** 入职日期 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Column(name = "entry_date")
	private Date entryDate;
	/** 员工在职状态 */
	@Column(name = "employee_status")
	private String employeeStatus;
	/** 员工类型 */
	@Column(name = "employee_type")
	private String employeeType;
	/** qq号 */
	@Column(name = "qq")
	private String qq;
	/** 微信号 */
	@Column(name = "weixin")
	private String weixin;
	/** 身份证号 */
	@Column(name = "identity_card")
	private String identityCard;

//	@ManyToMany(cascade = CascadeType.REFRESH)
//	@JoinTable(name = "ES_CATEGORY_CHARGE", inverseJoinColumns = @JoinColumn(name = "category_id") , joinColumns = @JoinColumn(name = "employee_id") )
//	private List<Category> categories = new ArrayList<Category>();
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
	private List<CategoryCharge> categoryCharges = new ArrayList<CategoryCharge>();

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getEmployeeNO() {
		return employeeNO;
	}

	public void setEmployeeNO(String employeeNO) {
		this.employeeNO = employeeNO;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public List<CategoryCharge> getCategoryCharges() {
		return categoryCharges;
	}

	public void setCategoryCharges(List<CategoryCharge> categoryCharges) {
		this.categoryCharges = categoryCharges;
	}

//	public List<Category> getCategories() {
//		return categories;
//	}
//
//	public void setCategories(List<Category> categories) {
//		this.categories = categories;
//	}
	

}