package com.zzc.modules.shop.foreignproduct.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 海外进口商品页面
 */
@Controller
@RequestMapping(value = "/shop/foreign")
public class ForeignProductController {
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView foreignView(ModelMap model) {
		return new ModelAndView("shop/foreign/foreignIndex", model);
	}
}
