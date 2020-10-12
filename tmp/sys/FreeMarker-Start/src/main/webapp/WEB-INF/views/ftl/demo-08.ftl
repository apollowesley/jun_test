<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello FreeMarker</title>
</head>
<body>
<h4>freemarker自定义指令：admin权限信息获取</h4>
<#assign curRole='admin'>
<@role role='${curRole}';resultRole,permissions>
<p>当前用户角色：${resultRole}</p>
<p>
    当前用户权限：
    <#list permissions as item>
    ${item_index}、${item}--
    </#list>
</p>
</@role>
</body>
</html>