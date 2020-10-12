'use strict';
/*
ajax方法不知道传递什么格式的数据,暂不使用
*/
function ajax(url,data){
  console.log('ajaxUtil.ajax():',data)
	var promise = new Promise(function(resolve,reject){
    var client = new XMLHttpRequest();
    client.open("POST",url);
    client.onreadystatechange = handler;
    client.responseType = "json";
    client.setRequestHeader("Accept","application/json");
    client.send(data);

    function handler(){
      if(this.readyState!==4){
        return;
      }
      if(this.status===200){
        resolve(this.response);
      }else{
        reject(new Error(this.statusText));
      }
    };
  });
  return promise;
}
/*
  AJAX POST请求方法
  url:请求url,
  data:请求数据
  callback:回调函数
*/
function post(url,data,callback){
    if(!data){
        alert('please input data');
        return;
    }
    if(typeof data =='object'){
      data = (function(obj){ // 转成post需要的字符串.
        var str = "";
        for(var prop in obj){
          str += prop + "=" + obj[prop] + "&"
        }
        return str;
      })(data);
    }
    var xmlhttp;
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }else  {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()  {
      if (xmlhttp.readyState==4 && xmlhttp.status==200){
        callback(xmlhttp.responseText);
      }
    }
    xmlhttp.open("POST",url,true);//"model2.json"   
    xmlhttp.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    xmlhttp.send(data);
  }