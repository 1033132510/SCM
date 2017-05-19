<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>员工信息列表</title>
</head>
<body>
<%--<c:if test="${empty requestScope.employees }">
    没有任何用户信息。
</c:if>--%>
    <h2 class="title"><a class="first">用户管理</a><a>>员工信息管理</a></h2>
    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20 tab-container ">
        <div class="main-info">
            <div class="search padding-top-10 padding-bottom-10 padding-left-10 clearfix">
                <div class="form-group clearfix">
                    <input id="searchParam" type="text" placeholder="输入员工姓名或手机号进行检索" class="form-control pull-left margin-right-10"/>
                    <a id="search" href="javascript:void(0)" class="btn btn-info btn-xs pull-left">搜索</a>
                </div>
                <a href="info" class="btn btn-warning btn-xs pull-right margin-right-10">添加员工</a>
            </div>
            <div class="scroll scroll-h">
                <table class="table" id="employees">
                    <tr>
                        <th w_num="total_line" width="5%;">序号</th>
                        <th w_index="userName" width="20%;">账户名</th>
                        <th w_index="employeeName" width="10%;">员工姓名</th>
                        <th w_index="mobile" width="15%;">手机号</th>
                        <th w_index="entryDate" width="10%;">入职时间</th>
                        <th w_index="employeeStatus" w_render="formatterEmployeeStatus" width="10%;">任职情况</th>
                        <th w_index="employeeType" w_render="formatterEmployeeType" width="10%;">类型</th>
                        <th w_index="status" w_render="formatterStatus" width="10%;">状态</th>
                        <th w_render="operation" width="20%;">操作</th>
                    </tr>
                </table>
            </div>
        </div>
    </div>

<div class="modal" id="modal1">
    <div class="modal-content">
        <a href="" class="modal-close modal-close-btn">X</a>
        <h1>modal</h1>
        <p>
            模太框的使用方法
        </p>
        <a href="" class="btn btn-danger modal-close">关闭</a>
    </div>
</div>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/employee/employeeList.js"></script>

</body>
</html>