<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.*" errorPage=""%>

<div class="bill_add">
<p id="addbill" class="startnew">今天你花钱了吗？</p>
<p id="addmood" class="startnew">我只想记心情</p>
<form name="addform" method="post" action="Bills.yy">
<input type="hidden" name="a" value="add" />
<input type="hidden" name="theimage_form" value="" id="theimage_form" />
<div id="addbillinput" class="bill_add_h">
<p> 您于&nbsp;<span id='thetime'></span>&nbsp;因为
<input class="b_type" type="text" id="btype" name="btype" value="吃饭 购物等等" maxlength="50" readonly onClick="new ejectBtypeDiv().show(this)" />
<input type="hidden" id="btypename" name="btypename" value="27" />
<select class="b_io" id="bio" name="bio">
<option value="">&nbsp;</option>
<option value="1" selected>支出</option>
<option value="2">收入</option>
<option value="3">债务</option>
<option value="4">债权</option>
<option value="5">无金钱</option>
</select>
<input class="b_amount" type="text" id="bamount" name="bamount" value="金额" />
元，记录一下！ </p>
<p> <span title="你可以添加一次行程，并向行程中添加记账">添加到行程：</span>
<input class="inputcb" type="checkbox" id="addtravelbox" name="travel" value="travel" title="添加到行程" onChange="addtravel();" />
&#8195;
<input id="travelleader" name="travelleader" disabled="" readonly value="添加行程的名称" type="text" onClick="new ejectDiv().show(this)" />
<input id="dataInput" type="hidden" name="btid" />
</p>
</div>
<div id="addmoodinput" class="bill_add_h">
<div>
<textarea class="b_caption" type="text" id="bcaption" name="bcaption" >再说点什么……</textarea>
</div>
<input type="hidden" name="onlyaddmood" />
</div>
</form>
<div class="sendInsert">
<div class="menu_m"> <span class="menu_s_p" onClick="getSignTag('1');">签到</span>
<div class="menu_content">
<div class="menu_display" id="menu_display_1">
<div class="menu_display_t"> <span style="padding-left: 5px; float: left;">选择你要签到的话题</span> </div>
<div class="menu_display_con"> <span><a class="menu_htb_c2" style="cursor: pointer;" onclick="setSignTag('签到 心情')" href="javascript:void(0)">#签到 心情#</a></span> </div>
<sub class="menu_display_close" id="menu_display_close_1">×</sub> </div>
</div>
</div>
<div class="menu_m"> <span class="menu_s_p" onClick="getSignTag('2');">图片</span>
<div class="menu_content">
<div class="menu_display menu_image" id="menu_display_2">
<div class="menu_display_con">
<%@include file="./../uploadimage/uploadimage.jsp"%>
</div>
<sub class="menu_display_close" id="menu_display_close_2">×</sub> </div>
</div>
</div>
<div class="menu_m"> <span class="menu_s_p" onClick="getSignTag('3');">话题</span>
<div class="menu_content">
<div class="menu_display menu_topic" id="menu_display_3">
<div class="menu_display_t"> <span style="padding-left: 5px; float: left;">选择你要签到的话题</span> </div>
<div class="menu_display_con">
<ul id="tabnav2">
<a class="menu_htb_c2" style="cursor: pointer;" onclick="setSignTag('插入自定义话题')" href="javascript:void(0)">自定义话题</a> <a class="menu_htb_c2" style="cursor: pointer;" onClick="" href="javascript:void(0)">我关注的</a> <a class="menu_htb_c2" style="cursor: pointer;" onClick="" href="javascript:void(0)">推荐话题</a>
</ul>
<ul class="tagB">
<div id="add_ajax_favorite_tags"></div>
<div class="add_ajax_favorite_buttom"> <span>请添加想关注的话题
<p style="margin: 5px 0px;">
<input name="tag_names" class="topic_fav" id="tag_names" style="width: 180px;" type="text">
<input name="button" class="btnText" onclick="favoriteTag('tag_names','input_add')" type="button" value="保 存">
</p>
</span> </div>
</ul>
</div>
<sub class="menu_display_close" id="menu_display_close_3">×</sub> </div>
</div>
</div>
</div>
<div class="bill_add_s">
<input id="addinput" type="button" value="保存" onclick="javascript:if(checknull()&&addsubmit()){document.addform.submit();}" />
<span id="checknullprompt" style="color:#d64f44"></span> </div>
</div>
<div class="menu_m">
<div> <img id="theimage" src="" style="display:none;max-height:240px;max-width:240px" /> </div>
</div>
<form name="deleteform" method="post" action="Bills.yy">
<input type="hidden" name="a" value="delete" />
<input type="hidden" id="b" name="b" value="" />
</form>
<div id="modify_form" class="modify_form">
<div id="modify_add_block" class="bill_add_block">
<form name="modifyform" method="post" action="Bills.yy">
<div class="bill_add bill_modify">
<p class="startnew">修改记账：</p>
<div class="bill_add_h">
<p> 您于&nbsp;<span id='thetime'>
<input type="text" name="datetime_m" id="datetime_m" readonly value=""/>
</span>&nbsp;因为
<input class="b_type" type="text" id="btype_m" name="btype_m" value="吃饭 购物等等" readonly/>
<select class="b_io" id="bio_m" name="bio_m">
<option value="">&nbsp;</option>
<option value="1">支出</option>
<option value="2">收入</option>
<option value="3">债务</option>
<option value="4">债权</option>
<option value="5">无金钱</option>
</select>
<input class="b_amount" type="text" id="bamount_m" name="bamount_m" value="金额"/>
元，记录一下！</p>
<div>
<textarea class="b_caption" type="text" id="bcaption_m" name="bcaption_m">
再说点什么……
</textarea>
</div>
</div>
<div class="bill_add_s">
<input type="hidden" name="a" value="modify"/>
<input type="button" value="取消" onClick="javascript:document.getElementById('modify_form').className='modify_form';	document.getElementById('modify_mask').className='modify_mask';"/>
<input type="button" value="保存" onClick="javascript:if(checknull_m()){document.modifyform.submit();}"/>
<span id="checknullprompt_m"></span> </div>
</div>
<input type="hidden" id="b_modify" name="b" value=""/>
</form>
</div>
</div>
<div id="modify_mask" class="modify_mask"></div>
<script type="text/javascript" src="${templatePath}/js/nowdatetime.js"></script>
<script type="text/javascript" src="${templatePath}/js/modify.js"></script>
<script type="text/javascript">
	function addtravel() {
		if (!$$("addtravelbox").checked) {
			$$("travelleader").setAttribute("disabled", "disabled");
		} else if ($$("addtravelbox").checked) {
			$$("travelleader").removeAttribute("disabled");
		}
	}
	function addsubmit() {
		if ($$("addtravelbox").checked && $$("dataInput").value == "") {
			alert("请选择一次行程！");
			return false;
		} else {
			return true;
		}
	}
