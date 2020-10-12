<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生管理</title>
<style type="text/css">
	.stu_cont{padding: 1px;}
	.fitem{margin: 4px;}
</style>
</head>
<body>
<div class="stu_cont">

	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加学生</a>
	</div>
	<table id="dg" title="学生信息" class="easyui-datagrid"
			url="${ctx}listStudent.do"
			toolbar="#toolbar" pagination="true"
			rownumbers="false" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th data-options="field:'stuNo',sortable:true,width:20">学号</th>
				<th data-options="field:'name',width:20">姓名</th>
				<th data-options="field:'gender',width:20,formatter:formatGender">性别</th>
				<th data-options="field:'nationality',width:20">名族</th>
				<th data-options="field:'address',width:50,formatter:formatAddr">家庭地址</th>
				<th data-options="field:'mobile',width:20">手机号</th>
				<th data-options="field:'birthday',width:20">出生日期</th>
				<th data-options="field:'registDate',sortable:true,width:20">入学时间</th>
				<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper">操作</th>
			</tr>
		</thead>
	</table>
	
	<div id="dlg" class="easyui-dialog" style="width:500px;height:380px;padding:10px 20px"
			closed="true" modal="true" buttons="#dlg-buttons">
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>学号:</label>
				<input name="stuNo" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>姓名:</label>
				<input name="name" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>性别:</label>
				<select class="easyui-combobox" name="gender" style="width:100px;" required="true">
					<option value="1" selected>男</option>
					<option value="0">女</option>
				</select>
			</div>
			<div class="fitem">
				<label>名族:</label>
				<input name="nationality">
			</div>
			<div class="fitem">
				<label>政治面貌:</label>
				<select class="easyui-combobox" name="politicsStatus" style="width:100px;" required="true">
					<option value="0" selected>群众</option>
					<option value="1">共青团员</option>
					<option value="2">党员</option>
				</select>
			</div>
			<div class="fitem">
				<label>手机号:</label>
				<input name="mobile">
			</div>
			<div class="fitem">
				<label>联系地址:</label>
				<input name="address">
			</div>
			<div class="fitem">
				<label>出生日期:</label>
				<input name="birthday" class="easyui-datebox">
			</div>
			<div class="fitem">
				<label>入学时间:</label>
				<input name="registDate" class="easyui-datebox" required="true">
			</div>
			
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	
</div>
<jsp:include page="../../easyui_lib.jsp"></jsp:include>
<script type="text/javascript">
var url;
function newUser(){
	$('#dlg').dialog('open').dialog('setTitle','添加学生');
	$('#fm').form('clear');
	url = '${ctx}addStudent.do';
}
function editUser(index){
	$('#dg').datagrid('selectRow',index);
	var row = $('#dg').datagrid('getSelected');
	if (row){
		$('#dlg').dialog('open').dialog('setTitle','修改学生信息');
		$('#fm').form('clear').form('load',row);
		url = '${ctx}updateStudent.do?id='+row.id;
	}
}
function saveUser(){
	$('#fm').form('submit',{
		url: url,
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.success){
				$('#dlg').dialog('close');		// close the dialog
				$('#dg').datagrid('reload');	// reload the user data
			} else {
				var errorMsg = result.errorMsg;
				errorMsg = errorMsg || buildValidateError(result)
				$.messager.show({
					title: 'Error',
					msg: errorMsg,
					style:{
						right:'',
						top:document.body.scrollTop+document.documentElement.scrollTop,
						bottom:''
					}
				});
			}
		}
	});
}

function buildValidateError(result){
	var validateErrors = result.validateErrors;
	return validateErrors.join('<br>')
}

function removeUser(index){
	$('#dg').datagrid('selectRow',index);
	var row = $('#dg').datagrid('getSelected');
	if (row){
		$.messager.confirm('Confirm','确定要删除该学生('+row.name+')的信息吗?',function(r){
			if (r){
				$.post('${ctx}delStudent.do',{id:row.id},function(result){
					if (result.success){
						$('#dg').datagrid('reload');	// reload the user data
					} else {
						$.messager.show({	// show error message
							title: 'Error',
							msg: result.errorMsg
						});
					}
				},'json');
			}
		});
	}
}
function formatOper(val,row,index){
	return '<a href="#" onclick="editUser('+index+')">修改</a>'+
		' | <a href="#" onclick="removeUser('+index+')">删除</a>';
}
function formatGender(val,row){
	return val == 1 ? '男' : '女';
}
function formatAddr(val,row){
	val = val || "";
	if(val.length>20) {
		var showAddr = (val||'').substring(0,10) + "...";
		return '<div title="'+val+'">'+showAddr+'</div>';
	}else{
		return val;
	}
}
</script>
</body>
</html>