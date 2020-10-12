<%--

  Date: 12-12-4
  Time: 上午10:26
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
  <a href="/qqlogin/login"><img src="images/Connect_logo_1.png"/>QQ登录</a>
  <h3>
    <p>登陆原理:</p>
  </h3>
  <h5>
    <p>第一步: 请求QQ登陆地址:redirect:https://graph.qq.com/oauth2.0/authorize?client_id=101465199&redirect_uri=http://localhost:8080/afterlogin.do&response_type=code&state=cc86ad8159bf700ef45570777dc038ab&scope=get_user_info,add_topic,add_one_blog,add_album,upload_pic,list_album,add_share,check_page_fans,add_t,add_pic_t,del_t,get_repost_list,get_info,get_other_info,get_fanslist,get_idollist,add_idol,del_ido,get_tenpay_addr</p>
    <p>第二步:用户在客户端(PC端，手机端)点击授权按钮</p>
    <img src="images/qq-loginpage.png" style="height: 100px;width: 300px;"/>
    <p>第三步:腾讯统一用户认证中心，会去请求我们申请的回调地址:http://localhost:8080/afterlogin.do</p>
    <p>第四步：使用client_id,client_secret(这两个信息一定要保管好)..等去请求认证中心，认证中心返回access_token和refresh_token</p>
    <p>第五部：将上一步认证中心返回的access_token和refresh_token，再次请求请求认证中心，返回openid</p>
    <p>第六步:将第五步的openid和第四部的access_token再去请求用户信息接口，腾讯将返回用户信息给用户，此时已经QQ认证完毕，目的达成了。</p>
  </h5>
  </body>
</html>