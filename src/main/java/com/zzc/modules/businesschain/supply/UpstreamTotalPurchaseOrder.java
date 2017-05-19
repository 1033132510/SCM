package com.zzc.modules.businesschain.supply;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 上游总采购单
 * 
 * @author ping
 *
 */
public class UpstreamTotalPurchaseOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2147854322082623320L;

	/**
	 * 采购单号
	 */
	private String orderCode;

	/**
	 * 下游采购商
	 */
	private String downstreamPurchase;

	/**
	 * 订单时间
	 */
	private Date orderTime;

	/**
	 * 状态
	 */
	private Integer status;

}
