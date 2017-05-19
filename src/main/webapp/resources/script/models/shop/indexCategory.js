var categoryULTemplate = baidu.template;
var categoryUl ={
		init:function(divId,isShow){
			if(!divId){
				return;
			}
			var that = this;
			that.getCategoryList(1,1,divId,isShow);
		},
		getCategoryList : function( status, parentCategoryId,divId,isShow) {
			// 获取某一级类别的孩子类别
			var that = this;
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
						that.showTemplateCategory(divId,data,isShow);
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
		showTemplateCategory : function(divId, categoryList,isShow) {
			var hh = $("#" + divId);
			var item = {
				categorys : categoryList
			}
			var categoryHtml = categoryULTemplate('tempHtmlCategoryInit', item);
			hh.append($(categoryHtml));
			if(isShow){
				$('#firstUl').addClass('on'); 
			}
		},
		getChildCateByFCateId : function(firstcateId,divId) {
			//获取一级类别List,加载到页面的隐藏ul中
			var that = this;
			if (!firstcateId) {
				zcal({
					type : "error",
					title : "请重新选择类别"
				});
				return;
			}
			if("undefined" == typeof(childListObject)){
					
			}else{
				if(childListObject.length>0){
					var flag = true;
					for(var i=0;i<childListObject.length;i++){
						if(childListObject[i].key == firstcateId){
							categoryUl.showTemplateChildCategory(firstcateId,childListObject[i].value,divId);
							return;
						}
					}
				}
			}
			$.ajax({
				url : ctx + '/shop/category/cateDecide',
				type : 'get',
				dataType:'text',
				data : {
					categoryId : firstcateId,
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
						if(data == ''){
							
						}else{
							if("undefined" == typeof(childListObject)){
								
							}else{
								var childCateMap = {
										key : firstcateId,
										value :JSON.parse(data)
								};
								childListObject.push(childCateMap);
							}
							categoryUl.showTemplateChildCategory(firstcateId,JSON.parse(data),divId);
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
		showTemplateChildCategory : function(firstcateId, data, divId) {
			var $zhh = $("#" + divId);
			$zhh.html('');
			var count = 0;
			var html ='';
			for ( var i in data) {
				var map = JSON.parse(i);
				var cateItem = map;
				cateItem.firstId = firstcateId;
				var categoryHtml = categoryULTemplate('tempHtmlSecondCategoryDDInit',cateItem);
				$zhh.append($(categoryHtml));
				var c = data[i];
				for ( var j in c) {
					var item = c[j];
					item.firstId = firstcateId;
					item.secondId = map.id;
					html += categoryULTemplate('tempHtmlThirdcategoryDDInit', item);
				}
				$zhh.find('dl:eq('+(count++)+') dd').append($(html));
				html = '';
			}
		}
};

//菜单绑定加载子类别元素
$(document).on('mouseenter', '#firstUl li', function (e) {
	var id = $(this).find('a').attr('selfId');
	var $g =  $(this).find('div');
	if ($.trim($g.html()).length > 0) {
		return;
	}
	categoryUl.getChildCateByFCateId(id,$g.attr('id'));
});
