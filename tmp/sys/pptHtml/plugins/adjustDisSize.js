//==============================================fadePic部分=============================================================
var getEle = function(id) {
	return "string" === typeof id ? document.getElementById(id) : id;
};

var isIE = (document.all) ? true : false;

if (!isIE) {
	// 这个位置的含义不是很清楚？？？需要好好研究一下…………
	HTMLElement.prototype.__defineGetter__("currentStyle", function() {
		return this.ownerDocument.defaultView.getComputedStyle(this, null);
	});
}

// 这个东西有点像类制造工厂
var Class = {
	create : function() {
		return function() {
			this.initialize.apply(this, arguments);
		}
	}
}

// 复制拷贝性质的代码
Object.extend = function(destination, source) {
	for ( var property in source) {
		destination[property] = source[property];
	}
	return destination;
}

var FadeStruct = function(options) {
	this.run = false;
	// 是否渐变
	this.start = 0;
	// 开始值
	this.end = 0;
	// 结束值
	this.target = 0;
	// 目标值
	Object.extend(this, options || {});
}

// 下面定义 渐变对象 Fade
// 为什么要采用下面方法定义Fade类，我认为最根本的原因是：需要往构造函数中传递参数。
var Fade = Class.create();

Fade.prototype = {
	initialize : function(obj, options) {
		var obj = getEle(obj);
		obj.style.overflow = "hidden";
		this._obj = obj;
		this._timer = null;
		// 定时器
		this._finish = true;
		// 是否执行完成
		this._fun = function() {
		};
		// 渐变程序
		this._x = this._y = 0;
		// 变换点位置
		// 设置获取透明度程序
		this._setOpacity = isIE ? function(opacity) {
			obj.style.filter = "alpha(opacity:" + opacity + ")";
		} : function(opacity) {
			obj.style.opacity = opacity / 100;
		};
		this._getOpacity = isIE ? function() {
			return parseInt(obj.filters["alpha"].opacity);
		} : function(opacity) {
			return 100 * parseFloat(obj.currentStyle.opacity);
		};
		// 获取边框宽度程序
		this._xBorder = function() {
			return (parseInt(obj.currentStyle.borderLeftWidth) + parseInt(obj.currentStyle.borderRightWidth));
		}
		this._yBorder = function() {
			return (parseInt(obj.currentStyle.borderTopWidth) + parseInt(obj.currentStyle.borderBottomWidth));
		}
		this.SetOptions(options);
		this.Mode = this.options.Mode;
		this.Time = Math.abs(this.options.Time);
		this.onFinish = this.options.onFinish;
		// 先设置特殊默认值
		this.Opacity = new FadeStruct({
			end : 100
		});
		this.Top = new FadeStruct({
			start : this._obj.offsetTop,
			end : this._obj.offsetTop
		});
		this.Left = new FadeStruct({
			start : this._obj.offsetLeft,
			end : this._obj.offsetLeft
		});
		this.Height = new FadeStruct({
			end : this._obj.offsetHeight - this._yBorder()
		});
		this.Width = new FadeStruct({
			end : this._obj.offsetWidth - this._xBorder()
		});
		// 再设置用户默认值
		Object.extend(this.Opacity, this.options.Opacity);
		Object.extend(this.Top, this.options.Top);
		Object.extend(this.Left, this.options.Left);
		Object.extend(this.Height, this.options.Height);
		Object.extend(this.Width, this.options.Width);
		// 变换位置参数
		this.Height.pos = Number(this.options.Height.pos);
		this.Width.pos = Number(this.options.Width.pos);
		// 设置成默认状态
		this.Show = !this.options.Show;
		this.Step = 1;
		this.Start();
		// 重新设置Step
		this.Step = Math.abs(this.options.Step);
	},
	// 设置默认属性
	SetOptions : function(options) {
		this.options = {// 默认值
			Opacity : {}, // 透明渐变参数
			Height : {}, // 高度渐变参数
			Width : {}, // 宽度渐变参数
			Top : {}, // Top渐变参数
			Left : {}, // Left渐变参数
			Step : 10, // 变化率
			Time : 10, // 变化间隔
			Mode : "both", // 渐变顺序
			Show : false, // 是否默认打开状态
			onFinish : function() {
			}// 完成时执行
		};
		Object.extend(this.options, options || {});
	},
	// 触发
	Start : function() {
		clearTimeout(this._timer);
		// 取反表示要设置的状态
		this.Show = !this.Show;
		// 为避免透明度为null值，需要先设置一次透明度
		if (this.Opacity.run) {
			this._setOpacity(this.Show ? this.Opacity.start : this.Opacity.end);
		}
		// 根据状态设置目标值
		if (this.Show) {
			this.Opacity.target = this.Opacity.end;
			this.Top.target = this.Top.end;
			this.Left.target = this.Left.end;
			this.Height.target = this.Height.end;
			this.Width.target = this.Width.end;
		} else {
			this.Opacity.target = this.Opacity.start;
			this.Top.target = this.Top.start;
			this.Left.target = this.Left.start;
			this.Height.target = this.Height.start;
			this.Width.target = this.Width.start;
		}
		// 设置渐变程序
		switch (this.Mode.toLowerCase()) {
		case "width":
			this._fun = function() {
				this.SetWidth() && this.SetHeight();
				// 由于分了两步，下面的步长变成两倍
				this.Step = 2 * this.Step;
				this.SetOpacity();
				this.SetTop();
				this.SetLeft();
				this.Step = this.Step / 2;
			}
			break;
		case "height":
			this._fun = function() {
				his.SetHeight() && this.SetWidth();
				// 由于分了两步，下面的步长变成两倍
				this.Step = 2 * this.Step;
				this.SetOpacity();
				this.SetTop();
				this.SetLeft();
				this.Step = this.Step / 2;
			}
			break;
		case "both":
		default:
			this._fun = function() {
				this.SetHeight();
				this.SetWidth();
				this.SetOpacity();
				this.SetTop();
				this.SetLeft();
			}
		}
		// 获取变换点位置
		// 由于设置变换点后与top和left变换有冲突只能执行其一
		if (this.Height.pos) {
			this._y = this._obj.offsetTop + this._obj.offsetHeight
					* this.Height.pos;
			this.Top.run = false;
		}
		if (this.Width.pos) {
			this._x = this._obj.offsetLeft + this._obj.offsetWidth
					* this.Width.pos;
			this.Left.run = false;
		}
		this.Run();
	},
	// 执行
	Run : function() {
		clearTimeout(this._timer);
		this._finish = true;
		// 执行渐变
		this._fun();
		// 未完成继续执行
		if (this._finish) {
			this.onFinish();
		} else {
			var oThis = this;
			this._timer = setTimeout(function() {
				oThis.Run();
			}, this.Time);
		}
	},
	// 设置高度渐变
	SetHeight : function() {
		var iGet = this.Get(this.Height, this._obj.offsetHeight
				- this._yBorder());
		if (isNaN(iGet))
			return true;
		this._obj.style.height = iGet + "px";
		// 如果有变换点设置
		if (this.Height.pos) {
			this._obj.style.top = this._y - this._obj.offsetHeight
					* this.Height.pos + "px";
		}
		return false;
	},
	// 设置宽度渐变
	SetWidth : function() {
		var iGet = this
				.Get(this.Width, this._obj.offsetWidth - this._xBorder());
		if (isNaN(iGet))
			return true;
		this._obj.style.width = iGet + "px";
		if (this.Width.pos) {
			this._obj.style.left = this._x - this._obj.offsetWidth
					* this.Width.pos + "px";
		}
		return false;
	},
	// 设置top渐变
	SetTop : function() {
		var iGet = this.Get(this.Top, this._obj.offsetTop);
		if (isNaN(iGet))
			return true;
		this._obj.style.top = iGet + "px";
		return false;
	},
	// 设置left渐变
	SetLeft : function() {
		var iGet = this.Get(this.Left, this._obj.offsetLeft);
		if (isNaN(iGet))
			return true;
		this._obj.style.left = iGet + "px";
		return false;
	},
	// 设置透明渐变
	SetOpacity : function() {
		var iGet = this.Get(this.Opacity, this._getOpacity());
		if (isNaN(iGet))
			return true;
		this._setOpacity(iGet);
		return false;
	},
	// 获取设置值
	Get : function(o, now) {
		if (o.run) {
			var iStep = (o.target - now) / this.Step;
			if (iStep) {
				this._finish = false;
				if (Math.abs(iStep) < 1) {
					iStep = iStep > 0 ? 1 : -1;
				}
				return now + iStep;
			}
		}
	}
};

