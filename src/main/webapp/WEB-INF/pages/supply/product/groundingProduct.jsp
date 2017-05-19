<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <title>添加商品</title>
    <title>商品库管理-下架商品管理-设置商品上架</title>
</head>
<body>
<!-- 主体切换的内容 -->
<h2 class="title">
    <a class="first">商品库管理</a><a>>下架商品管理</a><a>>设置商品上架</a>
</h2>

<form id="productForm">
    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
        <div class="container-fluid main-info clearfix">
            <div class="type-manage">
                <h3 class="head">
                    <a>商品信息</a>
                    <input type="hidden" id="id" value=""/>
                    <input type="hidden" id="productSKUId" value="${productSKUId}"/>
                    <input type="hidden" id="cateId" value="${cateId}"/>
                    <input type="hidden" id="parentCateId" value="${parentCateId}"/>
                    <input type="hidden" id="brandId"/>
                    <input type="hidden" id="supplierOrgId"/>
                </h3>

                <div class="padding-left-20  padding-bottom-20 clearfix">
                    <div class="form-group media pull-left margin-top-20 margin-right-25">
                        <label><i class="imp">*</i>商品编号:</label>
                        <input type="text" class="form-control padding-left-font4" id="productCode" name="productCode" readonly/>
                    </div>
                    <div class="form-group media pull-left margin-top-20 margin-right-25">
                        <label><i class="imp">*</i>商品名称:</label>
                        <input type="text" class="form-control padding-left-font4" id="productName" name="productName"/>
                    </div>
                    <div class="form-group media pull-left margin-top-20 margin-right-25">
                        <label>供应商:</label>
                        <input type="text" class="form-control padding-left-font3" id="supplierName" readonly/>
                    </div>
                    <div class="form-group media pull-left margin-top-20 margin-right-25">
                        <label>品牌:</label> <input type="text" class="form-control padding-left-font2" id="brandName"
                                                  readonly/>
                    </div>
                </div>
                <div class="border-bg"></div>

                <div class="row">
                    <div id="productCategoryDefaultContainer"
                         class="padding-top-20 padding-left-20 padding-bottom-20 pull-left">
                        <div class="blurb-cost file-upload pull-left margin-right-25">
                            <label class="media-label pull-left">品类：</label>
                            <label class="pull-left" id="firstLevelCategory"></label><label class="pull-left">></label><label class="pull-left" id="secondLevelCategory"></label><label class="pull-left">></label><label class="pull-left" id="thirdLevelCategory"></label>
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
                        <label><i class="imp">*</i>库存数量:</label> <input type="text"
                                                                        class="form-control padding-left-font4"
                                                                        name="number"
                                                                        id="number" placeholder="只能输入数字"/>
                    </div>
                    <div class="form-group margin-left-20 margin-top-20">
                        <label><i class="imp">*</i>最小起订量:</label> <input type="text"
                                                                        class="form-control padding-left-font5"
                                                                        name="minOrderCount"
                                                                        id="minOrderCount" placeholder="只能输入数字"/>
                    </div>
                    <div class="form-group form-textarea margin-top-10 margin-left-20 margin-right-20">
                        <label>商品描述:</label>
                        <textarea type="text" class="form-control" name="description" id="description" rows="10"
                                  cols="100"/></textarea>
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
                    <div class="container-fluid main-info margin-top-20">
                        <div class="type-manage">
                            <div class="cost-news">
                                <h3 class="head">
                                    <a>费用信息</a>
                                </h3>

                                <div class="padding-left-20">
                                    <div class="row clearfix">
                                        <div class="form-group short margin-top-20 margin-right-5 pull-left">
                                            <label><i class="imp">*</i>成本价:</label>
                                            <input type="text" class="form-control padding-left-font3 cost"
                                                   id="costPrice"
                                                   name="costPrice"/>
                                        </div>
                                        <div class="form-group checkbox pull-left margin-right-10 margin-top-20">
                                            <label><span>元/</span></label></div>
                                        <div class="form-group min margin-top-20 margin-right-30 pull-left">
                                            <input type="text" class="form-control" name="unit" placeholder="单位"/>
                                        </div>
                                        <div class="form-group short margin-top-20 margin-right-5 margin-left-10 pull-left">
                                            <label><i class="imp">*</i>建议售价:</label>
                                            <input type="text" class="form-control padding-left-font4 cost"
                                                   id="recommendedPrice"
                                                   name="recommendedPrice"/>
                                        </div>
                                        <div class="form-group checkbox pull-left margin-right-10 margin-top-20">
                                            <label><span>元/</span></label></div>
                                        <div class="form-group min margin-top-20 margin-right-20 pull-left">
                                            <input type="text" class="form-control" name="unit" placeholder="单位"
                                                   readonly="readonly"/>
                                        </div>
                                        <div class="form-group margin-top-20 margin-left-20 checkbox pull-left">
                                            <label class="checked">
                                                <input type="checkbox" id="hasTax"/>
                                                <i></i><span>含税</span></label>
                                            <label class="checked margin-left-20">
                                                <input type="checkbox" id="hasTransportation">
                                                <i></i><span>含运费</span></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group form-textarea margin-top-10 margin-left-20">
                                    <label>费用备注:</label>
                            <textarea type="text" class="form-control" id="feeRemark" name="feeRemark" rows="10"
                                      cols="100"/></textarea>
                                </div>
                                <div class="form-group form-textarea margin-top-10 margin-left-20">
                                    <label>物流费用:</label>
                            <textarea type="text" class="form-control" id="feeLogistics" name="feeLogistics" rows="10"
                                      cols="100"/></textarea>
                                </div>
                                <div class="text-center margin-top-30 margin-bottom-30">
                                    <input class="btn btn-info margin-right-10" type="submit" value="上架并提交审批"/>
                                    <a class="hide open-modal" data-target="#commentModal" id="commentButton"></a>
                                    <a onclick="ProductDetail.previewProductForUpdate('${ctx}/product/preview');" class="margin-left-10 font-14"
                                       href="javascript:void(0);">预览展示效果</a>
                                </div>
                            </div>
                            <!-------------------------------账号信息end--------------------------------->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<div class="modal" id="commentModal">
    <div class="modal-content">
        <a href="" class="modal-close modal-close-btn">X</a>
        <div class="margin-right-20 margin-top-10 margin-left-20 margin-bottom-20 clearboth">
            <h1 class="text-center margin-bottom-20">确认提交审批</h1>
            <div class="row file-upload margin-top-20 margin-left-20 margin-bottom-20">
                <label><span>商品调整完成后，需要提交给品类管理员来审批，审批通过后，商品上架成功。</span></label>
                <label><span>商品提交审批后，则不可修改，请确认后提交</span></label>
            </div>
            <div class="row file-upload margin-top-20 margin-bottom-5">
                <span class="caption-14 imp"><i>*</i>备注信息</span>
            </div>
            <textarea type="text" class="form-control" rows="10" cols="100%" id="comment"></textarea>
        </div>
        <div class="text-center margin-top-20">
            <a class="btn btn-info modal-close" id="closeCommentModal">取消</a>
            <a class="btn btn-danger" id="submitForm">确认提交</a>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/pages/sysmgr/product/updateProductPropertiesTemplate.jsp" %>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script src="${ctx}/resources/script/plugins/fileUpload/jquery.ui.widget.js"></script>
<script src="${ctx}/resources/script/plugins/fileUpload/jquery.fileupload.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/uploadFileUtil.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/supply/product/getProductDetailData.js?<%=new Date().getTime()%>"></script>
<script src="${ctx}/resources/script/models/supply/product/groundingProduct.js?<%=new Date().getTime()%>"></script>
</body>
</html>