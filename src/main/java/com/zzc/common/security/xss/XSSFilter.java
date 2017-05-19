package com.zzc.common.security.xss;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XSSFilter implements Filter {

    private FilterConfig config;
    private static boolean no_init = true;

    public XSSFilter() {
        config = null;
    }

    public void init(FilterConfig filterconfig) throws ServletException {
        config = filterconfig;
        no_init = false;
    }

    public void destroy() {
        config = null;
    }

    public FilterConfig getFilterConfig() {
        return config;
    }

    public void setFilterConfig(FilterConfig filterconfig) {
        if (no_init) {
            no_init = false;
            config = filterconfig;
        }
    }

    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain) throws IOException, ServletException {
        // 解决：拒绝恶意请求 (中)	跨站点请求伪造
        HttpServletRequest request = (HttpServletRequest) servletrequest;
        HttpServletResponse response = (HttpServletResponse) servletresponse;
        String referer = request.getHeader("Referer"); // HTTP 头设置 Referer过滤
        if (referer != null &&
                (referer.indexOf("sysmgr") < 0
                        && referer.indexOf("shop") < 0
                )) {
            request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
        }
        filterchain.doFilter(new RequestWrapper((HttpServletRequest) servletrequest), servletresponse);
    }
}