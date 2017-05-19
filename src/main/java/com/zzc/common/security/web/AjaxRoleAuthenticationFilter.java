package com.zzc.common.security.web;

import com.zzc.core.exceptions.constant.ExceptionConstant;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

/**
 * 自定义角色过滤，增加处理ajax请求的逻辑
 * Created by wufan on 2015/12/11.
 */
public class AjaxRoleAuthenticationFilter extends RolesAuthorizationFilter {

    private String unauthorizedUrl;


    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);
        String[] rolesArray = (String[])((String[])mappedValue);
        if(rolesArray != null && rolesArray.length != 0) {
            Set roles = CollectionUtils.asSet(rolesArray);
            return subject.hasAllRoles(roles);
        } else {
            return true;
        }
    }

    public AjaxRoleAuthenticationFilter() {
    }

    public String getUnauthorizedUrl() {
        return this.unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = this.getSubject(request, response);
        if(subject.getPrincipal() == null) {
            if (((HttpServletRequest)request).getHeader("ajax") != null) {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JsonUtils.toJson(new ResultData(false, "session失效", ExceptionConstant.EXCEPTION_TYPE_SESSION_INVALID)));
            } else {
                this.saveRequestAndRedirectToLogin(request, response);
            }
        } else {
            if (((HttpServletRequest)request).getHeader("ajax") != null) {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JsonUtils.toJson(new ResultData(false, "没有权限", ExceptionConstant.EXCEPTION_TYPE_AUTH)));
            } else {
                String unauthorizedUrl = this.getUnauthorizedUrl();
                if(StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                }
            }
            return false;
        }

        return false;
    }

}
