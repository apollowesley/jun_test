<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
<%@ taglib prefix="fn" uri="/WEB-INF/tld/fn.tld"%>
<nav class="breadcrumb">
	<div class="notify">
		<i class="fa fa-volume-up"></i>
	</div>
	<div id="scrolldiv">
		<div class="scrolltext">
			<ul class="list-unstyled" style="margin-top: 0px;">
				<c:forEach items="${notices }" var="notice">
					<li class="scrolltext-title">${notice.msg }</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</nav>