function Event(e) {
	var oEvent = isIE ? window.event : e;
	if (isIE) {
		oEvent.pageX = oEvent.clientX + document.documentElement.scrollLeft;
		oEvent.pageY = oEvent.clientY + document.documentElement.scrollTop;
		oEvent.stopPropagation = function() {
			this.cancelBubble = true;
		};
	}
	return oEvent;
}

//====================================================以上为fadePic支持层==============================================
//=====================================================下面为业务逻辑===================================================

// 这个文档的目的在于根据当前显示的界面的大小动态调整各个元素显示的大小

//下面部分是针对section qual2Intro部分进行介绍的。   
var my_resize=function(){
	//document.width=document.body.clientWidth;
	//document.height=document.body.clientHeight;
	
}
//window.onresize=my_resize;

//针对section technicalLine 进行格式调整
var technicalLine=document.getElementById("technicalLine");
var techLineH2=document.getElementById("techLineH2");

var techMethodContainer=document.getElementById("techMethodContainer");
var techMethod=document.getElementById("techMethod");
var techMethodPic=document.getElementById("techMethodPic");
var techLinep=document.getElementById("techLinep");

function adjTechLine(){
	var techLineW=technicalLine.clientWidth;
	var techLineH=technicalLine.clientHeight-techLineH2.offsetHeight-techLinep.offsetHeight;
	
	techMethodContainer.style.width=techLineW+"px";
	techMethodContainer.style.height=techLineH+"px";
	techMethodContainer.style.textAlign="center";
	
	techMethod.style.width=643+"px";
	techMethod.style.height=techLineH+"px";
}



