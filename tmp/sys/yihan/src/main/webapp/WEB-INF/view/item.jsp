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
    img {
      max-height: 60px;
      /*  max-width: 150px;*/
    }

    .text {
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2; /*文字占几行*/
      -webkit-box-orient: vertical;
      width: 300px;
    }
  </style>
</head>

<body>
<!-- 主框架 -->
<div style="padding: 15px;">
  <div class="layui-col-md12">
    <div class="layui-card">
      <div class="layui-card-header">选择分类</div>
      <div class="layui-card-body">
        <a href="item.action">
          <button type="button" class="layui-btn layui-btn-sm layui-bg-black">全部分类</button>
        </a>
        <c:forEach items="${type}" var="i">
          <a href="item.action?typeId=${i.id}">
            <button type="button" class="layui-btn layui-btn-sm">${i.item}</button>
          </a>
        </c:forEach>

      </div>
    </div>
  </div>
</div>
<div style="clear: both"></div>
<div style="padding: 15px;">

  <div class="layui-col-md12">
    <div class="layui-card">
      <div class="layui-card-header">作品管理</div>
      <div class="layui-card-body">

        <table class="layui-table">

          <thead>
          <tr>
            <th>ID</th>
            <th>缩略图<br/><span style="font-size: 10px">(点击查看大图)</span></th>
            <th>名称<br/><span style="font-size: 10px">(点击查看详情页)</span></th>
            <th>分类</th>
            <th>基本信息</th>
            <th>权重</th>
            <th min-width="120px">
              <button type="button" data-id="${i.id}" data-method="setTop" style="width: 98px"
                      class="layui-btn layui-btn-normal">添加作品
              </button>
            </th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${item}" var="i">
            <tr>
              <td>${i.id}</td>
              <td>
                <img src="src/${i.src} " data-method="preview" data-src="src/${i.src}"
                     onclick="preview('src/${i.src}')">
              </td>
              <td><a href="main.action?id=${i.id}" target="_blank">${i.name}</a></td>
              <td>${i.type1}</td>
              <td>
                <div><strong>作者:</strong> ${i.author}</div>
                <div><strong>时间:</strong> ${i.date}</div>
                <div class="text">
                  <strong>简介:</strong> ${i.text}</div>

              </td>
              <td>${i.level} </td>
              <td>
                <button data-method="setTop" data-id="${i.id}" class="layui-btn layui-btn-warm layui-btn-sm">修改</button>
                <button data-method="confirmTrans" data-id="${i.id}" class="layui-btn layui-btn-danger layui-btn-sm"
                        style="margin-left: 0">删除
                </button>
                <br/>
                <button data-method="edit" data-id="${i.id}" class="layui-btn layui-btn-primary layui-btn-sm"
                        style="margin-top:10px;width: 98px">编辑内容
                </button>

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

    function preview(src) {
        var preview = layer.open({
            type: 2,
            title: '查看',
            maxmin: true,
            content: '/preview.action?src=' + src
        });
        layer.full(preview);
    }

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
                    area: ['800px', '600px'],
                    maxmin: true,
                    content: '/itemEdit.action?id=' + id
                });
                layer.full(edit);
            }
            , confirmTrans: function (othis) {
                var id = othis.data('id');
                layer.msg('您确认删除吗?', {
                    btn: ['确认删除', '取消']
                    , yes: function () {
                        $.get("${pageContext.request.contextPath}/itemDel.action", "id=" + id, function (obj) {
                            refresh();
                        });
                    }
                    , btn2: function () {
                        layer.closeAll();
                    }
                });
            },

            edit: function (othis) {
                var id = othis.data('id');
                var edit = layer.open({
                    type: 2 //此处以iframe举例
                    ,
                    title: '添加/修改',
                    area: ['800px', '600px'],
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