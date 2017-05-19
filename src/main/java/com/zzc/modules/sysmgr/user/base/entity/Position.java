package com.zzc.modules.sysmgr.user.base.entity;

import com.zzc.core.entity.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by wufan on 2015/11/12.
 */
@Entity
@Table(name = "SYS_POSITION")
public class Position extends BaseEntity {

    @Column(name = "code", length = 50)
    private String positionCode;
    @NotBlank(message = "岗位名称不能为空")
    @Column(name = "name", length = 200)
    private String positionName;
    @Column(name = "type", length = 1)
    private String positionType;
    @Column(name = "description", length = 200)
    private String description;

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
