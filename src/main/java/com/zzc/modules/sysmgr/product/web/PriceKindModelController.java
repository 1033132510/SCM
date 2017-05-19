package com.zzc.modules.sysmgr.product.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.product.entity.PriceKind;
import com.zzc.modules.sysmgr.product.service.PriceKindService;

/**
 * 
 * @author apple
 *
 */
@Controller
@RequestMapping("/sysmgr/productKindModel")
public class PriceKindModelController extends BaseController {

	@Autowired
	private PriceKindService priceKindModelService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<PriceKind> getCate() {
		return priceKindModelService.findAll();
	}
}
