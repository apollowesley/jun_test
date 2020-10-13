<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="dt" uri="/dictionary"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
	<div class="pageContent">
		<form id="inputForm" method="post" name="inputForm"
			action="admin/user/update"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="id" value="${user.id}" />
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>登录名：</label> <input type="text" size="30"
						value="${user.loginName}" disabled="" maxlength="30" />
				</p>
				<p>
					<label>用户名：</label> <input type="text" id="name" name="name"
						value="${user.name}" class="input-large required" size="30"
						maxlength="30" />
				</p>
				<p>
					<label>密码：</label> <input type="text" id="plainPassword"
						name="plainPassword" class="alphanumeric" minlength="6"
						maxlength="20" placeholder="不改变则留空" size="30"
						onfocus="this.type='password'" />
				</p>
				<p>
					<label>确认密码：</label> <input type="text" id="confirmPassword"
						name="confirmPassword" class="input-large" minlength="6"
						maxlength="20" equalTo="#plainPassword" placeholder="不改变则留空" size="30"
						onfocus="this.type='password'" />
				</p>
				<p>
					<label>邮箱：</label> <input type="text" name="email"
						value="${user.email}" class="email" size="30" maxlength="30" />
				</p>
				<p>
					<label>创建时间：</label> <span class="help-inline"
						style="padding: 5px 0px"><fmt:formatDate
							value="${user.registerDate}" pattern="yyyy年MM月dd日  HH时mm分ss秒" /></span>
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
