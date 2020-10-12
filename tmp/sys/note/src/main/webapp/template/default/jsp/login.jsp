<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">

<title>登录 - ${websiteName}</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
<link href="${templatePath}/css/index.css" rel="stylesheet" type="text/css"/>
<script src="${templatePath}/js/md5.js"></script>
<script type="text/javascript">
	function login() {
		if (document.getElementById("username").value != "" && document.getElementById("password_clear").value != "") {
			document.getElementById("password").value = md5(document.getElementById("username").value+md5(document.getElementById("password_clear").value));
			document.login_form.submit();
		}
	}
</script>
</head>

<body>
	<script type="text/javascript">
	var uoid="<%=session.getAttribute("UOID")%>";//返回值为对象，转换成字符串后为'null'(一个内容为null的字符串)
	//alert(uoid);
	if(!(uoid==""||uoid=="null"))
	{
		location.href="<%=basePath%>"+"Bills.yy";
	}
	</script>
<%@include file="global_topbar.jsp" %>
<div class="contenter">
  <div class="header">&nbsp;</div>
  <div class="navigate">&nbsp;</div>
  <div class="pagebody"> <img src="${templatePath}/img/login1.jpg" alt="生活，其实可以很快乐" title="生活，其实可以很快乐" height="320" style="float:left"/>
    <div class="login_div">
      <form name="login_form" id="login_form" action="Login.yy" method="post"  onsubmit="login();">
      <div class="login_form_nm">
            <label for="username">用户名：</label>
            <input id="username" name="username" type="text" tabindex="1"/></div>
       <div class="login_form_pwd">
            <label for="password_clear">密码：</label>
            <a class="forget_pwd" href="${templatePath}/jsp/forgetPassword.jsp">忘记密码</a>
            <input id="password_clear" type="password" tabindex="2"/>
            <input id="password" name="password" type="hidden"/>
          </div>
          <div class="login_form_remember">
            <label><input type="checkbox" name="rememberId" value="1"/>一个周期内不用登录</label></div>
            <div class="login">
              <input class="login_btn" type="submit" value="登　录" tabindex="3"/>
            </div>
            <div class="register">
              <a href="${templatePath}/jsp/register.jsp" >免费注册</a>
            </div>
      </form>
    </div>
  </div>
</div>
<%@include file="global_footer.jsp" %>
<%@include file="verifycookies.jsp" %>
</body>
</html>
