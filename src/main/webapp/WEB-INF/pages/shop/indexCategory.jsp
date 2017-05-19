<script id="tempHtmlCategoryInit" type="text/html">
<ul class="level level1" id="firstUl">
	<!for(var i=0; i < categorys.length; i++){
		var category = categorys[i];
	!>
		<li>
			<h3><a selfId="<!=category.id!>" firstId="<!=category.id!>" secondId="" thirdId =""><!=category.cateName!></a></h3>
			<div class="level-inner" id="<!=category.id!>_li_div"></div>
		</li>
	<!}!>
</ul>
</script>

<script id="tempHtmlSecondCategoryDDInit" type="text/html">
	<dl class="level-details">
    	<dt><a selfId="<!=id!>" firstId="<!=firstId!>" secondId="<!=id!>" thirdId =""><!=cateName!></a><span>:</span></dt>
		<dd></dd>
    </dl>
</script>

<script id="tempHtmlThirdcategoryDDInit" type="text/html">
   	<a selfId="<!=id!>" firstId="<!=firstId!>" secondId="<!=secondId!>" thirdId ="<!=id!>"><!=cateName!></a>
</script>

