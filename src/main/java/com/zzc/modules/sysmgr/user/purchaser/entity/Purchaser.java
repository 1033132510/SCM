package com.zzc.modules.sysmgr.user.purchaser.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zzc.common.entityListeners.Creatable;
import com.zzc.common.entityListeners.CreateIdListener;
import com.zzc.common.entityListeners.Updatable;
import com.zzc.common.entityListeners.UpdateIdListener;
import com.zzc.modules.sysmgr.user.base.entity.BaseOrg;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 采购商
 * 
 * @author chenjiahai
 *
 */
@Entity
@Table(name = "SYS_PURCHASER")
@EntityListeners({
		CreateIdListener.class,
		UpdateIdListener.class
})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler",
		"parentPurchaser", "childrenPurchaser", "purchaserUsers", "createId", "updateId"})
public class Purchaser extends BaseOrg implements Serializable,
		Comparable<Purchaser>, Creatable, Updatable {

	private static final long serialVersionUID = -699115063028501505L;

	// 法人姓名
	@NotBlank(message = "法人姓名不能为空")
	@Column(name = "legal_name", nullable = false)
	private String legalName;

	// 公司座机
	@Column(name = "tel")
	@NotBlank(message = "公司座机不能为空")
	private String tel;

	// 级别：1、2、3级
	@Column(name = "level", nullable = false)
	private Integer level;

	// 创建人
	@Column(name = "create_id", updatable = false)
	private String createId;

	// 修改人
	@Column(name = "update_id")
	private String updateId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "purchaser")
	private Set<PurchaserUser> purchaserUsers = new HashSet<>(0);

	// 父采购商
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_purchaser_id")
	private Purchaser parentPurchaser;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentPurchaser")
	private Set<Purchaser> childrenPurchaser = new HashSet<>(0);

	public Purchaser getParentPurchaser() {
		return parentPurchaser;
	}

	public void setParentPurchaser(Purchaser parentPurchaser) {
		this.parentPurchaser = parentPurchaser;
	}

	public Set<Purchaser> getChildrenPurchaser() {
		return childrenPurchaser;
	}

	public void setChildrenPurchaser(Set<Purchaser> childrenPurchaser) {
		this.childrenPurchaser = childrenPurchaser;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Set<PurchaserUser> getPurchaserUsers() {
		return purchaserUsers;
	}

	public void setPurchaserUsers(Set<PurchaserUser> purchaserUsers) {
		this.purchaserUsers = purchaserUsers;
	}

	public String getCreateId() {
		return createId;
	}

	public String getUpdateId() {
		return updateId;
	}

	@Override
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}



	@Override
	public int compareTo(Purchaser other) {
		return this.getOrgCode().compareToIgnoreCase(other.getOrgCode());
	}

}
