<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    .text {
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2; /*文字占几行*/
      -webkit-box-orient: vertical;
      width: 300px;
    }

    .layui-btn-radius {
      width: 100%;
      margin-top: 10px;
    }
  </style>
</head>

<body>
<div class="layui-fluid" id="LAY-component-progress">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md9">
      <div class="layui-card">
        <div class="layui-card-header">内容详情</div>
        <div class="layui-card-body">


          <table class="layui-table">
            <thead>
            <tr>
              <th>ID</th>
              <th>类型</th>
              <th>内容1</th>
              <th>内容2</th>
              <th>权重</th>
              <th width="150px">
              </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${detail}" var="d">
              <tr>
                <td>${d.id}</td>
                <td>${d.type2}</td>
                <td><span class="text">${d.item1}</span></td>
                <td>
                  <c:choose>
                    <c:when test="${d.type1==3}">
                      <img src="src/${d.item1} " style="max-height: 80px" data-method="preview"
                           onclick="preview('src/${d.item1}')">
                    </c:when>
                    <c:otherwise>${d.item2}</c:otherwise>
                  </c:choose>
                </td>
                <td>${d.level}</td>
                <td>
                  <button data-method="setTop" data-id="${d.id}" class="layui-btn layui-btn-warm">修改
                  </button>
                  <button data-method="confirmTrans" data-id="${d.id}" class="layui-btn layui-btn-danger">删除
                  </button>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>

        </div>
      </div>
    </div>

    <div class="layui-col-md3">
      <div class="layui-card">
        <div class="layui-card-header">添加内容<a href="main.action?id=${itemid}" target="_blank" style="margin-left: 50px;color: #1E9FFF">预览</a></div>
        <div class="layui-card-body">

          <button type="button" data-method="setTop1" data-type="1"
                  class="layui-btn layui-btn-primary layui-btn-radius">标题元素
          </button>
          <br/>
          <button type="button" data-method="setTop1" data-type="2"
                  class="layui-btn layui-btn-primary layui-btn-radius">正文元素
          </button>
          <c:if test="${itemid!=-2}">
            <br/>
            <button type="button" data-method="setTop1" data-type="3"
                    class="layui-btn layui-btn-primary layui-btn-radius">图片元素
            </button>
            <br/>
            <button type="button" data-method="setTop1" data-type="4"
                    class="layui-btn layui-btn-primary layui-btn-radius">链接元素
            </button>
          </c:if>
        </div>
      </div>
    </div>


  </div>
</div>
<div style="clear: both"></div>


<script src="../layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
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
                    area: ['700px', '500px'],
                    maxmin: true,
                    content: '/detailEdit.action?itemid=${itemid}&id=' + id
                });

            },
            setTop1: function (othis) {
                var type = othis.data('type');
                var edit = layer.open({
                    type: 2 //此处以iframe举例
                    ,
                    title: '添加/修改',
                    area: ['700px', '500px'],
                    maxmin: true,
                    content: '/detailEdit.action?itemid=${itemid}&type=' + type
                });

            }
            , confirmTrans: function (othis) {
                var id = othis.data('id');
                layer.msg('您确认删除吗?', {
                    btn: ['确认删除', '取消']
                    , yes: function () {
                        $.get("${pageContext.request.contextPath}/detailDel.action", "id=" + id, function (obj) {
                            refresh();
                        });
                    }
                    , btn2: function () {
                        layer.closeAll();
                    }
                });
            },
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