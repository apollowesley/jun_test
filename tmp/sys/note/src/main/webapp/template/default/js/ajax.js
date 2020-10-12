function Ajax(url, data) {
	this.url = url;
	this.data = data+"&date="+new Date().getTime();
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