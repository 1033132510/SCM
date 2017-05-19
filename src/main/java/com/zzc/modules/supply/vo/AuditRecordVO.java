package com.zzc.modules.supply.vo;

import java.util.Date;

public class AuditRecordVO {

    private String id;

    // 审批人
    private String approverName;

    // 审批人电话
    private String approverNumber;
    
    private Date createTime;
    
    private String comment;
    
	private String createName;
	
	private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getApproverNumber() {
        return approverNumber;
    }

    public void setApproverNumber(String approverNumber) {
        this.approverNumber = approverNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
