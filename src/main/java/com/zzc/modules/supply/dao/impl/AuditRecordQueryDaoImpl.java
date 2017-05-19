package com.zzc.modules.supply.dao.impl;

import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.modules.sysmgr.enums.AuditRecordTypeEnum;
import com.zzc.modules.supply.dao.AuditRecordQueryDao;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("auditRecordQueryDao")
public class AuditRecordQueryDaoImpl implements AuditRecordQueryDao {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 供应商获得审核记录列表
	 * @param auditbillId
	 * @return
	 */
	@Override
	public List<AuditRecord> findAuditRecordListForSupply(String auditbillId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("from AuditRecord a where a.status=:status and a.type in (");
		sql.append(AuditRecordTypeEnum.CATEGORY_ADMINISTRATOR_toSUPPLY_USER.getCode()).append(",")
				.append(AuditRecordTypeEnum.SUPPLY_USER_toADMINISTRATOR.getCode()).append(",").append(AuditRecordTypeEnum.SUPPLY_USER_toDelete.getCode());
		sql.append(")");
		if (StringUtils.isNotEmpty(auditbillId)) {
			sql.append(" and a.auditBill.id=:auditbillId ");
		}
		sql.append(" order by createTime desc");
		Query query = entityManager.createQuery(sql.toString(), AuditRecord.class);
		query.setParameter("status", CommonStatusEnum.有效.getValue());
		if (StringUtils.isNotEmpty(auditbillId)) {
			query.setParameter("auditbillId", auditbillId);
		}
		return query.getResultList();
	}

	/**
	 * 品类管理员审核记录列表
	 * @param auditbillId
	 * @return
	 */
	@Override
	public List<AuditRecord> findAuditRecordListForCateAdmin(String auditbillId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("from AuditRecord a where a.status=:status ");
//        sql.append(AuditRecordTypeEnum.SUPPLY_USER_toADMINISTRATOR.getCode()).append(",")
//                .append(AuditRecordTypeEnum.CATEGORY_ADMINISTRATOR_toMAJORDOMO.getCode())
//                .append(AuditRecordTypeEnum.CATEGORY_MAJORDOMO_toPass.getCode()).append(AuditRecordTypeEnum.SUPPLY_USER_toDelete.getCode())
//                .append(AuditRecordTypeEnum.CATEGORY_ADMINISTRATOR_toSUPPLY_USER.getCode()).append(AuditRecordTypeEnum.);
//        sql.append(")");
		if (StringUtils.isNotEmpty(auditbillId)) {
			sql.append(" and a.auditBill.id=:auditbillId ");
		}
		sql.append(" order by createTime desc");
		Query query = entityManager.createQuery(sql.toString(), AuditRecord.class);
		query.setParameter("status", CommonStatusEnum.有效.getValue());
		if (StringUtils.isNotEmpty(auditbillId)) {
			query.setParameter("auditbillId", auditbillId);
		}
		return query.getResultList();
	}

	/**
	 * 项目主管审核记录列表
	 * @param auditbillId
	 * @return
	 */
	@Override
	public List<AuditRecord> findAuditRecordListForCateDirector(String auditbillId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("from AuditRecord a where a.status=:status ");
		if (StringUtils.isNotEmpty(auditbillId)) {
			sql.append(" and a.auditBill.id=:auditbillId ");
		}
		sql.append(" order by createTime desc");
		Query query = entityManager.createQuery(sql.toString(), AuditRecord.class);
		query.setParameter("status", CommonStatusEnum.有效.getValue());
		if (StringUtils.isNotEmpty(auditbillId)) {
			query.setParameter("auditbillId", auditbillId);
		}
		return query.getResultList();
	}

	/**
	 * 获得下架商品详情
	 * @param auditbillId
	 * @return
	 */
	@Override
	public AuditBill findSubProductDetail(String auditbillId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("from AuditBill a where a.status=:status ");
		if (StringUtils.isNotEmpty(auditbillId)) {
			sql.append(" and a.id=:auditbillId ");
		}
		sql.append(" order by a.createTime desc");
		Query query = entityManager.createQuery(sql.toString(), AuditBill.class);
		query.setParameter("status", CommonStatusEnum.有效.getValue());
		if (StringUtils.isNotEmpty(auditbillId)) {
			query.setParameter("auditbillId", auditbillId);
		}
		return (AuditBill) query.getSingleResult();
	}
}
