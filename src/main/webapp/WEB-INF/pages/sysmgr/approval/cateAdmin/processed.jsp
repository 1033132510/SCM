<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title">
    <a class="first">审批管理</a><a>>已处理</a>
</h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info clearfix">
        <div class="row file-upload margin-top-20 margin-left-20 margin-bottom-20">
        <label><span>已处理商品指提品类管理员审批过的商品列表，包含待部类总监审批和待供应商调整的商品，</span></label>
        <label><span>待总监审批：指品类管理员审批通过的商品等待部类总监审批，</span></label>
        <label><span>待调整：指品类管理员审批不通过的商品，需要供应商重新调整的商品。</span></label>
        </div>
        <form class="margin-left-20 margin-bottom-20 clearfix">
            <div class="form-group short pull-left margin-right-25">
                <input type="text" placeholder="输入商品名称\商品编号" class="form-control" id="nameOrCode" value=""/>
            </div>
            <div class="form-group short pull-left margin-right-25">
                <input type="text" placeholder="供应商公司名称" class="form-control" id="orgName" value=""/>
            </div>
            <div class="form-group short pull-left margin-right-25">
                <input type="text" placeholder="所属品牌" class="form-control" id="brandName" value=""/>
            </div>
            <a  class="btn btn-success btn-xs pull-left margin-right-10" id="searchBtn">检索</a>
        </form>
        <div class="scroll scroll-h">
            <table class="table bsgrid" id="adminProcessedGrid">
                <tr>
                    <th w_num="total_line" width="5%;">编号</th>
					<th w_index="productCode" width="8%;">商品编号</th>
                    <th w_index="productName" width="10%;">商品名称</th>
                    <!-- <th w_render="priceShowOperation" width="8%;">价格</th> -->
                    <th w_index="supplyOrgName" width="15%;">公司</th>
                    <th w_index="brandName" width="15%;">所属品牌</th>
                    <th w_index="createName" width="8%;">提交人</th>
                    <th w_index="auditStatus" width="8%">状态</th>
                    <th w_index="createTime" width="15%;">操作日期</th>
                    <th w_render="baseOperation" width="10%;">操作</th>
                </tr>
            </table>
        </div>
    </div>
</div>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/approval/cateAdmin/processed.js?t="+new Date().valueOf()></script>
