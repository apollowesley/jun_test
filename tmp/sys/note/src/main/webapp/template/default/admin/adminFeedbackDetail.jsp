<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>后台管理用户反馈界面  - ${websiteName}</title>

<link href="${templatePath}/css/home.css" rel="stylesheet" type="text/css"/>
<style>
table td{border-bottom:0.5px dotted}
tbody tr.split{background-color:#fbfbfb;}
table tbody tr td{padding:3px;margin:3px;line-height:23px;height:23px;}
input{height:18px;line-height:18px;width:183px;}
.fid{width:60px;}
.submitbutton{height:21px;line-height:21px;width:47px;}
.feedbackc{ width:598px;line-height:19px;border:1px solid #ddd;color:#666;font-size:12px;margin:0;overflow-x:hidden;overflow-y:auto;font-family:Tahoma, 宋体; padding:5px; height:50px;}
</style>
</head>
<body>
<form action="Admin.yy" method="post">
<input type="hidden" value="feedback" name="a"/>
<input type="hidden" value="${feedback.fid}" name="fid"/>
<input type="hidden" value="change" name="operate"/>
<table>
<tr><td class="fid">FID</td><td class="uid">${feedback.fid}</td></tr>
<tr><td>反馈人</td><td>${feedback.username}</td></tr>
<tr><td>反馈时间</td><td>${feedback.fdate}</td></tr>
<tr><td>反馈内容</td><td>${feedback.contents}</td></tr>
<tr><td>回复内容</td><td><textarea class="feedbackc" id="feedbackc" name="rcontents">${feedback.rcontents}</textarea></td></tr>
<tr><td></td><td><input class="submitbutton" type="submit" value="提交"/></td></tr>
</table>
</form>
</body>
</html>