function Ajax(url, data) {
	this.url = url;
	this.data = data;
	this.browser = (function() {
		if (navigator.userAgent.indexOf("MSIE") > 0) {
			return "MSIE";// IE浏览器
		} else {
			return "other";// 其他
		}
	})();
};
Ajax.prototype = {
	get : function() {
		var result;
		var xmlhttp;
		if (this.browser == 'MSIE') {
			try {
				xmlhttp = new ActiveXObject('microsoft.xmlhttp');
			} catch (e) {
				xmlhttp = new ActiveXObject('msxml2.xmlhttp');
			}
		} else {
			xmlhttp = new XMLHttpRequest();
		}
		;
		xmlhttp.onreadystatechange = function() {
			result = xmlhttp.responseText;// 闭包，不能采用this.属性
		};
		xmlhttp.open('GET', this.url + '?' + this.data, false);// true无法抓取数据，why?
		xmlhttp.send(null);
		return result;
	},
	post : function() {
		var result;
		var xmlhttp;
		if (this.browser == 'MSIE') {
			xmlhttp = new ActiveXObject('microsoft.xmlhttp');
		} else {
			xmlhttp = new XMLHttpRequest();
		}
		;
		xmlhttp.onreadystatechange = function() {
			result = xmlhttp.responseText;// 闭包，不能采用this.属性
		};
		xmlhttp.open('POST', this.url, false);// 需设为false,否则无法抓取responseText
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");// POST中，这句必须
		xmlhttp.send(this.data);
		return result;
	}
};
/*
 * document.getElementById("btn").onclick=function(){ var p=''; var a=new
 * Ajax("http://peaches-pc:8080/billing/Bills.yy",p);
 * document.getElementById("box").innerHTML=a.get(); };
 */
