<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<nav id="topmenu" class="navbar navbar-default navbar-fixed-top" style="opacity: 1;">
	<div class="menu-box">
		<div class="pull-left">
			<ul class="list-unstyled list-inline">
				<li><span id="currentTime">2018年04月11日 09时43分41秒 星期三</span></li>
			</ul>
			<div class="clear"></div>
		</div>
		<div class="menu-topmenu-container pull-right">
			<ul class="list-unstyled list-inline pull-left">
				<li>
					<a href="about.html" class="menu_a" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="关于博客"><i class="fa fa-info fa-fw"></i>关于</a>
				</li>
				<li>
					<a href="links.html" class="menu_a" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="友情链接"><i class="fa fa-link fa-fw"></i>友情链接</a>
				</li>
			</ul>
		</div>
	</div>
</nav>
<nav id="mainmenu" class="navbar navbar-default navbar-fixed-top" role="navigation" style="top: 30px; z-index: 998;">
	<div class="menu-box">
		<div class="navbar-header">
			<span class="pull-right nav-search toggle-search" data-toggle="modal" data-target=".nav-search-box"><i class="fa fa-search"></i></span>
			<a type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</a>
			<a class="navbar-brand logo" href="index.html"></a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<div class="pull-left site-desc" style="line-height: 0.9;">
				<h1 style="font-size: 20px;font-weight: 700;" class="auto-shake"><a href="//www.zhyd.me/" data-original-title="${blogInfo.sign }" data-toggle="tooltip" data-placement="bottom">${blogInfo.siteName }</a></h1>
				<p class="site-description">${blogInfo.sign }</p>
			</div>
			<ul class="nav navbar-nav ">
				<c:forEach items="${blogNavs }" var="nav">
					<li class="active">
						<a href="${nav.href }" class="menu_a"><i class="fa fa-external-link"></i>${nav.nav }</a>
					</li>
				</c:forEach>
				<li><span class="pull-right nav-search main-search" data-toggle="modal" data-target=".nav-search-box"><i class="fa fa-search"></i></span></li>
			</ul>
		</div>
	</div>
</nav>