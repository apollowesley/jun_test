<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!-- 本页面涉及的js函数，都在head.jsp页面中     -->
<div id="sidebar" class="menu-min">

	<!-- <div id="sidebar-shortcuts">

		<div id="sidebar-shortcuts-large">

			<button class="btn btn-small btn-success" onclick="changeMenu();"
				title="切换菜单">
				<i class="icon-pencil"></i>
			</button>

			<button class="btn btn-small btn-warning" title="数据字典"
				id="adminzidian" onclick="zidian();">
				<i class="icon-book"></i>
			</button>

			<button class="btn btn-small btn-danger" title="菜单管理" id="adminmenu"
				onclick="menu();">
				<i class="icon-folder-open"></i>
			</button>
		</div>
	</div> -->
	<!-- #sidebar-shortcuts -->
	<ul class="nav nav-list">
		<li class="active" id="fhindex"><a href="${ctx}/index/home"><i class="icon-home"></i><span>首页</span></a></li>
		<c:forEach items="${menuList}" var="menu">
		  <shiro:hasPermission name="${menu.permissionCode}">
			<li id="lm${menu.id }">
				<a style="cursor: pointer;" class="dropdown-toggle">
					<i class="${menu.icon == null ? 'icon-desktop' : menu.icon}"></i>
					<span>${menu.name }</span>
					<b class="arrow icon-angle-down"></b>
				</a>
				<ul class="submenu">
					<c:forEach items="${menu.subMenu}" var="sub">
					   <shiro:hasPermission name="${sub.permissionCode}">
						<c:choose>
							<c:when test="${not empty sub.url}">
								<li id="z${sub.id }">
									<a class="J_menuItem" style="cursor: pointer" href="${ctx}/${sub.url}"><i class="icon-double-angle-right"></i>${sub.name }</a>
								</li>
							</c:when>
							<c:otherwise>
								<li><a href="javascript:void(0);"><i class="icon-double-angle-right"></i>${sub.name}</a></li>
							</c:otherwise>
						</c:choose>
						</shiro:hasPermission>
					</c:forEach>
				</ul>
			</li>
			</shiro:hasPermission>
		</c:forEach>
	</ul>
	<!--/.nav-list-->

	<div id="sidebar-collapse">
		<i class="icon-double-angle-left"></i>
	</div>

</div>
<!--/#sidebar-->
