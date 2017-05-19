package com.zzc.modules.sysmgr.product.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zzc.core.entity.BaseEntity;


/**
 * 
 * @author famous
 *
 */
@Entity
@Table(name = "ES_PRODUCT_CATEGORY")
public class ProductCategory extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6463988860751641981L;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "es_product_id", referencedColumnName = "id")
	private ProductSKU productSKU;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "es_category_id", referencedColumnName = "id")
	private Category category;

	@Column(name = "level")
	private Integer level;

	// ProductCateTypeEnum
	@Column(name = "type")
	private Integer type;

	public ProductSKU getProductSKU() {
		return productSKU;
	}

	public void setProductSKU(ProductSKU productSKU) {
		this.productSKU = productSKU;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