//下面定义调增section的函数
var headerIntro=document.getElementById("intro");

// section qual2Intro部分的东西
var qual2Intro=document.getElementById("qual2Intro");
var qualHisIntro=document.getElementById("qualHisIntro");


//section qual2Intro内部图片：qualHis
var qualHis=document.getElementById("qualHis");

var qualHisContainer=document.getElementById("qualHisContainer");

//首先针对保存图片的div，我们需要确定下它的宽和高
//动态的确定
window.onresize=function(){
	//窗口发生改变的时候，要调整各个元素的相对位置
	//alert(window.innerHeight);
	var height=window.innerHeight;
	
	qualHis.style.height=200;
	qualHis.style.width=200;
};

var qualHisPic=document.getElementById("qualHisPic");

//下面代码完全可以已进入页面就完全自动执行
/*qualHis.onfocus=function(){
	//包含图片div的 id是qualHis
	qualHis.align="middle";
	//最好不要通过计算设定 图像 长和宽，同时，这里需要用一些别的动画技术，否则的话，不好弄。
	var qualHisPic=document.getElementById("qualHisPic");
	var qualHisIntro=document.getElementById("qualHisIntro");
	qualHisIntro.style.marginBottom="10px"
}*/

//实测下面方法是没有问题的，下面开始研究如何撤销图片
(function(){
	qualHis.style.textAlign="center";
})();


//qualHis.onclick

var qualHisClick=function(e){
	//e.stopPropagation();
	if(f==null){
		f=new Fade("qualHis",{
			Show: true,
			Opacity:{
				run: true	
			},	
			Height:{
				run:true
			},
			Width:{
				run:true,
				pos: .5
			},
			Top:{
				run:true,
				end:70
			}
		});
	}
	f.Mode="both";
	f.Start();
};
//qualHis.addEventListener("click",qualHisClick,false);

