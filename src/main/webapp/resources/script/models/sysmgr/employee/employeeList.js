$(function () {
    var gridObj = utils.initGrid('employees', 'sysmgr/employee/page');


    $('#search').click(function (e) {
        e.preventDefault();
        var searchParams = {
            searchParam: $('#searchParam').val()
        }
        gridObj.search(searchParams);
    });

    // 删除单条记录事件
    $(".delete").click(function () {
        var href = $(this).attr("href");
        $("#deleteForm").attr("action", href).submit();
        return false;
    });

    /**
     * 回车搜索事件
     */
    $('#searchParam').bind('keypress',function(event){
        if(event.keyCode == "13")
        {
            $('#search').trigger("click");
        }
    });
});

/**
 * 解析员工在职状态
 * @param record
 * @returns {string}
 */
var formatterEmployeeStatus = function (record) {
    if (record.employeeStatus == 2) {
        return '离职';
    } else {
        return '在职';
    }
};
/**
 * 解析员工类型
 */
var formatterEmployeeType = function (record) {
    if (record.employeeType == 2) {
        return '合约员工';
    } else {
        return '正式员工';
    }
};
/**
 * 解析员工数据状态
 */
var formatterStatus = function (record) {
    if (record.status == 0) {
        return '失效';
    } else {
        return '有效';
    }
};
/**
 * 删除操作
 * @param employeeId
 */
var deleteEmployee = function (employeeId) {
    zcal({
        type:'confirm',
        title:'确定失效该员工吗？'
    }, function(){
        //var href = 'employee/info/' + employeeId;
        //$("#deleteForm").attr("action", href).submit();

        var submitOption = {
            url: ctx + '/sysmgr/employee/remove/' + employeeId,
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            success: function showResponse(result) {
                if (result.success) {
                    location.reload();
                } else {
                    // 处理错误信息
                    zcal({
                        type: 'error',
                        title: '删除失败'
                    });
                }
            },
            error : function () {
                zcal({
                    type: 'error',
                    title: '服务器异常'
                });
            }
        };
        $.ajax(submitOption);
    })
};
/**
 * 设置账户生效操作
 * @param employeeId
 */
var restoreEmployee = function (employeeId) {
    zcal({
        type:'confirm',
        title:'确定生效该员工吗？'
    }, function(){
        var submitOption = {
            url: ctx + '/sysmgr/employee/updateStatus/' + employeeId + '/1',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            success: function showResponse(result) {
                if (result.success) {
                    location.reload();
                } else {
                    zcal({
                        type: 'error',
                        title: '设置失败'
                    });
                }
            },
            error : function () {
                zcal({
                    type: 'error',
                    title: '服务器异常'
                });
            }
        };
        $.ajax(submitOption);
    })
};
/**
 * 定义列表操作
 * @param record
 * @returns {string}
 * record, rowIndex, colIndex, options
 */
var operation = function (record, rowIndex) {
    var modify = '<a class="red" href="info/' + record.id + '">修改</a>';
    var updateStatus;
    if (record.status == 0) {
        updateStatus = '<a class="delete red" onclick="restoreEmployee(\'' + record.id + '\')"' + 'href="javascript:void(0);">设置生效</a>';
    } else {
        updateStatus = '<a class="delete red" onclick="deleteEmployee(\'' + record.id + '\')"' + 'href="javascript:void(0);">设置失效</a>';
    }
    var setOrgPositon = '<a class="red" href="orgPosition/' + record.id + '">岗位</a>';
    return modify + '&nbsp;&nbsp;&nbsp;' + updateStatus + '&nbsp;&nbsp;&nbsp;' + setOrgPositon;
};




