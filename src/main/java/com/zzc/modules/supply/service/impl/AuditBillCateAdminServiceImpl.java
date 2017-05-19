package com.zzc.modules.supply.service.impl;

import com.zzc.common.page.PageForShow;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.enums.AuditRecordTypeEnum;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillShowStatusEnum;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum;
import com.zzc.modules.sysmgr.product.service.CategoryChargeService;
import com.zzc.modules.sysmgr.product.web.vo.CategoryChargeVO;
import com.zzc.modules.supply.dao.AuditBillCateAdminQueryDao;
import com.zzc.modules.supply.dao.AuditBillDao;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;
import com.zzc.modules.supply.entity.SysProductSKU;
import com.zzc.modules.supply.service.AuditBillCateAdminService;
import com.zzc.modules.supply.service.AuditRecordService;
import com.zzc.modules.supply.service.SupplyProductSKUService;
import com.zzc.modules.supply.vo.AuditBillVO;
import com.zzc.modules.supply.vo.SupplyProductSKUVO;
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

@Service("auditBillCateAdminService")
public class AuditBillCateAdminServiceImpl extends BaseCrudServiceImpl<AuditBill> implements AuditBillCateAdminService {

    private AuditBillDao auditBillDao;

    @Autowired
    private AuditBillCateAdminQueryDao auditBillCateAdminQueryDao;

    @Autowired
    private AuditRecordService auditRecordService;

    @Autowired
    private SupplierUserService supplierUserService;

    @Autowired
    private SupplyProductSKUService supplyProductSKUService;

    @Autowired
    private CategoryChargeService categoryChargeService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @Autowired
    public AuditBillCateAdminServiceImpl(BaseDao<AuditBill> baseDao) {
        super(baseDao);
        this.auditBillDao = (AuditBillDao) baseDao;
    }

