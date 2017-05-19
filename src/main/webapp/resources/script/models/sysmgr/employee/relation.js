$(function(){
    //初始化节点
    zzcTree.initTree('orgTree', '/sysmgr/companyOrg/initTree', false, false, true, true);
    //获得root节点的孩子节点
    zzcTree.getChildNode(zTree.getNodes()[0]);

    var gridObj;
    $.bsgridLanguage.noDataToDisplay = '没有数据可以用于显示。';
    gridObj = $.fn.bsgrid.init('positions', {
        url: ctx + '/sysmgr/position/page',
        autoLoad: true,
        pageSizeSelect : false,
        pageSize : 10,
        displayBlankRows: false, // 不显示空白行
        displayPagingToolbarOnlyMultiPages: true,
        event: {
            selectRowEvent: function (record, rowIndex, trObj, options) {
                $('#positionId').val(gridObj.getRecordIndexValue(record, 'id'));
                $('#positionName').val(gridObj.getRecordIndexValue(record, 'positionName'));
                $("#closePositionDiv").trigger('click');
            }
        }
    });

    $('#userOrgPostionForm').validate();
});

/**
 * 树节点点击回调
 * @param data
 */
var showNodeInfo = function (data) {
    var orgName = data.orgName;
    var orgId = data.id;

    // 临时存储选择的组织
    $('#chooseOrgId').val(orgId);
    $('#chooseOrgName').val(orgName);
};
/**
 * 确认选择组织
 */
var confirmOrgChoose = function () {
    $('#companyOrgId').val($('#chooseOrgId').val());
    $('#companyOrgName').val($('#chooseOrgName').val());
    $('#closeTreeDiv').trigger('click');
}

var operation = function (record, rowIndex) {
    return '';
};