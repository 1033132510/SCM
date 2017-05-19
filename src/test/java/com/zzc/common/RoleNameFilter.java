package com.zzc.common;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by wufan on 2015/11/26.
 */
@JsonFilter("roleNameFilter")
@JsonIgnoreProperties({"roleName","roleType"})
public interface RoleNameFilter {
}
