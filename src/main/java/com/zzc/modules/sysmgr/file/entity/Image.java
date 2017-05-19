package com.zzc.modules.sysmgr.file.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zzc.core.entity.BaseEntity;

/**
 * 图片表
 *
 * @author chenjiahai
 */
@Entity
@Table(name = "SYS_IMAGE")
public class Image extends BaseEntity {

	private static final long serialVersionUID = -4259632975723070263L;

	@Column(name = "original_name")
	private String originalName;

	@Column(name = "alias_name")
	private String aliasName;

	@Column(name = "relation_type")
	private Integer relationType;

	@Column(name = "sort_order")
	private Integer sortOrder;

	@Transient
	private String path;

	@Column(name = "relation_id")
	private String relationId;

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public Integer getRelationType() {
		return relationType;
	}

	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

}
