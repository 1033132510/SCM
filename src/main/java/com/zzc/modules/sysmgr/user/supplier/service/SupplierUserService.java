package com.zzc.modules.sysmgr.user.supplier.service;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierUser;
import com.zzc.operatelog.common.OperateLog;
import com.zzc.operatelog.common.OperateType;

public interface SupplierUserService extends BaseCrudService<SupplierUser> {
	@OperateLog(begin="$operateLog=logInformation($args[0]);$user=userLog()",
		    targetId="4",
		    targetName="供应商用户信息",
		    operateType= OperateType.UPDATE,
		    description="供应商用户管理",
		    evalText="\n用户~~"+"$!user"+"\n信息~~" + "$!operateLog",
		    end="")
	public SupplierUser save(SupplierUser supplierUser);
	
	public String logInformation(SupplierUser supplierUser);
	
	public String userLog();
}
