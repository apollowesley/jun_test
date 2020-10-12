$(document).ready(function(){
	$("#cancel").bind("click",function(){
		window.history.back(-1);
	})
	//增加方法
	$("#articleAdd").bind("click",function(){
		var data=$("#articledata").serialize()+"&content="+context.get();
		if(verify()){
			$.ajax({
				url:"/common/article/add",
				data:data,
				type:"post",
				dataType:"json",
				success:function(res){
						Alert.hint(res.message);
					if(res.state==1){
						setTimeout(function(){
							window.location.href="/common/article";
						},2000)
					}
				},error:function(err){
					Alert.hint(err);
				}
			})
		}
	})
	//更新方法
	$("#articleUpdate").bind("click",function(){
		var data=$("#articledata").serialize()+"&content="+context.get();
		console.log(verify());
		if(verify()){
			$.ajax({
				url:"/common/article/update",
				data:data,
				type:"post",
				dataType:"json",
				success:function(res){
						Alert.hint(res.message);
					if(res.state==1){
						setTimeout(function(){
							window.location.href="/common/article";
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