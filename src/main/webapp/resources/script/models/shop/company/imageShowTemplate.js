var imageBt = baidu.template;
var imageTemplate={
		init:function(divId,relationId,relationType,isClear,width,height){
			var that = this;
			if(!(divId && relationId && relationType)){
				return;
			}
			if(isClear){
				$('#'+divId).html('');
			}
			var imageList = that.getImageList(relationId,relationType); 
			that.showImageTemplate(divId,imageList,width,height);
		},
		getImageList:function(relationId,relationType){
			var imageList = [];
			$.ajax({
				url : ctx + '/imageshow/getImages',
				type : 'get',
				data : {
					relationId:relationId,
					relationType:relationType,
					t:new Date().valueOf()
				},
				async : false,
				success : function(data) {
					if (data == 'fail') {
						zcal({
							type:"error",
							title:"加载数据失败"
						});
						return;
					} else {
						imageList = data;
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					zcal({
						type:"error",
						title:"加载数据失败"
					});
				}
			});
			return  imageList;
		},
		showImageTemplate:function(divId,imageList,width,height){
			var hh = $("#"+divId);
			var item ={
				images:imageList,
				width:width,
				height:height
			}
			var html = imageBt('tempHtmlImageInit',item);
			hh.append($(html));
		}
}
