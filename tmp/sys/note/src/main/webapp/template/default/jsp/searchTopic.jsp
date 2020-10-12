<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.*" errorPage="" %>
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
<title>我的欢乐生活 - ${websiteName}</title>
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
<link href="${templatePath}/css/bills.css" rel="stylesheet" />
<link href="${templatePath}/css/operate.css" rel="stylesheet" />
<script type="text/javascript">
var $$ = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};
</script>
</head>
<body>
<%@include file="global_topbar.jsp" %>
<div class="header">
</div>
<div class="container">
<div class="bills">
  <div class="lcontent">
<%@include file="bills_add.jsp" %>
    <div class="bill_list" id="bill_list">
<div class="ui_recent_footer">
</div>
    </div><div class="ui_recent_footer">
						<c:if test="${pagenow!=0}">
							<a title="首页" class="more"
								href="Bills.yy?a=searchTopic&topicname=${topicname}&pageNum=0&date=<%=new Date().getTime()%>"
								target="_self"><<首页</a>
							<a title="上一页" class="more"
								href="Bills.yy?a=searchTopic&topicname=${topicname}&pageNum=${pagenow-1}&date=<%=new Date().getTime()%>"
								target="_self"><上一页</a>
						</c:if>
						第${pagenow*20+1}-${(pagenow*20+20)>ofTotal?ofTotal:(pagenow*20+20)}条
						共${ofTotal}条
						<c:if test="${pagenow!=pages}">
							<a title="下一页" class="more"
								href="Bills.yy?a=searchTopic&topicname=${topicname}&pageNum=${pagenow+1}&date=<%=new Date().getTime()%>"
								target="_self">下一页></a>
							<a title="尾页" class="more"
								href="Bills.yy?a=searchTopic&topicname=${topicname}&pageNum=${pages}&date=<%=new Date().getTime()%>"
								target="_self">尾页>></a>
						</c:if>
					</div>
  </div>
  <div class="rcontent">
    <div class="calendar"><%@include file="../html/Calendar3.html" %></div>
    <div class="userinfo"></div>
  </div></div>

</div>
<%@include file="../jsp/global_footer.jsp" %>
<%@include file="../html/modbacktop.html" %>
<script>
/*这里的变量来自于后台，是提供给下面的ejectdiv.js和weibolist.js使用的*/
var templatePath="${templatePath}";
var listjson='${listjson}';
var u="${sessionScope.UOID}";
</script>
<script type="text/javascript" src="${templatePath}/js/ejectdiv.js"></script>
<script type="text/javascript" src="${templatePath}/js/weibolist.js"></script>
<div style="clear:both;"></div>
</body>
</html>