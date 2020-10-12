<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="icon" href="${ctx}/static/images/favicon.ico">
<title><sitemesh:write property='title' /></title>
<link href="${ctx}/static/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/bootstrap/css/bootstrap-theme.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/bootstrap/css/bootstrap-submenu.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery/plugins/css/jquery.scrollToTop.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/static/jquery/jquery-1.9.1.js" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap-submenu.js" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/js/highlight.min.js" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/js/menu.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery/plugins/js/jquery.scrollToTop.js" type="text/javascript"></script>
<script type="text/javascript">
  var _ctx = "${pageContext.request.contextPath}";
  jQuery(function() {
    jQuery(window).scrollToTop();
  });
</script>

<sitemesh:write property='head' />
</head>
<body>

	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<a class="navbar-brand">后台管理系统</a>
		</div>
		<div class="collapse navbar-collapse">

			<c:if test="${not empty permission_session }">
				<c:forEach items="${permission_session }" var="one">
					<ul class="nav navbar-nav">
						<li class="dropdown"><a tabindex="0" data-toggle="dropdown" data-submenu=""> ${one.menuName}<span class="caret"></span>
						</a>

							<ul class="dropdown-menu">

								<c:forEach items="${one.children }" var="two">
									<li class="dropdown-submenu"><a tabindex="0">${two.menuName }</a>
										<ul class="dropdown-menu">
											<c:forEach items="${two.children }" var="three">
												<li><a tabindex="0" href="${ctx}/${three.url }">${three.menuName }</a></li>
											</c:forEach>
										</ul></li>
								</c:forEach>

							</ul></li>
					</ul>
				</c:forEach>
			</c:if>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a tabindex="0" data-toggle="dropdown"><shiro:principal />个人中心 <span class="caret"></span> </a>

					<ul class="dropdown-menu">
						<li><a tabindex="0" href="${ctx }/user/updatepasswordPage">修改密码</a></li>
						<li class="divider"></li>
						<li><a tabindex="0" href="${ctx }/logout">退出</a></li>
					</ul></li>
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
				Copyright &copy; 2008-2013 <a href="https://github.com/infowangxin/springmvc-dubbo">github.com</a>
			</p>
		</div>
	</div>

</body>
</html>