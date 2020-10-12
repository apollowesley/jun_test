<!doctype html>
<#assign basePath="${request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-store">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title><@title/></title>
    <!-- BOOTSTRAP STYLES-->
    <link href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet"/>
    <!-- FONTAWESOME STYLES-->
    <link href="http://cdn.bootcss.com/font-awesome/4.3.0/css/font-awesome.min.css"
          rel="stylesheet"/>
    <link rel="stylesheet" href="${basePath}/assets/css/custom.css"/>

</head>
<body>

<#assign menus=[
{"url": "/index", "id": "index", "label": "记录", "class": "fa fa-calendar fa-2x"},
{"url": "/setting", "id": "setting", "label": "设置", "class": "fa fa-cog fa-2x"}
]/>

<div id="wrapper" class="container">

    <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target=".sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div style="color: white; padding: 15px 50px 5px 50px; float: right; font-size: 16px;">
            <a href="" class="btn btn-danger square-btn-adjust">
                退出
            </a>
        </div>
    </nav>
    <!-- /. NAV TOP  -->
    <nav class="navbar-default navbar-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="main-menu">
                <li class="text-center">
                </li>
            <#list menus as m>
                <li>
                    <a href="${m.url}" <#if m.id == active_page!"">class="active-menu"</#if> >
                        <i class="${m.class}"></i>${m.label}
                    </a>
                </li>
            </#list>
            </ul>

        </div>
    </nav>
    <!-- /. NAV SIDE  -->

    <div id="page-wrapper">
        <div id="page-inner">

        <@body/>

        </div>
        <!-- /. PAGE INNER  -->
    </div>
    <!-- /. PAGE WRAPPER  -->
</div>
<!-- /. WRAPPER  -->

<!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
<!-- JQUERY SCRIPTS -->
<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
<!-- BOOTSTRAP SCRIPTS -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<!-- METISMENU SCRIPTS -->
<script src="http://cdn.bootcss.com/metisMenu/2.0.2/metisMenu.min.js"></script>
<script src="${basePath}/assets/js/custom.js"></script>
</body>
</html>