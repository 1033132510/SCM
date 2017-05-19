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
</head>
<body>
<!-- 主体切换的内容 -->
<h2 class="title"><a class="first">商品库管理</a><a>>商品维护</a><a>>价格维护</a></h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <input type="hidden" id="id" value="${id}"/>
    <input type="hidden" id="cateId" value="${cateId}"/>
    <div class="container-fluid main-info clearfix">
        <div class="row" id="productAndBrand">
        </div>
        <div class="type-manage" id="productPrice">

        </div>
        <h3 class="head"><a>价格修改记录</a></h3>
        <div class="scroll scroll-h">
            <table class="table">
                <thead>
                <tr>
                    <th width="11%">修改次数</th>
                    <th width="10%">标价</th>
                    <th width="10%">成本价</th>
                    <th width="10%">一级客户报价</th>
                    <th width="10%">二级客户报价</th>
                    <th width="10%">三级客户报价</th>
                    <th width="18%">税/运费</th>
                    <th width="11%">修改人</th>
                    <th width="10%">修改时间</th>
                </tr>
                </thead>
                <tbody id="modifyRecords">
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx }/resources/script/models/sysmgr/product/updateProductPrice.js"></script>
<script id="productAndBrandTpl" type="text/html">
    <h3 class="head"><a>商品信息</a></h3>
    <div class="row margin-bottom-20">
        <div class="form-group long pull-left margin-top-20 margin-left-10">
            <label>商品编号:</label>
            <input class="form-control padding-left-font4 border-none background-none" readonly="readonly"
                   value="<!=productCode!>"/>
        </div>
        <div class="form-group long pull-left margin-top-20 margin-left-10">
            <label>名称:</label>
            <input class="form-control padding-left-font2 border-none background-none" readonly="readonly"
                   value="<!=productName!>"/>
        </div>
        <div class="form-group long pull-left margin-top-20 margin-left-10">
            <label>所属品牌:</label>
            <input class="form-control padding-left-font4 border-none background-none" readonly="readonly"
                   value="<!=brandName!>"/>
        </div>
        <div class="form-group pull-left margin-top-20 margin-left-10 height-30">
            <label><a href="${ctx}/sysmgr/product/searchProductDetail?id=${id}&cateId=${cateId}"
                      target="_blank">查看商品详情</a></label>
        </div>
    </div>
