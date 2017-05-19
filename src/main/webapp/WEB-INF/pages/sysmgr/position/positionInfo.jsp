    <%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>岗位基本信息维护</title>
</head>
<body>
<h2 class="title"><a class="first">岗位维护</a></h2>

<div class="container-fluid padding-left-20 padding-right-10 margin-top-20">
<form id="positionForm" action="${ctx}/sysmgr/position/info" method="POST">
    <input type="hidden" id="oldPositionName" value="${position.positionName}">
    <input type="hidden" id="entityId" name="id" value="${position.id}"/>
    <input name="status" id="status" type="hidden" value="1"/>
    <div class="container-fluid main-info margin-bottom-25 padding-bottom-10">
        <h3 class="head text-left">岗位信息</h3>

        <div class="positionName form-group long margin-left-20 margin-top-20">
            <label><i class="imp">*</i>岗位名称:</label>
            <input name="positionName" id="positionName" value="${position.positionName}" class="form-control padding-left-font4 required positionNameHasExist"/>
        </div>
        <div class="form-group long margin-left-20 margin-top-20 form-success">
            <label>岗位编码:</label>
            <input name="positionCode" id="positionCode" value="${position.positionCode}" class="form-control padding-left-font4"/>
        </div>
        <div class="form-group form-textarea margin-left-20 margin-top-20">
            <label>描述:</label>
            <textarea name="description" id="description" rows="5" cols="30" class="form-control">${position.description}</textarea>
        </div>
        <div class="text-center margin-bottom-30">
            <%--<c:if test="${empty position.id}">--%>
                <%--<input type="reset" class="btn btn-warning margin-top-30 margin-right-10 margin-left-10">--%>
            <%--</c:if>--%>
            <a href="javascript:void 0" id="submitBtn" class="btn btn-info margin-top-30 margin-right-10">保存</a>
            <a href="${ctx}/sysmgr/position/view" class="btn btn-default margin-top-30 margin-left-10">返回</a>
        </div>
    </div>
</form>
</div>
<br/>
<script src="${ctx}/resources/script/common/dataSubmitUtil.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/position/positionInfo.js"></script>
</body>
</html>