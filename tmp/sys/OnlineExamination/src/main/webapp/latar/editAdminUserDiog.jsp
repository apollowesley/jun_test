<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//labelD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.labeld">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑管理员</title>
</head>
<script type="text/javascript">
	function resetPassword() {
		$("#password").val("123456");
	}
</script>
<body>

	<div class="pageContent">
		<!-- 	 -->
		<s:set var="haveRight"
			value="@com.evil.util.ValidateUtil@hasRight('/AdminUserAction_doSaveOrUpdateAdminUser')" />
		<form method="post"
			action="${haveRight?'AdminUserAction_doSaveOrUpdateAdminUser':'#' }"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,dialogAjaxDone)">
			<div class="pageFormContent nowrap" layoutH="56">
				<!-- 		NAME,parentId,url,ORDER,content -->
				<c:if test="${model!=null&&model.id!=null }">
					<p>
						<label>用户id：</label> <input type="text" name="id"
							value="${model.id }" readonly="readonly" style="width: 200px" />
					</p>
				</c:if>
				<p>
					<label>用户名：</label> <input type="text" name="name"
						value="${model.name}" style="width: 200px" class="required" />
				</p>
				<s:if test="model==null||model.id==null">
					<p>
						<label>密码：</label> <input id="password" type="password"
							name="password" class="required alphanumeric" minlength="6"
							maxlength="20" style="width: 200px" />
					</p>
					<p>
						<label>确认密码：</label> <input type="password" name="repassword"
							class="required" equalto="#password" style="width: 200px" />
					</p>
				</s:if>
				<s:else>
					<p>
						<label>密码：</label> <input id="password" type="password"
							name="password" value="${password }" style="width: 200px"
							readonly="readonly" /> <input type="button" value="重置"
							onclick="resetPassword()">
					</p>
				</s:else>
			</div>
			<div class="formBar">
				<ul>
					<li><s:if test="#haveRight">
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">提交</button>
								</div>
							</div>
						</s:if> <s:else>
							<div class="buttonDisabled">
								<div class="buttonContent">
									<button type="button">提交</button>
								</div>
							</div>
						</s:else></li>
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