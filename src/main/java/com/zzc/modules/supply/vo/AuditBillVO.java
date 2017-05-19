package com.zzc.modules.supply.vo;

import java.math.BigDecimal;
import java.util.Date;

public class AuditBillVO {

    private String id;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String productCode;
    
    private String productName;
    
    private String brandName;
    
    private BigDecimal cost;
    
    private String auditStatus;
    
    private Date createTime;
    
    private String approverName;
    
    private String approverNumber;
    
    private String supplyOrgName;
    
    private String createName;
    
    private Date modifiedTime;
    
    private BigDecimal standard;
    
    private BigDecimal recommend;

    public BigDecimal getRecommend() {
        return recommend;
    }

    public void setRecommend(BigDecimal recommend) {
        this.recommend = recommend;
    }

    public BigDecimal getStandard() {
        return standard;
    }

    public void setStandard(BigDecimal standard) {
        this.standard = standard;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getSupplyOrgName() {
        return supplyOrgName;
    }

    public void setSupplyOrgName(String supplyOrgName) {
        this.supplyOrgName = supplyOrgName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

}
