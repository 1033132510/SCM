<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script id="propertiesTemplate" type="text/html">
<!
	var i = 0, length;
	if(list && (length = list.length)) {
		for(; i < length; i++) {
			var itemValueObj = list[i], itemValue = itemValueObj.value,
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
			<tr>
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
					<td>
						<!=productPropertiesName!>
					</td>
					<td class="form-group">
						<div class="form-group disabled <!=(hasItemsSources ? labelType : '')!>">
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
									<input type="<!=labelType!>" name="property_<!=number!>" class="propertyValue" value="<!=itemsSource!>"/>
									<i></i><span><!=itemsSource!></span>
								</label>
					<!
							}
						}
						if (allowedCustom && itemsSourcesLength) {
					!>
							<label class="pull-left <!=(itemValueArray.length ? 'checked' : '')!>" for="property_<!=number!>">
								<input type="<!=labelType!>" name="property_<!=number!>"/>
								<i class="pull-left margin-top-10 margin-right-5"></i>
							</label>
					<!
						}
						if(allowedCustom) {
					!>
						<div class="form-group pull-left">
							<input type="text" class="form-control block" value="<!=(itemValueArray.length ? itemValueArray.join(',') : '')!>" readonly/>
						</div>
					<!
						}
					!>
						</div>
					</td>
			<!
				} else {
			!>
					<td>
						<div class="form-group">
							<input type="text" id="property_<!=number!>" value="<!=productPropertiesName!>" class="form-control" readonly/>
						</div>
					</td>
					<td>
						<div class="form-group">
							<input type="text" id="customInput_<!=number!>" class="customInput form-control" value="<!=itemValue!>" readonly/>
						</div>
					</td>
			<!
				}
			!>
				<td>
					<div class="form-group">
						<input type="text" value="<!=orderNumber!>" class="form-control sm-form-control text-center" readonly/>
					</div>
				</td>
			</tr>
<!
		}
	}
!>
</script>