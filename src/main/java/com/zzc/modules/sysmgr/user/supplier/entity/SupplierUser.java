package com.zzc.modules.sysmgr.user.supplier.entity;

import com.zzc.common.entityListeners.Creatable;
import com.zzc.common.entityListeners.CreateIdListener;
import com.zzc.common.entityListeners.Updatable;
import com.zzc.common.entityListeners.UpdateIdListener;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 供应商人员
 *
 * @author ping
 */
@Entity
@Table(name = "ES_SUPPLIER_USER")
@EntityListeners({
		CreateIdListener.class,
		UpdateIdListener.class
})
public class SupplierUser extends BaseUser implements Serializable, Creatable, Updatable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7421577832312624977L;
	/**
	 * 邮箱
	 */

	@Column(name = "email", length = 255, nullable = false)
	private String email;

	/**
	 * 身份证号
	 */
	@NotBlank(message = "身份证号不能为空")
	@Length(min = 2, max = 100, message = "长度不能小于2个字符，不能超过100个字符")
	@Column(name = "idcard", length = 255, nullable = false)
	private String idcard;

	/**
	 * 部门
	 */
	@Column(name = "department", length = 100, nullable = true)
	private String department;

	/**
	 * 职位
	 */
	@Column(name = "position", length = 100, nullable = true)
	private String position;

	/**
	 * 姓名
	 */
	@Column(name = "supplier_user_name", length = 100, nullable = true)
	private String supplierUserName;

	/**
	 * 联系电话
	 */
	@NotBlank(message = "联系电话不能为空")
	@Length(min = 2, max = 100, message = "长度不能小于2个字符，不能超过100个字符")
	@Column(name = "contact_number", nullable = true, length = 30)
	private String contactNumber;

	// 创建人
	@Column(name = "create_id", updatable = false)
	private String createId;

	// 修改人
	@Column(name = "update_id")
	private String updateId;


	public String getSupplierUserName() {
		return supplierUserName;
	}

	public void setSupplierUserName(String supplierUserName) {
		this.supplierUserName = supplierUserName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * 供应商id
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	@JoinColumn(name = "supplier_org_id")
	private SupplierOrg supplierOrg;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public SupplierOrg getSupplierOrg() {
		return supplierOrg;
	}

	public void setSupplierOrg(SupplierOrg supplierOrg) {
		this.supplierOrg = supplierOrg;
	}

	public String getUpdateId() {
		return updateId;
	}

	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getCreateId() {
		return createId;
	}

	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}
}
