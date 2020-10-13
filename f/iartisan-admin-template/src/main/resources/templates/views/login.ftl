<!DOCTYPE html>
<html class="loginHtml">
<head>
    <meta charset="utf-8">
    <title>登录--layui后台管理模板 2.0</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="/assets/iartisan/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="/assets/iartisan/css/login.css" media="all"/>
</head>
<body>
<div class="layadmin-user-login">
    <div class="layadmin-user-login-main">
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2>${_title!''}
                <#if _env?? && _env='demo' >
                    <i style="font-size: 20px;color: #FF5722;margin-left: 10px;"
                       class="layui-icon"
                       lay-tips="<span style='color:red;'>注意：</span>demo环境只是为了演示用<br>修改和删除操作做了限制，其它操作没有影响！！！"></i>
                </#if>
            </h2>
        </div>
        <form class="layui-form" autocomplete="off">
            <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon" for="userName">&#xe612;</label>
                    <input type="text" name="userName" id="userName" lay-verify="required" lay-verType="tips"
                           class="layui-input" autocomplete="off">
                </div>
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon" for="password">&#xe672;</label>
                    <input type="password" name="password" id="password" lay-verType="tips" lay-verify="required"
                           class="layui-input" autocomplete="off">
                </div>
                <#--<div class="layui-form-item">
                    <div class="layui-row">
                        <div class="layui-col-xs7">
                            <label class="layadmin-user-login-icon layui-icon"
                                   for="vercode">&#xe60d;</label>
                            <input type="text" name="vercode" id="vercode" lay-verType="tips"
                                   lay-verify="required" placeholder="图形验证码" class="layui-input">
                        </div>
                        <div class="layui-col-xs5">
                            <div style="margin-left: 10px;">
                                <img src="/captcha" style="width: 100%;" onclick="this.src=this.src">
                            </div>
                        </div>
                    </div>
                </div>-->
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-filter="login" lay-submit>登 录</button>

                </div>
            </div>

        </form>
    </div>
</div>
<script type="text/javascript" src="/assets/iartisan/plugins/layui/layui.js"></script>
<script type="text/javascript" src="/assets/iartisan/js/login.js?${staticVerison!'1'}"></script>
</body>
</html>