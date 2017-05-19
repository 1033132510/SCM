package com.zzc.modules.supply.dao;

import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;

import java.math.BigDecimal;
import java.util.List;


public interface AuditBillSupplyQueryDao {

    public List<AuditRecord> findSubmittedforSupply(String orgId, Integer pageSize, Integer curPage);
    
    public BigDecimal findSubmittedCountforSupply(String orgId);

    List<AuditBill> findNeedAdjustforSupply(String orgId, Integer pageSize, Integer curPage);

    Long findNeedAdjustCountforSupply(String orgId);
}
