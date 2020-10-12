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
<title>个人信息</title>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/font-awesome.min.css" />
<link rel="stylesheet" href="css/changepass.css" />
<link rel="stylesheet" href="css/reset.css" />
</head>
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
						个人中心<span>></span><a href="userinfo.html">个人信息</a>
					</div>
				</div>
				<form id="userForm" name="userForm" method="post">
					<div class="a_content">
						<div class="text_content">
							<div class="biaoge">
								<div class="input-prepend">
									<span class="add-on">姓名</span> <input id="title" type="text"
										name="userName" disabled="true" class="span4"
										value="${sessionScope.user.userName }" required>

								</div>

								<div class="input-prepend">
									<span class="add-on">年龄</span> <input id="contents" type="text"
										name="userAge" name="textsug" disabled="true" class="span4"
										value="${sessionScope.user.userAge }" required>

								</div>
								<div class="input-prepend">
									. <span class="add-on">单位</span> <input id="ndate" type="text"
										name="userUnit" disabled="true" class="span4"
										value="${sessionScope.user.userUnit }" required>

								</div>

								<div class="changebtn">
									<input type="button" id="change" class="btn btn-primary margin"
										value="点击修改" /> <input type="button" id="save"
										onclick="fromSumbit()" class="btn btn-primary margin"
										value="保存修改" /> <input type="reset" id="reset"
										class="btn btn-danger margin" value="取消" />
								</div>
							</div>


						</div>
						<!-- text_content 结束-->

					</div>
				</form>
				<!-- a_content 结束-->
				<!-------  修改成功弹出对话框  ------->
				<div class="modal hide fade" id="resultDialog">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4>提示</h4>
					</div>
					<div class="modal-body" id="message">密码修改成功！</div>
					<div class="modal-footer">
						<a class="btn btn-primary" data-dismiss="modal" onclick="window.location.reload();" >确定</a>

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

	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script>
		$(document).ready(function() {
			$("#menu a").removeClass("active");
			$("#userInfo").addClass("active");
		});
		$(function() {
			$('#save').css('display', 'none')

			$('#change').click(function(e) {
				$(this).css('display', 'none')
				$('#save').css('display', "inline-block") /*只能是inline-block  block会占一行*/

				$('#news_id').attr("disabled", false)
				$('#title').attr("disabled", false)
				$('#contents').attr("disabled", false)
				$('#ndate').attr("disabled", false)

				$('#save').click(function(e) {
					$(this).css('display', 'none')
					$('#change').css('display', 'inline-block')

					$('#news_id').attr("disabled", true)
					$('#title').attr("disabled", true)
					$('#contents').attr("disabled", true)
					$('#ndate').attr("disabled", true)
				});
			});
		})
		function fromSumbit() {
			data = $("#userForm").serialize();
			$.ajax({
				type : "POST",
				async : false,
				url : "FrontendUserAction_doUpdateUserinfo",
				data : data,
				dataType : "json",
				success : function(msg) {
					$("#message").text(msg);
					$('#resultDialog').modal('show');
				}
			});
		}
	</script>
</body>
</html>