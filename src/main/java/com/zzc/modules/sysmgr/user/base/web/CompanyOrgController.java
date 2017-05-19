package com.zzc.modules.sysmgr.user.base.web;

import com.zzc.common.enums.EnumUtil;
import com.zzc.common.page.PageForShow;
import com.zzc.common.zTree.ZTreeNode;
import com.zzc.core.exceptions.constant.ExceptionConstant;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.enums.OrgClassEnum;
import com.zzc.modules.sysmgr.user.base.entity.CompanyOrg;
import com.zzc.modules.sysmgr.user.base.entity.UserOrgPositionRelation;
import com.zzc.modules.sysmgr.user.base.service.CompanyOrgService;
import com.zzc.modules.sysmgr.user.base.service.UserOrgPositionRelationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wufan on 2015/11/17.
 */
@Controller
@RequestMapping("/sysmgr/companyOrg")
class CompanyOrgController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(CompanyOrgController.class);

    private static final String BASE_TREE_URL= "/sysmgr/companyOrg/";
    private static final int ROOT_ORG_LEVEL = 0;

    @Autowired
    private CompanyOrgService companyOrgService;
    @Autowired
    private UserOrgPositionRelationService userOrgPositionRelationService;

    // 组织类型
    private Map<String, String> orgClasses;

    /**
     * 进入组织管理列表
     * @return
     */
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String list(Map<String, Object> map) {
        prepareInitDatas(map);
        map.put("companyOrg", new CompanyOrg());
        return "sysmgr/org/companyOrgList";
    }


    /**
     * 初始化组织树
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "initTree", method = RequestMethod.GET)
    public ZTreeNode initTree() {

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("AND_EQ_orgLevel", ROOT_ORG_LEVEL);

        List<CompanyOrg> companyOrgs = companyOrgService.findAll(paramsMap, CompanyOrg.class);
        if (companyOrgs != null && companyOrgs.size() > 0) {
            return generateZTreeNode(companyOrgs.get(0));
        } else {
            // 创建根组织节点，初始化数据，替代初始化脚本
            CompanyOrg rootOrg = new CompanyOrg();
            rootOrg.setDescription("集团");
            rootOrg.setOrgName("中直采集团");
            rootOrg.setOrgCode("zzc_company");
            rootOrg.setOrgLevel(ROOT_ORG_LEVEL);
            companyOrgService.create(rootOrg);
            return generateZTreeNode(rootOrg);
        }
    }

    /**
     * 获取下一级组织树
     * @param parentOrgId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "loadSubTree", method = RequestMethod.GET)
    public List<ZTreeNode> loadSubTree(@RequestParam(value = "parentOrgId", required = false) String parentOrgId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("AND_EQ_parentOrg.id", parentOrgId);

        List<CompanyOrg> companyOrgs = companyOrgService.findAll(paramsMap, CompanyOrg.class);
        List<ZTreeNode> nodeList = new ArrayList<>();

        for (CompanyOrg companyOrg : companyOrgs) {
            ZTreeNode zTreeNode = generateZTreeNode(companyOrg);
            nodeList.add(zTreeNode);
        }
        return nodeList;
    }

    /**
     * 生成树节点
     * @param companyOrg
     * @return
     */
    private ZTreeNode generateZTreeNode(CompanyOrg companyOrg) {
        ZTreeNode zTreeNode = new ZTreeNode();
        zTreeNode.setName(companyOrg.getOrgName());
        zTreeNode.setId(companyOrg.getId());
        zTreeNode.setLevel(companyOrg.getOrgLevel());
        zTreeNode.setInfoURL(BASE_TREE_URL + "/getInfo?orgId=" + companyOrg.getId());
        zTreeNode.setChildURL(BASE_TREE_URL + "/loadSubTree?parentOrgId=" + companyOrg.getId());
        zTreeNode.setOpen(true);
        if (companyOrg.getOrgLevel().intValue() == 0) {
            zTreeNode.setpId("-1");
        } else {
            zTreeNode.setpId(companyOrg.getParentOrg().getId());
        }

        if (null != companyOrg.getSubOrgs() && companyOrg.getSubOrgs().size() > 0) {
            zTreeNode.setLast(false);
        } else {
            zTreeNode.setLast(true);
        }

        return zTreeNode;
    }

    /**
     * 分页查询组织信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/page")
    public String list( @RequestParam("curPage") Integer pageNumber,
                        @RequestParam("pageSize") Integer pageSize,
                        @RequestParam(name = "searchParam", required = false) String searchParam,
                        @RequestParam(name = "companyOrgType", required = false) String companyOrgType) {

        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotEmpty(searchParam)) {
            params.put("OR_LIKE_companyOrgCode", searchParam);
            params.put("OR_LIKE_companyOrgName", searchParam);
            params.put("OR_LIKE_description", searchParam);
        }

        if (StringUtils.isNotEmpty(companyOrgType)) {
            params.put("AND_EQ_companyOrgType", companyOrgType);
        }

        Page<CompanyOrg> results = companyOrgService.findByParams(CompanyOrg.class, params, pageNumber, pageSize, "desc", "createTime");

        return JsonUtils.toJson(PageForShow.newInstanceFromSpringPage(
                results, pageNumber));
    }

    /**
     * 获取组织信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public String getOrgInfo(@RequestParam(value = "orgId", required = false) String orgId) {
        return JsonUtils.toJson(companyOrgService.findByPK(orgId));
    }

    /**
     * 删除组织节点
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteOrg/{orgId}", method = RequestMethod.POST)
    public ResultData deleteOrgInfo(@PathVariable String orgId) {
        Map<String, Object> params = new HashMap<>();
        params.put("AND_EQ_id.companyOrg.id", orgId);

        List<UserOrgPositionRelation> relationList = userOrgPositionRelationService.findAll(params, UserOrgPositionRelation.class);
        if (relationList != null && relationList.size() > 0) {
            return new ResultData(false, "该组织分配过用户，无法删除", ExceptionConstant.EXCEPTION_TYPE_BIZ);
        } else {
            companyOrgService.delete(orgId);
            return new ResultData(true);
        }
    }

    /**
     * 修改组织信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    public String saveOrUpdate(@RequestBody CompanyOrg companyOrg) {
        Map<String, Object> results = new HashMap<>();
        if (StringUtils.isNotEmpty(companyOrg.getId())) {
            CompanyOrg oldCompanyOrg = companyOrgService.findByPK(companyOrg.getId());
            oldCompanyOrg.setOrgName(companyOrg.getOrgName());
            oldCompanyOrg.setOrgClass(companyOrg.getOrgClass());
            oldCompanyOrg.setDescription(companyOrg.getDescription());
            oldCompanyOrg.setPhone(companyOrg.getPhone());
            CompanyOrg org = companyOrgService.update(oldCompanyOrg);
            results.put("orgId", oldCompanyOrg.getId());
            results.put("treeNode", this.generateZTreeNode(org));
        } else {
            CompanyOrg org = companyOrgService.create(companyOrg);
            results.put("orgId", org.getId());
            results.put("treeNode", this.generateZTreeNode(org));
        }

        results.put("success", true);
        

        return JsonUtils.toJson(results);
    }


    /**
     * 进入组织维护页面 - 添加
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String toAddPage(Map<String, Object> map) {
        map.put("companyOrg", new CompanyOrg());
        return "sysmgr/org/companyOrgInfo";
    }


    /**
     * 判断组织是否已经存在
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkOrgNameIsExist", method = RequestMethod.POST)
    public ResultData checkOrgNameIsExist (@RequestBody CompanyOrg companyOrg) {
        return new ResultData(companyOrgService.isOrgExsit(companyOrg.getParentOrg().getId(), companyOrg.getOrgName()));
    }


    /**
     * 进入组织维护页面 - 修改
     *
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public String toAddPage(@PathVariable String id, Map<String, Object> map) {
        map.put("companyOrg", companyOrgService.findByPK(id));
        return "sysmgr/org/companyOrgInfo";
    }

    /**
     * 准备页面的初始化数据
     * @param map
     */
    private void prepareInitDatas(Map<String, Object> map) {
        orgClasses = EnumUtil.toMap(OrgClassEnum.class);
        map.put("orgClasses", orgClasses);
    }
}
