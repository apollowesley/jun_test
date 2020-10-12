<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>注册 - ${websiteName}</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${templatePath}/js/regAjax.js"></script>
<script type="text/javascript" src="${templatePath}/js/reglegal.js"></script>
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
<link href="${templatePath}/css/register.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<%@ include file="./../html/topbar.html"%>
<div class="contenter">
  <div class="header"></div>
  <div class="navigate">&nbsp;</div>
  <div class="pagebody"> <img class="reg_img1" src="${templatePath}/img/login1.jpg" alt="记账，就像和朋友对话" title="记账，就像和朋友对话" height="320"/>
    <div class="register_div">
      <form name="register_form" id="register_form" action="Register.yy" method="post">
        <div class="register_form_nm msg_weak">
          <span class="reg_label"><label for="username">用户名：</label></span>
          <input id="username" name="username" type="text"/>
          <i></i>
          <div id="username_msg" class="msg_con"></div>
          </div>
        <div class="register_form_pwd msg_weak">
          <span class="reg_label"><label for="password">密码：</label></span>
          <input id="password" name="password" type="password" />
          <i></i>
          <div id="password_msg" class="msg_con"></div>
        </div>
        <div class="register_form_pwd2 msg_weak">
          <span class="reg_label"><label for="password2">确认密码：</label></span>
          <input id="password2" name="password2" type="password" />
          <i></i>
          <div id="password2_msg" class="msg_con"></div>
        </div>
        
        <div class="register_form_em msg_weak">
      <span class="reg_label"><label for="email">邮箱：</label></span>
      <input id="email" name="email" type="text"/>
          <i></i>
          <div id="email_msg" class="msg_con"></div>
      </div>
      <div class="register">
        <input type="button" value="注册" onclick="checkform();"/>
      </div>
      </form>
    </div>
  </div>
  <div class="footer">&nbsp;</div>
</div>

</body>
</html>
