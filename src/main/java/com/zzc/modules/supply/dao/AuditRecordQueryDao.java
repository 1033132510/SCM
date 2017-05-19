package com.zzc.modules.supply.dao;

import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;

import java.util.List;

public interface AuditRecordQueryDao {
    
    public List<AuditRecord> findAuditRecordListForSupply(String auditbillId);
    
    public List<AuditRecord> findAuditRecordListForCateAdmin(String auditbillId);
    
    public List<AuditRecord> findAuditRecordListForCateDirector(String auditbillId);
    
   
    public AuditBill findSubProductDetail(String auditbillId);
}
