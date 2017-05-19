<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script id="examineTemplate" type="text/html">
    <div class="type-manage blurb">
        <form id="recordForm" name="recordForm">
            <h3 class="head">
                <a>审核信息</a>
            </h3>
            <div class="form-group media short cost-news pull-left margin-top-20 margin-left-20 margin-bottom-20 padding-left-40">
                <label class="media-label"><i class="imp">*</i>标价:</label>
                <!if(standard){!>
                <input type="text" class="form-control valid cost" id="standard" value="<!=standard!>" name="standard"/>
                <!}else{!>
                <input type="text" class="form-control valid cost" id="standard" value="<!=recommendedPrice!>"
                       name="standard"/>
                <!}!>

                <label class="pull-right">元/<!=unit!></label>
            </div>
            <div class="form-group margin-top-20 margin-left-20 checkbox pull-left">
                <!if(shasTax != 1){!>
                <label class="pull-left" id="hasTaxClass">
                    <input type="checkbox" id="hasTax" name="hasTax" checked/>
                    <i></i><span>含税</span>
                </label>
                <!}else{!>
                <label class="checked pull-left" id="hasTaxClass">
                    <input type="checkbox" id="hasTax" name="hasTax" checked/>
                    <i></i><span>含税</span>
                </label>
                <!}!>
                <!if(shasTransportation != 1){!>
                <label class="margin-left-20 pull-left" id="hasTransportationClass">
                    <input type="checkbox" id="hasTransportation" name="hasTransportation" checked/>
                    <i></i><span>含运费</span>
                </label>
                <!}else{!>
                <label class="checked margin-left-20 pull-left" id="hasTransportationClass">
                    <input type="checkbox" id="hasTransportation" name="hasTransportation" checked/>
                    <i></i><span>含运费</span>
                </label>
                <!}!>
                <label class="gray-color margin-left-20">＊标价为电商平台采购商看到的价格</span></label>
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
                <textarea type="text" class="form-control" rows="10" placeholder="审批通过／不通过原因，备注信息。" cols="100%"
                          id="comment" name="comment"></textarea>
            </div>
        </form>
    </div>
</script>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script>
    var examineTemplate = baidu.template;
    function showExamineTemplate(id, item) {
        item.standard = utils.toFixed(item.standard);
        item.cost = utils.toFixed(item.cost);
        item.recommendedPrice = (item.recommendedPrice != null && item.recommendedPrice != undefined) ? utils.toFixed(item.recommendedPrice) : '无';
        var bdhtml = examineTemplate('examineTemplate', item);
        $("#" + id).append(bdhtml);
        $('[class=error]').hide();
    }
</script>