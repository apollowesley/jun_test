<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<#assign username="李四">
<#--此时模板中的变量的名称和模型中的变量名称一致，不是覆盖，而是隐藏-->
${username}
<#--使用.globals可以访问模型中的变量-->
${.globals.username}
<#macro test>
<#--此时当调用该指令之后，会将模板中的变量username覆盖为王五
所以这种方式存在风险，所以一般不使用这种方式在指令中定义变量-->
	<#--<#assign username="王五"/>-->
	<#--使用local可以声明局部变量，所以在marco中非特殊使用局部变量-->
	<#local username="王五"/>
	${username}
</#macro>
<@test/>
${username}
<#list 1..3 as username>
<#--循环中的变量出了循环就消失-->
	${username}
</#list>
${username}
</body>
</html>