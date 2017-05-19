package com.zzc.modules.supply.service.impl;

import com.zzc.common.page.PageForShow;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.supply.dao.AuditBillCateDirectorQueryDao;
import com.zzc.modules.supply.dao.AuditBillDao;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;
import com.zzc.modules.supply.entity.SysProductSKU;
import com.zzc.modules.supply.service.AuditBillCateDirectorService;
import com.zzc.modules.supply.service.AuditRecordService;
import com.zzc.modules.supply.service.ProductShelvesByApprovalService;
import com.zzc.modules.supply.service.SupplyProductSKUService;
import com.zzc.modules.supply.vo.AuditBillVO;
import com.zzc.modules.supply.vo.SupplyProductSKUVO;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.enums.AuditBillOperationTypeEnum;
import com.zzc.modules.sysmgr.enums.AuditRecordTypeEnum;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillShowStatusEnum;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum;
import com.zzc.modules.sysmgr.product.entity.ProductSKU;
import com.zzc.modules.sysmgr.product.service.CategoryChargeService;
import com.zzc.modules.sysmgr.product.service.ProductPriceActionService;
import com.zzc.modules.sysmgr.product.service.vo.ProductPriceActionVO;
import com.zzc.modules.sysmgr.product.web.vo.CategoryChargeVO;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.service.RoleService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("auditBillCateDirectorService")
public class AuditBillCateDirectorServiceImpl extends BaseCrudServiceImpl<AuditBill> implements AuditBillCateDirectorService {

	private AuditBillDao auditBillDao;

	@Autowired
	private AuditBillCateDirectorQueryDao auditBillCateDirectorQueryDao;

	@Autowired
	private AuditRecordService auditRecordService;

	@Autowired
	private ProductShelvesByApprovalService productShelvesByApprovalService;

	@Autowired
	private SupplyProductSKUService supplyProductSKUService;

	@Autowired
	private SupplierUserService supplierUserService;

	@Autowired
	private CategoryChargeService categoryChargeService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ProductPriceActionService productPriceActionService;

	@Autowired
	public AuditBillCateDirectorServiceImpl(BaseDao<AuditBill> baseDao) {
		super(baseDao);
		this.auditBillDao = (AuditBillDao) baseDao;
	}

	/**
	 * 品类主管待处理
	 * @param currentUserId
	 * @param nameOrCode
	 * @param orgName
	 * @param brandName
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	@Override
	public PageForShow<AuditBillVO> findPendingforCateDirector(String currentUserId, String nameOrCode, String orgName, String brandName,
	                                                           Integer pageSize, Integer curPage) {
		// TODO Auto-generated method stub
		List<AuditBillVO> list = new ArrayList<AuditBillVO>();
		List<AuditBill> auditBills = auditBillCateDirectorQueryDao
				.findPendingforCateDirector(currentUserId, nameOrCode, orgName, brandName, pageSize, curPage);
		Long count = auditBillCateDirectorQueryDao.findPendingCountforCateDirector(currentUserId, nameOrCode, orgName, brandName);
		// list 转page
		for (AuditBill auditBill : auditBills) {
			AuditBillVO auditBillVO = new AuditBillVO();
			auditBillVO = this.auditBilltoAuditBillVO(auditBill);
			auditBillVO.setAuditStatus(this.getShowStatus(auditBill.getAuditStatus()));
			list.add(auditBillVO);
		}
		PageForShow<AuditBillVO> p = PageForShow.newInstanceFromSpringPage(list, curPage, count);
		return p;
	}

	/**
	 *
	 * @param auditBill
	 * @return
	 */
	private AuditBillVO auditBilltoAuditBillVO(AuditBill auditBill) {
		AuditBillVO auditBillVO = new AuditBillVO();
		auditBillVO.setId(auditBill.getId());
		auditBillVO.setBrandName(
				auditBill.getSysProductSKU().getBrand().getBrandZHName() + "(" + auditBill.getSysProductSKU().getBrand().getBrandENName() + ")");
		SupplyProductSKUVO supplyProductSKUVO = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
		auditBillVO.setCost(supplyProductSKUVO.getCost());

		auditBillVO.setCreateTime(auditBill.getCreateTime());
		auditBillVO.setProductCode(auditBill.getSysProductSKU().getProductCode());
		auditBillVO.setProductName(auditBill.getSysProductSKU().getProductName());
		if (null != auditBill.getAdminEmployee()) {
			auditBillVO.setApproverName(auditBill.getAdminEmployee().getEmployeeName());
			auditBillVO.setApproverNumber(auditBill.getAdminEmployee().getMobile());
		} else {
			List<CategoryChargeVO> categoryChargeVOs = categoryChargeService.getCategoryChargeByCateId(supplyProductSKUVO.getSecondLevelCateId());
			for (CategoryChargeVO categoryChargeVO : categoryChargeVOs) {
				Employee employee = employeeService.findByPK(categoryChargeVO.getEmployeeId());
				if (isCategoryCharge(employee)) {
					auditBill.setAdminEmployee(employee);
					auditBillVO.setApproverName(employee.getEmployeeName());
					auditBillVO.setApproverNumber(employee.getMobile());
				} else {
					auditBill.setDirectorEmployee(employee);
				}
			}
			this.update(auditBill);
		}
		auditBillVO.setSupplyOrgName(auditBill.getSysProductSKU().getSupplierOrg().getOrgName());
		auditBillVO.setModifiedTime(auditBill.getModifiedTime());
		if (null != supplierUserService.findByPK(auditBill.getCreateId())) {
			auditBillVO.setCreateName(supplierUserService.findByPK(auditBill.getCreateId()).getSupplierUserName());
		}
		auditBillVO.setStandard(auditBill.getSysProductSKU().getStandard());
		return auditBillVO;
	}

