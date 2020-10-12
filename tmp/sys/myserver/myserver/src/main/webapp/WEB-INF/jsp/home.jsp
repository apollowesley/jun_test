<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
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
		
	</style>
<body>
	<div class="jumbotron">
		<div class="container">
			<h3>留言栏</h3>
			<p>leave me messages,Give me advice, I won't take it,just kidding,hhh</p>
		</div>
	</div>
	<div class="container">
		<form style="margin-bottom:50px;" action="addUserMessage" method="post">
  			<div class="form-group">
  			<input type="hidden"  value="${userInfo.userNo}" name="userNo" >
    			<label for="author"  >您不想让我知道的昵称 Name</label>
    			<input type="text"  name="userName" placeholder="请输入您的名字" value="${userInfo.userName}">
  			</div>
 			<div class="form-group">
    			<label for="content">您的留言内容 Content</label>
    			<textarea type="text" class="form-control" name="userWords" placeholder="请输入您的留言内容" rows="10"></textarea>
  			</div>
  			<button type="submit" class="btn btn-block btn-info">提交 Submit</button>
		</form>
		<h4>留言消息</h4>
		<table class="table">
			<thead>
				<tr>
					<td class="col-md-4 col-xs-4">留言者 name</td>
					<td class="col-md-4 col-xs-4">留言时间time</td>
					<td class="col-md-4 col-xs-4">内容 Content</td></tr>
				 <c:forEach var="message"   items="${requestScope.messageData }" >
				 	<tr><td class="col-md-4 col-xs-4">${message.userName}</td>
						<td class="col-md-4 col-xs-4">${message.userTime}</td>
						<td class="col-md-4 col-xs-4">${message.userWords }</td>
					 </tr>
                 </c:forEach>
			</thead>
		</table>
	</div>
	
</body>
</html>