<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello FreeMarker</title>
</head>
<body>
<h4>字符串的内嵌函数</h4>
<h3><a href="http://blog.csdn.net/zgf19930504/article/details/50768033" target="_blank">更多Freemarker内嵌函数</a></h3>
<ul>
<#assign a='hello'>
<#assign b='world!'>
    <li>连接</li>
    <font color="red" size="18px">${a+b}</font><br/>
    <li>截取</li>
    <font color="blue" size="18px">${(a+b)?substring(5,8)}</font><br/>
    <li>长度</li>
    <font color="red" size="18px">${(a+b)?length}</font><br/>
    <li>大写</li>
    <font color="blue" size="18px">${(a+b)?upper_case}</font><br/>
    <li>小写</li>
    <font color="red" size="18px">${(a+b)?lower_case}</font><br/>
    <li>index_of</li>
    <font color="blue" size="18px">${(a+b)?index_of('w')}</font><br/>
    <li>replace</li>
    <font color="red" size="18px">${(a+b)?replace('o','abc')}</font><br/>
    <li>last_index_of</li>
    <font color="blue" size="18px">${(a+b)?last_index_of('o')}</font><br/>
</ul>
</body>

</html>