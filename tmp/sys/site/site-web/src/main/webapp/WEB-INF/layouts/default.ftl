<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="${ctx}/static/images/favicon.ico">
<title><sitemesh:write property='title' /></title>
<link href="${ctx}/static/bootstrap/css/bootstrap.min.css?${version_css}" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/bootstrap/css/bootstrap-theme.min.css?${version_css}" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/bootstrap/css/bootstrap-submenu.css?${version_css}" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery/plugins/css/jquery.scrollToTop.css?${version_css}" type="text/css" rel="stylesheet" />
<sitemesh:write property='head' />
</head>
<body>

	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<a class="navbar-brand">后台管理系统</a>
		</div>
		<div class="collapse navbar-collapse">
			<#if !permission_session?if_exists  || 0==permission_session?size>
			<#list permission_session as one>
					<ul class="nav navbar-nav">
						<li class="dropdown">
							<a tabindex="0" data-toggle="dropdown" data-submenu=""> ${one.menuName}<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<#if !one.children?if_exists  || 0==one.children?size>
									<#list one.children as two>
										<li class="dropdown-submenu"><a tabindex="0">${two.menuName }</a>
											<ul class="dropdown-menu">
												<#if !two.children?if_exists  || 0==two.children?size>
													<#list two.children as three>
														<li><a tabindex="0" href="${ctx}/${three.url }">${three.menuName }</a></li>
													</#list>
												</#if>
											</ul>
										</li>
									</#list>
								</#if>
							</ul>
						</li>
					</ul>
				</#list>
			</#if>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a tabindex="0" data-toggle="dropdown"><shiro:principal />个人中心 <span class="caret"></span> </a>
					<ul class="dropdown-menu">
						<li><a tabindex="0" href="${ctx }/user/updatepasswordPage">修改密码</a></li>
						<li class="divider"></li>
						<li><a tabindex="0" href="${ctx }/logout">退出</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>

	<div id="wrap">
		<div class="container">
			<div id="content">
				<sitemesh:write property='body' />
			</div>
		</div>
	</div>

	<div id="footer">
		<div class="container">
			<p class="text-center text-muted credit">
				Copyright &copy; 2016 <a href="https://github.com/infowangxin/springmvc-dubbo">github.com</a>
			</p>
		</div>
	</div>
	
<script src="${ctx}/static/jquery/jquery-1.9.1.js?${version_js}" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap.min.js?${version_js}" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap-submenu.js?${version_js}" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/js/highlight.min.js?${version_js}" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/js/menu.js?${version_js}" type="text/javascript"></script>
<script src="${ctx}/static/jquery/plugins/js/jquery.scrollToTop.js?${version_js}" type="text/javascript"></script>
<script type="text/javascript">
  var _ctx = "${ctx}";
  jQuery(function() {
    jQuery(window).scrollToTop();
  });
</script>
</body>
</html>