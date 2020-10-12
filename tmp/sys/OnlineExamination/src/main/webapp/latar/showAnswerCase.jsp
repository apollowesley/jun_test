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
<link href="themes/css/button.css" rel="stylesheet" type="text/css">
<title>作答情况</title>
<style type="text/css">
.titlebar {
	display: block;
	font-size: 1.2em;
	-webkit-margin-before: 1em;
	-webkit-margin-after: 1em;
	-webkit-margin-start: 0px;
	-webkit-margin-end: 0px;
	font-weight: bold;
	background: #b8d0d6;
	padding: 10px;
}

.questionContent {
	border: 1px solid #dfd9c3;
}

.qtitle {
	margin-left: 15px;
	margin-top: 5px;
	margin-bottom: 5px;
}

.qtitle span {
	font-size: 14px !important;
}

.options {
	font-weight: normal;
	padding: 0 5px;
	line-height: 21px;
}

.Bar, .Bars {
	position: relative;
	width: 200px;
	/* 宽度 */
	border: 1px solid #B1D632;
	padding: 1px;
	margin: 0 5px 5px 5px;
}

.Bar div, .Bars div {
	display: block;
	position: relative;
	background: #00F; /* 进度条背景颜色 */
	color: #333333;
	height: 10px; /* 高度 */
	line-height: 20px;
	/* 必须和高度一致，文本才能垂直居中 */
}

.Bars div {
	background: #090
}

