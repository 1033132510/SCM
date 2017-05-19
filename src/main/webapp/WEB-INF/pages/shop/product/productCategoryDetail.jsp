<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div class="main-content classify">
    <div class="categorys">
        <div class="level-nav" id="heardDiv"></div>
        <div class="level-content" id="indexCategoryDiv">
            <div class="level level2" id="childCategoryDiv"></div>
        </div>
    </div>
    <div class="classify-wrapper">
        <div class="bread">
            <a id="firstCateShow"></a>
            <a id="secondCateShow"></a>
            <a id="thirdCateShow"></a>
        </div>
        <div id="searchCategoryByNameDiv"></div>
        <div id="searchInfoTemplateDiv"></div>
        <!-- 
            <div class="class-search">
                <span>
                    共检索10000个商品
                </span>
                <input type="text"/>
                <a>检索</a>
            </div>
         -->
        <div id="productShow"></div>
        <div class="pagination text-center" id="pager"></div>
        <div id='result'></div>
    </div>
</div>
<%@ include file="/WEB-INF/pages/shop/indexCategory.jsp" %>
<%@ include file="/WEB-INF/pages/shop/product/categoryTemplate.jsp" %>
<%@ include file="/WEB-INF/pages/shop/product/sarchInfoTemplate.jsp" %>
<%@ include file="/WEB-INF/pages/shop/product/productShowTemplate.jsp" %>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script src="${ctx}/resources/script/models/shop/indexCategory.js?t=" +new Date().valueOf()></script>
<script src="${ctx}/resources/script/models/shop/CommonShop.js"></script>
<script src="${ctx}/resources/script/models/shop/product/drawerNav.js?t=" +new Date().valueOf()></script>
<script src="${ctx}/resources/script/models/shop/product/shopStyleOperate.js?t=" +new Date().valueOf()></script>
<script src="${ctx}/resources/script/models/shop/product/categoryTemplate.js?t=" +new Date().valueOf()></script>
<script src="${ctx}/resources/script/models/shop/product/searchInfoTemplate.js?t=" +new Date().valueOf()></script>
<script src="${ctx}/resources/script/models/shop/product/productShowTemplate.js?t=" +new Date().valueOf()></script>
<script src="${ctx}/resources/script/plugins/jqueryPager/jquery.pager.js"></script>
<script>
    $(function () {
    	//初始化UL类别
        categoryUl.init('indexCategoryDiv');
        $('.level2').addClass('on');
        //初始化DIV类别
        category.init('${firstId}','${secondId}', '${thirdId}');
        //初始化搜索条件
        category.selectInitMethod(firstCategoryId, '${secondId}', '${thirdId}',  $.trim($('#searchName').val()));
    });
    
    //隐藏UL的绑定事件
    $(document).on('click', '#firstUl li a', function (e) {
        e.preventDefault();
        //如果不相同在请求显示Div
        if ($('#firstCategoryId').attr('selfId') != $(this).attr("firstId")) {
            category.init($(this).attr("firstId"));
        }
        // 查找条件
        category.getSearchInfoByCategoryId($(this).attr("selfId"));
    });
    
    $(document).on('click', '#searchBtn', function (e) {
    	//通过名字查找
    	if(searchSelect){
    		category.getSearchInfoByBrandName($.trim($('#searchName').val()));
    	}else{
    		category.getSearchInfoByName($.trim($('#searchName').val()));
    	}
    });
    
  //推荐类别绑定事件
    $("a[id^=tjsecondId]").bind('click',function(){
    	var id= $(this).attr('selfId');
    	var firstId = $(this).attr('firstId');
 		category.init(firstId,id);
    	category.selectInitMethod(firstId,id);
    });
    $(function(){
   	 $("#searchName").keydown(function(event){
          event = document.all ? window.event : event;
          if((event.keyCode || event.which) == 13) {
          		event.stopPropagation();
          		//通过名字查找
            	if(searchSelect){
            		category.getSearchInfoByBrandName($.trim($('#searchName').val()));
            	}else{
            		category.getSearchInfoByName($.trim($('#searchName').val()));
            	}
          }
       }); 
   });
</script>
