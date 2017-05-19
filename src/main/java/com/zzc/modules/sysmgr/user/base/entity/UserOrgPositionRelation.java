package com.zzc.modules.sysmgr.user.base.entity;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by wufan on 2015/11/12.
 */
@Entity
@Table(name = "SYS_USER_ORG_POSITION")
public class UserOrgPositionRelation {

    @EmbeddedId
    private UserOrgPositionRelationPK id;

    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    public UserOrgPositionRelationPK getId() {
        return id;
    }

    public void setId(UserOrgPositionRelationPK id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
