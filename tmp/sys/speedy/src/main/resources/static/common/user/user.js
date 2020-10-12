var user={
	//配置基础属性的键值对，是否显示？控制值的属性等等？参数guns
	load:function(){
		$.ajax({
			url:"/common/user/load",
			data:{},
			dataType:"json",
			type:"json",
			success:function(res){
				console.log(res)
			},error:function(err){
				
			}
		})
	},
	loadPage:function(page,pageSize){
		if(!page){
			page=0;
		}
		if(!pageSize){
			pageSize=20;
		}
		$.ajax({
			url:"/common/user/loadPage",
			data:{"page":page,"pageSize":pageSize},
			dataType:"json",
			type:"post",
			success:function(res){
				if(res.state==1){
					$(".table").find("tbody").html("");
					var data=res.data;
					for(var i=0;i<data.length;i++){
						var tagTrs=$("thead").html();
						$("table").find("tbody").append(tagTrs);
						for(var item in data[i]){
							var parame=data[i][item]+"";
							if(parame=="null")parame="";
							if(parame!=null){
								if(parame.indexOf("http")>-1||parame.indexOf("images")>-1){
									$($("."+item)[i+1]).html("<img width='50' src='"+parame+"' />");
								}else{
									if(parame.length>50)parame=parame.substr(0,50)+"...";
									$($("."+item)[i+1]).text(parame);
								}
							}
						}
						var j=i+1;
						var checkboxs='<div class="checkbox">'+
										        '<input type="checkbox" id="box'+data[i].id+'" value="'+data[i].id+'">'+
										            '<label for="box'+data[i].id+'"></label>'+
										'</div>';
						$($(".checkbox")[j]).parent().html(checkboxs);
						if(data[i].menuSon){
							var menuSon=data[i].menuSon;
							for(var m=0;m<menuSon.length;m++){
								
							}
						}
					}
					$(".id").hide();
					var count=parseInt(res.message);
					var pages=Math.ceil(count/pageSize)+1;
					$(".pagination").html("");
					for(var i=1;i<pages;i++){
						if(i==1){
							$(".pagination").append('<li class="pages active"><a href="#">'+i+'</a></li>');
						}else{
							$(".pagination").append('<li class="pages"><a href="#">'+i+'</a></li>');
						}

					}
				}
			},error:function(err){
				console.log(err);
			}
		})
	},
	updatePage:function(){
		$("#update").bind("click",function(){
			var ids=new Array();
			$(".checkbox").not(":first").each(function(){
				if($(this).find("input").prop("checked")){
					ids.push($(this).find("input").val());
				}
				
			})
			ids.join(",");
			if(ids.length>1){
				Alert.hint("只能选择一条数据");
				return;
			}
			if(ids.length==0){
				Alert.hint("请至少选择一条数据!");
				return;
			}
			window.location.href="/common/user/updatePage?id="+ids;
		})
	},
	delete:function(){
		$("#delete").bind("click",function(){
			var ids=new Array();
			$(".checkbox").not(":first").each(function(){
				if($(this).find("input").prop("checked")){
					ids.push($(this).find("input").val());
				}
				
			})
			ids.join(",");
			if(ids.length==0){
				Alert.hint("请至少选择一条数据!");
				return;
			}
			var idStr=ids+"";
			$.ajax({
				url:"/common/user/delete",
				data:{"id":idStr},
				type:"post",
				dataType:"json",
				success:function(res){
					if(res.state==1){
						Alert.hint(res.message);
						setTimeout(function(){
							location.reload();
						},2000)
					}
				},error:function(err){
					console.log(res);
				}
			})
		})
	},
	roleset:function(){
		$("#roleset").bind("click",function(){
			var ids=new Array();
			var roleid="";
			$(".checkbox").not(":first").each(function(){
				if($(this).find("input").prop("checked")){
					ids.push($(this).find("input").val());
					roleid=$(this).parents("tr").find(".role").text();
				}
			})
			ids.join(",");
			if(ids.length==0){
				Alert.hint("请至少选择一条数据!");
				return;
			}
			var idStr=ids+"";
			//选择角色列表
			$.post("/common/role/load",{},function (res) {
				if(res.state==1){
					var data=res.data;
					var option="";
					for(var i=0;i<data.length;i++){
						if(roleid.indexOf(data[i].id)>-1){
							option+="<option value='"+data[i].id+"' selected='selected'>"+data[i].name+"</option>";
						}else{
							option+="<option value='"+data[i].id+"'>"+data[i].name+"</option>";
						}
					}
					var html="<div style='width:400px;'><select multiple style='width: 100%;' id='roleList'>"+option+"</select></div><button id='sure' style='width:70px;height:30px;'>确定</button>";
					Alert.code("角色分配",html);
					$("#sure").bind("click",function(){
						$.ajax({
							url:"/common/user/roleset",
							data:{"userids":idStr,"roleids":$("#roleList").val()+""},
							dataType:"json",
							type:"post",
							success:function(res){
								Alert.hint(res.message);
								if(res.state==1){

								}
								setTimeout(function(){
									$("#close").click();
									location.reload();
								},2000)

							},error:function(err){
								$("#close").click();
								location.reload();
							}
						})
					})
				}
			})
		})
	}
}