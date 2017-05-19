<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<link rel="stylesheet"
      href="${ctx }/resources/script/plugins/ztree/zTreeStyle.css"
      type="text/css"/>
<h2 class="title"><a class="first">会员管理</a><a>>采购商管理</a><a>>
    <c:if test="${empty purchaser.id}">添加公司信息</c:if>
    <c:if test="${not empty purchaser.id}">编辑公司信息</c:if></a></h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info margin-bottom-25">
        <h3 class="head">公司信息</h3>

        <div class="tree-preview">
            <div class="tree scroll scroll-v">
                <div id="purchaserTree"></div>
            </div>
            <div class="row">
                <c:if test="${not empty purchaser.id and readOnly == 0}">
                    <div class="row margin-top-20" id="relationExistedPurchaserBtnDiv">
                        <a id="relationExistedPurchaser" class="btn btn-warning">关联已有公司</a>
                    </div>
                    <div id="relationExistedPurchaserCodeDiv" class="row margin-top-20 hide">
                        <div class="form-group pull-left">
                            <label class="sr-only" for=""><i class="imp">*</i>关联公司编号:</label>
                            <input type="text" id="relationOrgCode" class="form-control padding-left-font6"/>
                        </div>
                        <div class="pull-left margin-left-25">
                            <a id="getPurchaserInfo" class="btn btn-warning btn-xs">查看</a>
                        </div>
                    </div>
                </c:if>
                <div id="purchaserInfoDiv">
                    <form id="purchaserForm">
                        <div class="row margin-top-20">
                            <div class="form-group pull-left">
                                <label><i class="imp">*</i>公司名称:</label>
                                <input type="text" name="orgName" placeholder="公司名称要与营业执照名称保持一致" id="orgName"
                                       class="form-control padding-left-font4"
                                       value="${fn:escapeXml(purchaser.orgName) }"/>
                            </div>
                            <div class="form-group pull-left margin-left-25">
                                <label><i class="imp">*</i>法人姓名:</label>
                                <input type="text" name="legalName" placeholder="请输入法人姓名，与身份证姓名保持一致" id="legalName"
                                       class="form-control padding-left-font4"
                                       value="${fn:escapeXml(purchaser.legalName)}"/>
                            </div>
                        </div>
                        <div class="row margin-top-20">
                            <div class="form-group pull-left">
                                <label><i class="imp">*</i>公司座机:</label>
                                <input type="text" name="tel" id="tel" placeholder="请输入公司座机号码"
                                       class="form-control padding-left-font4" value="${purchaser.tel }"/>
                            </div>
                            <div class="form-group form-select pull-left margin-left-25">
                                <label><i class="imp">*</i>级别:</label>
                                <select id="level" class="form-control select">
                                    <c:forEach items="${levels }" var="level">
                                        <option value="${level.key}"
                                                <c:if test="${purchaser.level == level.key }">selected</c:if>>${level.value }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="row margin-top-20 file-upload">
                            <label for="">
                                <i>*</i>公司营业执照：
                                <span>上传公司营业执照照片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，尺寸大小600*850或850*600px，文件大小不超过150kb</span>
                            </label>
                            <div id="licenceDiv" class="row"></div>
                        </div>
                        <div class="row margin-top-20 file-upload">
                            <label for="">
                                <i>*</i>组织机构代码证：
                                <span>上传公司组织机构代码证照片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，尺寸大小600*850或850*600px，文件大小不超过150kb</span>
                            </label>
                            <div id="codeLicenceDiv" class="row"></div>
                        </div>
                        <div class="row margin-top-20 file-upload">
                            <label for="">
                                <i>*</i>税务登记证：
                                <span>上传公司税务登记证照片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，尺寸大小600*850或850*600px，文件大小不超过150kb</span>
                            </label>
                            <div id="taxRegistrationDiv" class="row"></div>
                        </div>
                        <div class="row margin-top-20 file-upload">
                            <label for="">
                                <i>*</i>施工资质：
                                <span>上传公司施工资质照片，图片格式为jpg、png、gif、jpeg格式，最多上传1张图片，尺寸大小600*850或850*600px，文件大小不超过150kb</span>
                            </label>
                            <div id="constructionQualificationDiv" class="row"></div>
                        </div>
                        <div id="savePurchaserLinkDiv" class="text-center margin-bottom-30 margin-top-30">
                            <a id="savePurchaser" href="" class="btn btn-info">保存</a>
                        </div>
                        <c:if test="${readOnly == 0}">
                            <div id="relateLinkDiv" class="text-center margin-bottom-30 margin-top-30 hide">
                                <a id="relate" href="" class="btn btn-info">确认关联</a>
                            </div>
                        </c:if>
                        <input type="hidden" id="id" name="id" value="${purchaser.id}"/>
                        <input type="hidden" id="parentId" name="parentId" value="${purchaser.parentId}"/>
                        <input type="hidden" id="originalOrgName" value="${purchaser.orgName}"/>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx }/resources/script/plugins/fileUpload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx }/resources/script/plugins/fileUpload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${ctx }/resources/script/plugins/ztree/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctx}/resources/script/plugins/ztree/ZTreeUtils.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/uploadFileUtil.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/purchaser/addOrUpdatePurchaser.js"></script>
<script type="text/javascript" src="${ctx }/resources/script/plugins/select/select2.min.js"></script>
    <script>
    $(document).ready(function () {
    // carousel
        $(".select").select2({
        minimumResultsForSearch: -1
        });//去掉输入框
    })
    ;
    </script>
