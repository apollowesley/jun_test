<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>主页</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css" />
<link rel="stylesheet" href="css/test1.css" />
<link rel="stylesheet" href="css/reset.css" />
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript">
	var is_submit = false; 
	window.onbeforeunload=function checkLeave(e){ 
			e = e || window.event; //在firefox中的兼容
            if(!is_submit){ 
            	e.returnValue='确认试卷提交过才可以关闭窗口，否则考试信息将丢失，自负后果哦！'; 
            	} 
            	} //提交表单 
document.oncontextmenu = new Function("event.returnValue=false;"); //禁止右键功能,单击右键将无任何反应
document.onselectstart = new Function("event.returnValue=false;"); //禁止选择,也就是无法复
	function fromSumbit() {
	 is_submit = true;
		data=$("#answerForm").serialize();
  		$.ajax({  
			   type: "POST",  
			   async:false,
			   url: "EngagePaperAction_doEngagePaper?bingTime="+$('#timestart').text(),  
			   data: data,  
			   dataType: "json",  
			   success: function(msg){ 
				   $("#radioScore").text(msg.scores[0]);
				   $("#ChoiceScore").text(msg.scores[1]);
				   $("#judgeScore").text(msg.scores[2]);
				   $("#allScore").text(msg.scores[4]);
				   $('#gradeMess').attr('href','FrontendGradeAction_gradeDetailsPage?gid='+msg.gid); 
				   $('#submit').modal('show');
			   }
		}); 
	}
