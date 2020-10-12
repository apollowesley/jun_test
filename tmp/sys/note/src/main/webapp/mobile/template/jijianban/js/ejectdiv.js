/*
使用方法：<input type="text" onclick="new ejectDiv().show(this)"/>
<input id="dataInput" type="hidden" name="btid"/>*/

//添加监听事件
function addEventHandler(target, type, func) {
    if (document.addEventListener)// 如果是Firefox
        target.addEventListener(type, func, false);
    else if (document.attachEvent)
        target.attachEvent("on" + type, func);
    else target["on" + type] = func;
};

var inputObj;
var req = null;
var url = null;
var content1="";
document.write('<div id="ejectDivC" style="z-index:1000;position:absolute;width:98%;" ><ul id="ejectDivUl" style="font-size:24px;padding:3px;list-style:none;min-width:200px;display:none;position:absolute;background:#fefefe;border:1px solid #cd9a5b;"></ul></div>');
document.write('<div id="ejectTableC" style="position:absolute;width:98%;border:1px solid #e6e6e6;display:none;" ></div>');
var $$ = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};

//创建方法，也是类（js中的）
function ejectDiv(name)
{
	hide_xx();
	this.name=name;
	//对象方法
	this.Introduce=function(){
		alert("My name is "+this.name);
	};
}

//原型方法

//显示内容
ejectDiv.prototype.show=function(dataObj){
	this.dataObj=dataObj;
	this.panel=$$("ejectDivC");
	this.contents=$$("ejectDivUl");
	this.draw();
	inputObj=dataObj;
	this.contents.style.display="block";
	this.bindClick();
	
	this.panel.style.left = window.screen.availWidth/2-200 + "px";
	this.panel.style.top = "20px";
	
	/*var xy = this.getAbsPoint(dataObj);
	this.panel.style.left = xy.x + "px";
	this.panel.style.top = (xy.y + dataObj.offsetHeight) + "px";*/
};

//添加内容
ejectDiv.prototype.draw=function(){
	this.ajax();
	var content2;
	content2='<li><span style="display:inline-block;width:100%;"><input type="text" id="newtravel" style="border:1px solid #cd9a5b;width:142px;margin-bottom:5px;margin-right:0;padding:5px 3px;font-size:24px;" value=""/></li><li><input type="button" id="newtravelsub" value="添加新行程" style="border:0;line-height:27px;height:27px;margin:2px;padding:0;width:180px;color:#ffffff;background-color:#0096d0;font-size:24px;"/></span></li>';
	this.contents.innerHTML=content1+content2;
};

/* 通过同步传输XMLHTTP发送参数到Bills.yy,用于获取已有的行程id */
ejectDiv.prototype.ajax=function() {
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
		url = "mobile/Bills.yy?a=btlid&date="+new Date();
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
		url = "mobile/Bills.yy?a=btlid&date="+new Date();
	}
	if (req) {
		req.open("GET", url, false);
		//req.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
		req.onreadystatechange = this.complete;
		req.send();
	}
};
ejectDiv.prototype.complete=function(){
	if (req.readyState == 4) {
		if (req.status == 200) {
			var Text = req.responseText;
			Text=replaceLineBreakToBR(Text);
			//var Text = '{"type":[{"btype":"去广西旅游","bid":"79"},{"btype":"去广西旅游1","bid":"791"},]}';
			TextDoc= (new Function("return " + Text))();//转换为json
			content1="";
			for(var key in TextDoc){
				for(var i=0;i<TextDoc[key].length;i++){
					content1+='<li data="'+TextDoc.type[i].bid+'" style="cursor: pointer; width:200px;padding:2px;margin:2px;color:#2b4490;border:1px solid #feeeed;">'+TextDoc.type[i].btypename+'</li>';
					}
				}
			}
		}
};
//绑定点击事件
ejectDiv.prototype.bindClick=function(){
	obj=this;
	var lis=$$("ejectDivUl").getElementsByTagName("li");
	for(var i = 0; i < lis.length-2; i++) {
  		/*lis[i].style.cursor = "pointer";
  		lis[i].style.width = "70px";*/
		lis[i].onclick = null;
		lis[i].onmouseover = null;
		lis[i].onmouseout = null;
		lis[i].onclick = function(){obj.dataObj.value=this.innerHTML;$$("dataInput").value=this.getAttribute("data");$$("ejectDivUl").style.display="none";};
		lis[i].onmouseover = function(){this.style.backgroundColor="#EFEFEF";this.style.color="#000000";};
		lis[i].onmouseout = function(){this.style.backgroundColor="";this.style.color="#2b4490";};
	}
	$$("newtravelsub").onclick=function(){obj.ajaxadd();};
};

