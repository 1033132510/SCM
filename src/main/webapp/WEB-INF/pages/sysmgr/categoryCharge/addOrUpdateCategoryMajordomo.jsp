<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="renderer" content="webkit"/>
    <meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible"/>
    <meta name="description" content=""/>
    <meta name="author" content="zhanjun"/>
    <title>直采科技有限公司</title>
</head>
<body>
	<h2 class="title"><a class="first">用户管理</a><a>>品类负责人维护</a><a>>分配品类</a></h2>
    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
        <div class="container-fluid main-info">
            <div class="form-group margin-bottom-10 margin-left-10 margin-top-10 pull-left">
                <label class="sr-only">员工姓名:</label>
				<input type="hidden" id="userId" value="${employeeId}"/>
				<!-- 初始值 -->
				<input type="hidden" id="employeeName" value="${employeeName}"/>
                <input type="text" class="form-control short pull-left padding-left-font4" id="keywords" value="${employeeName}"/>
                <div class="input-box text-left" id="employeeListContainer">
                	<ul id="employeeList"></ul>
                </div>
            </div>
            
            <div class="category-manager">
            	<div class="level-content pull-left" id="firstLevelContainer">

                </div>
                <div class="level-right padding-bottom-20">
                    <ul class="form-group checkbox level-inner" id="secondLevelContainer"></ul>
                    <div class="text-center margin-top-20 clearfix">
                        <a class="btn btn-info" id="checkCategory">确定选中类别</a>
                    </div>
                </div>
            </div>
    	</div>
        <div class="container-fluid main-info margin-top-20 clearfix">
            <h3 class="head"><a>已配置品类</a></h3>
            <div class="category-list-box" id="checkedContainer"></div>
            <div class="form-group row margin-left-20 padding-bottom-10 hide" id="errorContainer">
            	<label>
            		<span></span>
            	</label>
            </div>
            <div class="text-center margin-top-20 margin-bottom-20 clearfix">
                <a class="btn btn-info" id="addOrUpdate">确定</a>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/pages/sysmgr/categoryCharge/categoryTemplate.jsp"%>
	<script src="${ctx}/resources/script/plugins/slick/slick.min.js"></script>
	<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
    <script src="${ctx}/resources/script/models/sysmgr/categoryCharge/addOrUpdateCategoryMajordomo.js?<%=new Date().getTime()%>"></script>
</body>
</html>