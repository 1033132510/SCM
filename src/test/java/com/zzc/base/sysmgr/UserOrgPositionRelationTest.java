package com.zzc.base.sysmgr;

import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.user.base.entity.CompanyOrg;
import com.zzc.modules.sysmgr.user.base.entity.Position;
import com.zzc.modules.sysmgr.user.base.entity.UserOrgPositionRelation;
import com.zzc.modules.sysmgr.user.base.entity.UserOrgPositionRelationPK;
import com.zzc.modules.sysmgr.user.base.service.CompanyOrgService;
import com.zzc.modules.sysmgr.user.base.service.PositionService;
import com.zzc.modules.sysmgr.user.base.service.UserOrgPositionRelationService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hadoop on 2015/11/19.
 */
public class UserOrgPositionRelationTest extends BaseServiceTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CompanyOrgService companyOrgService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private UserOrgPositionRelationService userOrgPositionRelationService;


    @Test
    public void testCreate () {
        UserOrgPositionRelationPK userOrgPositionRelationPK = new UserOrgPositionRelationPK();

        Employee employee = employeeService.findByPK("4028b881511e5a7801511e5abdd20000");
        CompanyOrg companyOrg = companyOrgService.findByPK("4028b88151139e4f0151139e807b0000");
        Position position = positionService.findByPK("000000005114f547015114f9f0d70000");

        userOrgPositionRelationPK.setEmployee(employee);
        userOrgPositionRelationPK.setCompanyOrg(companyOrg);
        userOrgPositionRelationPK.setPosition(position);

        UserOrgPositionRelation userOrgPositionRelation = new UserOrgPositionRelation();

        userOrgPositionRelation.setId(userOrgPositionRelationPK);
        userOrgPositionRelation.setDescription("test");

        userOrgPositionRelationService.create(userOrgPositionRelation);
    }



    /*@Test
    public void testCreate () {
        UserOrgPositionRelationPK userOrgPositionRelationPK = new UserOrgPositionRelationPK();
        userOrgPositionRelationPK.setEmployeeId("1");
        userOrgPositionRelationPK.setOrgId("2");
        userOrgPositionRelationPK.setPositionId("3");

        UserOrgPositionRelation userOrgPositionRelation = new UserOrgPositionRelation();

        userOrgPositionRelation.setId(userOrgPositionRelationPK);
        userOrgPositionRelation.setDescription("test");

        userOrgPositionRelationService.create(userOrgPositionRelation);
    }*/

    @Test
    public void testFindByEmployeeId () {
        Map<String, Object> map = new HashMap<>();
        map.put("AND_EQ_id.employee.id", "4028b881511e5a7801511e5abdd20000");

        List<UserOrgPositionRelation> relationList = userOrgPositionRelationService.findAll(map, UserOrgPositionRelation.class);
        System.out.println("size : " + relationList.size());

        if (relationList != null && relationList.size() > 0) {
            System.out.println("employeeName:" + relationList.get(0).getId().getEmployee().getEmployeeName());
        }

    }
}
