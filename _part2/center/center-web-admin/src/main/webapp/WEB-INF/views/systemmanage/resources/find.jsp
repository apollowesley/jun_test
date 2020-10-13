<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp" %> 
<%
request.setAttribute("namespace", "resources");
%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/common/common.jsp" %>
</head>
<body>
    <kisso:hasPermission name="resources_delete">
         <input type="hidden" id="deleteAuth" value="Y"/>
    </kisso:hasPermission>
    
	<table  class="easyui-treegrid" fit="true" id="${namespace }Grid" <kisso:hasPermission name="resources_find">url="resources/find"</kisso:hasPermission> title="资源数据" singleSelect="true" 
	    data-options="<kisso:hasPermission name="resources_update">onDblClickCell: function(index,field,value){update('resources/toUpdate','修改资源',400,350);},</kisso:hasPermission>onBeforeLoad: function(row,param){if (!row) { param.id = 0;}}" 
	    rownumbers="true"  idField="id"   treeField="name"  pagination="true" toolbar="#toolbar">
		<thead>
			<tr>
				<th field="id" hidden="true">id</th>
				<th field="name" width="180">权限名</th>
				<th field="linkUrl" width="220">URL</th>
				<th field="sequence" width="50">排序号</th>
				<th field="typeText" width="80">资源类型</th>
				<th field="authCode" width="140">资源code</th>
				<th field="createTime" width="140" formatter=dateFormatByEasyui>创建时间</th>
				<th field="action" width="80" formatter="formatterAction">操作</th>
			</tr>
		</thead>
	</table>

	<div id="toolbar" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
		    <kisso:hasPermission name="resources_add">
		        <a href="javascript:void(0);" onclick="add('resources/toAdd','添加',400,350);" class="easyui-linkbutton" iconCls="icon-add" plain="true" title="添加">添加</a>
		    </kisso:hasPermission>
			
			<kisso:hasPermission name="resources_update">
		       <a href="javascript:void(0);" onclick="update('resources/toUpdate','修改',400,350);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" title="修改">修改</a>
		    </kisso:hasPermission>
			
		</div>
		<div id="${namespace }SearchDiv">
			
		</div>
	</div>
	
	<script type="text/javascript">
	    setNamespace("${namespace }");
		function formatterAction(value, row, index) {
			var deleteAuth = $("#deleteAuth").val();
			if ("Y" == deleteAuth) {
				return "<a href='javascript:void(0);' onclick='delById(\"resources/delete\","+row.id+");'>删除</a>";
			}
			return "";
		}
	</script>
</body>
</html>