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

    td {
      padding: 5px;
    }

    .layui-card-header {
      font-size: 18px;
      text-align: center;
    }

    .text {
      /*margin: 10px 0;*/
      font-size: 16px;
      line-height: 28px;
      padding: 10px;
    }
    .main-img{
      margin: 10px 0;
    }
    .layui-field-title{
      color: black;
    }
  </style>
</head>

<body class="layui-bg-gray" id="body">
<div class="layui-layout layui-layout-admin">
  <jsp:include page="form/header.jsp"/>
  <div class="layui-bg-gray" style="min-height: 500px;">
    <div style="width: 1200px;margin: 0 auto;">
      <!-- 主框架 -->
      <table border="0" width="100%">
        <tr>
          <td width="70%" valign="top">
            <div class="layui-card main-card">
              <div class="layui-card-header layui-bg-cyan">${item.name}</div>
              <div class="layui-card-body">
                <c:forEach items="${detail}" var="d">
                  <c:choose>
                    <c:when test="${d.type1==1}">
                      <fieldset class="layui-elem-field layui-field-title">
                        <legend>${d.item1}</legend>
                      </fieldset>
                    </c:when>
                    <c:when test="${d.type1==2}">
                      <div class="text"> ${d.item1}</div>
                    </c:when>
                    <c:when test="${d.type1==3}">
                      <img class="main-img" src="src/${d.item1}" width="100%">
                    </c:when>
                    <c:when test="${d.type1==4}">
                      <a href="${d.item2}" target="_blank">
                        <div class="text" style="color: #1E9FFF">
                            ${d.item1}
                        </div>
                      </a>
                    </c:when>
                  </c:choose>
                </c:forEach>


              </div>
            </div>
          </td>
          <td width="29%" valign="top">
            <div class="layui-card main-card">
              <div class="layui-card-header layui-bg-cyan"> 项目信息</div>
              <div class="layui-card-body">
                <img class="main-img" src="src/${item.src}" style="margin: 15px 0px;width: 100%">

                <ul class="layui-timeline">
                  <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis"></i>
                    <div class="layui-timeline-content layui-text">
                      <h3 class="layui-timeline-title">设计分类</h3>
                      <p>${item.type1}</p>
                    </div>
                  </li>

                  <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis"></i>
                    <div class="layui-timeline-content layui-text">
                      <h3 class="layui-timeline-title">发布日期</h3>
                      <p>${item.date}</p>
                    </div>
                  </li>

                  <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis"></i>
                    <div class="layui-timeline-content layui-text">
                      <h3 class="layui-timeline-title">作者</h3>
                      <p>${item.author}</p>
                    </div>
                  </li>

                  <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis"></i>
                    <div class="layui-timeline-content layui-text">
                      <h3 class="layui-timeline-title">简单介绍</h3>
                      <p>${item.text}</p>
                    </div>
                  </li>

                </ul>

              </div>
            </div>
          </td>
        </tr>

      </table>

    </div>
  </div>

</div>
<jsp:include page="form/footer.jsp"/>

</div>

<script src="./layui/layui.js"></script>

<script>
    //JavaScript代码区域
    layui.use('element', function () {
        var element = layui.element;

    });
</script>


</body>

</html>
