function ajaxPost(url,data,callName,async) {
	ajaxLoading();
	$.ajax({
	   type: "POST",
	   url: url,
	   data: data,
	   async : async, 
	   dataType : "json",
	   success: function(resultData){
		   ajaxLoadEnd();
		   callName(resultData);
	   },
	   error: function(XMLHttpRequest){
		   ajaxLoadEnd();
		   // 请求出错处理
		   if (XMLHttpRequest.status == "403") {
			   alert("会话超时!");
		   }
	   }
	});
}

function ajaxLoading(){ 
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
} 

function ajaxLoadEnd(){ 
     $(".datagrid-mask").remove(); 
     $(".datagrid-mask-msg").remove();             
} 