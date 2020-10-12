# redis-cli连接 {#concept_tzm_xdd_5db .concept}

您可以使用原生Redis自带的工具redis-cli来连接阿里云Redis。

云数据库Redis版仅支持阿里云内网访问，不支持外网访问，即只有在同一VPC内的ECS上安装redis-cli才能与云数据库建立连接并进行数据操作。

**说明：** 

-   Redis-cli是Redis原生的命令行工具，可以先下载安装Redis即可使用redis-cli。在ECS上安装Redis的命令请参考[Redis官方网页](https://redis.io/download)。
-   如果同一VPC内的实例开启了免密访问功能，则无需提供密码信息，即可连接数据库。
-   请确认ECS实例的安全组允许其与Redis实例的数据交互，同时，您需要将ECS实例的内网地址[加入Redis的白名单](../../../../../intl.zh-CN/用户指南/管理实例/设置IP白名单.md#)。

Redis-cli连接云数据库Redis版的命令如下：

```
redis-cli -h 实例连接地址 -a 密码
```

您可以通过观看以下视频来了解如何通过redis-cli连接实例，视频时长约2分钟。



