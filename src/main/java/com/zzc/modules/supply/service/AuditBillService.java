package com.zzc.modules.supply.service;


import com.zzc.common.page.PageForShow;
import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.vo.AuditBillVO;
import com.zzc.modules.supply.vo.SupplyProductSKUVO;

/**
 * Created by chenjiahai on 16/1/19.
 */
public interface AuditBillService extends BaseCrudService<AuditBill> {

	/**
     * //TODO 供应商获取已提交的审批单
     * @param orgId
     * @param pageSize
     * @param curPage
     * @return
     * @author ping
     */
    PageForShow<AuditBillVO> findSubmittedforSupply(String orgId, Integer pageSize, Integer curPage);

    PageForShow<AuditBillVO> findNeedAdjustforSupply(String currentUserId, Integer pageSize, Integer curPage);

    void findProcessingProductDetail(String id);

    /**
     * 调整商品后, 改变审批单状态
     *
     * @param id supplyProductSKUId
     */
    AuditBill updateAuditBillStatusToMangerAuditing(String supplyProductSKUId);
    
    /**
     * 查询处于处理流程中的商品
     * @param sysProductId
     * @return
     */
    public AuditBill findAuditBillBySysProductId(String sysProductId);
    
    public SupplyProductSKUVO findSubProductDetailForSupply(String auditBillId);
    
}
