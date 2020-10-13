<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>${_title!''}</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="favicon.ico">
    <link rel="stylesheet" href="/assets/iartisan/css/font/font.css" media="all"/>
    <link rel="stylesheet" href="/assets/iartisan/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="/assets/iartisan/css/index.css" media="all"/>
    <link rel="stylesheet" href="/assets/iartisan/css/layui-theme-custom.css" media="all"/>
</head>
<body class="layui-layout-body layui-layout-admin">
<!-- 头部 -->
<div class="layui-header">
    <#include "head_nav.ftl" />
</div>
<!-- 侧边栏 -->
<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll navBar">
        <ul class="layui-nav layui-nav-tree" id="index-nav" lay-filter="index-nav">
            <li class="layui-nav-item layui-this">
                <a href="javascript:;" data-url="${context.contextPath}/main">
                    <i class="layui-icon" data-icon=""></i>
                    <span>首页</span>
                </a>
            </li>
        </ul>
    </div>
</div>
<!-- 右侧内容 -->
<div class="layui-body">
    <div class="layui-tab mag0" lay-filter="bodyTab" id="top_tabs_box" style="margin: 0px;">
        <ul class="layui-tab-title top_tab" id="top_tabs">
            <li class="layui-this" lay-id=""><i class="layui-icon">&#xe68e;</i> <cite>首页</cite></li>
        </ul>
        <ul class="layui-nav closeBox">
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="layui-icon caozuo">&#xe643;</i>
                    <span style="margin-left: 6px;">页面操作</span></a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;" class="refresh refreshThis"><i class="layui-icon">&#x1002;</i>
                        刷新当前</a></dd>
                    <dd><a href="javascript:;" class="closePageOther"><i class="seraph icon-prohibit"></i> 关闭其他</a>
                    </dd>
                    <dd><a href="javascript:;" class="closePageAll"><i class="seraph icon-guanbi"></i> 关闭全部</a></dd>
                </dl>
            </li>
        </ul>
        <div class="layui-tab-content clildFrame">
            <div class="layui-tab-item layui-show">
                <iframe src="${context.contextPath!''}/main"></iframe>
            </div>
        </div>
    </div>
</div>
<div class="layui-footer footer" style="background-color: #f9f9f9;border-top: 1px solid #f9f9f9;">
    <p><span style="color: #bab0a0;">2019 &copy; powerd by <a
            href="https://gitee.com/iartisan/iartisan-admin-template" style="color: #01AAED;" target="_blank">iartisan-admin-template</a></span>
        <a href="javascript:void(0);" class="layui-btn layui-btn-sm  layui-btn-disabled" id="donation">捐赠作者</a>
        <a href="mailto:${_authorEmail!''}" class="layui-btn layui-btn-sm layui-btn-warm">给作者提意见</a>
    </p>
</div>
<!-- 手机屏幕遮罩层 -->
<div class="site-mobile-shade"></div>
<script type="text/javascript" src="/assets/iartisan/plugins/layui/layui.js"></script>
<script type="text/javascript" src="/assets/iartisan/js/index.js?${staticVerison!'1'}"></script>
<script type="text/javascript" src="/assets/iartisan/plugins/lib/cache.js"></script>
<script type="text/javascript" src="/assets/iartisan/js/head_nav.js?${staticVerison!'1'}"></script>
</body>

</html>