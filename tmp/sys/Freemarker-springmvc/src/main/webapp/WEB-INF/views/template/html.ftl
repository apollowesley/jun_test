<#assign static_path>${request.getContextPath()}/static</#assign>

<#macro html js="">
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="${static_path}/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${static_path}/base.css">
    <script src="${static_path}/jquery/jquery.js"></script>
    <script src="${static_path}/bootstrap/js/bootstrap.js"></script>
</head>
<body>
	<#nested />
	<#if js!="">
		<#list js?split('||') as s>
        <script src="${static_path}/js/${s}" type="text/javascript"></script>
		</#list>
	</#if>
</body>
</html>
</#macro>