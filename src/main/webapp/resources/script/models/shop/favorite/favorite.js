$(function() {
	
	// 在我的收藏页面执行以下代码
	if(location.href.indexOf('myFavorite') != -1) {
		var $myFavoriteLi = $('#myFavoriteLi');
		$myFavoriteLi.addClass('on');
		$myFavoriteLi.siblings('li').removeClass('on');
		FavoriteObject.countSecondLevelCategoryProductCount();
		FavoriteObject.getFavoritePage(1);
	}
	
});

var FavoriteObject = {
	addFavorite : function(productId, productCategoryId) {
		$.ajax({
			url : ctx + '/shop/favorite/addFavorite',
			data : {productId : productId, productCategoryId : productCategoryId},
			success : function(data) {
				if(data.success) {
					zcal({
						type : 'success',
						title : '提示',
						text : '收藏成功'
					});
				} else {
					zcal({
						type : 'success',
						title : '提示',
						text : data.msg
					});
				}
			}
		});
	},
	// 删除收藏
	deleteFavorite : function() {
		var favoriteId = $(this).attr('data-favorite-id');
		$.ajax({
			url : ctx + '/shop/favorite/deleteFavorite',
			data : {favoriteId : favoriteId},
			success : function(data) {
				if(data == true) {
					zcal({
						type : 'success',
						title : '系统提示',
						text : '取消成功'
					});
					FavoriteObject.getFavoritePage(1);
					FavoriteObject.countSecondLevelCategoryProductCount();
				}
			}
		});
	},
	getFavoritePage : function(curPage, param) {
		param = param || {};
		param.curPage = curPage;
		param.pageSize = PAGE_SIZE;
		$.ajax({
			url : ctx + '/shop/favorite/getMyFavoritePage',
			data : param,
			success : function(data) {
				productTemplate.showProductTemplate('favoriteContainer', data.data, {showCancelFavorite : true});
				var totalCount = data.totalRows || 0, totalPage = Math.ceil(totalCount / PAGE_SIZE), $pager = $('#pager'),
				$noFavoriteDiv = $('#noFavoriteDiv');
				if(totalCount) {
					var PageClick = function(curPage) {
						FavoriteObject.getFavoritePage(curPage, param);
						$pager.pager({pagenumber : curPage, pagecount : totalPage, buttonClickCallback : PageClick});
					}, opt = {
							pagenumber : curPage,
							pagecount : totalPage,
							buttonClickCallback: PageClick
					};
					$pager.pager(opt);
					$noFavoriteDiv.addClass('hide');
				} else {
					$noFavoriteDiv.removeClass('hide');
					$pager.addClass('hide');
				}
				$('.cancelFavorite .cancelFavorite').unbind().bind('click', FavoriteObject.deleteFavorite);
				$('.cancelFavorite .showProductDetail').unbind().bind('click', FavoriteObject.showProductDetail);
			}
		});
	},
	getFavoritePageByCategoryId : function() {
		var $this = $(this);
		param = {
			secondLevelCategoryId : $this.attr('data-id')
		};
		FavoriteObject.getFavoritePage(1, param);
	},
	// 统计二级商品品类商品数量
	countSecondLevelCategoryProductCount : function() {
		$.ajax({
			url : ctx + '/shop/search/category/getProductCount',
			success : function(data) {
				var html = '', length,
				i = 0, $secondLevelCategoryUl = $('#secondLevelCategoryUl'),
				list;
				if(data && (list = data.resultData) && (length = list.length)) {
					for(; i < length; i++) {
						var obj = list[i];
						html += '<li data-id="' + obj.categoryId + '">'
								+ '<a>' + obj.categoryName + '（' + obj.productCount + '）</a>'
							+ '</li>';
					}
				}
				$secondLevelCategoryUl.html(html);
				$secondLevelCategoryUl.find('li').bind('click', FavoriteObject.getFavoritePageByCategoryId);
			}
		});
	},
	showProductDetail : function() {
		var $this = $(this), productId = $this.attr('data-product-id'),
		cateId = $this.attr('data-category-id');
		location.href = ctx + '/shop/product/gotoView?productId=' + productId + '&cateId=' + cateId;
	}
}, PAGE_SIZE = 8,
param = {};

$(document).on('click', '#searchBtn', function (e) {
	searchEvent.init(true);
});
$(function(){
	 $("#searchName").keydown(function(event){
      event = document.all ? window.event : event;
      if((event.keyCode || event.which) == 13) {
      		event.stopPropagation();
      		searchEvent.init(true);
      }
   }); 
});