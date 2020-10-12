<!--
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
-->
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>一函文化</title>
  <link rel="stylesheet" href="./layui/css/layui.css">
  <link rel="stylesheet" href="./layui/admin.css">
  <style>
    .main-card {
      margin: 0 auto;
      width: 400px;
      margin-top: 15px;
      box-shadow: 0 0 12px rgba(0, 0, 0, 0.2);
    }

    .layui-card-header {
      font-size: 18px;
      text-align: center;
    }
  </style>
</head>

<body class="layui-bg-gray">
<div class="layui-card main-card">
  <div class="layui-card-header layui-bg-green"> 管理员登录</div>
  <div class="layui-card-body">

    <form class="layui-form layui-form-pane" action="" id="login">
      <div class="layui-form-item">
        <label class="layui-form-label">账号</label>
        <div class="layui-input-block">
          <input type="text" name="username" autocomplete="off" placeholder="请输入账号" class="layui-input">
        </div>
      </div>

      <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-block">
          <input type="password" name="password" placeholder="请输入密码" autocomplete="off" class="layui-input">
        </div>
      </div>

      <div class="layui-form-item">
        <button class="layui-btn" style="width: 100%;" lay-submit lay-filter="formDemo">登录</button>
      </div>
    </form>


  </div>
</div>

<script src="./layui/layui.js"></script>
<script>
    //Demo
    layui.use('form', function () {
        var form = layui.form;
        var $ = layui.jquery;
        //监听提交

        form.on('submit(formDemo)', function (data) {
            // layer.msg(JSON.stringify(data.field));
            // alert($('#customer').serialize());
            $.ajax({
                url: '${pageContext.request.contextPath}/loginCheck.action',
                type: 'post',
                data: $('#login').serialize(),//表单的数据,相当于直接submit
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        layer.msg("登录成功");
                        window.location.href="login.action";
                    } else {
                        layer.msg("账号或密码错误");
                    }
                }
            });
            return false;
        });
    })
    ;
</script>

</body>

</html>
