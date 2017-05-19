<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/meta.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>

    <script type="text/javascript">
        $(function () {
            $(".delete").click(function () {
                var href = $(this).attr("href");
                $("#deleteForm").attr("action", href).submit();
                return false;
            });
        });
    </script>

</head>
<leftMenu>
    my is left menus list;
    <input type="hidden" id="menuid" value="1">
</leftMenu>
<body>
<form id="deleteForm" action="" method="POST">
    <input type="hidden" name="_method" value="DELETE"/>
</form>
<c:if test="${empty requestScope.users }">
    没有任何用户信息。
</c:if>
<c:if test="${!empty requestScope.users }">
    <table border="1">
        <tr>
            <th>ID</th>
            <th>姓名</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${requestScope.users }" var="user">
            <tr>
                <td>${user.id }</td>
                <td>${user.userName }</td>
                <td><fmt:formatDate value="${user.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>
                    <a class="modify" href="demousers/${user.id }">修改</a>
                    <a class="delete" href="demousers/${user.id }">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<br/>
<a href="demousers/user">添加用户</a>
<a href="demousers/selectList">查询用户</a>

</body>
</html>