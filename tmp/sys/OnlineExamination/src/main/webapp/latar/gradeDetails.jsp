e<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>成绩详情</title>
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
.XO {
	color: red;
	font-weight: bolder;
	font-size: 25px;
	font-family:"微软雅黑";
}
</style>
</head>
<body>
	<div class="pageFormContent" layouth="30"
		style="padding-left: 5%; padding-right: 5%">
		<div class="panelBar" style="padding-left: 5px">
			<a class="button" href="javascript:$.printBox('gradeDetails')"><span>打印</span></a>
		</div>
		<fieldset style="background-color: #f5f3e5; padding-top: 20px">
			<s:set var="type" value="model.paper.itemTypeArr" />
			<s:set var="number" value="model.paper.itemNumberArr" />
			<s:set var="score" value="model.paper.itemScoreArr" />
			<s:set var="num" value="0" />
			<!-- content_left 结束 -->
			<div id="gradeDetails" class="paperMess" style="padding-left: 30px">
				<s:set var="num" value="0" />
				<s:if test="%{#type!=null&&#type.length>0}">
				<div class="pageFormContent nowrap" >
					<h1 class="title">试卷详情</h1>
					<p><label>试卷名称：</label>${model.paper.title}</p>
					<p><label>考生姓名：</label>${model.user.userName}</p>
					<p><label>考试时长：</label>${model.paper.exanTime}分钟</p>
					<p><label>开始时间：</label><fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd hh:mm:ss" /></p>
					<p><label>结束时间：</label><fmt:formatDate value="${finishTime}" pattern="yyyy-MM-dd hh:mm:ss" /></p>
					<p><label>考试成绩：</label><s:property
											value="model.scoreArr==null?'0':model.scoreArr[model.scoreArr.length-1]" />/<s:property
											value="model.paper.allScore" /></p>
				</div>
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
													<td><p class="mess"><s:property
																value="#idx+1" />.${questions[pos].title} </p>
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
																			class="answer-left">${questions[pos].answer}</span>
																				<span class="answer">考生填写：</span><span class="answer-left"><s:property
																			value="answerMap.get(questionList[#idx+#num].id).answers" /></span>
																			<span class="XO"><s:if test="answerMap.get(questionList[#idx+#num].id)!=null">
																					<s:property value="answerMap.get(questionList[#idx+#num].id).rights?'√':'×'" />
																				  </s:if> 
																				  <s:else>×</s:else>
																		    </span>
																		 </td>
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
																		<td>
																			<span class="answer">正确答案：</span><span class="answer-left">${questions[pos].answer}</span>
																			<span class="answer">考生填写：</span><span class="answer-left"><s:property
																			value="answerMap.get(questionList[#idx+#num].id).answers" /></span>
																			<span class="XO"><s:if test="answerMap.get(questionList[#idx+#num].id)!=null">
																				<s:property value="answerMap.get(questionList[#idx+#num].id).rights?'√':'×'" />
																				  </s:if> 
																				  <s:else>×</s:else>
																		    </span>
																		</td>
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