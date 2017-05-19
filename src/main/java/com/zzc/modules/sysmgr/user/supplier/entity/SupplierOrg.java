package com.zzc.modules.sysmgr.user.supplier.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzc.common.entityListeners.Creatable;
import com.zzc.common.entityListeners.CreateIdListener;
import com.zzc.common.entityListeners.Updatable;
import com.zzc.common.entityListeners.UpdateIdListener;
import com.zzc.core.security.XssIgnoreSerializer;
import com.zzc.modules.sysmgr.user.base.entity.BaseOrg;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 供应商
 * 
 * @author ping
 *
 */
@Entity
@Table(name = "ES_SUPPLIER_ORG")
@EntityListeners({
		CreateIdListener.class,
		UpdateIdListener.class
})
public class SupplierOrg extends BaseOrg implements Serializable, Creatable, Updatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7907876671016534251L;

	/**
	 * 法人
	 */
	@NotBlank(message = "法人不能为空")
	@Length(min = 2, max = 100, message = "长度不能小于2个字符，不能超过100个字符")
	@Column(name = "legal_person", nullable = true, length = 255)
	private String legalPerson;

	/**
	 * 手机号码
	 */
	@Column(name = "mobile_number", nullable = true, length = 30)
	private String mobileNumber;

	/**
	 * 固定号码
	 */
	@Column(name = "tel_Number", nullable = true, length = 30)
	private String telNumber;

	/**
	 * 联系人
	 */
	@Column(name = "contact", nullable = true, length = 255)
	private String contact;

	/**
	 * 公司介绍text
	 */
	@Column(name = "supplier_org_introduction_text", nullable = true, columnDefinition = "text")
	private String supplierOrgIntroductionText;

	// 创建人
	@Column(name = "create_id", updatable = false)
	private String createId;

	// 修改人
	@Column(name = "update_id")
	private String updateId;

	public String getSupplierOrgIntroductionText() {
		return supplierOrgIntroductionText;
	}

	public void setSupplierOrgIntroductionText(String supplierOrgIntroductionText) {
		this.supplierOrgIntroductionText = supplierOrgIntroductionText;
	}

	public String getSupplierOrgIntroductionHtml() {
		return supplierOrgIntroductionHtml;
	}

	public void setSupplierOrgIntroductionHtml(String supplierOrgIntroductionHtml) {
		this.supplierOrgIntroductionHtml = supplierOrgIntroductionHtml;
	}

	/**
	 * 公司介绍html
	 */
	@JsonSerialize(using = XssIgnoreSerializer.class)
	@Column(name = "supplier_org_introduction_html", nullable = true, columnDefinition = "text")
	private String supplierOrgIntroductionHtml;

	@Column(name = "supplier_type", nullable = true, length = 1)
	// @Enumerated(EnumType.ORDINAL)
	private int supplierType;

	public int getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(int supplierType) {
		this.supplierType = supplierType;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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