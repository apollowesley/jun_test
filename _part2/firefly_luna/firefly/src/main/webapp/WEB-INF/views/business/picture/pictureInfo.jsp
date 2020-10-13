<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<head>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div class="pageContent">
		<form id="inputForm" method="post" name="inputForm"
			action="pic/savePicture"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="id" value="${pic.id }" />
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>图片名称：</label> <input name="name" type="text"
						style="width: 240px;" class="required" maxlength="30"
						value="${pic.name}" />
				</p>
				<p>
					<label>网络图片地址：</label> <input name="source" type="text"
						class="required" style="width: 240px;" maxlength="512"
						value="${picture.picUrl}" />
				</p>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" id="add_submitId">保存</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>
