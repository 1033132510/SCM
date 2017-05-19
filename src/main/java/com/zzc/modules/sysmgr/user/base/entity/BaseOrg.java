
package com.zzc.modules.sysmgr.user.base.entity;

import com.zzc.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 组织基类 Created by wufan on 2015/11/12.
 */
@MappedSuperclass
public class BaseOrg extends BaseEntity {

	/**
	 * 组织编码，系统自动生成
	 */
	@Column(name = "org_code", unique = true, length = 30)
	private String orgCode;

	/**
	 * 组织名称
	 */
	@Column(name = "org_name", nullable = false, length = 100)
	private String orgName;

	/**
	 * 组织类型；1：本公司；2：采购商；3：供应商
	 */
	@Column(name = "org_type", length = 1)
	private String orgType;

	/**
	 * 组织级别：总部("hq", "总部"), 总部部门("hqo", "总部部门"), 子公司("hqc", "子公司"),
	 * 子公司部门("hqco", "子公司部门");
	 */
	@Column(name = "org_class", length = 10)
	private String orgClass;

	// @Column(name = "is_commpany", length = 1)
	// private Boolean isCompany;

	/**
	 * logo地址
	 */
	@Column(name = "logo", nullable = true, length = 100)
	private String logo;

	/**
	 * 父ID
	 */
	/*
	 * @Column(name = "parent_id", nullable = true, length = 32) private String
	 * parentId;
	 */

	/*
	 * @ManyToOne(cascade = CascadeType.DETACH)
	 * 
	 * @JoinColumn(name = "parent_id") private BaseOrg parentOrg;
	 * 
	 * @OneToMany(cascade = CascadeType.DETACH)
	 * 
	 * @JoinColumn(name = "parent_id") private Set<BaseOrg> sugOrgs;
	 */

	/**
	 * 组织层级,顶级为1
	 */
	@Column(name = "org_level", nullable = true)
	private Integer orgLevel;

	/**
	 * default constructor
	 */
	public BaseOrg() {
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getOrgClass() {
		return orgClass;
	}

	public void setOrgClass(String orgClass) {
		this.orgClass = orgClass;
	}
}