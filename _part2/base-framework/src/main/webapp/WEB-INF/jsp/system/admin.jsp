<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>在线考试管理平台</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport' />

<link rel="stylesheet" type="text/css" href="${ctx }/css/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx }/js/jquery-easyui/themes/bootstrap/easyui.css">

<!--[if lt IE 9]>
    <script src='${ctx}js/html5shiv.js' type='text/javascript'></script>
<![endif]-->
<link href='${ctx}/css/bootstrap/bootstrap.css' media='all' rel='stylesheet' type='text/css' />
<link href='${ctx}/css/bootstrap/bootstrap-responsive.css' media='all' rel='stylesheet' type='text/css' />
	
<script type="text/javascript" src="${ctx }/js/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script src="${ctx}/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx }/js/plugins/json2/json2.js"></script>

</head>

<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="color: #fff;height: 50px; background: #0163ac; padding: 10px">
		<div> 
			<span style="font-size: 20pt;">西藏自治区气象局科技项目管理平台</span>
			<div style="float:right;">
			    <span>
			        <i class="icon-user icon-white"></i> <small>欢迎:</small>
			    	<shiro:principal property="userName"/>
			    </span>
				<a  href='${ctx }/logout.html' style="color: #fff;">
				<span style="margin-left: 10px;">
						<i class="icon-off icon-white"></i>退出
				</span>
					</a>
			</div>
		</div>
	</div>

	<!-- 左侧菜单 -->
	<div data-options="region:'west',split:true,title:'菜单导航'" style="width: 250px; padding: 1px; color: blue;">
		<div id="main_accordion">
			
			<table id="userModule_tree" cellspacing="0" cellpadding="0">
		        <thead>
					<tr>
						<th data-options="field:'resourceName',width:80">名称</th>
					</tr>
				</thead>
		   	</table>
		</div>
	</div>

	<!-- 中间主窗体内容 -->
	<div data-options="region:'center',border:false">
		<div id="mainContent_tab" class="easyui-tabs"
			data-options="fit:true,border:false,pill:false"></div>
	</div>

<script type='text/javascript' src='${ctx}/js/common.js'></script>
<script type="text/javascript" src="${ctx}/js/system/main.js"></script>
</body>
</html>