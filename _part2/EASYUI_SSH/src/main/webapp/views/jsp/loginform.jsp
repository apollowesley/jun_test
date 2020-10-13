<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#loginSubmit").click(function(){
			debugger;
            var params = $("#loginform").serializeArray();
			debugger;
			$.ajax({
                type:"POST",
                url:"${pageContext.request.contextPath}/getUser",
                data:params,
                success:function(data){
                    alert("成功");
                },
                error:function(e) {
                    alert("出错："+e);
                }
		});
	});
</script>
</head>
<body>
	
	<form id='loginform'>
	     <input type="text" name="userName"/>
	     <input type="text" name="password"/>
	     <input id='loginSubmit' type="submit" value="提交"/>
	</form>

</body>
</html>