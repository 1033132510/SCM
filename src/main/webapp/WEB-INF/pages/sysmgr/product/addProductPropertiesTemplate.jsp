<script id="propertiesTemplate" type="text/html">
<!
	var i = 0, length;
	if(list && (length = list.length)) {
		for(; i < length; i++) {
			var itemKeyObj = list[i], itemKeyId = itemKeyObj.id,
			allowedNotNull = itemKeyObj.allowedNotNull || 0, allowedCustom = itemKeyObj.allowedCustom,
			itemsSources = itemKeyObj.itemsSources || '', allowedMultiSelect = itemKeyObj.allowedMultiSelect,
			itemCode = itemKeyObj.itemCode, itemName = itemKeyObj.itemName,
			m = 0, itemsSourcesLength = 0,
			labelType = allowedMultiSelect ? 'checkbox'	: 'radio', number = generateNumber(),
			itemsSourcesArray = [], hasItemsSources = $.trim(itemsSources).length > 0,
			orderNumber = (i + 1);
			if (hasItemsSources) {
				itemsSourcesArray = itemsSources.split(splitCharacter);
			}
!>
			<tr itemKeyId="<!=itemKeyId!>" class="property_<!=number!>">
				<td class="propertyName">
					<!
						if(allowedNotNull) {
					!>
							<i class="imp">*</i>
					<!
						}
					!>
					<!=itemName!>
				</td>
				<td class="propertyTd form-group <!=(allowedNotNull ? 'notNullTd' : '')!>" data-type="<!=(allowedMultiSelect ? 'checkbox' : 'radio')!>" id="notNull_<!=number!>">
					<div class="form-group <!=(hasItemsSources ? ' ' + labelType : '')!>">
						<!
							if (itemsSourcesArray && (itemsSourcesLength = itemsSourcesArray.length)) {
								for (; m < itemsSourcesLength; m++) {
									var itemsSource = itemsSourcesArray[m];
									if (!m) {
						!>
									<label class="pull-left margin-right-15 checked" for="property_<!=number!>">
											<input type="<!=labelType!>" name="property_<!=number!>" class="propertyValue system" value="<!=itemsSource!>"/>
											<i></i><span><!=itemsSource!></span>
										</label>
						<!
									} else {
						!>
										<label class="pull-left margin-right-15" for="property_<!=number!>">
											<input type="<!=labelType!>" name="property_<!=number!>" class="propertyValue system" value="<!=itemsSource!>"/>
											<i></i><span><!=itemsSource!></span></label>
						<!
									}
								}
							}
						!>
							<!
							if (allowedCustom && itemsSourcesLength) {
						!>
								<label class="pull-left" for="property_<!=number!>">
									<input data-type="custom" type="<!=labelType!>" name="property_<!=number!>" class="propertyValue custom"/>
									<i class="pull-left margin-top-10 margin-right-5"></i>
								</label>
						<!
							}
							if(allowedCustom) {
						!>
							<div class="form-group pull-left">
								<input type="text" class="customInput form-control block"/>
							</div>
						<!
							}
						!>
					</div>
				</td>
				<td>
					<div class="form-group">
						<input type="text" value="<!=orderNumber!>" class="orderInput form-control sm-form-control text-center" name="orderInput" id="order_<!=number!>"/>
					</div>
				</td>
				<td></td>
			</tr>
<!
		}
	}
!>
</script>