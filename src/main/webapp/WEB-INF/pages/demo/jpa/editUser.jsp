<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>
    <%@ include file="/common/meta.jsp" %>
</head>
<body>
<form:form action="${ctx}/demousers/user" method="POST" modelAttribute="demoUser">
    <c:if test="${demoUser.id != null }">
        <form:hidden path="id"/>
        <input type="hidden" name="_method" value="PUT"/>
    </c:if>
    用户名:<form:input path="userName"/>
    <input type="submit" value="提交"/>
</form:form>
</body>
</html>