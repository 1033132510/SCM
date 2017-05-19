package com.zzc.modules.sysmgr.user.employee.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.zzc.common.page.PageForShow;
import com.zzc.modules.sysmgr.user.employee.dao.EmployeeQueryDao;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;

/**
 * 员工信息查询类 Created by wufan on 2015/12/1.
 */
@Repository("employeeQueryDao")
public class EmployeeQueryDaoImpl implements EmployeeQueryDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Employee> findByEmployeeName(String employeeName) {
		StringBuilder hql = new StringBuilder("from Employee e");
		if (StringUtils.isNotEmpty(employeeName)) {
			hql.append(" where e.employeeName = :employeeName");
		}
		Query query = entityManager.createQuery(hql.toString());

		if (StringUtils.isNotEmpty(employeeName)) {
			query.setParameter("employeeName", employeeName);
		}
		return query.getResultList();
	}

	@Override
	public PageForShow<Employee> findEmployeeByPage(String employeeName, Integer pageNum, Integer pageSize) {
		StringBuilder hql = new StringBuilder("from Employee e");
		if (StringUtils.isNotEmpty(employeeName)) {
			hql.append(" where e.employeeName = :employeeName");
		}
		Query query = entityManager.createQuery(hql.toString());
		if (StringUtils.isNotEmpty(employeeName)) {
			query.setParameter("employeeName", employeeName);
		}

		// 设置分页查询参数
		int total = query.getResultList().size();
		int firstResult = pageNum * pageSize;
		if (firstResult < total && pageSize.intValue() > 0) {
			query.setFirstResult(firstResult);
			query.setMaxResults(pageSize);
		}

		// 构建分页数据
		PageForShow<Employee> pageForShow = new PageForShow<>();
		pageForShow.setCurPage(pageNum);
		pageForShow.setTotalRows(pageSize.longValue());
		pageForShow.setData(query.getResultList());

		return pageForShow;
	}
}
