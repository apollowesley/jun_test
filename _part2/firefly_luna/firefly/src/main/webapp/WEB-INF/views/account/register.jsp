<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
	<div class="pageContent">
		<form id="inputForm" method="post" action="register/save" class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<div class="pageFormContent" layoutH="56">
				<!-- 所属平台 -->
				<input  name="platformId" type="hidden"
						 size="30" maxlength="30" value=""/>
				<p>
					<label>用户名：</label> <input autocomplete="off" type="text" name="name"
						class="input-large required" size="30" maxlength="30"/>
						
				</p>
				<p>
					<label>邮箱：</label> <input autocomplete="off" type="text" name="email"
						class="email" size="30" maxlength="30"/>
						
				</p>
				<p>
					<label>登录名：</label> <input autocomplete="off" type="text" name="loginName"
						class="required" minlength="3" size="30" maxlength="30"/>
				</p>
				<p>
					<label>密码:</label> <input autocomplete="off" type="text" id="plainPassword"
						name="plainPassword" class="required alphanumeric" minlength="6" onfocus="this.type='password'"
						maxlength="20" size="30" />
				</p>
				<p>
					<label>确认密码:</label> <input  type="text" id="confirmPassword"
						name="confirmPassword" class="required" equalTo="#plainPassword" onfocus="this.type='password'"
						size="30" minlength="6" maxlength="20"/>
				</p>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" onclick="openAjaxLoading()">保存</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close" onclick="openAjaxLoading()">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<script>
				function openAjaxLoading(){
					$.ajaxSettings.global=true;
				}
				$(document).ready(function() {
					//聚焦第一个输入框
					$("#loginName",$.pdialog.getCurrent()).focus();
					//为inputForm注册validate函数
					$.ajaxSettings.global=false;
					$("#inputForm",$.pdialog.getCurrent()).validate({
						rules : {
							loginName : {
								remote : "register/checkLoginName"
							}
						},
						messages : {
							loginName : {
								remote : "用户登录名已存在"
							}
						}
					});
				
				});
			</script>
		</form>
	</div>
</body>
</html>
