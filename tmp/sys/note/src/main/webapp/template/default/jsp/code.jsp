<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>源码下载 - 我的欢乐生活 - ${websiteName}</title>

<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
<style>
.codepage{width:970px;margin:auto;}
.pro_introduce{color:#727272;font-family:"microsoft yahei";}
.pro_introduce ul{width:100%;height:41px;line-height:41px;background-color: #EAEAEA;background-image: linear-gradient(#FEFEFE, #EDEDED);border: 1px solid #E3E3E3;clear: both;font-family: Microsoft Yahei;font-size: 16px;margin: 10px 0 20px;}
.pro_introduce ul li{width:183px;float:left;border-right:1px solid #EBEBEB;text-align: center;text-shadow: 1px 1px 0 #FFFFFF;}
.pro_introduce h1 a{height:41px;line-height:41px;font-size:18px;color:#234F7A;font-weight:700;}

body, ul {margin:0;padding:0}
li {list-style:none}
img {border:none;display:block}
.slidefather{position: relative;top:130px;}
.slide-wp {
width: 970px;
height: 570px;
overflow: hidden;
position: absolute;
left: 0%;
top: 50%;
margin-top: -150px;
}
.nav-wp {
position: absolute;
background: #ccc;
top: 50%;
margin-top: 370px;
left: 50%;
margin-left: -100px;
border-radius: 4px;
-moz-border-radius: 4px;
-webkit-border-radius: 4px;
padding: 0 20px 6px 10px;
_padding: 0 20px 2px 10px;
}
.nav li {
float: left;
margin-left: 10px;
font-size: 20px;
font-weight: bold;
font-family: tahoma;
color: #22739e;
cursor: pointer;
height: 22px;
}
.nav li.cur{color: #ff7a00}
.next {
position:absolute;
top: 0;
left: 160px;
padding: 4px 8px;
color: #ff7a00;
background: #ccc;
height: 20px;
border-radius: 4px;
-moz-border-radius: 4px;
-webkit-border-radius: 4px;
cursor: pointer;
}
</style>
</head>

<body>
	<%@include file="global_topbar.jsp"%>
	<div class="codepage">
	<div class="pro_introduce">
	<h1><a href="<%=basePath%>template/default/jsp/code.jsp" target="_self">记账记事微博系统</a></h1>
	<p>以短博客和长微博为思想，开发的一个记事网站。以微博的形式记录生活。同时有手机APP，可以随时随地记录生活。后期会有记录打印成册的功能，让一个人真正的持久自己的生活。
网站使用java开发，没有使用太多框架，用servlet开发后台，用Hibernate做为持久层。现在源代码开源，有需要的网友可以直接下载。</p>
	<ul>
	<li><a class="code_index" title="网站源码下载" href="https://code.csdn.net/zhanlanmg/billing" target="_blank">网站源码下载</a></li>
	<li><a class="code_index" title="Android源码下载" href="https://code.csdn.net/zhanlanmg/billing_android" target="_blank">Android源码下载</a></li>
	</ul>
	</div>
	<div style="clear:both"></div>
<div class="slidefather">
<div id="slider" class="slide-wp">
<ul>
<li><a href="http://www.cnblogs.com/hongru/" target="_blank"><img src="${templatePath}/img/code_big_img1.jpg"  alt="" /></a></li>
<li><a href="http://www.cnblogs.com/hongru/" target="_blank"><img src="${templatePath}/img/code_big_img2.jpg"  alt="" /></a></li>
<li><a href="http://www.cnblogs.com/hongru/" target="_blank"><img src="${templatePath}/img/code_big_img3.jpg" usemap="#planetmap" alt="" /></a></li>
</ul>
</div>
<div class="nav-wp">
<ul id="nav" class="nav">
<li onclick="mySlider.pos(0)">●</li>
<li onclick="mySlider.pos(1)">●</li>
<li onclick="mySlider.pos(2)">●</li>
</ul>
<a class="next" onclick="mySlider.move()">next</a>
</div>
</div>
<map name="planetmap" id="planetmap">
  <area shape="rect" coords="592,342,866,411" href ="https://code.csdn.net/zhanlanmg/billing" alt="网站源码下载" target="_blank"/>
  <area shape="rect" coords="592,430,866,498" href ="https://code.csdn.net/zhanlanmg/billing_android" alt="Android源码下载" target="_blank"/>
</map>
	</div>
	
<script>
/**
 * 图片切换
 */
 var HR = {
		 $ : function(i) {return document.getElementById(i)},
		 $$ : function(c, p) {return p.getElementsByTagName(c)},
		 ce : function(i, t) {
		  var o = document.createElement(i);
		  t.appendChild(o);
		  return o;
		 }
		};
		HR.slider3D = function () {
		 var init = function (o) {
		  this.o = o;
		  var wp = HR.$(o.id), ul = HR.$$('ul', wp)[0], li = this.li = HR.$$('li', ul);
		  this.l = li.length;
		  this.w = wp.offsetWidth;
		  this.h = wp.offsetHeight;
		  this.at = o.auto? o.auto : 4;
		  var con = this.con = HR.ce('div', wp);
		  con.style.cssText = 'position:absolute;left:0;top:0;width:'+this.w+'px;height:'+this.h+'px';
		  ul.style['display'] = 'none';  
		  this.a1 = HR.ce('a', con);  
		  this.a1.style.cssText = 'position:absolute;left:0;top:0;overflow:hidden';  
		  this.a2 = HR.ce('a', con);
		  this.a2.style.cssText = 'position:absolute;top:0;right:0;overflow:hidden';
		  this.a1.innerHTML = this.a2.innerHTML = '<img alt="" />';
		  this.img = HR.$$('img', ul);
		  this.s = o.maskSize ? o.maskSize : 5;
		  
		  this.mask11 = HR.ce('span', this.a1);
		  this.mask12 = HR.ce('span', this.a1);
		  this.mask21 = HR.ce('span', this.a2);
		  this.mask22 = HR.ce('span', this.a2);
		  
		  this.pos(0);
		 }
		 init.prototype = {
		  pos : function (i) {
		   clearInterval(this.li[i].a); clearInterval(this.au); this.au = 0; this.cur = i;
		   var navli = HR.$$('li', HR.$(this.o.navId));
		   for (var j=0; j<navli.length; j++) {
		    navli[j].className = i == j ? 'cur' : '';
		   }
		   var img1 = HR.$$('img', this.a1)[0], img2 = HR.$$('img', this.a2)[0], _this = this;
		   img1.src = i==0 ? this.img[this.l-1].src : this.img[i-1].src;
		   img1.width = this.w;
		   img2.src = this.img[i].src;
		   img2.src = this.img[i].src;
		   img2.setAttribute("usemap","#planetmap");
		   img2.width = 0;
		   img1.height = img2.height = this.h;
		   this.mask11.style.cssText = 'position:absolute;left:0;top:0;font-size:0;overflow:hidden;width:0;height:0;border-color:black transparent transparent black;border-style:solid dashed dashed solid;border-width:0 '+this.w/2+'px';
		   this.mask12.style.cssText = 'position:absolute;left:0;bottom:0;font-size:0;overflow:hidden;width:0;height:0;border-color:transparent transparent black black;border-style:dashed dashed solid solid;border-width:0 '+this.w/2+'px';
		   this.mask21.style.cssText = 'position:absolute;right:0;top:0;font-size:0;overflow:hidden;width:0;height:0;border-color:black black transparent transparent;border-style:solid solid dashed dashed;border-width:0px';
		   this.mask22.style.cssText = 'position:absolute;right:0;bottom:0;font-size:0;overflow:hidden;width:0;height:0;border-color:transparent black black transparent;border-style:dashed solid solid dashed;border-width:0px';
		   this.li[i].a = setInterval(function(){_this.anim(i)}, 20);
		  },
		  anim : function (i) {
		   var w1 = HR.$$('img', this.a1)[0].width, w2  = HR.$$('img', this.a2)[0].width;
		   if (w2 == this.w) {
		    clearInterval(this.li[i].a);
		    HR.$$('img', this.a1)[0].width = 0;
		    HR.$$('img', this.a2)[0].width = this.w;
		    this.mask11.style.borderLeftWidth = this.mask11.style.borderRightWidth = this.mask12.style.borderLeftWidth = this.mask12.style.borderRightWidth = '0px';
		    this.mask11.style.borderTopWidth = this.mask11.style.borderBottomWidth = this.mask12.style.borderTopWidth = this.mask12.style.borderBottomWidth = this.h/this.s + 'px';
		    this.mask21.style.borderLeftWidth = this.mask21.style.borderRightWidth = this.mask22.style.borderLeftWidth = this.mask22.style.borderRightWidth = this.w/2 + 'px';
		    this.mask21.style.borderTopWidth = this.mask21.style.borderBottomWidth = this.mask22.style.borderTopWidth = this.mask22.style.borderBottomWidth = '0px';
		   }else {
		    HR.$$('img', this.a1)[0].width -= Math.ceil((this.w-w2)*.13);
		    HR.$$('img', this.a2)[0].width += Math.ceil((this.w-w2)*.13);
		    this.mask11.style.borderLeftWidth = this.mask11.style.borderRightWidth = this.mask12.style.borderLeftWidth = this.mask12.style.borderRightWidth = HR.$$('img', this.a1)[0].width/2 + 'px';
		    this.mask11.style.borderTopWidth = this.mask11.style.borderBottomWidth = this.mask12.style.borderTopWidth = this.mask12.style.borderBottomWidth = HR.$$('img', this.a2)[0].width*this.h/(this.s*this.w) + 'px';
		    this.mask21.style.borderLeftWidth = this.mask21.style.borderRightWidth = this.mask22.style.borderLeftWidth = this.mask22.style.borderRightWidth = HR.$$('img', this.a2)[0].width/2 + 'px';
		    this.mask21.style.borderTopWidth = this.mask21.style.borderBottomWidth = this.mask22.style.borderTopWidth = this.mask22.style.borderBottomWidth = this.h/this.s - HR.$$('img', this.a2)[0].width*this.h/(this.s*this.w) + 'px';
		    if (!this.au) this.auto();
		   }
		  },
		  auto : function () {
		   var _this = this;
		   this.au = setInterval(function(){_this.move()}, this.at*1000);
		  },
		  move : function () {
		   var n = this.cur==this.l-1 ? 0 : this.cur+1;
		   this.pos(n);
		  }
		 }
		 return init;
		}();
		var mySlider = new HR.slider3D({
		 id: 'slider',
		 maskSize: 6,
		 navId: 'nav',
		 auto: 4
		})
</script>
</body>
</html>
