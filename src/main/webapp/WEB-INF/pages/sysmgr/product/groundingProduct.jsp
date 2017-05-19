<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="renderer" content="webkit"/>
    <meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible"/>
    <meta name="description" content=""/>
    <meta name="author" content="zhanjun"/>
    <title>商品上架</title>
    <title>商品库管理-商品维护-商品上架</title>
</head>
<body>
<!-- 主体切换的内容 -->
<h2 class="title">
    <a class="first">商品库管理</a><a>>商品维护</a><a>>商品上架</a>
</h2>

<form id="productForm">
    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
        <div class="container-fluid main-info clearfix">
            <div class="type-manage">
                <h3 class="head">
                    <a>商品信息</a>
                    <input type="hidden" id="id" value="${id}"/>
                    <!-- 三级类别id -->
                    <input type="hidden" id="cateId" value="${cateId}"/>
                </h3>

                <div class="padding-top-20 padding-left-20  padding-bottom-20 clearfix">
                    <div class="form-group media pull-left margin-right-25">
                        <label><i class="imp">*</i>商品名称:</label>
                        <input type="text" class="form-control padding-left-font4" id="productName" name="productName"/>
                    </div>
                    <div class="form-group media pull-left margin-right-25">
                        <label>供应商:</label>
                        <input type="text" class="form-control padding-left-font3" id="supplierName" readonly/>
                    </div>
                    <div class="form-group media pull-left margin-right-25">
                        <label>品牌:</label> <input type="text" class="form-control padding-left-font2" id="brandName" readonly/>
                    </div>
                </div>
                <div class="border-bg"></div>

                <div class="row">
                    <div id="productCategoryDefaultContainer" class="padding-left-20 padding-bottom-20 pull-left">
                        <div class="form-group media margin-top-20 pull-left margin-right-25 padding-left-70">
                            <label class="media-label">一级品类：</label>
                            <input type="text" class="form-control" id="firstProductCategoryName" readonly/>
                        </div>
                        <div class="form-group media margin-top-20 pull-left margin-right-25 padding-left-70">
                            <label class="media-label">二级品类：</label>
                            <input type="text" class="form-control" id="secondProductCategoryName" readonly/>
                        </div>
                        <div class="form-group media margin-top-20 pull-left padding-left-70">
                            <label class="media-label">三级品类：</label>
                            <input type="text" class="form-control" id="thirdProductCategoryName" readonly/>
                        </div>
                    </div>
                </div>

                <!-- 锚点 -->
                <a id="hashProductProperty1"></a>
                <a id="hashProductProperty2"></a>
                <!-- 锚点 -->
                <div class="row">
                    <form id="productPropertyForm">
                        <div id="productCategoryAttr">
                            <table class="table table-bordered-bottom margin-bottom-20">
                                <thead id="productPropertiesHead">
                                <tr>
                                    <th width="15%">属性名称</th>
                                    <th width="65%">选择属性</th>
                                    <th width="10%">排序</th>
                                    <th width="10%">操作</th>
                                </tr>
                                </thead>
                                <tbody id="productPropertiesContainer"></tbody>
                            </table>
                        </div>
                    </form>
                    <!--------------------------品类属性end------------------------------->
                    <div class="margin-left-20">
                        <a class="btn btn-info" id="continueAdd">继续添加属性</a>
                    </div>
                    <div class="form-group margin-left-20 margin-top-20">
                        <label><i class="imp">*</i>商品数量:</label>
                        <input type="text" class="form-control padding-left-font4" name="number" id="number" placeholder="只能输入数字"/>
                    </div>
                    <div class="form-group margin-left-20 margin-top-20">
                        <label><i class="imp">*</i>最小起订量:</label>
                        <input type="text" class="form-control padding-left-font5" name="minOrderCount" id="minOrderCount" placeholder="只能输入数字"/>
                    </div>
                    <div class="form-group form-textarea margin-top-10 margin-left-20 margin-right-20">
                        <label>商品描述:</label>
                        <textarea type="text" class="form-control" name="description" id="description" rows="10" cols="100"/></textarea>
                    </div>
                    <div class="row margin-top-20 file-upload margin-left-20 margin-bottom-20">
                        <label> <i>*</i>商品图片（可批量上传）： <span>上传商品展示图片，图片格式为jpg、png、gif、jpeg格式，最多上传15张图片，长宽不超过500＊500px，不超过200k</span></label>
                        <a id="hashProductImage1"></a>
                        <a id="hashProductImage2"></a>

                        <div class="row">
                            <div id="productImage" class="row"></div>
                        </div>
                    </div>

                    <div class="row margin-top-20 file-upload margin-left-20 margin-bottom-20">
                        <label> <i>*</i>商品详情图片（可批量上传）： <span>上传商品详情介绍图片，图片格式为jpg、png、gif、jpeg格式，最多上传15张图片，宽不超过1200px，不超过600K</span></label>
						<a id="hashProductImageDetail1"></a>
                    	<a id="hashProductImageDetail2"></a>
                    	
                        <div class="row">
                            <div id="productImageDetail" class="row"></div>
                        </div>
                    </div>
                    <div class="text-center margin-top-30 margin-bottom-30">
                        <input class="btn btn-info" type="submit" id="groundingProduct" value="确认上架"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<%@ include file="/WEB-INF/pages/sysmgr/product/updateProductPropertiesTemplate.jsp"%>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script src="${ctx}/resources/script/plugins/fileUpload/jquery.ui.widget.js"></script>
<script src="${ctx}/resources/script/plugins/fileUpload/jquery.fileupload.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/uploadFileUtil.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/product/groundingProduct.js?<%=new Date().getTime()%>"></script>
</body>
</html>