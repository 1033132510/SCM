package com.zzc.modules.supply.service.impl;

import com.zzc.common.page.PageForShow;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillShowStatusEnum;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum;
import com.zzc.modules.sysmgr.product.service.CategoryChargeService;
import com.zzc.modules.sysmgr.product.web.vo.CategoryChargeVO;
import com.zzc.modules.supply.dao.AuditBillDao;
import com.zzc.modules.supply.dao.AuditBillSupplyQueryDao;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;
import com.zzc.modules.supply.service.AuditBillService;
import com.zzc.modules.supply.service.AuditRecordService;
import com.zzc.modules.supply.vo.AuditBillVO;
import com.zzc.modules.supply.vo.SupplyProductSKUVO;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.service.RoleService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.util.AuditBillStatusUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by chenjiahai on 16/1/19.
 */
@Service("auditBillService")
public class AuditBillServiceImpl extends BaseCrudServiceImpl<AuditBill> implements AuditBillService {

	@Autowired
	private AuditBillDao auditBillDao;

	@Autowired
	private AuditBillSupplyQueryDao auditBillSupplyQueryDao;

	@Autowired
	private AuditRecordService auditRecordService;

	@Autowired
	private CategoryChargeService categoryChargeService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private RoleService roleService;

	@Autowired
	public AuditBillServiceImpl(BaseDao<AuditBill> baseDao) {
		super(baseDao);
		this.auditBillDao = (AuditBillDao) baseDao;
	}

	/**
	 * 获得供应商申请数量
	 * @param orgId
	 * @return
	 */
	@Override
	public PageForShow<AuditBillVO> findSubmittedforSupply(String orgId, Integer pageSize, Integer curPage) {
		// TODO Auto-generated method stub
		List<AuditBillVO> list = new ArrayList<AuditBillVO>();
		List<AuditRecord> auditRecords = auditBillSupplyQueryDao.findSubmittedforSupply(orgId, pageSize, curPage);
		Long count = auditBillSupplyQueryDao.findSubmittedCountforSupply(orgId) == null ? 0L : auditBillSupplyQueryDao.findSubmittedCountforSupply(orgId).longValue();
		// list 转page
		for (AuditRecord auditRecord : auditRecords) {
			AuditBillVO auditBillVO = new AuditBillVO();
			auditBillVO = auditRecordtoAuditBillVO(auditRecord);
			auditBillVO.setAuditStatus(this.getStatusForSupply(auditRecord.getAuditBill().getAuditStatus()));
			list.add(auditBillVO);
		}
		PageForShow<AuditBillVO> p = PageForShow.newInstanceFromSpringPage(list, curPage, count);
		return p;
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
		auditBillVO.setBrandName(auditBill.getSysProductSKU().getBrand().getBrandZHName() + "(" + auditBill.getSysProductSKU().getBrand().getBrandENName() + ")");
		SupplyProductSKUVO supplyProductSKUVO = new SupplyProductSKUVO();
		if (auditBill != null) {
			supplyProductSKUVO = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
		}
		auditBillVO.setCost(supplyProductSKUVO.getCost());
		auditBillVO.setRecommend(supplyProductSKUVO.getRecommend());
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
		auditBillVO.setModifiedTime(auditRecord.getCreateTime());
		return auditBillVO;
	}

