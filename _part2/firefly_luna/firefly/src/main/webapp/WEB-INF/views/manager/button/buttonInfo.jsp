<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<body>
	<div class="pageContent">
		<form id="inputForm" method="post" name="inputForm"
			action="sysButton/saveButton"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name=buttonElderCode
				value="${button.buttonCode}" /> <input type="hidden" name=buttonId
				value="${button.buttonId}" />
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>按钮名称：</label> <input id="buttonName" name="buttonName"
						type="text" size="30" value="${button.buttonName}"
						class="required" maxlength="30" />
				</p>
				<p>
					<label>按钮代码：</label> <input type="text" name="buttonCode"
						id="buttonCode" size="30" value="${button.buttonCode}"
						class="required" maxlength="50"
						<c:if test="${button != null}"> readonly=readonly</c:if> />
				</p>
				<p>
					<label>描述：</label> <input type="text" name="buttonDesc" size="30"
						maxlength="80" value="${button.buttonDesc}" />
				</p>
				<p>
					<label>所属菜单：</label> <select name="menu.id" class="combox required"
						remoteUrl="sysButton/getMenuList" defalutOption=false
						defaultVal="${button.menu.id}">
					</select>
				</p>

			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
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
