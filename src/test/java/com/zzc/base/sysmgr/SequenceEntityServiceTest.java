package com.zzc.base.sysmgr;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.common.dao.SequenceEntityDao;
import com.zzc.modules.sysmgr.common.entity.SequenceEntity;
import com.zzc.modules.sysmgr.common.service.SequenceEntityService;
import com.zzc.modules.sysmgr.product.entity.Brand;
import com.zzc.modules.sysmgr.product.service.BrandService;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.service.BaseUserService;
import com.zzc.modules.sysmgr.user.base.service.RoleService;

/**
 * Created by wufan on 2015/11/1.
 */
public class SequenceEntityServiceTest extends BaseServiceTest {

	@Autowired
	private SequenceEntityService sequenceEntityService;

	@Test
	public void createBrand() {

		Map<String, Object> map = new HashMap<String, Object>();
		List<SequenceEntity> findAll = sequenceEntityService.findAll(map, SequenceEntity.class);
		if (findAll != null && findAll.size() == 1) {
			Integer sequenceNumber = findAll.get(0).getSequenceNumber();
			System.err.println(sequenceNumber);
		}
	}

}
