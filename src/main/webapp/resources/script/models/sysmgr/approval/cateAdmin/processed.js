$(function () {
    var gridObj = utils.initGrid('adminProcessedGrid',
        '/sysmgr/approvals/admin/getProcessedList');
    $('#searchBtn').click(function (e) {
        e.preventDefault();
        var searchParams = { // 搜索条件
            "nameOrCode": $("#nameOrCode").val(),
            "orgName": $("#orgName").val(),
            "brandName": $("#brandName").val()
        }
        gridObj.search(searchParams);
    });
});

function baseOperation(record, rowIndex, colIndex, options) {
    var processButton = '<a onclick="lookProcess(\'' + record.id
        + '\');" href="javascript:void(0);" class="red margin-right-10">查看商品信息</a>';
    return processButton;
}
function lookProcess(auditBillId) {
    window.location.href = ctx + '/sysmgr/approvals/admin/' + auditBillId + '/processedInfo';
}

function priceShowOperation(record, rowIndex, colIndex, options) {
    var priceShow = '';
    if (record.standard) {
        priceShow = '¥' + utils.toFixed(record.standard);
    }
    return priceShow;
}