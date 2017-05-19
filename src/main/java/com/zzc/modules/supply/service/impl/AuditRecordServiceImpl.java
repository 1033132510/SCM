package com.zzc.modules.supply.service.impl;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.enums.AuditRecordTypeEnum;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum;
import com.zzc.modules.sysmgr.enums.TaxTypeEnum;
import com.zzc.modules.sysmgr.enums.TransportationTypeEnum;
import com.zzc.modules.supply.dao.AuditRecordDao;
import com.zzc.modules.supply.dao.AuditRecordQueryDao;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;
import com.zzc.modules.supply.entity.SysProductSKU;
import com.zzc.modules.supply.service.AuditRecordService;
import com.zzc.modules.supply.vo.AuditRecordVO;
import com.zzc.modules.supply.vo.ProductPriceVO;
import com.zzc.modules.supply.vo.SupplyProductSKUVO;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierUser;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjiahai on 16/1/19.
 */
@Service("auditRecordService")
public class AuditRecordServiceImpl extends BaseCrudServiceImpl<AuditRecord> implements AuditRecordService {


	@Autowired
	private AuditRecordDao auditRecordDao;

	@Autowired
	private AuditRecordQueryDao auditRecordQueryDao;

	@Autowired
	private SupplierUserService supplierUserService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	public AuditRecordServiceImpl(BaseDao<AuditRecord> baseDao) {
		super(baseDao);
		this.auditRecordDao = (AuditRecordDao) baseDao;
	}

	/**
	 * 创建供应商档案
	 * @param comment
	 * @param auditBill
	 * @param sysProductSKU
	 * @param lastestAuditRecord
	 */
	@Override
	public void createSupplyRecord(String comment, AuditBill auditBill, SysProductSKU sysProductSKU, AuditRecord lastestAuditRecord) {
		AuditRecord auditRecord = new AuditRecord();
		auditRecord.setSysProductSKU(sysProductSKU);
		auditRecord.setAuditBill(auditBill);
		auditRecord.setComment(comment);
		auditRecord.setStandard(sysProductSKU.getStandard());
		auditRecord.setStatus(CommonStatusEnum.有效.getValue());
		auditRecord.setHasTax(lastestAuditRecord.getHasTax());
		auditRecord.setHasTransportation(lastestAuditRecord.getHasTransportation());
		auditRecord.setType(AuditRecordTypeEnum.SUPPLY_USER_toADMINISTRATOR.getCode());
		auditRecordDao.save(auditRecord);
	}

	/**
	 * 查找供应商审核记录列表
	 * @param auditbillId
	 * @return
	 */
	@Override
	public List<AuditRecordVO> findAuditRecordListForSupply(String auditbillId) {
		// TODO Auto-generated method stub
		List<AuditRecord> auditRecords = auditRecordQueryDao.findAuditRecordListForSupply(auditbillId);
		List<AuditRecordVO> auditRecordVOs = new ArrayList<AuditRecordVO>();
		AuditBill auditBill = new AuditBill();
		if (!auditRecords.isEmpty()) {
			auditBill = auditRecords.get(0).getAuditBill();
		}

		if (auditBill.getAuditStatus() == SupplyAuditBillStatusEnum.PASS.getCode()) {
			Employee adminEmployee = auditBill.getAdminEmployee();
			AuditRecordVO auditRecordVO = new AuditRecordVO();
			auditRecordVO.setComment("审批通过");
			auditRecordVO.setCreateTime(auditBill.getModifiedTime());
			auditRecordVO.setApproverName(adminEmployee.getEmployeeName());
			auditRecordVO.setApproverNumber(adminEmployee.getMobile());
			auditRecordVOs.add(auditRecordVO);
		}
		for (int i = 0; i < auditRecords.size(); i++) {
			auditRecordVOs.add(this.getAuditRecords(auditRecords.get(i)));
		}
		return auditRecordVOs;
	}

	/**
	 * 查找品类管理员审核记录列表
	 * @param auditbillId
	 * @return
	 */
	@Override
	public List<AuditRecordVO> findAuditRecordListForCateAdmin(String auditbillId) {
		// TODO Auto-generated method stub
		List<AuditRecord> auditRecords = auditRecordQueryDao.findAuditRecordListForCateAdmin(auditbillId);
		List<AuditRecordVO> auditRecordVOs = new ArrayList<AuditRecordVO>();
		for (int i = 0; i < auditRecords.size(); i++) {
			auditRecordVOs.add(this.getAuditRecords(auditRecords.get(i)));
		}
		return auditRecordVOs;
	}

	/**
	 * 查找品类主管审核记录列表
	 * @param auditbillId
	 * @return
	 */
	@Override
	public List<AuditRecordVO> findAuditRecordListForCateDirector(String auditbillId) {
		// TODO Auto-generated method stub
		List<AuditRecord> auditRecords = auditRecordQueryDao.findAuditRecordListForCateDirector(auditbillId);
		List<AuditRecordVO> auditRecordVOs = new ArrayList<AuditRecordVO>();
		for (int i = 0; i < auditRecords.size(); i++) {
			auditRecordVOs.add(this.getAuditRecords(auditRecords.get(i)));
		}
		return auditRecordVOs;
	}

