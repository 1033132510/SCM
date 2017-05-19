package com.zzc.modules.shop.nationalproduct.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * 国内优选商品
 *
 */
@Controller
@RequestMapping(value = "/shop/national")
public class NationalProductController {
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView nationalView(ModelMap model) {
		return new ModelAndView("shop/national/nationalIndex", model);
	}
}
