package com.zzc.modules.businesschain.supply;

import java.io.Serializable;
/**
 * 上游采购商品
 * @author ping
 *
 */
public class UpstreamPurchaseProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7060691983607047237L;

	/**
	 * 上游子订单编号
	 */
	private String sonOrderCode;
	
	/**
	 * 商品编号
	 */
	private String productCode;
	
	/**
	 * 数量
	 */
	private double count;
	
	/**
	 * 价格
	 */
	private double price;
	
	
}
