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

<body class="layui-bg-gray">



<script src="../layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use('layer', function () { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句

        layer.open({
            type: 1
            ,
            title: false //不显示标题栏
            ,
            closeBtn: false
            ,
            area: '300px;'
            ,
            shade: 0.8
            ,
            btn: ['前往首页','我知道了']
            ,
            btnAlign: 'c'
            ,
            moveType: 1 //拖拽模式，0或者1
            ,
            content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; ' +
                'color: #fff; font-weight: 300;">${msg}</div>'
            ,success: function(layero){
                var btn = layero.find('.layui-layer-btn');
                btn.find('.layui-layer-btn0').attr({
                    href: 'index.action'
                    // ,target: '_blank'
                });
            }
        });


    });
</script>


</body>

</html>
