<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello FreeMarker</title>
</head>
<body>
<h4>自定义排序函数</h4>
<#assign  mylist=[2,3,6,4,0,8,7,9] />
<h2>排序前</h2>
<#list  mylist  as  item>
${item}
</#list>
<h2>排序后</h2>
<#list  sort_int(mylist)  as  item>
${item}
</#list>
<h4>freemarker引擎的内嵌sort函数</h4>
<#assign  mylist=[2,3,6,4,0,8,7,9] />
<h2>排序后,list size is ${mylist?size}</h2>
<#list  mylist?sort?reverse  as  item>
<p>${item_index}-${item}</p>
</#list>
<h4>freemarker其他方法</h4>
<p>mylist[2]=${mylist[2]}</p>
</body>
</html>