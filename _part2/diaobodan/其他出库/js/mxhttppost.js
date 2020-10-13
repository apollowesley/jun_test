var MxHttpPostDebuger={
	forceToDebug:false,
	foreToMX:false,
	isUseMx:false,
	init:function(){},
	postHttp:function(url,params,objectid,canOffline,useMxToken,callbackFunc){},
	queryHttpResultByPostId:function(postid,CallbackFunc){},
	queryHttpResultByObjectId:function(objectid,CallbackFunc){},
}
MxHttpPostDebuger.init=function(){
	if(MxHttpPostDebuger.forceToDebug){
		MxHttpPostDebuger.isUseMx = false;
	}else if(MxHttpPostDebuger.foreToMX){
		MxHttpPostDebuger.isUseMx = true;
	}else{
		if (window.WebViewJavascriptBridge ==undefined 
			|| window.WebViewJavascriptBridge == null){
			MxHttpPostDebuger.isUseMx = false;
		}else{
			MxHttpPostDebuger.isUseMx = true;
		}
	}
}
MxHttpPostDebuger.postHttp = function(url,params,objectid,canOffline,useMxToken,callbackFunc){
	//alert(MxHttpPostDebuger.isUseMx);
	if (MxHttpPostDebuger.isUseMx==false){
		$.post(url,params,function(jsonData){
			if(typeof(jsonData)=="string"){
					 jsonData=$.parseJSON(jsonData);
				}
				var code=jsonData.code.toString();
				if(code===-1){			
					window.localStorage.removeItem(appbase.config.accountMsg);
					window.location.href = appbase.config.loginUrl;					
				} else {
					if(typeof callbackFunc !== 'function') { // 如果没有回调函数,抛出异常。
						throw new Error('请求数据之后，没有回调函数!');
					}
					// 没过期。执行下面函数。
					callbackFunc(jsonData);		

				}
		}).error(function(xhr,textStatus){callbackFunc({code:500,msg:'网络异常'})});
	}else{
		mxCore.postHttp(url,params,objectid,canOffline,useMxToken,callbackFunc);
	}
}
MxHttpPostDebuger.queryHttpResultByPostId=function(postid,CallbackFunc){
	mxCore.getPostResultByPostID(postid,CallbackFunc);
}

MxHttpPostDebuger.queryHttpResultByObjectId=function(objectid,CallbackFunc){
	mxCore.getPostResultByObjectID(objectid,CallbackFunc);
}

mxCore.connectWebViewJavascriptBridge(
	function(bridge){
		MxHttpPostDebuger.init();
	}
);
/**立即初始化**/
