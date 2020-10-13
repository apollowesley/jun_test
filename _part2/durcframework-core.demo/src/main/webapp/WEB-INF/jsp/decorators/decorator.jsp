<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[if lt IE 7 ]><html lang="en" class="ie6 ielt7 ielt8 ielt9"><![endif]--><!--[if IE 7 ]><html lang="en" class="ie7 ielt8 ielt9"><![endif]--><!--[if IE 8 ]><html lang="en" class="ie8 ielt9"><![endif]--><!--[if IE 9 ]><html lang="en" class="ie9"> <![endif]--><!--[if (gt IE 9)|!(IE)]><!--> 
<html lang="en"><!--<![endif]--> 
	<head>
		<meta charset="utf-8">
		<title><sitemesh:write property="title" /></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="${ctx}resources/css/bootstrap.min.css" rel="stylesheet">
		<link href="${ctx}resources/css/site.css" rel="stylesheet">
		<link href="${ctx}resources/css/table/OrangesInTheSky.css" rel="stylesheet">
		<script src="${ctx}resources/js/jquery.min.js"></script>
		<script src="${ctx}resources/js/bootstrap.min.js"></script>
		<script src="${ctx}resources/js/site.js"></script>
		<!--[if lt IE 9]><script src="js/html5.js"></script><![endif]-->
		<sitemesh:write property="head" />
	</head>
	<body>
		<div class="main-container">
			<div class="navbar">
				<div class="navbar-inner">
					<div class="container">
						<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </a>
						<a class="brand" href="#">学生管理系统</a>
						<div class="nav-collapse">
							<ul class="nav">
								<li>
									<a href="#">用户设置</a>
								</li>
								<li>
									<a href="#">帮助</a>
								</li>
							</ul>
							<ul class="nav pull-right">
								<li>
									<a href="#">@username</a>
								</li>
								<li>
									<a href="loginAdmin.do">注销</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span3">
					<div class="well" style="padding: 8px 0;">
						<ul class="nav nav-list">
							<li class="nav-header">
								菜单
							</li>
							<li class="uuid-studentManager">
								<a href="${ctx}jsp/studentManager.do"><i class="icon-home"></i>学生管理</a>
							</li>
							<li class="nav-header">
								我的账户
							</li>
							<li>
								<a href="#"><i class="icon-cog"></i>设置</a>
							</li>
						</ul>
					</div>
				</div>
				<div class="content-body">
				<sitemesh:write property="body" />
				</div>
		</div>
		
		<ul class="pager">
			<li>
				学生管理系统
			</li>
		</ul>
		
<script type="text/javascript">
function activeMenu(){
	var url = location.href;
	var filename =  url.substr(url.lastIndexOf('/') + 1);
	filename = "uuid-"+filename.substr(0, filename.lastIndexOf('.'));
	var li = $("."+filename);
	li.addClass('active');
	li.find('i').addClass('icon-white');
}

activeMenu();
</script>
</body>
</html>