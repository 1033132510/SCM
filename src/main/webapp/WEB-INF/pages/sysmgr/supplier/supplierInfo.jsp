 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<link href="${ctx}/resources/script/plugins/umeditor/themes/default/css/umeditor.min.css"  type="text/css" rel="stylesheet">
<!-- 主体切换的内容 -->
<h2 class="title"><a class="first">会员管理</a><a>>供应商管理</a><a>>添加</a></h2>
<form name="supplierOrg" method="post"  id="supplierOrg" >
<input type="hidden" class="form-control" name="id" id="id" value="${supplierOrg.id}"/>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20 tab-container ">
    <div class="main-info">
        <div class="tab-content padding-bottom-30 padding-left-10 padding-right-10">
            <div class="tab on">
                <div class="row margin-top-20">
                    <div class="form-group pull-left">
                        <label class="sr-only" for=""><i class="imp">*</i>公司名称:</label>
                        <input type="text" class="form-control" name="orgName" id="orgName" value="${supplierOrg.orgName}"/>
                    </div>
                    <div class="form-group pull-left margin-left-25">
                        <label class="sr-only" for=""><i class="imp">*</i>法人姓名:</label>
                        <input type="text" class="form-control" placeholder="请输入法人姓名与身份证姓名保持一致" name="legalPerson" id="legalPerson" value="${supplierOrg.legalPerson}"/>
                    </div>
                </div>
                <div class="row margin-top-20">
                    <div class="form-group pull-left">
                        <label class="sr-only" for=""><i class="imp">*</i>联系电话:</label>
                        <input type="text" class="form-control" placeholder="请输入联系电话" id="contactNumber" name="contactNumber" value="${supplierOrg.contactNumber}"/>
                    </div>
                    <div class="form-group form-select pull-left margin-left-25 ">
                        <label class="sr-only" for=""><i class="imp">*</i>类型:</label>
                      
                        <select name="supplierType" id="supplierType" class="form-control">
                        	<option value="">请选择</option>
							<c:forEach  var="supplierTypeEnum" items="${supplierTypeEnums}" >
								<option value="${supplierTypeEnum.key}" <c:if test="${supplierOrg.supplierType == supplierTypeEnum.key }">selected</c:if>>${supplierTypeEnum.value}</option>
							</c:forEach>
						</select>
						
                    </div>
                </div>
                <div class="row margin-top-20">
                    <div class="form-group pull-left">
                        <label class="sr-only" for=""><i class="imp">*</i>公司座机:</label>
                        <input type="text" class="form-control" placeholder="请输入公司座机号码" name="telNumber" id="telNumber" value="${supplierOrg.telNumber}"/>
                    </div>
                    <div class="form-group form-select margin-left-25 pull-left">
                        <label class="sr-only" for=""><i class="imp">*</i>状态:</label>
                       <!--  
                        <select  class="form-control" id="status" name="status">
                            <option value="">请选择</option>
                            <c:forEach  var="statusEnum" items="${statusEnums }" >
								<option value="${statusEnum.key}">${statusEnum.value}</option>
							</c:forEach>
                        </select>
                        -->
                    </div>
                </div>
                <div class="row margin-top-20 file-upload">
                    <label for="">
                        <i>*</i>公司营业执照：
                        <span>上传公司营业执照照片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，600*850px，文件大小不超过150kb</span>
                    </label>
					<div class="row">
						<c:forEach  var="image" items="${imageList}" >
							<c:if test="${not empty image.path && image.relationType == businessLicenceType}">
		                	<span class="preview">
				                <img src="${ctx }${image.path}" alt=""/>
		            		</span>
	                		</c:if>
						</c:forEach>
	                    <a id="businessLicencePath" class="upload-btn"><input id="businessLicenceImage" type="file" name="file" accept="image/*"/></a>
	                </div>
	            </div>
                </div>
                <div class="row margin-top-20 file-upload">
                    <label for="">
                        <i>*</i>公司营业执照：
                        <span>上传公司营业执照照片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，600*850px，文件大小不超过150kb</span>
                    </label>

                    <div class="row">
						<c:forEach  var="image" items="${imageList}" >
							<c:if test="${not empty image.path && image.relationType == orgCodeType}">
		                	<span class="preview">
				                <img src="${ctx }${image.path}" alt=""/>
		            		</span>
	                		</c:if>
						</c:forEach>
	                    <a id="orgcodePath" class="upload-btn"><input id="orgCode" type="file" name="file" accept="image/*"/></a>
	                </div>
                </div>
                <div class="row margin-top-20 file-upload">
                    <label for="">
                        <i>*</i>公司logo：
                        <span>上传公司logo图片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，长宽不超过200＊84px，白色背景，不超过80K</span>
                    </label>

                    <div class="row">
						<c:forEach  var="image" items="${imageList}" >
							<c:if test="${not empty image.path && image.relationType == LOGOType}">
		                	<span class="preview">
				                <img src="${ctx }${image.path}" alt=""/>
		            		</span>
	                		</c:if>
						</c:forEach>
	                    <a id="LOGOPath" class="upload-btn"><input id="LOGO" type="file" name="file" accept="image/*"/></a>
	                </div>
                </div>
                <div class="row margin-top-20 file-upload">
                    <label for="">
                        <i>*</i>荣誉证书图片：
                        <span>上传公司荣誉证书照片，图片格式为jpg、png、gif、jpeg格式，最多上传10张图片，600*850px，文件大小不超过150kb</span>
                    </label>

                    <div class="row">
						<c:forEach  var="image" items="${imageList}" >
							<c:if test="${not empty image.path && image.relationType == honorType}">
		                	<span class="preview">
				                <img src="${ctx }${image.path}" alt=""/>
		            		</span>
	                		</c:if>
						</c:forEach>
	                    <a id="honorPath" class="upload-btn"><input id="honor" type="file" name="file" accept="image/*"/></a>
	                </div>
                </div>
                <div class="row margin-top-20 file-upload padding-bottom-20">
                    <label for="">
                        <i>*</i>公司简介：
                        <span>请输入公司简介，用于商品和公司页面展示，字数不超过2000</span>
                    </label>

                    <div class="row">
                    	<!--  
                        <textarea name="" id="" cols="30" rows="10" placeholder="录入公司简介荣誉证书，图片等内容"></textarea>
                    	-->
                    	<!-- 加载编辑器的容器 -->
                            <script id="supplierIntroduction" name="supplierIntroduction" type="text/plain" class="zzcEditor">
                                <p class="init-content">录入公司简介荣誉证书，图片等内容</p>
                            </script>
                    </div>
                </div>
                <div class="text-center margin-bottom-30 padding-bottom-30 padding-top-10">
                        <a onclick="saveSupplierOrg('${supplierOrg.id}')" class="btn btn-info margin-top-30"> 保 &nbsp;存</a>
                </div>
            </div>
        </div>
    </div>
</div>
</form>
<script type="text/javascript" src="${ctx}/resources/script/plugins/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/script/plugins/umeditor/umeditor.config.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/supplier/supplierInfo.js?t="+new Date().valueOf()></script>