<%--
  Created by KiWiPeach.
  User: liuburu
  DateTime: 2018/3/30 18:02
  Description: 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>QQ登陆成功页面</title>
</head>
<body style="text-align: center">
<div>
    <table>
        <tr>
            <td>昵称</td>
            <td>${requestScope.userInfoBean.nickname}</td>
        </tr>
        <tr>
            <td>头像</td>
            <td><image src="${requestScope.userInfoBean.avatar.avatarURL50}"></image></td>
        </tr>
            <td>性别</td>
            <td>${requestScope.userInfoBean.gender}</td>
        </tr>
        <tr>
            <td>accessToken</td>
            <td>${sessionScope.accessToken}</td>
        </tr>
        <tr>
            <td>openid</td>
            <td>${sessionScope.openid}</td>
        </tr>
        <tr>
            <td>province</td>
            <td>${sessionScope.userInfoBean.province}</td>
        </tr>
        <tr>
            <td>city</td>
            <td>${sessionScope.userInfoBean.city}</td>
        </tr>
       <tr>
            <td>year</td>
            <td>${sessionScope.userInfoBean.year}</td>
        </tr>

    </table>
</div>
<div>
    <a href="/qqlogin/out">注销当前用户</a>
    <button>获取Qzone的昵称</button>
    <button>是否为认证空间的粉丝</button>
    <button>微博的昵称等信息</button>
</div>

</body>
</html>
