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
<div class="layui-side layui-bg-black top50">
    <div class="layui-side-scroll navBar">
        <ul class="layui-nav layui-nav-tree" id="index-nav" lay-filter="index-nav">
        <#if _menus ?? >
            <#list _menus as first>
                <#if first.children ?? >
                    <#if (first.children?size > 0) >
                        <li class="layui-nav-item" name="_layui-nav-item">
                            <a href="javascript:void(0);">
                                <i class="layui-icon ${first.icon!''}" data-icon="${first.icon!''}"></i>
                                <span><cite>${first.title}</cite></span><span class="layui-nav-more"></span>
                            </a>
                            <dl class="layui-nav-child">
                                <#list (first.children) as second>
                                    <dd class=""><a href="javascript:void(0);" data-url="${second.href!''}">
                                        <i class="layui-icon ${second.icon!''}" data-icon="${second.icon!''}"></i>
                                        <span><cite>${second.title!''}</cite></span></a>
                                    </dd>
                                </#list>
                            </dl>
                        </li>
                    <#else>
                        <li class="layui-nav-item" name="_layui-nav-item">
                            <a href="javascript:void(0);" data-url="${first.href!''}">
                                <i class="layui-icon ${first.icon!''}" data-icon="${first.icon!''}"></i>
                                <span><cite>${first.title!''}</cite></span>
                            </a>
                        </li>
                    </#if>
                </#if>
            </#list>
        </#if>
        </ul>
    </div>
</div>
<!-- 右侧内容 -->
<div class="layui-body">
    <iframe id="_content"></iframe>
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
<script type="text/javascript" src="/assets/iartisan/js/single.index.js?${staticVerison!'1'}"></script>
<script type="text/javascript" src="/assets/iartisan/plugins/lib/cache.js"></script>
<script type="text/javascript" src="/assets/iartisan/js/head_nav.js?${staticVerison!'1'}"></script>
</body>

</html>