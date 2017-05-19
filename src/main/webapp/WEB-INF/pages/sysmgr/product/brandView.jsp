<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<link
	href="${ctx}/resources/script/plugins/ueidtor/themes/default/css/ueditor.css"
	type="text/css" rel="stylesheet">
<h2 class="title">
	<a class="first">会员管理</a><a>>品牌管理</a><a>>品牌维护</a>
</h2>
<div
	class="container-fluid padding-left-20 padding-right-20 margin-top-20 tab-container ">
	<div class="main-info">
		<h3 class="head">品牌维护</h3>
		<form id="add_brand_form">
			<input type="hidden" id="brandview_id" value="${brand.id }">
			<input type="hidden" id="brandview_supplierOrg_orgId" value="${brand.supplierOrg.id }" />
			<!-- 图片隐藏标签 -->
			<input type="hidden" id="brandview_supplierOrg_orgId" value="${brand.supplierOrg.id }" /> 
			<input type="hidden" id="brandview_supplierOrg_orgId" value="${brand.supplierOrg.id }" />
			<div class="tab-content padding-bottom-20 padding-left-20 padding-right-20">
				<div class="tab on">
					<div class="row margin-top-20">
						<div class="form-group long pull-left">
							<label><i class="imp">*</i>供应商名称:</label>
							<input type="text" class="form-control padding-left-font5" value="${brand.supplierOrg.orgName}"
								id="brandview_supplierOrg_orgName" name="brandview_supplierOrg_orgName" readonly />
						</div>
					</div>
					<div class="row margin-top-20">
						<div class="form-group long pull-left">
							<label><i class="imp">*</i>品牌中文名称:</label>
							<input type="text" placeholder="请输入公司拥有的品牌名称，字数不超过30" class="form-control padding-left-font6"
								value="${brand.brandZHName}" id="brandview_brandZHName" name="brandview_brandZHName" />
						</div>
						<div class="form-group long pull-left margin-left-25">
							<label><i class="imp">*</i>品牌英文名称:</label>
							<input type="text" placeholder="请输入品牌英文名称，如没有填写拼音，字数不超过30" class="form-control padding-left-font6"
								value="${brand.brandENName}" id="brandview_brandENName" name="brandview_brandENName" />
						</div>
					</div>
					<div class="row margin-top-20 file-upload">
						<label> <i>*</i>品牌LOGO:<span>上传品牌logo图片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，长宽不超过200＊84px，白色背景，不超过80K</span></label>
							<div id="brandview_brandLogo_Div" class="row"></div>
					</div>
					<div class="row margin-top-20 file-upload">
						<label> <i>&nbsp;</i>品牌图片:<span>上传品牌商品或品牌说明图片，图片格式为jpg、png、gif、jpeg格式，最多上传10张图片，宽不超过1200px，不超过600K</span></label>
							<div id="brandview_brandrecord_Div" class="row"></div>
					</div>
					<div class="row margin-top-20 file-upload padding-bottom-20">
						<label> <i>*</i>品牌简介:<span>请输入品牌简介，用于商品和公司页面展示，字数不超过2000</span>
						</label>
						<div class="row">
							<!-- 加载编辑器的容器 -->
							<script id="zzcEditor" name="zzcEditor" type="text/plain">
                            </script>
							<script type="text/javascript"
								src="${ctx}/resources/script/plugins/ueidtor/ueditor.config.js"></script>
							<script type="text/javascript"
								src="${ctx}/resources/script/plugins/ueidtor/ueditor.all.js"></script>
							<script type="text/javascript"
								src="${ctx}/resources/script/plugins/ueidtor/lang/zh-cn/zh-cn.js"></script>
							<script type="text/javascript">
								var um = UE.getEditor('zzcEditor');
							</script>
						</div>
					</div>
					<div class="text-center margin-bottom-30">
						<a href="" class="btn btn-info margin-top-30" id="brandView_addBrand">保存</a>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<!-- 图片删除X 实例代码 -->
<div class="modal" id="modal1">
	<div class="modal-content">
		<a href="" class="modal-close modal-close-btn">X</a>
		<h1>modal</h1>
		<p>模太框的使用方法</p>
		<a href="" class="btn btn-danger modal-close">关闭</a>
	</div>
</div>

<!-- 配置文件 -->
<script type="text/javascript" src="${ctx }/resources/script/plugins/fileUpload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx }/resources/script/plugins/fileUpload/jquery.fileupload.js"></script>
<script src="${ctx }/resources/script/models/sysmgr/uploadFileUtil.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/product/brandView.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script>
	var brandDesc = '${brand.brandDesc}';
	$('#zzcEditor').html(brandDesc);
</script>
