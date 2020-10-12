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
    
    <title>用户中心 - ${websiteName}</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link href="${templatePath}/css/home.css" rel="stylesheet" type="text/css"/>
<link href="${templatePath}/css/bills.css" rel="stylesheet" />
<style>
.user_cent{width:950px;margin:0 auto;}
.user_cent .user_cent_l{width:150px;float:left;padding:20px 0px;}
.user_cent .user_cent_l ul li{line-height:27px;height:27px;font-size:14px;padding-left:35px;}
.user_cent .user_cent_l ul li:hover{background-color:#eee;}
.user_cent .user_cent_l ul li:hover a{text-decoration:none;color:#000;}
.user_cent .user_cent_l .tag_open{background-color:#888;}
.user_cent .user_cent_l .tag_open a{color:#fff;}
.user_cent_r{width:750px;;float:left;padding:20px 25px;}
.user_cent_r td{padding:7px 0px;}
.user_cent_r td.u_td_l{text-align:right;width:100px;}
.user_cent_r td.u_td_l i{color:#f00;padding-right:5px;vertical-align:middle;font-style:normal;}
.user_cent_r input{height:20px;width:186px;border:1px solid #ddd;}
.user_cent_r h1{margin: 5px 0px 20px; font-size: 1.5em;font-weight:700;}
.user_cent_r h1 span {
	color: rgb(153, 153, 153); font-size: 12px; font-weight: 500; margin-left: 8px;
}
.user_cent_r h1 span i {
	color: red; font-family: "SimSun";font-style:normal;
}
.user_cent_r .btnText{background-color:#80d;color:#fff;height:24px;width:80px;cursor: pointer;}
.user_cent_r .btnText:hover{background-color:#88d;}
.user_cent_r .confirm_box input{width:14px;height:14px;line-height:16px;vertical-align:middle;margin-left:12px;margin-right:8px;}
.user_cent_r .confirm_box span{height:16px;line-height:16px;vertical-align:middle;}

.Menulink {
	height: 30px; line-height: 30px; clear: both; margin-bottom: 20px; border-bottom-color: rgb(234, 237, 238); border-bottom-width: 3px; border-bottom-style: solid;
}
.Menulink ul {
	padding: 0px; width: 100%; height: 27px;
}
.Menulink a {
	border-width: 1px 1px medium; border-style: solid solid none; border-color: rgb(234, 237, 238) rgb(234, 237, 238) currentColor; padding: 0px 18px; border-radius: 3px 3px 0px 0px; text-align: center; color: rgb(68, 68, 68); font-size: 12px; margin-right: 2px; float: left; display: block; cursor: pointer;
}
.Menulink a.selected {
	background: rgb(234, 237, 238); padding: 0px 20px; height: 30px; color: rgb(51, 51, 51); line-height: 30px;
}
.myAvatarText{font-size:18px;font-weight:700;}
</style>

</head>
  
<body>
<%@include file="global_topbar.jsp" %>
<div class="user_cent">
<div class="user_cent_l">
<ul>
<li <c:if test="${page=='1'}">class="tag_open"</c:if>><a href="Usercenter.yy?uc=1">用户资料</a></li>
<li <c:if test="${page=='2'}">class="tag_open"</c:if>><a href="Usercenter.yy?uc=2">我的头像</a></li>
<li <c:if test="${page=='3'}">class="tag_open"</c:if>><a href="Usercenter.yy?uc=3">修改密码</a></li>
<li <c:if test="${page=='4'}">class="tag_open"</c:if>><a href="Message.yy?uc=4&a=news">我的消息</a></li>
<li <c:if test="${page=='5'}">class="tag_open"</c:if>><a href="Friends.yy?uc=5&a=myFriends">我的好友</a></li>
</ul>
</div>
<%--此位置添加right页面--%>
<c:if test="${page=='1'}"><%@include file="./userCenterPersonalData.jsp" %></c:if>
<c:if test="${page=='2'}"><%@include file="./userCenterHeadPicture.jsp" %></c:if>
<c:if test="${page=='3'}"><%@include file="./userCenterChangePassword.jsp" %></c:if>
<c:if test="${page=='4'}"><%@include file="./message.jsp" %></c:if>
<c:if test="${page=='5'}"><%@include file="./myFriends.jsp" %></c:if>

</div>
<%@include file="./global_footer.jsp" %>
</body>
</html>
