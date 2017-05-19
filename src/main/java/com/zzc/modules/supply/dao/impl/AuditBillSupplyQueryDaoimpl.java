package com.zzc.modules.supply.dao.impl;

import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum;
import com.zzc.modules.supply.dao.AuditBillSupplyQueryDao;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

@Repository("auditBillSupplyQueryDao")
public class AuditBillSupplyQueryDaoimpl implements AuditBillSupplyQueryDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 获得供应商申请
     * @param orgId
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public List<AuditRecord> findSubmittedforSupply(String orgId, Integer pageSize, Integer curPage) {
        // TODO Auto-generated method stub
        // StringBuilder sql = new StringBuilder("from AuditBill a where a.status=:status and
        // a.auditStatus!=:auditStatus ");
        // sql.append(" and a.sysProductSKU.supplierOrg.id=:orgId ");
        // sql.append(" order by a.createTime desc");
        // Query query = entityManager.createQuery(sql.toString(), AuditBill.class);
        // query.setParameter("status", CommonStatusEnum.有效.getValue());
        // query.setParameter("auditStatus", SupplyAuditBillStatusEnum.TO_ADJUSTED.getCode());
        // query.setParameter("orgId", orgId);
        // Integer pageNum = curPage - 1;
        // int total = query.getResultList().size();
        // int firstResult = pageNum * pageSize;
        // if (firstResult < total && pageSize.intValue() > 0) {
        // query.setFirstResult(firstResult);
        // query.setMaxResults(pageSize);
        // }
        // return query.getResultList();

        Integer pageNum = curPage - 1;
        int firstResult = pageNum * pageSize;

        int lastResult = curPage * pageSize;

        StringBuilder sql = new StringBuilder(
                "select r.* from SYS_AUDIT_BILL a, SYS_AUDIT_RECORD r,SYS_PRODUCT_SKU s where a.id = r.audit_bill_id and  a.status=:status and a.status=r.status and a.status =s.status " +
                        " and r.sys_product_sku_id = s.id "
                        + " and a.sys_product_sku_id = s.id and s.supply_org_id=:orgId and a.audit_status <>:auditStatus and r.create_time in ("
                        + " select max(m.create_time) from SYS_AUDIT_RECORD m,ES_SUPPLIER_USER u,SYS_PRODUCT_SKU s2 where"
                        + "  m.create_id = u.id and u.supplier_org_id = s2.supply_org_id and u.supplier_org_id=:morgId  group by m.audit_bill_id ) group by r.audit_bill_id order by r.create_time desc limit :start,:end ");
        Query query = entityManager.createNativeQuery(sql.toString(), AuditRecord.class);
        query.setParameter("status", CommonStatusEnum.有效.getValue());
        query.setParameter("orgId", orgId);
        query.setParameter("morgId", orgId);
        query.setParameter("start", firstResult);
        query.setParameter("end", lastResult);
        query.setParameter("auditStatus", SupplyAuditBillStatusEnum.TO_ADJUSTED.getCode());

        return query.getResultList();
    }

    /**
     * 获得供应商申请数量
     * @param orgId
     * @return
     */
    @Override
    public BigDecimal findSubmittedCountforSupply(String orgId) {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder(
                "select sum(t.cnt) from (select count(r.id) cnt from SYS_AUDIT_BILL a, SYS_AUDIT_RECORD r,SYS_PRODUCT_SKU s where a.id = r.audit_bill_id and  a.status=:status and a.status=r.status and a.status =s.status " +
                        " and r.sys_product_sku_id = s.id "
                        + " and a.sys_product_sku_id = s.id and s.supply_org_id=:orgId and a.audit_status <>:auditStatus and r.create_time in ("
                        + " select max(m.create_time) from SYS_AUDIT_RECORD m,ES_SUPPLIER_USER u,SYS_PRODUCT_SKU s2 where"
                        + "  m.create_id = u.id and u.supplier_org_id = s2.supply_org_id and u.supplier_org_id=:morgId  group by m.audit_bill_id ) group by r.audit_bill_id order by r.create_time desc ) t ");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("status", CommonStatusEnum.有效.getValue());
        query.setParameter("orgId", orgId);
        query.setParameter("morgId", orgId);
        query.setParameter("auditStatus", SupplyAuditBillStatusEnum.TO_ADJUSTED.getCode());
        return (BigDecimal) query.getSingleResult();
    }

    /**
     * 获得供应商最新审核结果
     * @param currentUserId
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public List<AuditBill> findNeedAdjustforSupply(String currentUserId, Integer pageSize, Integer curPage) {
        StringBuilder hql = new StringBuilder("from AuditBill a where ");
        generateWhereHql(hql);
        hql.append(" order by a.modifiedTime asc");
        Query query = entityManager.createQuery(hql.toString(), AuditBill.class);
        setQueryParameters(query, currentUserId);
        query.setFirstResult((curPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    /**
     * 生成hql语句
     * @param hql
     * @return
     */
    private StringBuilder generateWhereHql(StringBuilder hql) {
        hql.append(" a.status=:status and a.auditStatus=:auditStatus ");
        hql.append(" and a.createId=:currentUserId ");
        return hql;
    }

    /**
     * 设置query参数
     * @param query
     * @param currentUserId
     */
    private void setQueryParameters(Query query, String currentUserId) {
        query.setParameter("status", CommonStatusEnum.有效.getValue());
        query.setParameter("auditStatus", SupplyAuditBillStatusEnum.TO_ADJUSTED.getCode());
        query.setParameter("currentUserId", currentUserId);
    }

    @Override
    public Long findNeedAdjustCountforSupply(String currentUserId) {
        StringBuilder hql = new StringBuilder("select count(a.id) from AuditBill a where ");
        generateWhereHql(hql);
        Query query = entityManager.createQuery(hql.toString());
        setQueryParameters(query, currentUserId);
        return (Long) query.getSingleResult();
    }

}
