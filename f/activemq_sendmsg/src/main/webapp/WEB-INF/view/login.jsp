<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shiro权限认证登录中心</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/camera.css"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-responsive.min.css"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/matrix-login.css"/>" />
<link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet" />

<script src="<c:url value="/resources/js/jquery-1.7.2.js"/>" type="text/javascript"></script>
<script type="text/javascript">
	
	//是否开启验证码登录
	var openVerifyCode = true;
	
	//是否开启ajax登录
	var openAjaxLogin = true;
	
	
	$(document).ready(function() {
		if(!openVerifyCode){
			$(".form-actions div:gt(0)").css("display","none");
		}
		$("#openVerifyCode").val(openVerifyCode);
		
		//显示提示信息
		$("#tipDiv").tips({
			side : 1,
			msg : $("#accountDiv").html(),
			bg : '#68B500',
			time : 0
		});
		
		//手动调整提示信息宽度
		$(".jq_tips_info").attr("style","color: rgb(255, 255, 255); background-color: rgb(104, 181, 0);max-width:250px;margin-top:20px");
	});
</script>
</head>
<body>
<div style="width:100%;text-align: center;margin: 0 auto;position: absolute;">
	<div id="loginbox">
		<form action="<c:url value="/login/login"/>" name="loginForm" method="post" id="loginForm">
			<!-- 是否使用验证码验证 -->
			<input type="hidden" name="openVerifyCode" value="true" id="openVerifyCode"/>
			<div class="control-group normal_text">
				<h3>
					<img src="<c:url value="/resources/images/login/logo.png"/>" alt="Logo" />
				</h3>
			</div>
			
			<!-- 登录错误提示 -->
			<span style="font-weight: bold;color: red;">${loginErrorMsg}</span>
			
			
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_lg"><i><img height="37" src="<c:url value="/resources/images/login/user.png"/>" /></i></span>
						<input type="text" name="userName" id="userName" value="" placeholder="请输入用户名" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_ly"><i><img height="37" src="<c:url value="/resources/images/login/suo.png"/>" /></i></span>
						<input type="password" name="password" id="password" placeholder="请输入密码" value="" />
					</div>
				</div>
			</div>
			<div style="float:right;padding-right:10%;">
				<div style="float: left;margin-top:3px;margin-right:2px;">
					<font color="white">记住密码</font>
				</div>
				<div style="float: left;">
					<input name="form-field-checkbox" id="saveid" type="checkbox" onclick="savePaw();" style="padding-top:0px;" />
				</div>
			</div>
			<div class="form-actions">
				<div style="width:86%;padding-left:8%;">

					<div style="float: left;">
						<i><img src="<c:url value="/resources/images/login/yan.png"/>" /></i>
					</div>
					<div style="float: left;" class="codediv">
						<input type="text" name="code" id="code" class="login_code" style="height:16px; padding-top:0px;" />
					</div>
					<div style="float: left;">
						<i><img style="height:22px;" id="codeImg" alt="点击更换" title="点击更换" src="" /></i>
					</div>

					<span class="pull-right" style="padding-right:3%;"><a href="javascript:quxiao();" class="btn btn-success">重置</a></span>
					<span class="pull-right"><a onclick="severCheck();" class="flip-link btn btn-info" id="to-recover">登录</a></span>

				</div>
			</div>
		</form>
		
		<!-- 提示信息 -->
		<div style="float: right;margin-right: -20px;margin-top: 50px;" id="tipDiv"></div>
		<div style="margin-bottom:15px;display: none;" id="accountDiv">
		    <span>提供3个测试账户，密码都是 123456：</span><br/>
		    <span>admin</span><br/>
		    <span>user</span><br/>
		    <span>caiwu</span><br/>
		    
		    <br/>
		    <br/>
		    <span>账户说明：</span><br/>
		    <span><br/>每个账户对应的角色都不同，拥有的权限也不同。<br/>登录成功后，页面会显示所拥有的权限(也可以理解为：拥有相应的权限的用户才可以看的到)。<br/>具体的请看 home.jsp下面的权限控制内容。</span><br/>
		</div>

		<div class="controls" style="margin-top: 20px;">
			<div class="main_input_box">
				<font color="white"><span id="nameerr" style="color: white;font-weight: bold;">JAVA免费项目资源下载<a target="_blank" style="color: white;font-weight: bold;" href="http://www.2b2b92b.com"> www.2b2b92b.com</a></span></font>
			</div>
		</div>
	</div>
