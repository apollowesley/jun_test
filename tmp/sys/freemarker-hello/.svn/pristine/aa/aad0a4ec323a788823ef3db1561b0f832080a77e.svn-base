<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<#--对于hello这个自定义指令而言，hello后的都是参数，有两个参数一个是num一个是ok-->
<#macro hello num ok>
	<#list 1..num as n>
		<h1>Hello${ok}${n}</h1>
	</#list>
</#macro>

<#--<@hello/>会报错，因为hello有两个参数,在定义参数的值的时候参数名不能省略-->
<@hello num=3 ok="World"/>

<#--为repeat的两个参数定义了初始值，此时在调用该指令就可以省略参数，如果省略会使用默认值-->
<#macro repeat num=10 ok="World">
	<#list 1..num as n>
		<h1>Hello${ok}${n}</h1>
	</#list>
</#macro>

<@repeat/>

<#macro test>
	<#--nested会输出指令中的内容-->
	<#nested 12 33/>
	<#nested 11 22/>
	<#nested 22 33/>
</#macro>

<@test;x,y>
	<h1>你好啊--${x}--${y}</h1>
</@test>
</body>
</html>