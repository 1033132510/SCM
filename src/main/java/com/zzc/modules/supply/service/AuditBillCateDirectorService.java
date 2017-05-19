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
public interface AuditBillCateDirectorService extends BaseCrudService<AuditBill> {

    /**
     * //TODO 品类总监获取待处理的审批单
     * @param currentUserId
     * @param nameOrCode
     * @param orgName
     * @param brandName
     * @param pageSize
     * @param curPage
     * @return
     * @author ping
     */
    PageForShow<AuditBillVO> findPendingforCateDirector(String currentUserId, String nameOrCode, String orgName, String brandName, Integer pageSize, Integer curPage);
    
    PageForShow<AuditBillVO> findProcessedforCateDirector(String currentUserId, Integer pageSize, Integer curPage);
    
    
    void setPass(String displayUserName, String auditBillId, String comment, Integer hasTax, Integer hasTransportation, BigDecimal standard);
    
    void setNoPass(String auditBillId, String comment, Integer hasTax, Integer hasTransportation, BigDecimal standard);
    
    public SupplyProductSKUVO findSubProductDetailForCateDirector(String auditBillId);
}
