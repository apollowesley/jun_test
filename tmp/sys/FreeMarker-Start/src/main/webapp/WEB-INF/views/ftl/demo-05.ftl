<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello FreeMarker</title>
</head>
<body>
<#assign var=102/>
<h4>a=${var}</h4>
<h2>if语法</h2>
<#if var==100>
A等于100
<#elseif var==-100>
A等于-100
<#else >
A等于其他
</#if>

<h2>switch语法</h2>

<#switch var>
    <#case 100>
    A==100
        <#break >
    <#case 101>
    A==101
        <#break >
    <#case 102>
    A==102
        <#break >
    <#default>
    A默认未知
</#switch>
</body>
</html>