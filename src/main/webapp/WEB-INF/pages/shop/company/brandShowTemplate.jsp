<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script id="tempHtmlBrandInit" type="text/html">
	<div class="row">
		<span class="pp-logo pull-left">
			<!for(var i=0;i<imageList.length;i++){
				if(imageList[i].relationType == brandLOGOType){
					var logoItem = imageList[i];
				!>
					<img src="${ctx}<!=logoItem.path!><!=logoItem.aliasName!>" alt=""/>
				<!}
			}!>
		</span>
		<i class="pp-caption"><!=brand.brandZHName!></i>
	</div>
	<!for(var i=0;i<imageList.length;i++){
		if(imageList[i].relationType == brandDescType){
			var descItem = imageList[i];
	!>
			<img src="${ctx}<!=descItem.path!><!=descItem.aliasName!>" alt=""/>
		<!}
	}!>
	<p>
		<!:=brand.brandDesc!>
	</p>
</script>