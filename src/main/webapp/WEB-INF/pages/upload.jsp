<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="ctx" value=" ${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
    <title>My JSP 'MyJsp.jsp' starting page</title>
</head>

<body>
<h1>上传文件</h1>

<div id="licenceDiv"></div>
<script src="${ctx}/resources/script/jquery-1.11.0.min.js"></script>
<script src="${ctx}/resources/script/base.js"></script>
<script type="text/javascript" src="${ctx }/resources/script/plugins/fileUpload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx }/resources/script/plugins/fileUpload/jquery.fileupload.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/uploadFileUtil.js"></script>
<script type="text/javascript" src="${ctx }/resources/script/models/demo/fileUpload.js"></script>
</body>
</html>