	/**
	 *
	 * @param auditRecord
	 * @return
	 */
	private AuditBillVO auditRecordtoAuditBillVO(AuditRecord auditRecord) {
		AuditBillVO auditBillVO = new AuditBillVO();
		AuditBill auditBill = auditRecord.getAuditBill();
		auditBillVO.setId(auditBill.getId());
		auditBillVO.setBrandName(
				auditBill.getSysProductSKU().getBrand().getBrandZHName() + "(" + auditBill.getSysProductSKU().getBrand().getBrandENName() + ")");
		SupplyProductSKUVO supplyProductSKUVO = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
		auditBillVO.setCost(supplyProductSKUVO.getCost());

		auditBillVO.setCreateTime(auditRecord.getCreateTime());
		auditBillVO.setProductCode(auditBill.getSysProductSKU().getProductCode());
		auditBillVO.setProductName(auditBill.getSysProductSKU().getProductName());
		if (null != auditBill.getAdminEmployee()) {
			auditBillVO.setApproverName(auditBill.getAdminEmployee().getEmployeeName());
			auditBillVO.setApproverNumber(auditBill.getAdminEmployee().getMobile());
		} else {
			List<CategoryChargeVO> categoryChargeVOs = categoryChargeService.getCategoryChargeByCateId(supplyProductSKUVO.getSecondLevelCateId());
			for (CategoryChargeVO categoryChargeVO : categoryChargeVOs) {
				Employee employee = employeeService.findByPK(categoryChargeVO.getEmployeeId());
				if (isCategoryCharge(employee)) {
					auditBill.setAdminEmployee(employee);
					auditBillVO.setApproverName(employee.getEmployeeName());
					auditBillVO.setApproverNumber(employee.getMobile());
				} else {
					auditBill.setDirectorEmployee(employee);
				}
			}
			this.update(auditBill);
		}
		auditBillVO.setSupplyOrgName(auditBill.getSysProductSKU().getSupplierOrg().getOrgName());
		auditBillVO.setModifiedTime(auditBill.getModifiedTime());
		if (null != supplierUserService.findByPK(auditBill.getCreateId())) {
			auditBillVO.setCreateName(supplierUserService.findByPK(auditBill.getCreateId()).getSupplierUserName());
		}
		auditBillVO.setStandard(auditBill.getSysProductSKU().getStandard());
		return auditBillVO;
	}

	/**
	 * 判断审核人员类别
	 * @param employee
	 * @return
	 */
	private boolean isCategoryCharge(Employee employee) {
		boolean isCategoryCharge = false;
		List<Role> roles = roleService.findRolesByUser(employee);
		for (Role role : roles) {
			if (SystemConstants.CATEGORY_ADMINISTRATOR.equals(role.getRoleCode())) {
				isCategoryCharge = true;
			}
		}
		return isCategoryCharge;
	}

