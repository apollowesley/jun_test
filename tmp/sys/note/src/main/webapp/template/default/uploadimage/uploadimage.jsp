<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<h2 class="myAvatarText">选择你要上传的图片</h2>
<form method="post" name="form2" enctype="MULTIPART/FORM-DATA" action="${templatePath}/uploadimage/AnswerFile.jsp" target="upAnswerFile">
<A class="btn_addPic"><SPAN><EM>+</EM>选择图片</SPAN> <INPUT class="filePrew" title="支持jpg、jpeg、gif、png格式，文件小于2M" tabIndex=3 type="file" size=3  type="file" name="file" id="file" onchange="check();"></A>
</form>
<script type="text/javascript">
	function check() {
		if (document.form2.file.value == "") {
			alert("请选择一张图片！！");
			return false;
		}
		document.form2.submit();
		return true;
	}
	function callbackt(filename,filenamesmall){
		document.getElementById("theimage").src = "image/images/"+filenamesmall;
		document.getElementById("theimage").style.display="block";
		document.getElementById("menu_display_2").style.display="none";
		document.getElementById("theimage_form").value="image/images/"+filename;
	}
</script>
<iframe name="upAnswerFile" id="upAnswerFile" style= "display:none"></iframe>