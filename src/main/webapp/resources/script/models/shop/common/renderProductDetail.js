var bt = baidu.template;
var commonRender = {
    setHeard: function (data) {
        var fCate = data.category.parentCategory.parentCategory.cateName;
        var sCate = data.category.parentCategory.cateName;
        var tCate = data.category.cateName;
        var fCateId = data.category.parentCategory.parentCategory.id;
        var sCateId = data.category.parentCategory.id;
        var tCateId = data.category.id;
        $('#fCate').html(fCate);
        $('#sCate').html("&gt;" + sCate);
        $('#tCate').html("&gt;" + tCate);
        $('#fCate').bind('click', function () {
            location.href = ctx + '/shop/search/category/childCategoryView?firstId=' + fCateId + "&t=" + new Date().valueOf();
        });
        $('#sCate').bind('click', function () {
            location.href = ctx + '/shop/search/category/childCategoryView?firstId=' + fCateId + '&secondId=' + sCateId + "&t=" + new Date().valueOf();
        });
        $('#tCate').bind('click', function () {
            location.href = ctx + '/shop/search/category/childCategoryView?firstId=' + fCateId + '&secondId=' + sCateId + "&thirdId=" + tCateId + "&t=" + new Date().valueOf();
        });
    },
    leftImages: function (data) {	// 左侧图片展示
        var htmlPreview = '';
        if (data.productImages.length > 0) {
            var item = {
                images: data.productImages
            };
            htmlPreview += bt('previewTpl', item);
        }
        else {
            htmlPreview += '<div class="text-center padding-top-30">没有数据哦</div>';
        }
        return htmlPreview;
    },
    leftImagesAnimate: function () {// 左侧商品切换
        $('.slider-for').slick({
            lazyLoad: 'ondemand',
            slidesToShow: 1,
            slidesToScroll: 1,
            arrows: false,
            fade: true,
            asNavFor: '.slider-nav'
        });
        var slideCount = $('.slider-nav div').length;
        var slidesToShow = 5;
        var Boole = false;
        if (slideCount < slidesToShow || slideCount == slidesToShow) {
            Boole = false;
        } else {
            Boole = true;
        }
        $('.slider-nav').slick({
            slidesToShow: slidesToShow,
            lazyLoad: 'ondemand',
            slidesToScroll: 1,
            asNavFor: '.slider-for',
            dots: false,
            centerMode: Boole,
            centerPadding: 0,
            focusOnSelect: true
        });
    },
    rightInfo: function (data) { // 右侧详细信息
        var htmlInner = bt('infoTpl', {
            productName: data.productName,
            productPrice: utils.toFixed(data.productPrices[0].actuallyPrice),
            //discountPrice: utils.toFixed(data.productPrices[1].actuallyPrice),
            productCode: data.productCode ? data.productCode : '000001',
            tip: data.productCode ? '' : '(模拟商品编码，正式商品编码在品类管理员审批通过后生效)',
            minOrderCount: data.minOrderCount,
            productNumber: data.productNumber,
            productCategoryId: data.category.id,
            productId: data.productId,
            purchaserLevel: data.customData.purchaserLevel,
            status: data.status,
            hasTax: data.hasTax,
            hasTransportation: data.hasTransportation,
            unit: data.unit ? '/' + data.unit : ''
        });
        return htmlInner;
    },
    rightInfoReadOnly: function (data) {
        var htmlInner = bt('infoTpl', {
            productName: data.productName,
            productPrice: utils.toFixed(data.price),
            productCode: data.productCode,
            quantity: data.quantity,
            hasTax: data.hasTax,
            hasTransportation: data.hasTransportation
        });
        return htmlInner;
    },
    rightEvent: function () {
        var i = $(".choose-amount input").val();
        $(".choose-amount input").on('blur', function () {
            var value = $(this).val(), reg = /^[1-9]{1}[0-9]*$/;
            if (!reg.test(value)) {
                $(this).val(i);
                return false;
            }
            i = parseInt(value);
        });
        $('#amountAdd').on('click', function () {
            var i = parseInt($(".choose-amount input").val());
            $(".choose-amount input").val(++i);
        });
        $('#amountReduce').on('click', function () {
            var i = parseInt($(".choose-amount input").val());
            if (i == 1) {
                return false;
            }
            $(".choose-amount input").val(--i);
        });
        $('#addCart').on('click', function (e) {
            e.preventDefault();
            var params = {
                productSkuId: productId,
                productCategoryId: cateId,
                quantity: $('.choose-amount').find('input[id="quantity"]').val()
            }
            $.ajax({
                url: ctx + '/shop/cart',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(params),
                dataType: 'json',
                success: function (result) {
                    if (result && result.success) {
                        $('.shop-count').html('(' + result.resultData + ')');
                        zcal({
                            type: 'success',
                            title: '提示',
                            text: '加入购物车成功'
                        });
                    } else {
                        zcal({
                            type: 'error',
                            title: '提示',
                            text: '加入购物车失败'
                        });
                    }
                },
                error: function () {
                    zcal({
                        type: 'error',
                        title: '提示',
                        text: '加入购物车失败'
                    });
                }
            });
        });
    },
    tabProductDetail: function (data) {
        return bt('tabProductDetail', data);
    },
    tabCompanyDetail: function (data) {
        return bt('tabCompanyDetail', data);
    },
    tabProductPreview: function (data) {
        return bt('tabProductPreview', data);
    },
    rend: function (data, isComplete) {
        this.setHeard(data);
        this.tabProductPreview(data);
        $('.product-intro .preview').html(this.leftImages(data));
        $('.tab-product-detail').html(this.tabProductDetail(data));
        $('.tab-company-detail').html(this.tabCompanyDetail(data));
        this.leftImagesAnimate();
        if (isComplete) {
            $('.product-intro .inner').html(this.rightInfo(data));
            this.rightEvent();
        } else {
            $('.product-intro .inner').html(this.rightInfoReadOnly(data));
        }
        // 产品详情添加延迟加载
        $("img.lazy").lazyload({
            effect: "fadeIn",
            threshold: 200
        });
    },
}