// /上面是封闭的ajax一般方法
function wblObj(json_obj, theparentNode) {
	/* json_obj,即需要显示的数据对象，theparentNode父标签，在谁之下显示 */
	/* wbl=weibolist */
	/* 这是一个从bills_list.jsp页面中 c:forEach 中抽象出来的一个对象，用于显示，并添加js事件 */
	var wbl = document.createElement("div");// 最外层class=""/class="split"
	var wbl_weibo = document.createElement("div");// class="weibo"
	this.wbl_weibo = wbl_weibo;
	var wbl_operateup = document.createElement("div");// class="operate"
	// 这个是上面的操作，下面还有一个
	var wbl_wb_l = document.createElement("div");// class="wb_l"
	var wbl_wb_l_sub1 = document.createElement("div");// sub即子标签，不写则说明没有class
	var wbl_wb_l_sub1_img = document.createElement("img");// class="author"
	var wbl_wb_r = document.createElement("div");// class="wb_r"
	var wbl_user_info = document.createElement("div");// class="user_info"
	var wbl_fl_p_u = document.createElement("span");// class="fl p_u"
	var wbl_fr_p_t = document.createElement("span");// class="fr p_t"
	var wbl_fr_p_t_sub1 = document.createElement("span");// sub子标签
	var wbl_wb_c_wp = document.createElement("div");// class="wb_c_wp"
	var wbl_wb_c = document.createElement("div");// class="wb_c"
	var wbl_wordColor = document.createElement("span");// class="wordColor"
	var wbl_wordColor2 = document.createElement("span");// class="wordColor",这个显示${list.bsign}${list.bamount}
	var wbl_wordColor_sub1 = document.createElement("a");// sub子标签
	var wbl_from = document.createElement("div");// class="from"
	var wbl_fl = document.createElement("span");// class="fl"
	var wbl_fr_num = document.createElement("span");// class="fr num"
	var wbl_div1 = document.createElement("div");// style="position:relative1;"
	var wbl_div1_sub1 = document.createElement("img");// class="locateimg_img"
	var wbl_operatebottom = document.createElement("div");// class="operate"
	// 这个是下面的操作，上面还有一个
	var wbl_wb_line = document.createElement("div");// class="wb_line"
	wbl.appendChild(wbl_weibo);
	wbl.appendChild(wbl_wb_line);
	wbl_weibo.appendChild(wbl_operateup);
	wbl_weibo.appendChild(wbl_wb_l);
	wbl_weibo.appendChild(wbl_wb_r);
	wbl_weibo.appendChild(wbl_operatebottom);
	wbl_wb_l.appendChild(wbl_wb_l_sub1);
	wbl_wb_l_sub1.appendChild(wbl_wb_l_sub1_img);
	wbl_wb_r.appendChild(wbl_user_info);
	wbl_wb_r.appendChild(wbl_wb_c_wp);
	wbl_user_info.appendChild(wbl_fl_p_u);
	wbl_user_info.appendChild(wbl_fr_p_t);
	wbl_fr_p_t.appendChild(wbl_fr_p_t_sub1);
	wbl_wb_c_wp.appendChild(wbl_wb_c);
	wbl_wb_c_wp.appendChild(wbl_from);
	wbl_wb_c_wp.appendChild(wbl_div1);
	wbl_wb_c.innerHTML = json_obj.bamount != '0.00'?'由于 ':'记录 ';// 必须在这个位置，因为用了innerHTML是文本模式，会导致方法失效
	wbl_wb_c.appendChild(wbl_wordColor);
	wbl_wb_c.appendChild(wbl_wordColor2);
	wbl_wordColor.appendChild(wbl_wordColor_sub1);
	wbl_from.appendChild(wbl_fl);
	wbl_from.appendChild(wbl_fr_num);
	// document.body.appendChild(wbl);//到此处对象结构完成
	theparentNode.appendChild(wbl);
	wbl.setAttribute("class", 'split');
	wbl_weibo.setAttribute("class", 'weibo');
	wbl_operateup.setAttribute("class", 'operate');
	wbl_wb_l.setAttribute("class", 'wb_l');
	wbl_wb_l_sub1_img.setAttribute("class", 'author');
	wbl_wb_r.setAttribute("class", 'wb_r');
	wbl_user_info.setAttribute("class", 'user_info');
	wbl_fl_p_u.setAttribute("class", 'fl p_u');
	wbl_fr_p_t.setAttribute("class", 'fr p_t');
	wbl_wb_c_wp.setAttribute("class", 'wb_c_wp');
	wbl_wb_c.setAttribute("class", 'wb_c');
	wbl_wordColor.setAttribute("class", 'wordColor');
	wbl_wordColor2.setAttribute("class", 'wordColor');
	wbl_from.setAttribute("class", 'from');
	wbl_fl.setAttribute("class", 'fl');
	wbl_fr_num.setAttribute("class", 'fr num');
	wbl_div1_sub1.setAttribute("class", 'locateimg_img');
	wbl_operatebottom.setAttribute("class", 'operate');
	wbl_wb_line.setAttribute("class", 'wb_line');
	wbl_div1.setAttribute("style", "position:relative1;");
	wbl_wordColor_sub1.setAttribute("style",
			'cursor:pointer;vertical-align:bottom;color:#1460dd;');
	wbl_wordColor_sub1.setAttribute("data", json_obj.bid);
	wbl_wordColor_sub1.onmouseover = function() {
		new displaydetail(this);
	};
	wbl_wordColor_sub1.onclick = function() {
		displaydetailatpage(this);
	};
	var a = json_obj.bio * 1;
	var b = ' ' + json_obj.bioname + ' ';
	var c = json_obj.bsign;
	c = (a * 1 == 5 || a * 1 == 0) ? c : c + json_obj.bamount;
	wbl_fl_p_u.innerHTML = json_obj.username + ' ';
	wbl_fr_p_t_sub1.innerHTML = json_obj.bdate.substring(0, 19);
	if (json_obj.bctype != 2) {// 如果不是转发，则显示关于记账的金钱那一行，否则不显示
		if (json_obj.bbetravelleader == 'true') {// 行程时，显示为链接
			wbl_wordColor_sub1.innerHTML = json_obj.btypename+' '+b;
		} else if (json_obj.btid != '0') {// 子行程时，显示为灰色
			wbl_wordColor.innerHTML = json_obj.btypename;
			wbl_wordColor.style.color = "#aaa";
			if (json_obj.bamount != '0.00')
				wbl_wordColor2.innerHTML = b + ' 人民币 ' + json_obj.bsign
						+ json_obj.bamount + '元';
		} else {// 普通模式=
			wbl_wordColor.innerHTML = json_obj.btypename;
			wbl_wordColor.style.color = "#333";
			if (json_obj.bamount != '0.00')
				wbl_wordColor2.innerHTML = b + ' 人民币 ' + json_obj.bsign
						+ json_obj.bamount + '元';
		}
	} else {
		wbl_wb_c.innerHTML = "";
	}
	wbl_fl.innerHTML = json_obj.bcaption;
	wbl_wb_l_sub1_img.src = templatePath + "/uploadavatar/getAvatar.jsp?au="
			+ json_obj.uid;
	if (json_obj.bidir != 'null')// 如果有图片，显示图片
	{
		wbl_div1.appendChild(wbl_div1_sub1);// 如果有图片，再加入图片
		wbl_div1_sub1.setAttribute("data", json_obj.bidir);
	}
	if (json_obj.bctype == 2) {// 如果是转发的，需要显示转发的内容
		var wbl_wb_f = document.createElement("div");// class="wb_c"
		wbl_wb_f
				.setAttribute(
						"style",
						"background-color:#f2f2f2;border:1px solid #d9d9d9;border-radius:3px;padding:5px;");
		var p = "a=bid&bid=" + json_obj.rootbid + "&date=" + new Date();
		var a = new Ajax("Bills.yy", p);
		var result = a.get();
		var result_json_obj = (new Function("return " + result))();// 转换为json对象
		wbl_wb_f.innerHTML = "<b>" + result_json_obj.type[0].username
				+ "</b><br/>" + result_json_obj.type[0].bcaption + "<br/>"
				+ result_json_obj.type[0].bdate.substring(0, 19);
		if (result_json_obj.type[0].bamount != '0.00') {
			wbl_wb_f.innerHTML += " 金额：" + result_json_obj.type[0].bsign
					+ result_json_obj.type[0].bamount;
		}
		wbl_wb_c_wp.appendChild(wbl_wb_f);

	}
	/* 上面的定义是本身的内容 */

	/* 这里是内容上部操作按钮 */
	var wbl_operateup_div = document.createElement("div");
	var wbl_operateup_div_i = document.createElement("i");
	var wbl_operateup_div_ul = document.createElement("ul");
	var wbl_operateup_div_ul_li = document.createElement("li");
	var wbl_operateup_div_ul_li_a = document.createElement("a");
	wbl_operateup.appendChild(wbl_operateup_div);
	wbl_operateup_div.appendChild(wbl_operateup_div_i);
	wbl_operateup_div.appendChild(wbl_operateup_div_ul);
	wbl_operateup_div_ul.appendChild(wbl_operateup_div_ul_li);
	wbl_operateup_div_ul_li.appendChild(wbl_operateup_div_ul_li_a);
	wbl_operateup_div.setAttribute("class", 'test');
	wbl_operateup_div_i.setAttribute("class", 'icon-dropdown');
	wbl_operateup_div_ul_li_a.setAttribute("href", 'javascript:void(0);');
	wbl_operateup_div_ul_li_a.onclick = function() {
		if (confirm('确认删除此条记录？？？\n\n此删除为永久删除，不可恢复！！！')) {
			javascript: document.getElementById('b').value = json_obj.bid;
			document.deleteform.submit();
		}
	};
	wbl_operateup_div_ul_li_a.innerHTML = "删除";

	/* 下面的定义是评论内容，下面的操作按钮 */
	var wbl_operatebottom_div = document.createElement("div");
	var wbl_operatebottom_div_i = document.createElement("i");
	var wbl_operatebottom_div_ul = document.createElement("ul");
	var wbl_operatebottom_div_ul_li = document.createElement("li");
	var wbl_operatebottom_div_ul_li_a1 = document.createElement("a");
	var wbl_operatebottom_div_ul_li_a2 = document.createElement("a");
	wbl_operatebottom_div_i.innerHTML = " | ";
	wbl_operatebottom.appendChild(wbl_operatebottom_div);
	wbl_operatebottom_div.appendChild(wbl_operatebottom_div_ul);
	wbl_operatebottom_div_ul.appendChild(wbl_operatebottom_div_ul_li);
	wbl_operatebottom_div_ul_li.appendChild(wbl_operatebottom_div_ul_li_a1);
	wbl_operatebottom_div_ul_li.appendChild(wbl_operatebottom_div_i);
	wbl_operatebottom_div_ul_li.appendChild(wbl_operatebottom_div_ul_li_a2);
	wbl_operatebottom_div.setAttribute("class", 'opbo');
	wbl_operatebottom_div_ul_li_a1.setAttribute("href", 'javascript:void(0);');
	wbl_operatebottom_div_ul_li_a1.setAttribute("subparameter", "frombid="
			+ json_obj.bid);// 评论时，需要用到的记录id
	wbl_operatebottom_div_ul_li_a1.setAttribute("getbybid", json_obj.bid);// 评论时，需要用到的记录id
	wbl_operatebottom_div_ul_li_a2.setAttribute("subparameter", "frombid="
			+ json_obj.bid);// 评论时，需要用到的记录id
	wbl_operatebottom_div_ul_li_a2.setAttribute("getbybid", json_obj.bid);// 评论时，需要用到的记录id
	wbl_operatebottom_div_ul_li_a1.onclick = function() {
		if (wbl_weibo.lastChild.getAttribute("class") != "operate") {
			wbl_weibo.removeChild(wbl_weibo.lastChild);
		} else {
			var wbl_r = document.createElement("div");// 最外层class="r_top"
			var wbl_rs = document.createElement("div");// 显示其他人的评论内容
			wbl_r.innerHTML = ' <div class="r_top"><span>评论本条</span></div> ';
			var wbl_r_d = document.createElement("div");// textarea的外层div
			var wbl_r_d_t = document.createElement("textarea");// textarea
			wbl_r_d_t.setAttribute("class", "inputtxt");
			wbl_r.appendChild(wbl_r_d);
			wbl_r_d.appendChild(wbl_r_d_t);
			var wbl_r_d_i = document.createElement("div");// textarea的同层div,添加表情和同步转发的功能
			wbl_r.appendChild(wbl_r_d_i);
			wbl_r_d_i.innerHTML = '<div class="insertFun"><div class="sendList insertFace">表情</div></div><div class="left"><ul><li><label><input name="forward" type="checkbox" onclick="if(this.checked){}"/>同时转发</label></li></ul></div>';
			// <p class="btn"><a class="btn_a"><span class="btn_30px
			// f14">评论</span></a></p>
			var wbl_r_p = document.createElement("p");// 评论提交按钮p
			var wbl_r_p_a = document.createElement("a");// 评论提交按钮a
			var wbl_r_p_a_span = document.createElement("span");// 评论提交按钮span
			wbl_r.appendChild(wbl_r_p);
			wbl_r_p.appendChild(wbl_r_p_a);
			wbl_r_p_a.appendChild(wbl_r_p_a_span);
			wbl_r.setAttribute("class", 'r_rp');
			wbl_r_p.setAttribute("class", 'btn');
			wbl_r_p_a.setAttribute("class", 'btn_a');
			wbl_r_p_a_span.setAttribute("class", 'btn_30px f14');
			wbl_r_p_a_span.innerHTML = '评论';
			var subparameter = this.getAttribute("subparameter");
			var getbybid = this.getAttribute("getbybid");
			wbl_r_p_a.onclick = function() {// 提交评论内容
				if (wbl_r_d_t.value == "") {
					alertbox("提示", "写点东西吧，评论内容不能为空");
					return false;
				}
				var p = 'bcaption=' + wbl_r_d_t.value + '&' + subparameter
						+ "&";

				// 由此节点开始获取<label><input name="forward"
				// type="checkbox"/>同时评论</label>中input的选中状态
				var andreply = this.parentNode.previousSibling.lastChild.firstChild.firstChild.firstChild.firstChild.checked;
				p += '&andforward=' + andreply;

				var a = new Ajax("Bills.yy?a=addreply", p);
				var result = a.post();
				if (result == "success") {
					wbl_weibo.removeChild(wbl_weibo.lastChild);
				}
			};
			wbl_r.appendChild(wbl_rs);
			/* 这里是显示其他人的评论 */
			var p = "a=breply&bid=" + getbybid + "&date=" + new Date();
			var a = new Ajax("Bills.yy", p);
			var result = a.get();
			var wbl_json = result;
			var wbl_json_obj = (new Function("return " + wbl_json))();// 转换为json对象

			for ( var i = 0; i < wbl_json_obj.type.length; i++) {
				new wblObjReply(wbl_json_obj.type[i], wbl_rs, true);// 第三个参数决定是第几级回复，只有第一级回复显示别人的回复，并显示所有的按钮，后面的就没有了。true为第一级、显示，false为其它，不显示。
			}
			wbl_weibo.appendChild(wbl_r);
		}
	};
	wbl_operatebottom_div_ul_li_a1.innerHTML = "评论(" + json_obj.replys + ")";
	wbl_operatebottom_div_ul_li_a2.setAttribute("href", 'javascript:void(0);');
	wbl_operatebottom_div_ul_li_a2.onclick = function() {
		if (wbl_weibo.lastChild.getAttribute("class") != "operate") {
			wbl_weibo.removeChild(wbl_weibo.lastChild);
		} else {
			var wbl_r = document.createElement("div");// 最外层class="r_top"
			var wbl_rs = document.createElement("div");// 显示其他人的转发内容
			wbl_r.innerHTML = ' <div class="r_top"><span>转发本条</span></div> ';
			var wbl_r_d = document.createElement("div");// textarea的外层div
			var wbl_r_d_t = document.createElement("textarea");// textarea
			wbl_r_d_t.setAttribute("class", "inputtxt");
			wbl_r.appendChild(wbl_r_d);
			wbl_r_d.appendChild(wbl_r_d_t);
			var wbl_r_d_i = document.createElement("div");// textarea的同层div,添加表情和同步转发的功能
			wbl_r.appendChild(wbl_r_d_i);
			wbl_r_d_i.innerHTML = '<div class="insertFun"><div class="sendList insertFace">表情</div></div><div class="left"><ul><li><label><input name="forward" type="checkbox"/>同时评论</label></li></ul></div>';
			// <p class="btn"><a class="btn_a"><span class="btn_30px
			// f14">转发</span></a></p>
			var wbl_r_p = document.createElement("p");// 转发提交按钮p
			var wbl_r_p_a = document.createElement("a");// 转发提交按钮a
			var wbl_r_p_a_span = document.createElement("span");// 转发提交按钮span
			wbl_r.appendChild(wbl_r_p);
			wbl_r_p.appendChild(wbl_r_p_a);
			wbl_r_p_a.appendChild(wbl_r_p_a_span);
			wbl_r.setAttribute("class", 'r_rp');
			wbl_r_p.setAttribute("class", 'btn');
			wbl_r_p_a.setAttribute("class", 'btn_a');
			wbl_r_p_a_span.setAttribute("class", 'btn_30px f14');
			wbl_r_p_a_span.innerHTML = '转发';
			var subparameter = this.getAttribute("subparameter");
			var getbybid = this.getAttribute("getbybid");
			wbl_r_p_a.onclick = function() {// 提交转发内容
				if (wbl_r_d_t.value == "") {
					alertbox("提示", "写点东西吧，转发内容不能为空");
					return false;
				}
				var p = 'bcaption=' + wbl_r_d_t.value + '&' + subparameter;

				// 由此节点开始获取<label><input name="forward"
				// type="checkbox"/>同时评论</label>中input的选中状态
				var andreply = this.parentNode.previousSibling.lastChild.firstChild.firstChild.firstChild.firstChild.checked;
				p += '&andreply=' + andreply;

				var a = new Ajax("Bills.yy?a=addforward", p);
				var result = a.post();
				if (result == "success") {
					wbl_weibo.removeChild(wbl_weibo.lastChild);
				}
			};
			wbl_r.appendChild(wbl_rs);
			/* 这里是显示其他人的转发 */
			var p = "a=bforward&bid=" + getbybid + "&date=" + new Date();
			var a = new Ajax("Bills.yy", p);
			var result = a.get();
			var wbl_json = result;
			var wbl_json_obj = (new Function("return " + wbl_json))();// 转换为json对象

			for ( var i = 0; i < wbl_json_obj.type.length; i++) {
				new wblObjForward(wbl_json_obj.type[i], wbl_rs, true);// 第三个参数决定是第几级回复，只有第一级回复显示别人的回复，并显示所有的按钮，后面的就没有了。true为第一级、显示，false为其它，不显示。
			}
			wbl_weibo.appendChild(wbl_r);
		}
	};
	wbl_operatebottom_div_ul_li_a2.innerHTML = "转发(" + json_obj.forwards + ")";
}// 对象结束

