var drawerNav = {
	init:function(cateId){
		$('#firstCateShow,#secondCateShow,#thirdCateShow').html('');
		if(!cateId){
			return;
		}
		var that = this;
		that.getCateInfo(cateId);
	},	
	showDrawerNav:function(info){
		if(info.firstCate.id){
			$("#firstCateShow").html(info.firstCate.cateName);
			$("#firstCateShow").attr('selfId',info.firstCate.id);
			$("#firstCateShow").attr('firstId',info.firstCate.id);
			$("#firstCateShow").attr('secondId',"");
			$("#firstCateShow").attr('thirdId',"");
		}

		if(info.secondCate.id){
			$("#secondCateShow").html("&gt;"+info.secondCate.cateName);
			$("#secondCateShow").attr('selfId',info.secondCate.id);
			$("#secondCateShow").attr('firstId',info.firstCate.id);
			$("#secondCateShow").attr('secondId',info.secondCate.id);
			$("#secondCateShow").attr('thirdId',"");
		}
		if(info.thirdCate.id){
			$("#thirdCateShow").html("&gt;"+info.thirdCate.cateName);
			$("#thirdCateShow").attr('selfId',info.thirdCate.id);
			$("#thirdCateShow").attr('firstId',info.firstCate.id);
			$("#thirdCateShow").attr('secondId',info.secondCate.id);
			$("#thirdCateShow").attr('thirdId',info.thirdCate.id);
		}
	},
	getCateInfo:function(cateId){
		if(!cateId){
			return;
		}
		var info = [];
		var that = this;
		$.ajax({
			url : ctx + '/shop/search/category/Info',
			type : 'get',
			data : {
				cateId : cateId,
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
					info =  JSON.parse(data);
					that.showDrawerNav(info);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				zcal({
					type : "error",
					title : "加载数据失败"
				});
			}
		});
	}
}
//抽屉点击事件
$(document).on('click', '#firstCateShow,#secondCateShow,#thirdCateShow', function (e) {
//	location.href = ctx + '/shop/search/category/childCategoryView?firstId='+$(this).attr('firstId')+'&secondId='+$(this).attr('secondId')+"&thirdId="+$(this).attr('thirdId')+"&t="+new Date().valueOf();
	setFight($(this).attr("selfId"));
	drawerNav.init($(this).attr('selfId'));
	category.selectInitMethod($(this).attr('firstId'),$(this).attr('secondId'),$(this).attr('thirdId'));
});

$(document).on('click', '[firstId]', function (e) {
	//设置抽屉
    drawerNav.init($(this).attr("selfId"));
});

$(document).on('click', '#searchBtn', function (e) {
	//通过名字查找
	 drawerNav.init();
});

$(function(){
	 $("#searchName").keydown(function(event){
      event = document.all ? window.event : event;
      if((event.keyCode || event.which) == 13) {
      		event.stopPropagation();
      		drawerNav.init();
      }
   }); 
});