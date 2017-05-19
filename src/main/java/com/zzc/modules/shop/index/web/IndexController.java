package com.zzc.modules.shop.index.web;

import com.zzc.core.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 电商首页
 * Created by wufan on 2015/11/24.
 */
@Controller
@RequestMapping("/shop/index")
public class IndexController extends BaseController {


    /**
     * 电商首页
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String toIndex() {
        return "shop/index";
    }
}