var f;
var qualHisPicNum=1;       //用于表示这是qual历史发展历程中的第几张图片。   
qualHisContainer.onclick=function(e){
	//alert("进入这个函数了");
	if(f==null){
		f=new Fade("qualHis",{
			Show: true,
			Opacity:{
				run: true	
			},	
			Height:{
				run:true
			},
			Width:{
				run:true,
				pos: .5
			},
			Top:{
				run:true,
				end:70
			}
		});
	}
	if(f.Show){
		e = Event(e);
		e.stopPropagation();
		var o = getEle("qualHis"), x = o.offsetLeft, y = o.offsetTop;
		while (o.offsetParent) {
			o = o.offsetParent;
			x += o.offsetLeft;
			y += o.offsetTop;
		}
		f.Width.pos = (e.pageX - x) / getEle("qualHis").offsetWidth;
		f.Height.pos = (e.pageY - y) / getEle("qualHis").offsetHeight;
	}
	if(f.Show){
		qualHisPicNum+=1;
	}
	if(qualHisPicNum>4){
		qualHisPicNum=1;
	}
	switch(qualHisPicNum)
	{
		case 1:qualHisPic.src="img/Qual2Intro/Qual1示意图.jpg";
			qualHisIntro.textContent="Qual I示意图";
			break;
		case 2:qualHisPic.src="img/Qual2Intro/Qual2示意图.jpg";
			qualHisIntro.textContent="Qual II示意图";
			break;
		case 3:qualHisPic.src="img/Qual2Intro/Qual2e示意图.jpg";
			qualHisIntro.textContent="Qual 2E示意图";
			break;
		case 4:qualHisPic.src="img/Qual2Intro/Qual2k示意图.jpg";
			qualHisIntro.textContent="Qual 2K示意图";
			break;
	}
	f.Start();
	var p=document.getElementById("qualHisIntro");
	var h2=document.getElementById("qual2IntroH2");
	qualHisContainer.style.height=(qual2Intro.offsetHeight-p.offsetHeight-h2.offsetHeight-50) + "px";
}

function adjQualHeight(){
	var p=document.getElementById("qualHisIntro");
	var h2=document.getElementById("qual2IntroH2");
	qualHisContainer.style.height=(qual2Intro.offsetHeight-p.offsetHeight-h2.offsetHeight-50)+"px";
	qualHis.height=qual2Intro.offsetHeight-p.offsetHeight-h2.offsetHeight-50;
	qualHisPic.height=qual2Intro.offsetHeight-p.offsetHeight-h2.offsetHeight-50;
}


//这对技术路线页面进行设置


//针对 section qual2KwIntro进行介绍
var qual2KIntroContainer=document.getElementById("qual2KIntroContainer");
var qual2KIntro=document.getElementById("qual2KIntro");
var qual2KIntroPic=document.getElementById("qual2KIntroPic");
var qual2Kp=document.getElementById("qual2Kp");

(function(){
	qual2KIntro.style.textAlign="center";
	qual2KIntroContainer.style.height="387px";
})();

var f1;    //针对Qual2Kw介绍部分设置的切换动画
var qual2KwIntroNum=1;
qual2KIntroContainer.onclick=function(e){
	
	if(f1==null){
		f1=new Fade("qual2KIntro",{
			Show: true,
			Opacity:{
				run: true	
			},	
			Height:{
				run:true
			},
			Width:{
				run:true,
				pos: .5
			},
			Top:{
				run:true,
				end:70
			}
		});
	}
	if(f1.Show){
		e = Event(e);
		e.stopPropagation();
		var o = getEle("qual2KIntro"), x = o.offsetLeft, y = o.offsetTop;
		while (o.offsetParent) {
			o = o.offsetParent;
			x += o.offsetLeft;
			y += o.offsetTop;
		}
		f1.Width.pos = (e.pageX - x) / getEle("qual2KIntro").offsetWidth;
		f1.Height.pos = (e.pageY - y) / getEle("qual2KIntro").offsetHeight;
	}
	if(f1.Show){
		qual2KwIntroNum+=1;
	}
	if(qual2KwIntroNum>4){
		qual2KwIntroNum=1;
	}
	switch(qual2KwIntroNum)
	{
		case 1:qual2KIntroPic.src="img/qual2KwIntro/模型基本结构示意图.bmp";
			qual2Kp.textContent="qual2Kw模型基本结构";
			break;
		case 2:qual2KIntroPic.src="img/qual2KwIntro/热量平衡.jpg";
			qual2Kp.textContent="qual2Kw模型热量平衡";
			break;
		case 3:qual2KIntroPic.src="img/qual2KwIntro/物质传输示意图.jpg";
			qual2Kp.textContent="qual2Kw物质传输示意图";
			break;
		case 4:qual2KIntroPic.src="img/qual2KwIntro/qual2Kw各种化学反应图.jpg";
			qual2Kp.textContent="qual2Kw部分化学反应简图";
			break;
	}
	f1.Start();
	
}


