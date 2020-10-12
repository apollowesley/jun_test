<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>成绩查询</title>
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
						成绩记录<span>></span><a href="FrontendPaperAction_showPaperType">成绩查询</a>
					</div>
				</div>
				<div class="a_content">
					<div class="text_content">
						<table class="record">
							<thead>
								<tr>
									<th>考试类别</th>
									<th>已做 / 总共</th>
									<th>查看</th>
								</tr>
							</thead>
							<tbody class="he">
								<s:iterator var="paperType" value="typeList">
									<tr>
										<td>${paperType.paperTypeNmae }</td>
										<td>${paperType.finshNum }/${paperType.allNum }</td>
										<td><a class="btn btn-info btn-small" type="button"
											href="FrontendGradeAction_toQueryGradePage?paperType=${paperType.paperTypeId}">查看详情</a></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
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