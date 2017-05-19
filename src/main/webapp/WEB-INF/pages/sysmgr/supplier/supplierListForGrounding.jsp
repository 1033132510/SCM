<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title"><a class="first">会员管理</a><a>>供应商管理</a></h2>

<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info ">
        <div class="search padding-top-10 padding-bottom-10 padding-left-10 clearfix">
            <div class="form-group clearfix">
                <input type="text" id="orgCodeOrorgName" placeholder="输入供应商编号/公司名称进行检索" class="form-control pull-left margin-right-10"/>
                <a id="search" class="btn btn-info btn-xs pull-left">搜索</a>
            </div>
        </div>
        <div class="scroll scroll-h">
	        <table class="table table" id="supplierOrgGrid">
	            <tr>
	                <th w_num="line" width="5%;">编号</th>
	                <th w_index="orgCode" width="15%;">供应商编号</th>
	                <th w_index="orgName" width="14%;">供应商名称</th>
	                <th w_index="legalPerson" width="10%;">法人</th>
	                <th w_index="supplierType" w_render="formatterSupplierType" width="5%;">类型</th>
	                <th w_index="createTime" width="15%;">创建日期</th>
	                <th w_render="baseOperation" width="7%;">基本操作</th>
	                <th w_render="brandOperation" width="7%;">品牌操作</th>
	            </tr>
	        </table>
        </div>
    </div>
</div>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/supplier/supplierListForGrounding.js"></script>