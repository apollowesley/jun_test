<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 
</head>
<body>
	<div class="pageContent">
		<form id="inputForm"  method="post" name="inputForm" action="systemConfig/saveSystemConfig"
			onsubmit="return validateCallback(this,dialogAjaxDone);">
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>系统参数编号：</label> <input type="text" name="configId"
						size="30" value="${systemConfig.configId}" readOnly="readonly" />
				</p>
				<p>
					<label>系统参数名称：</label> <input id="configName" name="configName" type="text"
						size="30" value="${systemConfig.configName}"    />
				</p>
				<p>
					<label>系统参数描述：</label> <input type="text" name="configDesc"
						size="30" value="${systemConfig.configDesc}"  />
				</p>
				<p>
					<label>系统参数值：</label> <input type="text" name="configValue" size="30"
						value="${systemConfig.configValue}" maxlength="200"/>
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
