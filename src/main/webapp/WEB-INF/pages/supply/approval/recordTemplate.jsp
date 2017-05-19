<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script id="recordTemplate" type="text/html">
	<div class="type-manage approve">
        <h3 class="head">
            <a>反馈信息</a>
            <a class="away pull-right margin-right-20"><span>收起</span><i></i></a>
        </h3>
        <div class="approve-list padding-bottom-10 margin-top-10">
			<!for(var i=0;i<recordList.length;i++){
				var record = recordList[i];
			!>
            	<div class="row padding-left-20 margin-top-10">
					<!if(record.type == 5 || record.type==6){!>
						<label class="imp imp-noline">提交人：<!=record.approverName!> （<!=record.approverNumber!>） 提交时间：<!=record.createTime!> </label>
					<!}else{!>
						<label class="imp imp-noline">审批人：<!=record.approverName!> （<!=record.approverNumber!>） 审批时间：<!=record.createTime!> </label>
					<!}!>
            	</div>
            	<div class="row padding-left-20">
                	<label class="font-14"><!=record.comment!></label>
            	</div>
			<!}!>
        </div>
     </div>
</script>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script>
var recordTemplate=baidu.template;
function showRecordTemplate(id,data){
	var bdhtml = recordTemplate('recordTemplate',data);
	$("#"+id).append(bdhtml);


	$(".away").click(function(){
		if($(".away span").html() != "展开"){
			$(".away span").html("展开");
			$(".approve-list").hide();
			$(".away i").css("background-position","0 -510");
		}else{
			$(".away span").html("收起");
			$(".approve-list").show();
			$(".away i").css("background-position","0 -466");
		}
	});
}
</script>