<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 3/31/16
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <!--
        如果输入的age不能转化成数字,则ConvertionAction会在input result指向的页面中显示出错误信息

        默认的xhtml主题可以显示出错误信息,如果为simple主题,则需要通过s:fielderror fieldName="fieldName"来显示错误消息,
        或直接使用值栈中的fieldErrors属性 $ {fieldErrors.age[0] }

        如何修改此错误信息:
            在对应的action类所在包中新建ActionClassName.properties文件,添加如下key-value:
            invalid.fieldvalue.fieldname = my message

        如果使用了simple主题,且使用s:fielderror来显示错误消息，则会自动地加上ul li span标签,可以去除这些标签：
        在struts2-core包中有一个template包,simple下有各种模板,对应的fielderror.ftl,把对应的ul li span标签去除掉即可
        复制该文件放到src目录下的template.simple包后进行修改
    -->
    <s:debug></s:debug>
    <s:form action="convertion" method="post">
        <s:textfield name="age" label="Age"></s:textfield>
        <s:submit value="OK" />
    </s:form>

</body>
</html>