	/**
	 * 品类管理员已处理
	 * @param currentUserId
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	@Override
	public PageForShow<AuditBillVO> findProcessedforCateDirector(String currentUserId, Integer pageSize, Integer curPage) {
		// TODO Auto-generated method stub
		List<AuditBillVO> list = new ArrayList<AuditBillVO>();
		List<AuditRecord> auditRecords = auditBillCateDirectorQueryDao.findProcessedforCateDirector(currentUserId, pageSize, curPage);

		Long count = auditBillCateDirectorQueryDao.findProcessedCountforCateDirector(currentUserId) == null ? 0L : auditBillCateDirectorQueryDao.findProcessedCountforCateDirector(currentUserId).longValue();
		// list 转page
		for (AuditRecord auditRecord : auditRecords) {
			AuditBillVO auditBillVO = new AuditBillVO();
			auditBillVO = auditRecordtoAuditBillVO(auditRecord);
			if (null == auditRecord.getType()) {
				auditBillVO.setAuditStatus(SupplyAuditBillShowStatusEnum.MANAGER_AUDITING.getName());
			}
			if (null != auditRecord.getType() && auditRecord.getType() == AuditRecordTypeEnum.CATEGORY_MAJORDOMO_toADMINISTRATOR.getCode()) {
				auditBillVO.setAuditStatus(SupplyAuditBillShowStatusEnum.TO_ADMIN_ADJUSTED.getName());
			}
			if (null != auditRecord.getType() && auditRecord.getType() != AuditRecordTypeEnum.CATEGORY_MAJORDOMO_toADMINISTRATOR.getCode()) {
				auditBillVO.setAuditStatus(this.getShowStatus(auditRecord.getAuditBill().getAuditStatus()));
			}
			list.add(auditBillVO);
		}
		PageForShow<AuditBillVO> p = PageForShow.newInstanceFromSpringPage(list, curPage, count);
		return p;
	}

	/**
	 * 显示状态
	 * @param status
	 * @return
	 */
	private String getShowStatus(Integer status) {
		String strStatus = "";
		if (status == SupplyAuditBillStatusEnum.MANAGER_AUDITING.getCode()) {
			strStatus = SupplyAuditBillShowStatusEnum.MANAGER_AUDITING.getName();
		} else if (status == SupplyAuditBillStatusEnum.BOSS_AUDITING.getCode()) {
			strStatus = SupplyAuditBillShowStatusEnum.BOSS_AUDITING.getName();
		} else if (status == SupplyAuditBillStatusEnum.TO_ADJUSTED.getCode()) {
			strStatus = SupplyAuditBillShowStatusEnum.TO_ADJUSTED.getName();
		} else if (status == SupplyAuditBillStatusEnum.PASS.getCode()) {
			strStatus = SupplyAuditBillShowStatusEnum.PASS.getName();
		} else if (status == SupplyAuditBillStatusEnum.DELETE.getCode()) {
			strStatus = SupplyAuditBillShowStatusEnum.DELETE.getName();
		}
		return strStatus;
	}

	/**
	 * 审核通过
	 * @param displayUserName
	 * @param auditBillId
	 * @param comment
	 * @param hasTax
	 * @param hasTransportation
	 * @param standard
	 */
	@Override
	@Transactional
	public void setPass(String displayUserName, String auditBillId, String comment, Integer hasTax, Integer hasTransportation, BigDecimal standard) {
		// TODO Auto-generated method stub
		AuditBill auditBill = this.findByPK(auditBillId);
		// 审批通过，进入审批成功状态
		auditBill.setAuditStatus(SupplyAuditBillStatusEnum.PASS.getCode());

		AuditBill resultAuditBill = this.update(auditBill);

		AuditRecord auditRecord = new AuditRecord();
		auditRecord.setAuditBill(resultAuditBill);
		auditRecord.setSysProductSKU(resultAuditBill.getSysProductSKU());
		auditRecord.setComment(comment);
		auditRecord.setStatus(CommonStatusEnum.有效.getValue());
		if (hasTax != null) {
			auditRecord.setHasTax(hasTax);
		}
		if (hasTransportation != null) {
			auditRecord.setHasTransportation(hasTransportation);
		}
		if (standard != null) {
			auditRecord.setStandard(standard);
		}
		auditRecord.setType(AuditRecordTypeEnum.CATEGORY_MAJORDOMO_toPass.getCode());
		AuditRecord resultAuditRecord = auditRecordService.create(auditRecord);


		ProductSKU productSKU = new ProductSKU();
		SupplyProductSKUVO supplyProductSKUVO = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
		if (resultAuditBill.getOperationType() == AuditBillOperationTypeEnum.OFF_SHELVE.getValue()) {
//            下架
			productSKU = productShelvesByApprovalService.productOFFShelves(resultAuditBill, resultAuditRecord, supplyProductSKUVO);
		} else {
//          商品上架
			productSKU = productShelvesByApprovalService.productShelves(resultAuditBill, resultAuditRecord, supplyProductSKUVO);
			saveProductPriceAction(displayUserName, auditBillId, hasTax, hasTransportation, standard, productSKU, supplyProductSKUVO);
		}
		SysProductSKU sysProductSKU = resultAuditBill.getSysProductSKU();
		sysProductSKU.setProductSKUId(productSKU.getId());
		sysProductSKU.setStandard(standard);
		supplyProductSKUService.update(sysProductSKU);


	}

