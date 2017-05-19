package com.zzc.modules.sysmgr.user.base.entity;

import com.zzc.modules.sysmgr.user.employee.entity.Employee;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * Created by wufan on 2015/11/12.
 */
@SuppressWarnings("serial")
@Embeddable
public class UserOrgPositionRelationPK implements Serializable {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id")
    private CompanyOrg companyOrg;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id")
    private Position position;


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public CompanyOrg getCompanyOrg() {
        return companyOrg;
    }

    public void setCompanyOrg(CompanyOrg companyOrg) {
        this.companyOrg = companyOrg;
    }
}
