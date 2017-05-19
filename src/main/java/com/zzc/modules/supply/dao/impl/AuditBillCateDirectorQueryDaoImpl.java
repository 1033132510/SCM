package com.zzc.modules.supply.dao.impl;

import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum;
import com.zzc.modules.supply.dao.AuditBillCateDirectorQueryDao;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

@Repository("auditBillCateDirectorQueryDao") 
public class AuditBillCateDirectorQueryDaoImpl implements AuditBillCateDirectorQueryDao {

    @PersistenceContext
    private EntityManager entityManager;
    /**
     * //TODO 品类总监获取待审批
     * @param currentUserId
     * @param nameOrCode
     * @param orgName
     * @param brandName
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public List<AuditBill> findPendingforCateDirector(String currentUserId, String nameOrCode, String orgName, String brandName, Integer pageSize,
            Integer curPage) {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder("from AuditBill a where a.status=:status and a.auditStatus=:auditStatus and a.directorEmployee.id =:currentUserId ");
        if(StringUtils.isNotEmpty(nameOrCode)){
            sql.append(" and ( a.sysProductSKU.productCode like :productCode or a.sysProductSKU.productName like :productName )");
        }
        if(StringUtils.isNotEmpty(orgName)){
            sql.append(" and a.sysProductSKU.supplierOrg.orgName like :orgName ");
        }
        if(StringUtils.isNotEmpty(brandName)){
            sql.append(" and (a.sysProductSKU.brand.brandZHName like :brandZHName or a.sysProductSKU.brand.brandENName like :brandENName)");
        }
        
        sql.append(" order by a.modifiedTime asc");
        Query query = entityManager.createQuery(sql.toString(), AuditBill.class);
        query.setParameter("status", CommonStatusEnum.有效.getValue());
        query.setParameter("auditStatus", SupplyAuditBillStatusEnum.BOSS_AUDITING.getCode());
        query.setParameter("currentUserId", currentUserId);
        if(StringUtils.isNotEmpty(nameOrCode)){
            query.setParameter("productCode","%"+nameOrCode+"%");
            query.setParameter("productName", "%"+nameOrCode+"%");
        }
        if(StringUtils.isNotEmpty(orgName)){
            query.setParameter("orgName", "%"+orgName+"%");
        }
        if(StringUtils.isNotEmpty(brandName)){
            query.setParameter("brandZHName", "%"+brandName+"%");
            query.setParameter("brandENName","%"+brandName+"%");
        }
        Integer pageNum = curPage - 1;
        int total = query.getResultList().size();
        int firstResult = pageNum * pageSize;
        if (firstResult < total && pageSize.intValue() > 0) {
            query.setFirstResult(firstResult);
            query.setMaxResults(pageSize);
        }
        return query.getResultList();
    }
    /**
     * //TODO 品类总监获取待审批数量
     * @param currentUserId
     * @param nameOrCode
     * @param orgName
     * @param brandName
     * @return
     */
    @Override
    public Long findPendingCountforCateDirector(String currentUserId, String nameOrCode, String orgName, String brandName) {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder("select count(a.id) from AuditBill a where a.status=:status and a.auditStatus=:auditStatus and a.directorEmployee.id =:currentUserId ");
        if(StringUtils.isNotEmpty(nameOrCode)){
            sql.append(" and ( a.sysProductSKU.productCode like :productCode or a.sysProductSKU.productName like :productName )");
        }
        if(StringUtils.isNotEmpty(orgName)){
            sql.append(" and a.sysProductSKU.supplierOrg.orgName like :orgName ");
        }
        if(StringUtils.isNotEmpty(brandName)){
            sql.append(" and (a.sysProductSKU.brand.brandZHName like :brandZHName or a.sysProductSKU.brand.brandENName like :brandENName)");
        }
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("status", CommonStatusEnum.有效.getValue());
        query.setParameter("auditStatus", SupplyAuditBillStatusEnum.BOSS_AUDITING.getCode());
        query.setParameter("currentUserId", currentUserId);
        if(StringUtils.isNotEmpty(nameOrCode)){
            query.setParameter("productCode","%"+nameOrCode+"%");
            query.setParameter("productName", "%"+nameOrCode+"%");
        }
        if(StringUtils.isNotEmpty(orgName)){
            query.setParameter("orgName", "%"+orgName+"%");
        }
        if(StringUtils.isNotEmpty(brandName)){
            query.setParameter("brandZHName", "%"+brandName+"%");
            query.setParameter("brandENName","%"+brandName+"%");
        }
        return (Long) query.getSingleResult();
    }
    /**
     * //TODO 品类总监获取已处理
     * @param pageSize
     * @param curPage
     * @return
     * @author ping
     */
    @Override
    public List<AuditRecord> findProcessedforCateDirector(String currentUserId, Integer pageSize, Integer curPage) {
        // TODO Auto-generated method stub
        
        Integer pageNum = curPage - 1;
        int firstResult = pageNum * pageSize;
        
        int lastResult = curPage*pageSize;
        
        StringBuilder sql = new StringBuilder(
                "select r.* from SYS_AUDIT_BILL a, SYS_AUDIT_RECORD r where a.id = r.audit_bill_id and  a.status=:status and a.status=r.status and a.category_director_id=:currentUserId "
                        + " and r.create_id=:createId and a.audit_status <>:auditStatus and r.create_time in (select max(m.create_time) from SYS_AUDIT_RECORD m where"
                        + " m.create_id=:mcreateId group by m.audit_bill_id ) group by r.audit_bill_id order by r.create_time desc limit :start,:end ");
        Query query = entityManager.createNativeQuery(sql.toString(), AuditRecord.class);
        query.setParameter("status", CommonStatusEnum.有效.getValue());
        query.setParameter("currentUserId", currentUserId);
        query.setParameter("createId", currentUserId);
        query.setParameter("mcreateId", currentUserId);
        query.setParameter("start", firstResult);
        query.setParameter("end", lastResult);
        query.setParameter("auditStatus", SupplyAuditBillStatusEnum.BOSS_AUDITING.getCode());
        
        return query.getResultList();
    }

    /**
     * //TODO 品类总监获取已处理的数量
     * @return
     */

    @Override
    public BigDecimal findProcessedCountforCateDirector(String currentUserId) {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder(
                "select sum(d.cnt) from (select count(a.id) cnt from SYS_AUDIT_BILL a, SYS_AUDIT_RECORD r where a.id = r.audit_bill_id and  a.status=:status and a.status=r.status and a.category_director_id=:currentUserId "
                        + " and r.create_id=:createId and a.audit_status <>:auditStatus and r.create_time in (select max(m.create_time) from SYS_AUDIT_RECORD m where"
                        + " m.create_id=:mcreateId group by m.audit_bill_id ) group by r.audit_bill_id order by r.create_time desc ) d");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("status", CommonStatusEnum.有效.getValue());
        query.setParameter("currentUserId", currentUserId);
        query.setParameter("createId", currentUserId);
        query.setParameter("mcreateId", currentUserId);
        query.setParameter("auditStatus", SupplyAuditBillStatusEnum.BOSS_AUDITING.getCode());
        return (BigDecimal) query.getSingleResult();
    }

}
