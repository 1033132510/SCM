package com.zzc.modules.businesschain.supply;

import java.io.Serializable;
/**
 * 上游子订单
 * @author ping
 *
 */
public class UpstreamSonPurchaseOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5586530965233890631L;

	/**
	 * 上游子订单编号
	 */
	private String orderCode;
	
	/**
	 * 上游总采购单号
	 */
	private String totalOrderCode;
	
	/**
	 * 供应商
	 */
	private String supplier;
	
	/**
	 * 打折
	 */
	private String discount;
	/**
	 * 状态
	 */
	private Integer status;
	
	
}
