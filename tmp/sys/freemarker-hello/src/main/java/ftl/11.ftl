<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<#--
	使用incldue可能会出现覆盖的问题，可以使用import来完成导入，并且加入名称空间
	<#include "/inc/inc1.ftl"/>
	<#include "/inc/inc2.ftl"/>
-->
<#import "/inc/inc2.ftl" as inc2/>
<#import "/inc/inc1.ftl" as inc1/>
	${inc2.username}
	${inc1.username}
<#--将一个变量定义到名称空间中-->
<#assign age=12 in inc2/>
${inc2.age}
<#--访问名称空间中的自定义指令-->
<@inc1.test/>
</body>
</html>