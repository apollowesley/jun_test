<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<title>后台管理</title>
		<link rel="stylesheet" href="./layui/css/layui.css">
		<link rel="stylesheet" href="./layui/admin.css">
	</head>

	<body class="layui-bg-gray">
		<div class="layui-layout layui-layout-admin">
			<div class="layui-header">
				<div class="layui-logo">后台管理</div>
				<!-- 头部区域（可配合layui已有的水平导航） -->

				<ul class="layui-nav layui-layout-right">
					<li class="layui-nav-item">
						<a href="index.action" target="_blank">网站首页</a>
					</li>
					<li class="layui-nav-item">
						<a href="crm.action" target="main">${admin.username}</a>
					</li>
					<li class="layui-nav-item">
						<a href="logout.action">登出</a>
					</li>
				</ul>
			</div>

			<div class="layui-side layui-bg-black">
				<div class="layui-side-scroll">
					<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
					<ul class="layui-nav layui-nav-tree">
						
						<li class="layui-nav-item layui-nav-itemed">
							<a class="">作品管理</a>
							<dl class="layui-nav-child">
								<dd><a href="item.action" target="main" class="layui-this">作品管理</a></dd>
								<dd><a href="type.action" target="main">分类管理</a></dd>
							</dl>
						</li>
						<li class="layui-nav-item layui-nav-itemed">
							<a class="">网站管理</a>
							<dl class="layui-nav-child">
								<dd><a href="turnimg.action" target="main">轮播图管理</a></dd>
								<dd><a href="about.action" target="main">关于我们</a></dd>
								<dd><a href="crm.action" target="main">用户管理</a></dd>
								<dd><a href="site.action" target="main">网站设置</a></dd>
							</dl>
						</li>
						
					</ul>
				</div>
			</div>

			<div class="layui-body">
				<iframe name="main" src="item.action" frameborder="0" class="layadmin-iframe" style="width: 100%;height: 100%;">
					<!-- 内容主体区域 -->
				</iframe>
			</div>

		</div>
		<script src="./layui/layui.js"></script>
		<script>
			//JavaScript代码区域
			layui.use('element', function() {
				var element = layui.element;

			});
		</script>
	</body>

</html>
