package com.zzc.base.sysmgr;

import com.zzc.core.enums.CommonStatusEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.enums.AccountTypeEnum;
import com.zzc.modules.sysmgr.user.purchaser.entity.Purchaser;
import com.zzc.modules.sysmgr.user.purchaser.entity.PurchaserUser;
import com.zzc.modules.sysmgr.user.purchaser.service.PurchaserService;
import com.zzc.modules.sysmgr.user.purchaser.service.PurchaserUserService;

public class PurchaserUserServiceTest extends BaseServiceTest {

	@Autowired
	private PurchaserUserService purchaserUserService;

	@Autowired
	private PurchaserService purchaserService;

	@Test
	public void testCreate() {
		PurchaserUser purchaserUser = new PurchaserUser();
		Purchaser purchaser = new Purchaser();
		purchaser.setId("0000000050fbe08c0150fbe097fe0000");
		purchaserUser.setName("陈家海");
		purchaserUser.setDepartment("测试部门1");
		purchaserUser.setEmail("258388184@qq.com");
		purchaserUser.setIdentityNumber("120104100101010101");
		purchaserUser.setMobile("15822248411");
		// purchaserUser.setPurchaserUserCode("123456");
		purchaserUser.setStatus(CommonStatusEnum.有效.getValue());
		purchaserUser.setUserName("15822248411");
		purchaserUser.setUserPwd("111111");
		purchaserUser.setType(AccountTypeEnum.purcharser.getCode());
		purchaserUser.setPurchaser(purchaser);
		purchaserUserService.create(purchaserUser);
	}
}
