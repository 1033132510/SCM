var brandBt = baidu.template;
var brandTemplate = {
		init:function(divId,orgId,isClear){
			var that = this;
			if(!(divId && orgId)){
				return;
			}
			if(isClear){
				$('#'+divId).html('');
			}
			var brandList = that.getBrandList(orgId);
			that.showBrandTemplate(divId,brandList);
		},
		getBrandList:function(orgId){
			var brandList = [];
			$.ajax({
				url : ctx + '/shop/company/getBrandList',
				type : 'post',
				data : {
					orgId:$('#orgId').val(),
					t:new Date().valueOf()
				},
				async : false,
				dataType: 'text',
				success : function(data) {
					if (data == 'fail') {
						zcal({
							type:"error",
							title:"加载数据失败"
						});
						return;
					} else {
						brandList = JSON.parse(data);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					zcal({
						type:"error",
						title:"加载数据失败"
					});
				}
			});
			return brandList;
		},
		showBrandTemplate:function(divId,brandList){
			for ( var index in brandList) {
				var item = brandList[index];
				var hh = $("#"+divId);
				var html = brandBt('tempHtmlBrandInit', item);
				hh.append($(html));
				$("img.lazy").lazyload({
					effect: "fadeIn",
					threshold: 100
				});
			}
		}
};