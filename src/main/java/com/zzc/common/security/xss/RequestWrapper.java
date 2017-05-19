package com.zzc.common.security.xss;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *
 */
public final class RequestWrapper extends HttpServletRequestWrapper {

    public RequestWrapper(HttpServletRequest httpservletrequest) {
        super(httpservletrequest);
    }

    @Override
    public String[] getParameterValues(String s) {
        String str[] = super.getParameterValues(s);
        if (str == null) {
            return null;
        }
        int i = str.length;
        String as1[] = new String[i];
        for (int j = 0; j < i; j++) {
            as1[j] = cleanXSS(str[j]);
        }

        return as1;
    }
    @Override
    public String getParameter(String s) {
        String s1 = super.getParameter(s);
        if (s1 == null) {
            return null;
        } else {
            return cleanXSS(s1);
        }
    }
    @Override
    public String getHeader(String s) {
        String s1 = super.getHeader(s);
        if (s1 == null) {
            return null;
        } else {
            return cleanXSS(s1);
        }
    }

    private String cleanXSS(String value) {
        value = StringEscapeUtils.escapeHtml4(value);
        return value;
    }
}