/*
 * 调用对象，需要输入一个json数据。格式为：var wbl_json='{ "type": [{ "uid": "1", "btypename":
 * "旅游", "btype": "8", "replys": "0", "bimageid": "", "bsign": "-", "bioname":
 * "支出", "bidir": "null", "forwards": "0", "username": "泪痕", "bio": "1",
 * "bamount": "33.00", "bdate": "2014-04-20 19:25:48.0", "bid": "358",
 * "bcaption": "我" }, { "uid": "1", "btypename": "旅游", "btype": "8", "replys":
 * "0", "bimageid": "", "bsign": "-", "bioname": "支出", "bidir": "null",
 * "forwards": "0", "username": "泪痕", "bio": "1", "bamount": "36.00", "bdate":
 * "2014-04-20 16:03:26.0", "bid": "355", "bcaption": "我" }, { "uid": "1",
 * "btypename": "人生", "btype": "127", "replys": "0", "bimageid": "", "bsign":
 * "^-^", "bioname": "无金钱", "bidir": "null", "forwards": "0", "username": "泪痕",
 * "bio": "5", "bamount": "0.00", "bdate": "2014-04-19 20:48:28.0", "bid":
 * "352", "bcaption": "我" } ] }';
 */
var wbl_json = listjson;
var wbl_json_obj = (new Function("return " + wbl_json))();// 转换为json对象