/* 通过同步传输XMLHTTP发送参数到Bills.yy,用于添加新行程*/
ejectDiv.prototype.ajaxadd=function() {
	if($$("newtravel").value==""){
		alert("行程名不能为空！");
		return;
		}
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
		url = "mobile/Bills.yy?a=addtravel&btype="+$$("newtravel").value+"&date="+new Date();
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
		url = "mobile/Bills.yy?a=addtravel&btype="+$$("newtravel").value+"&date="+new Date();
	}
	url=encodeURI(encodeURI(url));
	if (req) {
		req.open("POST", url, false);
		req.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
		req.onreadystatechange = this.addcomplete;
		req.send();
	}
};
ejectDiv.prototype.addcomplete=function(){
	if (req.readyState == 4) {
		if (req.status == 200) {
			var Text = req.responseText;
			if(Text=="success")
				{alert("添加成功");}
			}
		}
};
//获取对象坐标
ejectDiv.prototype.getAbsPoint = function (e){
	var x = e.offsetLeft;
	var y = e.offsetTop;
	while(e = e.offsetParent){
		x += e.offsetLeft;
		y += e.offsetTop;
	}
	return {"x": x, "y": y};
};


//隐藏
function hide_x(evt){
	evt = evt || window.event;
	var _target= evt.target || evt.srcElement;
	if(!_target.getAttribute("Author") &&  _target != inputObj &&  _target != $$("ejectDivUl") &&  _target != $$("newtravel"))
	{
		$$("ejectDivUl").style.display="none";
	}
}
addEventHandler(document,"click",hide_x);

