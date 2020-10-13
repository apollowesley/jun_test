# jfinal-layui-blog

将后端框架Jfinal和前端框架layui整合，形成一个轻量级博客.

整合实现了`代码生成工具`，实现了`Excel导入导出`，实现了`文件上传下载管理`和`富文本博客`.



### 前台界面：

![前端界面](https://gitee.com/uploads/images/2018/0104/215041_a3900bb9_496317.png "sp180104_212639.png")



### 后台界面：

![后台界面](https://gitee.com/uploads/images/2018/0119/114538_d43e8a6e_496317.png "sp180104_212803.png")



### 二次开发说明：

#### 1. 完成项目的初始化：

- 查阅`./doc`目录下jfinal项目的`"启动说明.txt"`
- 本项目数据库初始化文件是`jfinal_layui_blog.sql`，对应`blog.sql`
- 配置文件是`config.properies`，对应`a_little_config.txt`
- 进入`AppConfig`类，根据注释用Eclipse或IDEA方式执行`main()`快速启动项目



#### 2. 快速开发新功能：

- 在数据库中创建对应的业务表格
- 进入`_TppGenerator`类，修改`className`和`tableName`
- 执行`main()`方法，即可按统一的风格生成对应模块代码，并生成相应模块的前端界面文件
- 再根据具体业务需求修改具体实现. 可修改`src\main\resources\tpl`目录下模板，实现自定义代码




#### 3. 发现问题，解决问题，完善项目

- 有任何问题或想法，欢迎 [提交 issue](https://gitee.com/imzdx/jfinal-layui-demo/issues)

- 有任何改进或建议，欢迎 [提交 PR](https://gitee.com/imzdx/jfinal-layui-demo/pulls)




### 参考：

[jfinal](https://gitee.com/jfinal/jfinal): 后端 WEB + ORM 框架

[layui](https://gitee.com/sentsin/layuil): 前端 UI 框架

[jfinal-ext](https://gitee.com/zhouleib1412/jfinal-ext): 实现了 Excel 导入、导出

[JfGenerato](http://www.jfinal.com/share/381): 实现代码统一生成