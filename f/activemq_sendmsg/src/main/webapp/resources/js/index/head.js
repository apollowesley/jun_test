var locat = (window.location+'').split('/'); 
$(function(){if('main'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});


$(function(){

	//换肤
	$("#skin-colorpicker").ace_colorpicker().on("change",function(){
		var b=$(this).find("option:selected").data("class");
		hf(b);
	});
});

var USER_ID;

/*$(function(){
	$.ajax({
		type: "POST",
		url: locat+'/head/getUname.do?tm='+new Date().getTime(),
    	data: encodeURI(""),
		dataType:'json',
		//beforeSend: validateData,
		cache: false,
		success: function(data){
			//alert(data.list.length);
			 $.each(data.list, function(i, list){
				 
				 //登陆者资料
				 $("#user_info").html('<small>Welcome</small> '+list.NAME+'');
				 
				 
				 USER_ID = list.USER_ID;//用户ID
				 hf(list.SKIN);//皮肤
				 
				 if(list.USERNAME != 'admin'){
					 $("#adminmenu").hide();	//隐藏菜单设置
					 $("#adminzidian").hide();	//隐藏数据字典
					 $("#systemset").hide();	//隐藏系统设置
					 $("#productCode").hide();	//隐藏代码生成
				 }
				 
			 });
		}
	});
});*/

function hf(b){
	
	var a=$(document.body);
	a.attr("class",a.hasClass("navbar-fixed")?"navbar-fixed":"");
	if(b!="default"){
		a.addClass(b);
	}if(b=="skin-1"){
		$(".ace-nav > li.grey").addClass("dark");
	}else{
		$(".ace-nav > li.grey").removeClass("dark");
	}
	if(b=="skin-2"){
			$(".ace-nav > li").addClass("no-border margin-1");
			$(".ace-nav > li:not(:last-child)").addClass("white-pink")
			.find('> a > [class*="icon-"]').addClass("pink").end()
			.eq(0).find(".badge").addClass("badge-warning");
	}else{
			$(".ace-nav > li").removeClass("no-border").removeClass("margin-1");
			$(".ace-nav > li:not(:last-child)").removeClass("white-pink")
			.find('> a > [class*="icon-"]').removeClass("pink").end()
			.eq(0).find(".badge").removeClass("badge-warning");
	}
	if(b=="skin-3"){
		$(".ace-nav > li.grey").addClass("red").find(".badge").addClass("badge-yellow");
	}else{
		$(".ace-nav > li.grey").removeClass("red").find(".badge").removeClass("badge-yellow");
	}
	
	//初始化右侧窗口内容高度为100%
	$("#page-wrapper").css("height","100%");
}

