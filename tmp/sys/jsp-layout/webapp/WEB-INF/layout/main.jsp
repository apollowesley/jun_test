<html>
<head>
	<title>${page_title}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<style>
	h1 {font-size:16pt;border-bottom:2px solid #000;padding:0 0 10px 0px;}
	.button {border-top:1px solid #000;margin-top:20px;font-size:9pt;padding-top:5px;}
	.menu {width:180px;vertical-align:top;border-right:2px solid #666;}
	a {color:#00f;}
	ul li {text-align:left;}
	</style>
</head>
<body>
	<h1><span style='float:right;color:red'>(layout=main.jsp)</span>同步课堂后台 —— ${page_title}</h1>
	<table width="100%" border='0' cellpadding='0' cellspacing='0'>
	<tr>
		<td class='menu'>
			<jsp:include page='_menu.jsp'/>
		</td>
		<td align='center'>${screen_content}</td>
	</tr>
	</table>
	<div class='button'>Now is : <%=new java.util.Date()%></div>
</body>
</html>