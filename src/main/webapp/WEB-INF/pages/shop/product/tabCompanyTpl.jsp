<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script id="tabCompanyDetail" type="text/html">
    <div class="model">
        <span class="pp-logo pull-left">
            <i><img src="<!=supplierLogoImages[0].path!>" /></i>
        </span>
        <strong class="pp-caption"><!:=supplierOrg.orgName!></strong>
    </div>
    <div class="group">
        <h3>公司介绍</h3>
        <div class="row">
            <!:=supplierOrg.supplierOrgIntroductionHtml!>
        </div>
    </div>
    <div class="group certificate">
        <h3>荣誉证书</h3>
        <!if (honorImages.length > 0) {!>
            <!for(var i=0 ;i < honorImages.length ;i++){
                var image = honorImages[i];
            !>
            <i><span><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png" data-original="<!=image.path!>" id="<!=image.id!>" /></span></i>
            <!}!>
        <!} else {!>
            <h3>暂时没有图片</h3>
        <!}!>
    </div>
    <div class="inner"></div>
    <div class="model margin-top-20">
        <div class="row">
            <span class="pp-logo pull-left">
                <i><img src="<!=brandLogoImages[0].path!>" /></i>
            </span>
            <strong class="pp-caption"><!=brand.brandZHName!></strong>
        </div>

        <div class="group">
            <h3>品牌介绍</h3>
            <!if (brand.brandDesc.length > 0) {!>
                <div class="row">
                    <!:=brand.brandDesc!>
                </div>
            <!} else {!>
                <div class="group">
                    暂无品牌描述
                </div>
            <!}!>
        </div>
        <div class="group">
            <h3>品牌展示</h3>
            <!if(brandDescImages.length > 0){!>
            <!for (var i=0 ;i < brandDescImages.length ;i++){ !>
                <div class="row"><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png" data-original="<!=brandDescImages[i].path!>" alt=""/></div>

            <!}!>
            <!} else {!>
                <p>暂无品牌展示</p>
            <!}!>
        </div>
    </div>
</script>
