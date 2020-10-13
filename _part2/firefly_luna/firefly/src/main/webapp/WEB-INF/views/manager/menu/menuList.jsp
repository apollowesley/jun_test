<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>菜单管理</title>

<style type="text/css">
.panel .panelContent { background:white !important; } /**覆盖dwz默认的样式*/
.pageContent .panel { clear: both; margin: 0px !important;}

</style>
<script type="text/javascript">
	//操作后-刷新该页面
	function menu_navTabAjax(json) {
		navTabAjaxDone(json);
		navTab.reload();
	}
	//弹出修改菜单管理页面
	function edit() {
		var editingId = null;
        var row = $('#menuTable').treegrid('getSelected');
        if (row){
            editingId = row.id;
        }
        if (null == editingId) {
        	alertMsg.warn('请选择记录');
        	return;
        }
		var url = "sysMenu/edit/" + editingId;
		var dlgId = "systemMenuDetail";
		var title = "修改";
		var options = {
			width : 580,
			height : 300,
			mask : true,
			closeOnEscape : true
		};
		$.pdialog.open(url, dlgId, title, options);
	}	
	function del() {
		var editingId = null;
        var row = $('#menuTable').treegrid('getSelected');
        if (row){
            editingId = row.id;
        }
        if (null == editingId) {
        	alertMsg.warn('请选择记录');
        	return;
        }
		var url = "sysMenu/delete/"+editingId;
		
		alertMsg.confirm("确认删除该菜单及包含的子菜单？", {
            okCall: function(){
            	$.post(url, null, menu_navTabAjax);
            }
        });
	}
	$(function() {
		$('#menuTable').treegrid({    
		    url:'sysMenu/menuListById',    
		    idField:'id',    
		    treeField:'name',
		    loadMsg:"数据加载中..",
		    initialState:"expandAll",
		    columns:[[    
		        {field:'name',title:'菜单名称',width:180},    
		        {field:'description',title:'菜单描述',width:160},    
		        {field:'alias',title:'菜单别名',width:220},    
		        {field:'sort',title:'排序',width:80},   
		        {field:'createUser',title:'操作人',width:100},  
		        {field:'link',title:'菜单链接',width:280}    
		    ]],
		    onLoadSuccess:function(){
		        //$('#menuTable').treegrid('expandAll'); 是否默认展开
		    }
		});
	});
	function item_moved(type){
		var editingId ;
        var row = $('#menuTable').treegrid('getSelected');
        if (row){
            editingId = row.id
        }
		var url = "sysMenu/delete/"+editingId;

		if(type==1) {//上移
			
		}else if(type == 2){ //下移
			
		}
	}
</script>
</head>
<body>

	<div class="pageContent">
		<div class="panelBar">
			<!-- <input type="hidden" name="isAdvencedSearch" value="0" /> -->
			<ul class="toolBar">
				<shiro:hasPermission name="menu_add">
					<li><a class="add" href="sysMenu/add/0" target="dialog"
						mask="true" rel="dialog1"><span>添加</span></a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="menu_update">
					<li><a title="修改菜单" class="edit" onclick="edit()" href="javascript:void(0)"
						mask="true" rel="dialog1" width="580" height="300"><span>修改</span></a></li>
						
				</shiro:hasPermission> 
				<shiro:hasPermission name="menu_delete">
					<li><a title="删除菜单" onclick="del()" class="delete" href="javascript:void(0)"  
						mask="true" rel="dialog1" width="580" height="300"><span>删除</span></a></li>
				</shiro:hasPermission>

			</ul>
		</div>
	</div>
			<table id="menuTable" width="100%" bgcolor="#333366" style="height:515px"  >
	    </table>
	
</body>
</html>