<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<h2 class="contentTitle">编辑考试</h2>
<div class="pageContent"
	style="display: block; overflow: hidden; padding: 0 10px; line-height: 21px;">
	<s:set var="haveRight"
		value="@com.evil.util.ValidateUtil@hasRight('/ExamAction_doSaveOrUpdateExam')" />
	<form method="post"
		action="${haveRight?'ExamAction_doSaveOrUpdateExam':'#' }"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div layoutH="80">
			<table>
				<tbody style="margin: 5px">
					<br>
					<c:if test="${model.id!=null }">
						<tr>
							<td><label>考试id：</label></td>
							<td>
								<div class="unit">
									<input type="text" name="id" value="${model.id }"
										readonly="readonly" style="width: 200px" />
								</div> <br>
							</td>

						</tr>
					</c:if>
					<tr>
						<td>考试名称:</td>
						<td>
							<div class="unit">
								<input type="text" name="examName" value="${model.examName }"
									style="width: 200px" class="required" />
							</div> <br>
						</td>
					</tr>
					<tr>
						<td>考试时间:</td>
						<td>
							<div class="unit">
								<input type="number" name="examTime" min="0" step="10"
									value="${model.examTime}"
									class="required digits textInput error">分钟
							</div> <br>
						</td>
					</tr>
					<tr>
						<td>考试页解析?</td>
						<td><s:checkbox name="examParsing" theme="simple" />
							（在考试界面显示试题解析按钮，点击后显示试题答案及解析）。 <br></td>
					</tr>
					<tr>
						<td>前台显示?</td>
						<td><s:checkbox name="frontDeskShow" theme="simple" />
							（已经过期的考试或还未开放的考试可以先设置好，但不在前台显示。正式考试可以不显示）。 <br></td>
					</tr>
					<tr>
						<td>开放时间：</td>
						<td>
							<div class="unit">
								<input type="text" name="startTime" id="startTime" value="${exam.startTime==null?'2015-06-03 00:00:00':'exam.startTime' }"
									class="date textInput readonly valid"
									datefmt="yyyy-MM-dd HH:mm:ss" readonly="true">
							</div> <br>
						</td>
					</tr>
					<tr>
						<td>结束时间：</td>
						<td>
							<div class="unit">
								<input type="text" name="endTime" id="endTime"  value="${exam.endTime==null?'2099-07-03 00:00:00':'exam.startTime' }"
									class="date textInput readonly valid"
									datefmt="yyyy-MM-dd HH:mm:ss" readonly="true">
							</div> <br>
						</td>
					</tr>
					<tr>
						<td>试卷类型：</td>
						<td><select class="combox" name="examTypeId">
								<s:iterator var="t" value="examTypes">
									<option value="${t.id }"
										<c:if test="${exam.examType.id==t.id}">selected = "true"</c:if>>${t.getName() }${exam.examType.id}</option>
								</s:iterator>
						</select> <br> <br></td>
					</tr>
					<tr>
						<td>试卷：</td>
						<td>
							<div class="unit">
								<select class="combox" name="paperId">
									<s:iterator var="p" value="papers">
										<option value="${p.id }"
											<c:if test="${exam.paper.id==p.id}">selected = "true"</c:if>>${p.title }</option>
									</s:iterator>
								</select>
							</div> <br>
						</td>
					</tr>
					<tr>
						<td>显示考试须知?</td>
						<td><s:checkbox name="showTeamInstruction" theme="simple" />
							（进入考试后，首先显示考试须知）。 <br></td>
					</tr>
					<tr>
						<td>考试须知:</td>
						<td>
							<div class="unit">
								<textarea class="editor" name="TeamInstruction" rows="8"
									cols="100" tools="simple"></textarea>
							</div> <br>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><s:if test="#haveRight">
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">提交</button>
							</div>
						</div>
					</s:if> <s:else>
						<div class="buttonDisabled">
							<div class="buttonContent">
								<button type="button">提交</button>
							</div>
						</div>
					</s:else></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>
</html>