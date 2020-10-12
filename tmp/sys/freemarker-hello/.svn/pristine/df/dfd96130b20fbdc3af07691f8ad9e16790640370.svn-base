<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<#--定义变量-->
<#assign username="张三"/>
${username}
<#--定义了一个数字-->
<#assign num=10>
${num+11}
<#assign str="10"/>
${str+11}
<#--值会完成覆盖-->
<#assign str=33/>
${str+11}
<#assign b=true/>
<#--不能直接输出数字或者字符串以外的类型，否则都会报错，需要转换为字符串才能输出
	使用xxx?string可以完成对字符串的转换
-->
<#--${b}-->
${b?string}
${(a.d)???string}
<#--日期也不能直接输出，需要转换为字符串-->
${now?string("yyyy-MM-dd HH:mm:ss")}
<#--${now?string}没有为日期设定格式也会报错-->

<#--以下显示了使用字符链接和插值的方式连接字符串-->
${"hello"+username}

${"hello${username}"}

<#--字符串转换为日期
data用来转换日期，datatime用来转换日期和时间,time用来转换时间
-->
<#assign bir="1979-12-02 12:22:33"?date("yyyy-MM-dd HH:mm:ss")>
<#assign bir="1979-12-02 12:22:33"?datetime("yyyy-MM-dd HH:mm:ss")>
${bir}

${"<br/>"?html}

[${"abcde"?left_pad(10,"--")}]

${1.4?string(0)}
${1.8?int}
</body>
</html>