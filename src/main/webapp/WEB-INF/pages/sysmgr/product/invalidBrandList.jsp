<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 主体切换的内容 -->
<title>失效品类管理</title>
<h2 class="title">
	<a class="first">会员管理</a><a>>供应商管理</a><a>>失效品牌管理</a>
</h2>
<input type="hidden" name="orgId">
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
	<div class="container-fluid main-info ">
		<h3 class="head">
			<a>失效品牌管理</a>
		</h3>
		<div class="scroll scroll-h">
			<input type="hidden" value="${supplierOrgId}" name="supplierOrgId" id="supplierOrgId" />
			<table class="table table" id="brandGrid">
				<thead>
					<tr>
						<th w_num="line">编号</th>
						<th w_index="brandZHName">品牌中文</th>
						<th w_index="brandENName">品牌英文</th>
						<th w_render="baseOperation">基本操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div class="pagination text-center margin-top-30" id="pagerBar"></div>
</div>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script>
	var supplierOrgId = $("#supplierOrgId").val(), INVALID_STATUS = 0;
	$(function() {
		var gridObj = utils.initGrid('brandGrid',
				'/sysmgr/brand/getBrandManagerList', {
					supplierOrgId : supplierOrgId,
					status : INVALID_STATUS
				});
	});
	function baseOperation(record, rowIndex, colIndex, options) {
		var modifySupplier = '<a onclick="modifyBrandInfo(\'' + record.id
				+ '\');" href="javascript:void(0);" class="red margin-right-10">基本操作</a>'
				+ '<a class="red" onclick="toGroundingBrand(\'' + record.id + '\')">置为有效</a>';
		return modifySupplier;
	}
	function modifyBrandInfo(brandId) {
		location.href = ctx + '/sysmgr/brand/toUpdateDisabledBrand?brandId='+ brandId + "&t=" + new Date().valueOf();
	}
	function toGroundingBrand(brandId) {
		location.href = ctx + '/sysmgr/brand/toGroundingBrand?brandId='+ brandId + "&t=" + new Date().valueOf();
	}
</script>