//显示详细行程内容
function displaydetail(obj){
	/*先做一个延时0.5s,如果超过0.5s再显示，否则不显示*/
	var objdiv=this;
	obj.onmouseout=function () {objdiv.stoptime();};//添加清除延时事件
	this.delay(this,obj);
	
}
displaydetail.prototype.delay=function(object,obj){
	this.t=setTimeout(function(){object.display(obj);},500);//延时500ms
};
displaydetail.prototype.stoptime=function(){
	 clearTimeout(this.t);//清除延时
};
displaydetail.prototype.display=function(obj){	
	this.objdata=obj;
	var objdiv=this;
	this.ajax();
	this.panel=$$("ejectTableC");
	var xy = this.getAbsPoint(obj);
	
	this.panel.style.left = (window.screen.availWidth-this.panel.style.width)/2 + "px";
	this.panel.style.top = "20px";
	/*this.panel.style.left = (xy.x-540) + "px";
	this.panel.style.top = (xy.y + obj.offsetHeight-2) + "px";*/
	this.panel.onclick=function () {objdiv.hide();};
};
displaydetail.prototype.hide=function(){
	this.panel.style.display="none";
};
/* 通过同步传输XMLHTTP发送参数到Bills.yy,用于获取所有子行程*/
displaydetail.prototype.ajax=function() {
	//alert(this.objdata.getAttribute("data"));
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
		url = "mobile/Bills.yy?a=btmid&btid="+this.objdata.getAttribute("data")+"&date="+new Date();
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
		url = "mobile/Bills.yy?a=btmid&btid="+this.objdata.getAttribute("data")+"&date="+new Date();
	}
	url=encodeURI(encodeURI(url));
	if (req) {
		req.open("GET", url, true);
		req.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
		req.onreadystatechange = this.complete;
		req.send();
	}
};
displaydetail.prototype.complete=function(){
	if (req.readyState == 4) {
		if (req.status == 200) {
			var Text = req.responseText;
			Text=replaceLineBreakToBR(Text);
			//var Text = '{"type":[{"btype":"去广西旅游","bid":"79"},{"btype":"去广西旅游1","bid":"791"},]}';
			TextDoc= (new Function("return " + Text))();//转换为json
			var content1='<div class="" id="weibo_list_wp">';
			for(var key in TextDoc){
				for(var i=0;i<TextDoc[key].length;i++){
					var a=TextDoc.type[i].bio*1;
					//var b=a==1?"支出":(a==2?"收入":(a==3?"债务":(a==4?"债权":a)));
					var b=TextDoc.type[i].bioname;
					//var c=a==1?"-":(a==2?"+":(a==3?"--":(a==4?"++":a)));
					var c=TextDoc.type[i].bsign;
					c=(a*1==5||a*1==0)?c:c+TextDoc.type[i].bamount;
					content1+='<div class="split" id=""> <div class="weibo" data-tid="" data-login="" data-uid="" data-huifu=""> <div class="wb_l"> <div> <img class="author" src="'+avatar+'" onclick="goToUserInfo(2);" /> </div> </div> <div class="wb_r"> <div class="user_info"> <span class="fl p_u"></span> <span class="fr p_t"> <span>'+TextDoc.type[i].bdate.substring(0,19)+'</span> </span> </div> <div class="wb_c_wp"> <div class="wb_c">由于 '+TextDoc.type[i].btypename +b+'<span class="wordColor">人民币'+c+'元。</span> </div> <div class="from"> <span class="fl">'+TextDoc.type[i].bcaption+'</span> <span class="fr num"> </span> </div> ';
						if(TextDoc.type[i].bidir!="null")
							{
								content1+='<div style="position:relative1;"><img class="locateimg_img" id="locateimg_img_${status.index}" data="'+TextDoc.type[i].bidir+'"/></div>';
							}
					content1+='</div></div></div><div class="wb_line"></div></div> ';
						}
			}
			$$("ejectTableC").innerHTML=(content1+'</div>');
			$$("ejectTableC").style.display="block";
			displayimage();
			}
		}
};
//获取对象坐标
displaydetail.prototype.getAbsPoint = function (e){
	var x = e.offsetLeft;
	var y = e.offsetTop;
	while(e = e.offsetParent){
		x += e.offsetLeft;
		y += e.offsetTop;
	}
	return {"x": x, "y": y};
};
function displaydetailatpage(obj){
	window.open(document.getElementsByTagName("base")[0].href+"Bills.yy?a=btmidpage&btid="+obj.getAttribute("data")+"&date="+new Date().toDateString());
}




/**显示可以选择的账单类型*/
document.write('<div id="ejectBtypeDivC" style="z-index:1000;position:absolute;width:98%;" ><ul id="ejectBtypeDivUl" style="font-size:24px;padding:3px;list-style:none;min-width:200px;display:none;position:absolute;background:#fefefe;border:1px solid #cd9a5b;"></ul></div>');

//创建方法，也是类（js中的）
function ejectBtypeDiv(name)
{
	hide_xx();
	this.name=name;
	//对象方法
	this.Introduce=function(){
		alert("My name is "+this.name);
	};
}

//原型方法

//显示内容
ejectBtypeDiv.prototype.show=function(dataObj){
	this.dataObj=dataObj;
	this.panel=$$("ejectBtypeDivC");
	this.contents=$$("ejectBtypeDivUl");
	this.draw();
	inputObj=dataObj;
	this.contents.style.display="block";
	this.bindClick();

	
	this.panel.style.left = (window.screen.availWidth-this.panel.style.width)/2 + "px";
	this.panel.style.top = "20px";
	/*var xy = this.getAbsPoint(dataObj);
	this.panel.style.left = xy.x + "px";
	this.panel.style.top = (xy.y + dataObj.offsetHeight) + "px";*/
};

