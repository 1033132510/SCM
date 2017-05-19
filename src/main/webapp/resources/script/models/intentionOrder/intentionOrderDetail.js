var RMB = '￥';
$(function () {
    var gridObj = utils.initGrid('orderItems', 'sysmgr/intentionOrder/detail', {orderId : $('#id').val()});
    
    $('#name').autocomplete({
 	   autoFocus: true,
 	   delay: 300,
 	   minLength: 1,
 	   source: function(request, response) {
 		   $.ajax({
 			   url : ctx + '/sysmgr/employee/orderTaker',
 			   type : 'POST',
 			   data : {employeeName : request.term},
 			   dataType : 'json',
 			   success : function(result) {
 				   if(result && result.success) {
 					  response($.map(result.resultData, function(item, index){
 						   return {label: item.employeeName, value: item.employeeName, mobile : item.mobile, id: item.id};
 					   })); 
 				   }
 			   }
 		   });
 	   },
 	   select: function( event, ui ) {
 		   $('#employeeId').val(ui.item.id);
 		   $('#employeeName').val(ui.item.value);
 	   }
 }).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		   return $( "<li>" )
		     .attr( "data-value", item.value )
		     .append( item.label + '+手机号：' + item.mobile )
		     .appendTo( ul );
		};
		
		// 确认分配
		$('#confirmAllotOrder').click(function(e) {
			e.preventDefault();
			var name = $('#name').val();
			var employeeId = $('#employeeId').val();
			var id = $('#id').val();
			var employeeName = $('#employeeName').val();
			if(name == '' || employeeId == '') {
				zcal({
			        type: 'error',
			        text: '请选择接单人姓名'
			    });
				return false;
			}
			if(name != employeeName) {
				zcal({
			        type: 'error',
			        text: '请选择接单人姓名'
			    });
				return false;
			}
			
			$.ajax({
				url : ctx + '/sysmgr/intentionOrder/allot',
				type : 'POST',
				data : {id : id, employeeId : employeeId, employeeName : employeeName},
				dataType : 'json',
				success : function(result) {
					zcal({
	   			        type: 'success',
	   			        text: '派单成功'
	   			    });
					// 清空modal并关闭
					$('#employeeId').val('');
					$('#employeeName').val('');
					$('#name').val('');
					modalClose('#allotOrderModal');
					
					// 显示派单时间和信息 
					$('#allotTime').html(result.allotTime);
					var allotInfo = '运营总监<i class="red">'+ result.cooName +'</i>分配<i class="red">'+ result.employee.employeeName +'</i>';
					$('#allotInfo').html(allotInfo);
					
					// 改变意向单状态
					$('#orderStatusText').html('已接单');
					//隐藏派单按钮
					$('#allotOrder').addClass('hide');
					
					
				},
				error : function() {
					zcal({
	   			        type: 'error',
	   			        text: '服务器异常'
	   			    });
				}
				
			});
		});
		
		// 关闭modal
		$('#closeModal').click(function() {
				$('#employeeId').val('');
				$('#employeeName').val('');
				$('#name').val('');
		});
		
});

function formatterNameAndImage(record, rowIndex, colIndex, tdObj, trObj, options) {
    return '<div class="cart-name"><img src="' + record.productImage + '" alt="" align="middle" />' + '<span>' + record.productName + '</span></div>';
}

function formatterPrice(record, rowIndex, colIndex, tdObj, trObj, options) {
	var hasTax = '含税';
	var hasTransportation = '含运费';
	if (!record.hasTax) {
		hasTax = '不含税';
	}
	if (!record.hasTransportation) {
		hasTransportation = '不含运费';
	}
	var text = '<p>' + hasTax + '&nbsp;&nbsp;' + hasTransportation + '</p>';
	return RMB + utils.toFixed(record.price) + text;
}

function formatterItemTotalPrice(record, rowIndex, colIndex, tdObj, trObj, options) {
	return RMB + utils.toFixed(record.itemTotalPrice);
}