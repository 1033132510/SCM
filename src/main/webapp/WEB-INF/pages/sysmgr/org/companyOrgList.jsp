<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/script/plugins/ztree/zTreeStyle.css" type="text/css"/>

<!-- 主体切换的内容 -->
<h2 class="title"><a class="first">组织管理</a></h2>

<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info margin-bottom-25">
        <h3 class="head">公司信息</h3>

        <form id="companyOrgForm">
            <input type="hidden" id="orgLevel" value=""/>
            <input type="hidden" id="parentOrgId" value=""/>
            <input type="hidden" id="id" value=""/>
            <!-- 修改判重 -->
            <input type="hidden" id="oldOrgName" value=""/>

            <div class="tree-preview">
                <!-- 组织树 -->
                <div class="tree scroll scroll-v">
                    <div id="companyTree"></div>
                </div>
                <!-- 组织详情 -->
                <div class="row" id="orgInfoDiv" style="display: none">
                    <div class="form-group long margin-top-20" id="companyOrgInfoDiv">
                        <label><i class="imp">*</i>上级组织:</label>
                        <input type="text" id="parentOrgName" readonly="true" class="form-control padding-left-font4"/>
                    </div>

                    <div class="form-group long margin-top-20" id="companyOrgInfoDiv">
                        <label><i class="imp">*</i>组织名称:</label>
                        <input type="text" id="orgName" class="form-control padding-left-font4 required hasExist"/>
                    </div>

                    <div class="form-group long margin-top-20">
                        <label>联系电话:</label>
                        <input type="text" id="phone" name="phone" class="form-control padding-left-font4">
                    </div>
                    <div class="form-group form-textarea long margin-top-20">
                        <label>描述:</label>
                            <textarea id="description" name="description" rows="5" cols="30" placeholder="组织职能描述"
                                      class="form-control"></textarea>
                    </div>
                    <div id="saveOrgDiv" class="text-center margin-bottom-30 margin-top-30">
                        <input type="submit" id="saveOrg" class="btn btn-info"></input>
                        <input id="reset" type="reset" style="display:none"></input>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="${ctx }/resources/script/plugins/ztree/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctx}/resources/script/plugins/ztree/ZTreeUtils.js"></script>
<script src="${ctx}/resources/script/common/dataSubmitUtil.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/org/companyOrg.js"></script>
<script src="${ctx}/resources/script/plugins/select/select2.min.js"></script>