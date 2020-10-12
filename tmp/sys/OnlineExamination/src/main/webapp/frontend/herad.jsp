<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="navbar navbar-fixed-top">
		<!---------------- navbar-fixed-top ----------------->
		<div class="backcolor">
			<div class="head_content">
				<div class="head_logo">
					<img src="images/school5.png" />
				</div>
				<div class="title">
					<h1 style="color: #FFF; text-align: center;">在线考试系统</h1>
				</div>
				<div class="pull-right">
					<div class="name">
						<i><img src="images/user.png" />&nbsp;&nbsp;</i><a href="#">${sessionScope.user.userName}</a>
					</div>
					<div class="out">
						&nbsp;&nbsp;&nbsp;&nbsp;<a href="FrontendLoginAction_doLoginOut"> 退出</a>
					</div>
				</div>
			</div>
			<!-- /container -->

		</div>
		<!-- /navbar-inner -->
	</div>
</body>
</html>