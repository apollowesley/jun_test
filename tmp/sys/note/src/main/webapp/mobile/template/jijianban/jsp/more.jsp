<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=0.67,maximum-scale=0.67,minimum-scale=0.67" />
<title>我的欢乐生活 - ${websiteName}</title>
<link href="${templatePathMobile}/css/common.css"	rel="stylesheet" type="text/css" />
<script src="${templatePathMobile}/js/iscroll.js"></script>
<script type="text/javascript">
//tab类型
var TAB_HOME = 101;
var TAB_MESSAGE = 102;
var TAB_PROFILE = 103;
var TAB_SQUARE = 104;
var TAB_MORE = 105;
var PAGE_ADD = 201;
function changeTab(type){
	var url = "";
	var urlnum = "";
	switch (type) {
		case TAB_HOME:
			url = "mobile/Bills.yy";
			urlnum="1";
			break;
		case TAB_MESSAGE:
			url = "mobile/index.jsp";
			urlnum="2";
			break;
		case TAB_PROFILE:
			url = "mobile/index.jsp";
			urlnum="3";
			break;
		case TAB_SQUARE:
			url = "mobile/index.jsp";
			urlnum="4";
			break;
		case TAB_MORE:
			url = "${templatePathMobile}/jsp/more.jsp";
			urlnum="5";
			break;
		case PAGE_ADD:
			url = "${templatePathMobile}/jsp/addbill.jsp";
			urlnum="5";
			break;
	}
	location.href = "<%=basePath%>mobile/PageTab.yy?ur="+url+"&urlnum="+urlnum;
}
</script>
</head>
<body>
	<script type="text/javascript">
		//一些初始化操作
		var PerPage_MBlog = parseInt("20");
		var PerPage_Pm = parseInt("20");
		var PerPage_Member = parseInt("20");
		var PerPage_Def = parseInt("20");
		var Code = "";
		var Module = "more";
		var Uid = "2";
		var MobileClient = false;
		var myScroll;
		function loaded() {
			var scrollName = "g_isrcollWrapper";
			if (Module == "topic" && Code == "detail") {
				//scrollName = "";
			}
			if (scrollName != "") {
				myScroll = new iScroll(scrollName, {
					checkDOMChanges : true
				});
			}
		}
		document.addEventListener('touchmove', function(e) {
			e.preventDefault();
		}, false);
		document.addEventListener('DOMContentLoaded', function() {
			setTimeout(loaded, 200);
		}, false);
	</script>
	<div data-role="page" data-theme="f" class="page">
		<div id="g_header">
			<div class="g_left_nav_toolbar">
				<ul>
					<li><button class="g_nav_btn_edit"
							onclick="changeTab(PAGE_ADD);">&nbsp;</button></li>
				</ul>
			</div>
			<div class="g_middle_nav_toolbar">更多</div>
			<div class="g_right_nav_toolbar">
				<ul>
					<li><button class="g_nav_btn_ref" onclick="location.reload();">&nbsp;</button></li>
				</ul>
			</div>
		</div>
		<div id="g_tips" onclick="closeTipsExp();"></div>
		<div id="g_isrcollWrapper">
			<div data-role="content">
				<div id="setting_wp" class="mc">
					<ul class="lv_4">
						<li onClick="location.href='<%=basePath%>/mobile/Login.yy?a=logout'">
							<div class="col accounts">退出</div>
							<div class="arrow"></div>
						</li>
						<li onClick="">
							<div class="col about">关于</div>
							<div class="arrow"></div>
						</li>
						<li onClick="">
							<div class="col about">手机客户端</div>
							<div class="arrow"></div>
						</li>
						<li class="nb"
							onClick="location.href='<%=basePath%>'">
							<div class="col about">电脑版网站</div>
							<div class="arrow"></div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div id="g_footer">
			<ul class="g_tabbar">
				<li onclick="changeTab(TAB_HOME);">
					<div class="g_tab <c:if test="${urlnum==1}">g_tabbar_on</c:if>">
						<img src="${templatePathMobile}/img/tabbar/icon_1_n.png" /> <span
							class="g_tabbar_tips">首页</span>
					</div>
				</li>
				<li onclick="changeTab(TAB_MESSAGE);">
					<div class="g_tab <c:if test="${urlnum==2}">g_tabbar_on</c:if>">
						<img src="${templatePathMobile}/img/tabbar/icon_2_n.png" /> <span
							class="g_tabbar_tips">信息</span>
					</div>
				</li>
				<li onclick="changeTab(TAB_PROFILE);">
					<div class="g_tab <c:if test="${urlnum==3}">g_tabbar_on</c:if>">
						<img src="${templatePathMobile}/img/tabbar/icon_3_n.png" /> <span
							class="g_tabbar_tips">我的资料</span>
					</div>
				</li>
				<li onclick="changeTab(TAB_SQUARE);">
					<div class="g_tab <c:if test="${urlnum==4}">g_tabbar_on</c:if>">
						<img src="${templatePathMobile}/img/tabbar/icon_4_n.png" /> <span
							class="g_tabbar_tips">广场</span>
					</div>
				</li>
				<li onclick="changeTab(TAB_MORE);">
					<div class="g_tab <c:if test="${urlnum==5}">g_tabbar_on</c:if>">
						<img src="${templatePathMobile}/img/tabbar/icon_5_n.png" /> <span
							class="g_tabbar_tips">更多</span>
					</div>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>