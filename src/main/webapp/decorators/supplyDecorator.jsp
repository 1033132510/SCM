<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="renderer" content="webkit"/>
    <meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible"/>
    <meta name="description" content=""/>
    <meta name="author" content="zhanjun"/>
    <title>中直采跨境供应商电子商务后台</title>
    <link rel="stylesheet" href="${ctx}/resources/script/plugins/mCustomScrollbar/jquery.mCustomScrollbar.css"/>
    <link rel="stylesheet" href="${ctx}/resources/script/plugins/select/select2.min.css"/>
    <link rel="stylesheet" href="${ctx}/resources/content/sysmgr/site.css"/>
    <script src="${ctx}/resources/script/jquery-1.11.0.min.js"></script>
    <script src="${ctx}/resources/script/plugins/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="${ctx}/resources/script/back.js"></script>
</head>
<body>
<!-- Google Tag Manager -->
<noscript>
    <iframe src="//www.googletagmanager.com/ns.html?id=GTM-KD2RXS" height="0" width="0"
            style="display:none;visibility:hidden"></iframe>
</noscript>
<script>
    (function (w, d, s, l, i) {
        w[l] = w[l] || [];
        w[l].push({
            'gtm.start': new Date().getTime(), event: 'gtm.js'
        });
        var f = d.getElementsByTagName(s)[0],
                j = d.createElement(s), dl = l != 'dataLayer' ? '&l=' + l : '';
        j.async = true;
        j.src =
                '//www.googletagmanager.com/gtm.js?id=' + i + dl;
        f.parentNode.insertBefore(j, f);
    })(window, document, 'script', 'dataLayer', 'GTM-KD2RXS');
</script>
<!-- End Google Tag Manager -->

<header class="clearfix padding-left-20 padding-right-20">
    <h2 class="logo pull-left">中直采跨境供应商电子商务后台</h2>

    <div class="user-info pull-right">
        <i></i> <span>欢迎你,${sessionScope.session_user.displayUserName}</span>
        <c:if test="${not empty sessionScope.session_user.currentOrgName}">
            <span>${sessionScope.session_user.currentOrgName}
                <c:if test="${not empty sessionScope.session_user.currentPositionName}">
                    - ${sessionScope.session_user.currentPositionName}
                </c:if>
            </span>
        </c:if>
        <c:if test="${empty sessionScope.session_user.currentOrgName}">
            <span>暂未分配组织岗位</span>
        </c:if>
        <c:if test="${not empty sessionScope.session_user.displayUserName}">
            <a href="${ctx}/supply/logout">退出</a>
        </c:if>
    </div>
