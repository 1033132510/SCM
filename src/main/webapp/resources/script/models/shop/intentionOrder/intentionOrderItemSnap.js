var id = $('#id').val();
$.ajax({
	type : 'GET',
	url : ctx + '/shop/intentionOrder/productSnap/' + id,
	dataType : 'json',
	success : function(result) {
		var data = result.snap;
		if($.type(data) == 'string') {
			data = $.parseJSON(data);
		}
		data.price = result.price;
		data.quantity = result.quantity;
		commonRender.rend(data, false);
	},
    error: function () {
        zcal({
            type: "error",
            title: "加载数据失败"
        });
    }
});

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
