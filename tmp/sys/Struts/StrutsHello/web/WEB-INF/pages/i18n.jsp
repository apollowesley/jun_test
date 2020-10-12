<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 4/8/16
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<s:form>

    <a href="/i18n/change.do?request_locale=zh_CN">中文</a>
    <a href="/i18n/change.do?request_locale=en_US">English</a>
    <s:text name="username" />
    <s:text name="time">
        <s:param name="0" value="11" />
        <s:param name="1" value="22" />
    </s:text>
    <!-- 使用key的方式引用国际化资源 -->
    <!-- 使用getText(String)的方法获取国际化资源,字符串中需要强制OGNL解析,因为此时的对象栈中有DefaultTextProvider对象，提供了访问国际化资源的getText方法 -->
    <s:textfield name="name" label="%{getText('username')}" />
    <s:textfield name="name" key="username"></s:textfield>
    <s:password name="password" key="password"></s:password>
    <s:submit key="submit"></s:submit>
</s:form>
</body>
</html>
