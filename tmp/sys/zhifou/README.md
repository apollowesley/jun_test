## 说明

>项目名称：知否
>版本 1.0.1
>作者：yichen
>邮箱：2782268022@qq.com
>技术交流qq群：714566447
>功能说明：实时通讯，添加删除好友，用户登录注册，个人中心，修改个人资料。添加、删除收货地址，修改默认收货地址；新闻列表，新闻详情，发布新闻；
>兼容支持  (已经测试通过：anderd， 微信小程序 ，h5)
>源码地址：https://gitee.com/wokaixin/zhifou.git
>anderd apk等其他终端测试地址：链接：https://pan.baidu.com/s/1mMebvYoQ7VLiwfIvyaYIRA 
>提取码：psjm  需打开百度网盘
>h5 ![临时测试地址](http://zf.cs.01film.cn)

## 测试方式
>测试方式，用俩个手机分布注册两个账号互相发送消息。
>或者一个手机注册多个账号，发送多个信息后。退出登录另一个账号接收消息。
![avatar](http://zf.cs.01film.cn/img/demo.jpg)
## 目录结构
```
┌─components            uni-app组件目录
│	│─pages				 可复用的页面目录
│	│	└─ 
│	│─crop			     裁剪图片
│	│	└─crop				裁剪图片
│	│─mpvue-citypicker   弹出城市选择器
│	│	│─mpvueCityPicker	    弹出选择器
│	│	└─city-data				地区数据
│	│		│─area                  省-市-区
│	│		│─city                  省-市
│	│	    └─province		        省
│	└─template			 可复用的模板组件目录
│		│─drawer			抽屉模板目录
│		│	│─bottom        底部弹出商品选择
│		│	└─drawer		抽屉模板
│		│─im-chat			聊天对话模板
│		│	│─chatinput		输入模板
│		│	│─orderMessage  新订单消息模板
│		│	└─messageshow	消息内容模板
│		│─nav				导航模板目录
│		│	└─bottom		底部导航
│		│─network	            网络状态
│		│	└─socketConnect     网络断开连接操作蒙版
│		│─verify	            验证
│		│	└─captchaInput      验证码
├─common				可复用公共工具插件类
│	│─css				公共css 目录
│	│	│─iconfont.css	项目需要的iconfont样式
│	│	└─tui.css			项目自定义的全局css样式
│	│─city.data.js		城市数据
│	│─uni.css			uni-app官方默认css
│	│─uni.js			uni-app官方默认js
│	└─utils				公共js工具插件目录
│		│─Base64.js		base64编码转换工具
│		│─Id.js		    id生成
│		│─Img.js		图片处理
│		│─index.js		入口
│		│─Json.js		json处理转换
│		│─Md5.js			Md5编码转换工具
│		│─Obj.js			数组对象处理
│		│─Storage.js		Storage缓存工具
│		│─Time.js			时间格式转换处理工具
│		│─Url.js			Url地址处理工具
│		└─Validate.js		input输入验证器
│     
├─request               AJAX请求封装
│	├─data				模拟请求所需的数据目录，实际开发中，请删除。
│	│	│─xxx.js			模拟数据
│	│	└─xxx.js			…………
│	│─api.js			api接口
│	└─index.js			AJAX请求封装
│     
├─hybrid                存放本地网页的目录，解决小程序只支持网络目录,[详见](https://uniapp.dcloud.io/component/web-view)
│	└─html
│		├─css
│		│	└─xxx.css
│		├─img
│		│	└─icon.png
│		├─js
│		│	└─xxx.js
│		└─local.html
│    
├─store     vuex
│	├─chat              vuex的chat通讯功能模块
│	│	│─actions.js          
│	│	│—getters.js
│	│	│—mutations.js
│	│	│—state.js	    
│	│	└─index.vue       index入口
│	│─user				用户模块
│	│   └─index				 
│	│─win				window窗口模块
│	│   └─index	
│	│─index.js	            vuex引入入口
│	│─state.js              vuex-state默认状态
│ 
├─platforms             存放各平台专用页面的目录，[详见](https://uniapp.dcloud.io/platform)
│     
├─pages                 业务页面文件存放的目录
│	│─chat				消息对话
│	│	│─chat.vue          消息对话
│	│	│—list.vue		    消息列表
│	│	└─order.vue         订单消息
│	│─friend				好友
│	│	│─add.vue          添加好友
│	│	└─index.vue         我的好友
│	│─login				登陆目录
│	│	│—login.vue		    登陆
│	│	│—pwd.vue		    密码找回
│	│	└─reg.vue		    注册页
│	│─main			    首页
│	│	└─main.vue	    首页
│	│─order			    订单目录
│	│	└─detail.vue	    订单详情
│	│─user				用户目录
│	│	│—address.vue		    地址页
│	│	│—info.vue		        个人信息页
│	│	│—security.vue		    设置
│	│	│—update.vue		    信息修改
│	│	└─wode.vue              我的首页
│     
├─static                存放应用引用静态资源（如图片、视频等）的地方，注意：静态资源只能存放于此
│	├─img				图片目录
│	│	└─xxx.jpg			图片
│	│─audio				媒体目录
│	│	└─xxx.mp3    		音频
│	└─font				公共字体
│     
├─main.js               Vue初始化入口文件
│     
├─App.vue               应用配置，用来配置App全局样式以及监听 应用生命周期
│     
├─manifest.json         配置应用名称、appid、logo、版本等打包信息，详见
│     
└─pages.json            配置页面路由、导航条、选项卡等页面类信息，详见
```

