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
<script type="text/javascript" src="${templatePath}/js/nowdatetime.js"></script>
<script type="text/javascript" src="${templatePath}/js/modify.js"></script>
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
								href="seeworld.yy?pageNum=0&date=<%=new Date().getTime()%>"
								target="_self"><<首页</a>
							<a title="上一页" class="more"
								href="seeworld.yy?pageNum=${pagenow-1}&date=<%=new Date().getTime()%>"
								target="_self"><上一页</a>
						</c:if>
						第${pagenow*20+1}-${(pagenow*20+20)>ofTotal?ofTotal:(pagenow*20+20)}条
						共${ofTotal}条
						<c:if test="${pagenow!=pages}">
							<a title="下一页" class="more"
								href="seeworld.yy?pageNum=${pagenow+1}&date=<%=new Date().getTime()%>"
								target="_self">下一页></a>
							<a title="尾页" class="more"
								href="seeworld.yy?pageNum=${pages}&date=<%=new Date().getTime()%>"
								target="_self">尾页>></a>
						</c:if>
					</div>
  </div>
  <div class="rcontent">
    <div class="calendar"><%@include file="../html/Calendar3.html" %></div>
    <div class="userinfo"></div>
  </div></div>

    <div id="modify_form" class="modify_form">
	<div id="modify_add_block" class="bill_add_block">
	<form name="modifyform" method="post" action="Bills.yy">
        <div class="bill_add bill_modify">
      <p class="startnew">修改记账：</p><div class="bill_add_h"><p>
      您于&nbsp;<span id='thetime'>
    <input type="text" name="datetime_m" id="datetime_m" readonly="readonly" value=""/></span>&nbsp;因为<input class="b_type" type="text" id="btype_m" name="btype_m" value="吃饭 购物等等" readonly/><select class="b_io" id="bio_m" name="bio_m">
            <option value="">&nbsp;</option>
            <option value="1">支出</option>
            <option value="2">收入</option>
            <option value="3">债务</option>
            <option value="4">债权</option>
            <option value="5">无金钱</option></select><input class="b_amount" type="text" id="bamount_m" name="bamount_m" value="金额"/>元，记录一下！</p><div><textarea class="b_caption" type="text" id="bcaption_m" name="bcaption_m"/>再说点什么……</textarea></div>
        </div>
        <div class="bill_add_s">
    <input type="hidden" name="a" value="modify"/>
        <input type="button" value="取消" onclick="javascript:document.getElementById('modify_form').className='modify_form';	document.getElementById('modify_mask').className='modify_mask';"/>
       <input type="button" value="保存" onclick="javascript:if(checknull_m()){document.modifyform.submit();}"/>
         <span id="checknullprompt_m"></span>
        </div></div>
    <input type="hidden" id="b_modify" name="b" value=""/></form>
    </div>
	</div>
    <div id="modify_mask" class="modify_mask"></div>
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