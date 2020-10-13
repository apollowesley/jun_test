var mxCore={
	/***核心函数：检测window.WebViewJavascriptBridge是否已经可用，如果不可用，就注册监听事件，直至WebViewJavascriptBridge可用。当可用时回调调用者的回调函数，通知调用者mxjs桥可用***/
	connectWebViewJavascriptBridge:function (callback) {
		if (window.WebViewJavascriptBridge) {
			WebViewJavascriptBridge.init(function(message, responseCallback) {
				var data = { 'Javascript Responds':'signin' }
			});
			callback(WebViewJavascriptBridge);
		} else {
			document.addEventListener('WebViewJavascriptBridgeReady', function() {
									  WebViewJavascriptBridge.init(function(message, responseCallback) {
											var data = { 'Javascript Responds':'signin' }
											//responseCallback(data);
										});
									  callback(WebViewJavascriptBridge);
			}, false);
		}
	}
	,
	/**使用js方式解析URL（包括远程URL和本地URL）的某个参数的值**/
	getQueryString:function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	}
	,
	/***设置原生界面（WEBView）的标题***/
	setWebViewTitle:function(title){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('setWebViewTitle', {'title':title}, function(response) {});
		}
	},
	getMXToken:function(appid,securityid,callBackFunc){
		if (window.WebViewJavascriptBridge){
			window.WebViewJavascriptBridge.callHandler('getMXToken', 
								{'appid':appid,
								'securityid':securityid}, callBackFunc);
		}else{
			callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
		}
	},
	/***设置原生界面的菜单****/
	setMainMenu:function(menus){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('setMainMenu', menus, function(response) {});
		}
	},
	/****隐藏WebView标题栏***/
	hideWebViewTitle:function(){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('hideTitle', {}, function(response) {
                                                      });
		}
	},
	/***设置原生的退回按钮的响应方式***/
	setTurnbackMode:function(turnbackMode,turnbackFuncName){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('setTrunbackFunc', {turnbacktype:turnbackMode,functionname:turnbackFuncName}, function(response) {
                                                      });
		}
	},
	/***设置原生的退回按钮的响应方式***/
	setTurnbackMode2Normal:function(){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('setTrunbackFunc', {turnbacktype:'normal'}, function(response) {
                                                      });
		}
	},
	/***设置原生的退回按钮的响应方式***/
	setTurnbackMode2GoBack:function(){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('setTrunbackFunc', {turnbacktype:'goback'}, function(response) {
                                                      });
		}
	},
	/***设置原生的退回按钮的响应方式***/
	setTurnbackMode2Custom:function(turnbackFuncName){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('setTrunbackFunc', {turnbacktype:'custom',functionname:turnbackFuncName}, function(response) {
                                                      });
		}
	},
	/****隐藏WebView标题栏***/
	showWebViewTitle:function(){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('showTitle', {}, function(response) {
                                                      });
		}
	},
	/***关闭轻应用的窗口，并返回数据***/
	closeMXWindow:function(data){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('finishActivity', data, function(response) {
                                                      });
		}
	},
	/***获取当前登录的职员信息 ***/
	getCurrentUser:function(callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('getPersonInfo',null,callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	/**选择职员****/
	selectPerson:function(typeFilter,isMultiSelect,callBackFunc){
		if(window.WebViewJavascriptBridge ){
				if (typeFilter > 7 || typeFilter <=0){
					if(callBackFunc != null && callBackFunc!= undefined){
					callBackFunc({code:-2,msg:'typeFilter不正确 '});
				}
				return;
			}
			window.WebViewJavascriptBridge.callHandler('selectPerson',{typefilter:typeFilter,ismultiselect:isMultiSelect},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	takePhoto:function(callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('takePhoto',{},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	selectPhoto:function(callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('selectPhoto',{},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
				// callBackFunc({code:0,data:'../photos/test.jpg',msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	offlineSaveImage:function(imageid,imagedata,callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('offlineSaveImage',{
				imageid:imageid,
				imagedata:imagedata},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
				// callBackFunc({code:0,data:'../photos/test.jpg',msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	getOfflineImage:function(imageid,callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('getOfflineImage',
				{imageid:imageid},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
				// callBackFunc({code:0,data:'../photos/test2.jpg',msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	
	getOfflineImageReceiveProgress:function(imageid,callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('getOfflineImageReceiveProgress',
				{imageid:imageid},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
				// callBackFunc({
				// 	code:0,
				// 	msg:'米讯桥服务器未完成初始化',
				// 	data:{
				// 		status:2,
				// 		receivecount:500,
				// 		totalcount:1000
				// 	}
				// });
			}
		}
	},
	getOfflineImageSendProgress:function(imageid,callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('getOfflineImageSendProgress',
				{imageid:imageid},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
				// callBackFunc({
				// 	code:0,
				// 	msg:'米讯桥服务器未完成初始化',
				// 	data:{
				// 		status:2,
				// 		receivecount:500,
				// 		totalcount:1000
				// 	}
				// });
			}
		}
	},
	getAllOfflineImage:function(callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('getAllOfflineImages',{},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	generateQRCode:function(strData,callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('generateQRCode',{data:strData},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	readImageFile:function(inputButton,imgBlock){
		var file = inputButton.files[0];
		if(!/image\/\w+/.test(file.type)){
			alert("请确保文件为图像类型");
			return false;
		}
		var reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onload = function(e){
			imgBlock.src=this.result;
		}
	},
	getLocation:function(callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('getLocation',{},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	/***
	通过经纬度获取poi信息
	*/
	translateAddress:function(lng,lat,callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('translateLocation',{longitude:lng,latitude:lat},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:-1,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	saveOfflineObject:function(appkey,groupkey,objectid,objstr,objstate,callBackFunc){
		callBackFunc({code:500,msg:'功能尚未实现'});
	},
	getOfflineObjectList:function(appkey,groupkey,objstate,callBackFunc){
		callBackFunc({code:500,msg:'功能尚未实现'});
	},
	getOfflineObject:function(appkey,groupkey,objectid,callBackFunc){
		callBackFunc({code:500,msg:'功能尚未实现'});
	},
	deleteOfflineObject:function(appkey,groupkey,objectid,callBackFunc){
		callBackFunc({code:500,msg:'功能尚未实现'});
	},
	deleteAllOfflineObject:function(appkey,groupkey,callBackFunc){
		callBackFunc({code:500,msg:'功能尚未实现'});
	},
	postHttp:function(url,params,objectid,canOffline,usemxtoken,callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('offlinehttp_post',{
					url:url,
					params:params,
					objectid:objectid,
					canoffline:canOffline,
					usemxtoken:usemxtoken},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:500,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	getPostResultByPostID:function(postid,callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('query_post',{
					postid:postid},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:500,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	getPostResultByObjectID:function(objectid,callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('query_post',{
					objectid:objectid},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:500,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	getPostResult:function(key,dtfrom,dtto,state,callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('query_post',{
				key:key,
				from:dtfrom,
				to:dtto,
				state:state},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:500,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	deleteCachedPostByPost:function(postid,callbackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('delete_cachedpost',{postid:postid},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:500,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	deleteCachedPostByObject:function(object,callbackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('delete_cachedpost',{postid:postid},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:500,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	getQRCode:function(callBackFunc){
		if(window.WebViewJavascriptBridge ){
			window.WebViewJavascriptBridge.callHandler('getQRCode',{},callBackFunc);
		}else{
			if(callBackFunc != null && callBackFunc!= undefined){
				callBackFunc({code:500,msg:'米讯桥服务器未完成初始化'});
			}
		}
	},
	guid:function() {
	    function S4() {
	       return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
	    }
	    return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
	},
	
	/***测试函数,请永远放在最后面***/
	
	testMXCore:function(msg){
		alert(msg);
	}
};