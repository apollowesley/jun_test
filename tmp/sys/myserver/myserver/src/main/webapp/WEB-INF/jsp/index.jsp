<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>2018knownoting.xyz注册页面</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">
	
</head>
	<style type="text/css">
		body{
			background-image:url(http://120.79.148.96:8080/800.jpg);

		}
		h3{
			text-align: center;
		}
		div{
			text-align: center;
			
		}
	</style>
<body>
		<h3>Welcome to Register account</h3>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>

	
		<div class="container">
		<form    method="post"   action="record">

		        <label for="userNo">账号:</label>
		        <input type="text" name="userNo" />
		        <br/>
		        <label for="userName" >昵称:</label>
		        <input type="text"   name="userName" />
		         <br/>
		          <label for="userSex" >性别:</label>
		        <input type="text"   name="userSex" />
		         <br/>
		         <label for="userPassword" >密码:</label>
		        <input type="password"   name="userPassword" />
		         <br/>
         		 <label for="userEmail" >邮箱:</label>
		        <input type="text"   name="userEmail" />
		         <br/>
		         <label for="userBirth" >生日:</label>
		        <input type="text"   name="userBirth" />
		         <br/>
		        
         
    	<button class="btn btn-block btn-info" type="submit">注册（邮箱要填对哦，不然激活不了账号）！</button>
		</form>

		<form    method="post"   action="">
		<a href="login.html" class="btn btn-block btn-info">进入登录页面</a>
		</form>

</div>
	

</body>
</html>