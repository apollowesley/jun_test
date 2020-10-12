<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的欢乐生活 - ${websiteName}</title>
<link charset="utf-8" rel="stylesheet"
	href="${templatePath}/css/home.css" />

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="${templatePath}/css/app.css">

</head>

<body>
	<%@include file="global_topbar.jsp"%>
	<div class="content">
	<div class="setframeWrap">
		<div class="Menubox">
			<ul>
				<li><div class="tagn">
						<a href="http://android.myapp.com/myapp/detail.htm?apkName=com.weiwo.billing" target="_blank"><span>应用下载</span></a>
					</div></li>
				<li><div class="tagn">
						<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.weiwo.billing" target="_blank"><span>应用下载</span></a>
					</div></li>
				<li><div class="tagn">
						<a><span></span></a>
					</div></li>
				<li><div class="tagn">
						<a><span>iPhone</span></a>
					</div></li>
				<li><div class="tagcurrent">
						<a><span>Android</span></a>
					</div></li>
				<!--<li><div class="tagn"><a href="index.php?mod=other&code=pad"><span>Android平板</span></a></div></li>-->
			</ul>
		</div>
		<div class="main3Box_t">
			<div class="main3Box_t2">
				<div class="ts_menu_22"></div>
			</div>
		</div>
		<div class="main3Box_m">
			<div class="ts_m3">
				<div style="padding-top:0;" class="feedCell">
					<div class="wap2">
						<div class="wapF"></div>
					</div>
					<div class="wapT">
						<div class="ts_m4">
							<p class="">Android 客户端</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基于Android平台的微我手机客户端，随时随地记录身边的事。这里不是一个社交平台，这里是为了让你记录属于你自己的世界。<br/><br/>更新日期：2014-09-06<br/>版本：1.09</p>
							<p class="W_hline"></p>
							<table>
							<tr><td>扫描下载</td><td></td></tr>
							<tr><td><p class="android_qr"></p></td><td><p class="android_click"><a href="app/billing.apk" target="_blank">本站下载</a></p></td></tr>
							</table>
							<p class="W_hline"></p>
							<p style="font-size:16px;font-weight:600">特点</p>
							<ul>
								<li>1.发表心情（配图最大720px）</li>
								<li>2.发表记账</li>
								<li>3.随时回忆</li>
								<li>4.一个人很孤单，自己和自己在一起就会好很多</li>
								<li>5.自定义背景</li>
								<li>6.你随手记下的语言，都属于你，功能正在研发中……</li>

							</ul>
							<!-- <ul>
								<li>1.手机拍照一键发送。</li>
								<li>2.同时添加多个帐号，保存多个帐号信息随心切换。</li>
								<li>3.支持客户端帐号注册。</li>
							</ul> -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
<%@include file="global_footer.jsp" %>
</body>
</html>
