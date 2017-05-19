package com.zzc.modules.sysmgr.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * 商品检索 联合主键 
 * @author famous
 *
 */
@Embeddable
public class ProductSearchView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6064634415886695737L;

	
	@Column(name = "product_id")
	private String productId;

	@Column(name = "cate_id_grand")
	private String cateIdGrand;

	@Column(name = "cate_id_parent")
	private String cateIdParent;
	
	@Column(name = "cate_id")
	private String cateId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cateId == null) ? 0 : cateId.hashCode());
		result = prime * result + ((cateIdGrand == null) ? 0 : cateIdGrand.hashCode());
		result = prime * result + ((cateIdParent == null) ? 0 : cateIdParent.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductSearchView other = (ProductSearchView) obj;
		if (cateId == null) {
			if (other.cateId != null)
				return false;
		} else if (!cateId.equals(other.cateId))
			return false;
		if (cateIdGrand == null) {
			if (other.cateIdGrand != null)
				return false;
		} else if (!cateIdGrand.equals(other.cateIdGrand))
			return false;
		if (cateIdParent == null) {
			if (other.cateIdParent != null)
				return false;
		} else if (!cateIdParent.equals(other.cateIdParent))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

	public String getCateIdGrand() {
		return cateIdGrand;
	}

	public void setCateIdGrand(String cateIdGrand) {
		this.cateIdGrand = cateIdGrand;
	}

	public String getCateIdParent() {
		return cateIdParent;
	}

	public void setCateIdParent(String cateIdParent) {
		this.cateIdParent = cateIdParent;
	}

}
