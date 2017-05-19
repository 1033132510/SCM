package com.zzc.modules.sysmgr.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.zzc.core.entity.BaseEntity;

/**
 * 序列号生成器
 * 
 * @author apple
 *
 */
@Entity
@Table(name = "ES_SEQUENCE_ENTITY")
public class SequenceEntity extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4899879540040624165L;

	@Column(name = "sequence_name")
	private String sequenceName;

	@Column(name = "sequence_number")
	private Integer sequenceNumber;

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

}
