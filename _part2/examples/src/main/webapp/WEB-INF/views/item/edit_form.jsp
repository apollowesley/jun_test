<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<input type="hidden" name="id" value="${item.id }" />
<div class="form-group">
	<label class="col-sm-4 control-label" for="goodsName">商品名称 <span class="text-danger">*</span></label>
	<div class="col-sm-8">
		<input type="text" id="goodsName" name="goodsName" value="${item.goodsName }" required class="form-control">
	</div>
</div>
<div class="form-group">
	<label class="col-sm-4 control-label" for="brandName">品牌名称 <span class="text-danger">*</span></label>
	<div class="col-sm-8">
		<input type="text" id="brandName" name="brandName" value="${item.brandName }" required class="form-control">
	</div>
</div>
<div class="form-group">
	<label class="col-sm-4 control-label" for="word">促销语 <span class="text-danger">*</span></label>
	<div class="col-sm-8">
		<input type="text" id="word" name="word" value="${item.word }" required class="form-control">
	</div>
</div>
<div class="form-group">
	<label class="col-sm-4 control-label" for="gkey">关键字 <span class="text-danger">*</span></label>
	<div class="col-sm-8">
		<input type="text" id="gkey" name="gkey" value="${item.gkey }" required class="form-control">
	</div>
</div>
<div class="form-group">
	<label class="col-sm-4 control-label" for="createTime">创建时间 <span class="text-danger">*</span></label>
	<div class="col-sm-8">
		<input type="text" id="createTime" name="createTime" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm',firstDayOfWeek:1,readOnly:true})" value='<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd hh:mm" />' class="form-control Wdate" required>
	</div>
</div>
<div class="form-group m-t-sm">
	<div class="col-sm-6 col-sm-push-4">
		<button class="btn btn-md btn-primary " type="submit">
			<strong>保存，重新发布</strong>
		</button>
		<button type="button" class="btn btn-white m-l-sm" data-dismiss="modal">取消</button>
	</div>
</div>
