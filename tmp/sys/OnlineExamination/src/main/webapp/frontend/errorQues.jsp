<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>错题库</title>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/wrongQuestion.js"></script>

<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css" />
<link rel="stylesheet" href="css/queryResults.css" />
<link rel="stylesheet" href="css/reset.css" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#menu a").removeClass("active");
		$("#error").addClass("active");
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
						成绩记录<span>></span><a href="errorQues.html">错题库</a>
					</div>
				</div>
				<div class="a_content">
					<div class="text_content">
						<!-- 标签页 -->
						<!--  加个 div <div class="tabbable tabs-left"> 可以使选项卡在左边显示，tabs-right右边显示--->
						<ul class="nav nav-tabs">
							<!----- 还有nav-pills选项卡样式 ----->
							<li class="active"><a href="#" id="sin" data-toggle="tab"
								onclick="showTab(0,1);">单选题</a></li>
							<li><a href="#tab1" id="mul" onclick="showTab(1);"
								data-toggle="tab">多选题</a></li>
							<!--不仅要s有href#、id对应，还要有在a标签里加data-toggle="tab" -->
							<li><a href="#tab1" id="jud" data-toggle="tab"
								onclick="showTab(2,1);">判断题</a></li>
						<!-- 	<li><a href="#tab1" id="fill" data-toggle="tab"
								onclick="showTab(1,1);">填空题</a></li> -->
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab1">
								<s:if test="pageResult.list==null||pageResult.list.isEmpty()">
									<p>没有错题</p>
								</s:if>
								<s:else>
									<table class="record">
										<thead>
											<tr>
												<th>序号</th>
												<th>题目</th>
												<th>出错次数</th>
												<th>查看</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody class="he" id="content">
											<s:iterator var="errorQues" value="pageResult.list"
												status="sta">
												<s:set var="questions" value="errorQues.questions" />
												<tr>
													<td><s:property value="#sta.index +1" /></td>
													<td><div class="autoCut">${questions.title }</div></td>
													<!-- class="autoCut" 超出部分自动省略-->
													<td>${errorQues.correctNumber }</td>
													<td><a class="btn btn-info btn-small" type="button"
														onclick="showWrongQuestion('${questions.id}');">查看试题</a></td>
													<td><a class="btn btn-success btn-small" type="button"
														href="#" onclick="showDeleteDialog('${errorQues.id}');">删除</a></td>
												</tr>
											</s:iterator>
										</tbody>
									</table>
									<div class="change_page">
										<form>
											<input type="hidden" id="type" value="">
										</form>
										<div class="page_sum fl">
											<h4>
												第<span id="currentPage">${pageResult.page.currentPage }</span>页
												/ 共<span id="pageNumShown">${pageResult.page.pageNumShown }</span>页
											</h4>
										</div>
										<div class="page_nav fr">
											<a href="#" onclick="showPageQuestions('end')">尾页</a> <a
												href="#" onclick="showPageQuestions('next')">下一页</a> <a
												href="#" onclick="showPageQuestions('pre')">上一页</a> <a
												href="#" onclick="showPageQuestions('first')">首页</a>
										</div>
									</div>
								</s:else>
							</div>
							<!-- 选项卡内容放在这个div里 class="active" 当前选中的选项卡内容-->
						</div>
						<!-- 标签页 end-->
					</div>
					<!-- text_content 结束 -->
				</div>
				<!-- a_content 结束-->
			</div>
			<!-- content_right 结束 -->
			<!-------  试题解析弹出对话框  ------->
			<div class="modal hide fade" id="QuestionDiaolg">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 id="title">查看试题</h4>
				</div>
				<div class="modal-body">
					<span id="myForm:displayChoice"></span> <span class="choiceTitle"
						id="questionTitle">${questions.title }</span>
					<div id="qtype" style="margin-left: 30px;">
						<span class="choiceTitle"> A.&nbsp; <span id="answerA">${questions.optionA }</span></span>
						<br> <span class="choiceTitle"> B.&nbsp; <span
							id="answerB">${questions.optionB}</span></span> <br> <span
							class="choiceTitle"> C.&nbsp; <span id="answerC">${questions.optionC }</span></span>
						<br> <span class="choiceTitle"> D.&nbsp; <span
							id="answerD">${questions.optionD}</span></span> <br>
					</div>
					<hr />
					<div style="margin-left: 0px;">
						<span class="answer">正确答案：</span> <span
							style="color: #356AA0; font-weight: bold;" id="questionAnswer">
						</span>&nbsp;
						<hr />
						<span class="answer" id="analysis">试题解析：</span> <span
							style="color: #356AA0;" id="questionAnalysis">${questions.content }</span>
						<br>
					</div>
				</div>

				<div class="modal-footer">
					<a href="#" class="btn btn-primary" data-dismiss="modal">确定</a>
					<!-- data-dismiss="modal" 关闭对话框  -->
				</div>
			</div>
			<!------- 试题解析弹出对话框  结束 ------->
			<!-------  删除弹出对话框  ------->
			<div class="modal hide fade" id="remove">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h3>提示</h3>
				</div>
				<div class="modal-body">
					<p>您确定要删除该条记录吗？</p>
				</div>
				<div class="modal-footer">
					<input type="hidden" id="deleteQuestion" value="" /> <a href="#"
						class="btn btn-primary" onclick="deleteWrongQuestion();">确定</a> <a
						href="#" class="btn btn-primary" data-dismiss="modal">取消</a>
					<!-- data-dismiss="modal" 点击“取消”时关闭对话框  -->
				</div>
			</div>
			<!------- /end 删除弹出对话框  ------->

			<!-------  结果信息对话框  ------->
			<div class="modal hide fade" id="resultMeg">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h3>提示</h3>
				</div>
				<div class="modal-body">
					<p id="hiht"></p>
				</div>
				<div class="modal-footer">
					<input type="hidden" id="deleteQuestion" value="" /> <a href="#"
						class="btn btn-primary" onclick="window.location.reload();">确定</a>
					<!-- data-dismiss="modal" 点击“取消”时关闭对话框  -->
				</div>
			</div>
			<!------- /end 结果信息对话框  ------->
		</div>
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