package com.zzc.base.sysmgr;

import com.zzc.common.operatelog.manage.entity.OperateLogInfo;
import com.zzc.common.operatelog.manage.service.OperateLogService;
import com.zzc.core.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wufan on 2015/11/1.
 */
public class OperateLogServiceTest extends BaseServiceTest {

	@Autowired
	private OperateLogService operateLogService;

	@Test
	public void testCreateLog() {
		OperateLogInfo operateLogInfo = new OperateLogInfo();
		operateLogInfo.setStatus(0);
		operateLogInfo.setInfo("test");

		operateLogService.create(operateLogInfo);
	}


}
