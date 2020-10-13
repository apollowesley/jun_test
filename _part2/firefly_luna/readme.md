# Firefly 企业信息开发平台

## 平台简介

Firefly本身是以Spring Framework为核心容器，Spring MVC为模型视图控制器，MyBatis为数据访问层，
Apache Shiro为权限授权层，Ehcahe,Redis对常用数据进行缓存，Maven做项目管理。

## 基础功能

1. 用户管理：该功能主要完成系统用户配置。
2. 系统参数：系统运行常用参数配置表。
3. 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
4. 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
5. 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
6. 操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。

## 技术相关

* 核心框架：Spring Framework 4.0
* 安全框架：Apache Shiro 1.2
* 视图框架：Spring MVC 4.0
* 服务端验证：Hibernate Validator 4.1
* 缓存框架：Ehcache 2.6、Redis
* 日志管理：SLF4J 1.7、Log4j