	/**
	 * 获得审核记录
	 * @param auditRecord
	 * @return
	 */
	private AuditRecordVO getAuditRecords(AuditRecord auditRecord) {
		AuditRecordVO auditRecordVO = new AuditRecordVO();
		auditRecordVO.setId(auditRecord.getId());
		auditRecordVO.setComment(auditRecord.getComment());
		auditRecordVO.setCreateTime(auditRecord.getCreateTime());
		auditRecordVO.setType(auditRecord.getType());
		if (null != auditRecord.getType() && (auditRecord.getType() == AuditRecordTypeEnum.SUPPLY_USER_toADMINISTRATOR.getCode() || auditRecord.getType() == AuditRecordTypeEnum.SUPPLY_USER_toDelete.getCode())) {
			SupplierUser supplierUser = supplierUserService.findByPK(auditRecord.getCreateId());
			auditRecordVO.setApproverName(supplierUser.getSupplierUserName());
			auditRecordVO.setApproverNumber(supplierUser.getContactNumber());
		} else {
			Employee employee = employeeService.findByPK(auditRecord.getCreateId());
			auditRecordVO.setApproverName(employee.getEmployeeName());
			auditRecordVO.setApproverNumber(employee.getUserName());
		}

		return auditRecordVO;
	}

	/**
	 * 查找商品价格
	 * @param auditbillId
	 * @return
	 */
	@Override
	public ProductPriceVO findProductPrice(String auditbillId) {
		// TODO Auto-generated method stub
		AuditBill auditBill = auditRecordQueryDao.findSubProductDetail(auditbillId);
		Map<String, Object> params = new HashMap<String, Object>();
		if (auditbillId != null) {
			params.put("AND_EQ_auditBill.id", auditbillId);
		}
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());

		//通过最新审批记录获取是否含税、含运费
		List<AuditRecord> records = findAll(params, AuditRecord.class, "DESC", "createTime");
		ProductPriceVO productPriceVO = this.getProductPriceVO(auditBill);

		if (!records.isEmpty()) {
			if (null == records.get(0).getHasTax()) {
				productPriceVO.setShasTax(TaxTypeEnum.hasTax.getCode());
			} else {
				productPriceVO.setShasTax(records.get(0).getHasTax());
			}
			if (null == records.get(0).getHasTransportation()) {
				productPriceVO.setShasTransportation(TransportationTypeEnum.hasTransportation.getCode());
			} else {
				productPriceVO.setShasTransportation(records.get(0).getHasTransportation());
			}
		}
		return productPriceVO;
	}

	/**
	 *
	 * @param auditBill
	 * @return
	 */
	private ProductPriceVO getProductPriceVO(AuditBill auditBill) {
		SupplyProductSKUVO supplyProductSKUVO = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
		ProductPriceVO productPriceVO = new ProductPriceVO();
		productPriceVO.setCost(supplyProductSKUVO.getCost());
		productPriceVO.setUnit(supplyProductSKUVO.getUnit());
		productPriceVO.setHasTax(supplyProductSKUVO.getHasTax());
		productPriceVO.setHasTransportation(supplyProductSKUVO.getHasTransportation());
		productPriceVO.setRecommendedPrice(supplyProductSKUVO.getRecommend());
		productPriceVO.setStandard(auditBill.getSysProductSKU().getStandard());
		return productPriceVO;
	}

	/**
	 * 查询最新记录
	 * @param auditbillId
	 * @param createId
	 * @return
	 */
	@Override
	public ProductPriceVO findRecordLatestByCreateId(String auditbillId, String createId) {
		// TODO Auto-generated method stub
		AuditBill auditBill = auditRecordQueryDao.findSubProductDetail(auditbillId);
		Map<String, Object> params = new HashMap<String, Object>();
		if (auditbillId != null) {
			params.put("AND_EQ_auditBill.id", auditbillId);
		}
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());

		params.put("AND_EQ_createId", createId);

		//通过最新审批记录获取是否含税、含运费
		List<AuditRecord> records = findAll(params, AuditRecord.class, "DESC", "createTime");
		ProductPriceVO productPriceVO = this.getProductPriceVO(auditBill);
		if (!records.isEmpty()) {
			productPriceVO.setStandard(records.get(0).getStandard());
			productPriceVO.setComment(records.get(0).getComment());
			if (null == records.get(0).getHasTax()) {
				productPriceVO.setShasTax(TaxTypeEnum.hasTax.getCode());
			} else {
				productPriceVO.setShasTax(records.get(0).getHasTax());
			}
			if (null == records.get(0).getHasTransportation()) {
				productPriceVO.setShasTransportation(TransportationTypeEnum.hasTransportation.getCode());
			} else {
				productPriceVO.setShasTransportation(records.get(0).getHasTransportation());
			}
		}
		return productPriceVO;
	}


}
