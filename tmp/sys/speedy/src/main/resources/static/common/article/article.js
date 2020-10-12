var article={
	//配置基础属性的键值对，是否显示？控制值的属性等等？参数guns
	load:function(){
		$.ajax({
			url:"/common/article/load",
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
			url:"/common/article/loadPage",
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
							if(parame.length>120)parame=parame.substr(0,120)+"...";
							if(parame!=null){
								if(item!="content") {
									$($("." + item)[i + 1]).text(parame);
								}else{
									$($("."+item)[i+1]).html("<a href='/common/article/updatePage?id="+data[i].id+"'>查看详情</a>");
								}
							}
						}
						var j=i+1;
						var checkboxs='<div class="checkbox">'+
										        '<input type="checkbox" id="box'+data[i].id+'" value="'+data[i].id+'">'+
										            '<label for="box'+data[i].id+'"></label>'+
										'</div>';
						$($(".checkbox")[j]).parent().html(checkboxs);
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
			window.location.href="/common/article/updatePage?id="+ids;
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
				url:"/common/article/delete",
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
	}
	
}