<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello FreeMarker</title>
</head>
<body>
<h4>freemarker宏macro nested return 指令</h4>
<h3>macro指令</h3>
<#--macro声明部分-->
<#macro kiwipeach1>
<p><font color="red">泥猴桃——卡卡罗特</font></p>
</#macro>
<#macro kiwipeach2 param1>
<p><font color="red">你好,${param1}</font></p>
</#macro>
<#macro kiwipeach3 param1="卜铷">
<p><font color="red">你好,${param1}</font></p>
</#macro>
<#--paramExt是一个map类型的数据-->
<#macro kiwipeach4 param1="卜铷" param2="黄莹" paramExt...>
<p><font color="red">你好,${param1},${param2},${paramExt["name"]}</font></p>
</#macro>
<#--macro调用部分-->
<h4>macro不带参数</h4>
<@kiwipeach1></@kiwipeach1>
<h4>macro带一个参数</h4>
<@kiwipeach2 param1="孙悟空"></@kiwipeach2>
<h4>macro带默认值参数</h4>
<@kiwipeach3 param1="孙悟空"></@kiwipeach3>
<h4>macro带多个参数</h4>
<@kiwipeach4 name="盖伦"></@kiwipeach4>

<#--nested指令部分-->
<h4>nested指令</h4>
<#macro myTag color count=1>
    <#list 1..count as index>
        <#nested color/>
    </#list>
</#macro>
<@myTag color="pink" count=3;paramColor>
<p><font color="${paramColor}">男孩别哭，美丽世界的孤儿</font></p>
</@myTag>
<@myTag color="blue";paramColor>
<p><font color="${paramColor}">男孩别哭，美丽世界的孤儿</font></p>
</@myTag>

<h4>function指令</h4>
<#function myAdd param1 param2>
    <#return param1+param2/>
</#function>
<p>100+23=【${myAdd(100,23)}】</p>
</body>
</html>