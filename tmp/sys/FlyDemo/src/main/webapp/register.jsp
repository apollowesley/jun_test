<%-- User: CDHONG.IT Date: 2019/8/21  --%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath %>"/>
    <title>注册界面</title>
    <link rel="stylesheet" href="commons/layui/css/layui.css" />
    <link rel="stylesheet" href="style/global.css" />
    <link rel="stylesheet" href="style/register.css" />
    <script src="commons/layui/layui.js" ></script>
</head>
<body>
   <div class="layui-container fly-container">
       <form class="layui-form layui-form-pane" action="">
           <div class="layui-form-item">
               <label class="layui-form-label">手机</label>
               <div class="layui-input-inline">
                   <input type="number" name="tel" required lay-verify="required" autocomplete="off" class="layui-input tel">
               </div>
           </div>
           <div class="layui-form-item">
               <label class="layui-form-label">图形码</label>
               <div class="layui-input-inline">
                   <input type="text" name="vimCode" required lay-verify="required" autocomplete="off" class="layui-input vifiyCode">
               </div>
               <div class="layui-form-mid layui-word-aux">
                   <img src="verifyCodeImg" class="vimCode" alt="验证码" />
               </div>
           </div>
           <div class="layui-form-item">
               <label class="layui-form-label">验证码</label>
               <div class="layui-input-inline">
                   <input type="text" name="telCode" required lay-verify="required" placeholder="请输入手机短信验证码" autocomplete="off" class="layui-input">
               </div>
               <div class="layui-form-mid layui-word-aux">
                   <button type="button" class="layui-btn layui-btn-normal tel-btn">获取验证码</button>
               </div>
           </div>
           <div class="layui-form-item">
               <label class="layui-form-label">昵称</label>
               <div class="layui-input-inline">
                   <input type="text" name="nickName" required lay-verify="required" autocomplete="off" class="layui-input">
               </div>
               <div class="layui-form-mid layui-word-aux">你的社区的名字</div>
           </div>
           <div class="layui-form-item">
               <label class="layui-form-label">密码</label>
               <div class="layui-input-inline">
                   <input type="password" name="pwd" required lay-verify="required" autocomplete="off" class="layui-input">
               </div>
               <div class="layui-form-mid layui-word-aux">6到16个字符</div>
           </div>
           <div class="layui-form-item">
               <label class="layui-form-label">确认密码</label>
               <div class="layui-input-inline">
                   <input type="password" name="pwd2" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
               </div>
           </div>
           <div class="layui-form-item">
               <div class="layui-input-block">
                   <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                   <button type="reset" class="layui-btn layui-btn-primary">重置</button>
               </div>
           </div>
       </form>
   </div>

    <script src="js/register.js"></script>

</body>
</html>
