<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>2018knownoting.xyz操作成功页面</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">
</head>
<style type="text/css">
		body{
			background-image:url(http://120.79.148.96:8080/800.jpg);

		}
		
	</style>
<body>
<h1>恭喜呀！</h1>

		 <a href="login.html">点击，进入登录界面</a>
		 <br/>
		 <a href="home.html">点击此处，继续留言挤爆服务器！</a>
		 <p>${message}</p>
		 <form    method="get"   action="checkStuNo">
	  <label for="userNo" >查询账号:</label>
		        <input type="text"   name="userNo" />
			<button class="btn btn-block btn-info" type="submit">check！</button>
		</form>
</body>
</html>