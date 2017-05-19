package com.zzc.modules.sysmgr.user.purchaser.web;

import com.zzc.common.enums.EnumUtil;
import com.zzc.common.page.PageForShow;
import com.zzc.common.security.util.UserUtil;
import com.zzc.common.zTree.ZTreeNode;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.ValidateException;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.shop.purchaser.service.ShopPurchaserService;
import com.zzc.modules.sysmgr.common.entity.SequenceEntity;
import com.zzc.modules.sysmgr.common.service.SequenceEntityService;
import com.zzc.modules.sysmgr.common.util.NumberSequenceUtil;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.enums.OrgTypeEnum;
import com.zzc.modules.sysmgr.enums.PurchaserLevelEnum;
import com.zzc.modules.sysmgr.file.service.ImageService;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.user.purchaser.dto.PurchaserAndImageDTO;
import com.zzc.modules.sysmgr.user.purchaser.dto.SavePurchaserRequestDTO;
import com.zzc.modules.sysmgr.user.purchaser.entity.Purchaser;
import com.zzc.modules.sysmgr.user.purchaser.service.PurchaserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 采购商管理
 */
@Controller
@RequestMapping("/sysmgr/purchaser")
public class PurchaserController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(PurchaserController.class);

	private static final String BASE_TREE_URL = "/sysmgr/purchaser/";
	private static final String PURCHASER_CODE_PREFIX = "CGS-";
	private static final String SEQUENCE_NAME = "PURCHASER";

	private static final String EMPTY_STRING = "";

	@Autowired
	private PurchaserService purchaserService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private SequenceEntityService sequenceEntityService;

	@Autowired
	private ShopPurchaserService shopPurchaserService;

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 列表页面
	 *
	 * @return
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String getPurchaserView() {
		return "sysmgr/purchaser/purchaser";
	}
	//添加或修改采购商
	@RequestMapping(value = "addView")
	public String getPurchaserAddView(ModelMap model) {
		initSelectForView(model);
		return "sysmgr/purchaser/addOrUpdatePurchaser";
	}

	/**
	 * 初始化选中模型
	 * @param model
	 */
	private void initSelectForView(ModelMap model) {
		model.addAttribute("levels", EnumUtil.toMap(PurchaserLevelEnum.class));
		model.addAttribute("commonStatus",
				EnumUtils.getEnumList(CommonStatusEnum.class));
	}
	//保存或修改数据
	@RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public ResultData saveOrUpdate(
			@Valid @RequestBody SavePurchaserRequestDTO requestDTO,
			BindingResult bindingResults) throws Exception {
		if (bindingResults.hasErrors()) {
			throw new ValidateException(bindingResults);
		}
		PurchaserAndImageDTO purchaserAndImageDTO = null;
		if (StringUtils.isNotBlank(requestDTO.getParentId())) {
			purchaserAndImageDTO = addSubPurchaser(requestDTO);
		} else if (StringUtils.isBlank(requestDTO.getId())) {
			purchaserAndImageDTO = addPurchaser(requestDTO);
		} else if (StringUtils.isNotBlank(requestDTO.getId())) {
			purchaserAndImageDTO = updatePurchaser(requestDTO);
		}
		setZTreeNodeFromDTO(purchaserAndImageDTO);
		return new ResultData(true, purchaserAndImageDTO);
	}
	//添加sub采购商
	private PurchaserAndImageDTO addSubPurchaser(
			SavePurchaserRequestDTO requestDTO) throws Exception {
		Purchaser parentPurchaser = purchaserService.findByPK(requestDTO
				.getParentId());
		Purchaser purchaser = new Purchaser();
		BeanUtils.copyProperties(purchaser, requestDTO);
		purchaser.setOrgCode(generateOrgCode());
		purchaser.setOrgLevel(parentPurchaser.getOrgLevel() + 1);
		purchaser.setOrgType(parentPurchaser.getOrgType());
		purchaser.setParentPurchaser(parentPurchaser);
		purchaser.setStatus(CommonStatusEnum.有效.getValue());
		purchaser = purchaserService.create(purchaser);
		imageService.buildRelationBetweenImageAndEntity(
				requestDTO.getImageIds(), purchaser.getId());
		return purchaserService.findPurchaserAndImageById(purchaser.getId());
	}

	/**
	 * 生产组织编码
	 * @return
	 */
	private String generateOrgCode() {
		SequenceEntity sequenceEntity = sequenceEntityService
				.findSequenceEntityBySequenceName(SEQUENCE_NAME);
		Integer sequenceNumber = 1;
		if (sequenceEntity == null) {
			sequenceEntity = new SequenceEntity();
			sequenceEntity.setSequenceName(SEQUENCE_NAME);
			sequenceEntity.setStatus(CommonStatusEnum.有效.getValue());
			sequenceEntity.setSequenceNumber(sequenceNumber);
		} else {
			sequenceNumber = sequenceEntity.getSequenceNumber() + 1;
			sequenceEntity.setSequenceNumber(sequenceNumber);
			sequenceEntity.setModifiedTime(new Date());
		}
		sequenceEntityService.create(sequenceEntity);
		String sequenceNumberStr = NumberSequenceUtil.appendPrefixChar(
				sequenceNumber, 4, '0');
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return PURCHASER_CODE_PREFIX + format.format(new Date()) + "-"
				+ sequenceNumberStr;
	}

	/**
	 * 添加采购商
	 * @param requestDTO
	 * @return
	 * @throws Exception
	 */
	private PurchaserAndImageDTO addPurchaser(SavePurchaserRequestDTO requestDTO)
			throws Exception {
		Purchaser purchaser = new Purchaser();
		BeanUtils.copyProperties(purchaser, requestDTO);
		purchaser.setOrgLevel(1);
		purchaser.setStatus(CommonStatusEnum.有效.getValue());
		purchaser.setOrgCode(generateOrgCode());
		purchaser.setOrgType(OrgTypeEnum.purchaser.getCode());
		purchaser = purchaserService.create(purchaser);
		imageService.buildRelationBetweenImageAndEntity(
				requestDTO.getImageIds(), purchaser.getId());
		return purchaserService.findPurchaserAndImageById(purchaser.getId());
	}

	/**
	 * 修改采购商
	 * @param requestDTO
	 * @return
	 */
	private PurchaserAndImageDTO updatePurchaser(
			SavePurchaserRequestDTO requestDTO) {
		Purchaser purchaserFromDb = purchaserService.findByPK(requestDTO
				.getId());
		purchaserFromDb.setLegalName(requestDTO.getLegalName());
		purchaserFromDb.setLevel(requestDTO.getLevel());
		purchaserFromDb.setOrgName(requestDTO.getOrgName());
		purchaserFromDb.setTel(requestDTO.getTel());
		imageService.buildRelationBetweenImageAndEntity(
				requestDTO.getImageIds(), requestDTO.getId());
		purchaserFromDb = purchaserService.update(purchaserFromDb);
		imageService.destoryRelationBetweenImageAndEntity(requestDTO
				.getDeleteImageIds());
		return purchaserService.findPurchaserAndImageById(requestDTO.getId());
	}

	/**
	 * 获得采购商列表
	 * @param pageNumber
	 * @param pageSize
	 * @param codeOrCompany
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "purchasers")
	@ResponseBody
	public String getPurchaserList(
			@RequestParam("curPage") Integer pageNumber,
			@RequestParam("pageSize") Integer pageSize,
			@RequestParam(name = "codeOrCompany", required = false) String codeOrCompany)
			throws Exception {
		Page<Purchaser> page = null;
		if (StringUtils.isNotBlank(codeOrCompany)) {
			page = purchaserService.findByPurchaserCodeOrCompany(codeOrCompany,
					pageNumber, pageSize);
		} else {
			page = purchaserService.findByParams(Purchaser.class, null,
					pageNumber, pageSize, "DESC", "createTime");
		}
		return JsonUtils.toJson(PageForShow.newInstanceFromSpringPage(page,
				pageNumber));
	}

	/**
	 * 修改公司信息
	 * @param id
	 * @param readOnly
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "modifyView/{id}")
	public String modifyPurchaser(
			@PathVariable("id") String id,
			@RequestParam(value = "readOnly", required = false) Integer readOnly,
			ModelMap model) {
		initSelectForView(model);
		model.addAttribute("purchaser",
				purchaserService.findPurchaserAndImageById(id));
		if (readOnly == null) {
			readOnly = 0;
		}
		model.addAttribute("readOnly", readOnly);
		return "sysmgr/purchaser/addOrUpdatePurchaser";
	}

	/**
	 * 采购商组织树
	 * @param init
	 * @param purchaserId
	 * @return
	 */
	@RequestMapping(value = "tree", method = RequestMethod.GET)
	@ResponseBody
	public String getPurchaserTree(
			@RequestParam(value = "init", required = false) Integer init,
			@RequestParam(value = "purchaserId", required = false) String purchaserId) {
		logger.info("【采购商组织树请求Id】" + purchaserId);
		Purchaser purchaser = purchaserService.findByPK(purchaserId);
		if (purchaser == null) {
			return EMPTY_STRING;
		}
		Set<Purchaser> purchasers = new TreeSet<>();
		List<ZTreeNode> nodes = new ArrayList<>();
		if (init != null) {
			initTree(purchasers, purchaser);
		} else {
			findPurchaserByParentId(purchasers, purchaserId);
		}
		for (Purchaser tempPurchaser : purchasers) {
			generateZTreeNode(nodes, tempPurchaser);
		}
		logger.info("【采购商组织树返回数据】" + nodes);
		if (CollectionUtils.isEmpty(nodes)) {
			return EMPTY_STRING;
		}
		return JsonUtils.toJson(nodes);
	}

	/**
	 * 初始化采购商组织树
	 * @param purchasers
	 * @param purchaser
	 * @return
	 */
	private Set<Purchaser> initTree(Set<Purchaser> purchasers,
	                                Purchaser purchaser) {
		Purchaser parent = purchaser.getParentPurchaser();
		purchasers.add(purchaser);
		purchasers.addAll(purchaser.getChildrenPurchaser());
		while (parent != null) {
			purchasers.add(parent);
			purchasers.addAll(parent.getChildrenPurchaser());
			parent = parent.getParentPurchaser();
		}
		return purchasers;
	}

	/**
	 * 根据父id得到采购商
	 * @param purchasers
	 * @param purchaserId
	 */
	private void findPurchaserByParentId(Set<Purchaser> purchasers,
	                                     String purchaserId) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_parentPurchaser.id", purchaserId);
		purchasers.addAll(purchaserService.findAll(params, Purchaser.class));
	}

	/**
	 * 生成树节点
	 * @param nodes
	 * @param tempPurchaser
	 */
	private void generateZTreeNode(List<ZTreeNode> nodes,
	                               Purchaser tempPurchaser) {
		ZTreeNode zTreeNode = new ZTreeNode();
		zTreeNode.setName(tempPurchaser.getOrgName());
		zTreeNode.setId(tempPurchaser.getId());
		zTreeNode.setLevel(tempPurchaser.getLevel());
		zTreeNode.setInfoURL(BASE_TREE_URL + "/getPurchaseInfo?purchaserId="
				+ tempPurchaser.getId());
		if (tempPurchaser.getParentPurchaser() != null) {
			zTreeNode.setpId(tempPurchaser.getParentPurchaser().getId());
		}
		zTreeNode.setChildURL(BASE_TREE_URL + "/tree?purchaserId="
				+ tempPurchaser.getId());
		nodes.add(zTreeNode);
	}

	/**
	 * 设置树节点
	 * @param dto
	 */
	private void setZTreeNodeFromDTO(PurchaserAndImageDTO dto) {
		ZTreeNode zTreeNode = new ZTreeNode();
		zTreeNode.setName(dto.getOrgName());
		zTreeNode.setId(dto.getId());
		zTreeNode.setLevel(dto.getLevel());
		zTreeNode.setInfoURL(BASE_TREE_URL + "/getPurchaseInfo?purchaserId="
				+ dto.getId());
		if (StringUtils.isNotBlank(dto.getParentId())) {
			zTreeNode.setpId(dto.getParentId());
		}
		zTreeNode.setChildURL(BASE_TREE_URL + "/tree?purchaserId="
				+ dto.getId());
		dto.setNode(zTreeNode);
	}

	/**
	 * 根据id获得采购商信息
	 * @param purchaserId
	 * @return
	 */
	@RequestMapping(value = "getPurchaseInfo", method = RequestMethod.GET)
	@ResponseBody
	public String getPurchaseInfoById(
			@RequestParam(value = "purchaserId", required = false) String purchaserId) {
		return JsonUtils.toJson(purchaserService
				.findPurchaserAndImageById(purchaserId));
	}

	/**
	 * 根据公司编码查询采购商信息
	 * @param orgCode
	 * @return
	 */
	@RequestMapping(value = "orgCode/{orgCode}", method = RequestMethod.GET)
	@ResponseBody
	public ResultData getPurchaseInfoByOrgCode(
			@PathVariable(value = "orgCode") String orgCode) {
		logger.info("【关联公司查询公司信息，公司编码】" + orgCode);
		PurchaserAndImageDTO purchaser = purchaserService
				.findPurchaserAndImageByOrgCode(orgCode.trim());
		if (purchaser == null) {
			logger.info("【关联公司查询公司信息，公司编码】" + orgCode + "【，没有查询到采购商】");
			return new ResultData(false);
		}
		return new ResultData(true, purchaser);
	}

	/**
	 * 关联已有公司
	 * @param orgCode
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "relate", method = RequestMethod.POST)
	@ResponseBody
	public ResultData relate(@RequestParam(value = "orgCode") String orgCode,
	                         @RequestParam(value = "parentId") String parentId) {
		return purchaserService.relateExistedPurchaser(orgCode, parentId);
	}

	/**
	 * 核对已有账户
	 * @param orgName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkOrgNameIsExist/{orgName}", method = RequestMethod.GET)
	public Boolean checkAccountIsExist(@PathVariable("orgName") String orgName) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_orgName", orgName);
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		logger.info("【查询采购商名称是否重复，用户输入的采购商名称：】" + orgName);
		List<Purchaser> purchasers = purchaserService.findAll(params,
				Purchaser.class);
		if (CollectionUtils.isNotEmpty(purchasers)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * 查看员工是否有权限查看或操作采购商登记信息，如果没有权限，跳转到对应页面提示
	 */
	private String hasRightToOperatePurchaser() {
		String employeeId = UserUtil.getUserFromSession().getCurrentUserId();
		Employee employee = employeeService.findByPK(employeeId);
		List<Role> roles = employee.getRoleList();
		boolean flag = false;
		String purchaserAdministrator = SystemConstants.PURCHASER_ADMINISTRATOR;
		for (Role role : roles) {
			String code = role.getRoleCode();
			if (purchaserAdministrator.equals(code)) {
				flag = true;
				break;
			}
		}
		if (flag) {
			return null;
		} else {
			return "common/401";
		}
	}

	/**
	 * 注册表
	 * @return
	 */
	@RequestMapping(value = "/registerPurchasersView")
	public String reigstList() {
		String result = hasRightToOperatePurchaser();
		if (result != null) {
			return result;
		} else {
			return "sysmgr/purchaser/registList";
		}
	}

	/**
	 *
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/registerPurchasers", method = RequestMethod.POST)
	@ResponseBody
	public String page(Integer curPage, Integer pageSize) {
		String result = hasRightToOperatePurchaser();
		if (result != null) {
			return "您没有权限查看该资源";
		} else {
			return JsonUtils.toJson(shopPurchaserService.getPurchaserPage(curPage, pageSize));
		}
	}
}
