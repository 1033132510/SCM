var priceKind2TplKey = {
    '-2': 'standard',
    '-1': 'cost',
    '1': 'level1',
    '2': 'level2',
    '3': 'level3'
};
var priceKindEN2CN = {
    'standard': '标价',
    'cost': '成本价',
    'level1': '一级客户报价',
    'level2': '二级客户报价',
    'level3': '三级客户报价'
};
var priceKeys = ['cost', 'standard', 'level1', 'level2', 'level3'];
var priceIdSuffix = '_id';
var id = $('#id').val();
var cateId = $('#cateId').val();
var bt = baidu.template;
bt.ESCAPE = false;
var ProductPrice = {
    init: function () {
        this.getProductShowInfo(this);
        this.getModifyRecords(this);
    },
    updateProductPrice: function () {
        var that = this;
        var validate = that.validatePrices();
        if (!validate.form()) {
            scrollTop({
                distance: '#' + utils.getFirstErrorLabelId(validate),
                customTop: 110,
                time: 200,
                windowScroll: false
            });
            return false;
        }
        // 单位必填
        var unit = $.trim($('#unit').val());
        if(!unit) {
        	zcal({
				type : 'warning',
				title : '错误提示',
				text : '请填写商品单位'
			});
        	return false;
        }
        zcal({
            type: 'confirm',
            title: '有关商品价格信息非常敏感，请确认修改？'
        }, function () {
            $.ajax({
                url: ctx + '/sysmgr/product/updatePrice',
                data: that.getRequestData(),
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if (result.success) {
                        zcal({
                            type: 'success',
                            title: '修改成功'
                        });
                        that.getModifyRecords(that);
                        that.setProductPrices();
                    } else {
                        zcal({
                            type: 'error',
                            title: '系统提示',
                            text: data.description
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
    },
    setProductPrices: function () {
        for (var i = 0; i < priceKeys.length; i++) {
            var price = $('#' + priceKeys[i]).val();
            $('#' + priceKeys[i]).val(utils.toFixed(price));
        }
    },
    rendProductAndBrand: function (data) {
        $('#productAndBrand').html(bt('productAndBrandTpl', this.getProductAndBrand(data)));
    },
    rendProductPriceAndRemark: function (data) {
        $('#productPrice').html(bt('productPriceTpl', this.getPricesAndRemark(data)));
        $('#unit').bind('blur', function() {
        	var unit = $(this).val();
        	$('input[name="unit"]').val(unit);
        });
        $('#standard,#cost,#level1,#level2,#level3').bind('blur', function() {
        	var $input = $(this), value = $input.val();
        	if(value && !isNaN(value)) {
        		$input.val(new Number(value).toFixed(2));
    		}
        });
    },
    rendModifyRecords: function (data) {
        if (data.length < 1) {
            $('#modifyRecords').html('<tr><td colspan="9">没有修改记录</td></tr>');
            return false;
        }
        var modifyRecordsHtml = '';
        for (var i = 0; i < data.length; i++) {
            for (var j = 0; j < priceKeys.length; j++) {
                data[i][priceKeys[j]] = utils.toFixed(data[i][priceKeys[j]]);
            }
            data[i]['taxAndTransportation'] = this.getTaxAndTransportationText(data[i].hasTax, data[i].hasTransportation);
            modifyRecordsHtml += bt('modifyRecordsTpl', data[i]);
        }
        $('#modifyRecords').html(modifyRecordsHtml);
    },
    getTaxAndTransportationText: function (hasTax, hasTransportation) {
        var taxText = '含税';
        var transportationText = '含运费';
        if (!hasTax) {
            taxText = '不含税';
        }
        if (!hasTransportation) {
            transportationText = '不含运费';
        }
        return taxText + '/' + transportationText;
    },
    getProductShowInfo: function (self) {
        $.ajax({
            url: ctx + '/sysmgr/product/viewProductDetail',
            data: {
                productId: id,
                cateId: cateId
            },
            success: function (data) {
                self.rendProductAndBrand(data);
                self.rendProductPriceAndRemark(data);
            }
        });
    },
    getModifyRecords: function (self) {
        $.ajax({
            url: ctx + '/sysmgr/product/modifyRecord',
            data: {
                productSkuId: id,
                productCategoryId: cateId
            },
            success: function (data) {
                self.rendModifyRecords(data);
            }
        });
    },
    getRequestData: function () {
        var productPrices = [];
        var modifyRecord = {};
        for (var key in priceKind2TplKey) {
            var tplKey = priceKind2TplKey[key];
            var priceIdAndValue = {
                id: $('#' + tplKey + priceIdSuffix).val(),
                actuallyPrice: $('#' + tplKey).val()
            };
            modifyRecord[tplKey] = $('#' + tplKey).val();
            productPrices.push(priceIdAndValue);
        }
        return {
            productSkuId: id,
            productCategoryId: cateId,
            productPricesJson: JSON.stringify(productPrices),
            modifyRecordJson: JSON.stringify(modifyRecord),
            feeRemark: $('#feeRemark').val(),
            feeLogistics: $('#feeLogistics').val(),
            unit : $('#unit').val(),
            hasTax: $('#hasTax').closest('label').hasClass('checked') ? 1 : 0,
            hasTransportation: $('#hasTransportation').closest('label').hasClass('checked') ? 1 : 0
        };
    },
    getProductAndBrand: function (data) {
        var productAndBrand = {
            productName: data.productName,
            productCode: data.productCode,
            brandName: data.brand.brandZHName
        };
        return productAndBrand;
    },
    getPricesAndRemark: function (data) {
        var prices = {};
        for (var i = 0; i < data.productPrices.length; i++) {
            var templateKey = priceKind2TplKey[data.productPrices[i].priceKindModel.priceKindType];
            prices[templateKey] = utils.toFixed(data.productPrices[i].actuallyPrice);
            prices[templateKey + priceIdSuffix] = data.productPrices[i].id;
        }
        prices.hasTax = data.hasTax;
        prices.hasTransportation = data.hasTransportation;
        var remarks = {
            feeRemark: data.feeRemark,
            feeLogistics: data.feeLogistics
        };
        return {prices: prices, remarks: remarks, unit : data.unit ? data.unit : ''};
    },
    generateValidateMessage: function (priceKindEN) {
        var message = {
            required: priceKindEN2CN[priceKindEN] + '不能为空',
            number: priceKindEN2CN[priceKindEN] + '格式不正确'
        }
        return message;
    },
    generateValidateRule: function () {
        var rule = {
            required: true,
            number: true
        };
        return rule;
    },
    validatePrices: function () {
        var validateSettings = {
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
                standard: this.generateValidateRule(),
                cost: this.generateValidateRule(),
                level1: this.generateValidateRule(),
                level2: this.generateValidateRule(),
                level3: this.generateValidateRule()
            },
            messages: {
                standard: this.generateValidateMessage('standard'),
                cost: this.generateValidateMessage('cost'),
                level1: this.generateValidateMessage('level1'),
                level2: this.generateValidateMessage('level2'),
                level3: this.generateValidateMessage('level3')
            }
        };
        return $("#updateProductPriceForm").validate(validateSettings);
    }
};

$(function () {
    ProductPrice.init();
});