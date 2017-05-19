package com.zzc.modules.sysmgr.user.base.service.impl;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.user.base.dao.UserOrgPositionRelationDao;
import com.zzc.modules.sysmgr.user.base.entity.UserOrgPositionRelation;
import com.zzc.modules.sysmgr.user.base.service.UserOrgPositionRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wufan on 2015/11/19.
 */
@Service("userOrgPositionRelationService")
public class UserOrgPositionRelationServiceImpl extends BaseCrudServiceImpl<UserOrgPositionRelation> implements UserOrgPositionRelationService {

    private UserOrgPositionRelationDao userOrgPositionRelationDao;

    @Autowired
    public UserOrgPositionRelationServiceImpl(BaseDao<UserOrgPositionRelation> userOrgPositionRelationDao) {
        super(userOrgPositionRelationDao);
        this.userOrgPositionRelationDao = (UserOrgPositionRelationDao) userOrgPositionRelationDao;
    }

}

