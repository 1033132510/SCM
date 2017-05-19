package com.zzc.modules.supply.dao;

import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;

import java.math.BigDecimal;
import java.util.List;


public interface AuditBillCateDirectorQueryDao {

    /**
     * //TODO 品类总监获取待审批
     * @param currentUserId
     * @param nameOrCode
     * @param orgName
     * @param brandName
     * @param pageSize
     * @param curPage
     * @return
     */
    public List<AuditBill> findPendingforCateDirector(String currentUserId, String nameOrCode, String orgName, String brandName, Integer pageSize, Integer curPage);
    
    /**
     * //TODO 品类总监获取待审批数量
     * @param currentUserId
     * @param nameOrCode
     * @param orgName
     * @param brandName
     * @return
     */
    public Long findPendingCountforCateDirector(String currentUserId, String nameOrCode, String orgName, String brandName);
    
    /**
     * //TODO 品类总监获取已处理
     * @param categoryList
     * @param pageSize
     * @param curPage
     * @return
     * @author ping
     */
    public List<AuditRecord> findProcessedforCateDirector(String currentUserId, Integer pageSize, Integer curPage);
    
    /**
     * //TODO 品类总监获取已处理的数量
     * @param categoryList
     * @return
     */
    
    public BigDecimal findProcessedCountforCateDirector(String currentUserId);
}
