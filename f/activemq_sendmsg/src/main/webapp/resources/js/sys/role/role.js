$(function(){
	var haveRoleEditPermission = $("#haveRoleEditPermission").val();
	var haveRoleDeletePermission = $("#haveRoleDeletePermission").val();
	
    $('#roleListTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
    	url:$("#getRoleListUrl").val(),
    	height:500,
		method: "post",//请求方式
        pagination: true, //启动分页  
        pageSize: 10,  //每页显示的记录数 
        pageList: [5, 10, 15,20,30,50],
        sidePagination: "server", //表示服务端请求
        buttonsAlign: "right",//按钮对齐方式
        toolbarAlign: "left",//工具栏对齐方式
        toolbar: '#toolbar',    //工具按钮用哪个容器
        queryParams : function(params) {
			params['status'] = $("#roleSearchForm #status").val();
			params['name'] = $("#roleSearchForm #roleName").val();
			return params;
		},
        columns: [{
            field: 'name',
            title: '角色名称',
            align: 'center'
        },{
            field: 'description',
            title: '角色描述',
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
            	if(haveRoleEditPermission){
            		str += "<button onclick=\"javascript:editRole(" + value + ")\" type=\"button\" class=\"btn btn-success\" id=\"editButton\">编辑</button>";
            	}
            	if(haveRoleDeletePermission){
            		str += "&nbsp;&nbsp;<button onclick=\"javascript:deleteRole(" + value + ")\" type=\"button\" class=\"btn btn-warning\" id=\"deleteButton\">删除</button>";
            	}
				return str;
            }
        }]
    });
   
    //查询
    $("#roleSearchButton").click(function() {
    	$('#roleListTable').bootstrapTable("refresh");
    });
    
    /**
     * 进入添加角色页面
     */
    $("#roleAddButton").click(function(){
    	openWindows("添加角色",$("#toAddRoleUrl").val(),null,
    		function(index,layero){
    		roleSubmitForm(index);
    	});
    })
});

/**
 * 设置表格行背景色 pink是页面中head内的css样式
 * 
 * @param row
 * @param index
 * @returns
 */
function rowStyle(row, index) {
	//var classes = [ "danger" ];//可以自定义css样式
	
	//bootstrap table支持5中表格的行背景色，分别是'active', 'success', 'info', 'warning', 'danger'这五种
	if (row["statusName"] == "禁用") {
		return {
			classes : "danger" //自定义样式可以这样使用 ： classes[0]，获取对应的颜色样式
		};
	}
	return {};
}

function editRole(value){
    openWindows("修改角色",$("#toEditRoleUrl").val(),{roleId:value},
    function(index,layero){
        roleSubmitForm(index)
    });
}

/**
 * 删除角色
 */
function deleteRole(value){
	$.confirm({
	    icon: 'fa fa-warning',
	    content: '确认删除该角色吗？',
	    confirm: function(){
	    	$.ajax({
				type : "post",
				url : $("#deleteRoleUrl").val(),
				dataType : "json",
				data : {
					"roleId" : value
				},
				success : function(result) {
					if (result.code == 200) {
						layer.msg('删除成功', {
							shift : 2,
							icon : 6
						});
						$('#roleListTable').bootstrapTable("refresh");
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