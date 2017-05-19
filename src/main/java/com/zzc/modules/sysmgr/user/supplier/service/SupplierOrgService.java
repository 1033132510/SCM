package com.zzc.modules.sysmgr.user.supplier.service;

import org.springframework.data.domain.Page;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;
import com.zzc.operatelog.common.OperateLog;
import com.zzc.operatelog.common.OperateType;

public interface SupplierOrgService extends BaseCrudService<SupplierOrg> {

	/**
	 * 根据供应商编号或供应商名称搜索
	 * 
	 * @param orgCodeOrorgName
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Page<SupplierOrg> findByorgCodeOrorgName(String orgCodeOrorgName, Integer pageNumber, Integer pageSize,Integer status);
	
	@OperateLog(begin="$operateLog=logInformation($args[0]);$user=userLog()",
		    targetId="2",
		    targetName="供应商信息",
		    operateType= OperateType.UPDATE,
		    description="供应商管理",
		    evalText="\n用户~~"+"$!user"+"\n信息~~" + "$!operateLog",
		    end="")
	public SupplierOrg save(SupplierOrg supplierOrg) ;
	
	public String logInformation(SupplierOrg supplierOrg);
	
	public String userLog();
}
