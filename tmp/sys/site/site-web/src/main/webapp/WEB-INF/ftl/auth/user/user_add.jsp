<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<title>用户新增</title>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx}/static/js/auth/user/user_add.js?${version_js}" type="text/javascript"></script>
<script type="text/javascript">
  var _ctx = '${ctx}';
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<form:form action="${ctx }/user/add" id="userCommand" method="post" class="form-horizontal">
				<div class="form-group">
					<label for="title" class="col-sm-2 control-label">登录账号：</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="loginName" id="loginName" />
					</div>
					<form:errors cssClass="label label-danger" path="loginName"></form:errors>
				</div>
				<div class="form-group">
					<label for="description" class="col-sm-2 control-label">邮箱：</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="email" id="email" />
					</div>
					<form:errors cssClass="label label-danger" path="email"></form:errors>
				</div>
				<div class="form-group">
					<label for="address" class="col-sm-2 control-label">用户名：</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="userName" id="userName" />
					</div>
					<form:errors cssClass="label label-danger" path="userName"></form:errors>
				</div>
				<div class="form-group">
					<label for="address" class="col-sm-2 control-label">密码：</label>
					<div class="col-sm-4">
						<input type="password" class="form-control" name="password" id="password" />
					</div>
					<form:errors cssClass="label label-danger" path="password"></form:errors>
				</div>
				<div class="form-group">
					<label for="address" class="col-sm-2 control-label">确认密码：</label>
					<div class="col-sm-4">
						<input type="password" class="form-control" name="confirmPassword" id="confirmPassword" />
					</div>
					<form:errors cssClass="label label-danger" path="confirmPassword"></form:errors>
				</div>


				<div class="form-group input-group-sm">
					<label class="col-sm-2 control-label">&nbsp;</label>
					<div class="col-sm-2">
						<button class="btn btn-default" type="button" id="saveBtn">保存</button>
						<button class="btn btn-default" type="reset">取消</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>
