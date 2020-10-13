<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<link href="${pageContext.request.contextPath}/easyui/themes/bootstrap/easyui.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/easyui/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/common.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/config.js" type="text/javascript"></script>
</head>
<body>
	
	<table id='dg'class="easyui-datagrid" title="程序配置记录表" style="width:100%;"
			data-options="fit:true,fitColumns:true,nowrap:false,rownumbers:true,singleSelect:true,url:'${pageContext.request.contextPath}/ProcedureConfig/getProcedureConfigs?',method:'get',toolbar:toolbar">
		<thead>
			<tr>
				<th data-options="field:'id',width:20,align:'center',hidden:true">序列</th>
				<th data-options="field:'createDate',width:80,align:'center'">创建时间</th>
				<th data-options="field:'createUser',width:80,align:'center'">创建人</th>
				<th data-options="field:'name',width:80,align:'center'">安装程序名称</th>
				<th data-options="field:'url',width:80,align:'center'">安装程序存放位置</th>
				<th data-options="field:'port',width:80,align:'center'">占用端口</th>
				<th data-options="field:'descript',width:160,align:'center'">描述</th>
			</tr>
		</thead>
	</table>
	
	 <div id="dlg" class="easyui-dialog" title="阿里云服务器信息配置添加" data-options="iconCls:'icon-save',closed:true" style="width:550px;height:350px;padding:10px">
		<form id="config_form" method="post" style="margin-bottom:10px">
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="name" style="width:100%" data-options="label:'程序名称:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="port" style="width:100%" data-options="label:'占用端口:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-combobox" name="createUser" style="width:100%" 
                data-options="label:'创建人:',valueField:'id',textField:'text',
                data:[{'id':'刘聪','text':'刘聪'},{'id':'彭继埔','text':'彭继埔'},{'id':'刘伟琦','text':'刘伟琦'}],required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="url" style="width:100%" data-options="label:'存放路径:'">
            </div>
            

            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="descript" style="width:100%;height:60px" data-options="label:'详细描述:',multiline:true">
            </div>
        </form>
         <div style="text-align:right;padding:5px 0;margin-bottom:30px">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls='icon-ok' onclick="submitForm()" style="width:80px">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls='icon-no' onclick="clearForm()" style="width:80px">关闭</a>
        </div>
    </div>
	<script type="text/javascript">
	
		var toolbar = [{
			text:'Add',
			iconCls:'icon-add',
			handler:function(){
				$('#dlg').dialog('open');
			}
		},{
			text:'Cut',
			iconCls:'icon-cut',
			handler:function(){
				deleteConfig();
				}
		},'-',{
			text:'Save',
			iconCls:'icon-save',
			handler:function(){alert('save')}
		}];

	</script>

</body>
</html>
