# 项目名【企业门户-影视文化类】qiye
[功能介绍视频](https://mparticle.uc.cn/video.html?uc_param_str=frdnsnpfvecpntnwprdssskt&wm_aid=49e95f6ca0994074b239cb4deff4cbff)
[接口管理-功能介绍视频观看](https://mparticle.uc.cn/video.html?uc_param_str=frdnsnpfvecpntnwprdssskt&wm_aid=cac7ff66a8d94186a3d8207b33908d5d)
[在线预览web](http://cs.01film.cn/qiye/)

## 全部源码下载地址
```
三个分支:可分离部署web站点。也可以部署到一个站点
请根据tag 版本号下载 大版本号对应即可
如1.0.0——1.0.n都可以 
1站.web前端   https://gitee.com/wokaixin/qiye 
2站.api开发管理web端  https://gitee.com/wokaixin/vue-admin
3站.api接口php服务端  https://gitee.com/wokaixin/php-admin-api
```
uni-app 技术交流群：714566447

## 【安装】Project setup
```
npm install
```

### 【启动运行】Compiles and hot-reloads for development 
```
npm run serve
```

### 【打包编译】Compiles and minifies for production
```
npm run build
```


###【代码检测】 Lints and fixes files
```
npm run lint
```

### 【vue cli官方配置说明】Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

## 目录结构
```
┌─public            入口
│	│─favicon				网站角标
│	└─index			     	网站入口
│─src            网站代码编写目录
│	│─assets				 静态资源目录
│	│	│─images				图片库
│	│	│	└─logo		    	企业logo
│	│	└─logo					企业首页logo
│	│─common				 公共类js/css
│	│	└─yc.css		    自定义的可复用的公共css
│	│─components			可复用的页面组件目录
│	│	│─template			可复用的样式模板目录
│	│	│	│─adMax				大屏广告
│	│	│	│─news_detail		新闻详情样式模板
│	│	│	│─news_list			新闻列表样式
│	│	│	│─updateFile		上传图片组件 
│	│	│	└─***		    	其他
│	│	└─public			前台公共组件
│	│	    │─Footer			底部信息
│	│	    │─header			头部菜单
│	│	    └─***		    	其他
│	│─pages				 	网站主要页面入口目录
│	│	│─home				公共入口页面
│   │	│	│─detail			详情模板目录
│	│   │	│─index				入口模板目录
│	│   │	│─list		        列表模板目录
│	│   │	└─login		        登录注册入口
│	│	│─user				公共入口页面
│	│   │	│─index				板块入口
│	│   │	└─list		        登录注册入口
│	│	│─Header				后台公共头
│	│	│─index				    后台入口
│	│	│─sidebar				后台公共菜单
│	│	└─tags		            后台公共标签
│	│─store				 	vuex目录
│	│	│─index				vuex代码文件
│	│	└─***		    	其他
│	│──request						ajax服务器请求接口配置目录
│	│		 
│	│──App.vue						应用启动加载页面
│	│──config.js					 自定义配置参数
│	│──main.js						应用启动加载js
│	└──router.js					 路由配置
│
│─package.json            	npm依赖配置文件
│─vue.config.js            项目配置文件
│─cmd.bat            		快捷打开当前目录命令窗口
│─start.bat            		快捷启动运行网站
└──────────────────────────────────────────────────────────────
