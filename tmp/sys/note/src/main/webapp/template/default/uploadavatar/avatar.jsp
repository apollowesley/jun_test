<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
var Class = {
	create: function() {
		return function() { this.initialize.apply(this, arguments); }
	}
}
</script>
<script type="text/javascript" src="${templatePath}/uploadavatar/Drag.js"></script>
<script type="text/javascript" src="${templatePath}/uploadavatar/Resize.js"></script>
<script type="text/javascript">
var isIE = (document.all) ? true : false;
isIE=false;/*貌似IE10以后对W3C标准支持好了，不再是自己的那一套独行标准了，不需要单独使用这个了。IE和firefox用同样的代码，一样的效果。*/
//var isIE6 = isIE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6);
var isIE6 =false;/*貌似IE10以后对W3C标准支持好了，不再是自己的那一套独行标准了，不需要单独使用这个了。貌似我没有想为IE6准备*/
var $ = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};

var Extend = function(destination, source) {
	for (var property in source) {
		destination[property] = source[property];
	}
}

var Bind = function(object, fun) {
	return function() {
		return fun.apply(object, arguments);
	}
}

var BindAsEventListener = function(object, fun) {
	var args = Array.prototype.slice.call(arguments).slice(2);
	return function(event) {
		return fun.apply(object, [event || window.event].concat(args));
	}
}

var CurrentStyle = function(element){
	return element.currentStyle || document.defaultView.getComputedStyle(element, null);
}

function addEventHandler(oTarget, sEventType, fnHandler) {
	if (oTarget.addEventListener) {
		oTarget.addEventListener(sEventType, fnHandler, false);
	} else if (oTarget.attachEvent) {
		oTarget.attachEvent("on" + sEventType, fnHandler);
	} else {
		oTarget["on" + sEventType] = fnHandler;
	}
};

function removeEventHandler(oTarget, sEventType, fnHandler) {
    if (oTarget.removeEventListener) {
        oTarget.removeEventListener(sEventType, fnHandler, false);
    } else if (oTarget.detachEvent) {
        oTarget.detachEvent("on" + sEventType, fnHandler);
    } else { 
        oTarget["on" + sEventType] = null;
    }
};

