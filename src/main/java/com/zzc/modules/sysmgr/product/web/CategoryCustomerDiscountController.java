package com.zzc.modules.sysmgr.product.web;

import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.product.entity.ProductDiscount;
import com.zzc.modules.sysmgr.product.service.ProductDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 
 * @author apple
 *
 */
@Controller
@RequestMapping("/sysmgr/categoryCustomerDiscount")
public class   CategoryCustomerDiscountController extends BaseController {

	@Autowired
	private ProductDiscountService categoryCustomerDiscountService;

	/**
	 * 折扣信息
	 * @param cateId
	 * @return
	 */
	@RequestMapping(value = "getDiscountByCateIdAndCyustomer", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductDiscount> getDiscount(@RequestParam(value = "cateId", required = false) String cateId) {
		cateId = "000000005165e188015165e49a910006";
		return categoryCustomerDiscountService.getDiscount(cateId, null);
	}
}
