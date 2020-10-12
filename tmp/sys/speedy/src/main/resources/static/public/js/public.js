$(document).ready(function(){
	$(".box").height($(window).height()-6);
	$(".box").width($(window).width());
	$(".list-box").height((($(window).height()-4) * 0.98));
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
	$(".box").height($(window).height()-6);
	$(".box").width($(window).width());
	$(".list-box").height((($(window).height()-4) * 0.98));
});

