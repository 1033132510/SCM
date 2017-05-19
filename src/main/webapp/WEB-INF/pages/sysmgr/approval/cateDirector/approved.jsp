<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title">
    <a class="first">审批管理</a><a>>已审批</a>
</h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info clearfix">
        <div class="row file-upload margin-top-20 margin-left-20 margin-bottom-20">
            <label><span>已审批指品类总监以及审批过的商品，包含审批通过上架商品和待品类管理员管理员调整的商品。</span></label>
        </div>
        <div class="scroll scroll-h">
            <table class="table bsgrid" id="directorProcessedGrid">
                <tr>
                    <th w_num="total_line" width="5%;">编号</th>
					<th w_index="productCode" width="8%;">商品编号</th>
                    <th w_index="productName" width="10%;">商品名称</th>
                    <th w_render="standardOperation" width="10%;">价格</th>
                    <th w_index="brandName" width="15%;">所属品牌</th>
                    <th w_index="auditStatus" width="8%">状态</th>
                    <th w_index="createTime" width="15%;">操作日期</th>
                    <th w_render="baseOperation" width="10%;">操作</th>
                </tr>
            </table>
        </div>
    </div>
</div>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/approval/cateDirector/approved.js"></script>
