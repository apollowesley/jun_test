<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>成绩查询 - 章节习题成绩-得分详情</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css" />
<link rel="stylesheet" href="css/queryResults.css" />
<link rel="stylesheet" href="css/reset.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#menu a").removeClass("active");
		$("#query").addClass("active");
	});
</script>
<style type="text/css"></style>
</head>
<body>
	<jsp:include page="herad.jsp" />
	<!-- /navbar -->
	<div id="container">
		<!----------------  container  ----------------->
		<div class="content_body">
			<jsp:include page="left.jsp" />
			<!-- content_left 结束 -->
			<div class="content_right_other fr">
				<div class="content_head">
					<div class="breadcrumb">
						成绩记录<span>></span><a href="FrontendPaperAction_showPaperType">成绩查询</a><span>></span><a
							href="FrontendPaperAction_showPaperType">全部试题</a><span>></span><a
							href="#"><s:property value="model.paper.title" /></a>
					</div>
				</div>
				<div class="a_content_other">
					<div class="text_content_other">
						<table class="record table-lay">
							<!-- table-lay   单元格固定比例宽度  -->
							<thead>
								<tr>
									<th>章节名称</th>
									<th>考试时间</th>
									<th>成绩 / 满分</th>
									<th>详情</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><s:property value="model.paper.title " /></td>
									<td><span>开始时间：<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd hh:mm:ss" /></span> <br /> 
										<span>结束时间：<fmt:formatDate value="${finishTime}" pattern="yyyy-MM-dd hh:mm:ss" /></span></td>
									<td><s:property
											value="model.scoreArr==null?'0':model.scoreArr[model.scoreArr.length-1]" />/<s:property
											value="model.paper.allScore" /></td>
									<td><span>单选题：<s:property
												value="model.scoreArr==null?'0':model.scoreArr[0]" />
									</span> <br /> <span>多选题：<s:property
												value="model.scoreArr==null?'0':model.scoreArr[1]" /></span> <br />
										<span>判断题：<s:property
												value="model.scoreArr==null?'0':model.scoreArr[2]" /></span> <br />
										<%-- <span>填空题：10</span> --%></td>
								</tr>

							</tbody>
						</table>
					</div>
					<!-- text_content_other 结束 -->
					<div class="myForm">
						<s:set var="type" value="model.paper.itemTypeArr" />
						<s:set var="number" value="model.paper.itemNumberArr" />
						<s:set var="score" value="model.paper.itemScoreArr" />
						<s:set var="num" value="0" />
						<s:if test="%{#type!=null&&#type.length>0}">
							<s:iterator var="i" begin="0" end="%{#type.length-1}" step="1">
								<div class="single_sel">
									<s:if test="%{#type[#i]==0}">
										<div class="breadcrumb">
											单选题(共
											<s:property value="#number[#type[#i]]" />
											小题，每题
											<s:property value="#score[#type[#i]]" />
											分)
										</div>
									</s:if>
									<s:elseif test="%{#type[#i]==1}">
										<div class="breadcrumb">
											多选题(共
											<s:property value="#number[#type[#i]]" />
											小题，每题
											<s:property value="#score[#type[#i]]" />
											分)
										</div>
									</s:elseif>
									<s:elseif test="%{#type[#i]==2}">
										<div class="breadcrumb">
											判断题(共
											<s:property value="#number[#type[#i]]" />
											小题，每题
											<s:property value="#score[#type[#i]]" />
											分)
										</div>
									</s:elseif>
									<div>
										<table >
											<tbody>
												<s:set var="questions" value="questionList.toArray()" />
												<s:iterator var="idx" begin="0"
													end="%{#number[#type[#i]]-1}" step="1">
													<s:set var="pos" value="#idx+#num" />
													<s:if test="%{#type[#i]<2}">
														<tr>
															<td><span><s:property value="#idx+1" />.${questions[pos].title}
															</span> <span class="sign"> <s:if
																		test="answerMap.get(questionList[#idx+#num].id)!=null">
																		<s:property
																			value="answerMap.get(questionList[#idx+#num].id).rights?'√':'×'" />
																	</s:if> <s:else>×</s:else>
															</span> <!----- 勾和叉显示 对错  ---->
																<div style="margin-left: 30px;">
																	<table>
																		<tbody>
																			<tr>
																				<td>A.&nbsp;<s:property
																						value="questionList[#idx+#num].optionA" />
																				</td>
																			</tr>
																			<tr>
																				<td>B.&nbsp;<s:property
																						value="questionList[#idx+#num].optionB" />
																				</td>
																			</tr>
																			<tr>
																				<td>C.&nbsp;<s:property
																						value="questionList[#idx+#num].optionC" />
																				</td>
																			</tr>
																			<tr>
																				<td>D.&nbsp;<s:property
																						value="questionList[#idx+#num].optionD" />
																				</td>
																			</tr>
																		</tbody>
																	</table>
																</div> <!-- 试题解析a标签加上 导航到 href="#analyze" data-toggle="modal" -->
																<div style="margin-left: 30px;">
																	<span class="anwer">正确答案：</span><span class="anwer_let">${questions[pos].answer}</span><span
																		class="anwer">;考生填写：</span><span class="anwer_let"><s:property
																			value="answerMap.get(questionList[#idx+#num].id).answers" />
																	</span> <br /> <span class="anwer">试题解析：</span><span
																		class="anwer_let">${questions[pos].content}</span>
																</div></td>
														</tr>
													</s:if>
													<s:elseif test="%{#type[#i]==2}">
														<tr>
															<td><span><s:property value="#idx+1" />.
																	${questions[pos].title}</span><span class="sign"><s:if
																		test="answerMap.get(questionList[#idx+#num].id)!=null">
																		<s:property
																			value="answerMap.get(questionList[#idx+#num].id).rights?'√':'×'" />
																	</s:if> <s:else>×</s:else></span> <!----- 勾和叉显示 对错  ---->
																<div style="margin-left: 30px;">
																	<span class="anwer">正确答案：</span><span class="anwer_let"><s:property
																			value="questionList[#idx+#num].answer=='right'?'正确':'错误'" /></span><span
																		class="anwer">；考生填写：</span><span class="anwer_let"><s:property
																			value="answerMap.get(questionList[#idx+#num].id).answers=='right'?'正确':'错误'" /></span>
																	<br /> <span class="anwer">试题解析：</span><span
																		class="anwer_let">${questions[pos].content}</span>
																</div></td>
														</tr>
													</s:elseif>
												</s:iterator>
												<s:set var="num" value="#num+#number[#type[#i]]" />
											</tbody>
										</table>
									</div>
								</div>
							</s:iterator>
						</s:if>
						<s:else>
								改试卷是空的
							</s:else>
					</div>
				</div>
				<!-- a_content_other 结束-->
			</div>
			<!-- content_right_other 结束 -->
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