//图片切割
var ImgCropper = Class.create();
ImgCropper.prototype = {
  //容器对象,控制层,图片地址
  initialize: function(container, handle, url, options) {
	this._Container = $(container);//容器对象
	this._layHandle = $(handle);//控制层
	this.Url = url;//图片地址
	
	this._layBase = this._Container.appendChild(document.createElement("img"));//底层
	this._layCropper = this._Container.appendChild(document.createElement("img"));//切割层
	this._layCropper.onload = Bind(this, this.SetPos);
	//用来设置大小
	this._tempImg = document.createElement("img");
	this._tempImg.onload = Bind(this, this.SetSize);
	
	this.SetOptions(options);
	
	this.Opacity = Math.round(this.options.Opacity);
	this.Color = this.options.Color;
	this.Scale = !!this.options.Scale;
	this.Ratio = Math.max(this.options.Ratio, 0);
	this.Width = Math.round(this.options.Width);
	this.Height = Math.round(this.options.Height);
	
	//设置预览对象
	var oPreview = $(this.options.Preview);//预览对象
	if(oPreview){
		oPreview.style.position = "relative";
		oPreview.style.overflow = "hidden";
		this.viewWidth = Math.round(this.options.viewWidth);
		this.viewHeight = Math.round(this.options.viewHeight);
		//预览图片对象
		this._view = oPreview.appendChild(document.createElement("img"));
		this._view.style.position = "absolute";
		this._view.onload = Bind(this, this.SetPreview);
	}
	//设置拖放
	this._drag = new Drag(this._layHandle, { Limit: true, onMove: Bind(this, this.SetPos), Transparent: true });
	//设置缩放
	this.Resize = !!this.options.Resize;
	if(this.Resize){
		var op = this.options, _resize = new Resize(this._layHandle, { Max: true, onResize: Bind(this, this.SetPos) });
		//设置缩放触发对象
		op.RightDown && (_resize.Set(op.RightDown, "right-down"));
		op.LeftDown && (_resize.Set(op.LeftDown, "left-down"));
		op.RightUp && (_resize.Set(op.RightUp, "right-up"));
		op.LeftUp && (_resize.Set(op.LeftUp, "left-up"));
		op.Right && (_resize.Set(op.Right, "right"));
		op.Left && (_resize.Set(op.Left, "left"));
		op.Down && (_resize.Set(op.Down, "down"));
		op.Up && (_resize.Set(op.Up, "up"));
		//最小范围限制
		this.Min = !!this.options.Min;
		this.minWidth = Math.round(this.options.minWidth);
		this.minHeight = Math.round(this.options.minHeight);
		//设置缩放对象
		this._resize = _resize;
	}
	//设置样式
	this._Container.style.position = "relative";
	this._Container.style.overflow = "hidden";
	this._layHandle.style.zIndex = 200;
	this._layCropper.style.zIndex = 100;
	this._layBase.style.position = this._layCropper.style.position = "absolute";
	this._layBase.style.top = this._layBase.style.left = this._layCropper.style.top = this._layCropper.style.left = 0;//对齐
	//初始化设置
	this.Init();
  },
  //设置默认属性
  SetOptions: function(options) {
    this.options = {//默认值
		Opacity:	50,//透明度(0到100)
		Color:		"",//背景色
		Width:		0,//图片高度
		Height:		0,//图片高度
		//缩放触发对象
		Resize:		false,//是否设置缩放
		Right:		"",//右边缩放对象
		Left:		"",//左边缩放对象
		Up:			"",//上边缩放对象
		Down:		"",//下边缩放对象
		RightDown:	"",//右下缩放对象
		LeftDown:	"",//左下缩放对象
		RightUp:	"",//右上缩放对象
		LeftUp:		"",//左上缩放对象
		Min:		true,//是否最小宽高限制(为true时下面min参数有用)
		minWidth:	50,//最小宽度
		minHeight:	50,//最小高度
		Scale:		true,//是否按比例缩放
		Ratio:		0,//缩放比例(宽/高)
		//预览对象设置
		Preview:	"",//预览对象
		viewWidth:	0,//预览宽度
		viewHeight:	0//预览高度
    };
    Extend(this.options, options || {});
  },
  //初始化对象
  Init: function() {
	//设置背景色
	this.Color && (this._Container.style.backgroundColor = this.Color);
	//设置图片
	this._tempImg.src = this._layBase.src = this._layCropper.src = this.Url;
	//设置透明
	if(isIE){
		this._layBase.style.filter = "alpha(opacity:" + this.Opacity + ")";
	} else {
		this._layBase.style.opacity = this.Opacity / 100;
	}
	//设置预览对象
	this._view && (this._view.src = this.Url);
	//设置缩放
	if(this.Resize){
		with(this._resize){
			Scale = this.Scale; Ratio = this.Ratio; Min = this.Min; minWidth = this.minWidth; minHeight = this.minHeight;
		}
	}
  },
  //设置切割样式
  SetPos: function() {
	//ie6渲染bug
	if(isIE6){ with(this._layHandle.style){ zoom = .9; zoom = 1; }; };
	//获取位置参数
	var p = this.GetPos();
	//按拖放对象的参数进行切割
	this._layCropper.style.clip = "rect(" + p.Top + "px " + (p.Left + p.Width) + "px " + (p.Top + p.Height) + "px " + p.Left + "px)";
	//设置预览
	this.SetPreview();
  },
  //设置预览效果
  SetPreview: function() {
	if(this._view){
		//预览显示的宽和高
		var p = this.GetPos(), s = this.GetSize(p.Width, p.Height, this.viewWidth, this.viewHeight), scale = s.Height / p.Height;
		//按比例设置参数
		var pHeight = this._layBase.height * scale, pWidth = this._layBase.width * scale, pTop = p.Top * scale, pLeft = p.Left * scale;
		//设置预览对象
		with(this._view.style){
			//设置样式
			width = pWidth + "px"; height = pHeight + "px"; top = - pTop + "px "; left = - pLeft + "px";
			//切割预览图
			clip = "rect(" + pTop + "px " + (pLeft + s.Width) + "px " + (pTop + s.Height) + "px " + pLeft + "px)";
		}
	}
  },
  //设置图片大小
  SetSize: function() {
	var s = this.GetSize(this._tempImg.width, this._tempImg.height, this.Width, this.Height);
	//设置底图和切割图
	this._layBase.style.width = this._layCropper.style.width = s.Width + "px";
	this._layBase.style.height = this._layCropper.style.height = s.Height + "px";
	//设置拖放范围
	this._drag.mxRight = s.Width; this._drag.mxBottom = s.Height;
	//设置缩放范围
	if(this.Resize){ this._resize.mxRight = s.Width; this._resize.mxBottom = s.Height; }
  },
  //获取当前样式
  GetPos: function() {
	with(this._layHandle){
		return { Top: offsetTop, Left: offsetLeft, Width: offsetWidth, Height: offsetHeight }
	}
  },
  //获取尺寸
  GetSize: function(nowWidth, nowHeight, fixWidth, fixHeight) {
	var iWidth = nowWidth, iHeight = nowHeight, scale = iWidth / iHeight;
	//按比例设置
	if(fixHeight){ iWidth = (iHeight = fixHeight) * scale; }
	if(fixWidth && (!fixHeight || iWidth > fixWidth)){ iHeight = (iWidth = fixWidth) / scale; }
	//返回尺寸对象
	return { Width: iWidth, Height: iHeight }
  }
}
</script>

