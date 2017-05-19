package com.zzc.modules.sysmgr.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.zzc.core.entity.BaseEntity;

/**
 * 商品价格修改记录表
 * 
 * @author chenjiahai
 *
 */
@Entity
@Table(name = "ES_PRODUCT_PRICE_ACTION")
public class ProductPriceAction extends BaseEntity {

	private static final long serialVersionUID = 4358352870458927823L;

	// 产品Id
	@Column(name = "product_sku_id")
	private String productSkuId;

	// 产品类别Id
	@Column(name = "product_category_id")
	private String productCategoryId;

	// 修改记录
	@Column(name = "modify_record")
	@Lob
	private String modifyRecord;

	public String getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(String productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getModifyRecord() {
		return modifyRecord;
	}

	public void setModifyRecord(String modifyRecord) {
		this.modifyRecord = modifyRecord;
	}

}
