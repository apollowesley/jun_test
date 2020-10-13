<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<div class="sidebar-module">
	<ul class="nav nav-tabs sidebar-tabs" role="tablist">
		<li role="presentation" class="active"><a
			href="//www.zhyd.me/#home" aria-controls="home" role="tab"
			data-toggle="tab"><i class="fa fa-thumbs-o-up"></i>站长推荐</a></li>
		<li role="presentation"><a href="//www.zhyd.me/#profile"
			aria-controls="profile" role="tab" data-toggle="tab"><i
				class="fa fa-list"></i>近期文章</a></li>
		<li role="presentation"><a href="//www.zhyd.me/#messages"
			aria-controls="messages" role="tab" data-toggle="tab"><i
				class="fa fa-hand-peace-o"></i>随机文章</a></li>
	</ul>
	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="home">
			<ol class="list-unstyled">
				<c:forEach items="${journalTopper }" var="journal" varStatus="index">
					<li><span
						class="li-icon li-icon-${index.index>3?4:(index.index) }">${index.index+1 }</span>
						<a href="detail.html" title="" data-toggle="tooltip"
						data-placement="bottom" data-original-title="${journal.title }">
							${journal.title }</a></li>
				</c:forEach>
			</ol>
		</div>
		<div role="tabpanel" class="tab-pane" id="profile">
			<ol class="list-unstyled">
				<c:forEach items="${journalNewsd }" var="journal" varStatus="index">
					<li><span
						class="li-icon li-icon-${index.index>3?4:(index.index) }">${index.index+1 }</span>
						<a href="detail.html" title="" data-toggle="tooltip"
						data-placement="bottom" data-original-title="${journal.title }">
							${journal.title }</a></li>
				</c:forEach>
			</ol>
		</div>
		<div role="tabpanel" class="tab-pane" id="messages">
			<ol class="list-unstyled">
				<c:forEach items="${journalHosed }" var="journal" varStatus="index">
					<li><span
						class="li-icon li-icon-${index.index>3?4:(index.index) }">${index.index+1 }</span>
						<a href="detail.html" title="" data-toggle="tooltip"
						data-placement="bottom" data-original-title="${journal.title }">
							${journal.title }</a></li>
				</c:forEach>
			</ol>
		</div>
	</div>
</div>