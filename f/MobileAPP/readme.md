## 36Kr 手机app开发

本项目基于mui二次开发，定位是资讯类的app

## 页面结构

- guide.html 欢迎页，第一次启动app后加载。
- index.html app启动页面，主要用于初始化（PS：并不在页面中显示实际的内容）。
- index-menu.html 侧边栏菜单
- about.html 关于信息（app制作人员，版本号）
- main.html 底部菜单容器
- subpage-chat.html 聊天页
- subpage-home.html 主体内容
- subpage-chat.html 聊天
- subpage-setting.html 设置
- login.html 登录
- reg.html	注册
- forget_password.html 忘记密码
- loading.html 加载中
- feedback.html 反馈
- articleXXX.html 文章详情页面
- unlock.html 图形解锁

## 资源说明

- 缩略图分辨率为500*333
- 相关资源请到[36Kr](http://36kr.com/)获取

## 数据库

数据库有2种实现方案：

- html5本地数据库
	- sqllite
	- NoSQL
- 传统的服务端数据库（本程序中使用MySQL,阿里云RDS）	

### 表摘要

- article_detail
文章详情表
数据字典
	- id TINYINY 自增主键(id)
	- title VARCHAR(10) NOT NULL (标题)
	- count int DEFAULT 0(访问量)
	- content text DEFAULT NULL （内容）
	- author VARCHAR(10) DEFAULT '无名氏' (作者)
	- pdate datetime  (发布时间)

- article_outline
文章概要表
数据字典（列定义）
	- id tinyint 自增主键（id）
	- title VARCHAR(10) NOT NULL（标题）
	- thumb VARCHAR(80) DEFAULT 'default.jpg' （缩略图，之所以设置这么长的字段是方便使用七牛的CDN）
	- introduce VARCHAR(30) NOT NULL （简介）

两张表并没有通过外键关联。
*注意：以上的并不是真正的数据库脚本，仅仅是列的定义*,具体的数据库初始化脚本参见server/db.sql