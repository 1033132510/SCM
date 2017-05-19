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

    <h2 class="title"><a class="first">商品库管理</a><a>>下架商品管理</a></h2>
	
	<c:forEach items="${statusEnum}" var="status">
		<input type="hidden" class="status" data-key="${status.key}" data-value="${status.value}"/>
	</c:forEach>    
    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
        <div class="container-fluid main-info ">
            <div class="row file-upload margin-top-20 margin-left-20 margin-bottom-20">
                <label><span>下架商品指已经下架成功商品，客户在电商平台已经不能查看、收藏、下单的商品。</span></label>
                <label><span>如商品需要修改信息价格后上架，请提前3个工作日提交审批(提交审批后的商品，在待审批商品列表中出现)，审批通过后商品上架成功。</span></label>
            </div>
            <div class="scroll scroll-h">
                <table class="table pinlei" id="productTable">
                    <thead>
                        <tr>
                            <th w_num="line" width="5%">编号</th>
                            <th w_index="productCode" width="20%">商品编号</th>
                            <th w_index="productName" width="20%">商品名称</th>
                            <th w_render="ProductMaintenanceObject.getPrice" width="10%">价格</th>
                            <th w_index="brandZHName" width="10%">所属品牌</th>
                            <th w_index="modifiedTime" width="15%">下架时间</th>
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
	<script src="${ctx}/resources/script/plugins/component/Components.js?<%=new Date().getTime()%>"></script>
	<script src="${ctx}/resources/script/models/supply/product/invalidProductList.js?<%=new Date().getTime()%>"></script>
</body>
</html>