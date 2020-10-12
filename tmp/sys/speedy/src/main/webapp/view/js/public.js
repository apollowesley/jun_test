window.onload=function(){
	//加载
	var width=window.innerWidth-5;
	var height=window.innerHeight-5;
	var classNames=document.getElementsByClassName("box")[0];
	classNames.style.height=height+"px";
	classNames.style.width=width+"px";
}
window.onresize=function(){
	//监听屏幕变化
	var width=window.innerWidth-5;
	var height=window.innerHeight-5;
	var classNames=document.getElementsByClassName("box")[0];
	classNames.style.height=height+"px";
	classNames.style.width=width+"px";
}

