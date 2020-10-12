<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//labelD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.labeld">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改期末考试试卷</title>
</head>

<body>

	<div class="pageContent">
		<s:set var="haveRight"
			value="@com.evil.util.ValidateUtil@hasRight('/FormalTestAction_doUpdateFormalTest')" />
		<form method="post"
			action="${haveRight?'FormalTestAction_doUpdateFormalTest':'#' }"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,dialogAjaxDone)">
			<div class="pageFormContent nowrap" layoutH="56">
				<!-- 		NAME,parentId,url,ORDER,content -->
				<s:if test="model.id!=null">
					<p>
						<label>试卷id：</label> <input type="text" name="id"
							value="${model.id }" readonly="readonly" style="width: 200px" />
					</p>
				</s:if>
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
					<label>开放时间：</label> <input type="text" name="startTime"
						id="startTime" class="date" datefmt="yyyy-MM-dd HH:mm:ss"
						readonly="true">
				</p>
				<p>
					<label>结束时间：</label> <input type="text" name="endTime" id="endTime"
						class="date" datefmt="yyyy-MM-dd HH:mm:ss" readonly="true">
				</p>
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
	<script type="text/javascript">
		$(function() {
			var startTime = "${model.startTime==null?'2015-06-03 00:00:00':model.startTime}";
			startTime = new Date(startTime);
			var endTime = "${model.endTime==null?'2015-07-03 00:00:00':model.endTime}";
			endTime = new Date(endTime);
			$("#startTime").val(CurentTime(startTime));
			$("#endTime").val(CurentTime(endTime))
		});
	</script>
</body>
</html>