package com.zzc.modules.supply.dao.impl;

import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.modules.supply.dao.AuditBillCateAdminQueryDao;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

@Repository("auditBillCateAdminQueryDao")
public class AuditBillCateAdminQueryDaoImpl implements AuditBillCateAdminQueryDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 品类管理员获取待审批
     * @param currentUserId
     * @param nameOrCode
     * @param orgName
     * @param brandName
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public List<AuditBill> findPendingforCateAdmin(String currentUserId, String nameOrCode,String orgName,
                                                   String brandName, Integer pageSize, Integer curPage) {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder(
                "from AuditBill a where a.status=:status and a.auditStatus=:auditStatus and " +
                        "a.adminEmployee.id = :currentUserId ");
        if (StringUtils.isNotEmpty(nameOrCode)) {
            sql.append(" and ( a.sysProductSKU.productCode like (:productCode) or " +
                    "a.sysProductSKU.productName like (:productName) )");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and a.sysProductSKU.supplierOrg.orgName  like (:orgName) ");
        }
        if (StringUtils.isNotEmpty(brandName)) {
            sql.append(" and (a.sysProductSKU.brand.brandZHName like (:brandZHName) or" +
                    " a.sysProductSKU.brand.brandENName like (:brandENName) ) ");
        }

        sql.append(" order by a.modifiedTime asc");
        Query query = entityManager.createQuery(sql.toString(), AuditBill.class);
        query.setParameter("status", CommonStatusEnum.有效.getValue());
        query.setParameter("auditStatus", SupplyAuditBillStatusEnum.MANAGER_AUDITING.getCode());
        query.setParameter("currentUserId", currentUserId);
        if (StringUtils.isNotEmpty(nameOrCode)) {
            query.setParameter("productCode", "%" + nameOrCode + "%");
            query.setParameter("productName", "%" + nameOrCode + "%");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            query.setParameter("orgName", "%" + orgName + "%");
        }
        if (StringUtils.isNotEmpty(brandName)) {
            query.setParameter("brandZHName", "%" + brandName + "%");
            query.setParameter("brandENName", "%" + brandName + "%");
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
     * //TODO 品类管理员获取待审批数量
     * @param currentUserId
     * @param nameOrCode
     * @param orgName
     * @param brandName
     * @return
     */
    @Override
    public Long findPendingCountforCateAdmin(String currentUserId, String nameOrCode, String orgName, String brandName) {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder(
                "select count(a.id) from AuditBill a where a.status=:status and" +
                        " a.auditStatus=:auditStatus and a.adminEmployee.id =:currentUserId ");
        if (StringUtils.isNotEmpty(nameOrCode)) {
            sql.append(" and ( a.sysProductSKU.productCode like (:productCode) or " +
                    "a.sysProductSKU.productName like (:productName) )");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and a.sysProductSKU.supplierOrg.orgName  like (:orgName) ");
        }
        if (StringUtils.isNotEmpty(brandName)) {
            sql.append(" and (a.sysProductSKU.brand.brandZHName like (:brandZHName) or " +
                    "a.sysProductSKU.brand.brandENName like (:brandENName) ) ");
        }
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("status", CommonStatusEnum.有效.getValue());
        query.setParameter("auditStatus", SupplyAuditBillStatusEnum.MANAGER_AUDITING.getCode());
        query.setParameter("currentUserId", currentUserId);
        if (StringUtils.isNotEmpty(nameOrCode)) {
            query.setParameter("productCode", "%" + nameOrCode + "%");
            query.setParameter("productName", "%" + nameOrCode + "%");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            query.setParameter("orgName", "%" + orgName + "%");
        }
        if (StringUtils.isNotEmpty(brandName)) {
            query.setParameter("brandZHName", "%" + brandName + "%");
            query.setParameter("brandENName", "%" + brandName + "%");
        }
        return (Long) query.getSingleResult();
    }
    /**
     * //TODO 品类管理员获取已处理
     * @param pageSize
     * @param curPage
     * @return
     * @author ping
     */
    @Override
    public List<AuditRecord> findProcessedforCateAdmin(String currentUserId, String nameOrCode,
                                                       String orgName, String brandName, Integer pageSize,
            Integer curPage) {
        // TODO Auto-generated method stub
        Integer pageNum = curPage - 1;
        int firstResult = pageNum * pageSize;

        int lastResult = curPage * pageSize;

        StringBuilder sql = new StringBuilder(
                "SELECT r.* FROM SYS_AUDIT_RECORD r INNER JOIN SYS_AUDIT_BILL a ON a.id = r.audit_bill_id AND a.status=r.status " +
                        "INNER JOIN SYS_PRODUCT_SKU k ON a.sys_product_sku_id =k.id AND a.status=k.status AND r.sys_product_sku_id=k.id" +
                        "INNER JOIN ES_SUPPLIER_ORG g ON k.supply_org_id = g.id " +
                        " INNER JOIN ES_BRAND d ON k.brand_id = d.id  a.status=:status and " +
                        " a.category_admin_id=:currentUserId "
                        + " and r.create_id=:createId and a.audit_status <>:auditStatus and " +
                        "r.create_time in (select max(m.create_time) from SYS_AUDIT_RECORD m where"
                        + " m.create_id=:mcreateId group by m.audit_bill_id ) ");
        if (StringUtils.isNotEmpty(nameOrCode)) {
            sql.append(" and ( k.product_name like '%").append(nameOrCode).append("%'");
            sql.append(" or k.product_code like '%").append(nameOrCode).append("%' )");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and g.org_name like '%").append(orgName).append("%'");
        }
        if (StringUtils.isNotEmpty(brandName)) {
            sql.append(" and ( d.brand_en_name like '%").append(brandName.toUpperCase()).append("%'");
            sql.append(" or d.brand_zh_name like '%").append(brandName).append("%' )");
        }

        sql.append(" group by r.audit_bill_id order by r.create_time desc limit :start,:end ");
        Query query = entityManager.createNativeQuery(sql.toString(), AuditRecord.class);
        query.setParameter("status", CommonStatusEnum.有效.getValue());
        query.setParameter("currentUserId", currentUserId);
        query.setParameter("createId", currentUserId);
        query.setParameter("mcreateId", currentUserId);
        query.setParameter("start", firstResult);
        query.setParameter("end", lastResult);
        query.setParameter("auditStatus", SupplyAuditBillStatusEnum.MANAGER_AUDITING.getCode());

        return query.getResultList();
    }
    /**
     * //TODO 品类管理员获取已处理的数量
     * @return
     */
    @Override
    public BigDecimal findProcessedCountforCateAdmin(String currentUserId, String nameOrCode, String orgName, String brandName) {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder(
                "select sum(t.cnt) from ( select count(r.id) cnt from SYS_AUDIT_BILL a, SYS_AUDIT_RECORD r," +
                        "SYS_PRODUCT_SKU k,ES_SUPPLIER_ORG g,ES_BRAND d where "
                        + " k.supply_org_id = g.id and k.brand_id = d.id and a.sys_product_sku_id=" +
                        "k.id and r.sys_product_sku_id=k.id "
                        + " and a.id = r.audit_bill_id and  a.status=:status and " +
                        "a.status=r.status and a.status=k.status and a.category_admin_id=:currentUserId "
                        + " and r.create_id=:createId and a.audit_status <>:auditStatus " +
                        "and r.create_time in (select max(m.create_time) from SYS_AUDIT_RECORD m where"
                        + " m.create_id=:mcreateId group by m.audit_bill_id ) ");
        if (StringUtils.isNotEmpty(nameOrCode)) {
            sql.append(" and ( k.product_name like '%").append(nameOrCode).append("%'");
            sql.append(" or k.product_code like '%").append(nameOrCode).append("%' )");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and g.org_name like '%").append(orgName).append("%'");
        }
        if (StringUtils.isNotEmpty(brandName)) {
            sql.append(" and ( d.brand_en_name like '%").append(brandName.toUpperCase()).append("%'");
            sql.append(" or d.brand_zh_name like '%").append(brandName).append("%' )");
        }
        sql.append(" group by r.audit_bill_id order by r.create_time desc ) t");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("status", CommonStatusEnum.有效.getValue());
        query.setParameter("currentUserId", currentUserId);
        query.setParameter("createId", currentUserId);
        query.setParameter("mcreateId", currentUserId);
        query.setParameter("auditStatus", SupplyAuditBillStatusEnum.MANAGER_AUDITING.getCode());
        return (BigDecimal) query.getSingleResult();
    }

}
