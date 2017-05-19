package com.zzc.common.entityListeners;

import com.zzc.common.security.service.UserUtil;

import javax.persistence.PrePersist;

/**
 * Created by chenjiahai on 16/1/8.
 */
public class CreateIdListener {

    @PrePersist
    public void setCreateId(final Creatable creatable) {
        if (UserUtil.getUserFromSession() != null) {
            creatable.setCreateId(UserUtil.getUserFromSession().getCurrentUserId());
        }
    }
}
