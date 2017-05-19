package com.zzc.modules.supply.entity;

import com.zzc.common.entityListeners.Creatable;
import com.zzc.common.entityListeners.CreateIdListener;
import com.zzc.core.entity.BaseEntity;
import com.zzc.modules.sysmgr.enums.AuditBillOperationTypeEnum;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;

import javax.persistence.*;

/**
 * Created by chenjiahai on 16/1/21.
 */
@Entity
@Table(name = "SYS_AUDIT_BILL")
@EntityListeners({
		CreateIdListener.class
})
public class AuditBill extends BaseEntity implements Creatable {

	/**
     * 
     */
    private static final long serialVersionUID = -7025058354041466729L;

	// 创建人
	@Column(name = "create_id", updatable = false)
	private String createId;

	/**
	 * @see com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum
	 */
	@Column(name = "audit_status")
	private Integer auditStatus;

	/**
	 * @see AuditBillOperationTypeEnum
	 */
	@Column(name = "operation_type")
	private Integer operationType;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_product_sku_id",referencedColumnName = "id")
	private SysProductSKU sysProductSKU;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_admin_id", referencedColumnName = "id")
    private Employee adminEmployee;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_director_id", referencedColumnName = "id")
    private Employee directorEmployee;


    public Employee getAdminEmployee() {
        return adminEmployee;
    }

    public void setAdminEmployee(Employee adminEmployee) {
        this.adminEmployee = adminEmployee;
    }

    public Employee getDirectorEmployee() {
        return directorEmployee;
    }

    public void setDirectorEmployee(Employee directorEmployee) {
        this.directorEmployee = directorEmployee;
    }

    public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
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