</script>
</head>
<body onLoad="time()" onkeydown="keyEvent()">
	<form id="answerForm" name="answerForm" method="post">
		<input type="hidden" name="pid" value="${model.id}" />
		<div class="navbar navbar-fixed-top">
			<!---------------- navbar-fixed-top ----------------->
			<div class="backcolor">
				<div class="head_content">
					<div class="head_logo">
						<img src="images/school5.png" />
					</div>
					<div class="title">
						<h1 style="color: #FFF; text-align: center;">在线考试系统</h1>
					</div>
					<div class="pull-right">
						<div class="name">
							<i><img src="images/user.png" />&nbsp;&nbsp;</i><a href="#">${sessionScope.user.userName}</a>
						</div>
					</div>
				</div>
				<!-- /container -->

			</div>
			<!-- /navbar-inner -->
		</div>
		<!-- /navbar -->
		<div id="container">
			<!----------------  container  ----------------->
			<div class="content_body">
				<div class="content_left fl">
					<s:set var="type" value="model.itemTypeArr" />
					<s:set var="number" value="model.itemNumberArr" />
					<s:set var="score" value="model.itemScoreArr" />
					<s:set var="num" value="0" />
					<s:if test="%{#type!=null&&#type.length>0}">
						<div class="n_position" style="overflow: auto">
							<s:iterator var="i" begin="0" end="%{#type.length-1}" step="1">
								<div class="single_hint">
									<s:if test="%{#type[#i]==0}">
										<div class="breadcrumb">单选题</div>
									</s:if>
									<s:elseif test="%{#type[#i]==1}">
										<div class="breadcrumb">多选题</div>
									</s:elseif>
									<s:elseif test="%{#type[#i]==2}">
										<div class="breadcrumb">判断题</div>
									</s:elseif>
									<div class="index">
										<table>
											<tbody>
												<s:set var="cells" value="4"></s:set>
												<s:iterator var="n" begin="1" end="%{#number[#type[#i]]}"
													step="#cells">
													<tr>
														<s:iterator var="j" begin="0" end="%{#cells-1}" step="1">
															<s:set var="idx" value="#n+#j" />
															<s:if test="%{#idx<=#number[#type[#i]]}">

																<td><a class="indexButton" id="answer-${idx+num}"
																	onClick="reply('#archor-<s:property value='#idx+#num' />');"><s:property
																			value="#idx" /></a></td>
															</s:if>
														</s:iterator>
													</tr>
												</s:iterator>
												<s:set var="num" value="#num+#number[#type[#i]]" />
											</tbody>
										</table>
									</div>
								</div>
							</s:iterator>
						</div>
						<div class="submit_quit">
							<a class="btn btn-info" id="btnSubmit" href="#"
								onclick="fromSumbit()">试卷提交</a>
						</div>
					</s:if>
				</div>
				<!-- content_left 结束 -->
				<div class="content_right fr">
					<div class="content_head">
						<div class="breadcrumb">
							我的考试<span>></span><a
								href="FrontendPaperAction_toPaperPage.action">全部试卷</a><span>></span>
							${model.title}
						</div>
					</div>
					<div class="a_content">
						<div class="myForm" style="overflow: auto">
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
											<table>
												<tbody>
													<s:set var="questions" value="questionList.toArray()" />
													<s:iterator var="idx" begin="0"
														end="%{#number[#type[#i]]-1}" step="1">
														<s:set var="pos" value="#idx+#num" />
														<s:if test="%{#type[#i]<2}">
															<tr
																id="archor-<s:property
																		value="#idx+#num+1" />"
																class="test_tr">
																<td><span><s:property value="#idx+1" />.${questions[pos].title
																		}
																</span>
																	<div
																		id="multi-select-<s:property value="#idx+#num+1" />"
																		style="margin-left: 30px;">
																		<table
																			class="threadColumn cell0 choiceTitle queSpanJSF">
																			<tbody>
																				<tr>
																					<td><input
																						type='<s:property value="#type[#i]<1?'radio':'checkbox' " />'
																						name="answer<s:property value="questionList[#idx+#num].id" />"
																						id="A<s:property value="#idx+#num+1" />" value="A"
																						<s:if test="%{#type[#i]<1}"> 
																					onChange='testSelectDial(<s:property value="#idx+#num+1" />,this.value)'
																				</s:if>
																						<s:else>
																					onChange='testMultiSelectDial(<s:property value="#idx+#num+1" />)'
																				</s:else>>
																						<label for="A<s:property value="#idx+#num+1" />">
																							A.&nbsp; <s:property
																								value="questionList[#idx+#num].optionA" />
																					</label></td>
																				</tr>
																				<tr>

																					<td><input
																						type='<s:property value="#type[#i]<1?'radio':'checkbox' " />'
																						name="answer<s:property value="questionList[#idx+#num].id" />"
																						id="B<s:property value="#idx+#num+1" />" value="B"
																						<s:if test="%{#type[#i]<1}"> 
																					onChange='testSelectDial(<s:property value="#idx+#num+1" />,this.value)'
																				</s:if>
																						<s:else>
																					onChange='testMultiSelectDial(<s:property value="#idx+#num+1" />)'
																				</s:else>>
																						<label for="B<s:property value="#idx+#num+1" />">
																							B.&nbsp; <s:property
																								value="questionList[#idx+#num].optionB" />
																					</label></td>
																				</tr>
																				<tr>
																					<td><input
																						type='<s:property value="#type[#i]<1?'radio':'checkbox' " />'
																						name="answer<s:property value="questionList[#idx+#num].id" />"
																						id="C<s:property value="#idx+#num+1" />" value="C"
																						<s:if test="%{#type[#i]<1}"> 
																					onChange='testSelectDial(<s:property value="#idx+#num+1" />,this.value)'
																				</s:if>
																						<s:else>
																					onChange='testMultiSelectDial(<s:property value="#idx+#num+1" />)'
																				</s:else>>
																						<label for="C<s:property value="#idx+#num+1" />">
																							C.&nbsp; <s:property
																								value="questionList[#idx+#num].optionC" />
																					</label></td>
																				</tr>
																				<tr>
																					<td><input
																						type='<s:property value="#type[#i]<1?'radio':'checkbox' " />'
																						name="answer<s:property value="questionList[#idx+#num].id" />"
																						id="D<s:property value="#idx+#num+1" />" value="D"
																						<s:if test="%{#type[#i]<1}"> 
																					onChange='testSelectDial(<s:property value="#idx+#num+1" />,this.value)'
																				</s:if>
																						<s:else>
																					onChange='testMultiSelectDial(<s:property value="#idx+#num+1" />)'
																				</s:else>>
																						<label for="D<s:property value="#idx+#num+1" />">
																							D.&nbsp; <s:property
																								value="questionList[#idx+#num].optionD" />
																					</label></td>
																				</tr>
																			</tbody>
																		</table>
																	</div> <!-- 试题解析a标签加上 导航到 href="#analyze" data-toggle="modal" -->
																	<input
																	id="s<s:property
																		value="#idx+#num+1" />"
																	type="checkbox" style="margin-left: 50px;"
																	onChange="markQuestion(<s:property
																		value="#idx+#num+1" />,this.checked)">
																	<label
																	for="s<s:property
																		value=" #idx+#num+1" />">标记</label></td>
															</tr>
														</s:if>
														<s:elseif test="%{#type[#i]==2}">
															<tr
																id="archor-<s:property
																		value="#idx+#num+1"/>"
																class="test2_tr">
																<td><span><s:property value="#idx+1" />.
																		${questions[pos].title}</span>
																	<div style="margin-left: 30px;">
																		<table
																			class="threadColumn cell0 choiceTitle queSpanJSF">
																			<tbody>
																				<tr>
																					<td><input type="radio"
																						name='answer<s:property value="questionList[#idx+#num].id" />'
																						id="right<s:property value="#idx+#num+1" />"
																						value="right"
																						onChange='testJudgeDial(<s:property value="#idx+#num+1" />,this.value)'>
																						<label
																						for="right<s:property value="#idx+#num+1" />">正确</label></td>
																				</tr>
																				<tr>
																					<td><input type="radio"
																						name='answer<s:property value="questionList[#idx+#num].id" />'
																						id="error<s:property value="#idx+#num+1" />"
																						value="error"
																						onChange='testJudgeDial(<s:property value="#idx+#num+1" />,this.value)'>
																						<label
																						for="error<s:property value="#idx+#num+1" />">错误</label></td>
																				</tr>
																			</tbody>
																		</table>
																	</div> <input
																	id="s<s:property
																		value="#idx+#num+1" />"
																	type="checkbox" style="margin-left: 50px;"
																	onChange="markQuestion(<s:property
																		value="#idx+#num+1" />,this.checked)">
																	<label
																	for="s<s:property
																		value="#idx+#num+1" />">标记</label></td>
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
						<!-------------- single_sel 结束 -------------------->
						<!-------------- multi_sel 结束 -------------------->
						<!-------------- fill_blank 结束 -------------------->
					</div>
				</div>
				<!-- a_content 结束 -->
				<!-------  提交成功弹出对话框  ------->
				<!------- 提交成功弹出对话框  结束 ------->
				<!-------  试题解析弹出对话框  ------->
				<!------- 试题解析弹出对话框  结束 ------->
			</div>
			<!-- content_right 结束 -->
		</div>
	</form>
	<!-------  提交成功弹出对话框  ------->
	<div class="modal hide fade" id="submit">
		<div class="modal-header">
			<h4>试卷提交成功</h4>
		</div>
		<div class="modal-body">
			<table id="myForm:scorePanel" class="generalInfo">
				<tbody>
					<tr>
						<td><label>单选题得分：</label></td>
						<td id="radioScore">0.0</td>
					</tr>
					<tr>
						<td><label>多选题得分：</label></td>
						<td id="ChoiceScore">0.0</td>
					</tr>
					<tr>
						<td><label>判断题得分：</label></td>
						<td id="judgeScore">0.0</td>
					</tr>
					<tr>
						<td><label>总分：</label></td>
						<td id="allScore">0.0</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="modal-footer">
			<a href="FormalTestLoginAction_doLoginOut" class="btn btn-primary">确定</a>
		</div>
	</div>
	</div>
	<!------- 提交成功弹出对话框  结束 ------->
	<!-- content_body 结束 -->
	<div id="l-extra">
		<!------ extra ----->
		<p>Copyright © 2014 成都工业学院</p>
	</div>
	<!-- /l-extra -->
	<div id="time">
		<span id="alltime">考试时长：</span> <span><span id="timeNum">${model.exanTime}</span>分钟</span>
		<br /> <span>开始时间：</span><span id="timestart"></span> <br /> <span>结束时间：</span><span
			id="timeend"></span> <br /> <br /> <span>现在时间：</span><span
			id="timespan"></span>
	</div>
	</div>
	<!-- /container 结束-->
	<script charset="gb2312" type="text/javascript" src="js/exam_case.js"></script>
</body>
</html>