package com.zzc.modules.sysmgr.user.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zzc.core.entity.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by wufan on 2015/11/12.
 */
@Entity
@Table(name = "SYS_ROLE")
public class Role extends BaseEntity implements Serializable {

    @NotBlank(message = "角色编码不能为空")
    @Column(name = "code", length = 50)
    private String roleCode;

    @NotBlank(message = "角色名称不能为空")
    @Column(name = "name", length = 200)
    private String roleName;

    @NotBlank(message = "角色类型不能为空")
    @Column(name = "type", length = 1)
    private String roleType;

    @Column(name = "description", length = 200)
    private String description;

    //@ManyToMany(mappedBy = "roleList", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_USER_ROLE", joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<BaseUser> userList;

    @Transient
    private String[] userIds;


    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BaseUser> getUserList() {
        return userList;
    }

    public void setUserList(List<BaseUser> userList) {
        this.userList = userList;
    }

    public String[] getUserIds() {
        return userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }
}
