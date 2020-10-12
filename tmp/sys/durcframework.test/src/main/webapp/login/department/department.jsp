<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系别管理</title>
<style type="text/css">
	.stu_cont{padding: 1px;}
	.fitem{margin: 4px;}
</style>
</head>
<body>

<div class="stu_cont">

	<div id="toolbarDept">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newDepartment()">添加系别</a>
	</div>
	<table id="dgDept" title="系别信息" class="easyui-datagrid" style="width:500px;"
			url="${ctx}listDepartment.do"
			toolbar="#toolbarDept" pagination="true"
			rownumbers="false" fitColumns="false" singleSelect="true">
		<thead>
			<tr>
				<th data-options="field:'departmentName',width:200">系别名称</th>
				<th data-options="field:'_operate',width:80,align:'center',formatter:formatOperDept">操作</th>
			</tr>
		</thead>
	</table>
	
	<div id="dlgDept" class="easyui-dialog" style="width:500px;height:180px;padding:10px 20px"
			closed="true" modal="true" buttons="#dlg-buttons">
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>系别名称 :</label>
				<input name="departmentName" class="easyui-validatebox" required="true">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveDepartment()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgDept').dialog('close')">取消</a>
	</div>
	
</div>
<jsp:include page="../../easyui_lib.jsp"></jsp:include>
<script type="text/javascript">
var url;
function newDepartment(){
	$('#dlgDept').dialog('open').dialog('setTitle','添加系别');
	$('#fm').form('clear');
	url = '${ctx}addDepartment.do';
}
function editDepartment(index){
	$('#dgDept').datagrid('selectRow',index);
	var row = $('#dgDept').datagrid('getSelected');
	if (row){
		$('#dlgDept').dialog('open').dialog('setTitle','修改系别');
		$('#fm').form('load',row);
		url = '${ctx}updateDepartment.do?id='+row.id;
	}
}
function saveDepartment(){
	$('#fm').form('submit',{
		url: url,
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.success){
				$('#dlgDept').dialog('close');		// close the dialog
				$('#dgDept').datagrid('reload');	// reload the user data
			} else {
				$.messager.show({
					title: 'Error',
					msg: result.msg
				});
			}
		}
	});
}
function removeDepartment(index){
	$('#dgDept').datagrid('selectRow',index);
	var row = $('#dgDept').datagrid('getSelected');
	if (row){
		$.messager.confirm('Confirm','确定要删除('+row.departmentName+')的信息吗?',function(r){
			if (r){
				$.post('${ctx}delDepartment.do',{id:row.id},function(result){
					if (result.success){
						$('#dgDept').datagrid('reload');	// reload the user data
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
function formatOperDept(val,row,index){
	return '<a href="#" onclick="editDepartment('+index+')">修改</a>'+
		' | <a href="#" onclick="removeDepartment('+index+')">删除</a>';
}
</script>
</body>
</html>