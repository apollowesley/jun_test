<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="layui-header layui-bg-cyan">

  <div class="layui-header layui-bg-cyan" style="width: 1200px;margin:0 auto">
    <a href="index.action">
      <div class="layui-logo" style="color: white;font-size: 24px;text-align: left;width: auto;">
        <img src="src/${site.logo}" height="40px">
        ${site.title}
      </div>
    </a>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left" style="left: 240px">
      <li class="layui-nav-item <c:if test="${vo.type==null}">layui-this</c:if>"><a href="index.action">首页</a></li>
      <c:forEach items="${type}" var="item">
        <li class="layui-nav-item <c:if test="${vo.type==item.id}">layui-this</c:if>"><a
          href="index.action?type=${item.id}">${item.item}</a></li>
      </c:forEach>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item"><a href="aboutUs.action" target="_blank">关于我们</a></li>
      <li class="layui-nav-item"><a href="login.action" target="_blank">后台管理</a></li>
    </ul>
  </div>
</div>