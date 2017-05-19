<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title">
    <a class="first">审批管理</a><a>>待审批商品</a>
</h2>
<input type="hidden" name="auditBillId" id="auditBillId" value="${auditBillId}"/>
<input type="hidden" name="operationType" id="operationType" value="${operationType}"/>
<input type="hidden" name="OFF_SHELVE" id="OFF_SHELVE" value="${OFF_SHELVE}"/>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info clearfix" id="approvalRecord">

    </div>
    <div class="container-fluid main-info clearfix margin-top-20" id="approvalBlurb">

    </div>
    <div class="container-fluid main-info clearfix margin-top-20" id="approvalExamining">

    </div>
    <div class="text-center margin-top-30 margin-bottom-30">
        <input class="btn btn-info margin-right-10" type="submit" value="审批不通过" id="submitNoPass">
        <!-- operationType -->
        <c:if test="${operationType== OFF_SHELVE}">
            <input class="btn btn-info margin-left-10" type="submit" value="下架" id="submitPass">
        </c:if>
        <c:if test="${operationType != OFF_SHELVE}">
            <input class="btn btn-info margin-left-10" type="submit" value="上架" id="submitPass">
        </c:if>
    </div>
</div>
<%@ include file="/WEB-INF/pages/supply/approval/recordTemplate.jsp" %>
<%@ include file="/WEB-INF/pages/supply/approval/blurbTemplate.jsp" %>
<%@ include file="/WEB-INF/pages/supply/approval/examineTemplate.jsp" %>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/supply/product/getProductDetailData.js?<%=new Date().getTime()%>"></script>
<script src="${ctx}/resources/script/models/sysmgr/approval/cateDirector/waitingApprovalInfo.js?t=" +new
        Date().valueOf()></script>
