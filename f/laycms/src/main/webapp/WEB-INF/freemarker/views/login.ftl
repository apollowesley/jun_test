<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>laycms-后台管理系统</title>
	<style>
	
	/* CSS Document */
	*{margin:0;padding:0;}
	.lt{float:left;}
	.rt{float:right;}
	
	.login_box{margin:100px auto 0px auto;width:800px; height:300px;padding-top:100px;border:solid 1px #eee;background:#fdfdfd;}
	.login_box table{margin:0px auto}
	.login_box table th{padding:10px;font-weight:normal;font-size:14px;}   
	.login_box table td{padding:10px;}  
	
	.btn-sub{padding:6px 30px;border:solid 1px #f40;background:#f40;color:#fff;margin-right:25px;cursor:pointer}
	.btn-reset{padding:6px 33px;border:solid 1px #f40;color:#f40;cursor:pointer;background:#fff}
	
	.input-text{padding:10px;width:200px;border:solid 1px #eee;}
	span{color:red;font-size:12px;}
	
	</style>
</head>
<body>


<div class="login_box">

   <form action="login.do" method="post">
   
	   <table>
	       <tr>
	           <th>用户名</th>
	           <td><input name="username" type="text" value="" class="input-text"/></td>
	       </tr>
	       <tr>
	           <th>密&nbsp;&nbsp;&nbsp;&nbsp;码</th>
	           <td><input name="password" type="password" value="" class="input-text"/></td>
	       </tr>
	       <tr>
	           <th></th>
	           <td>
		           <input type="submit" value="登录" class="btn-sub" /> 
	           </td>
	       </tr>
	       <tr>
	           <th></th>
	           <td><span>${errMsg!}</span></td>
	       </tr>
	   </table>
   
   </form>
   
</div>

</body>
</html>