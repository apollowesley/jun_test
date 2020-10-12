<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <title>layui</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="../layui/css/layui.css" media="all">
  <link rel="stylesheet" href="../layui/css/admin.css" media="all">
  <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>

<body>
<div class="layui-fluid" id="LAY-component-progress">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md12">
      <div class="layui-card">
        <div class="layui-card-body">
          <fieldset class="layui-elem-field layui-field-title">
            <legend>
              <c:choose>
                <c:when test="${user.id==null}">添加用户</c:when>
                <c:otherwise>修改用户</c:otherwise>
              </c:choose>
            </legend>
          </fieldset>
          <form class="layui-form" action="" id="user">
            <div class="layui-form-item">
              <label class="layui-form-label">ID</label>
              <div class="layui-input-block">
                <input readonly="true" type="text" value="${user.id}" name="id" class="layui-input layui-bg-gray">
              </div>
            </div>
            <div class="layui-form-item">
              <label class="layui-form-label">用户名</label>
              <div class="layui-input-block">
                <input type="text" value="${user.username}" name="username" required lay-verify="required"
                       autocomplete="off" class="layui-input">
              </div>
            </div>


            <div class="layui-form-item">
              <label class="layui-form-label">密码</label>
              <div class="layui-input-block">
                <input type="text" value="${user.password}" name="password" required lay-verify="required"
                       autocomplete="off" class="layui-input">
              </div>
            </div>

            <div class="layui-form-item">
              <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="formDemo">提交</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="../layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use('form', function () {
        var form = layui.form;
        var $ = layui.jquery;

        form.on('submit(formDemo)', function (data) {
            $.ajax({
                url: '/userWrite.action',
                type: 'post',
                data: $('#user').serialize(),//表单的数据,相当于直接submit
                dataType: 'json',
                success: function (data) {
                    layer.msg(data.msg);
                    if (data.success) {
                        var parentWin = window.parent;
                        parentWin.refresh();
                    }
                }
            });
            return false;
        });
    })
</script>
</body>

</html>