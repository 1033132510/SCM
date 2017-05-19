<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div class="main-content personal-detail my-order">
    <%@ include file="/WEB-INF/pages/shop/personalCenter.jsp" %>
    <div class="personal-wrapper ">
        <div class="tab-container personal-tab">
            <div class="tab-nav">
                <a status="-1" class="on">所有订单</a>
                <a status="0">已接单</a>
                <a status="1">待接单</a>
                <a status="2">待付款</a>
            </div>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th class="w1">商品名称</th>
                    <th class="w2">单价</th>
                    <th class="w3">数量</th>
                    <th class="w4">总金额</th>
                    <th class="w5">状态</th>
                    <th class="w6 last">操作</th>
                </tr>
            </thead>
            <tbody id="intentionOrders"></tbody>
        </table>
        <div class="pagination text-center" id="pager"></div>
    </div>
</div>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script id="intentionOrderTpl" type="text/html">
    <tr>
        <td colspan="7" class="cut"><div class="container-fluid margin-top-10"></div></td>
    </tr>
    <tr>
        <td colspan="7" class="colspan order-info">
            <span><!=createTime !></span>
            <span>订单编号：<i><!=orderCode !></i></span>
            <span><i><!=orgName !></i></span>
        </td>
    </tr>
    <tr>
        <td colspan="7" class="colspan order-total">
            <span>此订单共有<s><!=orderItemCount!>种</s>商品</span>
            <span>总金额为<s>￥<!=orderTotalPrice!></s></span>
        </td>
    </tr>

</script>
<script id="intentionOrderItemsTpl" type="text/html">
    <tr>
        <td class="text-left">
            <div class="cart-name">
                <img src="<!=productImage !>" alt=""/>
                <p>
                    <span><!=productName !></span>
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
</script>
<script id="orderStatusAndOperateTpl" type="text/html">
        <td rowspan="<!=itemsLength!>">
            <! if(orderStatus == 2) { !>
                待付款
            <!}else if(orderStatus == 1) { !>
                待接单
            <!}else{!>
                已接单 <br/>
                接单人：<!=employeeName !> <br/>
                <!=employeeMobile !>
            <!}!>
        </td>
        <td rowspan="<!=itemsLength!>">
            <a target="_blank" href="${ctx}/shop/intentionOrder/detailView/<!=orderId !>" class="green">订单详情</a>
        </td>
    </tr>

</script>
<script src="${ctx}/resources/script/plugins/jqueryPager/jquery.pager.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/shop/intentionOrder/intentionOrder.js"></script>