function adjQual2KwHeight(){
	qual2Kp=document.getElementById("qual2Kp");
	var h2=document.getElementById("qual2KwIntroH2");
	qual2KIntroContainer.style.height=(qual2KwIntro.offsetHeight-qual2Kp.offsetHeight-h2.offsetHeight-50)+"px";
	qual2KIntro.height=qual2KwIntro.offsetHeight-qual2Kp.offsetHeight-h2.offsetHeight-50;
	qual2KIntroPic.height=qual2KwIntro.offsetHeight-qual2Kp.offsetHeight-h2.offsetHeight-50;
}

//针对 section 为ReNuMaIntro 的部分进行介绍

var ReNuMaH2=document.getElementById("ReNuMaH2");
var ReNuMaIntroContainer=document.getElementById("ReNuMaIntroContainer");
var ReNuMaIntro=document.getElementById("ReNuMaIntro");
var ReNuMaIntroPic=document.getElementById("ReNuMaIntroPic");
var ReNuMap=document.getElementById("ReNuMap");

function adjReNuMaHeight(){
	ReNuMaIntroContainer.style.height=(ReNuMaIntro.offsetHeight-ReNuMap.offsetHeight-ReNuMaH2.offsetHeight-50)+"px";
	ReNuMaIntro.height=ReNuMaIntro.offsetHeight-ReNuMap.offsetHeight-ReNuMaH2.offsetHeight-50;
	ReNuMaIntroPic.height=ReNuMaIntro.offsetHeight-ReNuMap.offsetHeight-ReNuMaH2.offsetHeight-50;
}

//针对 mainControlIntro 进行介绍
var MCIntroH2=document.getElementById("MCIntroH2");
var MCIntroContainer=document.getElementById("MCIntroContainer");
var mainControlIntro=document.getElementById("mainControlIntro");
var MCIntro=document.getElementById("MCIntro");
var MCIntroPic=document.getElementById("MCIntroPic");
var MCIntrop=document.getElementById("MCIntrop");

function adjMCIntroHeight(){
	MCIntroContainer.style.height=(mainControlIntro.offsetHeight-MCIntrop.offsetHeight-MCIntroH2.offsetHeight-50)+"px";
	MCIntro.height=mainControlIntro.offsetHeight-MCIntrop.offsetHeight-MCIntroH2.offsetHeight-50;
	MCIntroPic.height=mainControlIntro.offsetHeight-MCIntrop.offsetHeight-MCIntroH2.offsetHeight-50;
}

//设置图片的动态切换
var f2;    //针对MC介绍部分设置的切换动画
var MCIntroNum=1;
MCIntroContainer.onclick=function(e){
	
	if(f2==null){
		f2=new Fade("MCIntro",{
			Show: true,
			Opacity:{
				run: true	
			},	
			Height:{
				run:true
			},
			Width:{
				run:true,
				pos: .5
			},
			Top:{
				run:true,
				end:70
			}
		});
	}
	if(f2.Show){
		e = Event(e);
		//e.stopPropagation();
		var o = getEle("MCIntro"), x = o.offsetLeft, y = o.offsetTop;
		while (o.offsetParent) {
			o = o.offsetParent;
			x += o.offsetLeft;
			y += o.offsetTop;
		}
		f2.Width.pos = (e.pageX - x) / getEle("MCIntro").offsetWidth;
		f2.Height.pos = (e.pageY - y) / getEle("MCIntro").offsetHeight;
	}
	if(f2.Show){
		MCIntroNum+=1;
	}
	if(MCIntroNum>4){
		MCIntroNum=1;
	}
	switch(MCIntroNum)
	{
		case 1:MCIntroPic.src="img/MainWorkbook/主控工作表.JPG";
			MCIntrop.textContent="主控工作表";
			break;
		case 2:MCIntroPic.src="img/MainWorkbook/面源参数.JPG";
			MCIntrop.textContent="面源参数";
			break;
		case 3:
			 MCIntroContainer.removeChild(MCIntro);
			 var mainControlIntro=document.getElementById("mainControlIntro");
			 mainControlIntro.removeChild(MCIntrop);
			 
			 var ul=document.createElement("ul");
			 var li;
			 
			 var lineHeight=40;
			 
			 li=document.createElement("li");
			 li.class="delayed";
			 li.innerHTML="气象数据";
			 li.style.fontSize=lineHeight+"px";
			 li.style.marginLeft="100px";
			 ul.appendChild(li);
			 
			 li=document.createElement("li");
			 li.class="delayed";
			 li.innerHTML="源头水数据";
			 li.style.fontSize=lineHeight+"px";
			 li.style.marginLeft="100px";
			 ul.appendChild(li);
			 
			 li=document.createElement("li");
			 li.class="delayed";
			 li.innerHTML="河段物理参数";
			 li.style.fontSize=lineHeight+"px";
			 li.style.marginLeft="100px";
			 ul.appendChild(li);
			 
			 li=document.createElement("li");
			 li.class="delayed";
			 li.innerHTML="点源数据";
			 li.style.fontSize=lineHeight+"px";
			 li.style.marginLeft="100px";
			 ul.appendChild(li);
			 
			 MCIntroContainer.appendChild(ul);
			break;
	}
	f2.Start();
	
}

