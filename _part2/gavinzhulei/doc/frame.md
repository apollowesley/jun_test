### 项目搭建

1. 根目录下创建项目名称（demo）
2. 创建项目入口-index.html
    
    ```
	<!DOCTYPE html>
    <!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
    <!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
    <!--[if !IE]><!-->
    <html lang="en" class="no-js">
    <!--<![endif]-->
    <head>
    <meta charset="utf-8"/>
    <title>Demo</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Pragma" content="no-cache">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    
    <link href="../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
    <link href="../assets/global/css/components-md.css" id="style_components" rel="stylesheet" type="text/css"/>
    <link href="../assets/global/css/plugins-md.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/admin/layout2/css/layout.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/admin/layout2/css/themes/blue.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link href="../assets/admin/layout2/css/custom.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
    
    <link rel="shortcut icon" href="favicon.ico"/>
    </head>
    <body class="page-md page-container-bg-solid page-sidebar-closed-hide-logo page-header-fixed page-sidebar-fixed page-footer-fixed">
    <div id="app-main">
    
    </div>
    
    <script src="../assets/global/plugins/jquery.min.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../node_modules/jquery.cookie/jquery.cookie.js"></script>
    <script src="../assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
    <script src="../assets/global/scripts/metronic.js" type="text/javascript"></script>
    <script src="../assets/admin/layout2/scripts/layout.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
    <script src="../assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
    <script src="../assets/admin/layout2/scripts/quick-sidebar.js" type="text/javascript"></script>
    </body>
    </html>
    ```
    
3. 项目开发目录如下

    - dev   *开发主目录*
        - index.js      *js入口文件*
        - config.js     *参数配置文件*
        - route.config.js   *路由配置文件*
        - frame.vue     *框架页*
        - page      *页面模块*
            - home.vue *首页*

    index.js

    ```
    import Vue from 'vue'
    import Router from 'vue-router'
    import Frame from './frame.vue'
    
    import RouteConfig from './route.config'
    
    /*注1*/
    require('../../src/style/comm.scss')
    
    Vue.use(Router)
    
    const router = window.router = new Router();
    
    router.map(RouteConfig)
    
    router.redirect({
    '*': '/home'
    })
    
    Vue.config.debug = true;
    
    router.start(Frame, '#app-main');
    ```
    
    > 注1：引入项目所需样式，根据具体项目所用到的样式引入对应的样式文件
    
    frame.vue  【可直接参考demo项目】
    
    ```
    <template>
        ...
        <router-view></router-view>
        ...
    </template>
    
    <script>
    
    export default{
    	...
        ready(){
            //注2
        }
    }	
    </script>
    
    <style lang="sass">
    .page-header.navbar .page-logo .logo-default{
        margin: 10px 0;
        height: 48px;
    }
    </style>
    ```

    > 注2：获取左侧菜单数据，请参考 [组件/Navbar](#!/doc/navbar) 目录中关于菜单导航组件数据结构，有详细的描述
    
    route.config.js  -- 路由配置，SPA（单页面应用）中对不同页面模块的控制
    
    ```
    import Home from './page/home.vue'
    
    export default {
        '/home':{
            name: 'home',
            component: Home
        }
    }
    ```

4. 在项目根目录下 webpack.config.js 文件中添加该项目编译解析入口
    
    ```
    entry:{
            'demo.index': './demo/dev/index.js'
        }
    ```

5. 启动命令行工具，在根目录下运行下面命令，代码将在你修改源码后实时编译到 bundle 目录中

    ```
    webpack --watch
    ```
    
6. 在index.html添加 bundle/demo.index.js 的引用


**至此我们就可以添加页面了，请参考 [第一个页面](#!/doc/home)**