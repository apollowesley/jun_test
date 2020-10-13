<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<script>
	jQuery.extend(jQuery.validator.messages, {
		remote : "角色名已存在,请修改",
	});
	$(document).ready(function() {
		var id = $("input[name='id']").val();
		if (null == id || "" == id) {
			id = -100;
		}
		$("#roleName").click(function() {
			$("#roleName").attr("remote", "roleManage/validate/" + id);
		});
	});
</script>
</head>
<body>
	<div class="pageContent">
		<form id="inputForm"  method="post" name="inputForm" action="roleManage/saveInfo"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="id" id="roleId" value="${role.id}" />
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>角色名称：</label> <input name="name" autocomplete="off" id="roleName"
						type="text" class="required" type="text" size="30"
						value="${role.name}"  remote="platform/validate/" maxlength="30"/>
				</p>
				<p>
					<label>角色描述：</label> <input type="text" name="description"
						size="30" value="${role.description}" maxlength="50"/>
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
