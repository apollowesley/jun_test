<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="myTags" uri="../myTags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>试卷列表</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css" />
<link rel="stylesheet" href="css/index.css" />
<link rel="stylesheet" href="css/reset.css" />
	<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#menu a").removeClass("active");
		var type = '${paperType}';
		if (!type) {
			$("#all").addClass("active");
		} else {
			$("#paper" + type).addClass("active");
		}
	});
</script>
</head>
<body>
	<jsp:include page="herad.jsp" />
	<!-- /navbar -->
	<div id="container">
		<!----------------  container  ----------------->
		<div class="content_body">
			<jsp:include page="left.jsp" />
			<!-- content_left 结束 -->
			<div class="content_right fr">
				<div class="content_head">
					<div class="breadcrumb">
						我的考试<span>></span>全部试卷
					</div>
				</div>
				<s:set var="paperList" value="pageResult.list"></s:set>
				<s:if test="#paperList.isEmpty()">目前没有任何考试!</s:if>
				<s:else>
					<s:set var="cells" value="5" />
					<div class="a_content">
						<div class="text_content">
							<s:iterator var="i" begin="0" end="%{#paperList.size()-1}"
								step="#cells">
								<ul>
									<s:iterator var="j" begin="0" end="%{#cells-1}" step="1">
										<s:set var="idx" value="#i+#j" />
										<s:if test="%{#idx<#paperList.size()}">
											<li <s:if test="%{#j!=0}"> class="mar_l"</s:if>>
												<div class="test">
													<s:property value="#idx + 1" />
													.
													<s:property value="#paperList[#idx].title" />
												</div>
												<p>
													<a
														href="FrontendPaperAction_getQuestions?pid=<s:property value="#paperList[#idx].id"/>&paperPage=questionPage">
														<button type="button" class="start">开始</button>
													</a>
												</p>
											</li>
										</s:if>
									</s:iterator>
								</ul>
							</s:iterator>
						</div>
						<div class="change_page" style="padding-bottom: 30px"
							align="right">
							<s:set var="page" value="pageResult.page"></s:set>
							<div class="page_sum fl">
								<h4>
									第<span id="currentPage">${pageResult.page.currentPage }</span>页
									/ 共<span id="pageNumShown">${pageResult.page.pageNumShown }</span>页
								</h4>
							</div>
							<ul class="pager">
								<myTags:pager submitType="form" numPerPage="${page.numPerPage}"
									pageNum="${page.currentPage}"
									url="FrontendPaperAction_toPaperPage"
									recordCount="${page.totalCount}"></myTags:pager>
							</ul>
						</div>
					</div>
				</s:else>
			</div>
			<!-- content_right 结束 -->
		</div>
		<!-- content_body 结束 -->
		<div id="l-extra">
			<!------ extra ----->
			<p>Copyright © 2014 成都工业学院</p>
		</div>
		<!-- /l-extra -->

	</div>
	<!-- /container 结束-->

</body>
</html>