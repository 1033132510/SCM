package com.zzc.modules.businesschain.supply;

import java.io.Serializable;
import java.util.Date;

/**
 * 物流
 * 
 * @author ping
 *
 */
public class Logistics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1082777527653415533L;

	/**
	 * 物流单号
	 */
	private String logisticsSingleNO;

	/**
	 * 上游子订单号
	 */
	private String sonOrderCode;

	/**
	 * 目的地
	 */
	private String destination;

	/**
	 * 始发地
	 */
	private String origin;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 物品
	 */
	private String products;

	/**
	 * 运费
	 */
	private double freight;

}
