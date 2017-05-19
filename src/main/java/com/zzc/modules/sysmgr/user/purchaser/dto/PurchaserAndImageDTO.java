package com.zzc.modules.sysmgr.user.purchaser.dto;

import com.zzc.common.zTree.ZTreeNode;

public class PurchaserAndImageDTO {

	private ZTreeNode node;

	private String id;

	private String parentId;

	private String legalName;

	private String orgName;

	private String orgCode;

	private Integer level;

	private String licencePath;

	private String licenceId;

	private Integer licenceRelationType;

	private String codeLicencePath;

	private String codeLicenceId;

	private Integer codeLicenceRelationType;

	private String taxRegistrationPath;

	private String taxRegistrationId;

	private Integer taxRegistrationRelationType;

	private String constructionQualificationPath;

	private String constructionQualificationId;

	private Integer constructionQualificationRelationType;

	private String tel;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public ZTreeNode getNode() {
		return node;
	}

	public void setNode(ZTreeNode node) {
		this.node = node;
	}

	public Integer getLicenceRelationType() {
		return licenceRelationType;
	}

	public void setLicenceRelationType(Integer licenceRelationType) {
		this.licenceRelationType = licenceRelationType;
	}

	public Integer getCodeLicenceRelationType() {
		return codeLicenceRelationType;
	}

	public void setCodeLicenceRelationType(Integer codeLicenceRelationType) {
		this.codeLicenceRelationType = codeLicenceRelationType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getLicencePath() {
		return licencePath;
	}

	public void setLicencePath(String licencePath) {
		this.licencePath = licencePath;
	}

	public String getCodeLicencePath() {
		return codeLicencePath;
	}

	public void setCodeLicencePath(String codeLicencePath) {
		this.codeLicencePath = codeLicencePath;
	}

	public String getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(String licenceId) {
		this.licenceId = licenceId;
	}

	public String getCodeLicenceId() {
		return codeLicenceId;
	}

	public void setCodeLicenceId(String codeLicenceId) {
		this.codeLicenceId = codeLicenceId;
	}

	public String getTaxRegistrationPath() {
		return taxRegistrationPath;
	}

	public void setTaxRegistrationPath(String taxRegistrationPath) {
		this.taxRegistrationPath = taxRegistrationPath;
	}

	public String getTaxRegistrationId() {
		return taxRegistrationId;
	}

	public void setTaxRegistrationId(String taxRegistrationId) {
		this.taxRegistrationId = taxRegistrationId;
	}

	public Integer getTaxRegistrationRelationType() {
		return taxRegistrationRelationType;
	}

	public void setTaxRegistrationRelationType(
			Integer taxRegistrationRelationType) {
		this.taxRegistrationRelationType = taxRegistrationRelationType;
	}

	public String getConstructionQualificationPath() {
		return constructionQualificationPath;
	}

	public void setConstructionQualificationPath(
			String constructionQualificationPath) {
		this.constructionQualificationPath = constructionQualificationPath;
	}

	public String getConstructionQualificationId() {
		return constructionQualificationId;
	}

	public void setConstructionQualificationId(
			String constructionQualificationId) {
		this.constructionQualificationId = constructionQualificationId;
	}

	public Integer getConstructionQualificationRelationType() {
		return constructionQualificationRelationType;
	}

	public void setConstructionQualificationRelationType(
			Integer constructionQualificationRelationType) {
		this.constructionQualificationRelationType = constructionQualificationRelationType;
	}

}