//添加内容
ejectBtypeDiv.prototype.draw=function(){
	this.ajax();
	var content2;
	content2='<li><span style="display:inline-block;width:100%;"><input type="text" id="newbtype" style="border:1px solid #cd9a5b;width:142px;margin-bottom:5px;margin-right:0;padding:5px 3px;font-size:24px;" value=""/></li><li><input type="button" id="newbtypesub" value="添加新类型" style="border:0;line-height:27px;height:27px;margin:2px;padding:0;width:180px;color:#ffffff;background-color:#0096d0;font-size:24px;"/></span></li>';
	this.contents.innerHTML=content1+content2;
};

/* 通过同步传输XMLHTTP发送参数到Bills.yy,用于获取已有的行程id */
ejectBtypeDiv.prototype.ajax=function() {
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
		url = "Bills.yy?a=btype&date="+new Date();
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
		url = "Bills.yy?a=btype&date="+new Date();
	}
	if (req) {
		req.open("GET", url, false);
		//req.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
		req.onreadystatechange = this.complete;
		req.send();
	}
};
ejectBtypeDiv.prototype.complete=function(){
	if (req.readyState == 4) {
		if (req.status == 200) {
			var Text = req.responseText;
			Text=replaceLineBreakToBR(Text);
			//var Text = '{"type":[{"btype":"去广西旅游","bid":"79"},{"btype":"去广西旅游1","bid":"791"},]}';
			TextDoc= (new Function("return " + Text))();//转换为json
			content1="";
			for(var key in TextDoc){
				for(var i=0;i<TextDoc[key].length;i++){
					content1+='<li data="'+TextDoc.type[i].btypeid+'" style="float:left;cursor: pointer; width:200px;padding:2px;margin:2px;color:#2b4490;border:1px solid #feeeed;">'+TextDoc.type[i].btypename+'</li>';
					}
				}
			}
		}
};
//绑定点击事件
ejectBtypeDiv.prototype.bindClick=function(){
	obj=this;
	var lis=$$("ejectBtypeDivUl").getElementsByTagName("li");
	for(var i = 0; i < lis.length-2; i++) {
  		/*lis[i].style.cursor = "pointer";
  		lis[i].style.width = "70px";*/
		lis[i].onclick = null;
		lis[i].onmouseover = null;
		lis[i].onmouseout = null;
		lis[i].onclick = function(){obj.dataObj.value=this.innerHTML;$$("btypename").value=this.getAttribute("data");$$("ejectBtypeDivUl").style.display="none";};
		lis[i].onmouseover = function(){this.style.backgroundColor="#EFEFEF";this.style.color="#000000";};
		lis[i].onmouseout = function(){this.style.backgroundColor="";this.style.color="#2b4490";};
	}
	$$("newbtypesub").onclick=function(){obj.ajaxadd();};
};

/* 通过同步传输XMLHTTP发送参数到Bills.yy,用于添加新行程*/
ejectBtypeDiv.prototype.ajaxadd=function() {
	if($$("newbtype").value==""){
		alert("账单类型名不能为空！");
		return;
		}
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
		url = "Bills.yy?a=addnewbtype&btypename="+$$("newbtype").value+"&date="+new Date();
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
		url = "Bills.yy?a=addnewbtype&btypename="+$$("newbtype").value+"&date="+new Date();
	}
	url=encodeURI(encodeURI(url));
	if (req) {
		req.open("POST", url, false);
		req.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
		req.onreadystatechange = this.addcomplete;
		req.send();
	}
};
ejectBtypeDiv.prototype.addcomplete=function(){
	if (req.readyState == 4) {
		if (req.status == 200) {
			var Text = req.responseText;
			if(Text=="success")
				{alert("添加成功");}else if(Text=="hasExist"){alert("此类型已经存在！");}
			}
		}
};
//获取对象坐标
ejectBtypeDiv.prototype.getAbsPoint = function (e){
	var x = e.offsetLeft;
	var y = e.offsetTop;
	while(e = e.offsetParent){
		x += e.offsetLeft;
		y += e.offsetTop;
	}
	return {"x": x, "y": y};
};


//隐藏
function hide_xx(evt){
	evt = evt || window.event;
	var _target= evt.target || evt.srcElement;
	if(!_target.getAttribute("Author") &&  _target != inputObj &&  _target != $$("ejectBtypeDivUl") &&  _target != $$("newtravel")&&_target != $$("newbtype"))
	{
		$$("ejectBtypeDivUl").style.display="none";
		$$("ejectDivUl").style.display="none";//前面那个的
	}
}
addEventHandler(document,"click",hide_xx);

