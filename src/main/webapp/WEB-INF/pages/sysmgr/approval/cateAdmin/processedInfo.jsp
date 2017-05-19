<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title">
    <a class="first">审批管理</a><a>>已处理</a>
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
        <input class="btn btn-info" type="submit" id="submitButton" value="关闭">
    </div>
</div>
<%@ include file="/WEB-INF/pages/supply/approval/recordTemplate.jsp"%>
<%@ include file="/WEB-INF/pages/supply/approval/blurbTemplate.jsp"%>
<%@ include file="/WEB-INF/pages/supply/approval/examineReadOnlyTemplate.jsp"%>
<script src="${ctx}/resources/script/models/sysmgr/approval/cateAdmin/processedInfo.js?t="+new Date().valueOf()></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script>
</script>