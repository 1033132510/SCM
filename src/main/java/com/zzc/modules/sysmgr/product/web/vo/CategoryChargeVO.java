/**
 * 
 */
package com.zzc.modules.sysmgr.product.web.vo;

/**
 * @author zhangyong
 *
 */
public class CategoryChargeVO {
	
	private String id;
	// 一级或二级类别id
	private String secondLevelCategoryId;
	private String employeeId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSecondLevelCategoryId() {
		return secondLevelCategoryId;
	}
	public void setSecondLevelCategoryId(String secondLevelCategoryId) {
		this.secondLevelCategoryId = secondLevelCategoryId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
}