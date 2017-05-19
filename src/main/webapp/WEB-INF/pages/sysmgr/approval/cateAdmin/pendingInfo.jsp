<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title">
    <a class="first">审批管理</a><a>>待处理商品</a>
</h2>
<input type="hidden" name="auditBillId" id="auditBillId" value="${auditBillId}" />
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info clearfix" id="approvalRecord">
        
    </div>
    <div class="container-fluid main-info clearfix margin-top-20" id="approvalBlurb">

    </div>
    <div class="container-fluid main-info clearfix margin-top-20" id="approvalExamining">

    </div>
    <div class="text-center margin-top-30 margin-bottom-30">
        <input class="btn btn-info margin-right-10" type="submit" value="审批不通过" id="submitNoPass">
     	<input class="btn btn-info margin-left-10" type="submit" value="提交总监审批" id="submitPass">
    </div>
</div>
<%@ include file="/WEB-INF/pages/supply/approval/recordTemplate.jsp"%>
<%@ include file="/WEB-INF/pages/supply/approval/blurbTemplate.jsp"%>
<%@ include file="/WEB-INF/pages/supply/approval/examineTemplate.jsp"%>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/supply/product/getProductDetailData.js?<%=new Date().getTime()%>"></script>
<script src="${ctx}/resources/script/models/sysmgr/approval/cateAdmin/pendingInfo.js?t="+new Date().valueOf()></script>
