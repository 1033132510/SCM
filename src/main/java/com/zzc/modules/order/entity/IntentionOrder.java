package com.zzc.modules.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zzc.core.entity.BaseEntity;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.purchaser.entity.PurchaserUser;

/**
 * 意向单
 * 
 * @author chenjiahai
 *
 */
@Entity
@Table(name = "ES_INTENTION_ORDER")
@JsonIgnoreProperties({ "intentionOrderItems" })
public class IntentionOrder extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7499592602807557366L;

	// 项目名称
	@Column(name = "project_name")
	private String projectName;

	// 项目备注
	@Column(name = "project_desc")
	private String projectDescription;

	// 预计需求期
	@Column(name = "demand_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date demandDate;

	// 意向单编号
	@Column(name = "order_code", nullable = false)
	private String orderCode;

	// 意向单状态: 1待办，0已办
	@Column(name = "order_status")
	private Integer orderStatus;

	// 派发和接单时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "allot_time")
	private Date allotTime;

	// 下单人
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "purchaser_user_id", referencedColumnName = "id")
	private PurchaserUser purchaserUser;

	// 接单人
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "employee_id", referencedColumnName = "id")
	private Employee employee;

	// 运营主管姓名
	@Column(name = "coo_name")
	private String cooName;

	// 下单人姓名
	@Column(name = "create_order_name")
	private String createOrderName;

	// 下单人联系方式
	@Column(name = "create_order_mobile")
	private String createOrderMobile;

	// 订单总金额
	@Column(name = "total_price")
	private BigDecimal totalPrice;

	// 意向单项
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "intentionOrder")
	private List<IntentionOrderItem> intentionOrderItems;

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCreateOrderName() {
		return createOrderName;
	}

	public void setCreateOrderName(String createOrderName) {
		this.createOrderName = createOrderName;
	}

	public String getCreateOrderMobile() {
		return createOrderMobile;
	}

	public void setCreateOrderMobile(String createOrderMobile) {
		this.createOrderMobile = createOrderMobile;
	}

	public String getCooName() {
		return cooName;
	}

	public void setCooName(String cooName) {
		this.cooName = cooName;
	}

	public Date getAllotTime() {
		return allotTime;
	}

	public void setAllotTime(Date allotTime) {
		this.allotTime = allotTime;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public Date getDemandDate() {
		return demandDate;
	}

	public void setDemandDate(Date demandDate) {
		this.demandDate = demandDate;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public PurchaserUser getPurchaserUser() {
		return purchaserUser;
	}

	public void setPurchaserUser(PurchaserUser purchaserUser) {
		this.purchaserUser = purchaserUser;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<IntentionOrderItem> getIntentionOrderItems() {
		return intentionOrderItems;
	}

	public void setIntentionOrderItems(
			List<IntentionOrderItem> intentionOrderItems) {
		this.intentionOrderItems = intentionOrderItems;
	}

}
