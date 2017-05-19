<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<link rel="stylesheet" href="${ctx}/resources/script/plugins/jqueryUI/jquery-ui.min.css">
<h2 class="title"><a class="first">意向单管理</a><a>>详情</a></h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info padding-bottom-20 clearfix intent">
        <h3 class="head"><a>客户信息</a></h3>
        <div class="row">
            <span class="margin-left-10 margin-top-10 margin-right-20 red pull-left"><fmt:formatDate value="${order.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
            <span class="margin-left-10 margin-top-10 margin-right-20 pull-left">客户名称：${order.purchaserUser.purchaser.orgName }</span>
            <span class="margin-left-10 margin-top-10 margin-right-20 pull-left">订单编号：${order.orderCode }</span>
            <span class="margin-left-10 margin-top-10 margin-right-20 pull-left">下单人：${order.purchaserUser.name }</span>
            <span class="margin-left-10 margin-top-10 margin-right-20 pull-left">联系电话：${order.purchaserUser.mobile }</span>
        </div>
        <div class="row">
            <span class="margin-left-10 margin-top-10 margin-right-20 pull-left">项目名称：${order.projectName }</span>
            <span class="margin-left-10 margin-top-10 margin-right-20 pull-left">预计需求期：<fmt:formatDate value="${order.demandDate }" pattern="yyyy-MM-dd" /></span>
        </div>
        <div class="row">
            <span class="margin-left-10 margin-top-10 margin-right-20 pull-left">备注：${order.projectDescription }</span>
        </div>
    </div>
</div>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info padding-bottom-20 clearfix intent">
        <h3 class="head"><a>接单情况</a></h3>
        <div class="row">
            <span class="margin-left-10 margin-top-10 margin-right-20 pull-left">当前意向单状态：</span>
			<span class="margin-left-10 margin-top-10 margin-right-20" id="orderStatusText">
			<c:if test="${order.orderStatus == 1}">
				待接单
			</c:if>
			<c:if test="${order.orderStatus == 0}">
				已接单
			</c:if>
			</span>
        </div>
        <div class="row">
            <span class="margin-left-10 margin-top-10 margin-right-20 pull-left"><fmt:formatDate value="${order.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
            <span class="margin-left-10 margin-top-10 margin-right-20 pull-left">订单编号：${order.orderCode }</span>
            <span class="margin-left-10 margin-top-10 margin-right-20 pull-left">下单人：${order.purchaserUser.name }</span>
            <span class="margin-left-10 margin-top-10 margin-right-20 pull-left">联系电话：${order.purchaserUser.mobile }</span>
        </div>
        <!-- 派单后显示START -->
        <c:if test="${not empty order.employee}">
	        <div class="row">
	            <span id="allotTime" class="margin-left-10 margin-top-10 margin-right-20 pull-left">
	            		<fmt:formatDate value="${order.allotTime }" pattern="yyyy-MM-dd HH:mm:ss" />
	            </span>
	            <span id="allotInfo" class="margin-left-10 margin-top-10 margin-right-20 pull-left">
	            		运营总监<i class="red">${order.cooName }</i>分配<i class="red">${order.employee.employeeName }</i>
	            </span>
	        </div>
        </c:if>
        <c:if test="${empty order.employee}">
	        <div class="row">
	            <span id="allotTime" class="margin-left-10 margin-top-10 margin-right-20 pull-left"></span>
	            <span id="allotInfo" class="margin-left-10 margin-top-10 margin-right-20 pull-left"></span>
	        </div>
        </c:if>
        <shiro:hasRole name="order_administrator">
	        <c:if test="${empty order.employee.id}">
	        		 <div class="text-right margin-left-10 margin-top-10 pull-left">
		            <a id="allotOrder" data-target="#allotOrderModal" class="btn btn-info appoint open-modal">派发管理员</a>
		        </div>
	        </c:if>
        </shiro:hasRole>
        
        <div class="modal" id="allotOrderModal">
		    <div class="modal-content">
		        <h1>派发接单员</h1>
		        <p>
			        <div class="form-group margin-bottom-20 margin-top-20 margin-left-20 margin-right-20">
		                <label class="sr-only" for="name"><i class="imp">*</i>请输入姓名:</label>
		                <input id="name" type="text" class="form-control padding-left-font6"/>
		                <span class="text-left option">输入姓名，系统会自动带出姓名及手机号的完整信息</span>
		            </div>
		            <div class="text-center margin-bottom-20">
		                <a id="confirmAllotOrder" class="btn btn-info close-appoint-box">派单</a>
		                <a id="closeModal" href="" class="btn btn-danger modal-close">关闭</a>
		            </div>
		        </p>
		    </div>
		    <input type="hidden" id="employeeId" />
		    <input type="hidden" id="employeeName" />
		    <input type="hidden" id="id" value="${order.id }" />
		</div>
    </div>
</div>

<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info clearfix intent">
        <h3 class="head"><a>意向单商品信息</a></h3>
        <div class="margin-right-20 order-all">
			<label>订单总价格：<span>￥${order.totalPrice }</span></label>
		</div>
		<div class="scroll scroll-h">
			<table class="table" id="orderItems">
					<thead>
					<tr>
						<th w_num="total_line" width="5%;">编号</th>
						<th w_index="productCode" width="10%">商品编号</th>
						<th w_index="productName" w_render="formatterNameAndImage" width="40%">商品名称</th>
						<th w_index="price" w_render="formatterPrice" width="15%">单价</th>
						<th w_index="quantity" width="10%">数量</th>
						<th w_index="itemTotalPrice" w_render="formatterItemTotalPrice" width="20%">价格</th>
					</tr>
				 </thead>
			</table>
		</div>
    </div>
</div>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/plugins/jqueryUI/jquery-ui.min.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/intentionOrder/intentionOrderDetail.js"></script>