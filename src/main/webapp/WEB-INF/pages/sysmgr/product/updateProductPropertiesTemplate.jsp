<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script id="propertiesTemplate" type="text/html">
<!
	var i = 0, length;
	if(list && (length = list.length)) {
		for(; i < length; i++) {
			var itemValueObj = list[i], itemValueId = itemValueObj.id,
			itemValue = itemValueObj.value,
			productPropertiesName = itemValueObj.productPropertiesName,
			itemType = itemValueObj.itemType,
			itemKeyObj = itemValueObj.productCategoryItemKey,
			itemKeyId = itemKeyObj && itemKeyObj.id || '',
			orderNumber = itemValueObj.orderNumber,
			number = generateNumber(), allowedNotNull = itemKeyObj && itemKeyObj.allowedNotNull || 0,
			itemValueArray = itemValue.split(splitCharacter);
			if (itemKeyObj) {
				productPropertiesName = itemKeyObj.itemName;
			}
!>
			<tr itemKeyId="<!=itemKeyId!>" itemValueId="<!=itemValueId!>" class="property_<!=number!>">
			<!
				if(itemType) {
					var allowedMultiSelect = itemKeyObj.allowedMultiSelect, labelType = allowedMultiSelect ? 'checkbox'	: 'radio',
					itemsSources = itemKeyObj.itemsSources || '', itemsSourcesLength,
					m = 0, allowedCustom = itemKeyObj.allowedCustom,
					itemsSourcesArray = [], hasItemsSources = $.trim(itemsSources).length > 0;
					if (hasItemsSources) {
						itemsSourcesArray = itemsSources.split(splitCharacter);
					}
			!>
					<td class="propertyName">
						<!
							if(allowedNotNull) {
						!>
								<i class="imp">*</i>
						<!
							}
						!>
							<!=productPropertiesName!>
					</td>
					<td class="propertyTd form-group <!=(allowedNotNull ? 'notNullTd' : '')!>" data-type="<!=labelType!>">
						<div class="form-group <!=(hasItemsSources ? labelType : '')!>">
					<!
						if (itemsSourcesArray && (itemsSourcesLength = itemsSourcesArray.length)) {
							var itemValues = itemValue.split(splitCharacter);
							for (; m < itemsSourcesLength; m++) {
								var itemsSource = itemsSourcesArray[m], index = itemValueArray.indexOf(itemsSource);
								if (index >= 0) {
									itemValueArray.splice(index, 1);
								}
					!>
								<label class="margin-right-15 pull-left <!=(index >= 0 ? 'checked' : '')!>">
									<input type="<!=labelType!>" name="property_<!=number!>" class="propertyValue system" value="<!=itemsSource!>"/>
									<i></i><span><!=itemsSource!></span>
								</label>
					<!
							}
						}
						if (allowedCustom && itemsSourcesLength) {
					!>
							<label class="pull-left <!=(itemValueArray.length ? 'checked' : '')!>" for="property_<!=number!>">
								<input type="<!=labelType!>" name="property_<!=number!>" class="custom propertyValue"/>
								<i class="pull-left margin-top-10 margin-right-5"></i>
							</label>
					<!
						}
						if(allowedCustom) {
					!>
						<div class="form-group pull-left">
							<input type="text" class="customInput form-control block" value="<!=(itemValueArray.length ? itemValueArray.join(',') : '')!>"/>
						</div>
					<!
						}
					!>
						</div>
					</td>
			<!
				} else {
			!>
					<td class="propertyName">
						<div class="form-group">
							<input type="text" name="propertyName" id="property_<!=number!>" value="<!=productPropertiesName!>" class="form-control"/>
						</div>
					</td>
					<td class="propertyTd">
						<div class="form-group">
							<input type="text" id="customInput_<!=number!>" name="customInputRequire" class="customInput form-control" value="<!=itemValue!>"/>
						</div>
					</td>
			<!
				}
			!>
				<td>
					<div class="form-group">
						<input type="text" value="<!=orderNumber!>" class="orderInput form-control sm-form-control text-center" name="orderInput" id="order_<!=number!>"/>
					</div>
				</td>
			<!
				if (itemType) {
			!>
				<td></td>
			<!
				} else {
			!>
				<td>
					<a class="red cancel">删除</a>
				</td>
			<!
				}
			!>
			</tr>
<!
		}
	}
!>
</script>