	/**
	 *
	 * @param auditBill
	 * @return
	 */
	private AuditBillVO auditBilltoAuditBillVO(AuditBill auditBill) {
		AuditBillVO auditBillVO = new AuditBillVO();
		auditBillVO.setId(auditBill.getId());
		auditBillVO.setBrandName(auditBill.getSysProductSKU().getBrand().getBrandZHName() + "(" + auditBill.getSysProductSKU().getBrand().getBrandENName() + ")");
		SupplyProductSKUVO supplyProductSKUVO = new SupplyProductSKUVO();
		if (auditBill != null) {
			supplyProductSKUVO = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
		}
		auditBillVO.setCost(supplyProductSKUVO.getCost());
		auditBillVO.setRecommend(supplyProductSKUVO.getRecommend());
		auditBillVO.setStandard(supplyProductSKUVO.getStandard());
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
	 * 获得供应商最新审核结果
	 * @param currentUserId
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	@Override
	public PageForShow<AuditBillVO> findNeedAdjustforSupply(String currentUserId, Integer pageSize, Integer curPage) {
		List<AuditBillVO> list = new ArrayList<AuditBillVO>();
		List<AuditBill> auditBills = auditBillSupplyQueryDao.findNeedAdjustforSupply(currentUserId, pageSize, curPage);
		Long count = auditBillSupplyQueryDao.findNeedAdjustCountforSupply(currentUserId);
		// list 转page
		for (AuditBill auditBill : auditBills) {
			AuditBillVO auditBillVO = new AuditBillVO();
			auditBillVO = auditBilltoAuditBillVO(auditBill);
			auditBillVO.setAuditStatus(this.getStatusForSupply(auditBill.getAuditStatus()));
			list.add(auditBillVO);
		}
		return PageForShow.newInstanceFromSpringPage(list, curPage, count);
	}

	/**
	 * 查询处理详情
	 * @param id
	 */
	@Override
	public void findProcessingProductDetail(String id) {
		// TODO
		// 查询反馈信息
		List<AuditRecord> auditRecords = findAuditRecordsByAuditBillId(id);
		// 商品信息
		findSupplyProductSKUByAudtiBillId(id);
		// 审批信息
	}

	/**
	 * 修改审核表状态
	 * @param sysProductSKUId
	 * @return
	 */
	@Override
	public AuditBill updateAuditBillStatusToMangerAuditing(String sysProductSKUId) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_sysProductSKU.id", sysProductSKUId);
		params.put("AND_EQ_auditStatus", SupplyAuditBillStatusEnum.TO_ADJUSTED.getCode());
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		List<AuditBill> auditBills = findAll(params, AuditBill.class);
		AuditBill auditBill = auditBills.get(0);
		auditBill.setAuditStatus(SupplyAuditBillStatusEnum.MANAGER_AUDITING.getCode());
		update(auditBill);
		return auditBill;
	}

	/**
	 * 通过审核单获得供应商产品信息
	 * @param id
	 */
	private void findSupplyProductSKUByAudtiBillId(String id) {
		AuditBill auditBill = findByPK(id);
		if (auditBill == null) {
			return;
		}
		auditBill.getSysProductSKU();
	}

	/**
	 * 通过审核单查看审核结果
	 * @param id
	 * @return
	 */
	private List<AuditRecord> findAuditRecordsByAuditBillId(String id) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		params.put("AND_EQ_auditBill.id", id);
		Page<AuditRecord> auditRecordPage = auditRecordService.findByParams(AuditRecord.class, params, 1, Integer.MAX_VALUE, "ASC", "createTime");
		return CollectionUtils.isEmpty(auditRecordPage.getContent()) ? Collections.EMPTY_LIST : auditRecordPage.getContent();
	}

	/**
	 * 供应商获得审核状态
	 * @param status
	 * @return
	 */
	private String getStatusForSupply(Integer status) {
		String strStatus = "";
		if (status == SupplyAuditBillStatusEnum.MANAGER_AUDITING.getCode()) {
			strStatus = SupplyAuditBillShowStatusEnum.APPROVAL_PENDING.getName();
		} else if (status == SupplyAuditBillStatusEnum.BOSS_AUDITING.getCode()) {
			strStatus = SupplyAuditBillShowStatusEnum.APPROVAL_PENDING.getName();
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
	 * 通过产品id获得审核单
	 * @param sysProductId
	 * @return
	 */
	@Override
	public AuditBill findAuditBillBySysProductId(String sysProductId) {
		List<AuditBill> auditBills = auditBillDao.findAuditBillBySysProductId(AuditBillStatusUtil.getTerminalStatus(), sysProductId);
		if (auditBills != null && auditBills.size() > 0) {
			return auditBills.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 供应商获得商品详情
	 * @param auditBillId
	 * @return
	 */
	@Override
	public SupplyProductSKUVO findSubProductDetailForSupply(String auditBillId) {
		// TODO Auto-generated method stub
		AuditBill auditBill = this.findByPK(auditBillId);
		SupplyProductSKUVO supplyProductSKUVO = new SupplyProductSKUVO();
		if (auditBill != null) {
			supplyProductSKUVO = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
//          供应商成员 查看的是参考价
			supplyProductSKUVO.setStandard(supplyProductSKUVO.getRecommend());
		}
		return supplyProductSKUVO;
	}
}
