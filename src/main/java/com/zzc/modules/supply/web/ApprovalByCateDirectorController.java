package com.zzc.modules.supply.web;

import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.service.AuditBillCateDirectorService;
import com.zzc.modules.supply.service.AuditRecordService;
import com.zzc.modules.sysmgr.enums.AuditBillOperationTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

/**
 * //TODO 添加类/品类总监审批
 * 
 * @author ping
 * @date 2016年2月17日
 */
@Controller
@RequestMapping("/sysmgr/approvals/director")
public class ApprovalByCateDirectorController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(ApprovalByCateDirectorController.class);

    @Autowired
    private AuditBillCateDirectorService auditBillCateDirectorService;

    @Autowired
    private AuditRecordService auditRecordService;

    /**
     * //TODO 品类总监待审批列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/waitingApproval", method = RequestMethod.GET)
    public String waitingApproval() {
        return "sysmgr/approval/cateDirector/waitingApproval";
    }

    @RequestMapping(value = "/getWaitingApprovalList", method = RequestMethod.POST)
    @ResponseBody
    public String getWaitingApprovalList(@RequestParam("curPage") Integer curPage, @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "nameOrCode", required = false) String nameOrCode,
            @RequestParam(value = "orgName", required = false) String orgName,
            @RequestParam(value = "brandName", required = false) String brandName) {
        SessionUser sessionUser = UserUtil.getUserFromSession();
        if (sessionUser == null) {
            return "";
        }
        return JsonUtils.toJson(
                auditBillCateDirectorService
                        .findPendingforCateDirector(sessionUser.getCurrentUserId(), nameOrCode, orgName, brandName, pageSize, curPage));
    }

    /**
     * //TODO 品类总监已处理列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/approved", method = RequestMethod.GET)
    public String approved() {
        return "sysmgr/approval/cateDirector/approved";
    }

    @RequestMapping(value = "/getApprovedList", method = RequestMethod.POST)
    @ResponseBody
    public String getApprovedList(@RequestParam("curPage") Integer curPage, @RequestParam("pageSize") Integer pageSize) {
        SessionUser sessionUser = UserUtil.getUserFromSession();
        if (sessionUser == null) {
            return "";
        }
        return JsonUtils.toJson(auditBillCateDirectorService.findProcessedforCateDirector(sessionUser.getCurrentUserId(), pageSize, curPage));
    }

    /**
     * 总监待处理详情
     *
     * @return
     */
    @RequestMapping(value = "/{auditBillId}/waitingApprovalInfo", method = RequestMethod.GET)
    public ModelAndView pendingDetailView(@PathVariable("auditBillId") String auditBillId, ModelMap modelMap) {
        modelMap.put("auditBillId", auditBillId);
        AuditBill auditBill = auditBillCateDirectorService.findByPK(auditBillId);
        modelMap.put("operationType", auditBill.getOperationType());
        modelMap.put("OFF_SHELVE", AuditBillOperationTypeEnum.OFF_SHELVE.getValue());
        return new ModelAndView("sysmgr/approval/cateDirector/waitingApprovalInfo", modelMap);
    }

    @RequestMapping(value = "/{auditBillId}/approvedInfo", method = RequestMethod.GET)
    public ModelAndView approvedInfo(@PathVariable("auditBillId") String auditBillId, ModelMap modelMap) {
        modelMap.put("auditBillId", auditBillId);
        return new ModelAndView("sysmgr/approval/cateDirector/approvedInfo", modelMap);
    }

    @RequestMapping(value = "/{auditBillId}/cateDirectorRecord", method = RequestMethod.POST)
    @ResponseBody
    public String getCateDirectorRecord(@PathVariable("auditBillId") String auditBillId) {
        return JsonUtils.toJson(auditRecordService.findAuditRecordListForCateDirector(auditBillId));
    }

    @RequestMapping(value = "/{auditBillId}/cateDirectorProductInfo", method = RequestMethod.POST)
    @ResponseBody
    public String getCateDirectorProductInfo(@PathVariable("auditBillId") String auditBillId) {
        return JsonUtils.toJson(auditBillCateDirectorService.findSubProductDetailForCateDirector(auditBillId));
    }

    @RequestMapping(value = "/{auditBillId}/waitingApprovalProductPrice", method = RequestMethod.POST)
    @ResponseBody
    public String getWaitingApprovalProductPrice(@PathVariable("auditBillId") String auditBillId) {
        return JsonUtils.toJson(auditRecordService.findProductPrice(auditBillId));
    }

    @RequestMapping(value = "/{auditBillId}/waitingApprovalNoPass", method = RequestMethod.POST)
    @ResponseBody
    public String setwaitingApprovalNoPass(@PathVariable("auditBillId") String auditBillId,
            @RequestParam(name = "comment", required = true) String comment, @RequestParam(name = "hasTax", required = false) Integer hasTax,
            @RequestParam(name = "hasTransportation", required = false) Integer hasTransportation,
            @RequestParam(name = "standard", required = false) BigDecimal standard) {
        auditBillCateDirectorService.setNoPass(auditBillId, comment, hasTax, hasTransportation, standard);
        return "success";
    }

    @RequestMapping(value = "/{auditBillId}/waitingApprovalpass", method = RequestMethod.POST)
    @ResponseBody
    public String setWaitingApprovalpass(@PathVariable("auditBillId") String auditBillId,
            @RequestParam(name = "comment", required = true) String comment, @RequestParam(name = "hasTax", required = false) Integer hasTax,
            @RequestParam(name = "hasTransportation", required = false) Integer hasTransportation,
            @RequestParam(name = "standard", required = false) BigDecimal standard) {
        SessionUser sessionUser = UserUtil.getUserFromSession();
        if (sessionUser == null) {
            return "";
        }
        auditBillCateDirectorService.setPass(sessionUser.getDisplayUserName(), auditBillId, comment, hasTax, hasTransportation, standard);
        return "success";
    }
    
    @RequestMapping(value = "/{auditBillId}/cateDirectorLatestRecord", method = RequestMethod.POST)
    @ResponseBody
    public String cateDirectorLatestRecord(@PathVariable("auditBillId") String auditBillId) {
        SessionUser sessionUser = UserUtil.getUserFromSession();
        if (sessionUser == null) {
            return "";
        }
        return JsonUtils.toJson(auditRecordService.findRecordLatestByCreateId(auditBillId,sessionUser.getCurrentUserId()));
    }
}
