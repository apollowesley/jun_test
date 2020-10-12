<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>反馈页面 - ${websiteName}</title>
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
<link rel="stylesheet" type="text/css" href="${templatePath}/css/feedbacks.css">
<script type="text/javascript">
	var req;
	var url;
	/** 通过异步传输XMLHTTP发送参数到ajaxServlet，返回符合条件的XML文档 */
	function publish() {
		var feedbackc = document.getElementById("feedbackc").value;
		if(feedbackc==""){alert("反馈内容不能为空");return;}
		/*feedbackc = encodeURI(feedbackc);
		feedbackc = encodeURI(feedbackc);*/
		url = "Feedback.yy";
		var data="a=feed&feedbackc="+feedbackc;
		if (window.XMLHttpRequest) {
			req = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		}
		if (req) {
			req.open("POST", url, false);
			req.setRequestHeader("Content-type","application/x-www-form-urlencoded");  //这句话必须加上，否则传不了值
			// 这里如果不设定头部则会导致 firfox 发送数据错误，servlet接受到的参数为乱码，在IE中正常
			req.onreadystatechange = complete;
			req.send(data);
		}
	};
	/** 分析返回的XML文档 */
	function complete() {
		if (req.readyState == 4) {
			if (req.status == 200) {
				alert("您反馈的问题已经提交，管理员会尽快回复，回复后会显示在此页面，回复前不可见。");
				document.getElementById("feedbackc").value="";
			}
		}
	}
</script>
</head>

<body>
	<%@include file="../jsp/global_topbar.jsp"%>
	<div class="bodycontent">
	<div class="bodycontentl">
	<span>请将您的建议填写在下面：</span>
	<textarea class="feedbackc" id="feedbackc"></textarea>
	<div class="sendBtn">
		<input class="indexBtn" id="publishSubmit"
			type="button" value="反 馈" onclick="publish()">
	</div>
<c:forEach items="${list}" var="list" varStatus="status">
<div class="feedcs">
<div class="feedl">
<img src="${templatePath}/uploadavatar/getAvatar.jsp?au=<%=request.getSession().getAttribute("UOID")%>" width="80px" height="80px"/>
</div>
<div class="feedr">
<div class="feedcontent">
<p>${list.username} <fmt:formatDate value="${list.fdate}" pattern="yyyy.MM.dd HH:mm:ss" /></p>
<span>反馈内容：${list.contents}</span>
</div>
<div class="feedrecontent">
<p>回复人：${list.rusername} 回复时间：<fmt:formatDate value="${list.frdate}" pattern="yyyy.MM.dd HH:mm:ss" /></p>
<span>回复内容：${list.rcontents}</span>
</div>
</div>
</div>
</c:forEach>
<div class="ui_recent_footer">
						<c:if test="${pagenow!=0}">
							<a title="首页" class="more"
								href="Feedback.yy?pageNum=0"
								target="_self"><<首页</a>
							<a title="上一页" class="more"
								href="Feedback.yy?pageNum=${pagenow-1}"
								target="_self"><上一页</a>
						</c:if>
						第${pagenow*20+1}-${(pagenow*20+20)>ofTotal?ofTotal:(pagenow*20+20)}条
						共${ofTotal}条
						<c:if test="${pagenow!=pages}">
							<a title="下一页" class="more"
								href="Feedback.yy?pageNum=${pagenow+1}"
								target="_self">下一页></a>
							<a title="尾页" class="more"
								href="Feedback.yy?pageNum=${pages}"
								target="_self">尾页>></a>
						</c:if>
</div>
</div>
	<%@include file="../jsp/global_footer.jsp"%>
	<%@include file="../html/modbacktop.html"%>
</body>
</html>
