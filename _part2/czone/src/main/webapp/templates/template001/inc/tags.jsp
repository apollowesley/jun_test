<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="sidebar-module">
	<h5 class="sidebar-title">
		<i class="fa fa-tags icon"></i><strong>文章标签</strong>
	</h5>
	<ul class="list-unstyled list-inline">
		<c:forEach items="${journalTags}" var="tag">
			<li class="tag-li">
				<a class="btn btn-default btn-xs"
								  href="//www.zhyd.me/tag/1" title="" data-toggle="tooltip"
								  data-placement="bottom" data-original-title="${tag.tag}">${tag.tag}
				</a>
			</li>
		</c:forEach>
	</ul>
</div>