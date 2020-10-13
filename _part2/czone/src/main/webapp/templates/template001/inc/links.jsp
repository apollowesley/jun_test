<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<div class="sidebar-module">
	<h5 class="sidebar-title">
		<i class="fa fa-link icon"></i><strong>友情链接</strong>
	</h5>
	<ul class="ul-default">
		<c:forEach items="${links}" var="link">
			<li><a href="${link.href}">${link.title}</a></li>
		</c:forEach>
	</ul>
</div>