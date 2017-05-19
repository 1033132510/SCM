var productBt = baidu.template;
var productULId = 'productULId';
var productTemplate = {
		initByOrgId:function(divId,orgId,isClear){
			var that = this;
			if(!(divId && orgId)){
				return;
			}
			if(isClear){
				$('#'+divId).html('');
			}
			var productList = that.getProductListByOrg(orgId);
			that.showProductTemplate(divId,productList);

		},
		getProductListByOrg:function(orgId){
			var productList = [];
			$.ajax({
				url : ctx + '/shop/product/searchBySupplierOrg',
				contentType:"application/x-www-form-urlencoded;charset=utf-8",
				type : 'get',
				data : {
					supplierOrgId:orgId,
					pageSize:8,
					curPage:1,
					status:1,
					t:new Date().valueOf()
				},
				async : false,
				success : function(data) {
					if (data == 'fail') {
						zcal({
							type:"error",
							title:"加载数据失败"
						});
						return;
					} else {
						productList = data.data;
						
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					zcal({
						type:"error",
						title:"加载数据失败"
					});
				}
			});
			return productList;
		},
		showProductTemplate:function(divId,productList){
			var that = this;
			$('#'+divId).html('');
			var currentId = productULId;
			//初始化UL
			that.showProductULInit(divId,currentId);
			//第一个上面在ul下面
			for(var i=0;i<productList.length;i++){
				// lidivId 下一个商品显示在liDivId的下一个
				var product = productList[i];
				var currentLiId = product.productId+'_li'+new Date().valueOf();
				//初始化UL 中得lI
				 that.showProductLIInit(productULId,currentLiId);
				//图片显示
				if(product.productImages.length>0){
					var currentId = product.productImages[0].id+'_firstImagesDiv'+new Date().valueOf();
					that.showProductFirstImage(currentLiId,currentId,product.productImages[0],product);
					currentId = product.productImages[0].id+'_imagesDiv'+new Date().valueOf();
					that.showProductImages(currentLiId,currentId,product.productImages);
				}else{
					var currentId = '_firstImagesDiv'+new Date().valueOf();
					that.showProductFirstImage(currentLiId,currentId,'',product);
					currentId ='_imagesDiv'+new Date().valueOf();
					that.showProductImages(currentLiId,currentId,'');
				}
				
				var price = 0;
				if(product.productPrices.length>=2){
					price = utils.toFixed(product.productPrices[0].actuallyPrice);
				}
				var txtItem = {
					productName : product.productName,
					category:product.category,
					productId:product.productId,
					price:price,
					hasTax:product.hasTax,
					hasTransportation:product.hasTransportation,
					customData : product.customData,
					status : product.status
				};
				//循环代码
				that.showProductTxt(currentLiId,txtItem);
			
			} 
			//   绑定事件
			that.bindEvent();
		},
		showProductULInit:function(divId,currentId){
			var hh = $('#'+divId);
			var item = {
					currentId:currentId
			};
			var html = productBt('tempHtmlProductULInit',item);
			hh.append(html);
		},
		showProductLIInit:function(frontDivId,currentId){
			var hh = $('#'+frontDivId);
			var item = {
					currentId:currentId
			};
			var html = productBt('tempHtmlProductLIInit',item);
			hh.append(html);
		},
		
		showProductFirstImage:function(frontDivId,currentId,image,product){
			var hh = $('#'+frontDivId);
			var item = {
					image:image,
					currentId:currentId,
					productId:product.productId,
					category:product.category
			};
			var html = productBt('tempHtmlProductFirstImageInit',item);
			hh.append(html);
			$("img.lazy").lazyload({
				effect: "fadeIn",
				threshold: 200,
			});
		},
		showProductImages:function(frontDivId,currentId,images){
			var hh = $('#'+frontDivId);
			var item = {
					images:images,
					currentId:currentId
			};
			var html = productBt('tempHtmlProductImagesInit',item);
			hh.append(html);
			$("img.lazy").lazyload({
				effect: "fadeIn",
				threshold: 200,
			});
		},
		showProductTxt:function(frontDivId,item){
			var hh = $('#'+frontDivId);
			this.showProductName(hh, item);
			this.showProductStatus(hh, item);
		},
		showProductName : function(hh, item) {
			hh.append(productBt('tempHtmlProductNameTxtInit', item));
		},
		showProductStatus : function(hh, item) {
			var html = productBt('tempHtmlProductTxtInit',item);
			if(item.status == 1) {
				html += productBt('tempHtmlProductPriceTxtInit', item);
			}else if(item.status == 0) {
				html += productBt('tempHtmlDisabledProductTxtInit', {});
			}
			hh.append(html);
		},
 		bindEvent:function(){
			$("a[id$='_product'],a[id$='_bigImage'],a[id$='_price']").bind('click',function(){
				window.open(ctx + '/shop/product/gotoView?productId='+$(this).attr("selfId")+'&cateId='+$(this).attr("categoryId")+"&t="+new Date().valueOf());
			});
		}
};
