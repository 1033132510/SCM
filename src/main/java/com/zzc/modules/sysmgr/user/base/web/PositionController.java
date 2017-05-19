package com.zzc.modules.sysmgr.user.base.web;

import com.zzc.common.page.PageForShow;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.ValidateException;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.user.base.entity.Position;
import com.zzc.modules.sysmgr.user.base.entity.UserOrgPositionRelation;
import com.zzc.modules.sysmgr.user.base.service.PositionService;
import com.zzc.modules.sysmgr.user.base.service.UserOrgPositionRelationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 岗位维护页面
 * Created by wufan on 2015/11/15.
 */
@Controller
@RequestMapping("/sysmgr/position")
class PositionController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(PositionController.class);

    @Autowired
    private PositionService positionService;
    @Autowired
    private UserOrgPositionRelationService userOrgPositionRelationService;

    /**
     * 进入岗位管理列表
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String list(Map<String, Object> map) {
        return "sysmgr/position/positionList";
    }

    /**
     * 分页查询岗位信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/page")
    public PageForShow<Position> list( @RequestParam("curPage") Integer pageNumber,
                        @RequestParam("pageSize") Integer pageSize,
                        @RequestParam(name = "searchParam", required = false) String searchParam) {

        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotEmpty(searchParam)) {
            params.put("AND_LIKE_positionName", searchParam);
        }
        params.put("AND_EQ_status", 1);

        Page<Position> results = positionService.findByParams(Position.class, params, pageNumber, pageSize, "desc", "createTime");

        return PageForShow.newInstanceFromSpringPage(
                results, pageNumber);
    }

    /**
     * 进入岗位维护页面 - 添加
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String toAddPage(Map<String, Object> map) {
        map.put("position", new Position());
        return "sysmgr/position/positionInfo";
    }

    /**
     * 添加或修改岗位
     *
     * @param position
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ResultData savePosition(@Valid @RequestBody Position position, BindingResult bindingResults) {
        if (bindingResults.hasErrors()) {
            log.error("后台数据校验错误");
            throw new ValidateException(bindingResults);
		}

        position = positionService.save(position);
        return new ResultData(true, position);
    }


    /**
     * 进入岗位维护页面 - 修改
     *
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public String toAddPage(@PathVariable String id, Map<String, Object> map) {
        map.put("position", positionService.findByPK(id));
        return "sysmgr/position/positionInfo";
    }

    /**
     * 删除岗位信息
     * @param positionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/remove/{positionId}", method = RequestMethod.PUT)
    public Boolean deletePosition(@PathVariable String positionId) {

        Map<String, Object> params = new HashMap<>();
        params.put("AND_EQ_id.position.id", positionId);

        List<UserOrgPositionRelation> relationList = userOrgPositionRelationService.findAll(params, UserOrgPositionRelation.class);
        if (relationList != null && relationList.size() > 0) {
            return false;
        } else {
            positionService.delete(positionId);
            return true;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkPositionNameIsExist/{positionName}", method = RequestMethod.GET)
    public Boolean checkPositionNameIsExist(@PathVariable String positionName) {
        Map<String, Object> params = new HashMap<>();
        params.put("AND_EQ_positionName", positionName);
        params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
        List<Position> positions = positionService.findAll(params, Position.class);
        if (positions != null && positions.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
