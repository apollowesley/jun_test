$(document).ready(function(){
	$.ajax({
		url:"/common/menu/load",
		data:{},
		dataType:"json",
		type:"json",
		success:function(res){
			console.log(res)
			if(res.state==1){
				var data=res.data;
				$("#parent").html("<option value='0'>顶级菜单</option>");
				for(var i=0;i<data.length;i++){
					$("#parent").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
				}
			}
		},error:function(err){

		}
	})
	$("#cancel").bind("click",function(){
		window.history.back(-1);
	})
	//增加方法
	$("#menuAdd").bind("click",function(){
		$("#menudata").submit();
		var data=$("#menudata").serialize();
		if(verify()){
			$.ajax({
				url:"/common/menu/add",
				data:data,
				type:"post",
				dataType:"json",
				success:function(res){
					Alert.hint(res.message);
					if(res.state==1){
						setTimeout(function(){
							window.location.href="/common/menu";
						},2000)
					}
				},error:function(err){
					Alert.hint(err);
				}
			})
		}
	})
	//更新方法
	$("#menuUpdate").bind("click",function(){
		var data=$("#menudata").serialize();
		if(verify()){
			$.ajax({
				url:"/common/menu/update",
				data:data,
				type:"post",
				dataType:"json",
				success:function(res){
					Alert.hint(res.message);
					if(res.state==1){
						setTimeout(function(){
							window.location.href="/common/menu";
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