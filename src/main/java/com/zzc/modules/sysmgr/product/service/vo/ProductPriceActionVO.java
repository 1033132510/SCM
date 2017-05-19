package com.zzc.modules.sysmgr.product.service.vo;

import java.util.Date;

/**
 * 商品价格修改记录VO
 *
 * @author chenjiahai
 */
public class ProductPriceActionVO {

	private String standard;

	private String cost;

	private String level1;

	private String level2;

	private String level3;

	private Integer hasTax;

	private Integer hasTransportation;

	private String operatorName;

	private Integer modCount;

	private Date createTime;

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getLevel1() {
		return level1;
	}

	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	public String getLevel2() {
		return level2;
	}

	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	public String getLevel3() {
		return level3;
	}

	public void setLevel3(String level3) {
		this.level3 = level3;
	}

	public Integer getHasTransportation() {
		return hasTransportation;
	}

	public void setHasTransportation(Integer hasTransportation) {
		this.hasTransportation = hasTransportation;
	}

	public Integer getHasTax() {
		return hasTax;
	}

	public void setHasTax(Integer hasTax) {
		this.hasTax = hasTax;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getModCount() {
		return modCount;
	}

	public void setModCount(Integer modCount) {
		this.modCount = modCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
