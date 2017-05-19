<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 主体切换的内容 -->
<title>品类管理</title>
<h2 class="title">
	<a class="first">会员管理</a><a>>供应商管理</a><a>>品牌管理</a>
</h2>
<input type="hidden" name="orgId">
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
	<div class="container-fluid main-info ">
		<h3 class="head">
			<a>品牌管理</a>
		</h3>
		<div class="text-right margin-bottom-10 margin-top-10 margin-right-10">
			<a class="btn btn-xs btn-warning" id="brandView_addBrand" href="javascript:void(0);">添加品牌</a>
		</div>
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
	$(function() {
		var url = "http://online.zzc:82/SCM/sysmgr/product/brand/searchBrandInfoByCateAndProductName?cateId=ff808081516abc8201516b44e31b0d47&productName=&t=1449750300928";
		$.ajax({
			type : "GET",
			url : url,// 提交的xURL
			success : function(data) {
				alert(1);
				debugger;
			},
			error : function(request) {
				zcal({
					type : 'error',
					title : '保存失败',
					time : 2000,
				});
			}
		});

	});
</script>