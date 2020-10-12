<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>${site.title}</title>
  <link rel="stylesheet" href="./layui/css/layui.css">
  <link rel="stylesheet" href="./layui/admin.css">
  <style>
    .main-card {
      margin-top: 15px;
      box-shadow: 0 0 12px rgba(0, 0, 0, 0.2);
    }

    .layui-carousel img {
      width: 100%;
      height: 100%;
    }

    .main-card-body {
      padding: 0;

    }

    .main-img {
      height: 160px; /* 图片高 */
      width: 284px;
    }

    .clear {
      clear: both
    }

    td {
      padding: 5px;
    }

  </style>
</head>

<body class="layui-bg-gray" id="body">
<div class="layui-layout layui-layout-admin">
  <jsp:include page="form/header.jsp"/>
  <div class="layui-bg-gray" style="min-height: 500px;">
    <div style="width: 1200px;margin: 0 auto;">
      <!-- 主框架 -->

      <!-- 轮播开始 -->
      <c:if test="${vo.type==null}"><%--and vo.name==null--%>
        <div class="layui-carousel layui-card main-card" id="test1" lay-filter="test1" style="height: 350px">
          <div carousel-item="">
            <c:forEach items="${turnimg}" var="item">
              <c:if test="${item.used==1}">
                <div>
                  <a target="_blank"
                     href="<c:if test="${item.href!=null}">${item.href}</c:if><c:if test="${item.href==null or item.href==''}">#</c:if>"><img
                    src="src/${item.src}"></a>
                </div>
              </c:if>
            </c:forEach>
          </div>
        </div>
      </c:if>
      <!-- 轮播结束 -->
      <c:if test="${vo.type!=null}">
        <div class="layui-card main-card" style="width: 1200px;height: 350px">
          <c:forEach items="${type}" var="t">
            <c:if test="${vo.type==t.id}">
              <img src="src/${t.src}" style="width: 100%;height: 100%">
            </c:if>
          </c:forEach>
        </div>
      </c:if>
      <div class="layui-card main-card" style="width: 1200px;min-height: 565px">
        <div class="layui-card-header" style="padding-left: 15px;padding-top: 10px;">

          <div class="">

            <form class="layui-form" action="index.action">
              <div class="layui-input-inline" style="display:flex;float: left;">
                <input value="${vo.name}" type="text" name="name" placeholder="请输入搜索关键词"
                       class="layui-input layui-bg-gray"
                       style="width: 400px;">
                <button type="submit" class="layui-btn layui-bg-cyan">立即搜索</button>
              </div>
            </form>
          </div>
          <div class="layui-card-body" style="float: right;">
            ${site.ad}
          </div>
          <div class="clear"></div>
        </div>
        <div class="layui-card-body main-card-body">
          <c:forEach items="${itemList}" var="item">
            <table border="0" style="margin: 10px;">
              <tr>
                <c:forEach items="${item}" var="i">
                  <td>
                    <a class="new-card" href="main.action?id=${i.id}" target="_blank">
                      <img class="main-img" src="src/${i.src}">
                      <div class="layui-card-header layui-bg-cyan text">${i.name}</div>
                        <%--                      <img class="main-img" src="src/${i.src}" style="width: ${i.width1}px;height: ${i.height1}px">--%>
                        <%--                      <div class="layui-card-header layui-bg-cyan text" style="width: ${i.width1-30}px">${i.name}</div>--%>
                    </a>
                  </td>
                </c:forEach>
              </tr>
            </table>
          </c:forEach>
          <div id="page-bar" style="margin-left: 20px;"></div>
          <!-- 分页标签 -->
        </div>
      </div>
    </div>

  </div>
  <jsp:include page="form/footer.jsp"/>

</div>

<script src="./layui/layui.js"></script>
<script>
    document.onreadystatechange = function () {
        if (document.readyState == "complete") {
            document.body.style.display = "block";
            if ('${vo.currentPage}' != '1') {
                window.scrollTo(0, document.body.scrollHeight);
            }
        } else {
            document.body.style.display = "none";
        }
    };
</script>
<script>
    //JavaScript代码区域
    layui.use('element', function () {
        var element = layui.element;

    });
</script>
<script>
    layui.use(['carousel', 'form'], function () {
        var carousel = layui.carousel,
            form = layui.form;

        //常规轮播
        carousel.render({
            elem: '#test1',
            arrow: 'always',
            width: '1200px',
            height: '350px',
            interval: 3000
        });
        //监听开关
        form.on('switch(autoplay)', function () {
            ins3.reload({
                autoplay: this.checked
            });
        });
    });
</script>

<script>
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage,
            layer = layui.layer;
        //完整功能
        laypage.render({
            limit:${vo.limitPage},
            elem: 'page-bar',
            count: ${vo.total},
            curr: ${vo.currentPage},
            layout: ['prev', 'page', 'next', 'count', 'skip'],
            jump: function (obj, first) {
                if (!first) {
                    window.location.href = "index.action?type=${vo.type}&name=${vo.name}&currentPage=" + obj.curr;
                }
            }
        });
    });

</script>


</body>

</html>