for ( var i = 0; i < wbl_json_obj.type.length; i++) {
	new wblObj(wbl_json_obj.type[i], $$("bill_list"));
}
displayimage();

// 评论的对象，虽然和原始的很像，但是有不同，就重写一个。
function wblObjReply(json_obj, theparentNode, displaybool) {
	/* json_obj,即需要显示的数据对象，theparentNode父标签，在谁之下显示 */
	/* wbl=weibolist */
	/* 这是一个从bills_list.jsp页面中 c:forEach 中抽象出来的一个对象，用于显示，并添加js事件 */
	var wbl = document.createElement("div");// 最外层class=""/class="split"
	var wbl_weibo = document.createElement("div");// class="weibo"
	this.wbl_weibo = wbl_weibo;
	var wbl_operateup = document.createElement("div");// class="operate"
	// 这个是上面的操作，下面还有一个
	var wbl_wb_l = document.createElement("div");// class="wb_l"
	var wbl_wb_l_sub1 = document.createElement("div");// sub即子标签，不写则说明没有class
	var wbl_wb_l_sub1_img = document.createElement("img");// class="author"//1
	var wbl_wb_r = document.createElement("div");// class="wb_r"
	var wbl_user_info = document.createElement("div");// class="user_info"
	var wbl_fl_p_u = document.createElement("span");// class="fl p_u"//1
	var wbl_fr_p_t = document.createElement("span");// class="fr p_t"
	var wbl_fr_p_t_sub1 = document.createElement("span");// sub子标签//1
	var wbl_wb_c_wp = document.createElement("div");// class="wb_c_wp"//1
	var wbl_wordColor = document.createElement("span");// class="wordColor"
	var wbl_wordColor2 = document.createElement("span");// class="wordColor",这个显示${list.bsign}${list.bamount}
	var wbl_wordColor_sub1 = document.createElement("a");// sub子标签
	var wbl_from = document.createElement("div");// class="from"//1
	var wbl_fl = document.createElement("span");// class="fl"//1
	var wbl_fr_num = document.createElement("span");// class="fr num"
	var wbl_div1 = document.createElement("div");// style="position:relative1;"
	var wbl_div1_sub1 = document.createElement("img");// class="locateimg_img"
	var wbl_operatebottom = document.createElement("div");// class="operate"
	// 这个是下面的操作，上面还有一个
	var wbl_wb_line = document.createElement("div");// class="wb_line"
	wbl.appendChild(wbl_weibo);
	wbl.appendChild(wbl_wb_line);
	wbl_weibo.appendChild(wbl_operateup);
	wbl_weibo.appendChild(wbl_wb_l);
	wbl_weibo.appendChild(wbl_wb_r);
	wbl_weibo.appendChild(wbl_operatebottom);
	wbl_wb_l.appendChild(wbl_wb_l_sub1);
	wbl_wb_l_sub1.appendChild(wbl_wb_l_sub1_img);// 1
	wbl_wb_r.appendChild(wbl_user_info);
	wbl_wb_r.appendChild(wbl_wb_c_wp);// 1
	wbl_user_info.appendChild(wbl_fl_p_u);// 1
	wbl_user_info.appendChild(wbl_fr_p_t);
	wbl_fr_p_t.appendChild(wbl_fr_p_t_sub1);// 1
	wbl_wb_c_wp.appendChild(wbl_from);// 1
	wbl_wb_c_wp.appendChild(wbl_div1);// 1
	wbl_wordColor.appendChild(wbl_wordColor_sub1);
	wbl_from.appendChild(wbl_fl);// 1
	wbl_from.appendChild(wbl_fr_num);
	// document.body.appendChild(wbl);//到此处对象结构完成
	theparentNode.appendChild(wbl);
	wbl.setAttribute("class", 'split');
	wbl_weibo.setAttribute("class", 'weibo');
	wbl_operateup.setAttribute("class", 'operate');
	wbl_wb_l.setAttribute("class", 'wb_l');
	wbl_wb_l_sub1_img.setAttribute("class", 'author_r');// 1
	wbl_wb_r.setAttribute("class", 'wb_r_r');
	wbl_user_info.setAttribute("class", 'user_info_r');
	wbl_fl_p_u.setAttribute("class", 'fl p_u_r');// 1
	wbl_fr_p_t.setAttribute("class", 'fr p_t_r');
	wbl_wb_c_wp.setAttribute("class", 'wb_c_wp');// 1
	wbl_wordColor.setAttribute("class", 'wordColor');
	wbl_wordColor2.setAttribute("class", 'wordColor');
	wbl_from.setAttribute("class", 'from_r');// 1
	wbl_fl.setAttribute("class", 'fl');// 1
	wbl_fr_num.setAttribute("class", 'fr num');
	wbl_div1_sub1.setAttribute("class", 'locateimg_img');
	wbl_operatebottom.setAttribute("class", 'operate');
	wbl_wb_line.setAttribute("class", 'wb_line');
	wbl_div1.setAttribute("style", "position:relative1;");

	wbl_fl_p_u.innerHTML = json_obj.username + ' ';
	wbl_fr_p_t_sub1.innerHTML = json_obj.bdate.substring(0, 19);
	wbl_fl.innerHTML = json_obj.bcaption;
	wbl_wb_l_sub1_img.src = templatePath + "/uploadavatar/getAvatar.jsp?au="
			+ json_obj.uid;
	/* 上面的定义是本身的内容 */

	/* 这里是内容上部操作按钮 */
	var wbl_operateup_div = document.createElement("div");
	var wbl_operateup_div_i = document.createElement("i");
	var wbl_operateup_div_ul = document.createElement("ul");
	var wbl_operateup_div_ul_li = document.createElement("li");
	var wbl_operateup_div_ul_li_a = document.createElement("a");
	wbl_operateup.appendChild(wbl_operateup_div);
	wbl_operateup_div.appendChild(wbl_operateup_div_i);
	wbl_operateup_div.appendChild(wbl_operateup_div_ul);
	wbl_operateup_div_ul.appendChild(wbl_operateup_div_ul_li);
	wbl_operateup_div_ul_li.appendChild(wbl_operateup_div_ul_li_a);
	wbl_operateup_div.setAttribute("class", 'test');
	wbl_operateup_div_i.setAttribute("class", 'icon-dropdown');
	wbl_operateup_div_ul_li_a.setAttribute("href", 'javascript:void(0);');
	wbl_operateup_div_ul_li_a.onclick = function() {
		if (confirm('确认删除此条记录？？？\n\n此删除为永久删除，不可恢复！！！')) {
			javascript: document.getElementById('b').value = json_obj.bid;
			document.deleteform.submit();
		}
	};
	wbl_operateup_div_ul_li_a.innerHTML = "删除";
	if (displaybool == true) {
		/* 下面的定义是评论内容，下面的操作按钮 */
		var wbl_operatebottom_div = document.createElement("div");
		var wbl_operatebottom_div_i = document.createElement("i");
		var wbl_operatebottom_div_ul = document.createElement("ul");
		var wbl_operatebottom_div_ul_li = document.createElement("li");
		var wbl_operatebottom_div_ul_li_a1 = document.createElement("a");
		var wbl_operatebottom_div_ul_li_a2 = document.createElement("a");
		wbl_operatebottom_div_i.innerHTML = " | ";
		wbl_operatebottom.appendChild(wbl_operatebottom_div);
		wbl_operatebottom_div.appendChild(wbl_operatebottom_div_ul);
		wbl_operatebottom_div_ul.appendChild(wbl_operatebottom_div_ul_li);
		wbl_operatebottom_div_ul_li.appendChild(wbl_operatebottom_div_ul_li_a1);
		wbl_operatebottom_div_ul_li.appendChild(wbl_operatebottom_div_i);
		wbl_operatebottom_div_ul_li.appendChild(wbl_operatebottom_div_ul_li_a2);
		wbl_operatebottom_div.setAttribute("class", 'opbo');
		wbl_operatebottom_div_ul_li_a1.setAttribute("href",
				'javascript:void(0);');
		wbl_operatebottom_div_ul_li_a1.setAttribute("subparameter", "frombid="
				+ json_obj.bid);// 评论时，需要用到的记录id
		wbl_operatebottom_div_ul_li_a1.setAttribute("getbybid", json_obj.bid);// 评论时，需要用到的记录id
		wbl_operatebottom_div_ul_li_a1.onclick = function() {
			if (wbl_weibo.lastChild.getAttribute("class") != "operate") {
				wbl_weibo.removeChild(wbl_weibo.lastChild);
			} else {
				var wbl_r = document.createElement("div");// 最外层class="r_top"
				var wbl_rs = document.createElement("div");// 显示其他人的评论内容
				wbl_r.innerHTML = ' <div class="r_top"><span>回复本条</span></div> ';
				var wbl_r_d = document.createElement("div");// textarea的外层div
				var wbl_r_d_t = document.createElement("textarea");// textarea
				wbl_r_d_t.setAttribute("class", "inputtxt_r");
				wbl_r.appendChild(wbl_r_d);
				wbl_r_d.appendChild(wbl_r_d_t);
				var wbl_r_d_i = document.createElement("div");// textarea的同层div,添加表情和同步转发的功能
				wbl_r.appendChild(wbl_r_d_i);
				// wbl_r_d_i.innerHTML='<div class="insertFun"><div
				// class="sendList insertFace">表情</div></div> <div
				// class="left"><ul><li><label><input name="forward"
				// type="checkbox"/>同时转发</label></li></ul></div><!--上面上转发功能，下面是别人的评论记录-->
				// ';
				// <p class="btn"><a class="btn_a"><span class="btn_30px
				// f14">评论</span></a></p>
				var wbl_r_p = document.createElement("p");// 评论提交按钮p
				var wbl_r_p_a = document.createElement("a");// 评论提交按钮a
				var wbl_r_p_a_span = document.createElement("span");// 评论提交按钮span
				wbl_r.appendChild(wbl_r_p);
				wbl_r_p.appendChild(wbl_r_p_a);
				wbl_r_p_a.appendChild(wbl_r_p_a_span);
				wbl_r.setAttribute("class", 'r_rp_r');
				wbl_r_p.setAttribute("class", 'btn');
				wbl_r_p_a.setAttribute("class", 'btn_a');
				wbl_r_p_a_span.setAttribute("class", 'btn_30px f14');
				wbl_r_p_a_span.innerHTML = '回复';
				var subparameter = this.getAttribute("subparameter");
				var getbybid = this.getAttribute("getbybid");
				wbl_r_p_a.onclick = function() {
					var p = 'bcaption=' + wbl_r_d_t.value + '&' + subparameter;
					var a = new Ajax("Bills.yy?a=addreply", p);
					var result = a.post();
					if (result == "success") {
						wbl_weibo.removeChild(wbl_weibo.lastChild);
					}
				};
				wbl_r.appendChild(wbl_rs);
				/*
				 * var p="a=breply&bid="+getbybid+"date="+new Date(); var a=new
				 * Ajax("Bills.yy",p); var result=a.get(); var
				 * wbl_json=result; var wbl_json_obj= (new Function("return " +
				 * wbl_json))();//转换为json对象
				 * 
				 * for(var i=0;i<wbl_json_obj.type.length;i++) { new
				 * wblObjReply(wbl_json_obj.type[i],wbl_rs,false); }
				 */
				wbl_weibo.appendChild(wbl_r);
			}
		};
		wbl_operatebottom_div_ul_li_a1.innerHTML = "回复";
		wbl_operatebottom_div_ul_li_a2.setAttribute("href",
				'javascript:void(0);');
		wbl_operatebottom_div_ul_li_a2.onclick = function() {
		};
		wbl_operatebottom_div_ul_li_a2.innerHTML = "举报";
	}
}// 评论对象结束

