# Redis-cli 连接 {#concept_tzm_xdd_5db .concept}

云数据库 Redis 版仅支持阿里云内网访问，不支持外网访问，即只有在同一VPC内的 ECS 上安装 redis-cli 才能与云数据库建立连接并进行数据操作。

**说明：** 

-   Redis-cli 是 Redis 原生的命令行工具，可以先下载安装 Redis 即可使用 redis-cli。在 ECS 上安装 Redis 的命令请参考[Redis 官方网页](https://redis.io/download)。
-   如果同一VPC内的实例开启了免密访问功能，则无需提供密码信息，即可连接数据库。
-   请确认 ECS 实例的安全组允许其与 Redis 实例的数据交互。

Redis-cli 连接云数据库 Redis 版的命令如下：

```
redis-cli -h 实例连接地址 -a 密码
```

您可以通过观看以下视频来了解如何通过 redis-cli 连接实例，视频时长约2分钟。



