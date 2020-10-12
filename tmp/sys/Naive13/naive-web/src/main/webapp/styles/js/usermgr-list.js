$(function() {
	$('#usermgr_list').flexigrid({
		url:webroot + "/UserMgrAction.action?method=listUser",
		resizable:false,
		height:320,
		dataType : 'xml',
		colModel : [
			{
				display : '',
				name : 'userId',
				width : 30,
				sortable : true,
				align : 'left'
			},
			{
				display : '用户编号',
				name : 'num',
				width : 100,
				sortable : true,
				align : 'left'
			},
			{
				display : '姓名',
				name : 'name',
				width : 100,
				sortable : true,
				align : 'left'
			},
			{
				display : '性别',
				name : 'sex',
				width : 100,
				sortable : true,
				align : 'left'
			},
			{
				display : '电话',
				name : 'phone',
				width : 150,
				align : 'left'
			}, 
			{
				display : '邮箱地址',
				name : 'email',
				width : 300,
				align : 'left'
			} 
		],
		buttons:[
			{
				name : '添加',
				bclass : 'datagrid-create',
				onpress : createUser
			},
			{
				name : '修改',
				bclass : 'datagrid-edit',
				onpress : editUser
			},
			{
				name : '删除',
				bclass : 'datagrid-delete',
				onpress : delUser,
			}
		],
		searchitems : [
  			{display: '姓名', name : 'name', isdefault: true},
  			{display: '邮箱', name : 'email'}
        ],
   		usepager:true,
   		useRp:true,
   		rp:10,
   		sortname:'',
   		sortorder:'asc',
		onSuccess:false,
		onError: function() {
			console.log("load data error!");
		}
	});
	
	function createUser() {
		alert("创建用户");
	}
	
	function editUser() {
		alert("修改用户");
	}
	
	function delUser() {
		alert("删除用户");
	}
});