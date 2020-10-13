var clickEvent = "ontouchstart" in document.documentElement ? "touchstart" : "click";

var BaseApp = {
	isDebug:false,
	//base_url:'http://10.50.9.248:8080/mescloudsoa',
	base_url:'http://115.29.137.247:8080/oa',
	//base_url:'http://59.42.107.139:9090/oa',
	//base_url:'http://10.50.9.238:8080',
	//base_url:'http://127.0.0.1:8080/mescloudsoa',
	//base_url:'http://10.50.9.47:8080/mescloudsoa',
	//base_url:'http://127.0.0.1:8080/mescloudsoa',
	token:'ZRmEetdNLDqwC9oMVah4c3oP5ROgX8',
	// 获取url的参数
	getParamByUrl: function() {

	   var url = window.location.search; //获取url中"?"符后的字串
	   var theRequest = {};

	   if (url.indexOf("?") !== -1) {

	      var str = url.substr(1),
	      	  strs = str.split("&");
	      for(var i = 0; i < strs.length; i ++) {

	         theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
	      }
	   }
	   return theRequest;
	},
	myAjax: function (type, url, data, callback1, callback2, isAsync) {
		var async = (typeof isAsync != 'undefined')? isAsync: true;
		$.ajax({
			async: async,
			type: type,
			cache:false,
			url: url,
			data: data,
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			success: function(json) {
				if(callback1 != '') { callback1(json); }
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				if(callback2 != '') { callback2(XMLHttpRequest, textStatus, errorThrown); }
			}
		});
	}

};

var ORDERSTUTAS = {
	10 : "新增",
	20 : "保存",
	30 : "提交",
	40 : "审核",
	50 : "复核",
	60 : "作废",
	70 : "已结帐",
	80 : "开始盘点",
	90 : "已生成盘点表",
	11 : "完成",
	12 : "取消",
	13 : "进行中",
	100: "初審",
	110: "审核中"
};

Date.prototype.format = function(format){ 
	var o = {
	"M+" : this.getMonth()+1, //month 
	"d+" : this.getDate(), //day 
	"h+" : this.getHours(), //hour 
	"m+" : this.getMinutes(), //minute 
	"s+" : this.getSeconds(), //second 
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
	"S" : this.getMilliseconds() //millisecond 
	} 

	if(/(y+)/.test(format)) { 
	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 

	for(var k in o) { 
	if(new RegExp("("+ k +")").test(format)) { 
	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
	} 
	} 
	return format; 
} 