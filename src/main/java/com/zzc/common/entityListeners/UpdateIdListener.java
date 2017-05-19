package com.zzc.common.entityListeners;

import com.zzc.common.security.service.UserUtil;

import javax.persistence.PreUpdate;

/**
 * Created by chenjiahai on 16/1/8.
 */
public class UpdateIdListener {

	@PreUpdate
	public void setUpdateId(final Updatable updatable) {
		if (UserUtil.getUserFromSession() != null) {
			updatable.setUpdateId(UserUtil.getUserFromSession().getCurrentUserId());
		}
	}
}
