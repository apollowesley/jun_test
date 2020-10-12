# 设计作品展示网站

#### 介绍
适合作为展示类公司网站，能够分类分页展示作品、作品详情。 网站后台能够添加修改删除：作品、分类、轮播图、关于、广告、管理员等，支持拖拽图片上传，网站前端使用LayUI，使用frame与layer弹出层实现单页后台。 

#### 软件架构


- 基本框架：SSM
- 开发工具：IDEA
- 使用的技术：SpringMVC、Spring、Mybatis、Druid、Jsp、Ajax、commons-io ... 


#### 安装教程

1.  下载源码
2.  打包成WAR包
3.  部署到TOMCAT服务器
4.  添加静态图片路径映射（将本地 D：/src 路径映射到 localhost/src）

```
<Host name="localhost"  ... 在tonmcat config.xml文件中添加如下一行,仅供参考,还有别的方法>
    ...		   
    <!-- 增加的静态资源映射配置 具体的写入路径在controller/Upload.java中-->
　　<Context path="/src" docBase="D:\src" reloadable="true" crossContext="true"></Context>
</Host>
```
5.  写入数据库配置

#### 亮点

1.  代码复用率高，整站使用一个上传接口，完备的图片上传 与回调
2.  异常处理器优化用户体验
3.  拦截器保护后台
4.  多管理员的权限管理
5.  上线至今稳定运行，没有任何运行异常与设计BUG。 

