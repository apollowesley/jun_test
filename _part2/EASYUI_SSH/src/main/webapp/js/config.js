/**
 * 提交表单数据
 * @returns
 */
function submitForm(){
	debugger;
	$('#config_form').form('submit', {
	    url:getContextPath()+'/ProcedureConfig/addProcedureConfig',
		dataType : 'json',
	    onSubmit: function(){
			// do some check
			// return false to prevent submit;
	    },
	    success:function(data){
	    	debugger;
			$('#dlg').dialog('close');
	    	$.messager.alert('tip',data);
	    	$('#dg').datagrid('reload');
	    }
	});
	
}

/**
 * 删除单个数据
 * @returns
 */
function deleteConfig(){
	debugger;
	var selectedRow=$('#dg').datagrid('getSelected');
	if(selectedRow==null){
    	$.messager.alert('tip',"请选择需要删除的数据!");
    	return;
	}
	$.ajax({
        type: "GET",
        url: getContextPath()+'/ProcedureConfig/deleteProcedureConfig',
        data: {id:selectedRow.id},
        dataType: "json",
        success: function(data){
        	debugger;
	    	$.messager.alert('tip',data[0].message);
	    	$('#dg').datagrid('reload');
        },
        error:function(data){
        	debugger;
	    	$.messager.alert('tip',data[0].message);
	    	$('#dg').datagrid('reload');
        }
    });	
}