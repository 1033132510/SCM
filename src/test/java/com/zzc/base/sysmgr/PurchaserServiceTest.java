package com.zzc.base.sysmgr;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;

import com.zzc.core.BaseServiceTest;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.modules.sysmgr.user.purchaser.entity.Purchaser;
import com.zzc.modules.sysmgr.user.purchaser.service.PurchaserService;

public class PurchaserServiceTest extends BaseServiceTest {

	@Autowired
	private PurchaserService purchaserService;

	@Test
	public void testCreate() {
		for (int i = 0; i < 30; i++) {
			Purchaser purchaser = new Purchaser();
			purchaser.setLegalName("测试法人" + i);
			purchaser.setLevel(1);
			purchaser.setTel("15822248411");
			purchaser.setOrgCode("CGS-abcd-" + i);
			purchaser.setOrgLevel(1);
			purchaser.setOrgName("测试采购商" + i);
			purchaser.setStatus(CommonStatusEnum.有效.getValue());
			purchaserService.create(purchaser);
		}
	}

	@Test
	public void testFindByPurchaserCodeOrCompany() {
		Page<Purchaser> page = purchaserService.findByPurchaserCodeOrCompany(
				"CGS-abcd-2", 1, 10);
		for (Purchaser purchaser : page.getContent()) {
			System.err.println(purchaser.getId());
		}
	}
}
