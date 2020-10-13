<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>登陆-考试系统</title>
 
<link href='${ctx}/css/login/styles.css' rel='stylesheet' type='text/css' />
<script type="text/javascript" src="${ctx }/js/jquery-easyui/jquery.min.js"></script>
 
</head>
<body id="login">
<div id="wrappertop">
    </div>
    <div id="wrapper">
        <div id="content">
            <div id="header">
                <h1> </h1>
            </div>
            <div id="darkbanner" class="banner320">
                <h2>西藏自治区气象局科技项目登陆</h2>
            </div>
            <div id="darkbannerwrap">
            </div>
            <form action="${ctx}/login.html" method="post">
            <fieldset class="form">
            	<p>
            		<div id="errorMessage" style="height:20px;margin-left:80px;color:red;">${errorMessage }</div>
            	</p>
            	<br>
                <p>
                    <label class="loginlabel" for="username">账号:</label>
                    <input style="border:1px solid #E1E1E1;height: 30px; line-height:30px; width: 280px;  margin-bottom: 15px; padding:0 5px;"  type="text" name="username" id="username"  onfocus="$('#errorMessage').text('');"/>
                </p>
                <p>
                    <label class="loginlabel" for="password">密码:</label>
                    <input type="password" style="border:1px solid #E1E1E1;height: 30px; line-height:30px; width: 280px;  margin-bottom: 15px; padding:0 5px;" name="password"  id="password" onfocus="$('#errorMessage').text('');" />
                </p>
                
                <p>
	                <label class="loginlabel" for="captcha">&nbsp;</label>
					<input type="text" style="width:80px;height: 30px; float:left;border:1px solid #E1E1E1; padding:0 5px" name="captcha" id="captcha"  placeholder="验证码" />
					<img style="cursor:pointer;margin-left:40px;vertical-align: middle;" alt="验证码" src="${ctx}/servlet/captchaCode.html" title="点击更换"   id="img_captcha" onclick="javascript:refreshCaptcha();">
                </p>
                
                <p>
               	 	<button type="submit" id="loginbtn" type="button" class="positive" style="width:280px;height: 40px;margin-top:35px;font-size:15pt;margin-bottom: 10px;" name="Submit">登&nbsp;&nbsp;陆</button>
                </p>
            </fieldset>
        </div>
    </div>
    
 

	
<script type="text/javascript" src="${ctx}/js/jquery/placeholder.js"></script>
<script type="text/javascript">
	function refreshCaptcha(){//刷新验证码
	   $("#img_captcha").attr("src","${ctx}/servlet/captchaCode.html?t=" + Math.random());  
	} 
</script>

</body>
</html>
