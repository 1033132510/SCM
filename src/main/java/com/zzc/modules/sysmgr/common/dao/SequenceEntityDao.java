package com.zzc.modules.sysmgr.common.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.common.entity.SequenceEntity;

/**
 * 1
 * 
 * @author apple
 *
 */
public interface SequenceEntityDao extends BaseDao<SequenceEntity> {
	
	@Query("from SequenceEntity se where se.sequenceName = :key")
	public SequenceEntity getValueByKey(@Param("key")String key);
	
	@Query("update SequenceEntity se set se.sequenceNumber = :value where se.sequenceName = :key")
	@Modifying
	public void updateValue(@Param("key")String key, @Param("value")Integer value);

}