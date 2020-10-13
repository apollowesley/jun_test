<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp" %> 
<%
request.setAttribute("namespace", "statusCode");
%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/views/common/common.jsp" %>
</head>
<body>
    <kisso:hasPermission name="statusCode_delete">
         <input type="hidden" id="deleteAuth" value="Y"/>
    </kisso:hasPermission>
    
    <!-- 数据展示列表查询区 -->
	<div id="toolbar" style="padding: 1px; height: auto">
		<div style="margin-bottom: 1px;" id="${namespace }SearchDiv"
			class="tab">
			<form action="#" name="searchForm" id="searchForm"
				style="display: inline;">
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					style="border: 1px solid #52B64C;">
					<tr>
						<td width="13%" class="bule">组编号：</td>
						<td>
						<input style="width: 120px" name="groupNum"  class="easyui-validatebox" data-options="required:false, validType:['length[0,20]']"/>
						</td>
						<td align="center" colspan="2"><a href="javascript:void(0);"
							class="easyui-linkbutton" data-options="iconCls:'icon-search'"
							onclick="searchData();">查询</a> &nbsp;&nbsp;&nbsp;&nbsp; <a
							href="javascript:void(0);" class="easyui-linkbutton"
							data-options="iconCls:'icon-reload'"
							onclick="$('#${namespace }SearchDiv').find('#searchForm').form('reset');">清空</a>
						</td>
					</tr>
				</table>
			</form>
			<kisso:hasPermission name="statusCode_add">
				<a href="javascript:void(0);" onclick="add('statusCode/toAdd','添加',400,420);" class="easyui-linkbutton" iconCls="icon-add" plain="true" title="添加">添加</a>
			</kisso:hasPermission>
			<kisso:hasPermission name="statusCode_update">
				<a href="javascript:void(0);" onclick="update('statusCode/toUpdate','修改',400,420);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" title="修改">修改</a>
			</kisso:hasPermission>
		</div>
	</div>
	
	<table class="easyui-datagrid" fit="true" <kisso:hasPermission name="statusCode_find"> url="<c:url value="/statusCode/find"> </c:url>" </kisso:hasPermission> id="${namespace }Grid"  title="数据列表" 
	     data-options="<kisso:hasPermission name="statusCode_update">onDblClickCell: function(index,field,value){update('statusCode/toUpdate','修改',400,420);}</kisso:hasPermission>" 
	     singleSelect="true" rownumbers="true" pagination="true" toolbar="#toolbar">
		<thead>
			<tr>
			    <th data-options="field:'groupNum',fit:true">组编号</th>
			    <th data-options="field:'groupName',fit:true">组名称</th>
			    <th data-options="field:'nodeNum',fit:true">节点编号</th>
			    <th data-options="field:'nodeKey',fit:true">节点key</th>
			    <th data-options="field:'nodeValue',fit:true">节点value</th>
			    <th data-options="field:'sequence',fit:true">排序号</th>
			    <th data-options="field:'isUse',fit:true">是否使用</th>
			    <th data-options="field:'createTime',fit:true" formatter=dateFormatByEasyui>创建时间</th>
				<th data-options="field:'action',fit:true" formatter="formatterAction">操作</th>
			</tr>
		</thead>
	</table>
	 
	<script type="text/javascript">
	    setNamespace("${namespace }");
		function formatterAction(value, row, index) {
			var deleteAuth = $("#deleteAuth").val();
			if ("Y" == deleteAuth) {
				return "<a href='javascript:void(0);' onclick='delById(\"statusCode/delete\","+row.id+");'>删除</a>";
			}
			return "";
		}
	</script>
</body>
</html>