$(function() {
	var haveUserEditPermission = $("#haveUserEditPermission").val();
	var haveUserDeletePermission = $("#haveUserDeletePermission").val();
	$("#userListTable").bootstrapTable({
		contentType: "application/x-www-form-urlencoded",
		url : $("#getUserListUrl").val(),
		height:500,
		method: "post",//请求方式
        pagination: true, //启动分页  
        pageSize: 10,  //每页显示的记录数 
        pageList: [5, 10, 15,20,30,50],
        sidePagination: "server", //表示服务端请求
        cache: false, // 不缓存
        showColumns: true,//列选择按钮
        buttonsAlign: "left",//按钮对齐方式
        toolbarAlign: "right",//工具栏对齐方式
        toolbar: '#toolbar',    //工具按钮用哪个容器
       	//detailView: true,
    	idField: "userId",  //标识哪个字段为id主键  
    	//showRefresh: true,  //显示刷新按钮
    	//singleSelect: true,//复选框只能选择一条记录  
    	clickToSelect: false,//点击行即可选中单选/复选框  
    	showExport: true,//是否显示导出
    	exportDataType: "basic",//basic', 'all', 'selected'.
		queryParams : function(params) {
			params['roleId'] = $("#userSearchForm #roleId").val();
			params['status'] = $("#userSearchForm #status").val();
			params['realName'] = $("#userSearchForm #realName").val();
			params['haveUserListPermission'] = $("#userSearchForm #haveUserListPermission").val();
			return params;
		},
		columns : [{
			field : 'state',
            checkbox: true
        },
		{
			field : '',
			title : '序号',
			align : 'center',
			formatter : function(value, row, index) {
				return index + 1;
			}
		},{
			field : 'realName',
			title : '真实姓名',
			align : 'center',
			titleTooltip: "名称可以直接点击编辑哦",
			editable:true
		},{
			field : 'userName',
			title : '账户名',
			align : 'center'
		},
		{
			field : 'roleName',
			title : '拥有角色',
			align : 'center'
		},
		{
			field : 'statusName',
			title : '允许登录',
			align : 'center'
		},
		{
			field : 'createTime',
			title : '创建日期',
			sortable: true,
			align : 'center'
		},
		{
			field : 'userId',
			title : '操作',
			align : 'center',
			formatter : function(value, row, index) {
				var str = "";
				if(haveUserEditPermission){
					str += "<button onclick=\"javascript:editUser(" + value + ")\" type=\"button\" class=\"btn btn-success\" id=\"editButton\">编辑</button>";
				}
				if(haveUserDeletePermission){
					//是管理员，则灰化删除按钮
					if(row["userName"] == "admin"){
						str += "&nbsp;&nbsp;<button onclick=\"javascript:deleteUser(" + value + ")\" type=\"button\" disabled=\"disabled\" class=\"btn btn-warning\">删除</button>";
					}else{
						str += "&nbsp;&nbsp;<button onclick=\"javascript:deleteUser(" + value + ")\" type=\"button\" class=\"btn btn-warning\" id=\"deleteButton\">删除</button>";
					}
					
				}
				return str;
			}
		}],
		
		//表格加载数据自定义提示信息
		formatLoadingMessage: function () {  
		    return "数据正在努力加载中...";  
		},
		
		//没有匹配的结果自定义提示信息
		formatNoMatches: function () {  //没有匹配的结果  
		    return "无符合条件的记录";  
		},
		
		//加载出现错误
		onLoadError: function (data) {  
		    //$('#userListTable').bootstrapTable('removeAll');  
		},
		
		//单击某一行
		//onClickRow: function (row) {  
		    //alert("选中了人员姓名是【" + row["realName"] + "】的一条记录");
		//},
		  
		//列编辑。四个参数field, row, oldValue, $el分别对应着当前列的名称、当前行数据对象、更新前的值、编辑的当前单元格的jQuery对象
		onEditableSave: function (field, row, oldValue, $el) {
			//console.log(field);
			//console.log(JSON.stringify(row));
			//console.log(oldValue);
			
			//这里声明一个对象，用于存储参数集合，实现编辑列的动态属性名与值的对应。field比较重要特殊，是一个变量：具体修改的参数名称，比如realName。
			var data = {};
			data[field]=row[field];
			data["userId"] = row["userId"];
			
            $.ajax({
                type: "post",
                dataType : "json",
                url: $("#editColumnUrl").val(),
                data: data,
                beforeSend : function(){
                	ZENG.msgbox.show("正在请求系统...", 6);
                },
                success: function (data) {
                    if (data.code == 200) {
                    	ZENG.msgbox.show("保存成功", 4, 2000);
                    }else{
                    	ZENG.msgbox.show(data.msg, 4, 2000);
                    }
                },
                
                //textStatus的值：null, timeout, error, abort, parsererror  
                //errorThrown的值：收到http出错文本，如 Not Found 或 Internal Server Error.
                error: function (XMLHttpRequest, textStatus, errorThrown) {  
                	ZENG.msgbox.show("系统发生错误", 5, 2000);
                }
            });
        }
	});

	// 查询
	$("#userSearchButton").click(function() {
		$('#userListTable').bootstrapTable("refresh");
	});

	// 打开添加人员页面
	$("#userAddButton").click(function() {
		openWindows("添加用户", $("#toAddUserUrl").val(), null, 
			function(index, layero) {
				userSubmitForm(index);
			}
		);
	});
});


/*var $table = $('#userListTable');
$(function () {
    $('#toolbar').find('select').change(function () {
        $table.bootstrapTable('destroy').bootstrapTable({
            exportDataType: $(this).val()
        });
    });
});*/


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

$(document).ready(function() {
	var setting = {
		check : {
			enable : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode) {// 节点被点击后，设置选择的节点id
				$("#depId").val(treeNode.id);

				$("#userSearchButton").click();
			}
		}
	};
	var depList = $("input[name='depList']").val();
	// $.fn.zTree.init($("#depTree"), setting, eval("(" + depList + ")"));
});

function editUser(value) {
	openWindows("编辑人员信息", $("#toEditUserUrl").val(), {userId : value}, 
		function(index, layero) {
			userSubmitForm(index);
		}
	);
}

function deleteUser(value) {
	$.confirm({
	    icon: 'fa fa-warning',
	    content: '是否永久删除该人员？',
	    confirm: function(){
	    	$.ajax({
				type : "post",
				url : $("#userListDiv #deleteUserUrl").val(),
				dataType : "json",
				data : {
					"userId" : value
				},
				success : function(result) {
					if (result.code == 200) {
						layer.msg('删除成功', {
							shift : 2,
							icon : 6
						});
						$('#userListTable').bootstrapTable("refresh");
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