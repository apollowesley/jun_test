<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>搭伙 加入我们 - ${websiteName}</title>
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
<link href="${templatePath}/css/bills.css" rel="stylesheet" />

</head>
  
<body>
<%@include file="global_topbar.jsp" %>
<div class="container">
  <div class="lcontent bgl">
  <div class="topjoinus">正在期待你加入的搭伙</div>
<c:forEach items="${list}" var="list" varStatus="status">
<div class="joinus">
<div class="numofapply"><div>${list.jaapthenumberofperson}</div><div>申请</div></div>
<div class="accessnum"><div>${list.jaapthenumberofperson}</div><div>通过</div></div>
<div class="persons"><div>${list.jaapthenumberofperson}</div><div>计划</div></div>
<div class="joinustitle"><a href="JoinUs.yy?a=detail&id=${list.jaapid}" target="_blank">${list.jaaptitle}</a>
</div>
<div class="deadline"><div><fmt:formatDate value="${list.jaapdeadline}" pattern="yyyy-MM-dd" /></div><div>截止时间</div></div>
</div>
</c:forEach>
<div class="ui_recent_footer">
						<c:if test="${pagenow!=0}">
							<a title="首页" class="more"
								href="JoinUs.yy?pageNum=0&date=<%=new Date().getTime()%>"
								target="_self"><<首页</a>
							<a title="上一页" class="more"
								href="JoinUs.yy?pageNum=${pagenow-1}&date=<%=new Date().getTime()%>"
								target="_self"><上一页</a>
						</c:if>
						第${pagenow*20+1}-${(pagenow*20+20)>ofTotal?ofTotal:(pagenow*20+20)}条
						共${ofTotal}条
						<c:if test="${pagenow!=pages}">
							<a title="下一页" class="more"
								href="JoinUs.yy?pageNum=${pagenow+1}&date=<%=new Date().getTime()%>"
								target="_self">下一页></a>
							<a title="尾页" class="more"
								href="JoinUs.yy?pageNum=${pages}&date=<%=new Date().getTime()%>"
								target="_self">尾页>></a>
						</c:if>
					</div>
  </div>
  <div class="rcontent">
    <div class="calendar"><%@include file="../html/Calendar3.html" %></div>
 <p class="btn_new"><a class="btn_a" id="btn_a" href="JoinUs.yy?a=add"><span class="btn_30px f14">发起新搭伙</span></a></p>
  </div>
</div>
<%@include file="../jsp/global_footer.jsp" %>
<%@include file="../html/modbacktop.html" %>
<div style="clear:both;"></div>
</body>
</html>
