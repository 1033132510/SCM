$(document).on('click', '#firstUl li a,#sortShow', function (e) {
	shopSwitch();
});
$(document).on('click', '#firstCategoryId,#firstUl li a,a[id$="_div"],h2[id$="_div"]', function (e) {
	//设置样式
   	setFight($(this).attr("selfId"));
});

//设置显示样式
function setFight(secId,thiId){
	var id = '';
	if(thiId){
		id = thiId;
	}else if(secId){
		id = secId;
	}else{
		return;
	}
	// 在h2 a下面的id值设为“id_div”
	var $lightDom =$("[id="+id+"_div]");
	$('#childCategoryDiv').find('a,h2').removeClass('nav-light');
	$lightDom.addClass('nav-light').siblings('a').removeClass('nav-light');
	$lightDom.closest('.classify-content').find('h2').addClass('nav-light');
//	$lightDom.closest('.classify-content').siblings('.classify-content').find('h2,a').removeClass('nav-light');
}

function shopSwitch() {//切换菜单
    if ($('.level2').hasClass('on')) {
        $('.level2').removeClass('on').siblings('.level1').addClass('on');
    } else {
        $('.level1').removeClass('on').siblings('.level2').addClass('on');
    }
}