</script>

<script type="text/javascript">
function addlistennerMain(obj, eve, fun) {
	if (document.addEventListener) {// 如果是Firefox
		obj.addEventListener(eve, fun, true);
	} else {
		obj.attachEvent("on"+eve, fun);
	}
}
addlistennerMain($$("bio"),"change",bioChange);
function bioChange(){
	/*无金钱时，自动为0并不可编辑*/
	if($$("bio").value==5){$$("bamount").value=0;$$("bamount").setAttribute("type","hidden");}
	else{$$("bamount").setAttribute("type","text");}
}
addlistennerMain($$("bio_m"),"change",bio_mChange);
function bio_mChange(){
	/*无金钱时，自动为0并不可编辑*/
	if($$("bio_m").value==5){$$("bamount_m").value=0;$$("bamount_m").setAttribute("type","hidden");}
	else{$$("bamount_m").setAttribute("type","text");}
}

function checkmoodnull(){
	if($$("bcaption").value=="再说点什么……")
		alert("写心情的时候必需输入一些内容。");
	else
		return true;
}

(function justaddmood(){//仅仅添加心情，隐藏不必要的表单内容
	$$("addbill").onclick=function(){$$("addbillinput").style.display="block";
	$$("addmoodinput").getElementsByTagName("input")[0].value="";
	$$("addinput").onclick=function(){javascript:if(checknull()&&addsubmit()){document.addform.submit();}};
};
	$$("addmood").onclick=function(){
		$$("addbillinput").style.display="none";
		$$("addmoodinput").getElementsByTagName("input")[0].value="onlyaddmood";
		$$("addmoodinput").getElementsByTagName("textarea")[0].focus();
		$$("addinput").onclick=function(){checkmoodnull()&&document.addform.submit();};
	};
})();
</script>