//针对section simDRes 进行格式调整
var simDRes=document.getElementById("simDRes");
var simDResH2=document.getElementById("simDResH2");
var simDResContainer=document.getElementById("simDResContainer");
var simDResU1=document.getElementById("simDResU1");
var simDResU2=document.getElementById("simDResU2");

var simDResD1=document.getElementById("simDResD1");
var simDResD2=document.getElementById("simDResD2");

var simDResp=document.getElementById("simDResp");

//subPics
var simDResU1Pic=document.getElementById("simDResU1Pic");
var simDResU2Pic=document.getElementById("simDResU2Pic");
var simDResD1Pic=document.getElementById("simDResD1Pic");
var simDResD2Pic=document.getElementById("simDResD2Pic");

//参数num表示一共要分成几个格子去展示，现在只定义2和4两种情况
function adjSimDRes(num){
	
	var picAreaW,picAreaH;
	var subPicW,subPicH;
	picAreaW=simDRes.clientWidth;
	picAreaH=simDRes.clientHeight-simDResH2.offsetHeight-simDResp.offsetHeight;
	
	subPicW=picAreaW/2 -10;
	
	subPicH=picAreaH/2-10;
	
	
	simDResContainer.style.height=picAreaH+"px";
	simDResContainer.style.width=picAreaW+"px";
	
	simDResU1.style.border="1px solid black";
	simDResU1.style.width=subPicW+ "px";
	simDResU1.style.height=subPicH + "px";
	simDResU1.style.cssFloat="left";
	simDResU1.style.textAlign="center";
	
	simDResU2.style.border="1px solid black";
	simDResU2.style.width=subPicW + "px";
	simDResU2.style.height=subPicH + "px";
	simDResU2.style.cssFloat="left";
	simDResU2.style.textAlign="center";
	
	if(num==4){
		simDResD1.style.visibility="visible";
		simDResD1.style.border="1px solid black";
		simDResD1.style.width=subPicW + "px";
		simDResD1.style.height=subPicH+"px";
		simDResD1.style.cssFloat="left";
		simDResD1.style.textAlign="center";
	
		simDResD2.style.visibility="visible";
		simDResD2.style.border="1px solid black";
		simDResD2.style.width=subPicW+ "px";
		simDResD2.style.height=subPicH +"px";
		simDResD2.style.cssFloat="left";
		simDResD2.style.textAlign="center";
	}else{
		simDResD1.style.visibility="hidden";
		simDResD2.style.visibility="hidden";
		simDResD1.style.height=0;
		simDResD2.style.height=0;
	}
	
	
	
	if((subPicW/subPicH)>(1406/955)){
		//Height长
		simDResU1Pic.style.height=subPicH+"px";
		simDResU2Pic.style.height=subPicH+"px";
		if(num==4){
			simDResD1Pic.style.visibility="visible";
			simDResD2Pic.style.visibility="visible";
			simDResD1Pic.style.height=subPicH+"px";
			simDResD2Pic.style.height=subPicH+"px";
			simDResD1Pic.style.width=subPicH*(1406/955)+"px";
			simDResD2Pic.style.width=subPicH*(1406/955)+"px";
		}else{
			simDResD1Pic.style.visibility="hidden";
			simDResD2Pic.style.visibility="hidden";
			simDResD1Pic.style.width=subPicH*(1406/955)+"px";
			simDResD2Pic.style.width=subPicH*(1406/955)+"px";
		}
	}else{
		//Width长
		simDResU1Pic.style.width=subPicW+"px";
		simDResU2Pic.style.width=subPicW+"px";
		if(num==4){
			simDResD1Pic.style.visibility="visible";
			simDResD2Pic.style.visibility="visible";
			simDResD1Pic.style.width=subPicW+"px";
			simDResD2Pic.style.width=subPicW+"px";
			simDResD1Pic.style.height=subPicW*(955/1406)+"px";
			simDResD2Pic.style.height=subPicW*(955/1406)+"px";
		}else{
			simDResD1Pic.style.visibility="hidden";
			simDResD2Pic.style.visibility="hidden";
			simDResD1Pic.style.height=subPicW*(955/1406)+"px";
			simDResD2Pic.style.height=subPicW*(955/1406)+"px";
		}
	}
}

