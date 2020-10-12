var resource={
	//配置基础属性的键值对，是否显示？控制值的属性等等？参数guns
	load:function(){
		$.ajax({
			url:"/common/resource/load",
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
			url:"/common/resource/list",
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
									$($("."+item)[i+1]).html("<a href='/common/resource/updatePage?id="+data[i].id+"'>查看详情</a>");
								}
							}
						}
						var j=i+1;
						var checkboxs='<div class="checkbox">'+
							'<input type="checkbox" id="box'+data[i].path+'" value="'+data[i].path+'">'+
							'<label for="box'+data[i].path+'"></label>'+
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
			window.location.href="/common/resource/updatePage?id="+ids;
		})
	},
	delete:function(){
		$("#delete").bind("click",function(){
			var ids=new Array();
			var flag="";
			$(".checkbox").not(":first").each(function(){
				if($(this).find("input").prop("checked")){
					ids.push($(this).find("input").val());
					flag=$(this).parents("tr").find(".inuse").text();
				}

			})
			ids.join(",");
			if(ids.length==0){
				Alert.hint("请至少选择一条数据!");
				return;
			}
			if(ids.length>1){
				Alert.hint("只能选择一条数据!");
				return;
			}
			if(flag=="是"){
				Alert.hint("文件正在使用中,不能删除~");
				return;
			}
			var idStr=ids+"";
			$.ajax({
				url:"/common/resource/delete",
				data:{"fileName":idStr},
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
					console.log(err);
				}
			})
		})
	},
	uploadFile: function (url, fileid, type, success, error) {
		var fileObj = document.getElementById(fileid).files[0]; // js 获取文件对象
		var form = new FormData(); // FormData 对象
		form.append("file", fileObj); // 文件对象
		form.append("type", type);//文件类型
		var table = document.getElementById("table").value;
		if (table) {
			form.append("table", table);//关联索引-表
		}
		var xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
		xhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
		xhr.onload = function (evt) {
			var data = JSON.parse(evt.target.responseText);
			success(data);
			setTimeout(function () {
				document.getElementById("panel").style.display = "none";
			}, 1000)
		}; //请求完成
		xhr.onerror = function (evt) {
			var data = JSON.parse(evt.target.responseText);
			if(error){
				error(data);
			}

			setTimeout(function () {
				var panel = document.getElementById("panel");
				panel.style.display = "none"
			}, 1000)
		}; //请求失败

		xhr.upload.onprogress = progressFunction;//【上传进度调用方法实现】
		xhr.upload.onloadstart = function () {//上传开始执行方法
			ot = new Date().getTime();   //设置上传开始时间
			oloaded = 0;//设置上传开始时，以上传的文件大小为0
		};
		xhr.send(form); //开始上传，发送form数据
	}
}