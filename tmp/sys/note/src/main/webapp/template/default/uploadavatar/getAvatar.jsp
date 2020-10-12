<%@ page language="java" import="java.util.*,com.staticClass.GetAvatar"
	pageEncoding="UTF-8"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
String au=request.getParameter("au");//avatar uid，用户的id，用来获取头像
response.sendRedirect(basePath1+GetAvatar.GetfileUrl(au));
%>