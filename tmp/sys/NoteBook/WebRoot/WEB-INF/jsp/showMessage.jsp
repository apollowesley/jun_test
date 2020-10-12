<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 引入Struts2标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- 引入JSTL标签 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>showMessage.jsp</title>
</head>

<body>
	<!-- 使用JSTL的${pageContext.request.contextPath} 代替写项目名称的绝对路径 -->
	<font size="6"><b><a	
			href="${pageContext.request.contextPath}/goPublishMessageUI">发布信息</a></b></font>
	<font size="6"><b><a href="/NoteBook/login.do?flag=logout">退出系统</a></b></font>
	<br /> 欢迎 ${user.username } 留言信息：

	<table width="500px">
		<tr>
			<td>发送人</td>
			<td>发送时间</td>
			<td>接收人</td>
			<td>信息内容</td>
		</tr>
		<!--  Struts2标签 
		<s:iterator value="#request.messageList" id="message">
			<tr>
				<td><s:property value="#message.sender.username" /></td>
				<td><s:property value="#message.mesTime" /></td>
				<td><s:property value="#message.getter.username " /></td>
				<td><s:property value="#message.content" /></td>
			</tr>		
		</s:iterator>
		-->
		
		<!--  JSTL标签 -->
		<c:forEach items="${messageList}" var="message">
			<tr>
				<td>${message.sender.username }</td>
				<td>${message.mesTime }</td>
				<td>${message.getter.username }</td>
				<td>${message.content }</td>
			</tr>
		</c:forEach>
		
	</table>

</body>
</html>
