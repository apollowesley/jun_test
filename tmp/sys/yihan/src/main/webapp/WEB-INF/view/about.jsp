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
  <style>
    .layui-btn {
      width: 100%;
      font-size: 20px;
    }
  </style>
</head>

<body>
<div class="layui-fluid" id="LAY-component-progress">
  <div class="layui-row">
    <div class="layui-col-xs9">
      <button type="button" data-method="setTop" data-id="-1" class="layui-btn layui-btn-primary layui-btn-lg"
              style="height: 600px;">
        左侧项目编辑
      </button>
    </div>
    <div class="layui-col-xs3">
      <button type="button" data-method="setTop" data-id="-2" class="layui-btn layui-btn-primary layui-btn-lg"
              style="height: 300px;">右侧项目编辑
      </button>
    </div>
  </div>
</div>

<script src="../layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use('layer', function () { //独立版的layer无需执行这一句
        var $ = layui.jquery,
            layer = layui.layer; //独立版的layer无需执行这一句

        var active = {
            setTop: function (othis) {
                var id = othis.data('id');
                var edit = layer.open({
                    type: 2 //此处以iframe举例
                    ,
                    title: '添加/修改',
                    maxmin: true,
                    content: '/detail.action?id=' + id
                });
                layer.full(edit);
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