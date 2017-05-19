/**
 *  shopDecorator.jsp  中 searchBtn检索事件
 */

var searchEvent = {
		init:function(target,productSearch,brandSearch){
			if (!$.trim($('#searchName').val())) {
				 return;
			}
		    var seaType = 0;
		    var keyword = shopUtils.strUnhtml($.trim($('#searchName').val()).replace(/\s+/g,' '));
		    if(searchSelect){
		    	seaType = 1;
		    }
		    if(target){
		        window.open( ctx + '/shop/search/category/childCategoryView?name=' + escape(keyword)+ "&seaType=" + seaType + "&t=" + new Date().valueOf());
		    }else{
		    	location.href = ctx + '/shop/search/category/childCategoryView?name=' + escape(keyword) + "&seaType=" + seaType + "&t=" + new Date().valueOf();
		    }
		}
};

var shopUtils={
		unhtml:function (str, reg) {
		    return str ? str.replace(reg || /[&<">'](?:(amp|lt|quot|gt|#39|nbsp|#\d+);)?/g, function (a, b) {
		        if (b) {
		            return a;
		        } else {
		            return {
		                '<':'&lt;',
		                '&':'&amp;',
		                '"':'&quot;',
		                '>':'&gt;',
		                "'":'&#39;'
		            }[a]
		        }

		    }) : '';	
		},
		html:function (str) {
		    return str ? str.replace(/&((g|l|quo)t|amp|#39|nbsp);/g, function (m) {
		        return {
		            '&lt;':'<',
		            '&amp;':'&',
		            '&quot;':'"',
		            '&gt;':'>',
		            '&#39;':"'",
		            '&nbsp;':' '
		        }[m]
		    }) : '';
		},
		strUnhtml:function(str){
			var resStr = '';
			var that = this;
			for(var i=0;i<str.length;i++){
				resStr += that.unhtml(str.substring(i,i+1));
		    }
			return resStr;
		}
}
