<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <div class="easyui-layout" style="width: 100%; height: 100%;">
        <div data-options="region:'east',split:true" title="已经存在的角色" style="width: 220px;height: 100%;">
            <table class="easyui-datagrid"  fit="true" id="existRoleGrid" userId="${param.userId }"
            data-options="rownumbers:true,singleSelect:true,pagination:false,url:'role/findByUserId?userId=${param.userId }',method:'post'">
		        <thead>
				<tr>
					<th data-options="field:'id',hidden:true">
					</th>
					<th data-options="field:'name',width:100">
						角色名
					</th>
					<th field="action" formatter="assignRoleAction">操作</th>
				</tr>
			</thead>
		    </table>
        </div>
         
        <div data-options="region:'center',title:'角色数据'" style="width: 420px;height: 100%;">
            <table class="easyui-datagrid" toolbar="#tb"  id="roleGrid"  fit="true"
            data-options="rownumbers:true,singleSelect:true,pagination:true,url:'role/find',method:'post'">
		        <thead>
					<tr>
						<th field="id" hidden="true">id</th>
						<th field="name" width="80">角色名</th>
						<th field="createTime" width="200" formatter=dateFormatByEasyui>创建时间</th>
					</tr>
				</thead>
		    </table>
        </div>
        
        <div id="tb" style="padding: 5px; height: auto">
			<div id="roleSearchDiv">
				角色名: <input style="width: 80px" name="nameLike"> 
				<a href="javascript:void(0);" onclick="searchData('role');" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-add" onclick="addUserRole(${param.userId });">添加角色</a> 
			</div>
		</div>
    </div>
<script type="text/javascript">

function assignRoleAction(value, row, index) {
	return "<a href='javascript:void(0);' onclick=\"deleteUserRole("+row.id+");\">删除</a>";
};

function addUserRole(userId) {
	var row = getSelected("role");
	if (row) {
		var roleId = row.id;
		if (!isExistRowId(roleId,"existRole")) {
			var sendData = {userId: userId, roleId: roleId};
			ajaxPost("role/addUserRole",sendData, function (resultData) {
				if (isSuccess(resultData)) {
					$("#existRoleGrid").datagrid('reload');
				}
			});
		};
	};
};

function deleteUserRole(roleId) {
	var userId = $("#existRoleGrid").attr("userId");
	if (!isEmpty(roleId)) {
		var sendData = {userId:userId, roleId:roleId};
		ajaxPost("role/deleteUserRole",sendData, function (resultData) {
			if (isSuccess(resultData)) {
				$("#existRoleGrid").datagrid('reload');
			}
		});
	};
};

</script>