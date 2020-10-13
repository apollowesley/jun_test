<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<html>
<head>
    <jsp:include page="inc/header.jsp" />
    <link href="//cdnjs.cloudflare.com/ajax/libs//simplemde/1.11.2/simplemde.min.css" rel="stylesheet">
    <link href="//cdnjs.cloudflare.com/ajax/libs//github-markdown-css/2.10.0/github-markdown.min.css" rel="stylesheet">
    <link href="//cdnjs.cloudflare.com/ajax/libs//highlight.js/9.12.0/styles/github.min.css" rel="stylesheet">
    <style>
        .CodeMirror, .CodeMirror-scroll {
            min-height: 130px;
            max-height: 200px;
        }
        .CodeMirror .cm-spell-error:not(.cm-url):not(.cm-comment):not(.cm-tag):not(.cm-word) {
            background: none;
        }
        .editor-statusbar {
            display: none;
        }
        .editor-preview {
            overflow-y: initial!important;
        }
        ul {
            list-style: none;
            margin-left: 0;
            padding-left: 0;
        }
    </style>
</head>
<body>
<!-- 顶部导航 -->
<jsp:include page="inc/nav.jsp" />
<div class="container custome-container">
    <nav class="breadcrumb">
        <a class="crumbs" title="" href="${basePath}index.do" data-toggle="tooltip" data-placement="bottom" data-original-title="返回首页"><i class="fa fa-home"></i>首页</a>
        <i class="fa fa-angle-right"></i>
        <a href="//www.zhyd.me/type/2" title="" data-toggle="tooltip" data-placement="bottom" data-original-title="点击查看该分类文章">后端技术</a>
        <i class="fa fa-angle-right"></i>正文
    </nav>
    <div class="row">
        <jsp:include page="inc/article.jsp" />
        <jsp:include page="inc/sidebar.jsp" />
    </div>
</div>
<!-- footer -->
<jsp:include page="inc/footer.jsp" />
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs//highlight.js/9.12.0/highlight.min.js"></script>
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs//simplemde/1.11.2/simplemde.min.js"></script>
</body>
</html>