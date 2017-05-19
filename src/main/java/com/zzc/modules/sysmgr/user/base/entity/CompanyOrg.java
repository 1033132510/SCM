package com.zzc.modules.sysmgr.user.base.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by wufan on 2015/11/17.
 */
@Entity
@Table(name = "SYS_COMPANY_ORG")
public class CompanyOrg extends BaseOrg implements Serializable {

    private static final long serialVersionUID = -3457589796178191235L;

    @Column(name = "description", length = 200)
    private String description;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "parent_id")
    private CompanyOrg parentOrg;

    @Column(name = "phone", length = 20)
    private String phone;

    /**
     * 子组织
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.DETACH)
    @JoinColumn(name = "parent_id")
    private Set<CompanyOrg> subOrgs;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public CompanyOrg getParentOrg() {
        return parentOrg;
    }

    public void setParentOrg(CompanyOrg parentOrg) {
        this.parentOrg = parentOrg;
    }

    public Set<CompanyOrg> getSubOrgs() {
        return subOrgs;
    }

    public void setSubOrgs(Set<CompanyOrg> subOrgs) {
        this.subOrgs = subOrgs;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
