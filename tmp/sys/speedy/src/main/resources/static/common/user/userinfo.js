$(document).ready(function(){
	$("#cancel").bind("click",function(){
		window.history.back(-1);
	})
	//增加方法
	$("#userAdd").bind("click",function(){
		var data=$("#userdata").serialize();
		console.log(verify());
		if(verify()){
			$.ajax({
				url:"/common/user/add",
				data:data,
				type:"post",
				dataType:"json",
				success:function(res){
					Alert.hint(res.message);
					if(res.state==1){
						setTimeout(function(){
							window.location.href="/common/user";
						},2000)
					}
				},error:function(err){
					Alert.hint(err);
				}
			})
		}
	})
	//更新方法
	$("#userUpdate").bind("click",function(){
		var data=$("#userdata").serialize();
		if(verify()){
			$.ajax({
				url:"/common/user/update",
				data:data,
				type:"post",
				dataType:"json",
				success:function(res){
					Alert.hint(res.message);
					if(res.state==1){
						setTimeout(function(){
							window.location.href="/common/user";
						},2000)
						
					}
				},error:function(err){
					Alert.hint(err);
				}
			})
		}
	})
})
function verify(){
//required
	var flag=true;
	$(".required").each(function(){
		var value=$(this).val();
		var idName=$(this).prop("id");
		if(""==value&&"id"!=idName){
			var text=$(this).parent().text();
			Alert.hint(text+"是必须填写的");
			flag=false;
			return false;
		}else{
			flag=true;
		}
	})
	return flag;
}