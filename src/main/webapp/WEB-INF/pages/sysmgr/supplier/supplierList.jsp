<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title"><a class="first">会员管理</a><a>>供应商管理</a></h2>

<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
	<input type="hidden" value="${status}" id="status">
    <div class="container-fluid main-info ">
        <div class="search padding-top-10 padding-bottom-10 padding-left-10 clearfix">
            <div class="form-group clearfix">
                <input type="text" id="orgCodeOrorgName" placeholder="输入供应商编号/公司名称进行检索" class="form-control pull-left margin-right-10"/>
                <a id="search" href="" class="btn btn-info btn-xs pull-left">搜索</a>
            </div>
             <c:if test="${status== 1}">
            <a onclick="addSupplierOrg()" class="btn btn-warning btn-xs pull-right margin-right-10">添加供应商</a>
            </c:if>
        </div>
        <div class="scroll scroll-h">
	        <table class="table table" id="supplierOrgGrid">
	            <tr>
	                <th w_num="total_line" width="5%;">编号</th>
	                <th w_index="orgCode" width="15%;">供应商编号</th>
	                <th w_index="orgName" width="14%;">供应商名称</th>
	                <th w_index="legalPerson" width="10%;">法人</th>
	                <th w_index="supplierType" w_render="formatterSupplierType" width="5%;">类型</th>
	                <th w_index="createTime" width="15%;">创建日期</th>
	                <th w_render="baseOperation" width="21%;">操作</th>
	                <!-- 
	                <c:if test="${status== 1}">
	                	<th w_render="statusOperation" width="7%;">置为无效</th>
	                </c:if>
	                <c:if test="${status== 0}">
	                	<th w_render="statusFailureOperation" width="7%;">置为有效</th>
	                </c:if>
	                 -->
	            </tr>
	        </table>
        </div>
       	<!--
        <table class="table" id="supplierOrgGrid">
            <thead>
            <tr>
                <th>编号</th>
                <th>会员ID</th>
                <th>公司名称</th>
                <th>级别</th>
                <th>联系人</th>
                <th>联系电话</th>
                <th>审核状态</th>
                <th>日期</th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
        -->
    </div>
    <div class="pagination text-center margin-top-30" id="pagerBar">
    <!--  
        <ul>
            <li><span>共4页</span></li>
            <li><a href="">首页</a></li>
            <li class="prev disabled"><a href="">上一页</a></li>
            <li class="active"><a href="">1</a></li>
            <li><a href="">2</a></li>
            <li><a href="">3</a></li>
            <li><a href="">4</a></li>
            <li><a href="">下一页</a></li>
            <li class="next"><a href="">尾页</a></li>
        </ul>
        -->
    </div>
</div>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/supplier/supplierList.js"></script>