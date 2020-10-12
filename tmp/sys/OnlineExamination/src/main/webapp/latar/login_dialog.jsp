<%@ page language="java" pageEncoding="UTF-8"%>
<div class="pageContent">

	<form method="post" action="LoginAction_timeOutDologin"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div class="pageFormContent " layoutH="58">
			<div class="unit">
				<label>用户名：</label> <input type="text" name="name"
					value="${model.name}" class="required" />
			</div>
			<div class="unit">
				<label>密码：</label> <input type="password" name="password" size="20"
					class="login_input required" required />
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">提交</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>

</div>

