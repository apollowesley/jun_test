<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>

<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
<link href="${templatePath}/css/bills.css" rel="stylesheet" />
<link href="${templatePath}/css/operate.css" rel="stylesheet" />
<script type="text/javascript" src="${templatePath}/js/ajax.js"></script>
<script>
var addFriends=function(uid){
	var result=new Ajax("Friends.yy","a=addFriends&fuid="+uid).get();
	result=(new Function('return '+result))();
	if(result.realSucess=='true'){
		alert(result.msg);
	}else{
		alert(result.msg);		
	}
};
</script>
  </head>
  
  <body>
<%@include file="global_topbar.jsp" %>
<div class="container">
找到的人：
<c:if test="${list1.size()>0}">
<c:forEach items="${list1}" var="vo">
用户名：${vo.username}&nbsp;<a href="###" onclick="javascript:addFriends(${vo.uid});return false;">加为好友</a>
</c:forEach>
</c:if>
</div>
<%@include file="../jsp/global_footer.jsp" %>
<%@include file="../html/modbacktop.html" %>
  </body>
</html>