</header>
<nav class="scroll scroll-v">
    <h1 class="logo">
        <a href="">中直采zhongzhicai</a>
    </h1>
    <ul class="slide-nav">
        <shiro:hasAnyRoles name="super_administrator,order_administrator,order_taker">
            <li id="intentionManage" onclick="menu.rememberMenu(event, this);">
                <a href="${ctx }/sysmgr/intentionOrder/view">意向单管理</a>
            </li>
        </shiro:hasAnyRoles>
        <shiro:hasAnyRoles name="super_administrator,buyer_administrator,supplier_administrator">
            <li class="ar" id="memberManage">
                <a javascript:void(0)>会员管理</a>
                <ul>
                    <shiro:hasAnyRoles name="buyer_administrator">
                        <li>
                            <a href="${ctx }/sysmgr/purchaser/registerPurchasersView" id="purchaserRegistList"
                               onclick="menu.rememberMenu(event, this);"
                               value="memberManage">采购商登记信息</a>
                        </li>
                    </shiro:hasAnyRoles>
                    <shiro:hasAnyRoles name="super_administrator,buyer_administrator">
                        <li>
                            <a href="${ctx }/sysmgr/purchaser/view" id="memberPurchaser"
                               onclick="menu.rememberMenu(event, this);"
                               value="memberManage">采购商管理</a>
                        </li>
                    </shiro:hasAnyRoles>
                    <shiro:hasAnyRoles name="supplier_administrator">
                        <li>
                            <a href="${ctx }/sysmgr/supplierOrg/registerSuppliesView" id="supplierRegistList"
                               onclick="menu.rememberMenu(event, this);"
                               value="memberManage">供应商登记信息</a>
                        </li>
                    </shiro:hasAnyRoles>
                    <shiro:hasAnyRoles name="super_administrator,supplier_administrator">
                        <li>
                            <a href="${ctx }/sysmgr/supplierOrg/view" id="memberSupplierOrg"
                               onclick="menu.rememberMenu(event, this);"
                               value="memberManage">供应商管理</a>
                        </li>
                        <%--
                        <li class="margin-left-25">
                            <a href="${ctx }/sysmgr/supplierOrg/invalidBrandManager" id="memberInvalidBrand"
                               onclick="menu.rememberMenu(event, this);" value="memberManage">失效信息管理</a>
                        </li>
                        --%>
                    </shiro:hasAnyRoles>
                </ul>
            </li>
        </shiro:hasAnyRoles>
        <shiro:hasAnyRoles name="super_administrator,category_administrator">
            <li class="ar" id="commodityManage"><a href="javascript:void(0)">商品库管理</a>
                <ul>
                    <li>
                        <a href="${ctx}/sysmgr/category/view" id="productCategory"
                           onclick="menu.rememberMenu(event, this);"
                           value="commodityManage">品类管理</a>
                    </li>
                    <li>
                        <a href="${ctx}/sysmgr/product/validProductManager" id="validProductManager"
                           onclick="menu.rememberMenu(event, this);" value="commodityManage">商品维护</a>
                    </li>
                    <li>
                        <a href="${ctx}/sysmgr/product/invalidProductManager" id="invalidProductManage"
                           onclick="menu.rememberMenu(event, this);" value="productValidManage">下架商品管理</a>
                    </li>
                </ul>
            </li>
        </shiro:hasAnyRoles>
        <shiro:hasAnyRoles name="supplier_operator">
            <li class="ar" id="approvalManageSupply">
                <a href="javascript:void(0)">审批管理</a>
                <ul>
                    <li>
                        <a href="${ctx}/supply/approvals/denied" id="denied"
                           onclick="menu.rememberMenu(event, this);"
                           value="approvalManageSupply">待调整商品</a>
                    </li>
                    <li>
                        <a href="${ctx}/supply/approvals/submitted" id="supplySubmittedView"
                           onclick="menu.rememberMenu(event, this);"
                           value="approvalManageSupply">已提交商品</a>
                    </li>
                </ul>
            </li>
        </shiro:hasAnyRoles>
        <shiro:hasAnyRoles name="category_administrator">
            <li class="ar" id="approvalManageCateAdmin">
                <a href="javascript:void(0)">审批管理</a>
                <ul>
                    <li>
                        <a href="${ctx}/sysmgr/approvals/admin/pending" id="categoryAdminPendingView"
                           onclick="menu.rememberMenu(event, this);"
                           value="approvalManageCateAdmin">待处理</a>
                    </li>
                    <li>
                        <a href="${ctx}/sysmgr/approvals/admin/processed"
                           id="categoryDirectorProcessedView"
                           onclick="menu.rememberMenu(event, this);"
                           value="approvalManageCateAdmin">已处理</a>
                    </li>
                </ul>
            </li>
        </shiro:hasAnyRoles>
        <shiro:hasAnyRoles name="category_majordomo">
            <li class="ar" id="approvalManageCateDirector">
                <a href="javascript:void(0)">审批管理</a>
                <ul>
                    <li>
                        <a href="${ctx}/sysmgr/approvals/director/waitingApproval" id="cateDirectorPendingView"
                           onclick="menu.rememberMenu(event, this);"
                           value="approvalManageCateDirector">待审批</a>
                    </li>
                    <li>
                        <a href="${ctx}/sysmgr/approvals/director/approved"
                           id="cateDirectorProcessedView"
                           onclick="menu.rememberMenu(event, this);"
                           value="approvalManageCateDirector">已审批</a>
                    </li>
                </ul>
            </li>
        </shiro:hasAnyRoles>

        <shiro:hasAnyRoles name="supplier_operator">
            <li class="ar" id="commodityManage"><a href="javascript:void(0)">商品库管理</a>
                <ul>
                    <li>
                        <a href="${ctx}/supply/product/addView" id="addProduct1"
                           onclick="menu.rememberMenu(event, this);"
                           value="addProduct1">添加商品</a>
                    </li>
                    <li>
                        <a href="${ctx}/supply/product/validProductManager" id="supplierValidProductList"
                           onclick="menu.rememberMenu(event, this);"
                           value="supplierValidProductList">上架商品管理</a>
                    </li>
                    <li>
                        <a href="${ctx}/supply/product/invalidProductManager" id="supplierinVlidProductList"
                           onclick="menu.rememberMenu(event, this);"
                           value="supplierinVlidProductList">下架商品管理</a>
                    </li>
                </ul>
            </li>
        </shiro:hasAnyRoles>
        <shiro:hasAnyRoles name="supplier_operator">
            <li class="ar" id="supplierOrgManage"><a href="javascript:void(0)">公司管理</a>
                <ul>
                    <li>
                        <a href="${ctx}/supply/supplierOrgManage/view" id="supplierOrgInfo"
                           onclick="menu.rememberMenu(event, this);"
                           value="supplierOrgManage">公司信息</a>
                    </li>
                </ul>
            </li>
        </shiro:hasAnyRoles>
        <li class="ar" id="changePassword"><a href="javascript:void(0)">账号管理</a>
            <ul>
                <li>

                    <a href="${ctx}/account/supply/password" id="changePasswordChild" onclick="menu.rememberMenu(event, this);"
                       value="changePassword">修改密码</a>
                </li>
            </ul>
        </li>
        <shiro:hasAnyRoles name="super_administrator,system_administrator">
            <li class="ar" id="userManager"><a href="javascript:void(0)">用户管理</a>
                <ul>
                    <li>
                        <a href="${ctx}/sysmgr/companyOrg/view" id="userCompanyOrg"
                           onclick="menu.rememberMenu(event, this);"
                           value="userManager">组织架构维护</a>
                    </li>
                    <li>
                        <a href="${ctx}/sysmgr/position/view" id="userPosition"
                           onclick="menu.rememberMenu(event, this);"
                           value="userManager">岗位维护</a>
                    </li>
                    <li>
                        <a href="${ctx}/sysmgr/employee/view" id="userEmployee"
                           onclick="menu.rememberMenu(event, this);"
                           value="userManager">员工信息维护</a>
                    </li>
                    <li>
                        <a href="${ctx}/sysmgr/role/view" id="userRole" onclick="menu.rememberMenu(event, this);"
                           value="userManager"> 角色维护</a>
                    </li>
                    <li>
                        <a href="${ctx}/sysmgr/category/charge/index" id="categoryCharge"
                           onclick="menu.rememberMenu(event, this);"
                           value="userManager">品类负责人维护</a>
                    </li>
                    <li>
                        <a href="${ctx}/sysmgr/category/charge/major/index" id="categoryMajor"
                           onclick="menu.rememberMenu(event, this);"
                           value="userManager">部类总监维护</a>
                    </li>
                </ul>
            </li>
        </shiro:hasAnyRoles>
    </ul>
    <a class="pack-up"><i>收起</i></a>
</nav>

<!-- 主体切换的内容 -->
<div class="container-fluid main-content padding-bottom-30">
    <sitemesh:write property='body'/>
</div>

<script src="${ctx}/resources/script/plugins/select/select2.min.js"></script>
<script src="${ctx}/resources/script/base.js"></script>
<script src="${ctx}/resources/script/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx}/resources/script/plugins/validate/messages_zh.min.js"></script>
<script src="${ctx}/resources/script/common/ajaxPrefilterForAuthority.js"></script>
</body>
</html>