<style type="text/css">
#rRightDown,#rLeftDown,#rLeftUp,#rRightUp,#rRight,#rLeft,#rUp,#rDown{
	position:absolute;
	background:#FFF;
	border: 1px solid #333;
	width: 6px;
	height: 6px;
	z-index:500;
	font-size:0;
	opacity: 0.5;
	filter:alpha(opacity=50);
}

#rLeftDown,#rRightUp{cursor:ne-resize;}
#rRightDown,#rLeftUp{cursor:nw-resize;}
#rRight,#rLeft{cursor:e-resize;}
#rUp,#rDown{cursor:n-resize;}

#rLeftDown{left:-4px;bottom:-4px;}
#rRightUp{right:-4px;top:-4px;}
#rRightDown{right:-4px;bottom:-4px;background-color:#00F;}
#rLeftUp{left:-4px;top:-4px;}
#rRight{right:-4px;top:50%;margin-top:-4px;}
#rLeft{left:-4px;top:50%;margin-top:-4px;}
#rUp{top:-4px;left:50%;margin-left:-4px;}
#rDown{bottom:-4px;left:50%;margin-left:-4px;}

#bgDiv{width:300px; height:400px; border:1px solid #666666; position:relative;}
.dragDiv{border:1px dashed #fff; width:100px; height:100px; top:50px; left:50px; cursor:move; }
.inputpreview input{width:60px;height:30px;}
.generateinput{display:inline-block;position: relative; top: -35px; left: 200px;height:0px;}
.generateinput input{width:80px;height:30px;background:#999999}
</style>
<style  type="text/css">
/*此处是上传框*/
*{margin:0;padding:0;} 
a{text-decoration:none;} 
.btn_addPic{ 
display: block; 
position: relative; 
width: 140px; 
height: 39px; 
overflow: hidden; 
border: 1px solid #EBEBEB; 
background: none repeat scroll 0 0 #F3F3F3; 
color: #999999; 
cursor: pointer; 
text-align: center; 
} 
.btn_addPic span{display: block;line-height: 39px;} 
.btn_addPic em { 
background:url(${templatePath}/img/t014ce592c1a0b2d489.png) 0 0; 
display: inline-block; 
width: 18px; 
height: 18px; 
overflow: hidden; 
margin: 10px 5px 10px 0; 
line-height: 20em; 
vertical-align: middle; 
} 
.btn_addPic:hover em{background-position:-19px 0;} 
.btn_addPic .filePrew { 
display: block; 
position: absolute; 
top: 0; 
left: 0; 
width: 140px; 
height: 39px; 
font-size: 100px; /* 增大不同浏览器的可点击区域 */ 
opacity: 0; /* 实现的关键点 */ 
filter:alpha(opacity=0);/* 兼容IE */ 
}
</style>
<h2 class="myAvatarText">设置新的头像</h2>
<form method="post" name="form2" enctype="MULTIPART/FORM-DATA" action="${templatePath}/uploadavatar/AnswerFile.jsp" target="upAnswerFile">
<A class="btn_addPic"><SPAN><EM>+</EM>选择图片</SPAN> <INPUT class="filePrew" title="支持jpg、jpeg、gif、png格式，文件小于2M" tabIndex=3 type="file" size=3  type="file" name="file" id="file" onchange="check();"></A>
</form>
<div class="generateinput"><input type="button" value="确认上传头像" onclick="javascript:generateImg();"/></div>
<table style="border:0">
  <tr>
    <td><div id="bgDiv">
        <div id="dragDiv" class="dragDiv">&nbsp;
          <div id="rRightDown"> </div>
          <div id="rLeftDown"> </div>
          <div id="rRightUp"> </div>
          <div id="rLeftUp"> </div>
          <div id="rRight"> </div>
          <div id="rLeft"> </div>
          <div id="rUp"> </div>
          <div id="rDown"> </div>
        </div>
      </div></td>
    <td><div id="viewDiv" style="width:300px; height:300px;"> </div></td>
  </tr>
</table>
<div class="inputpreview">
<input id="idSize" type="button" value="缩小显示" />
<input id="idOpacity" type="button" value="全透明" />
<input id="idColor" type="button" value="白色背景" />
<input id="idScale" type="hidden" value="使用比例" />
<input id="idMin" type="hidden" value="启用最小尺寸（最小50px）" />
<input id="idView" type="button" value="缩小预览" />
<input id="idImg" type="hidden" value="换图片" />
</div>
<script type="text/javascript">
	function check() {
		if (document.form2.file.value == "") {
			alert("请选择一张图片！！");
			return false;
		}
		document.form2.submit();
		return true;
	}
</script>
<iframe name="generateFile" id="generateFile" style= "display:none"></iframe>
<iframe name="upAnswerFile" id="upAnswerFile" style= "display:none"></iframe>

<script type="text/javascript">

var ic = new ImgCropper("bgDiv", "dragDiv", "image/avatar/avatar.png", {
	Width: 300, Height: 400, Color: "#000",
	Resize: true,
	Right: "rRight", Left: "rLeft", Up:	"rUp", Down: "rDown",
	RightDown: "rRightDown", LeftDown: "rLeftDown", RightUp: "rRightUp", LeftUp: "rLeftUp",
	Preview: "viewDiv", viewWidth: 300, viewHeight: 300
})

$("idSize").onclick = function(){
	if(ic.Height == 200){
		ic.Height = 400;
		this.value = "缩小显示";
	}else{
		ic.Height = 200;
		this.value = "还原显示";
	}
	ic.Init();
}

$("idOpacity").onclick = function(){
	if(ic.Opacity == 0){
		ic.Opacity = 50;
		this.value = "全透明";
	}else{
		ic.Opacity = 0;
		this.value = "半透明";
	}
	ic.Init();
}

$("idColor").onclick = function(){
	if(ic.Color == "#000"){
		ic.Color = "#fff";
		this.value = "白色背景";
	}else{
		ic.Color = "#000";
		this.value = "黑色背景";
	}
	ic.Init();
}

$("idScale").onclick = function(){
	if(ic.Scale){
		ic.Scale = false;
		this.value = "使用比例";
	}else{
		ic.Scale = true;
		this.value = "取消比例";
	}
	ic.Init();
}

$("idMin").onclick = function(){
	if(ic.Min){
		ic.Min = false;
		this.value = "设置最小尺寸";
	}else{
		ic.Min = true;
		this.value = "取消最小尺寸";
	}
	ic.Init();
}

$("idView").onclick = function(){
	if(ic.viewWidth == 200){
		ic.viewWidth = 300;
		this.value = "缩小预览";
	}else{
		ic.viewWidth = 200;
		this.value = "扩大预览";
	}
	ic.Init();
}
var filenameGlobal=null;
function generateImg(){
	if(filenameGlobal===null){alert("请选择一张图片！");return false;}
	var strgenerateImg="filename="+filenameGlobal;
	strgenerateImg+="&top="+$("dragDiv").style.top.replace("px","");
	strgenerateImg+="&left="+$("dragDiv").style.left.replace("px","");
	strgenerateImg+="&width="+$("dragDiv").style.width.replace("px","");
	strgenerateImg+="&height="+$("dragDiv").style.height.replace("px","");
	strgenerateImg="${templatePath}/uploadavatar/GenerateImg.jsp?"+strgenerateImg;
	$('generateFile').src=strgenerateImg;
}
function callbackt(filename){
	filenameGlobal=filename;
	ic.Url = "image/avatarTemp/"+filename;
	ic.Init();
}
function callbackg(result){
	if(result=="success"){
		alert("头像上传成功！");
		location.replace(location.href);
	}
	else{
		alert("头像上传失败！请重试~~~");
		}
}
</script>

