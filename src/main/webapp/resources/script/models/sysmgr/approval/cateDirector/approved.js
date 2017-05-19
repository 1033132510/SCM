$(function () {
    var gridObj = utils.initGrid('directorProcessedGrid',
        '/sysmgr/approvals/director/getApprovedList');
});

function baseOperation(record, rowIndex, colIndex, options) {
    var processButton = '<a onclick="lookProcess(\'' + record.id
        + '\');" href="javascript:void(0);" class="red margin-right-10">查看商品信息</a>';
    return processButton;
}

function lookProcess(auditBillId) {
    window.location.href = ctx + '/sysmgr/approvals/director/' + auditBillId + '/approvedInfo';
}
function standardOperation(record, rowIndex, colIndex, options) {
    var priceShow = '';
    if (record.standard) {
        priceShow = '¥' + utils.toFixed(record.standard);
    }
    return priceShow;
}
