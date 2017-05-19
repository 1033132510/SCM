package com.zzc.modules.sysmgr.user.base.service.impl;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.user.base.dao.PositionDao;
import com.zzc.modules.sysmgr.user.base.entity.Position;
import com.zzc.modules.sysmgr.user.base.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wufan on 2015/11/1.
 */
@Service("positionService")
public class PositionServiceImpl extends BaseCrudServiceImpl<Position> implements PositionService {

    private PositionDao positionDao;

    @Autowired
    public PositionServiceImpl(BaseDao<Position> positionDao) {
        super(positionDao);
        this.positionDao = (PositionDao) positionDao;
    }
}