/**使用class名称获取元素*/
var $c=function getElementsByClassName(n) {
	   var classElements = [],allElements = document.getElementsByTagName('*');
	   for (var i=0; i< allElements.length; i++ )
	  {
	      if (allElements[i].className == n ) {
	          classElements[classElements.length] = allElements[i];
	       }
	  }
	  return classElements;
	}

function getSignTag(divId){
	$$("menu_display_1").style.display="none";
	$$("menu_display_2").style.display="none";
	$$("menu_display_3").style.display="none";
	$$("menu_display_close_"+divId).onclick=function(){$$("menu_display_"+divId).style.display="none"};
	$$("menu_display_"+divId).style.display="block";
}
function setSignTag(str){
	if($$("bcaption").value=="再说点什么……")
		$$("bcaption").value="";
	if(str=="插入自定义话题")
		$$("bcaption").value+="#"+str+"#";
	else $$("bcaption").value+="#"+str+"#";
}

/*用于把图片显示出来，默认先不显示图片。显示小图*/
function displayimage(){
	var locateimg_img_list=$c("locateimg_img");
	for(var i = 0; i < locateimg_img_list.length; i++) {
		var src=locateimg_img_list[i].getAttribute("data");
		var srcs=src.split("\.");
		src=srcs[0]+"small."+srcs[1];
		locateimg_img_list[i].setAttribute("src",src);
		locateimg_img_list[i].style.display="block";
		locateimg_img_list[i].onclick=function(){displayorgimg(this.getAttribute("data"));};
	}
}
displayimage();

/*点击小图显示大图*/
document.write('<div id="displayorgimg" style="position:fixed;width:100%;height:100%;display:none;top:0;z-index:100; " ><img title="点击隐藏"  style="position:relative;display:block;z-index:100;"/><div style="background-color:#000000;height: 100%; position: fixed; width: 100%; left: 0px; top: 0px; z-index: 99;"></div></div>');
function displayorgimg(imgpath){
	var locateimg_img_list=$$("displayorgimg").getElementsByTagName("img");;
	for(var i = 0; i < locateimg_img_list.length; i++) {
		var t=locateimg_img_list[i];	
		t.setAttribute("src",templatePath+"/img/loading.gif");
		$$("displayorgimg").style.display="block";
		t.onclick=function(){$$("displayorgimg").style.display="none";};
		t.style.width="16px";
		t.style.top=(window.screen.availHeight*1-16)/3+"px";
		t.style.margin="auto";
		var whmax;
		var obj=new Image();
		obj.src=imgpath;
		obj.onload=function(){
			t.style.width="";
			/*需要把图片设置到可见区域*/
			//(obj.width*1-window.screen.availWidth*1)>(obj.height*1-window.screen.availHeight*1)?(obj.width*1>window.screen.availWidth*1?t.style.width=(window.screen.availWidth+"px"):t.style.width=(obj.width+"px")):(obj.height*1>window.screen.availHeight*1?t.style.height=(window.screen.availHeight+"px"):t.style.height=(obj.height+"px"));
			(obj.height*1-window.screen.availHeight*1)<0?t.style.top=(window.screen.availHeight*1-obj.height*1)/3+"px":t.style.top=0+"px";
			t.setAttribute("src",imgpath);
			(obj.height*1-window.screen.availHeight*1)<0?t.style.top=(window.screen.availHeight*1-obj.height*1)/3+"px":t.style.top=0+"px";
		}
	}
}

/**把返回的字符串（JSON）中的回车换行符换成<br/>*/
function replaceLineBreakToBR(str){
	str=str.replace(/\r\n/g,"<br/>");
	str=str.replace(/\n/g,"<br/>");
	return str;
}


/**从这里开始页面微博--评论--的js*/
function weiboreply(){
	
}
/**到这里结束页面微博--评论--的js*/

/**从这里开始页面微博--转发--的js*/
function weiboforward(){
	
}
/**到这里结束页面微博--转发--的js*/
