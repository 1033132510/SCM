package com.zzc.modules.sysmgr.user.purchaser.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zzc.common.entityListeners.Creatable;
import com.zzc.common.entityListeners.CreateIdListener;
import com.zzc.common.entityListeners.Updatable;
import com.zzc.common.entityListeners.UpdateIdListener;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 采购商用户
 *
 * @author chenjiahai
 */
@Entity
@Table(name = "SYS_PURCHASER_USER")
@EntityListeners({
		CreateIdListener.class,
		UpdateIdListener.class
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PurchaserUser extends BaseUser implements Serializable, Creatable, Updatable {

	private static final long serialVersionUID = -1877477239865097611L;

	// 邮箱
	@Column(name = "email", nullable = false)
	private String email;

	// 姓名
	@Column(name = "name", nullable = false)
	private String name;

	// 手机
	@Column(name = "mobile", nullable = false)
	private String mobile;

	// 身份证号
	@Column(name = "identity_number", nullable = false)
	private String identityNumber;

	// 所属部门
	@Column(name = "department")
	private String department;

	// 所属职位
	@Column(name = "position")
	private String position;

	// 创建人
	@Column(name = "create_id", updatable = false)
	private String createId;

	// 修改人
	@Column(name = "update_id")
	private String updateId;


	// 所属公司
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "purchaser_id")
	private Purchaser purchaser;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
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

	public Purchaser getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(Purchaser purchaser) {
		this.purchaser = purchaser;
	}

	public String getCreateId() {
		return createId;
	}

	public String getUpdateId() {
		return updateId;
	}

	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
}
