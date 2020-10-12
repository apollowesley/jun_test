<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>修改密码</title>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/font-awesome.min.css" />
<link rel="stylesheet" href="css/changepass.css" />
<link rel="stylesheet" href="css/reset.css" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/md5-min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#menu a").removeClass("active");
		$("#changepass").addClass("active");
	});
	function checkPass() {
		var oldpass = "${sessionScope.user.password}";
		var password = $("#password").val();
		password = md5(password);
		if (oldpass == password) {
			$("#passImg").html("<img src='images/right.png' />")
			return true;
		} else {
			$("#passImg").html("<img src='images/error.png' />")
			return false;
		}

	}
</script>
<body>
	<jsp:include page="herad.jsp" />
	<div id="container">
		<!----------------  container  ----------------->
		<div class="content_body">
			<jsp:include page="left.jsp" />
			<!-- content_left 结束 -->
			<div class="content_right fr">
				<div class="content_head">
					<div class="breadcrumb">
						个人中心<span>></span><a href="FrontendUserAction_toChangepassPage">修改密码</a>
					</div>
				</div>
				<form id="passForm" name="passForm" method="post">
					<div class="a_content">
						<div class="text_content">
							<div class="biaoge">
								<div class="input-prepend">
									<span class="add-on">原密码</span> <input class="span3"
										name="password" id="password" ;
										id="password"
										type="password" placeholder="输入原始密码"  pattern="^[A-Za-z0-9]+$" placeholder="请输入密码(只能是数字和英文)" oninvalid="setCustomValidity('只能输入数字和英文')" oninput="setCustomValidity('') onblur="checkPass()">
									<span id="passImg"> </span>
								</div>

								<div class="input-prepend">
									<span class="add-on">新密码</span> <input class="span3"
										name="newpass1" id="newpass1" type="password"
										placeholder="输入新密码"  required="required"> 
								</div>
								<div class="input-prepend">
									<span class="add-on">新密码</span> <input class="span3"
										name="newpass2" id="newpass2" type="password"
										placeholder="再次输入新密码"  > 
								</div>
								<div id="btn-div">

									<a href="#" onclick="fromSumbit()" class="btn btn-info"
										id="alterPwd">修改</a>
								</div>
							</div>


						</div>
						<!-- text_content 结束-->

					</div>
				</form>
				<!-- a_content 结束-->
				<!-------  修改成功弹出对话框  ------->
				<div class="modal hide fade" id="change">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4>提示</h4>
					</div>
					<div class="modal-body" id="message">密码修改成功！</div>
					<div class="modal-footer">
						<a id="rightBtn" class="btn btn-primary" data-dismiss="modal">确定</a>

					</div>
				</div>
			</div>
			<!------- 修改成功弹出对话框  结束 ------->
		</div>
		<!-- content_right 结束 -->
	</div>
	<!-- content_body 结束 -->
	<div id="l-extra">
		<!------ extra ----->
		<p>Copyright © 2014 成都工业学院</p>
	</div>
	<!-- /l-extra -->

	</div>
	<!-- /container 结束-->
	<script type="text/javascript">
		function fromSumbit() {
			if (checkPass()) {
				data = $("#passForm").serialize();
				$.ajax({
					type : "POST",
					async : false,
					url : "FrontendUserAction_doChangepass",
					data : data,
					dataType : "json",
					success : function(msg) {
						$("#message").text(msg.message);
						$('#change').modal('show');
					}
				});
			}
		}
	</script>
</body>
</html>