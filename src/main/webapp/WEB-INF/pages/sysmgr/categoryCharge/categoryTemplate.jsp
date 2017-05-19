<script id="firstLevel" type="text/html">
<ul class="level level1 form-group checkbox on">
	<!for(var i = 0, length = list.length; i < length; i++) {
			var item = list[i];
	!>
		<!if(!i) {!>
			<li class="on">
		<!} else {!>
			<li>
		<!}!>
			<label id="<!=item.id!>" class="first-level">
				<input type="checkbox"><i></i>
			</label>
			<span><b class="cateName"><!=item.cateName!></b></span>
		</li>
	<!}!>
</ul>
</script>

<script id="secondLevel" type="text/html">
<!
	for(var i = 0, length = list.length; i < length; i++) {
		var item = list[i], childs = item.childs, m, lengthM;
!>
		<!
			if(firstLevelId != item.parentId) {
		!>
				<li class="hide">
		<!
			} else {
		!>
				<li>
		<!
			}
		!>
			<label class="level-details <!=item.parentId!>" id="<!=item.id!>">
				<dt>
					<a class="second-level"><input type="checkbox"><i></i><span class="cateName"><!=item.cateName!></span></a>
				</dt>
				<!
					if(childs && (lengthM = childs.length)) {
				!>
					<dd>
				<!
					for(m = 0; m < lengthM; m++) {
						var child = childs[m];
				!>
						<a class="third-level" id="<!=child.id!>"><!=child.cateName!></a>
				<!			
					}
				!>
					</dd>
				<!
					}
				!>
			</label>
		</li>
<!
	}
!>
</script>

<script id="checkedCategories" type="text/html">
<!
	for(var i = 0, length = categoryList.length; i < length; i++) {
		var category = categoryList[i], cateName = category.cateName,
		secondCategoryList = category.child, secondCategoryLength = (secondCategoryList && secondCategoryList.length) || 0,
		m = 0, firstLevelId = category.id;
!>
		<div class="category-list padding-left-20 padding-bottom-10">
			<div class="category-fst margin-top-20 checked-div" data-id="<!=firstLevelId!>"><!=cateName!></div>
	<!
		for(; m < secondCategoryLength; m++) {
			var secondCategory = secondCategoryList[m], secondLevelId = secondCategory.id,
			secondCateName = secondCategory.cateName;
	!>
        	<div class="category-sec margin-top-10 checked-div" data-id="<!=secondLevelId!>"><!=secondCateName!></div>
	<!
		}
	!>
	</div>
<!
	}
!>
</script>