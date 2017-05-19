package com.zzc.modules.sysmgr.common.service;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.common.entity.SequenceEntity;

public interface SequenceEntityService extends BaseCrudService<SequenceEntity> {

	/**
	 * 根据编码名称得到编码数字
	 * 
	 * @param sequenceName
	 * @return
	 */
	public SequenceEntity findSequenceEntityBySequenceName(String sequenceName);
	
}
