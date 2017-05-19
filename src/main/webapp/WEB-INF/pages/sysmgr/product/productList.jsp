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
    <title>商品库管理－商品维护</title>
</head>
<body>

    <h2 class="title"><a class="first">商品库管理</a><a>>商品维护</a></h2>
	
    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
        <div class="container-fluid main-info ">
            <div class="search padding-top-10 padding-bottom-10 padding-left-10 padding-right-10 clearfix">
                <div id="productCategoryContainer" class="select-group pull-left" >

                </div>
                <div class="form-group pull-left">
                    <input type="text" placeholder="输入商品名称" class="form-control short pull-left margin-right-5" id="product_name_keywords"/>
                    <a class="btn btn-info btn-xs pull-left" id="search">检索</a>
                </div>
                <a class="btn btn-warning btn-xs pull-right" id="addProduct">添加商品</a>
            </div>
            <div class="border-bg"></div>

            <div class="scroll scroll-h">
                <table class="table pinlei" id="productTable">
                    <thead>
                        <tr>
                            <th w_index="productCode" width="20%">编号</th>
                            <th w_index="productName" width="30%">商品名称</th>
                            <th w_render="ProductMaintenanceObject.getBrandName" width="20%">品牌</th>
                            <th w_render="ProductMaintenanceObject.getProductCategory" width="10%">所属品类</th>
                            <th w_index="status" width="10%">状态</th>
                            <th w_render="ProductMaintenanceObject.operation" width="10%">操作</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
	
	<script src="${ctx}/resources/script/plugins/select/select2.min.js"></script>
	<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
	<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
	<script src="${ctx}/resources/script/models/utils.js"></script>
	<script src="${ctx}/resources/script/plugins/component/Components.js?<%=new Date().getTime()%>"></script>
	<script src="${ctx}/resources/script/models/sysmgr/product/productList.js?<%=new Date().getTime()%>"></script>
</body>
</html>