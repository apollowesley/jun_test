<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//labelD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.labeld">
<html>
<head>
<script type="text/javascript" src="customJs/lookupCallback.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>智能添加试卷</title>
<script type="text/javascript">
	function statisticalResult() {
		var singleAllNum = 0, singleAllScore = 0;
		var multipleAllNum = 0, multipleAllScore = 0;
		var judgeAllNum = 0, judgeAllScore = 0;
		var num = $("select[name$='singleNum']").length;
		$("select[name$='singleNum']").each(function(i) {
			singleAllNum += parseInt($(this).val());
		});
		$("select[name$='multipleNum']").each(function(i) {
			multipleAllNum += parseInt($(this).val());
		});
		$("select[name$='judgeNum']").each(function(i) {
			judgeAllNum += parseInt($(this).val());
		});
		singleAllScore = singleAllNum * parseFloat($("#singleScore").val());
		multipleAllScore = multipleAllNum * parseFloat($("#multipleScore").val());
		judgeAllScore = judgeAllNum * parseFloat($("#judgeScore").val());
		$("#singleAllScore").text(singleAllScore);
		$("#multipleAllScore").text(multipleAllScore);
		$("#judgeAllScore").text(judgeAllScore);
		$("#total").text(singleAllScore + multipleAllScore + judgeAllScore);
	}
</script>
</head>

<body>

	<div class="pageContent">
		<s:set var="haveRight"
			value="@com.evil.util.ValidateUtil@hasRight('/IntelligentPaperAction_doIntelligentAddPaper')" />
		<form method="post"
			action="${haveRight?'IntelligentPaperAction_doIntelligentAddPaper':'#' }"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,dialogAjaxDone)">
			<div class="pageFormContent nowrap" layoutH="56"
				style="overflow: auto;">
				<!-- 		NAME,parentId,url,ORDER,content -->
				<fieldset>
					<legend>试卷设置：</legend>
					<p>
						<label>试卷名称：</label> <input type="text" name="title"
							value="${model.title}" style="width: 200px" />
					</p>
					<p>
						<label>考试时间(分钟)：</label> <input type="number" name="exanTime"
							min="0" step="10" value="${model.exanTime}"
							class="required digits textInput error">
					</p>
					<p>
						<label>考试解析：</label>
						<s:checkbox name="examParsing" theme="simple" />
					</p>
					<p>
						<label>开放时间：</label> <input type="text" name="startTime"
							id="startTime" value="2015-06-03 00:00:00" class="date"
							datefmt="yyyy-MM-dd HH:mm:ss" readonly="true">
					</p>
					<p>
						<label>结束时间：</label> <input type="text" name="endTime"
							id="endTime" value="2015-07-03 00:00:00" class="date"
							datefmt="yyyy-MM-dd HH:mm:ss" readonly="true">
					</p>
					<p>
						<label>试卷类型：</label> <select class="combox" name="paperType">
							<s:iterator var="t" value="#application['paperTypes']">
								<option value="${t.getValue() }"
									<c:if test="${paperType==t.getValue()}">selected = "true"</c:if>>${t.getName() }</option>
							</s:iterator>
						</select>
					</p>
					<p>
						<label>是否开启</label> <input type="radio" name="cloesd"
							value="false" <c:if test="${!model.cloesd}">checked</c:if>>关闭
						<input type="radio" name="cloesd" value="true"
							<c:if test="${model.cloesd}">checked</c:if> />开启
					</p>
				</fieldset>
				<fieldset>
					<legend>试题设置：</legend>
					<div class="tabs">
						<div class="tabsContent" style="height: 200px;">
							<table class="list itemDetail" addButton="添加1条模板信息" width="100%">
								<thead>
									<tr>
										<th type="lookup" name="templateForms[#index#].name"
											lookupGroup="templateForms[#index#]"
											lookupUrl='latar/treeLookup.jsp' postField="keywords"
											callback="items_callback" size="12" fieldClass="required">模板名称</th>
										<th type="enum" name="templateForms[#index#].singleNum"
											size="12">单选题数量</th>
										<th type="enum" name="templateForms[#index#].multipleNum"
											size="12">多选题数量</th>
										<th type="enum" name="templateForms[#index#].judgeNum"
											size="12">判断题数量</th>
										<th type="del" width="60">操作</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
				</fieldset>
				<fieldset>
					<legend>分数设置：</legend>
					<table>
						<tr>
							<td><label>单选题分值：</label></td>
							<td><input type="number" id="singleScore" name="singleScore"
								class="required digits" min="0" step="1"></td>
						</tr>
						<tr>
							<td><label>多选题分值：</label></td>
							<td><input type="number" id="multipleScore"
								name="multipleScore" class="required digits" min="0" step="1"></td>
						</tr>
						<tr>
							<td><label>判断题分值：</label></td>
							<td><input type="number" id="judgeScore" name="judgeScore"
								class="required digits" min="0" step="1"></td>
						</tr>
					</table>
				</fieldset>
				<fieldset>
					<legend>统计：</legend>
					<div class="button">
						<div class="buttonContent">
							<button type="button" onclick="statisticalResult()">统计结果</button>
						</div>
					</div>
					<p>
					<table>
						<tr>
							<td><label>单选题总数量：</label></td>
							<td><span id="singleAllNum">0</span></td>
							<td><label>单选题总分：</label></td>
							<td><span id="singleAllScore">0</span></td>
						</tr>
						<tr>
							<td><label>多选题总数量：</label></td>
							<td><span id="multipleAllNum">0</span></td>
							<td><label>多选题总分：</label></td>
							<td><span id="multipleAllScore">0</span></td>
						</tr>
						<tr>
							<td><label>判断题总数量：</label></td>
							<td><span id="judgeAllNum">0</span></td>
							<td><label>判断题总分：</label></td>
							<td><span id="judgeAllScore">0</span></td>
						</tr>
						<tr>
							<td><label><b>试卷总分：</b></label></td>
							<td><span style="color: red;" id="total">0</span></td>
						</tr>
					</table>
					</p>
				</fieldset>
			</div>
			<div class="formBar">
				<ul>
					<li><s:if test="#haveRight">
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">添加</button>
								</div>
							</div>
						</s:if> <s:else>
							<div class="buttonDisabled">
								<div class="buttonContent">
									<button type="button">添加</button>
								</div>
							</div>
						</s:else></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>