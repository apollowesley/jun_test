var req;
var url;
/** 通过异步传输XMLHTTP发送参数到ajaxServlet，返回符合条件的XML文档 */
function getResult() {
	var username=document.getElementById('username').value;
	username=encodeURI(username);
	username=encodeURI(username);
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
		url = "RegLegalAjax.yy?p=u&username="+username;
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
		url = "RegLegalAjax.yy?p=u&username="+username;
	}
	if (req) {
		req.open("GET", url, true);
		req.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
		// 这里如果不设定头部则会导致 firfox 发送数据错误，servlet接受到的参数为乱码，在IE中正常
		req.onreadystatechange = complete;
		req.send(null);
		// req.setRequestHeader("Content-Type", "text/xml; charset=UTF-8");
	}
};
/** 分析返回的XML文档 */
function complete() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var xmlDoc = req.responseXML;
			var Node = xmlDoc.getElementsByTagName("type_name");
			if(Node.length==0){
				userisexisted=false;
			}else{
				userisexisted=true;
				document.getElementById('username').parentNode.className=document.getElementById('username').parentNode.className.substring(0, 25)+" msg_err";
				document.getElementById('username_msg').innerHTML="用户名已经存在";
			}
			/*var items = document.getElementById("belong");
			// 以下为解析返回的XML文档
			var xmlDoc = req.responseXML;
			var Node = xmlDoc.getElementsByTagName("type_name");
			// var str=new Array();
			var str = null;
			// 清空工作
			items.innerHTML = ""; // 删除一个 select内的全部内容
			for ( var i = 0; i < Node.length; i++) {
				str = Node[i];
				// alert(str.childNodes[0].nodeValue);
				var objectOption = document.createElement("option");
				items.options.add(objectOption);
				// firfox不支持innerText必须用textContent代替
				if (window.ActiveXObject) {
					objectOption.innerText = str.childNodes[0].nodeValue;
				} else {
					objectOption.textContent = str.childNodes[0].nodeValue;
				}
			}*/
		}
	}
};

function getResultEmail() {
	var email=document.getElementById('email').value;
	email=encodeURI(encodeURI(email));
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
		url = "RegLegalAjax.yy?p=e&email="+email;
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
		url = "RegLegalAjax.yy?p=e&email="+email;
	}
	if (req) {
		req.open("GET", url, true);
		req.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
		req.onreadystatechange = completeemail;
		req.send(null);
	}
};
/** 分析返回的XML文档 */
function completeemail() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var xmlDoc = req.responseXML;
			var Node = xmlDoc.getElementsByTagName("type_name");
			if(Node.length==0){
				emailisexisted=false;
			}else{
				emailisexisted=true;
				document.getElementById('email').parentNode.className=document.getElementById('email').parentNode.className.substring(0, 25)+" msg_err";
				document.getElementById('email_msg').innerHTML="邮箱已经被使用";
			}
		}
	}
};