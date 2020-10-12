(function(document){
    var viewport;
    var obj = {
        init:function(id){
           viewport = document.querySelector("#"+id);
           console.log(viewport);
        },
        addChild:function(child){
            viewport.appendChild(child);
        },
        removeChild:function(child){
            viewport.removeChild(child);
        }
    }
    window.jView = obj;
})(document);

var Test = (function (){
  function test1(){
     console.log('this is test1');
  }
  function test2(){
     console.log('this is test2');
  }
  (function(){
    test2();
  })();
  return {
    test1:test1,
    test2:test2
  }
})();

var ThisTest = (function(){

  function test11(){
    console.log(this.init);
  }
  return {
    test11:test11
  };
})();

var tt = (function(){
  
})();

var ajax = (function(){
  var xmlhttp;
  (function (){
    if(window.XMLHttpRequest){
      xmlhttp = new XMLHttpRequest();
    }else{
      xmlhttp = new ActiveXObject('Microsoft.XMLHTTP');
    }
    console.log('3333333333333333333333333333333333333333333333333333333333333');
    //return xmlhttp;
  })();
  function post(url,data,callback,async){
    if(typeof data =='object'){
      postData = (function(obj){ // 转成post需要的字符串.
        var str = "";
        for(var prop in obj){
          str += prop + "=" + obj[prop] + "&"
        }
        return str;
      })(postData);
    }
    xmlhttp.open('POST',url,async||true);
    xmlhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xmlhttp.send(data);
    xmlhttp.onreadystatuechange = function(){
      if(xmlhttp.readystate===4 && xmlhttp.status===200){
        callback(xmlhttp.responseText);
      }
    };
  }
  function get(url,callback,async){
    console.log(xmlhttp);
    xmlhttp.open('GET',url,async||true);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function(){
      if(xmlhttp.readyState===4 && xmlhttp.status===200){
        console.log(xmlhttp.responseText);
        callback(xmlhttp.responseText);
      }
    };

  }
  return {
    post:post,
    get:get
  };
})();
