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
    <title>供应商登记信息－登记列表</title>
</head>
<body>

    <h2 class="title"><a class="first">供应商登记信息</a><a>>登记列表</a></h2>
    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
        <div class="container-fluid main-info ">
            <div class="scroll scroll-h">
                <table class="table pinlei" id="registList">
                    <thead>
                        <tr>
                        	<th w_num="total_line" width="5%">编号</th>
                            <th w_render="SupplierObject.getCompanyName" width="25%">公司名称</th>
                            <th w_index="brandName" width="20%">公司品牌名称</th>
                            <th w_index="username" width="10%">姓名</th>
                            <th w_index="mobilePhone" width="10%">手机号</th>
                            <th w_index="email" width="15%">注册邮箱</th>
                            <th w_index="createTime" width="15%">登记时间</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
	
	<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
	<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
	<script src="${ctx}/resources/script/models/utils.js"></script>
	<script src="${ctx}/resources/script/models/sysmgr/supplier/registList.js?<%=new Date().getTime()%>"></script>
</body>
</html>