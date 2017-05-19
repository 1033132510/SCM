/**
 *
 */
package com.zzc.modules.shop.supplier.web;

import com.zzc.common.sms.SmsService;
import com.zzc.common.sms.SmsTemplateKeys;
import com.zzc.common.sms.SmsWithIndexPlaceholder;
import com.zzc.common.sms.SmsWithoutPlaceholder;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.modules.shop.supplier.entity.ShopSupplier;
import com.zzc.modules.shop.supplier.service.ShopSupplierService;
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
 * 供应商登记
 * @author zhangyong
 */
@Controller(value = "shopSupplierController")
@RequestMapping(value = "/shop/supplier")
public class ShopSupplierController {

	@Resource(name = "shopSupplierService")
	private ShopSupplierService shopSupplierService;

	@Resource(name = "smsService")
	private SmsService smsService;

	@Resource(name = "roleService")
	private RoleService roleService;

	@Resource(name = "employeeService")
	private EmployeeService employeeService;

	@RequestMapping(value = "/toAddSupplier")
	public String toAddSupplier() {
		return "shop/supplier/addSupplier";
	}

	@RequestMapping(value = "/addSupplier", method = RequestMethod.POST)
	@ResponseBody
	public boolean addSupplier(ShopSupplier supplier) {
		supplier.setStatus(CommonStatusEnum.无效.getValue());
		boolean flag = shopSupplierService.addSupplier(supplier);
		// 如果登记成功，发短信
		if (flag) {
			// 获取供应商管理员信息
			Role supplierRole = roleService.findByRoleCode(SystemConstants.SUPPLIER_MESSAGE_RECEIVER);
			List<Employee> employees = employeeService.findByRoleId(supplierRole.getId());
			if (employees != null && employees.size() > 0) {
				SmsWithoutPlaceholder sms = new SmsWithIndexPlaceholder(
						employees.get(0).getMobile(),
						SmsTemplateKeys.REGIST_SHOP_SUPPLIER,
						ArrayUtils.toArray(supplier.getCompanyName(),
								supplier.getUsername(),
								supplier.getMobilePhone()));
				smsService.sendSms(sms);
			}
		}
		return flag;
	}

	@RequestMapping(value = "/countByMobile")
	@ResponseBody
	public long countByMobilePhoneOrEmail(String mobile) {
		return shopSupplierService.countByMobile(mobile);
	}


}