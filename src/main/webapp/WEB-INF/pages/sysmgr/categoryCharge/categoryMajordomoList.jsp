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
    <h2 class="title"><a class="first">用户管理</a><a>>部类总监管理</a></h2>
    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
        <div class="container-fluid main-info ">
            <div class="search padding-top-10 padding-bottom-10 padding-left-10 clearfix">
                <div class="form-group clearfix">
                    <input type="text" placeholder="输入员工姓名进行检索" class="form-control pull-left margin-right-10" id="keywords"/>
                    <a class="btn btn-info btn-xs pull-left" id="search">搜索</a>
                </div>
                <a class="btn btn-warning btn-xs pull-right margin-right-10" id="add">添加配置</a>
            </div>
            <div class="scroll scroll-h">
                <table class="table" id="categoryChargeTable">
                    <thead>
                        <tr>
                            <th w_num="total_line" width="10%">序号</th>
                            <th w_index="employeeName" width="15%">姓名</th>
                            <th w_index="mobile" width="15%">手机号</th>
                            <th w_render="CategoryChargeObject.showCategoryName" width="30%">品类名称</th>
                            <th w_index="createTime" width="20%">配置时间</th>
                            <th w_render="CategoryChargeObject.option" width="20%">操作</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
	<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
	<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
	<script src="${ctx}/resources/script/models/utils.js"></script>
	<script src="${ctx}/resources/script/models/sysmgr/categoryCharge/categoryMajordomoList.js?<%=new Date().getTime()%>"></script>
</body>
</html>