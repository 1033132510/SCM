$(function () {
    var gridObj = utils.initGrid('purchasers', 'sysmgr/purchaser/purchasers');
    $('#search').click(function (e) {
        e.preventDefault();
        var searchParams = {
            codeOrCompany: $('#codeOrCompany').val()
        }
        gridObj.search(searchParams);
    });
    
    // 回车事件
    $("#codeOrCompany").keydown(function(event){
        event = document.all ? window.event : event;
        if((event.keyCode || event.which) == 13) {
        		event.stopPropagation();
        		$('#search').click();
        }
    });

    // 添加采购商
    $('#addPurchaser').click(function (e) {
        e.preventDefault();
        window.location.href = ctx + '/sysmgr/purchaser/addView';
    });
});


function formatterLevel(record, rowIndex, colIndex, tdObj, trObj, options) {
    return record.level + '级';
}

function operation(record, rowIndex, colIndex, options) {
    var modifyPurchaser = '<a onclick="modifyPurchaser(\'' + record.id + '\');" href="javascript:void(0);" class="red margin-right-10">编辑公司信息</a>';
    var modifyPurchaserUser = '<a onclick="modifyPurchaserUser(\'' + record.id + '\');" href="javascript:void(0);" class="blue margin-left-10">人员管理</a>';
    return modifyPurchaser + modifyPurchaserUser;
}

function modifyPurchaser(id) {
	window.location.href = ctx + '/sysmgr/purchaser/modifyView/' + id;
}

function modifyPurchaserUser(purchaserId) {
    window.location.href = ctx + '/sysmgr/purchaserUser/view/purchaser/' + purchaserId;
}
