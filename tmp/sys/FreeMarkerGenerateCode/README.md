# FreeMarkerGenerateCode

#### 介绍
    这是一个IDEA的插件，功能是通过FreeMarker 自动生成代码。
    Init初始化项目，初始化后会在项目根目录生成FreeMarker文件夹。
    FreeMarker 下需要自定义文件模版，模版文件的文件名称中{}会被替换成模版名称。例如{}Service.java 文件会被替换成XxxService.java
    FreeMarker 内置变量${modelName} 该变量的值为模版名称
    通过项目视图: 右键菜单-> Generate Code  生成代码，安装提示输入模版名称。会在右键选中的文件夹下生成对应的模版文件。
    模版文件夹下支持文件夹。递归生成。
#### 软件架构
软件架构说明


#### 安装教程

1. 访问发行页面：https://gitee.com/fzh-oschina/FreeMarkerGenerateCode/releases/
2. 下载最新版本的插件包：FreeMarkerGenerateCode.zip
3. 打开idea -> file -> settings -> plugins -> 设置图标（一个齿轮图片） -> install Plugin from Disk 
3. 选中下载好的FreeMarkerGenerateCode.zip
4. 重启idea

#### 使用说明

1. 在idea 项目视图中右键菜单点击FreeMarker Generate Code Init
2. 找到项目根目录下的FreeMarker文件夹
3. 在FreeMarker文件夹中编辑模版文件
4. 在项目视图中想要生成代码的位置右键菜单点击FreeMarker Generate Code
5. 输入模块名称。

![输入图片说明](https://fengzhihao.xyz/r/video/FreeMarkerGenerateCode.mp4 "在这里输入图片标题")

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)