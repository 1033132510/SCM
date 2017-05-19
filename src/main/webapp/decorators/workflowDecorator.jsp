<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/meta.jsp" %>


<!DOCTYPE html>
<html>
<head>
    <title><sitemesh:write property='title' /></title>
    <link href="${ctx }/resources/script/demo/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css"
          type="text/css" rel="stylesheet"/>
    <link href="${ctx }/resources/script/demo/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx }/resources/content/demo/style.css" type="text/css" rel="stylesheet"/>
    <!--[if IE]>
    <link rel="stylesheet" href="${ctx }/resources/content/demo/style-ie.css" type="text/css" media="screen"/>
    <![endif]-->
    <style type="text/css">
        /* block ui */
        .blockOverlay {
            z-index: 1004 !important;
        }

        .blockMsg {
            z-index: 1005 !important;
        }
    </style>

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
    <script src="${ctx }/resources/script/models/demo/leave/leave-todo.js" type="text/javascript"></script>
</head>

<body>
<header>this is shop page, <p><a href="${ctx}/logout">退出</a></p></header>

<sitemesh:write property='body' />
<footer>@中直采供应链2015</footer>
</body>

</html>