    /**
     * 品类管理员待处理
     * @param currentUserId
     * @param nameOrCode
     * @param orgName
     * @param brandName
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public PageForShow<AuditBillVO> findPendingforCateAdmin(String currentUserId, String nameOrCode, String orgName, String brandName,
            Integer pageSize, Integer curPage) {
        // TODO Auto-generated method stub
        List<AuditBillVO> list = new ArrayList<AuditBillVO>();
        List<AuditBill> auditBills = auditBillCateAdminQueryDao
                .findPendingforCateAdmin(currentUserId, nameOrCode, orgName, brandName, pageSize, curPage);
        Long count = auditBillCateAdminQueryDao.findPendingCountforCateAdmin(currentUserId, nameOrCode, orgName, brandName);
        // list 转page
        for (AuditBill auditBill : auditBills) {
            AuditBillVO auditBillVO = new AuditBillVO();
            auditBillVO = auditBilltoAuditBillVO(auditBill);
            Map<String, Object> params = new HashMap<>();
            params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
            params.put("AND_EQ_auditBill.id", auditBill.getId());
            List<AuditRecord> auditRecords = auditRecordService.findAll(params, AuditRecord.class, "DESC", "createTime");
            if (auditRecords.isEmpty()) {
                auditBillVO.setAuditStatus(SupplyAuditBillShowStatusEnum.MANAGER_AUDITING.getName());
            } else {
                if (null == auditRecords.get(0).getType()) {
                    auditBillVO.setAuditStatus(SupplyAuditBillShowStatusEnum.MANAGER_AUDITING.getName());
                }
                if (null != auditRecords.get(0).getType()
                        && auditRecords.get(0).getType() == AuditRecordTypeEnum.CATEGORY_MAJORDOMO_toADMINISTRATOR.getCode()) {
                    auditBillVO.setAuditStatus(SupplyAuditBillShowStatusEnum.TO_ADMIN_ADJUSTED.getName());
                }
                if (null != auditRecords.get(0).getType()
                        && auditRecords.get(0).getType() != AuditRecordTypeEnum.CATEGORY_MAJORDOMO_toADMINISTRATOR.getCode()) {
                    auditBillVO.setAuditStatus(this.getShowStatus(auditBill.getAuditStatus()));
                }
            }
            list.add(auditBillVO);
        }
        PageForShow<AuditBillVO> p = PageForShow.newInstanceFromSpringPage(list, curPage, count);
        return p;
    }

    /**
     * 品类管理员已处理
     * @param currentUserId
     * @param nameOrCode
     * @param orgName
     * @param brandName
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public PageForShow<AuditBillVO> findProcessedforCateAdmin(String currentUserId, String nameOrCode, String orgName, String brandName,
            Integer pageSize, Integer curPage) {
        // TODO Auto-generated method stub
        List<AuditBillVO> list = new ArrayList<AuditBillVO>();
        List<AuditRecord> auditRecords = auditBillCateAdminQueryDao
                .findProcessedforCateAdmin(currentUserId, nameOrCode, orgName, brandName, pageSize, curPage);
        Long count = auditBillCateAdminQueryDao
                .findProcessedCountforCateAdmin(currentUserId, nameOrCode, orgName, brandName) == null ? 0L : auditBillCateAdminQueryDao
                        .findProcessedCountforCateAdmin(currentUserId, nameOrCode, orgName, brandName).longValue();
        // list 转page
        for (AuditRecord auditRecord : auditRecords) {
            AuditBillVO auditBillVO = new AuditBillVO();
            auditBillVO = auditRecordtoAuditBillVO(auditRecord);
            auditBillVO.setAuditStatus(this.getShowStatus(auditRecord.getAuditBill().getAuditStatus()));
            list.add(auditBillVO);
        }
        PageForShow<AuditBillVO> p = PageForShow.newInstanceFromSpringPage(list, curPage, count);
        return p;
    }

    private AuditBillVO auditRecordtoAuditBillVO(AuditRecord auditRecord) {
        AuditBillVO auditBillVO = new AuditBillVO();
        AuditBill auditBill = auditRecord.getAuditBill();
        auditBillVO.setId(auditBill.getId());
        auditBillVO.setBrandName(
                auditBill.getSysProductSKU().getBrand().getBrandZHName() + "(" + auditBill.getSysProductSKU().getBrand().getBrandENName() + ")");
        SupplyProductSKUVO supplyProductSKUVO = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
        auditBillVO.setCost(supplyProductSKUVO.getCost());
        auditBillVO.setCreateTime(auditRecord.getCreateTime());
        auditBillVO.setProductCode(supplyProductSKUVO.getProductCode());
        auditBillVO.setProductName(supplyProductSKUVO.getProductName());
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
        // auditBillVO.setApproverName(auditBill.getAdminEmployee().getEmployeeName());
        // auditBillVO.setApproverNumber(auditBill.getAdminEmployee().getMobile());
        auditBillVO.setSupplyOrgName(auditBill.getSysProductSKU().getSupplierOrg().getOrgName());
        auditBillVO.setModifiedTime(auditBill.getModifiedTime());
        if (null != supplierUserService.findByPK(auditBill.getCreateId())) {
            auditBillVO.setCreateName(supplierUserService.findByPK(auditBill.getCreateId()).getSupplierUserName());
        }
        auditBillVO.setStandard(auditBill.getSysProductSKU().getStandard());
        return auditBillVO;
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

    private AuditBillVO auditBilltoAuditBillVO(AuditBill auditBill) {
        AuditBillVO auditBillVO = new AuditBillVO();
        auditBillVO.setId(auditBill.getId());
        auditBillVO.setBrandName(
                auditBill.getSysProductSKU().getBrand().getBrandZHName() + "(" + auditBill.getSysProductSKU().getBrand().getBrandENName() + ")");
        SupplyProductSKUVO supplyProductSKUVO = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
        auditBillVO.setCost(supplyProductSKUVO.getCost());
        auditBillVO.setCreateTime(auditBill.getCreateTime());
        auditBillVO.setProductCode(supplyProductSKUVO.getProductCode());
        auditBillVO.setProductName(supplyProductSKUVO.getProductName());

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
        if (null != auditBill.getSysProductSKU().getStandard()) {
            auditBillVO.setStandard(auditBill.getSysProductSKU().getStandard());
        } else {
            auditBillVO.setStandard(supplyProductSKUVO.getRecommend());
        }

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
     * 审核通过
     * @param auditBillId
     * @param comment
     * @param hasTax
     * @param hasTransportation
     * @param standard
     */
    @Override
    @Transactional
    public void setPass(String auditBillId, String comment, Integer hasTax, Integer hasTransportation, BigDecimal standard) {
        // TODO Auto-generated method stub
        AuditBill auditBill = this.findByPK(auditBillId);
        // 审批不通过，进入待调整状态
        auditBill.setAuditStatus(SupplyAuditBillStatusEnum.BOSS_AUDITING.getCode());
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
        auditRecord.setType(AuditRecordTypeEnum.CATEGORY_ADMINISTRATOR_toMAJORDOMO.getCode());
        auditRecordService.create(auditRecord);

        SysProductSKU sysProductSKU = resultAuditBill.getSysProductSKU();
        sysProductSKU.setStandard(standard);
        supplyProductSKUService.update(sysProductSKU);
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
        // 审批通过，进入待品类总监审批状态
        auditBill.setAuditStatus(SupplyAuditBillStatusEnum.TO_ADJUSTED.getCode());
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
        auditRecord.setType(AuditRecordTypeEnum.CATEGORY_ADMINISTRATOR_toSUPPLY_USER.getCode());
        auditRecordService.create(auditRecord);

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
	public SupplyProductSKUVO findSubProductDetailForCateAdmin(String auditBillId) {
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

	    if (auditRecords.size() > 0) {
	       supplyProductSKUVO.setHasTax(auditRecords.get(0).getHasTax());
	       supplyProductSKUVO.setHasTransportation(auditRecords.get(0).getHasTransportation());
	    }

		return supplyProductSKUVO;
	}
}
