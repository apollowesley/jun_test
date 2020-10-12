# 删除Redis中所有数据库的key {#Redis中批量删除数据库中的 .concept}

Redis中有删除单个Key的指令DEL，但没有批量删除Key的指令，在一些特殊情况下想要一键删除Redis中所有数据库的Key，本文对此做了详细介绍。

## 注意事项 {#section_zbt_qbs_sfb .section}

-   Key和value相互绑定，删除Key，等于删除value 。
-   删除后不可恢复。执行该操作前，建议对数据库进行备份。

## 操作方法一 {#section_qqd_xbs_sfb .section}

1.  登录[DMS管理控制台](https://dms-rds.aliyun.com/?spm=a2c4g.11186623.2.11.1c9d3846UZoxoO)。
2.  输入要登录的数据库连接地址和密码，单击登录。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/61628/154744480230997_zh-CN.png)

    **说明：** 

    -   连接信息可以在[Redis管理控制台](https://kvstore.console.aliyun.com/?spm=a2c4g.11186623.2.13.c4d03846jrzrzV)的实例信息页获取。

    -   经典网络及VPC网络的实例都支持DMS。使用DMS登录VPC网络中的实例时，系统需要申请一条特殊通道，所以该类实例在第一次登录时，会有短暂的等待时间。

3.  在DMS管理控制台上方导航栏的命令窗口，执行`info`命令，可以查询当前所有库的key数量。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/61628/154744480230998_zh-CN.png)

4.  执行`flushall`命令，删除所有数据库的key。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/61628/154744480330999_zh-CN.png)


## 操作方法二 {#section_mny_wpv_tfb .section}

1.  登录[Redis管理控制台](https://kvstore.console.aliyun.com/?spm=a2c4g.11186623.2.16.636f1549KqsXOl)，进入实例列表页面。![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/61628/154744480331368_zh-CN.png)
2.  在实例列表页面，单击实例ID，进入实例信息页面，单击导航栏右上角清空数据。![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/61628/154744480331369_zh-CN.png)

