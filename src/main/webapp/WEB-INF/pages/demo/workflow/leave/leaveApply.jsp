<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/common/taglibs.jsp" %>
    <title>请假申请</title>
    <%@ include file="/common/meta.jsp" %>

    <link href="${ctx }/resources/script/demo/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css"
          type="text/css" rel="stylesheet"/>

    <script src="${ctx }/resources/script/demo/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/demo/plugins/jui/jquery-ui-${themeVersion }.min.js"
            type="text/javascript"></script>
    <script src="${ctx }/resources/script/demo/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js"
            type="text/javascript"></script>
    <script src="${ctx }/resources/script/demo/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js"
            type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $('#startTime,#endTime').datetimepicker({
                stepMinute: 5
            });
        });
    </script>
</head>

<body>
<div class="container showgrid">
    <c:if test="${not empty message}">
        <div id="message" class="alert alert-success">${message}</div>
        <!-- 自动隐藏提示信息 -->
        <script type="text/javascript">
            setTimeout(function () {
                $('#message').hide('slow');
            }, 5000);
        </script>
    </c:if>
    <c:if test="${not empty error}">
        <div id="error" class="alert alert-error">${error}</div>
        <!-- 自动隐藏提示信息 -->
        <script type="text/javascript">
            setTimeout(function () {
                $('#error').hide('slow');
            }, 5000);
        </script>
    </c:if>
    <form:form id="inputForm" action="${ctx}/demo/leave/start" method="post" class="form-horizontal">
        <fieldset>
            <legend>
                <small>请假申请</small>
            </legend>
            <table border="1">
                <tr>
                    <td>请假类型：</td>
                    <td>
                        <select id="leaveType" name="leaveType">
                            <option>公休</option>
                            <option>病假</option>
                            <option>调休</option>
                            <option>事假</option>
                            <option>婚假</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>开始时间：</td>
                    <td><input type="text" id="startTime" name="startTime"/></td>
                </tr>
                <tr>
                    <td>结束时间：</td>
                    <td><input type="text" id="endTime" name="endTime"/></td>
                </tr>
                <tr>
                    <td>请假原因：</td>
                    <td>
                        <textarea name="reason"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <shiro:guest>
                            请先 <a href="${ctx}/login">登录</a>
                        </shiro:guest>
                        <shiro:user>
                            <button type="submmit">申请</button>
                        </shiro:user>

                    </td>
                </tr>
            </table>
        </fieldset>
    </form:form>
</div>
</body>
</html>
