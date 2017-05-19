package com.zzc.modules.sysmgr.user.purchaser.dto;

import org.hibernate.validator.constraints.NotBlank;

public class SavePurchaserRequestDTO {

	private String id;

	@NotBlank(message = "公司名称不能为空")
	private String orgName;

	@NotBlank(message = "法人名称不能为空")
	private String legalName;

	private Integer level;

	@NotBlank(message = "公司座机不能为空")
	private String tel;

	private String[] imageIds;

	private String[] deleteImageIds;

	private String parentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String[] getDeleteImageIds() {
		return deleteImageIds;
	}

	public void setDeleteImageIds(String[] deleteImageIds) {
		this.deleteImageIds = deleteImageIds;
	}

	public String[] getImageIds() {
		return imageIds;
	}

	public void setImageIds(String[] imageIds) {
		this.imageIds = imageIds;
	}

}
