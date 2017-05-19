var gridObj;
$(function () {
    var gridObj = utils.initGrid('submittedGrid', 'supply/approvals/getSubmittedList');
});

function baseOperation(record, rowIndex, colIndex, options) {
    var lookInformationButton = '<a onclick="lookInformation(\'' + record.id
        + '\');" href="javascript:void(0);" class="red margin-right-10">查看商品详情</a>';
    return lookInformationButton;
}
function approverNameOperation(record, rowIndex, colIndex, options) {
    var approverNameStr = '<span class="">' + record.approverName + ' ' + record.approverNumber + '</span>';
    return approverNameStr;
}
function lookInformation(auditBillId) {
    window.location.href = ctx + '/supply/approvals/' + auditBillId + '/submittedInfo' + '?t=' + new Date().valueOf();
}

function priceShowOperation(record, rowIndex, colIndex, options) {
    return '¥' + utils.toFixed((record.recommend != null && record.recommend != undefined) ? record.recommend : record.cost);
}
