<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type"content="text/html; charset=UTF-8">
    <title>Hello FreeMarker</title>
</head>
<body>
<h2>基础遍历</h2>

<#list ["green","red","blue"] as color>
    ${color}-
</#list>

<#assign numberList=[1,2,3]>
<#list numberList as num>
    ${num}-
</#list>


<h2>Collection遍历</h2>
<#list employCollection as item >
<h4>编号:${item.empno}-姓名:${item.ename}-职位:${item.job}-薪水:${item.sal}</h4>
</#list>
<h2>Map遍历</h2>
<#list employMap?keys as key>
<h4>Key:[key]  Value: [编号:${employMap[key].empno}-姓名:${employMap[key].ename}-职位:${employMap[key].job}-薪水:${employMap[key].sal}]</h4>
</#list>

</body>
</html>