package com.zzc.modules.supply.service;


import com.zzc.common.page.PageForShow;
import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.vo.AuditBillVO;
import com.zzc.modules.supply.vo.SupplyProductSKUVO;

import java.math.BigDecimal;

/**
 * //TODO 品类总监审批单服务层
 * @author ping
 * @date 2016年2月22日
 */
public interface AuditBillCateAdminService extends BaseCrudService<AuditBill> {

    /**
     * //TODO 品类管理员获取待处理的审批单
     * @param currentUserId
     * @param nameOrCode
     * @param orgName
     * @param brandName
     * @param pageSize
     * @param curPage
     * @return
     * @author ping
     */
    PageForShow<AuditBillVO> findPendingforCateAdmin(String currentUserId, String nameOrCode, String orgName, String brandName, Integer pageSize, Integer curPage);
    
    /**
     * //TODO 品类管理员获取已处理的审批单
     * @param currentUserId
     * @param pageSize
     * @param curPage
     * @return
     * @author ping
     */
    PageForShow<AuditBillVO> findProcessedforCateAdmin(String currentUserId, String nameOrCode, String orgName, String brandName, Integer pageSize, Integer curPage);
    
    void setPass(String auditBillId, String comment, Integer hasTax, Integer hasTransportation, BigDecimal standard);
    
    void setNoPass(String auditBillId, String comment, Integer hasTax, Integer hasTransportation, BigDecimal standard);
    
    public SupplyProductSKUVO findSubProductDetailForCateAdmin(String auditBillId);
}
