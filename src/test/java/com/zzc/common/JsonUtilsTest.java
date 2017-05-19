package com.zzc.common;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.zzc.core.BaseServiceTest;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.service.RoleService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json工具类测试
 * Created by wufan on 2015/11/26.
 */
public class JsonUtilsTest extends BaseServiceTest {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @Test
    public void testFilter() throws Exception {
        TestPOJO testPOJO = new TestPOJO();
        testPOJO.setA("1");
        testPOJO.setB("2");
        testPOJO.setC("3");
        testPOJO.setD("4");

        ObjectMapper objectMapper = new ObjectMapper();
        FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", SimpleBeanPropertyFilter.serializeAllExcept());
        objectMapper.setFilters(filters);
        objectMapper.addMixInAnnotations(TestPOJO.class, MyFilterMixIn.class);
        String jsonStr = objectMapper.writeValueAsString(testPOJO);
        System.out.println(jsonStr);

        ObjectMapper objectMapper1 = new ObjectMapper();
        FilterProvider filters1 = new SimpleFilterProvider().addFilter("myFilter1", SimpleBeanPropertyFilter.serializeAllExcept());
        objectMapper1.setFilters(filters1);
        objectMapper1.addMixInAnnotations(TestPOJO.class, MyFilterMixIn1.class);
        String jsonStr1 = objectMapper1.writeValueAsString(testPOJO);
        System.out.println(jsonStr1);

        ObjectMapper objectMapper2 = new ObjectMapper();
        TestPOJO t1 = objectMapper2.readValue(jsonStr1, TestPOJO.class);
        TestPOJO t = objectMapper2.readValue(jsonStr, TestPOJO.class);
        System.out.println("tt");


        System.out.println("filter" + JsonUtils.toJsonWithFilter(testPOJO, MyFilterMixIn.class, "myFilter"));
        System.out.println("no filter" + JsonUtils.toJson(testPOJO));

    }

    @Test
    public void testToJsonWithFilter2 () throws Exception {
        Role role = roleService.findByPK("4028817b5119a8f0015119ab7c3e07ea");
        System.out.println("filter" + JsonUtils.toJson(role));
        System.out.println("code filter" + JsonUtils.toJsonWithFilter(role, RoleCodeFilter.class, "roleCodeFilter"));
        System.out.println("name filter" + JsonUtils.toJsonWithFilter(role, RoleNameFilter.class, "roleNameFilter"));
        System.out.println("filter" + JsonUtils.toJson(role));
    }



    @Test
    public void testList () {
        List<Role> roleList = new ArrayList<>();
        Role role1 = new Role();
        role1.setRoleName("name1");
        role1.setRoleCode("code1");
        roleList.add(role1);

        Role role2 = new Role();
        role2.setRoleCode("code2");
        role2.setRoleName("name2");
        roleList.add(role2);

        String roleStr = JsonUtils.toJson(roleList);
        System.out.println(roleStr);
        List<Role> roles = JsonUtils.toList(roleStr, ArrayList.class, Role.class);
        for (Role role : roles) {
            System.out.println(role.getRoleCode());
        }

        List<Role> roleResult = JsonUtils.toObject(roleStr, ArrayList.class);
        for (Role role : roleResult) {
            System.out.println(role.getRoleCode());
        }
    }

    @Test
    public void testConvertMap () {
        Role role1 = new Role();
        role1.setRoleName("name1");
        role1.setRoleCode("code1");

        Role role2 = new Role();
        role2.setRoleCode("code2");
        role2.setRoleName("name2");

        Map<String, Object> map = new HashMap<>();
        map.put("1", role1);
        map.put("2", role2);

        System.out.println("test map :" + JsonUtils.toJson(map));
        System.out.println("test map :" + JsonUtils.toJsonForMap(map));
    }

    @Test
    public void testMap () {
        Map<Role, Employee> map = new HashMap<>();
        Role role1 = new Role();
        role1.setRoleName("name1");
        role1.setRoleCode("code1");

        Employee employee1 = new Employee();
        employee1.setUserName("ename1");
        employee1.setStatus(1);

        Role role2 = new Role();
        role2.setRoleCode("code2");
        role2.setRoleName("name2");
        Employee employee2 = new Employee();
        employee2.setUserName("ename2");
        employee2.setStatus(2);

        map.put(role1, employee1);
        map.put(role2, employee2);


        Map<String, Object> finalMap = new HashMap<>();
        for (Object o : map.keySet()) {
            String key = JsonUtils.toJson(o);
            System.out.println("key : " + key);
            finalMap.put(key, map.get(o));
        }

        System.out.println("final map:" + JsonUtils.toJson(finalMap));


        System.out.println("kye is object: " + JsonUtils.toJsonForMap(map));


//        String mapStr = JsonUtils.toJson(map);
//        System.out.println("JsonUtils.toJson(map):" + mapStr);
//        Map result1 = JsonUtils.toObject(mapStr, HashMap.class);
//        System.out.println(result1.size());


//        Map result3 = JsonUtils.toMap(mapStr, HashMap.class, Role.class, Employee.class);
//
//        System.out.println(result3.size());
    }

    @Test
    public void testJsonIgnore () {
        TestPOJO testPOJO = new TestPOJO();
        testPOJO.setA("aaa");
        testPOJO.setB("bbb");
        testPOJO.setC("ccc");

        System.out.println(JsonUtils.toJson(testPOJO));
    }

    public static class TestPOJO {
        @JsonIgnore
        private String a;
        private String c;
        private String d;
        private String b;
        //getters、setters省略

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }

    @JsonFilter("myFilter")
    @JsonIgnoreProperties({"a", "b"})
    private static interface MyFilterMixIn {
    }

    @JsonFilter("myFilter1")
    @JsonIgnoreProperties({"b"})
    private static interface MyFilterMixIn1 {
    }
}
