// var api='http://127.0.0.1:8080/api/'
var api='http://momo.free.idcfengye.com/api/'
var baseUrl='http://120.76.246.135/'    //用于文件上传
var loginUrl='http://127.0.0.1:8848/erp/login.html';
var loginUrl='http://127.0.0.1:8848/login.html';
//下次再发ajax请求把token带到后台
var token = $.cookie('TOKEN');

//设置全局ajax前置拦截
$.ajaxSetup( { 
	headers: {
		'TOKEN': token   //每次ajax请求时把token带过去
	}
});

//如果访问登陆页面这外的页面并且还没有登陆成功之后写入cookie的token就转到登陆页面
if(token==undefined){
	if(window.location!=loginUrl){
		window.top.location=loginUrl;
	}
}else{
	//对token进行校验
	if(window.location!=loginUrl){
		$.ajax({
				url:api+"login/checkLogin",
				async:true,
				type:'post',
				dataType:'json',
				success:function(res){
					if(res.code==-1){
						alert(res.msg)
						window.top.location=loginUrl;
					}
				},
				error:function(res){
					window.top.location=loginUrl;
				}
		});
	}
}

//从local中获取
var pers=localStorage.getItem("permissions");
// console.log(pers);
var usertype=localStorage.getItem("usertype");
if(usertype==1){
	if(pers!=null){
		var permissions=pers.split(",");
		//部门权限开始
		if(permissions.indexOf("dept:add")<0){
			$(".btn_add").hide();
		}
		if(permissions.indexOf("dept:update")<0){
			$(".btn_update").hide();
		}
		if(permissions.indexOf("dept:delete")<0){
			$(".btn_delete").hide();
		}
		//部门权限结束
		
		//菜单权限开始
		//菜单权限结束
		//角色权限开始
		//角色权限结束
		//用户权限开始
		//用户权限结束
		//日志权限开始
		//日志权限结束
		//其他权限
		
		
	}else{
		//各个权限按钮
		$(".btn_add").hide();
		$(".btn_update").hide();
		$(".btn_delete").hide();
		$(".btn_dispatch").hide();
		$(".btn_reset").hide();
	}
}

//给页面显示登陆用户名
var username=localStorage.getItem("username");
$(".login_name").html(username);





