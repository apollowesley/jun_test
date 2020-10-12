<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>预览</title>
<style type="text/css">
.title {
	font-size: 16px;
	font-weight: bold;
}

.titlebar {
	display: block;
	font-size: 1.27em;
	-webkit-margin-before: 1em;
	-webkit-margin-after: 1em;
	-webkit-margin-start: 0px;
	-webkit-margin-end: 0px;
	font-weight: bold;
}

.mess {
	font-size: 14px;
	font-weight: normal;
	float: left;
	padding: 0 5px;
	line-height: 21px;
}

.answer {
	font-style: normal;
	font-size: 14px;
	color: brown;
	padding: 0 5px;
	line-height: 21px;
}

.answer-left {
	color: green;
	font-weight: bold;
}
</style>
</head>
<body>
	<div class="pageFormContent" layouth="30"
		style="padding-left: 5%; padding-right: 5%">
		<div class="panelBar" style="padding-left: 5px">
			<a class="button" href="javascript:$.printBox('previewPaper')">&nbsp;&nbsp;&nbsp;<span>打印</span></a>
			<a class="button" href="javascript:$.printBox('answerResolve')"><span>打印答案及解析</span></a>
		</div>
		<fieldset style="background-color: #f5f3e5; padding-top: 20px;">
			<s:set var="type" value="model.itemTypeArr" />
			<s:set var="number" value="model.itemNumberArr" />
			<s:set var="score" value="model.itemScoreArr" />
			<s:set var="num" value="0" />
			<!-- content_left 结束 -->
			<div id="previewPaper" class="paperMess" style="padding-left: 30px">
				<s:set var="num" value="0" />
				<s:if test="%{#type!=null&&#type.length>0}">
					<h1 class="title">试卷名称：${model.title }</h1>
					<div style="padding-bottom: 10px">
						<s:iterator var="i" begin="0" end="%{#type.length-1}" step="1">
							<s:if test="%{#type[#i]==0}">
								<h3 class="titlebar">
									※单选题(共
									<s:property value="#number[#type[#i]]" />
									小题,每题
									<s:property value="#score[#type[#i]]" />
									分)
								</h3>
							</s:if>
							<s:elseif test="%{#type[#i]==1}">
								<h3 class="titlebar">
									※多选题(共
									<s:property value="#number[#type[#i]]" />
									小题,每题
									<s:property value="#score[#type[#i]]" />
									分)
								</h3>
							</s:elseif>
							<s:elseif test="%{#type[#i]==2}">
								<h3 class="titlebar">
									※判断题(共
									<s:property value="#number[#type[#i]]" />
									小题,每题
									<s:property value="#score[#type[#i]]" />
									分)
								</h3>
							</s:elseif>
							<div>
								<table>
									<tbody>
										<s:set var="questions" value="questionList.toArray()" />
										<s:iterator var="idx" begin="0" end="%{#number[#type[#i]]-1}"
											step="1">
											<s:set var="pos" value="#idx+#num" />
											<s:if test="%{#type[#i]<2}">
												<tr>
													<td><span class="mess"><s:property
																value="#idx+1" />.${questions[pos].title}</span>
														<div style="margin-left: 30px;">
															<table style="width: 100%">
																<tbody>
																	<tr>
																		<td><span class="mess"> A.&nbsp; <s:property
																					value="questionList[#idx+#num].optionA" /></span></td>
																	</tr>
																	<tr>
																		<td><span class="mess">B.&nbsp; <s:property
																					value="questionList[#idx+#num].optionB" /></span></td>
																	</tr>
																	<tr>
																		<td><span class="mess">C.&nbsp; <s:property
																					value="questionList[#idx+#num].optionC" /></span></td>
																	</tr>
																	<tr>
																		<td><span class="mess">D.&nbsp; <s:property
																					value="questionList[#idx+#num].optionD" /></span></td>
																	</tr>
																</tbody>
															</table>
														</div></td>
												</tr>
											</s:if>
											<s:elseif test="%{#type[#i]==2}">
												<tr>
													<td height="20px"><span class="mess"><s:property
																value="#idx+1" />.${questions[pos].title}<br></span></td>
												</tr>
											</s:elseif>
										</s:iterator>
										<s:set var="num" value="#num+#number[#type[#i]]" />
									</tbody>
								</table>
							</div>
						</s:iterator>
					</div>
				</s:if>
				<s:else>
								改试卷是空的
							</s:else>
			</div>
		</fieldset>
		<fieldset style="background-color: #f5f3e5; padding-top: 20px">
			<s:set var="type" value="model.itemTypeArr" />
			<s:set var="number" value="model.itemNumberArr" />
			<s:set var="score" value="model.itemScoreArr" />
			<s:set var="num" value="0" />
			<!-- content_left 结束 -->
			<div id="answerResolve" class="paperMess" style="padding-left: 30px">
				<s:set var="num" value="0" />
				<s:if test="%{#type!=null&&#type.length>0}">
					<h1 class="title">试卷答案解析</h1>
					<div style="padding-bottom: 10px">
						<s:iterator var="i" begin="0" end="%{#type.length-1}" step="1">
							<s:if test="%{#type[#i]==0}">
								<h3 class="titlebar">
									※单选题(共
									<s:property value="#number[#type[#i]]" />
									小题,每题
									<s:property value="#score[#type[#i]]" />
									分)
								</h3>
							</s:if>
							<s:elseif test="%{#type[#i]==1}">
								<h3 class="titlebar">
									※多选题(共
									<s:property value="#number[#type[#i]]" />
									小题,每题
									<s:property value="#score[#type[#i]]" />
									分)
								</h3>
							</s:elseif>
							<s:elseif test="%{#type[#i]==2}">
								<h3 class="titlebar">
									※判断题(共
									<s:property value="#number[#type[#i]]" />
									小题,每题
									<s:property value="#score[#type[#i]]" />
									分)
								</h3>
							</s:elseif>
							<div>
								<table>
									<tbody>
										<s:set var="questions" value="questionList.toArray()" />
										<s:iterator var="idx" begin="0" end="%{#number[#type[#i]]-1}"
											step="1">
											<s:set var="pos" value="#idx+#num" />
											<s:if test="%{#type[#i]<2}">
												<tr>
													<td><span class="mess"><s:property
																value="#idx+1" />.${questions[pos].title}</span>
														<div style="margin-left: 30px;">
															<table style="width: 100%">
																<tbody>
																	<tr>
																		<td><span class="mess"> A.&nbsp; <s:property
																					value="questionList[#idx+#num].optionA" /></span></td>
																	</tr>
																	<tr>
																		<td><span class="mess">B.&nbsp; <s:property
																					value="questionList[#idx+#num].optionB" /></span></td>
																	</tr>
																	<tr>
																		<td><span class="mess">C.&nbsp; <s:property
																					value="questionList[#idx+#num].optionC" /></span></td>
																	</tr>
																	<tr>
																		<td><span class="mess">D.&nbsp; <s:property
																					value="questionList[#idx+#num].optionD" /></span></td>
																	</tr>
																	<tr>
																		<td><span class="answer">正确答案：</span><span
																			class="answer-left">${questions[pos].answer}</span></td>
																	</tr>
																	<tr>
																		<td><span class="answer">试题解析：</span><span
																			class="answer-left">${questions[pos].content}</span></td>
																	</tr>
																</tbody>
															</table>
														</div></td>
												</tr>
											</s:if>
											<s:elseif test="%{#type[#i]==2}">
												<tr>
													<td height="20px"><span class="mess"><s:property
																value="#idx+1" />.${questions[pos].title}<br></span>
														<div style="margin-left: 30px;">
															<table style="width: 100%">
																<tbody>
																	<tr>
																		<td><span class="answer">正确答案：</span><span
																			class="answer-left">${questions[pos].answer}</span></td>
																	</tr>
																	<tr>
																		<td><span class="answer">试题解析：</span><span
																			class="answer-left">${questions[pos].content}</span></td>
																	</tr>
																</tbody>
															</table>
														</div></td>
												</tr>
											</s:elseif>
										</s:iterator>
										<s:set var="num" value="#num+#number[#type[#i]]" />
									</tbody>
								</table>
							</div>
						</s:iterator>
					</div>
				</s:if>
				<s:else>
								改试卷是空的
							</s:else>
			</div>
		</fieldset>
	</div>
</body>
</html>