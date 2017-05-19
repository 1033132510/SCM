package com.zzc.modules.supply.dao;

import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;

import java.math.BigDecimal;
import java.util.List;


public interface AuditBillCateAdminQueryDao {

    /**
     * //TODO 品类管理员获取待审批
     * @param currentUserId
     * @param nameOrCode
     * @param orgName
     * @param brandName
     * @param pageSize
     * @param curPage
     * @return
     */
    public List<AuditBill> findPendingforCateAdmin(String currentUserId, String nameOrCode, String orgName, String brandName, Integer pageSize, Integer curPage);
    
    /**
     * //TODO 品类管理员获取待审批数量
     * @param currentUserId
     * @param nameOrCode
     * @param orgName
     * @param brandName
     * @return
     */
    public Long findPendingCountforCateAdmin(String currentUserId, String nameOrCode, String orgName, String brandName);
    
    /**
     * //TODO 品类管理员获取已处理
     * @param categoryList
     * @param pageSize
     * @param curPage
     * @return
     * @author ping
     */
    public List<AuditRecord> findProcessedforCateAdmin(String currentUserId, String nameOrCode, String orgName, String brandName, Integer pageSize, Integer curPage);
    
    /**
     * //TODO 品类管理员获取已处理的数量
     * @param categoryList
     * @return
     */
    public BigDecimal findProcessedCountforCateAdmin(String currentUserId, String nameOrCode, String orgName, String brandName);
}
