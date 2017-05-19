$(function () {
    $.fn.image.init('licenceDiv', 1, {size: 150}, {relationId: $('#id').val(), relationType: 1});
    $.fn.image.init('codeLicenceDiv', 2, {size: 150}, {relationId: $('#id').val(), relationType: 2});
    $.fn.image.init('taxRegistrationDiv', 16, {size: 150}, {relationId: $('#id').val(), relationType: 16});
    $.fn.image.init('constructionQualificationDiv', 17, {size: 150}, {relationId: $('#id').val(), relationType: 17});
    $('#savePurchaser').click(function (e) {
        var validate = validateFormEle();
        if (!validate.form()) {
            scrollTop({
                distance: '#' + utils.getFirstErrorLabelId(validate),
                customTop: 110,
                time: 200,
                windowScroll: false
            });
            return false;
        }

        if ($('.preview').length < 4) {
            zcal({
                type: 'error',
                text: '请上传证件复印件'
            });
            return false;
        }
        e.preventDefault();
        var orgName = $.trim($('#orgName').val());
        var legalName = $.trim($('#legalName').val());
        var level = $('#level').val();
        var tel = $.trim($('#tel').val());
        var id = $('#id').val();
        var parentId = $('#parentId').val();
        var params = {
            orgName: orgName,
            legalName: legalName,
            level: level,
            tel: tel,
            id: id,
            imageIds: $.fn.image.getUploadImageIds(),
            deleteImageIds: $.fn.image.getDeleteImageIds(),
            parentId: parentId
        }

        var isExistSameOrgName = false;
        if (id == '') {
            isExistSameOrgName = checkExistOrgName(orgName);
        } else {
            var originalOrgName = $('#originalOrgName').val();
            if (originalOrgName != '' && originalOrgName != orgName) {
                isExistSameOrgName = checkExistOrgName(orgName);
            }
        }
        if (isExistSameOrgName) {
            zcal({
                type: 'error',
                title: '采购商已存在'
            });
            return !isExistSameOrgName;
        }

        $.ajax({
            url: ctx + '/sysmgr/purchaser/saveOrUpdate',
            contentType: 'application/json;charset=UTF-8',
            type: 'POST',
            data: JSON.stringify(params),
            dataType: 'json',
            success: function (result) {
                if (result && result.success) {
                    var title = id ? '编辑成功' : '保存成功';
                    zcal({
                        type: 'continue',
                        title: title,
                        continueBtnSure: '继续添加公司人员信息'
                    });
                    $('.continue-cancel').off().on('click', function (e) {
                        e.preventDefault();
                        window.location.href = ctx + '/sysmgr/purchaser/view';
                    });
                    $('.continue-sure').on('click', function (e) {
                        e.preventDefault();
                        window.location.href = ctx + '/sysmgr/purchaserUser/addView/purchaser/' + result.resultData.id;
                    });


                    if (id == '' && parentId == '') {
                        zzcTree.initTree('purchaserTree', '/sysmgr/purchaser/tree?init=1&purchaserId=' + result.resultData.id);
                        zzcTree.InfoAndSelectNodeById(result.resultData.id);
                    } else {
                        zzcTree.refreshAndSelected(result.resultData.node);
                    }
                    $('#id').val(result.resultData.id);
                    $('#parentId').val(result.resultData.parentId);

                } else {
                    var descriptionObj = JSON.parse(result.description);
                    var description = [];
                    for (var key in descriptionObj) {
                        description.push(descriptionObj[key]);
                    }
                    zcal({
                        type: 'error',
                        title: description.join('、')
                    });
                }

            },
            error: function () {
                zcal({
                    type: 'error',
                    title: '服务器异常'
                });
            }
        });
    });

    if (window.location.href.indexOf('modifyView') > 0) {
        //初始化节点
        zzcTree.initTree('purchaserTree', '/sysmgr/purchaser/tree?init=1&purchaserId=' + $('#id').val());
        zzcTree.InfoAndSelectNodeById($('#id').val());
    }

    $('#relationExistedPurchaser').click(function (e) {
        e.preventDefault();
        $('#relationExistedPurchaserCodeDiv').removeClass('hide');
        $('#purchaserInfoDiv').addClass('hide');
        $('#relationOrgCode').val('');
        resetForm();
    });

    $('#getPurchaserInfo').click(function () {
        $.ajax({
            url: ctx + '/sysmgr/purchaser/orgCode/' + $.trim($('#relationOrgCode').val()),
            type: 'GET',
            dataType: 'json',
            success: function (result) {
                if (result && result.success) {
                    showNodeInfo(result.resultData, true);
                    $('#purchaserInfoDiv').removeClass('hide');
                    $('#relationExistedPurchaserBtnDiv').removeClass('hide');
                    $('#relationExistedPurchaserCodeDiv').removeClass('hide');
                    $('#purchaserInfoDiv').find('input').prop('disabled', true);
                    $('#purchaserInfoDiv').find('select').prop('disabled', true);
                    $('#savePurchaserLinkDiv').addClass('hide');
                    $('#relateLinkDiv').removeClass('hide');
                } else {
                    zcal({
                        type: 'warning',
                        title: '查询无数据，企业编码输入有误或企业编码不存在，请重新输入'
                    });
                }
            }

        });
    });

    $('#relate').click(function (e) {
        e.preventDefault();
        var selectedNode = zTree.getSelectedNodes();
        if (selectedNode.length < 1) {
            zcal({
                type: 'error',
                title: '请先选择采购商才能关联'
            });
            return;
        }

        if ($.trim($('#relationOrgCode').val()) == '') {
            zcal({
                type: 'error',
                title: '请先填写关联公司编号'
            });
            return;
        }
        $.ajax({
            url: ctx + '/sysmgr/purchaser/relate',
            type: 'POST',
            data: {orgCode: $.trim($('#relationOrgCode').val()), parentId: selectedNode[0].id},
            dataType: 'json',
            success: function (result) {
                zcal({
                    type: result.success ? 'success' : 'error',
                    title: result.description
                });
                if (result.success) {
                    zzcTree.getChildNode(selectedNode[0]);
                }
            },
            error: function () {
                zcal({
                    type: 'error',
                    title: '服务器异常'
                });
            }
        });
    });

    disabledAllEle();
});

