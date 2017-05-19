<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <%@ include file="/common/taglibs.jsp" %>
    <title>请假已结束的流程实例列表</title>
    <%@ include file="/common/meta.jsp" %>
    <link href="${ctx }/resources/script/demo/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css"
          type="text/css" rel="stylesheet"/>
    <link href="${ctx }/resources/script/demo/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx }/resources/content/demo/style.css" type="text/css" rel="stylesheet"/>
    <!--[if IE]>
    <link rel="stylesheet" href="${ctx }/resources/content/demo/style-ie.css" type="text/css" media="screen"/>
    <![endif]-->
    <script src="${ctx }/resources/script/demo/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/demo/plugins/jui/jquery-ui-${themeVersion }.min.js"
            type="text/javascript"></script>
    <script src="${ctx }/resources/script/demo/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js"
            type="text/javascript"></script>
    <script src="${ctx }/resources/script/demo/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js"
            type="text/javascript"></script>
    <script src="${ctx }/resources/script/demo/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/demo/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/demo/plugins/blockui/jquery.blockUI.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/models/activiti/workflow.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            // 跟踪
            $('.trace').click(graphTrace);
        });
    </script>
</head>

<body>
<table width="100%" class="need-border">
    <thead>
    <tr>
        <th>假种</th>
        <th>申请人</th>
        <th>申请时间</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>当前节点</th>
        <th>任务创建时间</th>
        <th>流程状态</th>
        <th>当前处理人</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.result }" var="leave">
        <c:set var="task" value="${leave.task }"/>
        <c:set var="pi" value="${leave.processInstance }"/>
        <tr id="${leave.id }" tid="${task.id }">
            <td>${leave.leaveType }</td>
            <td>${leave.userName }</td>
            <td>${leave.applyTime }</td>
            <td>${leave.startTime }</td>
            <td>${leave.endTime }</td>
            <td>
                <a class="trace" href='#' pid="${pi.id }" pdid="${pi.processDefinitionId}"
                   title="点击查看流程图">${task.name }</a>
            </td>
            <td>${task.createTime }</td>
            <td>${pi.suspended ? "已挂起" : "正常" }；<b title='流程版本号'>V: ${leave.processDefinition.version }</b></td>
            <td>${task.assignee }</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<tags:pagination page="${page}" paginationSize="${page.pageSize}"></tags:pagination>
</body>
</html>
