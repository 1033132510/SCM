package com.zzc.modules.sysmgr.util;

import java.util.ArrayList;
import java.util.List;

import com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum;

public class AuditBillStatusUtil {
	
	public static List<Integer> getTerminalStatus() {
		List<Integer> auditStatus = new ArrayList<Integer>();
		auditStatus.add(SupplyAuditBillStatusEnum.DELETE.getCode());
		auditStatus.add(SupplyAuditBillStatusEnum.PASS.getCode());
		return auditStatus;
	}

}