<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table  class="easyui-treegrid" fit="true"  id="roleResourcesGrid"
        url="resources/findAll"  
        rownumbers="true" singleSelect="false"
        idField="id" treeField="name"  toolbar="#tbAssignRes" >
    <thead>
        <tr>
            <th field="name" width="320" data-options="formatter:formatterName">名称</th>
        </tr>
    </thead>
</table>

<div id="tbAssignRes" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveRoleResources(${param.roleId});">保存</a>
    </div>
</div>

<script type="text/javascript">
    var roleId = "${param.roleId}";
   
    $("#roleResourcesGrid").treegrid({
    	onLoadSuccess:function (row, data) {
    		findByRoleId(roleId);
        },
        onClickRow:function (row) {
        	 
            var isSelect = false;//用来标记当前节点的状态，true:勾选，false:未勾选  
              
            var selectNodes = $("#roleResourcesGrid").treegrid('getSelections');//获取当前选中项  
            for(var i=0;i<selectNodes.length;i++) {  
                if(selectNodes[i]["id"] == row.id) { 
                	isSelect = true;  
                }
            } 
            $("#"+row.id).attr("checked",isSelect);
            
            selectChildren(row.id,"id",true, isSelect); 
            selectParent(row.parentId,"id", isSelect);  
             
            $("#"+row.id).focus();
        }
    });
    
    /** 
     * 级联选择父节点 
     * @param {Object} target 
     * @param {Object} id 节点ID 
     * @param {Object} status 节点状态，true:勾选，false:未勾选 
     * @return {TypeName}  
     */  
    function selectParent(id,idField,status){  
        var row = $("#roleResourcesGrid").treegrid('find',id);  
        if (row) {
        	if ("-1" == row.parentId) {
        		if(status) {  
                	$("#roleResourcesGrid").treegrid('select',row.id); 
                    $("#"+row.id).attr("checked",status);
                } else {  
                	var children = $("#roleResourcesGrid").treegrid('getChildren', row.id);
                	var len = children.length;
                	var found = false;
                	for(var i=0;i<len;i++){
                		var checked = $("#"+children[i].id).attr("checked");
                		if(checked){
                			found=true;
                			break;
                		}
                	}
                	if(len == 0 || !found){
                		$("#roleResourcesGrid").treegrid('unselect',row.id);
                        $("#"+row.id).attr("checked",status);
                        
                	}
                }
        	} else {
        		var parentId = row[idField];  
                if(status) {  
                	$("#roleResourcesGrid").treegrid('select',parentId); 
                    $("#"+parentId).attr("checked",status);
                } else {
                	
                	var children = $("#roleResourcesGrid").treegrid('getChildren', parentId);
                	var len = children.length;
                	var found = false;
                	for(var i=0;i<len;i++){
                		var checked = $("#"+children[i].id).attr("checked");
                		if(checked){
                			found=true;
                			break;
                		}
                	}
                	if(!found){
                		$("#roleResourcesGrid").treegrid('unselect',parentId);
                        $("#"+parentId).attr("checked",status);
                	}
                }
                selectParent(row.parentId,idField,status); 
        	}
        }  
    }  
    /** 
     * 级联选择子节点 
     * @param {Object} target 
     * @param {Object} id 节点ID 
     * @param {Object} deepCascade 是否深度级联 
     * @param {Object} status 节点状态，true:勾选，false:未勾选 
     * @return {TypeName}  
     */  
    function selectChildren(id,idField,deepCascade,status){  
        /* //深度级联时先展开节点  
        if(!status&&deepCascade)  
        	$("#roleResourcesGrid").treegrid('expand',id);   */
        //根据ID获取下层孩子节点  
        var children = $("#roleResourcesGrid").treegrid('getChildren',id);  
        for(var i=0;i<children.length;i++){  
            var childId = children[i][idField];  
            if(status) {  
            	$("#roleResourcesGrid").treegrid('select',childId);  
                $("#"+childId).attr("checked",status);
            } else {  
            	$("#roleResourcesGrid").treegrid('unselect',childId);  
            	$("#"+childId).attr("checked",status);
            }
            selectChildren(childId,idField,deepCascade,status);//递归选择子节点  
        }  
    }  
     
    function formatterName(value,rowData,rowIndex) {
    	return "<input type='checkbox' value='"+rowData.id+"' id='"+rowData.id+"'/>"+ value;
    }
    
    function findByRoleId(roleId) {
    	var sendData = {roleId:roleId};
    	ajaxPost("resources/findByRoleId",sendData, function (resultData) {
    		$.each(resultData, function(i, n){
    		    $("#roleResourcesGrid").treegrid("select", n.id);
    		    $("#"+n.id).attr("checked", true);
    		});
    	});
    }
    
    function saveRoleResources(roleId) {
    	var rows = $("#roleResourcesGrid").treegrid('getSelections');;
    	if (rows && rows.length > 0) {
    		var sendData = {roleIds:[],resourcesIds:[]};
    		for (var i = 0; i < rows.length; i++) {
    			sendData.roleIds[i] = roleId;
    			sendData.resourcesIds[i] = rows[i].id;
    		}
    		ajaxPost("resources/saveRoleResources",$.param(sendData, true), function (resultData) {
    			if (isSuccess(resultData)) {
    				closeDialog();
    			}
    		});
    	}
    }
</script>
