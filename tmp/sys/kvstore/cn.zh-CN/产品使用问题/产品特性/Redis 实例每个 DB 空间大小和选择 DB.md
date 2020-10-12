# Redis 实例每个 DB 空间大小和选择 DB {#concept_qbg_px5_ydb .concept}

Redis 每个实例默认有256个库，DB 0 到 DB 255。

每个 DB 没有大小限制，DB 可以使用的空间大小，受 Redis 实例总体的空间限制。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13933/4245_zh-CN.png)

在不同 DB 之间进行切换，使用命令 select。

例如选择 DB 1：select 1

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13933/4246_zh-CN.png)

