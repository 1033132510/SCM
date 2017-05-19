<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>员工-组织-岗位维护</title>
</head>
<body>

<h2 class="title"><a class="first">员工信息维护</a><a>>组织信息</a><a>>岗位维护</a></h2>

<div class="container-fluid padding-left-20 padding-right-10 margin-top-20">
    <form:form id="userOrgPostionForm" action="${ctx}/sysmgr/employee/orgPosition" commandName="relation" method="POST">
        <input type="hidden" id="chooseOrgName" >
        <input type="hidden" id="chooseOrgId" >

        <div class="container-fluid main-info margin-bottom-25 padding-bottom-10 padding-left-20">
            <div class="form-group long margin-top-20">
                <label class="sr-only" for=""><i class="imp">*</i>员工:</label>
                <%--<span></span>--%>
                <input type="text" readonly="true" placeholder="" value="${relation.id.employee.employeeName}" class="form-control padding-left-font2 margin-right-30">
                <form:input id="employeeId" path="id.employee.id" type = "hidden" />
                <%--<form:input IdValue="" value="" path="id.employee.employeeName" class="form-control padding-left-font4 required"/>--%>
                <form:errors path="id.employee.id" ></form:errors>
            </div>
            <div class="form-group long margin-top-20">
                <label class="sr-only" for=""><i class="imp">*</i>部门名称:</label>
                <form:input id="companyOrgId" path="id.companyOrg.id" type = "hidden" />
                <form:input path="id.companyOrg.orgName" id="companyOrgName" data-target="#orgTreeDiv" value="" placeholder="点击选择所在部门" readonly="true" class="form-control padding-left-font4 open-modal required cursor"/>
                <form:errors path="id.companyOrg.id" ></form:errors>
            </div>
            <div class="form-group long margin-top-20">
                <label class="sr-only" for=""><i class="imp">*</i>岗位名称:</label>
                <form:input id="positionId" path="id.position.id" type = "hidden" />
                <form:input path="id.position.positionName" id="positionName" data-target="#positionDiv" value="" placeholder="点击选择岗位" readonly="true" class="form-control padding-left-font4 required open-modal cursor"/>
                <form:errors path="id.position.id" ></form:errors>
            </div>
            <div class="form-group form-textarea long margin-top-20">
                <label class="sr-only" for="">描述:</label>
                <form:textarea path="description" rows="5" cols="30" class="form-control"/>
            </div>
            <div class="text-center margin-bottom-30">
                <c:if test="${empty relation.id.companyOrg.id}">
                    <input type="reset" class="btn btn-warning margin-top-30 margin-right-10 margin-left-10"></input>
                </c:if>
                <form:button name="button" class="btn btn-info margin-top-30 margin-right-10 margin-left-10">保存</form:button>
                <a href="${ctx}/sysmgr/employee/view" class="btn btn-default margin-top-30 margin-right-10 margin-left-10">返回</a>
            </div>
        </div>
    </form:form>
</div>

<div class="modal" id="orgTreeDiv">
    <div class="modal-content">
        <a href="" class="modal-close modal-close-btn">X</a>
        <div class="tree-preview margin-top-10">
             <div class="tree scroll scroll-v">
                <div id="orgTree"></div>
             </div>
        </div>
        <div class="text-center margin-top-20">
             <a href="javascript:void 0" id="choose" onclick="confirmOrgChoose()" class="btn btn-info">确定</a>
             <a href="" id="closeTreeDiv" class="btn btn-danger modal-close">关闭</a>
        </div>
    </div>
</div>

<div class="modal" id="positionDiv">
    <div class="modal-content">
        <a href="javascript:void(0);" id="closePositionDiv" class="modal-close modal-close-btn">X</a>
            <table class="table margin-top-10" id="positions">
                <tr>
                    <th w_num="line" width="3%;">序号</th>
                    <th w_index="positionCode" width="5%;">岗位编码</th>
                    <th w_index="positionName" width="5%;">岗位名称</th>
                    <th w_index="description" width="5%;">岗位描述</th>
                </tr>
            </table>
        <div class="text-center margin-bottom-10">
            <a href="javascript:void(0);" class="btn btn-danger modal-close margin-top-30 margin-right-10 margin-left-10">关闭</a>
        </div>
    </div>
</div>


<script src="${ctx }/resources/script/plugins/ztree/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctx}/resources/script/plugins/ztree/ZTreeUtils.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/employee/relation.js"></script>
</body>
</html>