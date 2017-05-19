<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<h2 class="title"><a class="first">会员管理</a><a>>采购商管理</a>>人员信息管理</h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
	<div class="container-fluid main-info">
		<div class="search padding-top-10 padding-bottom-10 padding-left-10 clearfix">
			<a id="savePurchaserUserView" href="" class="btn btn-warning btn-xs pull-right margin-right-10">添加账号</a>
			<a id="getPurchaserInfo" href="" class="btn btn-warning btn-xs pull-right margin-right-10">查看公司信息</a>
		</div>
		<div class="scroll scroll-h">
			<table class="table" id="purchaserUser">
				<tr>
					<th w_num="line" width="5%;">编号</th>
					<th w_index="name" width="10%;">姓名</th>
					<th w_index="identityNumber" width="20%;">身份证号</th>
					<th w_render="formatterCompany" width="15%;">所属部门</th>
					<th w_index="position" width="15%;">职位</th>
					<th w_index="email" width="15%;">邮箱</th>
					<th w_index="mobile" width="10%;">联系电话</th>
					<th w_index="status" w_render="formatterStatus" width="5%;">状态</th>
					<th w_render="operation" width="5%;">操作</th>
				</tr>
			</table>
		</div>
	</div>
	<input type="hidden" id="purchaserId" value="${purchaserId }">
</div>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/purchaser/purchaserUser.js"></script>