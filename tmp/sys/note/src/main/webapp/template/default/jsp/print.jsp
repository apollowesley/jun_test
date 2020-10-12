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
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/print.css" />
<link href="${templatePath}/css/bills.css" rel="stylesheet" />
<link href="${templatePath}/css/operate.css" rel="stylesheet" />
<script type="text/javascript" src="${templatePath}/js/nowdatetime.js"></script>
<script type="text/javascript" src="${templatePath}/js/modify.js"></script>
<script type="text/javascript">
var $$ = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};
</script>
<style>
.operate{display:none}
.from{font-size:18px;}
</style>
</head>
<body>
<%--@include file="global_topbar.jsp" --%>
<div class="header">
</div>
<div class="container">
<div class="bills">
  <div class="lcontent" style="border-right:0">
    <div class="bill_list" id="bill_list">

    </div>
  </div>
  </div>
</div>
<%--@include file="../jsp/global_footer.jsp" --%>
<%@include file="../html/modbacktop.html" %>
<script>
/*这里的变量来自于后台，是提供给下面的ejectdiv.js和weibolist.js使用的*/
var templatePath="${templatePath}";
var listjson='${listjson}';
var u="${sessionScope.UOID}";
</script>
<script>
window.printBill=true;
</script>
<script type="text/javascript" src="${templatePath}/js/ejectdiv.js"></script>
<script type="text/javascript" src="${templatePath}/js/weibolist.js"></script>
<div style="clear:both;"></div>
</body>
</html>