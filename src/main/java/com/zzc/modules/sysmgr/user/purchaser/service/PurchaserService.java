package com.zzc.modules.sysmgr.user.purchaser.service;

import com.zzc.core.service.BaseCrudService;
import com.zzc.core.web.common.ResultData;
import com.zzc.modules.sysmgr.user.purchaser.dto.PurchaserAndImageDTO;
import com.zzc.modules.sysmgr.user.purchaser.entity.Purchaser;
import com.zzc.operatelog.common.OperateLog;
import com.zzc.operatelog.common.OperateType;
import org.springframework.data.domain.Page;

public interface PurchaserService extends BaseCrudService<Purchaser> {

	/**
	 * 根据采购商编号或公司名称搜索
	 * 
	 * @param codeOrCompany
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Page<Purchaser> findByPurchaserCodeOrCompany(String codeOrCompany,
			Integer pageNumber, Integer pageSize);

	PurchaserAndImageDTO findPurchaserAndImageById(String id);

	PurchaserAndImageDTO findPurchaserAndImageByOrgCode(String orgCode);

	/**
	 * 关联已经存在的公司
	 * 
	 * @param orgCode
	 * @param parentId
	 */
	ResultData relateExistedPurchaser(String orgCode, String parentId);
	
	@OperateLog(begin="$operateLog=logInformation($args[0]);$user=userLog()",
		    targetId="3",
		    targetName="采购商信息",
		    operateType= OperateType.CREATE,
		    description="采购商管理",
		    evalText="\n用户~~"+"$!user"+"\n信息~~" + "$!operateLog",
		    end="")
	Purchaser create(Purchaser purchaser);
	
	@OperateLog(begin="$operateLog=logInformation($args[0]);$user=userLog()",
		    targetId="3",
		    targetName="采购商信息",
		    operateType= OperateType.UPDATE,
		    description="采购商管理",
		    evalText="\n用户~~"+"$!user"+"\n信息~~" + "$!operateLog",
		    end="")
	Purchaser update(Purchaser purchaser);
	
	@OperateLog(begin="$operateLog=logInformation($args[0]);$user=userLog()",
		    targetId="3",
		    targetName="采购商信息",
		    operateType= OperateType.UPDATE,
		    description="采购商管理",
		    evalText="\n用户~~"+"$!user"+"\n信息~~" + "$!operateLog",
		    end="")
	public Purchaser save(Purchaser purchaser) ;
	
	public String logInformation(Purchaser purchaser);
	
	public String userLog();

}
