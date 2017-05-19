<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title">
    <a class="first">审批管理</a><a>>已提交商品</a>
</h2>
<input type="hidden" name="auditBillId" id="auditBillId" value="${auditBillId}" />
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info clearfix" id="approvalRecord">
        
    </div>
    <div class="container-fluid main-info clearfix margin-top-20" id="approvalBlurb">

    </div>
    <div class="text-center margin-bottom-30 margin-top-20">
        <a href="/SCM/supply/approvals/submitted" class="btn btn-info">关闭</a>
    </div>
</div>
<%@ include file="/WEB-INF/pages/supply/approval/recordTemplate.jsp"%>
<%@ include file="/WEB-INF/pages/supply/approval/blurbTemplate.jsp"%>
<script src="${ctx}/resources/script/models/supply/approval/submittedInfo.js?t="+new Date().valueOf()></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script>
    $(function(){
        $(".away").click(function(){
            if($(".away span").html() != "展开"){
                $(".away span").html("展开");
                $(".approve-list").hide();
                $(".away i").css("background-position","0 -510");
            }else{
                $(".away span").html("收起");
                $(".approve-list").show();
                $(".away i").css("background-position","0 -466");
            }
        });
    });
</script>