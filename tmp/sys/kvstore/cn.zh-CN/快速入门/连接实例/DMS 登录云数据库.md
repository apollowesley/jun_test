# DMS 登录云数据库 {#concept_jvw_c24_tdb .concept}

DMS 是一款访问管理云端数据的 Web 服务，支持 Redis、 MySQL、SQL Server、PostgreSQL 和 MongoDB 等数据源。DMS 提供了数据管理、对象管理、数据流转和实例管理四部分功能。您可以通过以下两种方式登录 DMS。

-   通过 [Redis 管理控制台](https://kvstore.console.aliyun.com/)，选择要登录的实例，单击右上角的**登录数据库**打开 DMS。通过该种方式打开 DMS，系统会自动填写登录页面中的数据库连接地址，您只要输入密码即可。

-   打开 [DMS](https://dms-rds.aliyun.com)，手工输入要登录的数据库连接地址和密码，如下图所示。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3127/1079_zh-CN.png)


**Note:** 

-   连接信息可以在[Redis 管理控制台](https://kvstore.console.aliyun.com/) 的实例信息页获取。
-   经典网络及 VPC 网络的实例都支持 DMS。由于 VPC 网络需要申请一个特殊通道，对于第一次登录的实例需要一定的缓冲时间。
-   更多的 DMS 相关信息请参见DMS的官方文档。

