<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<#macro showArt cid titleNum>
	<#nested>
	<#local articles=arts[cid]/>
	<#list articles as art>
		<li><span><a href="">
		<#if (art.title?length>titleNum)>
			${art.title[0..titleNum]}...
		<#else>
			${art.title}
		</#if>
		</span></a>
		</li>
	</#list>
</#macro>

<@showArt cid="1" titleNum=7>
	<h1>水水水水水水</h1>
</@showArt>


<@showArt cid="2" titleNum=10>
	<h1>活活后发货后会</h1>
</@showArt>
</body>
</html>