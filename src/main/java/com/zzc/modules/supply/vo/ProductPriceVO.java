package com.zzc.modules.supply.vo;

import java.math.BigDecimal;


public class ProductPriceVO {
    
    private String unit;
    
    private BigDecimal cost;
    
    private BigDecimal recommendedPrice;
    
    private BigDecimal standard;
    
    private Integer shasTax;
    
    private String comment;
    

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getShasTax() {
        return shasTax;
    }

    public void setShasTax(Integer shasTax) {
        this.shasTax = shasTax;
    }

    public Integer getShasTransportation() {
        return shasTransportation;
    }

    public void setShasTransportation(Integer shasTransportation) {
        this.shasTransportation = shasTransportation;
    }

    private Integer shasTransportation;
    
    public String getUnit() {
        return unit;
    }

    public BigDecimal getStandard() {
        return standard;
    }

    public void setStandard(BigDecimal standard) {
        this.standard = standard;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getRecommendedPrice() {
        return recommendedPrice;
    }

    public void setRecommendedPrice(BigDecimal recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
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

    private Integer hasTax;
    
    private Integer hasTransportation;
}
