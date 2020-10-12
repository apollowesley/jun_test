$(document).ready(function(){
	$(".box").css("width","100%");
	$(".box").height($(window).height()-3);
	$(".list-box").height($(window).height()-4);
	//全选控制
	$("#all").change(function(){
		//亦可进行反选操作
		if($(this).prop("checked")){
			$(".checkbox").each(function(){
				$(this).find("input").prop("checked",true);
			})
		}else{
			$(".checkbox").each(function(){
				$(this).find("input").prop("checked",false);
			})
		}

	})
})
$(window).resize(function () {
	$(".box").css("width","100%");
	$(".box").height($(window).height()-3);
	$(".list-box").height($(window).height()-4);
});

