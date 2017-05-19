$(function () {
    if ($('.main-content').hasClass('product-detail') || $('.main-content').hasClass('company-detail')) {
        scrollTop({
            name: '.main-content .tab-nav a',
            time: 0,
            distance: '.shortcut',
            windowScroll: false
        });
        scrollTop({
            name: '.main-content .tab-nav a',
            time: 500,
            distance: '.main-content .tab-nav',
            windowScroll: false
        });
        $(window).scroll(function () {
            var scrollTop = $(window).scrollTop();
            var tabTop = $('.main-content .tab-container').offset().top;
            if (scrollTop > tabTop) {
                $('.main-content .tab-container').addClass('fixed');
            }
            else {
                $('.main-content .tab-container').removeClass('fixed');
            }
        });
    }
    scrollTop({
        name: '.back-top',
        time: 500,
        windowScroll: true
    });
    // 详情页面导航切换
    $('.switch').click(function (e) {
        e.stopPropagation();
        e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        if ($('.level2').hasClass('on')) {
            $('.level2').removeClass('on').siblings('.level1').addClass('on');
        }
        else {
            $('.level1').removeClass('on').siblings('.level2').addClass('on');
        }
    });

    // 点击小图大图切换
    $(document).on('click', '.sm-img span', function (e) {
        e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        var imgWidth = 34;
        var showLength = $(this).closest('.sm-img').width() / imgWidth - 1;
        var imgIndex = $(this).index() + 1;
        var imgHtml = $(this).html();
        var smImg = $(this).closest('li');
        var imgLength = smImg.find('.sm-img span').length;
        smImg.find('.sm-img div').css('width', imgLength * imgWidth);

        $(this).addClass('on').siblings('span').removeClass('on');
        smImg.find('.big-img span').html(imgHtml);

        if (imgIndex > showLength && imgIndex != imgLength) {
            smImg.find('.sm-img div').animate({
                left: -imgWidth * (imgIndex - showLength) + 'px'
            }, 200);
        }
        else if (imgIndex < showLength) {
            smImg.find('.sm-img div').animate({
                left: 0
            }, 200);
        }
    });

    // 判断是什么系统
    function detectOS() {
        var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
        var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
        if (isMac) {
            $('html').addClass('mac');
        } else if (isWin) {
            $('html').addClass('win');
        }
    }

    detectOS();

    // 购物车数量
    $.ajax({
        url: ctx + '/shop/cart/count',
        type: 'GET',
        success: function (result) {
            if ($.isNumeric(result) && parseInt(result) >= 0) {
                $('.shop-count').html('(' + result + ')');
            }
        }
    });

    // 首页搜索处的切换
    // appendSelect();
    if ($('#seaType').val() == 1) {
        searchSelect = true;
    }
    $('.search-terms').click(function () {
        $(this).toggleClass('on');
    });
    $('.search-terms .dn').click(function () {
        if (searchSelect != true) {
            searchSelect = true;
        }
        else {
            searchSelect = false;
        }
        appendSelect();
    });

    // placeholder text color
    if ($('input[placeholder]').val() == '') {
        $('input[placeholder]').css('color', '#666');
    }
    $('input[placeholder]').focus(function () {
        $(this).css({
            'color': '#2a2e23'
        });
    }).blur(function () {
        if ($(this).val() == '') {
            $(this).css({
                'color': '#666'
            });
        }
    });
});
var searchSelect = false;
function appendSelect() {
    var tpHtml = '<a href="javascript:void(0)">商品</a>';
    var dnHtml = '<a href="javascript:void(0)">品牌</a>';
    if (searchSelect != true) {
        $('.search-terms .tp').html(tpHtml);
        $('.search-terms .dn').html(dnHtml);
        $('#searchName').attr("placeholder", "商品名称检索");
    }
    else {
        $('.search-terms .dn').html(tpHtml);
        $('.search-terms .tp').html(dnHtml);
        $('#searchName').attr("placeholder", "品牌名称检索");
    }
}