	/**
	 * 商品价格变更记录
	 *
	 * @param displayUserName    品类总监姓名
	 * @param auditBillId        审批单id
	 * @param hasTax             是否含税
	 * @param hasTransportation  是否含运费
	 * @param standard           标价
	 * @param productSKU
	 * @param supplyProductSKUVO
	 */
	private void saveProductPriceAction(String displayUserName, String auditBillId, Integer hasTax, Integer hasTransportation, BigDecimal standard, ProductSKU productSKU, SupplyProductSKUVO supplyProductSKUVO) {
		ProductPriceActionVO productPriceActionVO = new ProductPriceActionVO();
		productPriceActionVO.setCost(String.valueOf(supplyProductSKUVO.getCost()));
		productPriceActionVO.setHasTax(hasTax);
		productPriceActionVO.setHasTransportation(hasTransportation);
		productPriceActionVO.setLevel1(String.valueOf(standard));
		productPriceActionVO.setLevel2(String.valueOf(standard));
		productPriceActionVO.setLevel3(String.valueOf(standard));
		productPriceActionVO.setStandard(String.valueOf(standard));
		Map<String, Object> recordParams = new HashMap<>();
		recordParams.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		recordParams.put("AND_EQ_type", AuditRecordTypeEnum.CATEGORY_ADMINISTRATOR_toMAJORDOMO.getCode());
		recordParams.put("AND_EQ_auditBill.id", auditBillId);
		List<AuditRecord> auditRecords = auditRecordService.findAll(recordParams, AuditRecord.class, "desc", "createTime");
		AuditRecord adminAuditRecord = auditRecords.get(0);
		BigDecimal adminStandard = adminAuditRecord.getStandard();
		if (adminStandard.compareTo(standard) == 0) { // 如果品类管理员提交的标价和总监提交的标价相同, 价格维护操作人为品类管理员, 反之为品类总监
			productPriceActionVO.setOperatorName(employeeService.findByPK(adminAuditRecord.getCreateId()).getEmployeeName());
		} else {
			productPriceActionVO.setOperatorName(displayUserName);
		}
		productPriceActionService.saveProductPriceAction(productSKU.getId(), supplyProductSKUVO.getThirdLevelCateId(), productPriceActionVO);
	}

	/**
	 * 审核不通过
	 * @param auditBillId
	 * @param comment
	 * @param hasTax
	 * @param hasTransportation
	 * @param standard
	 */
	@Override
	@Transactional
	public void setNoPass(String auditBillId, String comment, Integer hasTax, Integer hasTransportation, BigDecimal standard) {
		// TODO Auto-generated method stub
		AuditBill auditBill = this.findByPK(auditBillId);
		// 审批不通过，进入管理员待调整状态
		/**
		 * 审批单更新
		 */
		auditBill.setAuditStatus(SupplyAuditBillStatusEnum.MANAGER_AUDITING.getCode());
		AuditBill resultAuditBill = this.update(auditBill);

		/**
		 * 审批记录保存
		 */
		AuditRecord auditRecord = new AuditRecord();
		auditRecord.setAuditBill(resultAuditBill);
		auditRecord.setSysProductSKU(resultAuditBill.getSysProductSKU());
		auditRecord.setComment(comment);
		auditRecord.setStatus(CommonStatusEnum.有效.getValue());
		if (hasTax != null) {
			auditRecord.setHasTax(hasTax);
		}
		if (hasTransportation != null) {
			auditRecord.setHasTransportation(hasTransportation);
		}
		if (standard!= null) {
			auditRecord.setStandard(standard);
		}
		auditRecord.setType(AuditRecordTypeEnum.CATEGORY_MAJORDOMO_toADMINISTRATOR.getCode());
		AuditRecord resultAuditRecord = auditRecordService.create(auditRecord);

		SysProductSKU sysProductSKU = resultAuditBill.getSysProductSKU();
		sysProductSKU.setStandard(standard);
		supplyProductSKUService.update(sysProductSKU);
	}

	/**
	 * 获得下架商品详情
	 * @param auditBillId
	 * @return
	 */
	@Override
	public SupplyProductSKUVO findSubProductDetailForCateDirector(String auditBillId) {
		// TODO Auto-generated method stub
		AuditBill auditBill = this.findByPK(auditBillId);

		SupplyProductSKUVO supplyProductSKUVO = new SupplyProductSKUVO();

		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_auditBill.id", auditBillId);
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		List<AuditRecord> auditRecords = auditRecordService.findAll(params, AuditRecord.class, "DESC", "createTime");

		if (auditBill != null) {
			supplyProductSKUVO = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
			supplyProductSKUVO.setStandard(auditBill.getSysProductSKU().getStandard());
		}
		if (auditRecords.size() > 1) {
			supplyProductSKUVO.setHasTax(auditRecords.get(0).getHasTax());
			supplyProductSKUVO.setHasTransportation(auditRecords.get(0).getHasTransportation());
		}
		return supplyProductSKUVO;
	}
}
