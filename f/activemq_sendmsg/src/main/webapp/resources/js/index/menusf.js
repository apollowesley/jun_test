$(function() {
	
	if(typeof($.cookie('menusf')) == "undefined"){
		$("#sidebar").attr("class","menu-min");
	}else{
		$("#sidebar").attr("class","");
	}
	
	
	if($("#sidebar").hasClass("menu-min")){
		 $("#page-wrapper").css("margin-left","30px");
		 $("#menusf").attr("checked",true);
	 }else{
		 $("#page-wrapper").css("margin-left","220px");
		 $("#menusf").attr("checked",false);
	 }
});

//保存缩放菜单状态
function menusf(){
	if(document.getElementsByName('menusf')[0].checked){
		$.cookie('menusf', '', { expires: -1 });
		$("#sidebar").attr("class","menu-min");
	}else{
		$.cookie('menusf', 'ok');
		$("#sidebar").attr("class","");
	}
	
	if($("#sidebar").hasClass("menu-min")){
		 $("#page-wrapper").css("margin-left","30px");
	 }else{
		 $("#page-wrapper").css("margin-left","220px");
	 }
}
