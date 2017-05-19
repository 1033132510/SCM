package com.zzc.modules.supply.service;

import com.zzc.modules.supply.vo.SupplyProductSKUVO;
import com.zzc.modules.sysmgr.product.entity.ProductSKU;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;

public interface ProductShelvesByApprovalService {
    ProductSKU productShelves(AuditBill auditBill, AuditRecord auditRecord, SupplyProductSKUVO supplyProductSKUVO);
    
    ProductSKU productOFFShelves(AuditBill auditBill, AuditRecord auditRecord, SupplyProductSKUVO supplyProductSKUVO);
}
