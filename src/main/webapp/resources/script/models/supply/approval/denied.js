var gridObj;
$(function () {
    gridObj = utils.initGrid('denied_grid', 'supply/approvals/denied');
});

function formatterPrice(record) {
    return '¥' + utils.toFixed((record.recommend != null && record.recommend != undefined) ? record.recommend : record.standard)
}
function baseOperation(record) {
    var toAdjustButton = '<a onclick="adjust(\'' + record.id + '\');" href="javascript:void(0);" class="red margin-right-10">调整商品信息</a>';
    var deleteButton = '<a onclick="deleteAuditBill(\'' + record.id + '\'' + ',' + '\'' + record.productName + '\');" href="javascript:void(0);" class="red margin-right-10">删除</a>';
    return toAdjustButton + deleteButton;
}

function adjust(id) {
    window.location.href = ctx + '/supply/approvals/' + id;
}

function deleteAuditBill(id, productName) {
    zcal({
            type: 'confirm',
            title: '商品删除后将无法撤回!请确认删除：' + productName + '商品？'
        }, function () {
            $.ajax({
                url: ctx + '/supply/approvals/' + id,
                type: 'DELETE',
                success: function (result) {
                    if (result.success) {
                        gridObj.refreshPage();
                    }
                }
            });
        }
    );
}