<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<script id="examineReadOnlyTemplate" type="text/html">
    <div class="type-manage blurb">
		<form id="recordForm" name="recordForm">
        <h3 class="head">
            <a>审核信息</a>
        </h3>
        <div class="form-group media short cost-news pull-left margin-top-20 margin-left-20 margin-bottom-20 padding-left-40">
            <label class="media-label"><i class="imp">*</i>标价:</label>
            <input type="text" class="form-control valid cost no-cursor" id="standard" value="<!=standard!>" disabled="disabled" name="standard" readonly="readonly" />
            <label class="pull-right">元/<!=unit!></label>
        </div>
        <div class="form-group margin-top-20 margin-left-20 checkbox pull-left">
            <!if(shasTax != 1){!>
				<label class="pull-left no-cursor">
					<span class="no-cursor">不含税</span>
				</label>
			<!}else{!>	
				<label class="checked pull-left no-cursor">
					<span>含税</span>
				</label>
			<!}!>
            <!if(shasTransportation != 1){!>
				<label class="margin-left-20 pull-left no-cursor">
					<span>不含运费</span>
				</label>
			<!}else{!>	
				<label class="checked margin-left-20 pull-left no-cursor">
					<span>含运费</span>
				</label>
			<!}!>    
            <label class="gray-color margin-left-20 no-cursor">＊标价为电商平台采购商看到的价格</span></label>
        </div>
        <div class="row margin-top-20 margin-left-20 margin-bottom-20 clearboth">
            <div class="blurb-cost pull-left padding-left-10">
				<label class="media-label">成本价:<span id="cost"><!=cost!></span>元/<!=unit!></label>
            </div>
            <div class="blurb-cost pull-left margin-left-20 padding-left-40">
                <label class="media-label">建议价格:<!=recommendedPrice!>元/<!=unit!></label>
            </div>
            <div class="blurb-cost pull-left margin-left-20 padding-left-40">
                <label class="media-label">
					<!if(hasTax != 1){!>
						 不含税、
					<!}else{!>
						含税、
					<!}!>
					<!if(hasTransportation != 1){!>
						 不含运费
					<!}else{!>
						含运费
					<!}!>
				</label>
            </div>
        </div>
        <div class="form-group form-textarea margin-top-10 margin-left-20 margin-bottom-20 clearboth">
            <label><i class="imp">*</i>审批备注:</label>
            <textarea type="text" class="form-control no-cursor" rows="10" cols="100%" id="comment" name="comment" disabled="disabled" readonly="readonly" ><!=comment!></textarea>
        </div>
	</form>
    </div>
</script>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script>
var examineTemplate=baidu.template;
function showReadOnlyExamineTemplate(id, item) {
	var bdhtml = examineTemplate('examineReadOnlyTemplate',item);
	
	$("#"+id).append(bdhtml);
}
</script>