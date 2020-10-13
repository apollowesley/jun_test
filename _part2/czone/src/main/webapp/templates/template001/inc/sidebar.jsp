<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 侧边栏 --%>
<div class="col-sm-3 blog-sidebar">
	<jsp:include page="author.jsp" />
	<div class="sidebar-module article-module hide" style="top: 0;">
		<h5 class="sidebar-title">
			<i class="fa fa-book icon"></i><strong>本文目录</strong> <i
				class="fa fa-close pull-right close-article-menu hide pointer"></i>
		</h5>
		<div id="article-menu">
			<ul class="list-unstyled">
			</ul>
		</div>
	</div>
	<jsp:include page="tags.jsp" />
	<jsp:include page="comments.jsp" />
	<jsp:include page="topper.jsp" />
	<div class="clear"></div>
	<jsp:include page="statistics.jsp" />
	<div class="clear"></div>
	<jsp:include page="links.jsp" />
</div>