.Bar div span, .Bars div span {
	position: absolute;
	width: 200px;
	/* 宽度 */
	text-align: center;
	font-weight: bold;
}
</style>
</head>
<body>
	<div class="pageFormContent" style="margin: 15px" layouth="30">
		<div style="height: 30px">
			<a class="buttons small orange" href="javascript:$.printBox('answerAnalyze')"><span>打印分析</span></a>
		</div>
		<div id="answerAnalyze">
		<hr
			style="margin-top: 10px; margin-bottom: 10px; height: 3px; border: 0px; background-color: #D5D5D5; color: #D5D5D5;" />
		<table style="width: 90%;">
			<tbody>
				<tr>
					<td>试卷名称：${title }</td>
					<td>考试时间：${exanTime }</td>
					<td>成绩数：${gradeNum }</td>
				</tr>
			</tbody>
		</table>
		<hr
			style="margin-top: 10px; margin-bottom: 10px; height: 3px; border: 0px; background-color: #D5D5D5; color: #D5D5D5;" />
		<div class="questionContent">
			<s:set var="type" value="model.itemTypeArr" />
			<s:set var="number" value="model.itemNumberArr" />
			<s:set var="score" value="model.itemScoreArr" />
			<s:set var="num" value="0" />
			<s:iterator var="i" begin="0" end="%{#type.length-1}" step="1">
				<s:if test="%{#type[#i]==0}">
					<h3 class="titlebar" style="margin-top: 0px">
						※单选题(共
						<s:property value="#number[#type[#i]]" />
						小题,每题
						<s:property value="#score[#type[#i]]" />
						分)
					</h3>
				</s:if>
				<s:if test="%{#type[#i]==1}">
					<h3 class="titlebar" style="margin-top: 0px">
						※多选题(共
						<s:property value="#number[#type[#i]]" />
						小题,每题
						<s:property value="#score[#type[#i]]" />
						分)
					</h3>
				</s:if>
				<s:if test="%{#type[#i]==2}">
					<h3 class="titlebar" style="margin-top: 0px">
						※判断题(共
						<s:property value="#number[#type[#i]]" />
						小题,每题
						<s:property value="#score[#type[#i]]" />
						分)
					</h3>
				</s:if>
				<s:set var="questionModelArr" value="questionModels.toArray()" />
				<s:iterator var="idx" begin="0" end="%{#number[#type[#i]]-1}"
					step="1">
					<s:set var="pos" value="#idx+#num" />
					<s:set var="count" value="#questionModelArr[#pos].count" />
					<s:set var="rightCount" value="#questionModelArr[#pos].rightCount" />
					<s:set var="optionsCount" value="#questionModelArr[#pos].optionsCount" />
					<s:if test="%{#type[#i]==0}">
						<div class="qtitle">
							<span><s:property value="#idx+1" />.${questionModelArr[pos].questions.title }</span>
							<div style="margin-top: 5px;margin-left: 35px;color: green;">正确率:${count==0?0:rightCount/count*100 }%</div>
							<div style="margin-left: 30px">
								<table style="width: 40%">
									<thead>
										<tr>
											<td><span class="options">A. ${questionModelArr[pos].questions.optionA }</span>
											</td>
										</tr>
										<tr>
											<td><div class="Bars">
													<div style="width: ${count==0?0:optionsCount[0]/count*100 }%;">
														<span>${count==0?0:optionsCount[0]/count*100 }%</span>
													</div>
												</div></td>
											<td align="center"><span style="color: red;">(${optionsCount[0]})</span></td>
										</tr>
										<tr>
											<td><span class="options">B. ${questionModelArr[pos].questions.optionB} </span></td>
										</tr>
										<tr>
											<td><div class="Bars">
													<div style="width: ${count==0?0:optionsCount[1]/count*100 }%;">
														<span>${count==0?0:optionsCount[1]/count*100 }%</span>
													</div>
												</div></td>
											<td align="center"><span style="color: red;">(${optionsCount[1]})</span></td>
										</tr>
										<tr>
											<td><span class="options">C. ${questionModelArr[pos].questions.optionC} </span></td>
										</tr>
										<tr>
										<td><div class="Bars">
													<div style="width: ${count==0?0:optionsCount[2]/count*100 }%;">
														<span>${count==0?0:optionsCount[2]/count*100 }%</span>
													</div>
												</div></td>
											<td align="center"><span style="color: red;">(${optionsCount[2]})</span></td>
										</tr>
										<tr>
											<td><span class="options">D. ${questionModelArr[pos].questions.optionD} </span></td>
										</tr>
										<tr>
										<td><div class="Bars">
													<div style="width: ${count==0?0:optionsCount[3]/count*100 }%;">
														<span>${count==0?0:optionsCount[3]/count*100 }%</span>
													</div>
												</div></td>
											<td align="center"><span style="color: red;">(${optionsCount[3]})</span></td>
										</tr>
										<tr>
											<td><span style="color: red;">正确答案: </span><span
												style="color: green;">${questionModelArr[pos].questions.answer}</span></td>
										</tr>
										<tr>
											<td><span style="color: red;">试题解析: </span><span
												style="color: green;">${questionModelArr[pos].questions.content}</span></td>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</s:if>
					<s:elseif test="%{#type[#i]==1}">
					<div class="qtitle">
							<span><s:property value="#idx+1" />.${questionModelArr[pos].questions.title }</span>
							<div style="margin-top: 5px;margin-left: 35px;color: green;">正确率:${count==0?0:rightCount/count*100 }%</div>
							<div style="margin-left: 30px">
								<table style="width: 40%">
									<thead>
										<tr>
											<td><span class="options">A. ${questionModelArr[pos].questions.optionA }</span>
											</td>
										</tr>
										<tr>
											<td><span class="options">B. ${questionModelArr[pos].questions.optionB} </span></td>
										</tr>
										<tr>
											<td><span class="options">C. ${questionModelArr[pos].questions.optionC} </span></td>
										</tr>
										<tr>
											<td><span class="options">D. ${questionModelArr[pos].questions.optionD} </span></td>
										</tr>
										<tr>
											<td><span style="color: red;">正确答案: </span><span
												style="color: green;">${questionModelArr[pos].questions.answer}</span></td>
										</tr>
										<tr>
											<td><span style="color: red;">试题解析: </span><span
												style="color: green;">${questionModelArr[pos].questions.content}</span></td>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</s:elseif>
					<s:elseif test="%{#type[#i]==2}">
										<div class="qtitle">
							<span><s:property value="#idx+1" />.${questionModelArr[pos].questions.title }</span>
							<div style="margin-top: 5px;margin-left: 35px;color: green;">正确率:${count==0?0:rightCount/count*100 }%</div>
							<div style="margin-left: 30px">
								<table style="width: 40%">
									<thead>
										<tr>
											<td><span style="color: red;">正确答案: </span><span
												style="color: green;">${questionModelArr[pos].questions.answer}</span></td>
										</tr>
										<tr>
											<td><span style="color: red;">试题解析: </span><span
												style="color: green;">${questionModelArr[pos].questions.content}</span></td>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</s:elseif>
				</s:iterator>
				<s:set var="num" value="#num+#number[#type[#i]]" />
			</s:iterator>
		</div>
		</div>
	</div>
</body>
</html>