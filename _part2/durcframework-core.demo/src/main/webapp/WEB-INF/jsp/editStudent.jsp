<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑学生信息</title>
<script language="javascript" type="text/javascript" src="${ctx}resources/My97DatePicker/WdatePicker.js"></script>

</head>
<body>
<form id="edit-profile" class="form-horizontal" action="${ctx}jsp/saveStudent.do" method="post">
	<input type="hidden" name="id" value="${stu.id}">
	<fieldset>
		<legend>学生信息</legend>
		<div  style="color:red;">
			<c:if test="${!empty error.errorMsg}">
				<c:forEach items="${error.errorMsgs}" var="err">
					${err}
				</c:forEach>
			</c:if>
		</div>
		<div class="control-group">
			<label class="control-label">学号</label>
			<div class="controls">
				<input name="stuNo" type="text" value="${stu.stuNo}" readonly="readonly">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名</label>
			<div class="controls">
				<input name="name" type="text" value="${stu.name}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别</label>
			<div class="controls">
				<select id="gender" name="gender">
					<option value="1">男</option>
					<option value="0">女</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名族</label>
			<div class="controls">
				<input name="nationality" type="text" value="${stu.nationality}">
			</div>
		</div>						
		<div class="control-group">
			<label class="control-label">手机号</label>
			<div class="controls">
				<input name="mobile" type="text" value="${stu.mobile}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系地址</label>
			<div class="controls">
				<input name="address" type="text" value="${stu.address}">
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">出生日期</label>
			<div class="controls">
				<input onClick="WdatePicker()" name="birthday" type="text" value="<fmt:formatDate value="${stu.birthday}" pattern="yyyy-MM-dd"/>">
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">入学时间</label>
			<div class="controls">
				<input onClick="WdatePicker()" name="registDate" type="text" value="<fmt:formatDate value="${stu.registDate}" pattern="yyyy-MM-dd"/>">
			</div>
		</div>						
		<div class="form-actions">
			<input type="submit" class="btn btn-primary" value="保存">
			<input type="button" class="btn" onclick="goBack()" value="取消">
		</div>
	</fieldset>
</form>
<script type="text/javascript">
$(function(){
	$('#gender').val(${stu.gender});
})

function goBack(){
	location.href = 'studentManager.do';
}
</script>
</body>
</html>