// 转发对象开始，和评论的很像，但是为了各自自由修改，分开
function wblObjForward(json_obj, theparentNode, displaybool) {
	/* json_obj,即需要显示的数据对象，theparentNode父标签，在谁之下显示 */
	/* wbl=weibolist */
	/* 这是一个从bills_list.jsp页面中 c:forEach 中抽象出来的一个对象，用于显示，并添加js事件 */
	var wbl = document.createElement("div");// 最外层class=""/class="split"
	var wbl_weibo = document.createElement("div");// class="weibo"
	this.wbl_weibo = wbl_weibo;
	var wbl_operateup = document.createElement("div");// class="operate"
	// 这个是上面的操作，下面还有一个
	var wbl_wb_l = document.createElement("div");// class="wb_l"
	var wbl_wb_l_sub1 = document.createElement("div");// sub即子标签，不写则说明没有class
	var wbl_wb_l_sub1_img = document.createElement("img");// class="author"//1
	var wbl_wb_r = document.createElement("div");// class="wb_r"
	var wbl_user_info = document.createElement("div");// class="user_info"
	var wbl_fl_p_u = document.createElement("span");// class="fl p_u"//1
	var wbl_fr_p_t = document.createElement("span");// class="fr p_t"
	var wbl_fr_p_t_sub1 = document.createElement("span");// sub子标签//1
	var wbl_wb_c_wp = document.createElement("div");// class="wb_c_wp"//1
	var wbl_wordColor = document.createElement("span");// class="wordColor"
	var wbl_wordColor2 = document.createElement("span");// class="wordColor",这个显示${list.bsign}${list.bamount}
	var wbl_wordColor_sub1 = document.createElement("a");// sub子标签
	var wbl_from = document.createElement("div");// class="from"//1
	var wbl_fl = document.createElement("span");// class="fl"//1
	var wbl_fr_num = document.createElement("span");// class="fr num"
	var wbl_div1 = document.createElement("div");// style="position:relative1;"
	var wbl_div1_sub1 = document.createElement("img");// class="locateimg_img"
	var wbl_operatebottom = document.createElement("div");// class="operate"
	// 这个是下面的操作，上面还有一个
	var wbl_wb_line = document.createElement("div");// class="wb_line"
	wbl.appendChild(wbl_weibo);
	wbl.appendChild(wbl_wb_line);
	wbl_weibo.appendChild(wbl_operateup);
	wbl_weibo.appendChild(wbl_wb_l);
	wbl_weibo.appendChild(wbl_wb_r);
	wbl_weibo.appendChild(wbl_operatebottom);
	wbl_wb_l.appendChild(wbl_wb_l_sub1);
	wbl_wb_l_sub1.appendChild(wbl_wb_l_sub1_img);// 1
	wbl_wb_r.appendChild(wbl_user_info);
	wbl_wb_r.appendChild(wbl_wb_c_wp);// 1
	wbl_user_info.appendChild(wbl_fl_p_u);// 1
	wbl_user_info.appendChild(wbl_fr_p_t);
	wbl_fr_p_t.appendChild(wbl_fr_p_t_sub1);// 1
	wbl_wb_c_wp.appendChild(wbl_from);// 1
	wbl_wb_c_wp.appendChild(wbl_div1);// 1
	wbl_wordColor.appendChild(wbl_wordColor_sub1);
	wbl_from.appendChild(wbl_fl);// 1
	wbl_from.appendChild(wbl_fr_num);
	// document.body.appendChild(wbl);//到此处对象结构完成
	theparentNode.appendChild(wbl);
	wbl.setAttribute("class", 'split');
	wbl_weibo.setAttribute("class", 'weibo');
	wbl_operateup.setAttribute("class", 'operate');
	wbl_wb_l.setAttribute("class", 'wb_l');
	wbl_wb_l_sub1_img.setAttribute("class", 'author_r');// 1
	wbl_wb_r.setAttribute("class", 'wb_r_r');
	wbl_user_info.setAttribute("class", 'user_info_r');
	wbl_fl_p_u.setAttribute("class", 'fl p_u_r');// 1
	wbl_fr_p_t.setAttribute("class", 'fr p_t_r');
	wbl_wb_c_wp.setAttribute("class", 'wb_c_wp');// 1
	wbl_wordColor.setAttribute("class", 'wordColor');
	wbl_wordColor2.setAttribute("class", 'wordColor');
	wbl_from.setAttribute("class", 'from_r');// 1
	wbl_fl.setAttribute("class", 'fl');// 1
	wbl_fr_num.setAttribute("class", 'fr num');
	wbl_div1_sub1.setAttribute("class", 'locateimg_img');
	wbl_operatebottom.setAttribute("class", 'operate');
	wbl_wb_line.setAttribute("class", 'wb_line');
	wbl_div1.setAttribute("style", "position:relative1;");

	wbl_fl_p_u.innerHTML = json_obj.username + ' ';
	wbl_fr_p_t_sub1.innerHTML = json_obj.bdate.substring(0, 19);
	wbl_fl.innerHTML = json_obj.bcaption;
	wbl_wb_l_sub1_img.src = templatePath + "/uploadavatar/getAvatar.jsp?au="
			+ json_obj.uid;
	/* 上面的定义是本身的内容 */

	/* 这里是内容上部操作按钮 */
	var wbl_operateup_div = document.createElement("div");
	var wbl_operateup_div_i = document.createElement("i");
	var wbl_operateup_div_ul = document.createElement("ul");
	var wbl_operateup_div_ul_li = document.createElement("li");
	var wbl_operateup_div_ul_li_a = document.createElement("a");
	wbl_operateup.appendChild(wbl_operateup_div);
	wbl_operateup_div.appendChild(wbl_operateup_div_i);
	wbl_operateup_div.appendChild(wbl_operateup_div_ul);
	wbl_operateup_div_ul.appendChild(wbl_operateup_div_ul_li);
	wbl_operateup_div_ul_li.appendChild(wbl_operateup_div_ul_li_a);
	wbl_operateup_div.setAttribute("class", 'test');
	wbl_operateup_div_i.setAttribute("class", 'icon-dropdown');
	wbl_operateup_div_ul_li_a.setAttribute("href", 'javascript:void(0);');
	wbl_operateup_div_ul_li_a.onclick = function() {
		if (confirm('确认删除此条记录？？？\n\n此删除为永久删除，不可恢复！！！')) {
			javascript: document.getElementById('b').value = json_obj.bid;
			document.deleteform.submit();
		}
	};
	wbl_operateup_div_ul_li_a.innerHTML = "删除";
	if (displaybool == true) {
		/* 下面的定义是转发内容，下面的操作按钮 */
		var wbl_operatebottom_div = document.createElement("div");
		var wbl_operatebottom_div_i = document.createElement("i");
		var wbl_operatebottom_div_ul = document.createElement("ul");
		var wbl_operatebottom_div_ul_li = document.createElement("li");
		var wbl_operatebottom_div_ul_li_a1 = document.createElement("a");
		var wbl_operatebottom_div_ul_li_a2 = document.createElement("a");
		wbl_operatebottom_div_i.innerHTML = " | ";
		wbl_operatebottom.appendChild(wbl_operatebottom_div);
		wbl_operatebottom_div.appendChild(wbl_operatebottom_div_ul);
		wbl_operatebottom_div_ul.appendChild(wbl_operatebottom_div_ul_li);
		wbl_operatebottom_div_ul_li.appendChild(wbl_operatebottom_div_ul_li_a1);
		wbl_operatebottom_div_ul_li.appendChild(wbl_operatebottom_div_i);
		wbl_operatebottom_div_ul_li.appendChild(wbl_operatebottom_div_ul_li_a2);
		wbl_operatebottom_div.setAttribute("class", 'opbo');
		wbl_operatebottom_div_ul_li_a1.setAttribute("href",
				'javascript:void(0);');
		wbl_operatebottom_div_ul_li_a1.setAttribute("subparameter", "frombid="
				+ json_obj.bid);// 转发时，需要用到的记录id
		wbl_operatebottom_div_ul_li_a1.setAttribute("getbybid", json_obj.bid);// 转发时，需要用到的记录id
		wbl_operatebottom_div_ul_li_a1.onclick = function() {
			if (wbl_weibo.lastChild.getAttribute("class") != "operate") {
				wbl_weibo.removeChild(wbl_weibo.lastChild);
			} else {
				var wbl_r = document.createElement("div");// 最外层class="r_top"
				var wbl_rs = document.createElement("div");// 显示其他人的转发内容
				wbl_r.innerHTML = ' <div class="r_top"><span>回复本条</span></div> ';
				var wbl_r_d = document.createElement("div");// textarea的外层div
				var wbl_r_d_t = document.createElement("textarea");// textarea
				wbl_r_d_t.setAttribute("class", "inputtxt_r");
				wbl_r.appendChild(wbl_r_d);
				wbl_r_d.appendChild(wbl_r_d_t);
				var wbl_r_d_i = document.createElement("div");// textarea的同层div,添加表情和同步转发的功能
				wbl_r.appendChild(wbl_r_d_i);
				// wbl_r_d_i.innerHTML='<div class="insertFun"><div
				// class="sendList insertFace">表情</div></div> <div
				// class="left"><ul><li><label><input name="forward"
				// type="checkbox"/>同时转发</label></li></ul></div><!--上面上转发功能，下面是别人的评论记录-->
				// ';
				// <p class="btn"><a class="btn_a"><span class="btn_30px
				// f14">评论</span></a></p>
				var wbl_r_p = document.createElement("p");// 转发提交按钮p
				var wbl_r_p_a = document.createElement("a");// 转发提交按钮a
				var wbl_r_p_a_span = document.createElement("span");// 转发提交按钮span
				wbl_r.appendChild(wbl_r_p);
				wbl_r_p.appendChild(wbl_r_p_a);
				wbl_r_p_a.appendChild(wbl_r_p_a_span);
				wbl_r.setAttribute("class", 'r_rp_r');
				wbl_r_p.setAttribute("class", 'btn');
				wbl_r_p_a.setAttribute("class", 'btn_a');
				wbl_r_p_a_span.setAttribute("class", 'btn_30px f14');
				wbl_r_p_a_span.innerHTML = '回复';
				var subparameter = this.getAttribute("subparameter");
				var getbybid = this.getAttribute("getbybid");
				wbl_r_p_a.onclick = function() {
					var p = 'bcaption=' + wbl_r_d_t.value + '&' + subparameter;
					var a = new Ajax("Bills.yy?a=addforward", p);
					var result = a.post();
					if (result == "success") {
						wbl_weibo.removeChild(wbl_weibo.lastChild);
					}
				};
				wbl_r.appendChild(wbl_rs);
				/*
				 * var p="a=breply&bid="+getbybid+"date="+new Date(); var a=new
				 * Ajax("Bills.yy",p); var result=a.get(); var
				 * wbl_json=result; var wbl_json_obj= (new Function("return " +
				 * wbl_json))();//转换为json对象
				 * 
				 * for(var i=0;i<wbl_json_obj.type.length;i++) { new
				 * wblObjReply(wbl_json_obj.type[i],wbl_rs,false); }
				 */
				wbl_weibo.appendChild(wbl_r);
			}
		};
		wbl_operatebottom_div_ul_li_a1.innerHTML = "回复";
		wbl_operatebottom_div_ul_li_a2.setAttribute("href",
				'javascript:void(0);');
		wbl_operatebottom_div_ul_li_a2.onclick = function() {
		};
		wbl_operatebottom_div_ul_li_a2.innerHTML = "举报";
	}
}// 转发对象结束，和评论的很像，但是为了各自自由修改，分开

