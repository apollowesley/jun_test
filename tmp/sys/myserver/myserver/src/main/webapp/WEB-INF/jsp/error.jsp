<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
       2018knownoting.xyz错误提醒
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <meta name="msapplication-tap-highlight" content="no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="robots" content="noindex" />
    <meta name="robots" content="noarchive" />
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
    <meta name="renderer" content="webkit" />
    <meta name="Copyright" Content=" 本页版权归liuye所有。All Rights Reserved" />
    <meta name="Description" Content=" 华南理工大学广州学院" />
    <meta name="Keywords" Content=" tentcoo, GCU, recruit" />
</head>
<style>
    body,
    div{
        margin: 0;
        padding: 0;
    }
    html,
    body{
        height: 100%;
       background-image:url(http://120.79.148.96:8080/cityBackground.jpg);
    }
    .content{
        position: absolute;
        top: 50%;
        left: 50%;
        margin: -125px auto auto -300px;
        border: 3px solid #000;
        width: 600px;
        height: 250px;
        line-height: 250px;
        text-align: center;
        font-size: 30px;
        color: #000;

    }
    h3{
    text-align: center;
    color:red;
    }
    a{
    text-align: center;
    color:red;
    }
</style>
<body>
	<h3>${message}</h3>
	<a href="index.html">点击这里，去注册啊！</a>
    <div class="content">
        请使用手机微信或手机浏览器访问！
    </div>
</body>
</html>