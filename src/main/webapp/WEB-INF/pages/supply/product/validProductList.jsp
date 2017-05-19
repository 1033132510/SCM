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
    <title>商品库管理－上架商品管理</title>
</head>
<body>

    <h2 class="title"><a class="first">商品库管理</a><a>>上架商品管理</a></h2>
	
    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
        <div class="container-fluid main-info ">
            <div class="row file-upload margin-top-20 margin-left-20 margin-bottom-20">
                <label><span>上架商品已经上架成功商品，客户在电商平台可以查看、收藏、下单的商品。</span></label>
                <label><span>如已上架商品，需要修改信息、价格或下架，请提前3个工作日提交审批(提交审批后的商品，在待审批商品列表中出现)，审批通过后数据做相应调整，在审批通过前商品发生订单仍然按照原有信息或价格来操作。</span></label>
            </div>
            <div class="search clearfix">
                <div class="type-manage">
                    <h3 class="head"><a>商品检索</a></h3>
                </div>
                <div class="row line margin-bottom-20 margin-left-10">
                	<label class="pull-left margin-right-10">品牌：</label>
                	<ul class="pull-left margin-right-10" id="brandName">
                		<li class="pull-left margin-right-10 cursor">全部</li>
                		<c:forEach var="brand" items="${brandList}">
                			<li class="pull-left margin-right-10 cursor" data-value="${brand.brandZHName}">${brand.brandZHName}</li>
                		</c:forEach>
                	</ul>
                </div>
                <div class="row line margin-bottom-10 margin-left-10 margin-top-20">
                	<label class="pull-left margin-right-10">商品品类：</label>
                	<ul class="pull-left margin-right-10" id="cateId">
                		<li class="pull-left margin-right-10 cursor">全部</li>
                		<c:forEach var="category" items="${categoryList}">
                			<li class="pull-left margin-right-10 cursor" data-value="${category.id}">${category.cateName}</li>
                		</c:forEach>
                	</ul>
                </div>
                <div class="row margin-bottom-20">
                    <div class="pull-left margin-left-10">
                        <div class="form-group pull-left margin-bottom-10">
                            <input type="text" placeholder="输入商品名称或商品编号" class="form-control short pull-left margin-right-5" id="keywords"/>
                            <a class="btn btn-info btn-xs pull-left" id="search">检索</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="border-bg"></div>


            <div class="scroll scroll-h">
                <table class="table pinlei" id="productTable">
                    <thead>
                        <tr>
                        	<th w_num="line" width="5%">编号</th>
                            <th w_index="productCode" width="20%">商品编号</th>
                            <th w_index="productName" width="20%">商品名称</th>
                            <th w_render="ProductMaintenanceObject.getPrice" width="10%">价格</th>
                            <th w_index="brandZHName" width="10%">所属品牌</th>
                            <th w_index="modifiedTime" width="15%">上架时间</th>
                            <th w_render="ProductMaintenanceObject.operation" width="20%">操作</th>
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
	<script src="${ctx}/resources/script/models/supply/product/validProductList.js?<%=new Date().getTime()%>"></script>
</body>
</html>