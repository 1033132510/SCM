<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title">
    <a class="first">审批管理</a><a>>已提交商品</a>
</h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info clearfix">
        <div class="row file-upload margin-top-20 margin-left-20 margin-bottom-20">
            <label><span>已提交指供应商把添加好的商品提交审批和已经审批通过的商品，</span></label>
            <label><span>待审批商品等待品类管理员审批，审批通过后，商品上架成功。</span></label>
            <label><span>商品审批过程中，不可修改，如有问题可联系相关审批人。</span></label>
        </div>
        <div class="scroll scroll-h">
            <table class="table bsgrid table-bordered" id="submittedGrid">
                <thead>
                     <tr>
                         <th w_num="total_line" width="5%;">编号</th>
                         <th w_index="productCode" width="8%;">商品编号</th>
                         <th w_index="productName" width="15%;">商品名称</th>
                         <th w_index="brandName" width="20%;">品牌</th>
                         <th w_render="priceShowOperation" width="5%;">价格</th>
                         <th w_index="auditStatus" width="8%;">状态</th>
                         <th w_index="modifiedTime" width="10%;">操作日期</th>
                         <th w_render="approverNameOperation" width="15%;">审批人</th>
                         <th w_render="baseOperation" width="20%;">操作</th>
                     </tr>
                </thead>
            </table>
        </div>
        <div class="pagination text-center margin-top-30" id="pagerBar"></div>
    </div>
</div>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/supply/approval/submitted.js"></script>