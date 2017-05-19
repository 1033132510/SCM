$(function () {
    var gridObj = utils.initGrid('adminPendingGrid',
        '/sysmgr/approvals/admin/getPendingList');

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
    var pendingButton = '<a onclick="lookPending(\'' + record.id
        + '\');" href="javascript:void(0);" class="red margin-right-10">处理商品信息</a>';
    return pendingButton;
}

function lookPending(auditBillId) {
    window.location.href = ctx + '/sysmgr/approvals/admin/' + auditBillId + '/pendingInfo';
}