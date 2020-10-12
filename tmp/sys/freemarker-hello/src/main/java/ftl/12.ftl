<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<#import "/inc/select.ftl" as my/>
<@my.select id="address" datas=["北京","天津","上海"]/>
<@my.select id="sex" datas=["选择性别","男","女"] value="男"/>
<@my.select id="username" datas=users key="id" text="name" 
		headkey="-1" headtext="请选择用户"/>

<@my.select id="stu" datas=stus key="no" text="name" 
		headkey="-1" headtext="请选择学生"/>

<@my.select id="sex" datas={"0":"男","1":"女"} value="1"/>
</body>
</html>