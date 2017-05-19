package com.zzc.modules.sysmgr.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 类别树联合索引 从1级到2级到3级的联合结构
 * 
 * @author apple
 *11
 */
@Embeddable
public class CategoryTreeView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "lev1_id")
	private String lev1Id;

	@Column(name = "lev2_id")
	private String lev2Id;

	@Column(name = "lev3_id")
	private String lev3Id;

	public String getLev1Id() {
		return lev1Id;
	}

	public void setLev1Id(String lev1Id) {
		this.lev1Id = lev1Id;
	}

	public String getLev2Id() {
		return lev2Id;
	}

	public void setLev2Id(String lev2Id) {
		this.lev2Id = lev2Id;
	}

	public String getLev3Id() {
		return lev3Id;
	}

	public void setLev3Id(String lev3Id) {
		this.lev3Id = lev3Id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lev1Id == null) ? 0 : lev1Id.hashCode());
		result = prime * result + ((lev2Id == null) ? 0 : lev2Id.hashCode());
		result = prime * result + ((lev3Id == null) ? 0 : lev3Id.hashCode());
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
		CategoryTreeView other = (CategoryTreeView) obj;
		if (lev1Id == null) {
			if (other.lev1Id != null)
				return false;
		} else if (!lev1Id.equals(other.lev1Id))
			return false;
		if (lev2Id == null) {
			if (other.lev2Id != null)
				return false;
		} else if (!lev2Id.equals(other.lev2Id))
			return false;
		if (lev3Id == null) {
			if (other.lev3Id != null)
				return false;
		} else if (!lev3Id.equals(other.lev3Id))
			return false;
		return true;
	}

}