</div>
<div id="templatemo_banner_slide" class="container_wapper">
	<div class="camera_wrap camera_emboss" id="camera_slide">
		<div data-src="<c:url value="/resources/images/login/banner_slide_01.jpg"/>"></div>
		<div data-src="<c:url value="/resources/images/login/banner_slide_02.jpg"/>"></div>
		<div data-src="<c:url value="/resources/images/login/banner_slide_03.jpg"/>"></div>
	</div>
	<!-- #camera_wrap_3 -->
</div>


<script type="text/javascript">
	//服务器校验
	function severCheck(){
		if(check()){
			//form表单提交登录
			if(!openAjaxLogin){
				$("#loginForm").submit();
			}
			
			//使用ajax登录
			if(openAjaxLogin){
				var userName = $("#userName").val();
				var password = $("#password").val();
				var code = $("#code").val();
				
				$.ajax({
					type: "post",
					url: "<c:url value='/login/login'/>",
			    	data: {"code" : code, "userName" : userName, "password" : password , "openAjaxLogin" : openAjaxLogin,"openVerifyCode" : $("#openVerifyCode").val()},
					dataType:'json',
					cache: false,
					success: function(data){
						if(200 == data.code){
							window.location.href="<c:url value='/index/home'/>";
						}else if("usererror" == data.msg){
							changeCode();
							$("#userName").tips({
								side : 1,
								msg : "用户名或密码有误",
								bg : '#FF5080',
								time : 15
							});
							$("#userName").focus();
						}else if("codeerror" == data.msg){
							changeCode();
							$("#code").tips({
								side : 1,
								msg : "验证码输入有误",
								bg : '#FF5080',
								time : 15
							});
							$("#code").focus();
						}else{
							changeCode();
							$("#userName").tips({
								side : 1,
								msg : data.msg,
								bg : '#FF5080',
								time : 15
							});
							$("#userName").focus();
						}
					}
				});
			}
		}
	}

	$(document).ready(function() {
		changeCode();
		$("#codeImg").bind("click", changeCode);
	});

	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			$("#to-recover").trigger("click");
		}
	});

	function genTimestamp() {
		var time = new Date();
		return time.getTime();
	}

	function changeCode() {
		$("#codeImg").attr("src", "<c:url value='/verifyCode?t=" + genTimestamp() +  "'/>");
	}

	//客户端校验
	function check() {

		if ($("#userName").val() == "") {

			$("#userName").tips({
				side : 2,
				msg : '用户名不得为空',
				bg : '#AE81FF',
				time : 3
			});

			$("#userName").focus();
			return false;
		} else {
			$("#userName").val(jQuery.trim($('#userName').val()));
		}

		if ($("#password").val() == "") {

			$("#password").tips({
				side : 2,
				msg : '密码不得为空',
				bg : '#AE81FF',
				time : 3
			});

			$("#password").focus();
			return false;
		}
		
		if(openVerifyCode){
			if ($("#code").val() == "") {
				$("#code").tips({
					side : 1,
					msg : '验证码不得为空',
					bg : '#AE81FF',
					time : 3
				});

				$("#code").focus();
				return false;
			}
		}

		$("#loginbox").tips({
			side : 1,
			msg : '正在登录 , 请稍后 ...',
			bg : '#68B500',
			time : 10
		});

		return true;
	}

	function quxiao() {
		$("#userName").val('');
		$("#password").val('');
	}
</script>
<script>
	if (window != top) {
		top.location.href = location.href;
	}
</script>

<script src="<c:url value="/resources/js/login/jquery.easing.1.3.js"/>"></script>
<script src="<c:url value="/resources/js/login/camera.min.js"/>"></script>
<script src="<c:url value="/resources/js/login/templatemo_script.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.tips.js"/>"></script>

</body>
</html>