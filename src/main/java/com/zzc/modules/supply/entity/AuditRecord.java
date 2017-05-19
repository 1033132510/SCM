package com.zzc.modules.supply.entity;

import com.zzc.common.entityListeners.Creatable;
import com.zzc.common.entityListeners.CreateIdListener;
import com.zzc.core.entity.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by chenjiahai on 16/1/21.
 */
@Entity
@Table(name = "SYS_AUDIT_RECORD")
@EntityListeners({
		CreateIdListener.class
})
public class AuditRecord extends BaseEntity implements Creatable {

	@Column(name = "has_tax")
	private Integer hasTax;

	@Column(name = "has_transportation")
	private Integer hasTransportation;

	// 反馈
	@Column(name = "comment")
	private String comment;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_bill_id")
	private AuditBill auditBill;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_product_sku_id")
	private SysProductSKU sysProductSKU;

	@Column(name = "create_id", updatable = false)
	private String createId;

	/**
	 * @see com.zzc.modules.sysmgr.enums.AuditRecordTypeEnum
	 */
	@Column(name = "type")
	private Integer type;
	
	private BigDecimal standard;

	public BigDecimal getStandard() {
        return standard;
    }

    public void setStandard(BigDecimal standard) {
        this.standard = standard;
    }

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getHasTax() {
		return hasTax;
	}

	public void setHasTax(Integer hasTax) {
		this.hasTax = hasTax;
	}

	public Integer getHasTransportation() {
		return hasTransportation;
	}

	public void setHasTransportation(Integer hasTransportation) {
		this.hasTransportation = hasTransportation;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public AuditBill getAuditBill() {
		return auditBill;
	}

	public void setAuditBill(AuditBill auditBill) {
		this.auditBill = auditBill;
	}

	public SysProductSKU getSysProductSKU() {
		return sysProductSKU;
	}

	public void setSysProductSKU(SysProductSKU sysProductSKU) {
		this.sysProductSKU = sysProductSKU;
	}

	public String getCreateId() {
		return createId;
	}

	@Override
	public void setCreateId(String createId) {
		this.createId = createId;
	}
}
