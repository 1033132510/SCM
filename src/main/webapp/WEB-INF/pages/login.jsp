<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>My JSP 'MyJsp.jsp' starting page</title>
</head>

<body>
<h1>登录页面----${message }</h1>
<form:form action="${ctx}/login" commandName="user" method="post">
    ${message}<br/>
    用户名：<form:input path="userName"/> <form:errors path="userName" cssClass="error"/> <br/>
    密 &nbsp;&nbsp;码：<form:password path="userPwd"/> <form:errors path="userPwd" cssClass="error"/> <br/>
    <form:button name="button">submit</form:button>
</form:form>
</body>
</html>