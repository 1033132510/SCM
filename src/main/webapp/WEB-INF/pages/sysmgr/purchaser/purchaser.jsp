<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<h2 class="title"><a class="first">会员管理</a><a>>采购商管理</a></h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info ">
        <div class="search padding-top-10 padding-bottom-10 padding-left-10 clearfix">
            <div class="form-group clearfix">
                <input id="codeOrCompany" type="text" placeholder="输入采购商编号/公司名称进行检索" class="form-control pull-left margin-right-10"/>
                <a id="search" href="" class="btn btn-info btn-xs pull-left">搜索</a>
            </div>
            <a id="addPurchaser" href="" class="btn btn-warning btn-xs pull-right margin-right-10">添加采购商</a>
        </div>
	    <div class="scroll scroll-h">
	        <table class="table" id="purchasers">
	            <tr>
					<th w_num="total_line" width="5%;">编号</th>
	                <th w_index="orgCode" width="15%;">采购商编号</th>
	                <th w_index="orgName" width="20%;">公司名称</th>
	                <th w_index="level" w_render="formatterLevel" width="5%;">级别</th>
	                <th w_index="legalName" width="10%;">法人姓名</th>
	                <th w_index="tel" width="10%;">公司座机</th>
	                <th w_index="createTime" width="15%;">日期</th>
	                <th w_render="operation" width="15%;">操作</th>
	            </tr>
	        </table>
	    </div>
</div>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/purchaser/purchaser.js"></script>