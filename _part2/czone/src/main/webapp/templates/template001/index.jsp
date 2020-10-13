<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<html>
<head>
<jsp:include page="inc/header.jsp" />
</head>
<body>
	<!-- 顶部导航 -->
	<jsp:include page="inc/nav.jsp" />
	<div class="container custome-container">
		<!--[if lt IE 9]><div class="alert alert-danger topframe" role="alert">Oh My God！你的浏览器实在<strong>太太太太太太旧了</strong>，赶紧升级浏览器 <a target="_blank" class="alert-link" href="http://browsehappy.com">立即升级</a></div><![endif]-->
		<!-- 公告 -->
		<jsp:include page="inc/notice.jsp" />
		<div class="row">
			<jsp:include page="inc/articles.jsp" />
			<jsp:include page="inc/sidebar.jsp" />
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="inc/footer.jsp" />

</body>
</html>