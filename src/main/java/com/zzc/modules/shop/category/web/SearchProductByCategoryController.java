package com.zzc.modules.shop.category.web;

import com.zzc.common.security.util.UserUtil;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.shop.favorite.service.FavoriteService;
import com.zzc.modules.sysmgr.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 商品检索
 */
@Controller
@RequestMapping(value = "shop/search/category")
public class SearchProductByCategoryController extends BaseController {

    @Resource(name = "favoriteService")
    private FavoriteService favoriteService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/childCategoryView", method = RequestMethod.GET)
    public ModelAndView categoryView(@RequestParam(value = "firstId", required = false) String firstId, @RequestParam(value = "secondId", required = false) String secondId,
                                     @RequestParam(value = "thirdId", required = false) String thirdId, @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "seaType", required = false) String seaType, ModelMap map) {
        map.put("firstId", firstId);
        map.put("secondId", secondId);
        map.put("thirdId", thirdId);
        map.put("productName", name);
        map.put("seaType", seaType);
        return new ModelAndView("shop/product/productCategoryDetail", map);
    }


    @RequestMapping(value = "/Info", method = RequestMethod.GET)
    @ResponseBody
    public String getCategoryInfo(@RequestParam(value = "cateId", required = true) String cateId) {
        return categoryService.getShopCategoryInfo(cateId);
    }

    /**
     * 显示当前用户收藏的二级商品品类个数 group by 二级商品品类
     *
     * @return
     */
    @RequestMapping(value = "/getProductCount")
    @ResponseBody
    public ResultData countSecondLevelCategoryProductCount() {
        String purchaserUserId = UserUtil.getUserFromSession().getCurrentUserId();
        List<Map<String, Object>> list = favoriteService.countProductBySecondCategoryLevel(
                purchaserUserId, CommonStatusEnum.有效.getValue());
        return new ResultData(true, list);
    }
}