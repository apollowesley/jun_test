<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Document</title>
  <style>
    #box table {
      border-collapse: collapse;
    }
  </style>
</head>
<body>
  <div id="box">
  
  </div>
  <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js" ></script>
  <script src="/static/js/common.js"></script>
  <script src="/static/js/table.js"></script>
  <script> 
  	$.post('/user/find',function(result){
  		console.log(result);
  		var bodyData = result;
  		 // 表头数据
  	    var headData = ['编码', '用户名', '密码', '操作'];

  	    var box = my$('box');
  	    
  	    createTable(box, headData, bodyData);
	});
  </script>
</body>
</html>