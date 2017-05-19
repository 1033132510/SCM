package com.zzc.common.operatelog.manage.service.impl;

import com.zzc.common.operatelog.manage.dao.OperateLogDao;
import com.zzc.common.operatelog.manage.entity.OperateLogInfo;
import com.zzc.common.operatelog.manage.service.OperateLogService;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: wufan
 * Date: 12-12-29
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
@Service("operateLogService")
public class OperateLogServiceImpl extends BaseCrudServiceImpl<OperateLogInfo> implements OperateLogService {

    private OperateLogDao operateLogDao;

    @Autowired
    public OperateLogServiceImpl(BaseDao<OperateLogInfo> operateLogDao) {
        super(operateLogDao);
        this.operateLogDao = (OperateLogDao)operateLogDao;
    }
}
