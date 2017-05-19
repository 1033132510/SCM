<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div class="main-content personal-detail order-detail">
    <%@ include file="/WEB-INF/pages/shop/personalCenter.jsp" %>
    <div class="personal-wrapper">
        <h2 class="title">订单信息</h2>

        <div id="orderInfo" class="order-detail-info margin-bottom-20">

        </div>
        <h2 class="title">商品信息</h2>

        <table class="table">
            <thead>
            <tr>
                <th class="w1">商品名称</th>
                <th class="w2">单价</th>
                <th class="w3">数量</th>
                <th class="w4 last">总金额</th>
            </tr>
            </thead>
            <tbody id="orderItems"></tbody>
        </table>
        <div class="pagination text-center" id="pager"></div>
    </div>
    <input type="hidden" id="id" value="${id }"/>
</div>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script id="orderInfoTpl" type="text/html">
    <div class="order-detail-time margin-right-20"><!=createTime !></div>
    <div class="order-detail-in">
        <div class="item w1">
            <div class="row"><span>客户名称：</span><!=orgName !></div>
            <div class="row"><span>项目名称：</span><!=projectName !></div>
        </div>
        <div class="item w2">
            <div class="row"><span>订单编号：</span><!=orderCode !></div>
            <div class="row"><span>预计需求期：</span><!=demandDate !></div>
        </div>
        <div class="item w3">
            <div class="row"><span>下单人：</span><!=name !></div>
        </div>
        <div class="item w4">
            <div class="row"><span>联系电话：</span><!=mobile !></div>
        </div>
        <div class="describe"><span>描述：</span><!=projectDescription !></div>
    </div>
</script>
<script id="orderItemsTpl" type="text/html">
    <tr>
        <!--<td><!=productCode !></td>-->
        <td class="text-left">
            <div class="cart-name">
                <img style="cursor:hand" src="<!=productImage !>" alt="" onclick="searchDetail('<!=itemId!>');"/>

                <p>
                    <span><a target="_blank" onclick="searchDetail('<!=itemId!>');"><!=productName !></a></span>
                </p>
            </div>
        </td>
        <td>￥<!=price !>
            <i class="tax">
            <! if(hasTax == 1) {!>
            含税
            <!}else{!>
            不含税
            <!}!>
            <! if(hasTransportation == 1) {!>
            含运费
            <!}else{!>
            不含运费
            <!}!>
            </i>
        </td>
        <td><!=quantity !></td>
        <td>￥<!=itemTotalPrice !></td>
    </tr>
</script>
<script src="${ctx}/resources/script/plugins/jqueryPager/jquery.pager.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/shop/intentionOrder/intentionOrderDetail.js"></script>