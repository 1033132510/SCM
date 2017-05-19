package com.zzc.demo.ztree.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzc.common.zTree.ZTreeNode;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.user.purchaser.entity.Purchaser;
import com.zzc.modules.sysmgr.user.purchaser.service.PurchaserService;

/**
 * zTree Demo
 * 
 * @author ping
 *
 */

@Controller
@RequestMapping(value = "/sysmgr/zTree")
public class ZTreeDemoController extends BaseController {

	private static String level = "0";

	private static String childURL = "/sysmgr/zTree/loadNode";

	private static String infoURL = "/sysmgr/zTree/nodeInfo";

	private static boolean isLast = true;

	private static boolean noLast = false;

	private static Logger log = LoggerFactory.getLogger(ZTreeDemoController.class);

	@Autowired
	private PurchaserService purchaserService;

	@RequestMapping(value = "/view")
	public String getZTree() {
		return "demo/ztree/ztreeDemo";
	}

	@RequestMapping(value = "/initzTree", method = RequestMethod.GET)
	@ResponseBody
	public String initzTree() {
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		// 加入rootNode
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("AND_EQ_orgLevel", this.level);
		List<Purchaser> purchaserList = purchaserService.findAll(paramsMap, null);
		for (int i = 0; i < purchaserList.size(); i++) {
			ZTreeNode rootNode = new ZTreeNode();
			rootNode.setId(purchaserList.get(i).getId());
			rootNode.setName(purchaserList.get(i).getOrgName());
			rootNode.setLevel(0);
			rootNode.setpId("-1");
			rootNode.setChildURL(this.childURL);
			rootNode.setInfoURL(this.infoURL);
			nodeList.add(rootNode);
		}
		return JSON.toJSONString(nodeList);
	}

	@RequestMapping(value = "loadNode", method = RequestMethod.GET)
	@ResponseBody
	public String loadzTreeNode(@RequestParam(value = "params", required = true) String params) {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap = (Map<String, Object>) JSONObject.parse(params);
		String level = requestMap.get("level").toString();
		String id = requestMap.get("id").toString();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if (this.level.equals(level)) {
			paramsMap.put("AND_EQ_parentPurchaser.id", "");
		} else {
			paramsMap.put("AND_EQ_parentPurchaser.id", id);
		}
		List<Purchaser> purchaserList = purchaserService.findAll(paramsMap, null);
		List<ZTreeNode> nodeList = new ArrayList<ZTreeNode>();
		for (int i = 0; i < purchaserList.size(); i++) {
			ZTreeNode node = new ZTreeNode();
			node.setId(purchaserList.get(i).getId());
			node.setName(purchaserList.get(i).getOrgName());
			node.setChildURL(this.childURL);
			node.setLast(this.isLast);
			node.setInfoURL(this.infoURL);
			nodeList.add(node);
		}
		log.debug("data:" + JSON.toJSONString(nodeList));
		return JSON.toJSONString(nodeList);
	}
}
