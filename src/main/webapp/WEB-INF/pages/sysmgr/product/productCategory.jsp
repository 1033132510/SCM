<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>品类管理</title>
<h2 class="title">
	<a class="first">商品库管理</a><a>>品类管理</a>
</h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
	<div class="container-fluid main-info type-manage">
		<h3 class="head">
			<a>新增品类</a>
		</h3>
		<div class="tree-preview padding-right-20">
			<div class="tree scroll scroll-v">
				<div id="productCategoryTree"></div>
			</div>
            <div class="row" id="mainView">
                <form id="productCategoryForm">
                    <div class="padding-bottom-10 padding-right-10 padding-top-20 clearfix">
                        <div class="form-group short pull-left margin-right-25">
                            <input type="hidden" id="parentCategoryId"/> <input type="hidden" id="categoryInfoForCateId"/>
                            <label>
                                <i class="imp">*</i>父级名称:
                            </label>
                            <input type="text" class="form-control padding-left-font4" id="parentCategoryName" name="parentCategoryName" readonly/>
                        </div>
                        <div class="form-group short pull-left margin-right-25">
                            <label>
                                <i class="imp">*</i>品类ID:
                            </label>
                            <input type="text" class="form-control padding-left-font3" id="categoryInfoForCateCode" name="categoryInfoForCateCode" readonly/>
                        </div>
                        <div class="form-group short pull-left margin-right-25 form-error">
                            <label>
                                <i class="imp">*</i>品类名称:
                            </label>
                            <input type="text" class="form-control padding-left-font4" id="categoryInfoForCateName" name="categoryInfoForCateName"/>
                        </div>
                        <div class="form-group form-select short pull-left">
                            <label><i class="imp">*</i>状态:</label>
                            <select class="form-control select" id="categoryInfoForStatus">
                                <option value="1">有效</option>
                                <option value="0">无效</option>
                            </select>
                        </div>
                    </div>

                    <h4 class="head">
                        <a>品类属性</a>
                    </h4>

                    <div class="scroll scroll-h">
                        <table class="table table-bordered" id="categoryInfoForItemValues">
                            <thead>
                            <tr>
                                <th class="hide">编号</th>
                                <th class="hide">code</th>
                                <th class="hide">canBeChanged</th>
                                <th width="15%">属性名称</th>
                                <th width="15%">属性参数</th>
                                <th width="12%">是否自定义</th>
                                <th width="10%">是否必填</th>
                                <th width="10%">是否多选</th>
                                <th width="15%">是否参与品类检索</th>
                                <th width="15%">属性参数初始显示数量</th>
                                <th width="8%">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </form>
                <div class="text-right margin-top-10">
                    <a href="" class="btn btn-warning btn-xs" id="addProductCategoryItemKey">添加</a>
                </div>
                <div class="text-center margin-bottom-30 padding-bottom-10">
                    <a href="" class="btn btn-info" id="saveOrUpdateProductCategory">保存</a>
                </div>
            </div>
		</div>
	</div>
</div>
<script id="tempHtmlForInit" type="text/html">
       <tr>
	        <th class="hide"><!=id!></th>
                <td class="hide"><input type="text" placeholder="" class="form-control" name="itemCode" value="<!=itemCode!>" id="<!=itemCode_id!>" /></td>
                <td class="hide"><input type="text" placeholder="" class="form-control" name="canBeChanged" value="<!=canBeChanged!>" /></td> 
				<td><div class="form-group form-inline"><input type="text"  placeholder="属性名称不超过15个字" class="form-control" name="itemName" value="<!=itemName!>" id="<!=itemName_id!>" /></div></td>
				<td><div class="form-group form-inline"><input type="text"  placeholder="多参数时用“，”隔开" class="form-control" name="itemsSources"  value="<!=itemsSources!>" id="<!=itemsSources_id!>"  /></div></td>
				<td>
					<div class="form-group radio">
						<label class="<!if(("undefined" != typeof allowedCustom) && allowedCustom){!>checked<!}else{!><!}!>"> 
                        <input type="radio" name="allowedCustom" value='1' checked><i></i><span>是</span>
						</label> 
                        <label class="<!if((("undefined" != typeof allowedCustom) && !allowedCustom)||"undefined" == typeof allowedCustom){!>checked<!}else{!><!}!>">
                            <input type="radio" name="allowedCustom" value='0'><i></i><span>否</span>
						</label>
					</div>
				</td>
				<td>
					<div class="form-group radio">
                        <label class="<!if(("undefined" != typeof allowedNotNull) && allowedNotNull){!>checked<!}else{!><!}!>"> 
                       <input type="radio" name="allowedNotNull" value='1' checked><i></i><span>是</span>
						</label> 
                               <label class="<!if((("undefined" != typeof allowedNotNull) && !allowedNotNull)||"undefined" == typeof allowedNotNull){!>checked<!}else{!><!}!>">
                              <input type="radio" name="allowedNotNull" value='0'><i></i><span>否</span>
						</label>
					</div>
				</td>
				<td>
			    <div class="form-group radio">
                        <label class="<!if(("undefined" != typeof allowedMultiSelect) && allowedMultiSelect){!>checked<!}else{!><!}!>"> 
                        <input type="radio" name="allowedMultiSelect" value='1' checked><i></i><span>是</span>
						</label> 
                               <label class="<!if((("undefined" != typeof allowedMultiSelect) && !allowedMultiSelect)||"undefined" == typeof allowedMultiSelect){!>checked<!}else{!><!}!>">
                              <input type="radio" name="allowedMultiSelect" value='0'><i></i><span>否</span>
						</label>
					</div>
				</td>
				<td>
					<div class="form-group radio">
					  <label class="<!if(("undefined" != typeof allowedCateFilter) && allowedCateFilter){!>checked<!}else{!><!}!>">  
                                     <input type="radio" name="allowedCateFilter" value='1' checked><i></i><span>是</span>
						</label> 
                                    <label class="<!if((("undefined" != typeof allowedCateFilter) && !allowedCateFilter)||"undefined" == typeof allowedCateFilter){!>checked<!}else{!><!}!>">
                                    <input type="radio" name="allowedCateFilter" value='0'><i></i><span>否</span>
						</label>
					</div>
				</td>
				<td><div class="form-group"><input type="text" placeholder="5" class="form-control sm-form-control text-center"
					name="defaultShowNumber" value="<!=defaultShowNumber!>" id="<!=defaultShowNumber_id!>"/></div></td>
				<td><a class="red" onclick='del(this);'>删除</a></td>
			</tr>
</script>
<script type="text/javascript" src="${ctx }/resources/script/plugins/ztree/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctx}/resources/script/plugins/ztree/ZTreeUtils.js"></script>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/product/productCategory.js"></script>
