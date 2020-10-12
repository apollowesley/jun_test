<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<h2 class="contentTitle">编辑试题</h2>
<div
	style="display: block; overflow: hidden; padding: 0 10px; line-height: 21px;">
	<s:set var="haveRight"
		value="@com.evil.util.ValidateUtil@hasRight('/QuestionsAction_savaOrUpdateQuestion')" />
	<form method="post"
		action="${haveRight?'QuestionsAction_savaOrUpdateQuestion':'#' }"
		class="pageForm  required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" name="pid" value="${pid}"> <input
			type="hidden" name="questionsType" value="${model.questionsType }">
		<s:set var="type" value="model.questionsType" />
		<s:set var="answer" value="model.answer"></s:set>
		<div layoutH="80">
			<table>
				<tbody>
					<tr>
						<c:if test="${model.id!=null }">
							<tr>
								<td><label>问题id：</label></td>
								<td>
									<div class="unit">
										<input type="text" name="id" value="${model.id }"
											readonly="readonly" style="width: 200px" />
									</div> <br>
								</td>

							</tr>
						</c:if>
						<td>题目名称:</td>
						<td>
							<div>
								<div class="unit">
									<textarea class="editor" name="title" rows="6" cols="55"
										tools="mfull">${model.title }</textarea>
								</div>
								<br>
							</div>
						</td>
					</tr>
					<tr>
						<td>试题解析:</td>
						<td>
							<div>
								<div class="unit">
									<textarea class="editor" name="content" rows="6" cols="55"
										tools="mfull">${model.content }</textarea>
								</div>
								<br>
							</div>
						</td>
					</tr>
					<tr>
						<s:if test="#type<2">
							<td>题目选项:</td>
							<td>
								<table class="list" width="100%">
									<thead>
										<tr>
											<th width="100">正确选项</th>
											<th width="80">选项标号</th>
											<th width="100">选项内容</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input name="answers"
												type='<s:property value="#type<1?'radio':'checkbox' " />'
												value='A'
												<s:property value='#answer.indexOf("A")!=-1?"checked":"" '/>></td>
											<td>A</td>
											<td><input name="optionA" type="text"
												value='${model.optionA}'></td>
										</tr>
										<tr>
											<td><input name="answers"
												type='<s:property value="#type<1?'radio':'checkbox' " />'
												value='B'
												<s:property value='#answer.indexOf("B")!=-1?"checked":"" '/>></td>
											<td>B</td>
											<td><input name="optionB" type="text"
												value='${model.optionB}'></td>
										</tr>
										<tr>
											<td><input name="answers"
												type='<s:property value="#type<1?'radio':'checkbox' " />'
												value='C'
												<s:property value='#answer.indexOf("C")!=-1?"checked":"" '/>></td>
											<td>C</td>
											<td><input name="optionC" type="text"
												value='${model.optionC}'></td>
										</tr>
										<tr>
											<td><input name="answers"
												type='<s:property value="#type<1?'radio':'checkbox' " />'
												value='D'
												<s:property value='#answer.indexOf("D")!=-1?"checked":"" '/>></td>
											<td>D</td>
											<td><input name="optionD" type="text"
												value='${model.optionD}'></td>
										</tr>
									</tbody>
								</table>
							</td>
						</s:if>
						<s:elseif test="#type==2">
							<td>题目答案</td>
							<td>
								<div style="margin-left: 30px;">
									<span class="choiceTitle"><input type="radio"
										name="answers" value="right"
										<s:property value="#answer=='right'?'checked':'' "/>>
										<label for="j1right">正确</label></span> <span class="choiceTitle">
										<input type="radio" name="answers" value="error"
										<s:property value="#answer=='error'?'checked':'' "/>>
										<label for="j2error">错误</label>
									</span>
								</div>
							</td>
						</s:elseif>
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