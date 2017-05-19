<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script id="tempHtmlSearchInfoInit" type="text/html">
 <ul class="search-wrap" id="searchInfoDiv">
	<!if(brandList.length>0){!>
    <li class="select-list max-height">
        <dl>
            <dt>品牌：</dt>
 			<dd id="brandDiv">
			<!for(var i=0;i<brandList.length;i++){
				var item = brandList[i];
				var enName = '';
				if( item.brandENName){
					enName = "("+item.brandENName+")";
				}
			!>
           		<a  id="<!=item.brandId!>" selfId ="brand" ><!=item.brandZHName!><!=enName!></a>
			<!}!>
            </dd>
        </dl>
        <a class="more">
            <span><em>更多</em><em>收起</em></span>
            <i><s class="s1"></s><s class="s2"></s></i>
        </a>
    </li>
	<!}!>
	<!for(var i=0;i<itemInfoList.length;i++){
		var item = itemInfoList[i];
		var info = $.trim(item.itemsSources).split(",");
		var showNumber  = item.defaultShowNumber;
		if(info.length>0 && info[0].length<=0){
			info = [];
		}
	!>
		<li class="select-list"  >
        	<dl>
				<dt id="<!=item.itemCode!>"><!=item.itemName!>：</dt>
            	<dd class="select-all" itemNum ="<!=showNumber!>">
				<!for(var j=0;j<info.length;j++){
					var infovalue = info[j];
          		!>
            		<a id="<!=item.itemCode!>_<!=infovalue!>_item" selfId="<!=item.itemCode!>"><!=infovalue!></a>
            	<!}!>
				</dd>
        	</dl>
            <!if(info.length > showNumber) {!>
        		<a class="more">
                    <span><em>更多</em><em>收起</em></span>
                    <i><s class="s1"></s><s class="s2"></s></i>
                </a>
            <!}!>
    	</li>
	<!}!>
    <li class="select-result">

    </li>
</ul>
</script>
<script id="tempHtmlSearchCategoryInit" type="text/html">

<!if(categoryList.length>0){!>
	 <ul class="search-wrap search-category" id="searchCatedgoryDiv">
    <li class="select-list max-height">
        <dl>
            <dt>类别：</dt>
            <dd id="nameCateDiv">
				<a id="qbId" selfId="" notShow="1">全部</a>
				<!for(var i=0;i<categoryList.length;i++){
					var item = categoryList[i].brandCategoryView;
					var name = categoryList[i].cateName;
				!>
					<a  id="<!=item.cateId!>" selfId="<!=item.cateId!>" firstId="<!=item.cateIdGrand!>" secondId="<!=item.cateIdParent!>" thirdId=<!=item.cateId!> notShow="1"><!=name!></a>
				<!}!>

            </dd>
        </dl>
        <a class="more">
            <span><em>更多</em><em>收起</em></span>
            <i><s class="s1"></s><s class="s2"></s></i>
        </a>
    </li>
	<!}!>
	 </ul>
</script> 
	 