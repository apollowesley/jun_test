<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>2018knownoting.xyz登录页面</title>
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
		<h3>Welcome to login！</h3>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>

	
		<div class="container">
		<form    method="post"   action="judge">

		        <label for="userNo">账号:</label>
		        <input type="text" name="userNo" />
		        <br/>
		    
	
		       
		     
		         <label for="userPassword" >密码:</label>
		        <input type="password"   name="userPassword" />
		         <br/>
		        
         
    	<button class="btn btn-block btn-info" type="submit">登录</button>
		</form>
			<form    method="post"   action="">
		<a href="index.html" class="btn btn-block btn-info">返回注册页面</a>
		</form>
</div>
	

</body>
</html>