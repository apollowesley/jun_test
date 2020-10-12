<html>
<body>
<h2>Show User Info</h2>
<#list userList as user>
name: ${user.userName} 
<br>
age: ${user.age}
<br>
</#list>
</body>
</html>
