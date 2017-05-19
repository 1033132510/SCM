package com.zzc.modules.sysmgr.user.purchaser.service;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.user.purchaser.entity.PurchaserUser;
import com.zzc.operatelog.common.OperateLog;
import com.zzc.operatelog.common.OperateType;

public interface PurchaserUserService extends BaseCrudService<PurchaserUser> {
	@OperateLog(begin="$operateLog=logInformation($args[0]);$user=userLog()",
		    targetId="5",
		    targetName="采购商用户信息",
		    operateType= OperateType.UPDATE,
		    description="采购商用户管理",
		    evalText="\n用户~~"+"$!user"+"\n信息~~" + "$!operateLog",
		    end="")
	public PurchaserUser save(PurchaserUser purchaserUser);
	
	public String logInformation(PurchaserUser purchaserUser);
	
	public String userLog();
}