//给四副图片添加动画
var subf1,subf2,subf3,subf4;
var simDResNum=1;
simDResContainer.onclick=function(e){
	if(subf1==null){
		subf1=new Fade("simDResU1",{
			Show: true,
			Opacity:{
				run: true	
			},	
			Height:{
				run:true
			},
			Width:{
				run:true,
				pos: .5
			},
			Top:{
				run:true,
				end:70
			}
		});
	}
	
	if(subf2==null){
		subf2=new Fade("simDResU2",{
			Show: true,
			Opacity:{
				run: true	
			},	
			Height:{
				run:true
			},
			Width:{
				run:true,
				pos: .5
			},
			Top:{
				run:true,
				end:70
			}
		});
	}
	
	if(subf3==null){
		subf3=new Fade("simDResD1",{
			Show: true,
			Opacity:{
				run: true	
			},	
			Height:{
				run:true
			},
			Width:{
				run:true,
				pos: .5
			},
			Top:{
				run:true,
				end:70
			}
		});
	}
	
	if(subf4==null){
		subf4=new Fade("simDResD2",{
			Show: true,
			Opacity:{
				run: true	
			},	
			Height:{
				run:true
			},
			Width:{
				run:true,
				pos: .5
			},
			Top:{
				run:true,
				end:70
			}
		});
	}
	
	if(subf1.Show){
		e = Event(e);
		//e.stopPropagation();
		var o = getEle("simDResU1"), x = o.offsetLeft, y = o.offsetTop;
		while (o.offsetParent) {
			o = o.offsetParent;
			x += o.offsetLeft;
			y += o.offsetTop;
		}
		subf1.Width.pos = (e.pageX - x) / getEle("simDResU1").offsetWidth;
		subf1.Height.pos = (e.pageY - y) / getEle("simDResU1").offsetHeight;
	}
	
	if(subf2.Show){
		e = Event(e);
		//e.stopPropagation();
		var o = getEle("simDResU2"), x = o.offsetLeft, y = o.offsetTop;
		while (o.offsetParent) {
			o = o.offsetParent;
			x += o.offsetLeft;
			y += o.offsetTop;
		}
		subf2.Width.pos = (e.pageX - x) / getEle("simDResU2").offsetWidth;
		subf2.Height.pos = (e.pageY - y) / getEle("simDResU2").offsetHeight;
	}
	
	if(subf3.Show){
		e = Event(e);
		//e.stopPropagation();
		var o = getEle("simDResD1"), x = o.offsetLeft, y = o.offsetTop;
		while (o.offsetParent) {
			o = o.offsetParent;
			x += o.offsetLeft;
			y += o.offsetTop;
		}
		subf3.Width.pos = (e.pageX - x) / getEle("simDResD1").offsetWidth;
		subf3.Height.pos = (e.pageY - y) / getEle("simDResD1").offsetHeight;
	}
	
	if(subf4.Show){
		e = Event(e);
		//e.stopPropagation();
		var o = getEle("simDResD2"), x = o.offsetLeft, y = o.offsetTop;
		while (o.offsetParent) {
			o = o.offsetParent;
			x += o.offsetLeft;
			y += o.offsetTop;
		}
		subf4.Width.pos = (e.pageX - x) / getEle("simDResD2").offsetWidth;
		subf4.Height.pos = (e.pageY - y) / getEle("simDResD2").offsetHeight;
	}
	
	if(subf1.Show && subf2.Show && subf3.Show && subf4.Show){
		simDResNum+=1;
	}
	
	if(simDResNum>6){
		simDResNum=1;
	}
	switch(simDResNum){
		case 1:
			adjSimDRes(4);
			simDResU1Pic.src="img/simResult/simDRes/Clib/溶解氧Clib.png";
			simDResU2Pic.src="img/simResult/simDRes/Clib/温度Clib.png";
			simDResD1Pic.src="img/simResult/simDRes/Clib/总氮Clib.png";
			simDResD2Pic.src="img/simResult/simDRes/Clib/总磷Clib.png";
			simDResp.textContent="校准期模拟结果";
			break;
		case 2:
			simDResU1Pic.src="img/simResult/simDRes/Clib/硝态氮Clib.png";
			simDResU2Pic.src="img/simResult/simDRes/Clib/有机氮Clib.png";
			simDResD1Pic.src="img/simResult/simDRes/Clib/氨氮Clib.png";
			simDResD2Pic.src="img/simResult/simDRes/Clib/电导率Clib.png";
			break;
		case 3:
			adjSimDRes(2);
			simDResU1Pic.src="img/simResult/simDRes/Clib/CBODfClib.png";
			simDResU2Pic.src="img/simResult/simDRes/Clib/pHClib.png";
			
			break;
		case 4:
			adjSimDRes(4);
			simDResU1Pic.src="img/simResult/simDRes/Conf/溶解氧Conf.png";
			simDResU2Pic.src="img/simResult/simDRes/Conf/温度Conf.png";
			simDResD1Pic.src="img/simResult/simDRes/Conf/总氮Conf.png";
			simDResD2Pic.src="img/simResult/simDRes/Conf/总磷Conf.png";
			simDResp.textContent="验证期模拟结果";
			break;
		case 5:
			simDResU1Pic.src="img/simResult/simDRes/Conf/硝态氮Conf.png";
			simDResU2Pic.src="img/simResult/simDRes/Conf/有机氮Conf.png";
			simDResD1Pic.src="img/simResult/simDRes/Conf/氨氮Conf.png";
			simDResD2Pic.src="img/simResult/simDRes/Conf/电导率Conf.png";
			break;
		case 6:
			adjSimDRes(2);
			simDResU1Pic.src="img/simResult/simDRes/Conf/CBODfConf.png";
			simDResU2Pic.src="img/simResult/simDRes/Conf/pHConf.png";
			break;
	}
	
	
	subf1.Start();
	subf2.Start();
	subf3.Start();
	subf4.Start();
}


