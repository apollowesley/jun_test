// console.log(urlEncode(arr||obj).slice(1))//调用
function ArrToUrl(param) {
    var paramStr = '';
		if(param instanceof Array){
			 for (let i=0; i< param.length;i++) {
				 for (var p in param[i]) {
				 	paramStr += p+'='+param[i][p]+'&';
				 }	
			 }
		}else if(param instanceof Object){
			for (var p in param) {
				paramStr += p+'='+param[p]+'&';
			}	
		}
		paramStr = paramStr.substring(0, paramStr.lastIndexOf('?'));
    return paramStr ;

}
function ObjToUrl(json){
    var str= Object.keys(json).map(function (key) {
        return encodeURIComponent(key) + "=" + encodeURIComponent(json[key]);
    }).join("&");
    if(str.substr(0, 1) == "&") { 
        // 删除第一个字符 
        str = str.slice(1); 
    }
    return str;
}
function UrlToObj(str){
　　var obj = {};
　　var arr1 = str.split("?");
　　var arr2 = arr1[1].split("&");
　　for(var i=0 ; i < arr2.length; i++){
　　　　var res = arr2[i].split("=");
　　　　obj[res[0]] = res[1];
　　}
　　return obj;
}
  export default {
    // CusBASE64: __BASE64,
    ArrToUrl:ArrToUrl,
	ObjToUrl:ObjToUrl,//
	UrlToObj:UrlToObj
	// encoder:base64decode
  }