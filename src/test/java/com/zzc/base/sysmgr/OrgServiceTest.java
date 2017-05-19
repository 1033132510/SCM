package com.zzc.base.sysmgr;

import com.alibaba.fastjson.JSON;
import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.user.base.entity.CompanyOrg;
import com.zzc.modules.sysmgr.user.base.service.CompanyOrgService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wufan on 2015/11/1.
 */
public class OrgServiceTest extends BaseServiceTest {

	@Autowired
	private CompanyOrgService orgService;

	@Test
	public void testCreateRootOrg() {
		CompanyOrg rootOrg = new CompanyOrg();
		rootOrg.setDescription("集团");
		rootOrg.setLogo("log");
		rootOrg.setOrgName("中直采集团");
		rootOrg.setOrgCode("zzc_company");
		rootOrg.setOrgLevel(0);
		orgService.create(rootOrg);
	}

	@Test
	public void testFindByOrgCode () {
//		CompanyOrg companyOrg = orgService.findByOrgCode("zzc_company");
		CompanyOrg companyOrg = orgService.findByPK("4028b8815113b26c015113b2a6750000");
		System.out.println("====" + companyOrg.getSubOrgs().size());
	}


	@Test
	public void testCreateSubOrgs () {
		CompanyOrg parentOrg = orgService.findByOrgCode("zzc_company");
		System.out.println("===========:" + JSON.toJSONString(parentOrg));

		CompanyOrg subOrg1 = new CompanyOrg();
		subOrg1.setDescription("suborg one ");
		subOrg1.setLogo("test 1");
		subOrg1.setOrgName("中直采集团-直采网络");
		subOrg1.setOrgCode("zzc_company_wl");
		subOrg1.setOrgLevel(2);
		subOrg1.setParentOrg(parentOrg);

		orgService.create(subOrg1);

		CompanyOrg subOrg2 = new CompanyOrg();
		subOrg2.setDescription("suborg two ");
		subOrg2.setLogo("test 2");
		subOrg2.setOrgName("中直采集团-直采金融");
		subOrg2.setOrgCode("zzc_company_jr");
		subOrg2.setOrgLevel(2);
		subOrg2.setParentOrg(parentOrg);

		orgService.create(subOrg2);

		/*CompanyOrg parentOrg = orgService.findByOrgCode("zzc_company_wl");
		System.out.println("===========:" + JSON.toJSONString(parentOrg));

		CompanyOrg subOrg1 = new CompanyOrg();
		subOrg1.setDescription("suborg three ");
		subOrg1.setLogo("test 1");
		subOrg1.setOrgName("中直采集团-直采网络_1");
		subOrg1.setOrgCode("zzc_company_wl1");
		subOrg1.setOrgLevel(3);
		subOrg1.setParentOrg(parentOrg);

		orgService.create(subOrg1);*/
	}

}
