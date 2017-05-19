<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>角色基本信息维护</title>
</head>
<body>
<form id="roleForm" action="${ctx}/sysmgr/role/info" method="POST">
    <input type="hidden" id="entityId" name="id" value="${role.id}" />
    <input type="hidden" id="status" name="status" value="${role.status}" />
    <input type="hidden" id="oldRoleName" value="${role.roleName}">
    <input type="hidden" id="oldRoleCode" value="${role.roleCode}">

    <h2 class="title"><a class="first">角色信息</a></h2>

    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
        <div class="container-fluid main-info padding-left-20 padding-right-20">
            <div class="form-group long margin-top-20">
                <label><i class="imp">*</i>角色名称:</label>
                <input name="roleName" id="roleName" value="${role.roleName}" class="form-control padding-left-font4 required roleNameHasExist"/>
            </div>
            <div class="form-group long margin-top-20">
                <label><i class="imp">*</i>角色编码:</label>
                <input name="roleCode" id="roleCode" value="${role.roleCode}" class="form-control padding-left-font4 required roleCodeHasExist"/>
            </div>
            <div class="form-group form-select select-font-4 margin-top-20 padding-left-70">
                <label><i class="imp">*</i>角色类型:</label>
                <select class="select" name="roleType" id="roleType">
                    <c:forEach items="${roleTypes}" var="roleType">
                        <option value="${roleType.key}" <c:if test="${role.roleType == roleType.key }">selected</c:if>>${roleType.value}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group form-textarea margin-top-10 margin-right-20">
                <label>
                    <i>&nbsp;</i>描述：
                </label>
                <textarea name="description" id="description" rows="5" cols="30" class="form-control">${role.description}</textarea>
            </div>
            <div class="text-center margin-bottom-30">
                <c:if test="${empty role.id}">
                    <input type="reset" class="btn btn-warning margin-top-30 margin-right-10 margin-left-10"></input>
                </c:if>
                <a href="javascript:void 0" id="submitBtn" class="btn btn-info margin-top-30 margin-right-10">保存</a>
                <a href="${ctx}/sysmgr/role/view" class="btn btn-default margin-top-30 margin-right-10 margin-left-10">返回</a>
            </div>
        </div>
    </div>
</form>
<br/>
    <script>
    $(document).ready(function () {
    // carousel
    $(".select").select2({
    minimumResultsForSearch: -1
    });//去掉输入框
    scrollTop({
    name: '.back-top',
    time: 500,
    windowScroll: true
    })
    // select2
    });
    </script>
<script src="${ctx}/resources/script/common/dataSubmitUtil.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/role/roleInfo.js"></script>

</body>
</html>