// /**封装一个自己的页面弹出框，用alert()不太爽**///
function alertbox(title, content) {
	/*
	 * HTML页面 .alertbox{position:absolute;left:0px;top:0px;border:3px solid
	 * #bbb;width:360px;padding:0;margin:0;z-index:2}
	 * .boxtitle{background-color:#ddd;padding:7px;font-weight:bold;
	 * vertical-align:middle;} .boxcontent{padding:17px;}
	 * .btn{text-align:right;padding:0;}
	 * .btn_a{color:#fff;overflow:hidden;vertical-align:middle;border:1px solid
	 * #ff9b01;cursor:pointer;display:inline-block;
	 * border-radius:2px;background-color:#ffa00a}
	 * .btn_a:havor{color:#fff;overflow:hidden;vertical-align:middle;border:1px
	 * solid #ffce82;cursor:pointer;display:inline-block;
	 * border-radius:2px;background-color:transparent} .f14{font-size:14px;}
	 * .btn_30px{height:26px;line-height:26px;padding:0
	 * 15px;display:inline-block;}
	 * .boxmask{position:fixed;left:0px;top:0px;height:100%;width:100%;background-color:#eee;display:block;background-attachment:fixed;filter:alpha(opacity=80);-moz-opacity:0.8;-khtml-opacity:
	 * 0.8;opacity: 0.8;z-index:1} <div><div class="alertbox">
	 * <p class="boxtitle">提示</p> <div class="boxcontent"> <p class="boxmsg">评论需要写点内容的，不能为空！</p>
	 * <p class="btn"><a class="btn_a"><span class="f14 btn_30px">确定</span></a></p>
	 * </div> </div> <div class="boxmask"></div><div>
	 */
	var ab_alertbox_main = document.createElement("div");
	var ab_alertbox = document.createElement("div");
	var ab_boxtitle = document.createElement("p");// 提示标题内容
	var ab_boxcontent = document.createElement("div");
	var ab_boxmsg = document.createElement("p");// 提示内容内容
	var ab_btn = document.createElement("p");
	var ab_btn_a = document.createElement("a");
	var ab_f14_btn_30px = document.createElement("span");
	var ab_boxmask = document.createElement("boxmask");
	ab_alertbox_main.appendChild(ab_alertbox);
	ab_alertbox_main.appendChild(ab_boxmask);
	ab_alertbox.appendChild(ab_boxtitle);
	ab_alertbox.appendChild(ab_boxcontent);
	ab_boxcontent.appendChild(ab_boxmsg);
	ab_boxcontent.appendChild(ab_btn);
	ab_btn.appendChild(ab_btn_a);
	ab_btn_a.appendChild(ab_f14_btn_30px);
	ab_alertbox.setAttribute("class", "alertbox");
	ab_boxtitle.setAttribute("class", "boxtitle");
	ab_boxcontent.setAttribute("class", "boxcontent");
	ab_boxmsg.setAttribute("class", "boxmsg");
	ab_btn.setAttribute("class", "btn");
	ab_btn_a.setAttribute("class", "btn_a");
	ab_f14_btn_30px.setAttribute("class", "f14 btn_30px");
	ab_boxmask.setAttribute("class", "boxmask");
	ab_f14_btn_30px.innerHTML = "确定";
	ab_btn_a.onclick = function() {
		ab_alertbox_main.style.display = "none"
	};
	ab_boxtitle.innerHTML = title;
	ab_boxmsg.innerHTML = content;
	/* 定位，最烦的事情。比写css还烦 */
	ab_alertbox.style.left = ((document.body.clientWidth - 360) / 2) + "px";
	var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
	ab_alertbox.style.top = (scrollY + (window.screen.availHeight - 360) / 2)
			+ "px";
	document.body.appendChild(ab_alertbox_main);

}