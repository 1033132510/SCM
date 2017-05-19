<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title">
    <a class="first">审批管理</a><a>>待调整商品</a>
</h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info clearfix">
        <div class="row file-upload margin-top-20 margin-left-20 margin-bottom-20">
            <label><span>待调整指给品类管理员审批不通过的商品，需根据反馈信息重新调整。</span></label>
            <label><span>调整完成后，提交审批（提交审批后，商品会进入已提交商品列表中）。</span></label>
            <label><span>品类管理员审批通过后，商品上架成功。商品审批过程中，不可修改，如有问题可联系相关审批人。</span></label>
        </div>
        <div class="scroll scroll-h">
            <table class="table table-bordered" id="denied_grid">
                <thead>
                <tr>
                    <th w_num="total_line" width="5%;">编号</th>
                    <th w_index="productCode" width="8%;">商品编号</th>
                    <th w_index="productName" width="15%;">商品名称</th>
                    <th w_render="formatterPrice" width="5%;">价格</th>
                    <th w_index="brandName" width="5%;">品牌</th>
                    <th w_index="modifiedTime" width="10%;">审批日期</th>
                    <th w_render="baseOperation" width="15%;">操作</th>
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
<script src="${ctx}/resources/script/models/supply/approval/denied.js"></script>