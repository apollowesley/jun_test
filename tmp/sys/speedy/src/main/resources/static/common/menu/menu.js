var menu={
	//配置基础属性的键值对，是否显示？控制值的属性等等？参数guns
	data:function(){
		return [
            {title: '唯一ID', field: 'id', visible: false, align: 'center', valign: 'middle'},
		]
	},
	load:function(){
		$.ajax({
			url:"/common/menu/load",
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
			url:"/common/menu/loadPage",
			data:{"page":page,"pageSize":pageSize},
			dataType:"json",
			type:"post",
			success:function(res){
				if(res.state==1){
					var data=res.data;
					$(".menu").find("tbody").html("");
					//最顶级
					for(var i=0;i<data.length;i++){
						var tagTrs=$("thead").html();
						$(".menu").find("tbody").append(tagTrs);
						for(var item in data[i]){
							var parame=data[i][item]+"";
							if(parame=="null")parame="";
							if(parame.length>50)parame=parame.substr(0,50)+"...";
							if(parame!=null){
								if(parame.indexOf("http")>-1||parame.indexOf("images")>-1){
									$($("."+item)[i+1]).html("<img width='50' src='"+parame+"' />");
								}else{
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
						$($(".checkbox")[j]).parent().parent().prop("id",data[i].id);
					}
					//二级
					for(var i=0;i<data.length;i++){
						if(data[i].menuSon.length>0){
							var menuSon=data[i].menuSon;
							for(var m=0;m<menuSon.length;m++){
								var tagTrs1=$("thead").html();
								$("#box"+menuSon[m].parent).parent().parent().parent().after(tagTrs1);
								var j=$("#box"+menuSon[m].parent).parent().parent().parent().index()+2;
								//处理参数
								for(var item in menuSon[m]){
									var parame=menuSon[m][item]+"";
									if(parame=="null")parame="";
									if(parame!=null){
										if(parame.indexOf("http")>-1||parame.indexOf("images")>-1){
											$($("."+item)[j]).html("<img width='50' src='"+parame+"' />");
										}else{
											$($("."+item)[j]).text(parame);
										}
									}
								}
								
								var checkboxs='<div class="checkbox">'+
												        '<input type="checkbox" id="box'+menuSon[m].id+'" value="'+menuSon[m].id+'">'+
												            '<label for="box'+menuSon[m].id+'"></label>'+
												'</div>';
								$($(".checkbox")[j]).parent().html(checkboxs);
								var parents=j-1;
								$($(".name")[parents]).addClass("showMenu showMenus");
								$($(".name")[j]).parent().addClass("sonMenus sonMenu"+data[i].id);
								$($(".parent")[j]).text(menuSon[m].parentName);
								$($(".checkbox")[j]).parent().parent().prop("id",menuSon[m].id);
								//权限
								if(menuSon[m].handle.length>0){
									$($(".name")[j]).addClass("showMenu");
									var handle=menuSon[m].handle;
									var hs=j+1;
									for(var h=0;h<handle.length;h++){
										$($(".name")[j]).parent().after(tagTrs1);
										//处理参数
										for(var items in handle[h]){
											var parame=handle[h][items]+"";
											if(parame=="null")parame="";
											if(parame!=null){
												if(parame.indexOf("http")>-1||parame.indexOf("images")>-1){
													$($("."+items)[hs]).html("<img width='50' src='"+parame+"' />");
												}else{
													$($("."+items)[hs]).text(parame);
												}
											}
										}
										var checkboxs1='<div class="checkbox">'+
								        '<input type="checkbox" id="box'+handle[h].id+'" value="'+handle[h].id+'">'+
								            '<label for="box'+handle[h].id+'"></label>'+
														'</div>';
										$($(".checkbox")[hs]).parent().html(checkboxs1);
										var handles=hs-1;
										$($(".name")[handles]).addClass("showMenu showMenus");
										$($(".name")[hs]).parent().addClass("handles sonMenu"+menuSon[m].id);
										$($(".parent")[hs]).text(handle[h].parentName);
									}
									
								}
								
							}
						}
					}
					//123456789
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
				$(".showMenu").click(function(){
					var id=$(this).parent().prop("id");
					var className=".sonMenu"+id;
					var classNames=$(this).prop("class");
					if(classNames.indexOf("showMenus")>-1){
						var sonMenus=$(className);
						for(var i=0;i<sonMenus.length;i++){
							var ids=$(sonMenus[i]).prop("id");
							$(".sonMenu"+ids).toggle(300);
						}
						$(".sonMenu"+id).find(".name").removeClass("showMenus");
						$(this).removeClass("showMenus");
					}else{
						$(this).addClass("showMenus");
					}
				
					$(className).toggle(300);
				})
			},error:function(err){
				console.log(err);
			}
		})
	},
	update:function(id){
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
			window.location.href="/common/menu/updatePage?id="+ids;
		})
	}
}