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
<!-- 主框架 -->
<div style="padding: 15px;">

  <div class="layui-col-md12">
    <div class="layui-card">
      <div class="layui-card-header">用户管理</div>
      <div class="layui-card-body">

        <table class="layui-table">

          <thead>
          <tr>
            <th>ID</th>
            <th>用户名</th>
            <th>密码</th>
            <th>超级管理员</th>
            <th>
                <button type="button" data-method="setTop" data-id="" class="layui-btn layui-btn-normal">添加用户</button>
            </th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${list}" var="user">
            <tr>
              <td>${user.id}</td>
              <td>${user.username}</td>
              <td>${user.password}</td>
              <td>
                <c:choose>
                  <c:when test="${user.admin==1}">是</c:when>
                  <c:otherwise>否</c:otherwise>
                </c:choose>
              </td>
              <td>
                <button data-method="setTop" data-id="${user.id}" class="layui-btn layui-btn-warm">修改</button>
                <button data-method="confirmTrans" data-id="${user.id}" class="layui-btn layui-btn-danger">删除</button>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
<!-- 主框架结束 -->
<script src="../layui/layui.js" charset="utf-8"></script>

<script>
    function refresh() {
        window.location.reload();
    }

    layui.use('layer', function () { //独立版的layer无需执行这一句
        var $ = layui.jquery,
        layer = layui.layer; //独立版的layer无需执行这一句

        //触发事件
        var active = {
            setTop: function (othis) {
                var id = othis.data('id');
                layer.open({
                    type: 2 //此处以iframe举例
                    ,
                    title: '添加/修改',
                    area: ['450px', '370px'],
                    maxmin: true,
                    content: '/userEdit.action?id=' + id
                });
            }
            , confirmTrans: function (othis) {
                var id = othis.data('id');
                layer.msg('您确认删除该用户吗?', {
                    btn: ['确认删除', '取消']
                    , yes: function () {
                        $.get("${pageContext.request.contextPath}/userDel.action", "id=" + id,function (obj) {
                            refresh();
                        });
                    }
                    , btn2: function () {
                        layer.closeAll();
                    }
                });
            }

        };

        $('.layui-btn').on('click', function () {
            var othis = $(this),
                method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });

    });
</script>


</body>

</html>
