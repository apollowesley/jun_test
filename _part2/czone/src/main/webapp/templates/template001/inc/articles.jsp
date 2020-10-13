<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<div class="col-sm-8 blog-main">
	<c:forEach items="${pager.data }" var="journal">
		<article class="fade-in">
			<figure class="thumbnail">
				<a href="${basePath}journals.do?id=${journal.id }"> <img width="150"
					height="150" data-original="${journal.ico }"
					class="img-responsive lazy-img" alt="${journal.title }"
					src="${journal.ico }" style="display: block;">
				</a>
				<span class="cat"><a href="//www.zhyd.me/type/2">${journal.tags }</a></span>
			</figure>
			<header class="entry-header">
				<h2 class="entry-title">
					<a href="${basePath}journals.do?id=${journal.id }" rel="bookmark" title=""
						data-toggle="tooltip" data-placement="bottom"
						data-original-title="点击查看文章详情">${journal.title }</a>
				</h2>
			</header>
			<div class="entry-content">
				<div class="archive-content">${journal.brief }</div>
				<span class="title-l"></span> <span class="entry-meta"> <span
					class="date" title="" data-toggle="tooltip" data-placement="bottom"
					data-original-title="文章发表日期"><i
						class="fa fa-calendar-o fa-fw"></i>2018-04-08</span> <span class="views"
					title="" data-toggle="tooltip" data-placement="bottom"
					data-original-title="文章阅读次数"><i class="fa fa-eye fa-fw"></i>浏览(${journal.views })</span>
					<span class="comment" title="" data-toggle="tooltip"
					data-placement="bottom" data-original-title="文章评论次数"> <a
						href="${basePath}journals.do?id=${journal.id }#comment-box"
						rel="external nofollow"> <i class="fa fa-comments-o fa-fw"></i>评论(0)
					</a>
				</span>
				</span>
				<div class="clear"></div>
				<span class="entry-more"> <a
					href="${basePath}journals.do?id=${journal.id }" rel="bookmark">阅读全文</a>
				</span>
			</div>
		</article>
	</c:forEach>
	<nav>
		<ul class="pager page-btn" data-url="${basePath}"
			data-search="false">
			<c:if test="${pager.hasPreviousPage}">
				<li><a class="pointer" data-page="${pager.prePage}"><i class="fa fa-angle-double-left"></i></a></li>
			</c:if>
			<c:forEach begin="1" end="${pager.totalPage}" var="pageNum">
				<li><a class="pointer <c:if test="${pager.currentPage == pageNum}">active</c:if>" data-page="${pageNum}">${pageNum}</a></li>
			</c:forEach>
			<c:if test="${pager.hasNextPage}">
				<li><a class="pointer" data-page="${pager.nextPage}"><i class="fa fa-angle-double-right"></i></a></li>
			</c:if>
		</ul>
	</nav>
</div>