//针对section simPRes进行格式设置
var simPRes=document.getElementById("simPRes");
var simPResH2=document.getElementById("simPResH2");
var simPResContainer=document.getElementById("simPResContainer");
var simPResDiv=document.getElementById("simPResDiv");
var simPResPic=document.getElementById("simPResPic");
var simPResp=document.getElementById("simPResp");

function adjSimPRes(){
	var picAreaW=simPRes.clientWidth;
	var picAreaH=simPRes.clientHeight-simPResH2.offsetHeight-simPResp.offsetHeight;
	
	simPResDiv.style.textAlign="center";
	
	if((picAreaW/picAreaH) > (910/580)){
		simPResPic.style.height=picAreaH + "px";
	}else{
		simPResPic.style.width=picAreaW+"px";
	}
}


//整个窗口全局改变的时候
window.onkeydown=function(evt){
	var loc=window.location;
	
	if(loc["href"].indexOf("technicalLine",0)>0){
		adjTechLine();
	}
	
	if(loc["href"].indexOf("qual2Intro",0)>0){
		adjQualHeight();
	}
	
	if(loc["href"].indexOf("qual2KwIntro",0)>0){
		adjQual2KwHeight();
	}
	
	if(loc["href"].indexOf("ReNuMaIntro",0)>0){
		adjReNuMaHeight();
	}
	
	if(loc["href"].indexOf("algorithmIntro",0)>0){
		MCIntroNum=1;   //置1，表示从第一张图片开始演示
	}
	
	if(loc["href"].indexOf("mainControlIntro",0)>0){
		adjMCIntroHeight();
	}
	
	if(loc["href"].indexOf("simDRes",0)>0){
		adjSimDRes(4);
	}
	
	if(loc["href"].indexOf("simPRes",0)>0){
		adjSimPRes();
	}
	
}