# Redis实例每个DB空间大小和选择DB {#concept_qbg_px5_ydb .concept}

Redis每个实例默认有256个库，DB 0到DB 255。

每个DB没有大小限制，DB可以使用的空间大小，受Redis实例总体的空间限制。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13933/15474443824245_zh-CN.png)

在不同DB之间进行切换，使用命令select。

例如选择DB 1：select 1

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13933/15474443824246_zh-CN.png)

