<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script id="tempHtmlImageInit" type="text/html">
	<!for(var i=0 ;i<images.length;i++){
	var item = images[i];
	var style = '';
	if((i+1)%4 == 0 && i !=0){
		style= 'style=margin-right:0';
	}
	!>
	<i <!=style!>><img src="<!=item.path!>" id="<!=item.id!>" width="272px" height="<!=height!>" ></i>
	<!}!>
</script>