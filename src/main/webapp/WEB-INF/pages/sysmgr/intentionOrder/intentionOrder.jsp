<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<h2 class="title"><a class="first">意向单管理</a></h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20 tab-container order-tab">
    <div class="main-info">
        <div class="tab-nav text-center">
        	<shiro:hasRole name="order_administrator">
            	<a status="-1" class="">全部</a>
            	<a status="1" class="on">待办意向单</a>
            	<a status="0" class="">已办意向单</a>
            </shiro:hasRole>
        </div>
        <div class="search padding-top-10 padding-bottom-10 padding-left-10 clearfix">
            <div class="form-group">
                <input id="orderCodeOrOrgName" type="text" placeholder="输入订单编号/客户名称进行检索" class="form-control pull-left margin-right-10"/>
                <a id="search" href="" class="btn btn-info btn-xs pull-left">搜索</a>
            </div>
        </div>
        <div class="scroll scroll-h">
            <table class="table" id="intentionOrders">
                <tr>
                    <th w_num="total_line" width="5%;">编号</th>
                   <th w_index="orderCode" width="15%;">订单编号</th>
                   <th w_index="purchaserUser" w_render="fmtPurchaserName" width="15%;">客户名称</th>
                   <th w_index="purchaserUser" w_render="fmtPurchaserUserName" width="8%;">下单人</th>
                   <th w_index="purchaserUser" w_render="fmtPurchaserUserMobile" width="10%;">联系电话</th>
                   <th w_index="createTime" width="15%;">下单时间</th>
                   <th w_index="orderStatus" w_render="fmtOrderStatus" width="5%;">状态</th>
                   <th w_index="status" w_render="fmtEmployeeName" width="7%;">接单人</th>
                   <th w_index="allotTime" width="15%;">接单时间</th>
                   <th w_render="operation" width="10%;">操作</th>
               </tr>
            </table>
        </div>
    </div>
</div>

<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/intentionOrder/intentionOrder.js"></script>