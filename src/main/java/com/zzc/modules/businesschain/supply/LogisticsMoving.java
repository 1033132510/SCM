package com.zzc.modules.businesschain.supply;

import java.io.Serializable;
/**
 * 物流流转
 * @author ping
 *
 */
import java.util.Date;
public class LogisticsMoving implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8277832454996163957L;

	/**
	 * 物流单号
	 */
	private String logisticsSingleNO;
	
	/**
	 * 类型 如到达、离开
	 */
	private String type;
	
	/**
	 * 当前站点
	 */
	private String site;
	
	/**
	 * 下级站点
	 */
	private String lowerSite;
	
	/**
	 * 联系人
	 */
	private String contacts;
	
	/**
	 * 联系电话
	 * 
	 */
	private String contactNumber;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 运输方式
	 */
	private String transportation;
	
	
}
