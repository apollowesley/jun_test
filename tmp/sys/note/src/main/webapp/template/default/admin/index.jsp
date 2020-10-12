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

<title>后台管理  - ${websiteName}</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${templatePath}/css/index.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
if(top.location.href!=this.location.href)
	{top.location.href=this.location.href;}
	function login() {
		if (document.getElementById("username").value != ""
				&& document.getElementById("password").value != "")
			document.login_form.submit();
	}
</script>
</head>

<body>
	<script type="text/javascript">
	var uoida="<%=session.getAttribute("UOIDA")%>";//返回值为对象，转换成字符串后为'null'(一个内容为null的字符串)
	//alert(uoid);
	if(!(uoida==""||uoida=="null"))
	{
		location.href="<%=basePath%>"+"Admin.yy";
	}
	</script>
<div class="contenter">
  <div class="header">&nbsp;</div>
  <div class="navigate">&nbsp;</div>
  <div class="pagebody"> <img src="${templatePath}/img/backstage2.jpg" alt="记账，就像和朋友对话" title="记账，就像和朋友对话" height="320"/>
    <div class="login_div">
      <form name="login_form" id="login_form" action="Admin.yy" method="post"  onsubmit="login();">
      <div class="login_form_nm">
            <label for="username">用户名：</label>
            <input id="username" name="username" type="text" tabindex="1"/></div>
       <div class="login_form_pwd">
            <label for="password">密码：</label>
            <a class="forget_pwd" href="jsp/forgetPassword.jsp">忘记密码</a>
            <input id="password" name="password" type="password" tabindex="2"/>
          </div>
            <div class="login">
              <input class="login_btn" type="submit" value="登　录" tabindex="3"/>
            </div>
            <div class="register">
              <a href="jsp/register.jsp" >免费注册</a>
            </div>
         <input type="hidden" value="login" name="a"/>
      </form>
    </div>
  </div>
</div>
</body>
</html>
