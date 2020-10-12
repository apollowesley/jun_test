<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<#assign nums=[1,3,4,56,33,43]/>
${nums?first}
<#--特别注意，以下定义不使用[12..200]-->
<#assign nums=12..200/><#--定义了一个连续的序列从12到199-->
<#--序列的拆分-->
<#assign nums1=nums[1..10]>
<#list nums1 as num>
${num}
</#list>
${"你好，你来了吗今天看书了吗！"[0..8]}...
<#assign maps={"1":"张三","2":"李四"}>
${maps["1"]}
<#--以下代码可以将map的key转换为相应的序列-->
<#assign keys=maps?keys>
<#list keys as key>
${key}---${maps[key]}

<#assign users={"username":"张三","password":"123"}>
${users.username}---${users["password"]}
</#list>
</body>
</html>