var searchbt = baidu.template;
var searchdivId = 'searchInfoTemplateDiv';
var categoryId = '';
var searchname = '';
var sBrandName = '';
var itemKey = [];
var brandId = '';
var beginPrice;
var endPrice;
var searchInfo = {
    initSearchShow: function (flag) {
        // 通过类别初始化条件
        var that = this;
        var brandList = [];
        brandList = that.getBrandList();
        var itemInfoList = [];
        if (categoryId) {
            itemInfoList = that.getItemInfo();
        }

        var categoryList = [];
        if ($.trim(searchname).length > 0 || $.trim(sBrandName).length > 0) {
            categoryList = that.getCategoryListByNameOrBrandName();
        }
        if (!flag) {
            var item = {
                categoryList: categoryList
            };
            that.showSearchCategory(item);
        }


        var searchObject = {
            brandList: brandList,
            itemInfoList: itemInfoList
        };

        that.showSearchInfo(searchObject);
        that.showMore();
        that.showResult();
    },
    getBrandList: function () {
        // 根据类别、品牌获取品牌
        var brandList = [];
        var url = "/shop/brand/searchBrandInfoByCateAndProductName";
        if (sBrandName) {
            url = "/shop/brand/searchBrandInfoByBrandName";
        }
        $.ajax({
            url: ctx + url,
            type: 'get',
            data: {
                cateId: categoryId,
                productName: searchname,
                brandName: sBrandName,
                t: new Date().valueOf()
            },
            async: false,
            dataType: 'text',
            success: function (data) {
                if (data == 'fail') {
                    zcal({
                        type: "error",
                        title: "加载数据失败"
                    });
                    return;
                } else {
                    if ('' == data) {
                    } else {
                        brandList = JSON.parse(data);
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                zcal({
                    type: "error",
                    title: "加载数据失败"
                });
            }
        });
        return brandList;
    },
    showSearchInfo: function (searchObject) {
        // 显示条件 模板
        var shh = $('#searchInfoTemplateDiv');
        shh.html('');
        var html = searchbt('tempHtmlSearchInfoInit', searchObject);
        shh.append($(html));
    },
    showSearchCategory: function (searchObject) {
        var shh = $('#searchCategoryByNameDiv');
        shh.html('');
        var tt = searchbt('tempHtmlSearchCategoryInit', searchObject);
        shh.append($(tt));
    },
    // 根据类别获取属性
    getItemInfo: function () {
        var itemList = [];
        $
            .ajax({
                url: ctx
                + '/shop/category/getCategoryInfoForItemSearch',
                type: 'get',
                data: {
                    cateId: categoryId,
                    t: new Date().valueOf()
                },
                async: false,
                success: function (data) {
                    if (data == 'fail') {
                        zcal({
                            type: "error",
                            title: "加载数据失败"
                        });
                        return;
                    } else {
                        if (data && data.items) {
                            itemList = data.items;
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    zcal({
                        type: "error",
                        title: "加载数据失败"
                    });
                }
            });
        return itemList;
    },
    getCategoryListByNameOrBrandName: function () {
        // 根据名称获取类别
        var categoryList = [];
        var url = '/shop/category/getCateByProductName';
        if ($.trim(sBrandName).length > 0) {
            url = '/shop/category/getCateByBrandName';
        }
        $.ajax({
            url: ctx + url,
            type: 'get',
            dataType: 'text',
            data: {
                productName: searchname,
                brandName: sBrandName,
                level: '3',
                t: new Date().valueOf()
            },
            async: false,
            success: function (data) {
                if (data == 'fail') {
                    zcal({
                        type: "error",
                        title: "加载数据失败"
                    });
                    return;
                } else if (data == '') {
                } else {
                    categoryList = JSON.parse(data);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                zcal({
                    type: "error",
                    title: "加载数据失败"
                });
            }
        });
        return categoryList;
    },
    initProductShow:function(productList){
    	$('#productShow').html('');
        if($.trim(productList).length<=0){
			$('#productShow').html('暂时没有数据');
			return;
		}
        if ($.trim(productList).length) {
            productTemplate.showProductTemplate('productShow', productList);
            scrollTop({
                distance: '#productShow',
                time: 500,
                windowScroll: true
            });
        }
    },
 // 获取商品
    getProductList: function () {
        var that = this;
        $.ajax({
            url: ctx + '/shop/product/Search',
            type: 'post',
            data: {
                searchVOMsg: JSON.stringify(getSearchVO()),
                t: new Date().valueOf()
            },
            async: true,
            dataType: 'text',
            success: function (data) {
                if (data == 'fail') {
                    zcal({
                        type: "error",
                        title: "加载数据失败"
                    });
                    return;
                } else {
                    if (data == '') {
                        totalSize = 0;
                        pageUtil.init();
                        that.initProductShow();
                    } else {
                        var redata = JSON.parse(data);
                        if (redata.curPage <= 1) {
                            total = redata.totalRows;
                            totalSize = Math.ceil(redata.totalRows / pageSize);
                            pageUtil.init();
                        }
                        //显示商品
                        that.initProductShow(redata.data);
                    }

                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                zcal({
                    type: "error",
                    title: "加载数据失败"
                });
            }
        });
    },
    setCategoryId: function (cateId) {
        var that = this;
        that.resetCateAndNameSearch();
        that.resetSKUSearch();
        categoryId = cateId;
        setPage();
        that.getProductList();
    },
    setbrandId: function (brnId) {
        var that = this;
        if (brnId) {
            brandId = brnId;
        } else {
            brandId = '';
        }
        setPage();
        that.getProductList();
    },
    setName: function (productname) {
        if (!productname) {
            return;
        }
        var that = this;
        that.resetCateAndNameSearch();
        that.resetSKUSearch();
        searchname = productname;
        setPage();
        that.getProductList();
    },
    setSBrandName: function (searchBrandName) {
        if (!searchBrandName) {
            return;
        }
        var that = this;
        that.resetCateAndNameSearch();
        that.resetSKUSearch();
        sBrandName = searchBrandName;
        setPage();
        that.getProductList();
    },
    setItem: function (item) {
        var flag = true;
        for (var i in itemKey) {
            if (itemKey[i].itemCode == item.itemCode) {
                itemKey[i].value = item.value;
                flag = false;
            }
        }
        if (flag) {
            itemKey.push(item);
        }
        var that = this;
        setPage();
        that.getProductList();
    },
    resetCateAndNameSearch: function () {
        categoryId = '';
        searchname = '';
        sBrandName = '';
    },
    resetSKUSearch: function () {
        itemKey = [];
        brandId = '';
    },
    showMore: function () {
        $('[itemnum]').each(function () {
            var itemNum = parseInt($(this).attr('itemnum')) - 1;
            var itemLength = $(this).find('a').length;
            var itemHide = $(this).find('a:gt(' + itemNum + ')');
            if (itemLength > itemNum) {
                itemHide.addClass('hide');
            }
        });
        $('.max-height').each(function () {
            var listHeight = 50;
            var ddHeight = $(this).find('dd').height();
            if (listHeight > ddHeight) {
                $(this).find('.more').addClass('hide');
            }
        });
    },
    showResult: function () {
        var dlNum = $(".search-wrap .select-list");
        for (var i = 0; i < dlNum.length; i++) {
            $(".select-result").append('<div class="selectedShow"></div>');
        }
    },
    setCategoryIdNoClear: function (cateId) {
        var that = this;
        categoryId = cateId;
        setPage();
        that.getProductList();
    },
    resetItemKey: function (id) {
        var that = this;
        var newItemKey = [];
        for (var i in itemKey) {
            if (id != itemKey[i].itemCode) {
                newItemKey.push(itemKey[i]);
            }
        }
        itemKey = newItemKey;
        setPage();
        that.getProductList();
    }
}

var total;
var pageSize = 16;
var totalSize;// 页数
var currentPage = 1;
// pagenumber 为当前页码
// pagecount 为显示页数
// PageClick 为回调函数
// pageclickednumber 为点击页码
var pageUtil = {
    init: function () {
        $("#pager").pager({
            pagenumber: 1,
            pagecount: totalSize,
            buttonClickCallback: PageClick
        });
    }
}
PageClick = function (pageclickednumber) {
    $("#pager").pager({
        pagenumber: pageclickednumber,
        pagecount: totalSize,
        buttonClickCallback: PageClick
    });
    currentPage = pageclickednumber;
    searchInfo.getProductList();
}
function setPage() {
    currentPage = 1;
    totalSize = 10;
    total = 0;
}

function con() {
    console.log(pageSize + ',' + currentPage);
    console.log("categoryId=" + categoryId);
    console.log("searchname=" + searchname);
    console.log("itemKey");
    console.log(itemKey);
    console.log("brandId=" + brandId);
}
function getSearchVO() {
    var searchvo = {
        cateId: categoryId,
        productName: searchname,
        brandId: brandId,
        // beginPrice:beginPrice,
        // endPrice:endPrice,
        pageSize: pageSize,
        curPage: currentPage,
        itemSearchVOs: itemKey,
        brandName: sBrandName
    };
    return searchvo;
}

$(document).on('click', '#brandDiv a', function (e) {
    searchInfo.setbrandId($(this).attr("id"));
});
$(document).on('click', 'a[id$="_item"]', function (e) {
    var item = {
        itemCode: $(this).attr('selfId'),
        value: $(this).html()
    }
    searchInfo.setItem(item);
});
$(document).on('click', '#nameCateDiv a', function (e) {
	if($(this).hasClass('selected')){
		return;
	}
    searchInfo.setCategoryIdNoClear($(this).attr('selfId'));
    searchInfo.initSearchShow(true);
    $(this).addClass("selected").siblings('a').removeClass('selected');
    
    //切换大菜单
    if($('#firstCategoryId').attr('firstId') == $(this).attr('firstId')){
    	
    }else{
    	category.init($(this).attr('firstId'));
    }
    setFight($(this).attr("selfId"));
});
// 点击展开
$(document).on('click', '.search-wrap .more', function (e) {
    e.preventDefault();
    var selectList = $(this).closest('.select-list');
    selectList.closest('.select-list').toggleClass('on');
    if (selectList.hasClass('max-height')) {
        return false;
    } else {
        var itemNum = selectList.find('dd').attr('itemnum') - 1;
        var itemLength = selectList.find('dd a').length;
        var itemHide = selectList.find('dd a:gt(' + itemNum + ')');
        if (itemLength > itemNum) {
            itemHide.toggleClass('hide');
        }
    }
});
$(function () {
    // 点击显示条件
    $(document).on('click', '.search-wrap .select-list dd a', function (e) {
        e.preventDefault();
        var selectedShow = $(".selectedShow");
        var listIndex = $(this).closest(".select-list").index();
        var ddSelfId = $(this).attr('selfid');

        var listCondition = $(this).siblings();
        var resultCondition = selectedShow.eq(listIndex).find('a');

        var addHtml = true;
        var changeSelected = false;

        var ddText = $(this).text();
        var itemType = '1'
        if (ddSelfId == 'brand') {
            itemType = '2';
        }
        var ddHtml1 = '<a selfid="' + ddSelfId + '" itemType="' + itemType + '" itemId = "' + this.id + '">' + ddText
            + '<span></span><em>x</em></a>';
        var ddHtml2 = '' + ddText + '<span></span><em>x</em>';
        if($(this).attr('notShow') == 1){
        	ddHtml1 = '';
        	ddHtml2 = '';
        }
        // 判断下面结果是否有重复id
        resultCondition.each(function () {
            var resultConditionSelfId = $(this).attr('selfid');
            if (ddSelfId == resultConditionSelfId) {
                addHtml = false;
                return false;
            } else {
                addHtml = true;
            }
        });

        // 判断当前list是否有重复id
        listCondition.each(function () {
            var listConditionSelfId = listCondition.attr('selfid');
            if (ddSelfId === listConditionSelfId) {
                changeSelected = true;
                return false;
            } else {
                changeSelected = false;
            }
        });
        
        if (addHtml == false && changeSelected == true) {
            $(this).addClass("selected").siblings('a').removeClass(
                'selected');
            selectedShow.eq(listIndex)
                .find('[selfid=' + ddSelfId + ']').html(ddHtml2);
        } else if (addHtml == false && changeSelected == false) {
            return;
        } else {
            $(this).addClass("selected");
            selectedShow.eq(listIndex).append(ddHtml1);
        }
    });
    // 点击移除条件
    $(document).on('click', '.select-result a em', function (e) {
        e.preventDefault();
        var ddSelfId = $(this).closest('a').attr('selfid');
        $('a[selfId='+ddSelfId+']').removeClass('selected');
        var listIndex = $(this).closest(".selectedShow").index();
        var itemType = $(this).closest('a').attr('itemType');
        $(this).closest('a').remove();
        $('.select-list').eq(listIndex).find(
            '[selfId=' + ddSelfId + ']').removeClass('selected');
        if (itemType == '2') {
            searchInfo.setbrandId();
        } else {
            // 搜索商品
            searchInfo.resetItemKey(ddSelfId);
        }

    });
    // 输入条件 价钱
    //$(document).on('click', '.import-price .sure', function (e) {
    //    e.preventDefault();
    //    var index = $(this).closest(".select-list").index();
    //    var valPriceA = $(this).closest('.import-price').find(
    //        '.price1').val();
    //    var valPriceB = $(this).closest('.import-price').find(
    //        '.price2').val();
    //    if (valPriceA != '' && valPriceB != ''
    //        && valPriceB > valPriceA) {
    //        $('.select-list').eq(index).find('.selected')
    //            .removeClass('selected');
    //        $('.selectedShow').eq(index).show().find('span')
    //            .text(valPriceA + '-' + valPriceB + '');
    //    }
    //});
});
