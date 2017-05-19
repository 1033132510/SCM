package com.zzc.modules.sysmgr.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.common.dao.SequenceEntityDao;
import com.zzc.modules.sysmgr.common.entity.SequenceEntity;
import com.zzc.modules.sysmgr.common.service.SequenceEntityService;

@Service("sequenceEntityService")
public class SequenceEntityServiceImpl extends BaseCrudServiceImpl<SequenceEntity> implements SequenceEntityService {

	@SuppressWarnings("unused")
	private SequenceEntityDao sequenceEntityDao;

	@Autowired
	public SequenceEntityServiceImpl(BaseDao<SequenceEntity> sequenceEntityDao) {
		super(sequenceEntityDao);
		this.sequenceEntityDao = (SequenceEntityDao) sequenceEntityDao;
	}

	@Override
	public SequenceEntity findSequenceEntityBySequenceName(String sequenceName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AND_EQ_sequenceName", sequenceName);
		List<SequenceEntity> findAll = findAll(map, SequenceEntity.class);
		if (findAll != null && findAll.size() == 1) {
			return findAll.get(0);
		}
		return null;
	}
}
