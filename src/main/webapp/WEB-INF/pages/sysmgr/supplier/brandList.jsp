<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title"><a class="first">会员管理</a><a>>品牌管理</a></h2>

<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info ">
        <div class="scroll scroll-h">
        	<input text="hidden" value="${supplierId}" name="id"/>
	        <table class="table table" id="supplierOrgBrandGrid">
	            <tr>
	                <th w_num="line" width="30%;">编号</th>
	                <th w_index="brandZHName" width="25%;">品牌中文</th>
	                <th w_index="brandENName" width="25%;">品牌英文</th>
	                <th w_render="baseOperation" width="20%;">基本操作</th>
	            </tr>
	        </table>
        </div>
    </div>
    <div class="pagination text-center margin-top-30" id="pagerBar">
    </div>
</div>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/supplier/brandList.js"></script>