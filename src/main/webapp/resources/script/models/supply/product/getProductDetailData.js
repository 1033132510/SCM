/**
 * Created by chenjiahai on 16/2/24.
 */

var ProductDetail = {
    getProductDetailDataForUpdate: function () {
        var $productForm = $('#productForm'), product,
            thirdLevelCateId = $('#cateId').val(), i = 0,
            brandId = $('#brandId').val(), productCode = $('#productCode').val(),
            supplierOrgId = $('#supplierOrgId').val(),
            productCategoryItemValues = [], $trs = $('#productPropertiesContainer').find('tr'),
            length = $trs.length, $costPrice = $('#costPrice'),
            $recommendedPrice = $('#recommendedPrice');

        // 防止重复提交
        if ($productForm.hasClass('disabled')) {
            return false;
        }
        $productForm.addClass('disabled');
        for (; i < length; i++) {
            var $tr = $($trs[i]), itemKeyId = $tr.attr('itemKeyId'),
                propertyName = $tr.find('input[name="propertyName"]').val() || $tr.find('td.propertyName').html(),
                $inputs = $tr.find('.propertyTd .checked .system'), inputLength = $inputs.length,
                order = $tr.find('.orderInput').val(), m = 0,
                propertyValues = [], productCategoryItemValueObj, $customInput = $tr.find('.propertyTd .customInput');

            for (; m < inputLength; m++) {
                propertyValues.push($($inputs[m]).val());
            }
            if (!inputLength || $tr.find('.propertyTd .checked .custom').closest('label').hasClass('checked')) {
                propertyValues.push($.trim($customInput.val()));
            }

            productCategoryItemValueObj = {
                order: order,
                value: propertyValues.join(','),
                itemType: itemKeyId ? CATEGORY_TYPE : PRODUCT_TYPE,
                status: VALID_STATUS,
                productCategoryItemKeyId: itemKeyId
            };

            if (!itemKeyId) {
                productCategoryItemValueObj.productPropertiesName = propertyName;
            }
            productCategoryItemValues.push(productCategoryItemValueObj);
        }

        product = {
            status: VALID_STATUS,
            productId: $('#id').val(),
            productCode: productCode,
            productName: $.trim($('#productName').val().replace(/\s+/g, ' ')),
            brandId: brandId,
            secondLevelCateId: $('#secondLevelCateId').val(),
            thirdLevelCateId: thirdLevelCateId,
            feeRemark: $('#feeRemark').val(),
            feeLogistics: $('#feeLogistics').val(),
            supplierOrgId: $('#supplierOrgId').val(),
            productDesc: $('#description').val(),
            hasTax: $('#hasTax').closest('label').hasClass('checked') ? 1 : 0,
            hasTransportation: $('#hasTransportation').closest('label').hasClass('checked') ? 1 : 0,
            productNumber: $('#number').val(),
            minOrderCount: $('#minOrderCount').val(),
            imageIds: ProductDetail.getImageIds(),
            //productImageIds: ProductDetail.getImageIds(),
            //delProductImageIds: $.fn.image.getDeleteImageIds(),
            productPropertiesModels: productCategoryItemValues,
            unit: $('input[name="unit"]').eq(0).val(),
            cost: new Number($costPrice.val()).toFixed(2),
            recommend: new Number($recommendedPrice.val()).toFixed(2)
        };

        return product;
    },
    getProductDetailData: function () {
        var $productForm = $('#productForm'), product,
            thirdLevelCateId = $('#productCategoryContainer').find('select:eq(2)').val(), i = 0,
            secondLevelCateId = $('#productCategoryContainer').find('select:eq(1)').val(),
            productCategoryItemValues = [], $trs = $('#productPropertiesContainer').find('tr'),
            length = $trs.length, $costPrice = $('#costPrice'),
            $recommendedPrice = $('#recommendedPrice');
        // 防止重复提交
        if ($productForm.hasClass('disabled')) {
            return false;
        }
        $productForm.addClass('disabled');
        for (; i < length; i++) {
            var $tr = $($trs[i]), itemKeyId = $tr.attr('itemKeyId'),
                propertyName = $tr.find('input[name="propertyName"]').val() || $tr.find('td.propertyName').html(),
                $inputs = $tr.find('.propertyTd .checked .system'), inputLength = $inputs.length,
                order = $tr.find('.orderInput').val(), m = 0,
                propertyValues = [], productCategoryItemValueObj, $customInput = $tr.find('.propertyTd .customInput');

            for (; m < inputLength; m++) {
                propertyValues.push($($inputs[m]).val());
            }
            if (!inputLength || $tr.find('.propertyTd .checked .custom').closest('label').hasClass('checked')) {
                propertyValues.push($.trim($customInput.val()));
            }
            productCategoryItemValueObj = {
                order: order,
                value: propertyValues.join(','),
                itemType: itemKeyId ? CATEGORY_TYPE : PRODUCT_TYPE,
                status: VALID_STATUS,
                productCategoryItemKeyId: itemKeyId
            };

            if (!itemKeyId) {
                productCategoryItemValueObj.productPropertiesName = propertyName;
            }
            productCategoryItemValues.push(productCategoryItemValueObj);
        }

        product = {
            status: VALID_STATUS,
            productName: $.trim($('#productName').val().replace(/\s+/g, ' ')),
            brandId: $('#brandSelectList').val(),
            secondLevelCateId: secondLevelCateId,
            thirdLevelCateId: thirdLevelCateId,
            feeRemark: $('#feeRemark').val(),
            feeLogistics: $('#feeLogistics').val(),
            supplierOrgId: $('#supplierId').val(),
            productDesc: $('#description').val(),
            hasTax: $('#hasTax').closest('label').hasClass('checked') ? 1 : 0,
            hasTransportation: $('#hasTransportation').closest('label').hasClass('checked') ? 1 : 0,
            productNumber: $('#number').val(),
            minOrderCount: $('#minOrderCount').val(),
            imageIds: ProductDetail.getImageIds(),
            //productImageIds: ProductDetail.getImageIds(),
            //delProductImageIds: $.fn.image.getDeleteImageIds(),
            productPropertiesModels: productCategoryItemValues,
            unit: $('input[name="unit"]').eq(0).val(),
            cost: new Number($costPrice.val()).toFixed(2),
            recommend: new Number($recommendedPrice.val()).toFixed(2)
        };
        return product;
    },
    getImageIds: function () {
        var uploadedImageIds = [];
        $('img[id^="img_"]').each(function () {
            var imgDomId = $(this).attr('id');
            var imageId = imgDomId.split('_')[2];
            uploadedImageIds.push(imageId);
        });
        return uploadedImageIds;
    },
    getProductDetailAndCommentForUpdate: function () {
        var productDetail = ProductDetail.getProductDetailDataForUpdate();
        productDetail.comment = $('#comment').val();
        return productDetail;
    },
    searchPreviewProduct: function (price, priceKindId, hasTax, hasTransportation) {
        var url = ctx + '/product/preview/' + $('#auditBillId').val() + '?price=' + price + '&priceKindId=' + priceKindId + '&hasTax=' + hasTax + '&hasTransportation=' + hasTransportation;
        window.open(url);
    },
    previewProduct: function (url, data) {
        //var validator = ProductDetail.validateAllNumber();
        //if (!validator.form()) {
        //    scrollTop({
        //        distance: '#' + utils.getFirstErrorLabelId(validator),
        //        customTop: 110,
        //        time: 200,
        //        windowScroll: false
        //    });
        //    return false;
        //}
        ProductDetail.removeForm();
        var mapForm = document.createElement("form");
        mapForm.id = "previewForm";
        mapForm.target = "preview";
        mapForm.method = "POST";
        mapForm.action = url ? url : DEFAULT_URL;

        data = data ? data : ProductDetail.getProductDetailData();

        var mapInput = document.createElement("input");
        mapInput.type = "hidden";
        mapInput.name = "productDetail";
        mapInput.value = JSON.stringify(data);
        mapForm.appendChild(mapInput);
        document.body.appendChild(mapForm);

        map = window.open("", "preview");

        if (map) {
            mapForm.submit();
        } else {
            alert('You must allow popups for this map to work.');
        }
    },
    removeForm: function () {
        $('#previewForm').remove();
        $('#productForm').removeClass('disabled');
    },
    previewProductForUpdate: function (url, data) {
        ProductDetail.removeForm();
        var mapForm = document.createElement("form");
        mapForm.target = "preview";
        mapForm.method = "POST";
        mapForm.action = url ? url : DEFAULT_URL;

        data = data ? data : ProductDetail.getProductDetailDataForUpdate();
        var mapInput = document.createElement("input");
        mapInput.type = "hidden";
        mapInput.name = "productDetail";
        mapInput.value = JSON.stringify(data);
        mapForm.appendChild(mapInput);
        document.body.appendChild(mapForm);

        map = window.open("", "preview");

        if (map) {
            mapForm.submit();
        } else {
            alert('You must allow popups for this map to work.');
        }
    },
    validateAllNumber: function () {
        var rules = {
            number: {
                digits: true
            },
            minOrderCount: {
                digits: true
            },
            costPrice: {
                number: true
            },
            recommendedPrice: {
                number: true
            }
        }, messages = {
            number: {
                digits: '库存数量只能是整数'
            },
            minOrderCount: {
                digits: '最小起订量只能是整数'
            },
            costPrice: {
                number: '成本价只能是数字'
            },
            recommendedPrice: {
                number: '建议售价只能是数字'
            }
        };
        var opt = {};
        opt.rules = rules;
        opt.messages = messages;
        return $('#productForm').validate(opt);
    }
}

DEFAULT_URL = ctx + '/product/preview';
