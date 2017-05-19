<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%@ page import="com.zzc.core.exceptions.constant.ExceptionConstant" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    //jquery.ui主题
    String defaultTheme = "redmond";
    String themeVersion = "1.9.2";

    session.setAttribute("themeName", defaultTheme);
    session.setAttribute("themeVersion", themeVersion);
    pageContext.setAttribute("timeInMillis", System.currentTimeMillis());

    // 错误编码
    String systemException = ExceptionConstant.EXCEPTION_TYPE_SYS;
    String authException = ExceptionConstant.EXCEPTION_TYPE_AUTH;
    String businessException = ExceptionConstant.EXCEPTION_TYPE_BIZ;
    String validateException = ExceptionConstant.EXCEPTION_TYPE_VALIDATE;
    String sessionInvalidException = ExceptionConstant.EXCEPTION_TYPE_SESSION_INVALID;
%>
<script type="text/javascript">
    var ctx = '<%=request.getContextPath() %>';

    var ERROR_CODE = {
        systemError: '<%=systemException %>',
        businessError: '<%=businessException %>',
        authError: '<%=authException %>',
        validateError: '<%=validateException %>',
        sessionInvalid: '<%=sessionInvalidException %>'
    }
</script>