</script>
<script id="productPriceTpl" type="text/html">
    <div class="cost-news">
        <form id="updateProductPriceForm">
            <h3 class="head"><a>费用信息</a></h3>
            <div class="row">
				<div class="form-group margin-left-10 short margin-top-20 margin-right-5 pull-left">
					<label><i class="imp">*</i>标价:</label>
					<input type="text" class="form-control padding-left-font2" name="standard" id="standard" value="<!=prices.standard!>"/>
					<input type="hidden" id="standard_id" value="<!=prices.standard_id!>"/>
				</div>
				<div class="form-group checkbox pull-left margin-right-10 margin-top-20">
			        <label><span>元/</span></label>
			    </div>
				<div class="form-group min margin-top-20 margin-right-30 pull-left">
			        <input type="text" class="form-control" name="unit" id="unit" placeholder="单位" value="<!=unit!>"/>
			    </div>
				<div class="form-group margin-top-20 margin-left-20 checkbox pull-left">
                    <label class="<! if(prices.hasTax == 1) {!>checked<!}!>">
                        <input type="checkbox" id="hasTax"/>
                        <i></i><span>含税</span></label>
                    <label class="margin-left-20 <! if(prices.hasTransportation == 1) {!>checked<!}!>">
                        <input type="checkbox" id="hasTransportation">
                        <i></i><span>含运费</span></label>
                </div>
            </div>
			<div class="row">
				<div class="form-group margin-left-10 short margin-top-20 margin-right-5 pull-left">
					<label><i class="imp">*</i>成本:</label>
					<input type="text" class="form-control padding-left-font2" name="cost" id="cost"
                           value="<!=prices.cost!>"/>
                    <input type="hidden" id="cost_id" value="<!=prices.cost_id!>"/>
				</div>
				<div class="form-group checkbox pull-left margin-right-10 margin-top-20">
					<label><span>元/</span></label>
				</div>
				<div class="form-group min margin-top-20 margin-right-30 pull-left">
					<input type="text" class="form-control" name="unit" readonly placeholder="单位" value="<!=unit!>"/>
				</div>
			</div>
            <div class="row">
				<div class="form-group margin-left-10 short margin-top-20 margin-right-5 pull-left">
					<label>
                        <i class="imp">*</i>一级客户报价:
                    </label>
                    <input type="text" class="form-control padding-left-font6" name="level1" id="level1"
                           value="<!=prices.level1!>"/>
                    <input type="hidden" id="level1_id" value="<!=prices.level1_id!>"/>
				</div>
				<div class="form-group checkbox pull-left margin-right-10 margin-top-20">
					<label><span>元/</span></label>
				</div>
				<div class="form-group min margin-top-20 margin-right-30 pull-left">
					<input type="text" class="form-control" name="unit" readonly placeholder="单位" value="<!=unit!>"/>
				</div>
            </div>
			<div class="row">
				<div class="form-group margin-left-10 short margin-top-20 margin-right-5 pull-left">
					<label>
                        <i class="imp">*</i>二级客户报价:
                    </label>
                    <input type="text" class="form-control padding-left-font6" name="level2" id="level2"
                           value="<!=prices.level2!>"/>
                    <input type="hidden" id="level2_id" value="<!=prices.level2_id!>"/>
				</div>
				<div class="form-group checkbox pull-left margin-right-10 margin-top-20">
					<label><span>元/</span></label>
				</div>
				<div class="form-group min margin-top-20 margin-right-30 pull-left">
					<input type="text" class="form-control" name="unit" readonly placeholder="单位" value="<!=unit!>"/>
				</div>
			</div>
			<div class="row">
				<div class="form-group margin-left-10 short margin-top-20 margin-right-5 pull-left">
					<label>
                        <i class="imp">*</i>三级客户报价:
                    </label>
                    <input type="text" class="form-control padding-left-font6" name="level3" id="level3"
                           value="<!=prices.level3!>"/>
                    <input type="hidden" id="level3_id" value="<!=prices.level3_id!>"/>
				</div>
				<div class="form-group checkbox pull-left margin-right-10 margin-top-20">
					<label><span>元/</span></label>
				</div>
				<div class="form-group min margin-top-20 margin-right-30 pull-left">
					<input type="text" class="form-control" name="unit" readonly placeholder="单位" value="<!=unit!>"/>
				</div>
			</div>
    </div>
    <div class="row">
        <div class="form-group form-textarea margin-top-10 margin-left-10">
            <label>费用备注:</label>
            <textarea type="text" class="form-control" rows="10" cols="100" placeholder="费用备注" id="feeRemark"><!=remarks.feeRemark!></textarea>
        </div>
    </div>
    <div class="row">
        <div class="form-group form-textarea margin-top-10 margin-left-10">
            <label>物流费用:</label>
            <textarea type="text" class="form-control" rows="10" cols="100" placeholder="物流费用备注" id="feeLogistics"><!=remarks.feeLogistics!></textarea>
        </div>
    </div>
    <div class="text-center margin-top-30 margin-bottom-30">
        <input class="btn btn-info" type="button" onclick="ProductPrice.updateProductPrice();" value="确认保存">
    </div>
    </form>
    </div>
</script>
<script id="modifyRecordsTpl" type="text/html">
    <tr>
        <td><!=modCount!></td>
        <td><!=standard!></td>
        <td><!=cost!></td>
        <td><!=level1!></td>
        <td><!=level2!></td>
        <td><!=level3!></td>
        <td><!=taxAndTransportation!></td>
        <td><!=operatorName!></td>
        <td><!=createTime!></td>
    </tr>
</script>
</body>
</html>