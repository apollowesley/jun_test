$(function(){
	var havePermissionEditPermission = $("#havePermissionEditPermission").val();
	var havePermissionDeletePermission = $("#havePermissionDeletePermission").val();
    
    $('#permissionListTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
    	url:$("#getPermissionListUrl").val(),
    	height:600,
		method: "post",//请求方式
        pagination: true, //启动分页  
        pageSize: 10,  //每页显示的记录数 
        pageList: [5, 10, 15,20,30,50],
        sidePagination: "server", //表示服务端请求
        buttonsAlign: "right",//按钮对齐方式
        toolbarAlign: "left",//工具栏对齐方式
        toolbar: '#toolbar',    //工具按钮用哪个容器
        queryParams : function(params) {
			params['status'] = $("#permissionSearchForm #status").val();
			params['name'] = $("#permissionSearchForm #name").val();
			return params;
		},
        columns: [{
            field: 'name',
            title: '权限名称',
            align: 'center'
        },{
            field: 'description',
            title: '权限描述',
            align: 'center'
        },{
            field: 'parentName',
            title: '父级权限',
            align: 'center'
        },{
            field: 'statusName',
            title: '状态',
            align: 'center'
        },{
            field:'id',
            title: '操作',
            align: 'center',
            formatter:function(value){
            	var str = "";
            	if(havePermissionEditPermission){
            		str += "<button onclick=\"javascript:editPermission(" + value + ")\" type=\"button\" class=\"btn btn-success\" id=\"editButton\">编辑</button>";
            	}
            	if(havePermissionDeletePermission){
            		str += "&nbsp;&nbsp;<button onclick=\"javascript:deletePermission(" + value + ")\" type=\"button\" class=\"btn btn-warning\" id=\"deleteButton\">删除</button>";
            	}
				return str;
            }
        }]
    });
   
    //查询
    $("#permissionSearchButton").click(function() {
    	$('#permissionListTable').bootstrapTable("refresh");
    });
    
    /**
     * 进入添加权限页面
     */
    $("#permissionAddButton").click(function(){
    	openWindows("添加权限",$("#toAddPermissionUrl").val(),null,
    		function(index,layero){
    		permissionSubmitForm(index);
    	});
    })
});



function editPermission(value){
    openWindows("修改权限",$("#toEditPermissionViewUrl").val(),{permissionId:value},
    function(index,layero){
    	permissionSubmitForm(index)
    });
}

/**
 * 删除权限
 */
function deletePermission(value){
	$.confirm({
	    icon: 'fa fa-warning',
	    content: '确认删除该权限吗？',
	    confirm: function(){
	    	$.ajax({
				type : "post",
				url : $("#deletePermissionUrl").val(),
				dataType : "json",
				data : {
					"permissionId" : value
				},
				success : function(result) {
					if (result.code == 200) {
						layer.msg('删除成功', {
							shift : 2,
							icon : 6
						});
						$('#permissionListTable').bootstrapTable("refresh");
					} else {
						layer.msg(result.msg, {
							shift : 2,
							icon : 5
						});
					}
				}
			});
	    },
	    cancel: function(){
	    }
	});
}