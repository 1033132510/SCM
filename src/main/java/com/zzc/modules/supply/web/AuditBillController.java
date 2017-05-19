/**
 * 
 */
package com.zzc.modules.supply.web;

import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.web.common.ResultData;
import com.zzc.modules.sysmgr.enums.AuditBillOperationTypeEnum;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.SysProductSKU;
import com.zzc.modules.supply.service.AuditBillService;
import com.zzc.modules.supply.service.SupplyProductSKUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhangyong
 *
 */

@RequestMapping(value = "/supply/auditBill")
@Controller(value = "auditBillController")
public class AuditBillController {
	
	@Autowired
	private AuditBillService auditBillService;
	
	@Autowired
	private SupplyProductSKUService supplyProductSKUService;

	/**
	 * 查看该商品是否还在审核流程过程中
	 * @param sysProductId
	 * @return
	 */
	@RequestMapping(value = "/existAuditBillBySysProductSKUId")
	@ResponseBody
	public ResultData existAuditBillBySysProductSKUId(@RequestParam("sysProductId") String sysProductId) {
		return getDescriptionByCode(auditBillService.findAuditBillBySysProductId(sysProductId));
	}
	
	/**
	 * 查看该商品是否还在审核流程过程中
	 * @return
	 */
	@RequestMapping(value = "/existAuditBillByProductSKUId")
	@ResponseBody
	public ResultData existAuditBillByProductSKUId(@RequestParam("productSKUId") String productSKUId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AND_EQ_productSKUId", productSKUId);
		map.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		List<SysProductSKU> sysProductSKUs = supplyProductSKUService.findAll(map, SysProductSKU.class, "DESC", "createTime");
		if(sysProductSKUs == null || sysProductSKUs.size() == 0) {
			return new ResultData(true, null, true, null);
		}
		return getDescriptionByCode(auditBillService.findAuditBillBySysProductId(sysProductSKUs.get(0).getId()));
	}
	
	private ResultData getDescriptionByCode(AuditBill auditBill) {
		if(auditBill == null) {
			return new ResultData(true, null, true, null);
		} else {
			Integer code = auditBill.getOperationType();
			String description = null;
			if(code.intValue() == AuditBillOperationTypeEnum.OFF_SHELVE.getValue()) {
				description = "此商品已调整下架操作，正在品类管理员审批过程中，等审批结束后方可重新调整";
			} else if(code.intValue() == AuditBillOperationTypeEnum.UPDATE.getValue()){
				description = "此商品已调整编辑，正在品类管理员审批过程中，等审批结束后方可重新调整";
			} else if(code.intValue() == AuditBillOperationTypeEnum.SHELVE.getValue()){
				description = "此商品已调整上架操作，正在品类管理员审批过程中，等审批结束后方可重新上架";
			}
			return new ResultData(true, description, false, null);
		}
	}
	
}