package com.zzc.modules.supply.service;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;
import com.zzc.modules.supply.entity.SysProductSKU;
import com.zzc.modules.supply.vo.AuditRecordVO;
import com.zzc.modules.supply.vo.ProductPriceVO;

import java.util.List;

/**
 * Created by chenjiahai on 16/1/19.
 */
public interface AuditRecordService extends BaseCrudService<AuditRecord> {

	void createSupplyRecord(String comment, AuditBill auditBill, SysProductSKU sysProductSKU, AuditRecord lastestAuditRecord);
	
	List<AuditRecordVO> findAuditRecordListForSupply(String auditbillId);
	
	List<AuditRecordVO> findAuditRecordListForCateAdmin(String auditbillId);
	
	List<AuditRecordVO> findAuditRecordListForCateDirector(String auditbillId);
	
	ProductPriceVO findProductPrice(String auditbillId);
	
	ProductPriceVO findRecordLatestByCreateId(String auditbillId, String createId);
}