function checkExistOrgName(orgName) {
    var isExist = false;
    $.ajax({
        url: ctx + '/sysmgr/purchaser/checkOrgNameIsExist/' + orgName,
        type: 'GET',
        async: false,
        success: function (result) {
            isExist = result;
        }
    });
    return isExist;
}

function disabledAllEle() {
    if (window.location.search.indexOf('?') >= 0) {
        $('input').each(function () {
            $(this).attr('readonly', true);
        });
        $('select').each(function () {
            $(this).attr('disabled', true);
        });
        $('.form-select .form-control').select2();

        $('b').each(function () {
            $(this).remove();
        });
        $('a[id^="a_"]').each(function () {
            if ($(this).siblings('span').length == 0) {
                $(this).find('input').each(function () {
                    $(this).attr('disabled', true);
                });
            } else {
                $(this).remove();
            }
        });
        $('#savePurchaserLinkDiv').remove();
        return true;
    }
    return false;
}

function showNodeInfo(data, readonly) {
    for (var key in data) {
        $('#' + key).val(utils.HTMLDecode(data[key]));
    }
    $('#originalOrgName').val(utils.HTMLDecode(data.orgName));
    if (data.licenceId && data.licencePath) {
        $.fn.image.reloadImage(data.licencePath, data.licenceRelationType, data.licenceId, readonly == true ? true : false);
    }
    if (data.codeLicenceId && data.codeLicencePath) {
        $.fn.image.reloadImage(data.codeLicencePath, data.codeLicenceRelationType, data.codeLicenceId, readonly == true ? true : false);
    }
    if (data.taxRegistrationId && data.taxRegistrationPath) {
        $.fn.image.reloadImage(data.taxRegistrationPath, data.taxRegistrationRelationType, data.taxRegistrationId, readonly == true ? true : false);
    }
    if (data.constructionQualificationId && data.constructionQualificationPath) {
        $.fn.image.reloadImage(data.constructionQualificationPath, data.constructionQualificationRelationType, data.constructionQualificationId, readonly == true ? true : false);
    }
    $('.form-select .form-control').select2(); // 让select重新渲染
    $('#purchaserInfoDiv').removeClass('hide');
    $('#relationExistedPurchaserCodeDiv').addClass('hide');
    $('#relationExistedPurchaserBtnDiv').removeClass('hide');
    $('#purchaserInfoDiv').find('input').prop('disabled', false);
    $('#purchaserInfoDiv').find('select').prop('disabled', false);
    $('#savePurchaserLinkDiv').removeClass('hide');
    $('#relateLinkDiv').addClass('hide');
    $('#parentId').val('');
    disabledAllEle();
}

// 添加子公司
function addInfo(treeNode) {
    if (disabledAllEle()) {
        return;
    }
    resetForm();
    $('#parentId').val(treeNode.id);
    $('#relationExistedPurchaserBtnDiv').addClass('hide');
    $('#relationExistedPurchaserCodeDiv').addClass('hide');
    $('#purchaserInfoDiv').removeClass('hide');
}

function resetForm() {
    $('#orgName').val('');
    $('#legalName').val('');
    $('#tel').val('');
    $('#level').val('1');
    $('.form-select .form-control').select2();
    $('.preview').each(function () {
        $(this).remove();
    });
    $.fn.image.clearData();
    $.fn.image.reInit([1, 2, 16, 17]);
    $('#id').val('');
}

function validateFormEle() {
    var validate = $("#purchaserForm").validate({
        errorElement: 'label',
        errorClass: 'error',
        focusInvalid: false,
        onkeyup: function (element) {
            $(element).valid();
        },
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
            orgName: {
                required: true
            },
            legalName: {
                required: true
            },
            tel: {
                required: true
            }
        },
        messages: {
            orgName: {
                required: '公司名称必填'
            },
            legalName: {
                required: '法人姓名必填'
            },
            tel: {
                required: '公司座机必填',
            }
        }
    });
    return validate;
};