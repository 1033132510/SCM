var childCateList = [];
var divId;
var secondCategoryBt = baidu.template;
$(function () {
    $('.banner').slick({
        dots: true,
        infinite: true,
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 5000
    });
    //显示首页Ul菜单
    //是否显示
    categoryUl.init('indexCategoryDiv', true);
});
function getSChildCateByFirstCate(catgegoryId, divId) {
    $.ajax({
        url: ctx + '/shop/category/cateShow',
        data: {
            parentCategoryId: catgegoryId,
            level: 2,
            status: 1,
            t: new Date().valueOf()
        },
        type: 'post',
        async: true,
        success: function (data) {
            showSecondCategoryTemplate(divId, data);
        },
        error: function () {
            zcal({
                type: "error",
                title: "加载数据失败"
            });
        }
    });
}
function showSecondCategoryTemplate(divId, data) {
    var shh = $('#' + divId);
    shh.html('');
    var item;
    var html = '';
    if (data.length < 14) {
        ltspan = data.length;
    }
    else {
        ltspan = 14;
    }
    for (var i = 0; i < ltspan; i++) {
        item = data[i];
        html += secondCategoryBt('tempHtmlSecondCategoryInit', item);
    }
    shh.append($(html));
}

function companyView(th) {
    if (!$.trim($(th).attr("selfId"))) {
        return;
    }
    location.href = ctx + '/shop/company/view?orgId=' + $(th).attr("selfId") + "&t=" + new Date().valueOf();
}

function productView(th) {
    if (!$.trim($(th).attr("selfId"))) {
        return;
    }
    location.href = ctx + '/shop/product/gotoView?productId=' + $(th).attr("selfId") + '&cateId=' + $(th).attr("cateId") + "&t=" + new Date().valueOf();
}

//初始化二级块
$(function () {
    //室内装饰 室外装饰   大总土建 机电设别
    var categoryIds = ['ff80808151565b2e015160b47e150009', 'ff80808151565b2e015160cf6af2003c',
        'ff80808151565b2e015160be71d00018', 'ff80808151565b2e015160b27faf0001'];
    var divId = '';
    for (var i = 0; i < categoryIds.length; i++) {
        divId = 'equipment' + (i + 1);
        getSChildCateByFirstCate(categoryIds[i], divId);
    }
});

//绑定事件	二级块
$(document).on('click', 'div[id^=equipment] a, .article .more', function (e) {
    e.preventDefault();
    var id = $(this).attr('selfId');
    var firstId = $(this).attr('firstId');
    location.href = ctx + '/shop/search/category/childCategoryView?firstId=' + firstId + '&secondId=' + id + "&t=" + new Date().valueOf();
});


//一级二级三级绑定事件
$(document).on('click', '#firstUl li a, .article .more', function (e) {
    e.preventDefault();
    location.href = ctx + '/shop/search/category/childCategoryView?firstId=' + $(this).attr('firstId') + '&secondId=' + $(this).attr('secondId') + "&thirdId=" + $(this).attr('thirdId') + "&t=" + new Date().valueOf();
});

$(document).on('click', '#searchBtn', function (e) {
    searchEvent.init();
});
//推荐类别绑定事件
$("a[id^=tjsecondId]").bind('click', function () {
    var id = $(this).attr('selfId');
    var firstId = $(this).attr('firstId');
    location.href = ctx + '/shop/search/category/childCategoryView?firstId=' + firstId + '&secondId=' + id + '&thirdId=' + '' + "&t=" + new Date().valueOf();
});

$(function () {
    $("#searchName").keydown(function (event) {
        event = document.all ? window.event : event;
        if ((event.keyCode || event.which) == 13) {
            event.stopPropagation();
            searchEvent.init();
        }
    });
});

