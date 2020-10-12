<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>成绩查询 - 期末考试成绩</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css" />
<link rel="stylesheet" href="css/queryResults.css" />
<link rel="stylesheet" href="css/reset.css" />
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
						成绩记录<span>></span><a href="FrontendPaperAction_showPaperType">成绩查询</a><span>></span><a
							href="#">全部试题</a>
					</div>
				</div>
				<div class="a_content">
					<s:if test="pageResult.list.isEmpty()">
						<p>没用成绩信息</p>
					</s:if>
					<s:else>
						<div class="text_content">
							<table class="record table-lay">
								<!-- table-lay   单元格固定比例宽度  -->
								<thead>
									<tr>
										<th>考试名称</th>
										<th>考试时间</th>
										<th>成绩 / 满分</th>
										<th>查看</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator var="userPaper" value="pageResult.list"
										status="sta">
										<tr>
											<td><s:property value="#userPaper.paper.title " /></td>
											<td><span>开始时间：<fmt:formatDate value="${userPaper.startTime}" pattern="yyyy-MM-dd hh:mm:ss" /></span> <br /> 
												<span>结束时间：<fmt:formatDate value="${userPaper.finishTime}" pattern="yyyy-MM-dd hh:mm:ss" /></span>
											</td>
											<td><s:property
													value="#userPaper.scoreArr==null?'0':#userPaper.scoreArr[#userPaper.scoreArr.length-1]" />/<s:property
													value="#userPaper.paper.allScore" /></td>
											<td><a class="btn btn-info btn-small" type="button"
												href="FrontendGradeAction_gradeDetailsPage?gid=${userPaper.id}">得分详情</a></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
						<s:set var="pageNum" value="pageResult.page.currentPage" />
						<s:set var="pageNumShown" value="pageResult.page.pageNumShown" />
						<s:url var="first" action="FrontendGradeAction_toQueryGradePage">
							<s:param name="pageNum" value="1"></s:param>
							<s:param name="paperType" value="paperType"></s:param>
						</s:url>

						<s:url var="next" action="FrontendGradeAction_toQueryGradePage">
							<s:param name="pageNum" value="%{#pageNum+1}"></s:param>
							<s:param name="paperType" value="paperType"></s:param>
						</s:url>
						<s:url var="pre" action="FrontendGradeAction_toQueryGradePage">
							<s:param name="pageNum" value="%{#pageNum-1}"></s:param>
							<s:param name="paperType" value="paperType"></s:param>
						</s:url>
						<s:url var="end" action="FrontendGradeAction_toQueryGradePage">
							<s:param name="pageNum" value="#pageNumShown"></s:param>
							<s:param name="paperType" value="paperType"></s:param>
						</s:url>
						<div class="change_page">
							<div class="page_sum fl">
								<h4>
									共<span>${pageNumShown}</span>页/当前<span id="currentPage">${pageNum }</span>页
								</h4>
							</div>
							<div class="page_nav fr">
								<a href="${end}">尾页</a> <a
									href="<s:property value="#pageNum>=#pageNumShown?'#':#next" />">下一页</a>
								<a href="<s:property value="#pageNum<=1?'#':#pre" />">上一页</a> <a
									href="${first}">首页</a>
							</div>
						</div>
					</s:else>
				</div>
				<!-- a_content 结束-->
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

	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#menu a").removeClass("active");
		$("#query").addClass("active");
	});
</script>
</body>
</html>