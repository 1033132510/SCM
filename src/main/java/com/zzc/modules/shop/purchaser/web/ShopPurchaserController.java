/**
 *
 */
package com.zzc.modules.shop.purchaser.web;

import com.zzc.common.sms.SmsService;
import com.zzc.common.sms.SmsTemplateKeys;
import com.zzc.common.sms.SmsWithIndexPlaceholder;
import com.zzc.common.sms.SmsWithoutPlaceholder;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.shop.purchaser.entity.ShopPurchaser;
import com.zzc.modules.shop.purchaser.service.ShopPurchaserService;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.service.RoleService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 采购商登记
 * @author zhangyong
 */
@Controller(value = "shopPurchaserController")
@RequestMapping(value = "/shop/purchaser")
public class ShopPurchaserController extends BaseController {

	@Resource(name = "shopPurchaserService")
	private ShopPurchaserService shopPurchaserService;

	@Resource(name = "smsService")
	private SmsService smsService;

	@Resource(name = "roleService")
	private RoleService roleService;

	@Resource(name = "employeeService")
	private EmployeeService employeeService;

	@RequestMapping(value = "/toAddPurchaser")
	public String toAddPurchaser() {
		return "shop/purchaser/addPurchaser";
	}


	/**
	 * 采购商登记
	 *
	 * @param purchaser
	 * @return
	 */
	@RequestMapping(value = "/addPurchaser", method = RequestMethod.POST)
	@ResponseBody
	public boolean addPurchaser(ShopPurchaser purchaser) {
		purchaser.setStatus(CommonStatusEnum.无效.getValue());
		boolean flag = shopPurchaserService.addPurchaser(purchaser);
		// 如果登记成功，需要发送短信
		if (flag) {
			Role puchaserRole = roleService.findByRoleCode(SystemConstants.PURCHASER_MESSAGE_RECEIVER);
			List<Employee> employees = employeeService.findByRoleId(puchaserRole.getId());
			if (employees != null && employees.size() > 0) {
				SmsWithoutPlaceholder sms = new SmsWithIndexPlaceholder(
						employees.get(0).getMobile(),
						SmsTemplateKeys.REGIST_SHOP_PURCHASER,
						ArrayUtils.toArray(purchaser.getCompanyName(),
								purchaser.getUsername(),
								purchaser.getMobilePhone()));
				smsService.sendSms(sms);
			}
		}
		return flag;
	}

	@RequestMapping(value = "/countByMobile")
	@ResponseBody
	public long countByMobile(String mobile) {
		return shopPurchaserService.countByMobile(mobile);
	}

}