var firstCategoryId;  //传入的一级类别
var firstItem = []; //如果无传入一级类别，则默认第一个对象
var secondCategoryId;
var thirdCategoryId;
var categoryBt = baidu.template;
var childCategoryDiv = 'childCategoryDiv';
var firstCateList = []; //一级类别
var childListObject = [];
var category = {
	// 初始化类别下拉菜单
	init : function(firstId,secondId,thirdId) {
		var that = this;
		// 获取头部 heardDiv
		firstCategoryId = firstId;
		firstItem = [];
		if(firstCateList.length<=0){
			that.getChildCategoryList(1, 1,secondId,thirdId);
		}else{
			that.setFirstCategoryId();
			that.showCategoryHeard('heardDiv', firstItem);
			// 获取一级类别的孩子类别
			var flag = true;
			for(var i=0;i<childListObject.length;i++){
				if(childListObject[i].key == firstCategoryId){
					that.showChlidDiv(childCategoryDiv,childListObject[i].value,firstCategoryId,secondId,thirdId);
					flag = false;
					break;
				}
			}
			if(flag){
				that.getChildCateListByFCate(firstCategoryId,secondId,thirdId);
			}
		}
	},
	setFirstCategoryId:function(){
		for (var i = 0; i < firstCateList.length; i++) {
			if (firstCateList[i].id == firstCategoryId) {
				//获取选择类别的一级类别对象
				firstItem = firstCateList[i];
				firstCategoryId = firstCategoryId;
			}
		}
		if(firstItem.length<=0 && firstCateList.length > 0){
			//默认取第一个分类
			firstItem = firstCateList[0];
			firstCategoryId = firstCateList[0].id;
		}
	},
	showCategoryHeard : function(divId, item) {
		var shh = $("#" + divId);
		shh.html('');
		var html = categoryBt('tempHtmlCategoryHeardInit', item);
		shh.append($(html));
	},
	getChildCategoryList : function(status, parentCategoryId,secondId,thirdId) {
		// 获取一级类别
		var  that = this;
		$.ajax({
			url : ctx + '/shop/category/cateShow',
			type : 'post',
			data : {
				status : status,
				parentCategoryId : parentCategoryId,
				t : new Date().valueOf()
			},
			async : true,
			success : function(data) {
				if (data == 'fail') {
					zcal({
						type : "error",
						title : "加载数据失败"
					});
					return;
				} else {
					firstCateList = data;
					that.setFirstCategoryId();
					that.showCategoryHeard('heardDiv', firstItem);
					// 获取一级类别的孩子类别
					that.getChildCateListByFCate(firstCategoryId,secondId,thirdId);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				zcal({
					type : "error",
					title : "加载数据失败"
				});
			}
		});
	},
	getChildCateListByFCate : function(firstcateId,secondId,thirdId) {
		//根据第一类别获取子类别，key对象
		var childCategoryList = [];
		var that = this;
		if (!firstcateId) {
			zcal({
				type : "error",
				title : "请重新选择类别"
			});
			return;
		}
		$.ajax({
			url : ctx + '/shop/category/cateDecide',
			type : 'get',
			data : {
				categoryId : firstcateId,
				t : new Date().valueOf()
			},
			async : true,
			dataType:'text',
			success : function(data) {
				if (data == 'fail') {
					zcal({
						type : "error",
						title : "加载数据失败"
					});
					return;
				} else {
					if(data == ''){
						
					}else{
						childCategoryList = JSON.parse(data);
						var childCateMap = {
								key : firstcateId,
								value :childCategoryList
						};
						childListObject.push(childCateMap);
						that.showChlidDiv(childCategoryDiv, childCategoryList,firstcateId,secondId,thirdId);
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				zcal({
					type : "error",
					title : "加载数据失败"
				});
			}
		});
	},
	showChlidDiv : function(divId, data, firstId,secondId,thirdId) {
		//显示一级类别下得子类别 DIV
		var $hhh = $("#" + divId);
		$hhh.html('');
		var count = 0;
		var tcategoryHtml = '';
		for ( var i in data) {
			var map = JSON.parse(i);// 二级类别
			var cateItem = map;
			cateItem.firstId = firstId;
			var scategoryHtml = categoryBt('tempHtmlSecondcategoryDivInit',cateItem);
			$hhh.append(scategoryHtml);
			var c = data[i];
			for ( var j in c) {
				var item = c[j];
				item.firstId = firstId;
				item.secondId = map.id;
				tcategoryHtml += categoryBt('tempHtmlThirdcategoryDivInit', item);
			}
			$hhh.find('div:eq('+(count++)+')').append($(tcategoryHtml));
			tcategoryHtml = '';
		}
		setFight(secondId,thirdId);
	},
	getSearchInfoByCategoryId : function(categoryId) {
		//搜索商品 必须先执行
		searchInfo.setCategoryId(categoryId);
		//搜索属性
		searchInfo.initSearchShow();
	},
	getSearchInfoByName : function(sname) {
		//搜索商品 必须先执行
		searchInfo.setName(sname);
		//搜索属性
		searchInfo.initSearchShow();
	},
	getSearchInfoByBrandName:function(bname){
		//搜索商品 必须先执行
		searchInfo.setSBrandName(bname);
		//搜索属性
		searchInfo.initSearchShow();
	},
	selectInitMethod:function(firstId,secondId,thirdId,initName){
		//初始化 选择如何获得搜索条件
		var that = this;
		if(initName && !searchSelect){
			drawerNav.init();
			that.getSearchInfoByName(initName);
			return;
		}
		if(initName && searchSelect){
			drawerNav.init();
			that.getSearchInfoByBrandName(initName);
			return;
		}
		if(thirdId){
			drawerNav.init(thirdId);
			that.getSearchInfoByCategoryId(thirdId);
			return;
		}
		if(secondId){
			drawerNav.init(secondId);
			that.getSearchInfoByCategoryId(secondId);
			return;
		}
		if(firstId){
			drawerNav.init(firstId);
			that.getSearchInfoByCategoryId(firstId);
			return;
		}
	}
};

$(document).on('click', 'a[id$="_div"],h2[id$="_div"],#firstCategoryId', function (e) {
	var id = $(this).attr('selfId');
	category.getSearchInfoByCategoryId(id);
});

