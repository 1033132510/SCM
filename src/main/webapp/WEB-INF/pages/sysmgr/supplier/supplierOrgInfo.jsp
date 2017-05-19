<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<link href="${ctx}/resources/script/plugins/ueidtor/themes/default/css/ueditor.css"  type="text/css" rel="stylesheet">
<!-- 主体切换的内容 -->
<h2 class="title"><a class="first">会员管理</a><a>>供应商管理</a><a>>添加</a></h2>
<input type="hidden" class="form-control" name="id" id="id" value="${supplierOrg.id}"/>
<input type="hidden" class="form-control" name="statusFlag" id="statusFlag" value="${status}"/>
<form id="supplierOrgForm" name="supplierOrgForm">
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20 tab-container ">
    <div class="main-info">
        <div class="tab-content padding-bottom-20 padding-left-20 padding-right-20">
            <div class="tab on">
                <div class="row margin-top-20">
                    <div class="form-group pull-left">
                        <label><i class="imp">*</i>公司名称:</label>
                        <input type="text" class="form-control padding-left-font4" placeholder="公司名称要与营业执照名称保持一致" name="orgName" id="orgName" value="${supplierOrg.orgName}"/>
                    </div>
                    <div class="form-group pull-left margin-left-25">
                        <label><i class="imp">*</i>法人姓名:</label>
                        <input type="text" class="form-control padding-left-font4" placeholder="请输入法人姓名与身份证姓名保持一致" name="legalPerson" id="legalPerson" value="${supplierOrg.legalPerson}"/>
                    </div>
                </div>
                <div class="row margin-top-20">
                    <div class="form-group pull-left">
                        <label><i class="imp">*</i>公司座机:</label>
                        <input type="text" class="form-control padding-left-font4" placeholder="请输入公司座机号码" id="telNumber" name="telNumber" value="${supplierOrg.telNumber}"/>
                    </div>
                    <div class="form-group form-select pull-left margin-left-25 ">
                        <label><i class="imp">*</i>类型:</label>
                        <select name="supplierType" id="supplierType" class="form-control">
							<c:forEach  var="supplierTypeEnum" items="${supplierTypeEnums}" >
								<option value="${supplierTypeEnum.key}" <c:if test="${supplierOrg.supplierType == supplierTypeEnum.key }">selected</c:if>>${supplierTypeEnum.value}</option>
							</c:forEach>
						</select>
                    </div>
                </div>
                <div class="row margin-top-20" style="display:none;">
                    <div class="form-group form-select pull-left ">
                        <label><i class="imp">*</i>状态:</label>
                        <select  class="form-control padding-left-font4" id="status" name="status">
                            <c:forEach  var="statusEnum" items="${statusEnums}" >
								<option value="${statusEnum.key}" <c:if test="${supplierOrg.status == statusEnum.key }">selected</c:if> >${statusEnum.value}</option>
							</c:forEach>
                        </select>
                    </div>
                </div>
	            <div class="row margin-top-20 file-upload">
	                <label>
	                    <i>*</i>公司营业执照（年检）：
                        <span>上传公司营业执照（年检）照片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，尺寸大小600*850或850*600px，文件大小不超过150kb</span>
	                </label>
	                <div id="businessLicenceDiv" class="row"></div>
	            </div>
	            <div class="row margin-top-20 file-upload">
	                <label>
	                    <i>*</i>组织机构代码证：
	                    <span>上传公司组织机构代码证照片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，尺寸大小600*850或850*600px，文件大小不超过150kb</span>
	                </label>
	                <div id="orgCodeDiv" class="row"></div>
	            </div>
	            <div class="row margin-top-20 file-upload">
	                <label>
	                    <i>*</i>公司logo：
	                    <span>上传公司logo图片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，长宽不超过200＊84px，白色背景，不超过80K</span>
	                </label>
	                <div id="LOGODiv" class="row"></div>
	            </div>
	            <div class="row margin-top-20 file-upload">
	                <label>
	                    <i>&nbsp;</i>荣誉证书：
                        <span>上传公司荣誉证书照片，图片格式为jpg、png、gif、jpeg格式，最多上传10张图片，尺寸大小600*850或850*600px，文件大小不超过150kb</span>
	                </label>
	                <div id="honorDiv" class="row"></div>
	            </div>
	            
	            <div class="row margin-top-20 file-upload">
	                <label>
	                    <i>*</i>税务登记证：
                        <span>上传公司税务登记证照片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，尺寸大小600*850或850*600px，文件大小不超过150kb</span>
	                </label>
	                <div id="taxRegistrationCertificateDiv" class="row"></div>
	            </div>
	            
	            <div class="row margin-top-20 file-upload">
	                <label>
	                    <i>&nbsp;</i>商标注册证：
                        <span>上传公司商标注册证照片，图片格式为jpg、png、gif、jpeg格式，最多上传10张图片，尺寸大小600*850或850*600px，文件大小不超过150kb</span>
	                </label>
	                <div id="trademarkRegistrationCertificateDiv" class="row"></div>
	            </div>
	            
	            <div class="row margin-top-20 file-upload">
	                <label>
	                    <i>&nbsp;</i>法人委托书：
                        <span>上传公司法人委托书照片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，尺寸大小600*850或850*600px，文件大小不超过150kb</span>
	                </label>
	                <div id="powerOfAttorneyDiv" class="row"></div>
	            </div>
	            <!-- 
	            <div class="row margin-top-20 file-upload">
	                <label>
	                    <i>*</i>总经销商或总代理授权书：
                        <span>上传总经销商或总代理授权书照片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，尺寸大小600*850或850*600px，文件大小不超过150kb</span>
	                </label>
	                <div id="soleDistributorPaDiv" class="row"></div>
	            </div>
	             -->
                <div class="row margin-top-20 file-upload padding-bottom-20">
                    <label>
                        <i>&nbsp;</i>公司简介：
                        <span>请输入公司简介，用于商品和公司页面展示，字数不超过2000</span>
                    </label>

                    <div class="row">
                    	<!--  
                        <textarea name="" id="" cols="30" rows="10" placeholder="录入公司简介荣誉证书，图片等内容"></textarea>
                    	-->
                    	<!-- 加载编辑器的容器 -->
                            <script id="supplierOrgIntroduction" name="content" type="text/plain" >
                            </script>
                            <script type="text/javascript" src="${ctx}/resources/script/plugins/ueidtor/ueditor.config.js"></script>
                            
                            <script type="text/javascript" src="${ctx}/resources/script/plugins/ueidtor/ueditor.all.js"></script>
                            <script type="text/javascript" src="${ctx}/resources/script/plugins/ueidtor/lang/zh-cn/zh-cn.js"></script>
                            
                            <script type="text/javascript">
					        	var ue = UE.getEditor('supplierOrgIntroduction');
					   		</script>
                    </div>
                </div>
                <div class="text-center margin-bottom-30">
               		<c:if test="${status==1}">
               			<a onclick="setSupplierOrgStatus(1)" class="btn btn-info margin-top-30">确认有效</a>
               		</c:if>
               		<c:if test="${status==0}">
               			<a onclick="setSupplierOrgStatus(0)" class="btn btn-info margin-top-30">确认无效</a>
               		</c:if>
               		<c:if test="${empty status}">
               			<a onclick="saveSupplierOrg()" class="btn btn-info margin-top-30"> 保存</a>
               		</c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</form>
<script type="text/javascript" src="${ctx}/resources/script/plugins/fileUpload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/resources/script/plugins/fileUpload/jquery.fileupload.js"></script>
<script src="${ctx }/resources/script/models/sysmgr/uploadFileUtil.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/supplier/supplierOrgInfo.js?t="+new Date().valueOf()></script>
<script>
	var  initIntroduction = '${supplierOrg.supplierOrgIntroductionHtml}';
	$('#supplierOrgIntroduction').html(initIntroduction);
</script>