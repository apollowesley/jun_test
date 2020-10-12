# 使用DTS将华为云分布式缓存服务Redis迁移至阿里云 {#concept_508448 .concept}

## 背景信息 {#section_2e1_dqd_asw .section}

本文介绍使用阿里云[数据传输服务（DTS）](https://help.aliyun.com/product/26590.html)，从华为云分布式缓存服务 Redi迁移至阿里云云数据库Redis版。

**说明：** 其他华为云分布式缓存服务 Redis 版本迁移到阿里云的方法，请参见[使用redis-shake将华为云分布式缓存服务Redis迁移至阿里云](cn.zh-CN/用户指南/迁移数据/从第三方数据库迁移到Redis/使用redis-shake将华为云分布式缓存服务Redis迁移至阿里云.md#)。

## 前提条件 {#section_d8b_w29_hic .section}

-   迁移的源数据库实例支持公网连接。

    **说明：** 您使用的华为云分布式缓存服务Redis是否支持绑定弹性公网IP，请参见华为云弹性公网IP文档。

-   源Redis实例类型为单机版。
-   已创建阿里云Redis实例，相关操作请参见[步骤1：创建实例](../../../../cn.zh-CN/快速入门/步骤1：创建实例.md#)。

## 迁移类型简介 {#section_ql8_rul_8e7 .section}

从华为云分布式缓存服务Redis实例到阿里云云数据库Redis版的数据迁移，可以支持全量数据迁移＋增量数据迁移，全量数据迁移及增量数据迁移的功能及限制如下。

|迁移类型|说明|
|----|--|
|全量数据迁移|DTS将源Redis中现有的key全部迁移到阿里云Redis实例中。|
|增量数据迁移|增量数据迁移将迁移过程中，源Redis实例的更新key同步到阿里云Redis实例。最终，源Redis和阿里云Redis实例进入动态数据复制的过程。通过增量数据迁移，可以实现在源Redis实例正常提供服务的同时，平滑完成华为云Redis实例至阿里云Redis实例的数据迁移。|

## 迁移功能 {#section_066_7yy_j9l .section}

Redis增量迁移支持的命令包括：

-   APPEND
-   BITOP, BLPOP, BRPOP, BRPOPLPUSH
-   DECR, DECRBY, DEL
-   EVAL, EVALSHA,EXEC, EXPIRE, EXPIREAT
-   FLUSHALL, FLUSHDB
-   GEOADD, GETSET
-   HDEL, HINCRBY, HINCRBYFLOAT, HMSET, HSET, HSETNX
-   INCR, INCRBY, INCRBYFLOAT
-   LINSERT, LPOP, LPUSH, LPUSHX, LREM, LSET, LTRIM
-   MOVE, MSET, MSETNX, MULTI
-   PERSIST, PEXPIRE, PEXPIREAT, PFADD, PFMERGE, PSETEX,PUBLISH
-   RENAME, RENAMENX, RESTORE,RPOP, RPOPLPUSH, RPUSH, RPUSHX
-   SADD, SDIFFSTORE, SELECT, SET, SETBIT, SETEX, SETNX, SETRANGE, SINTERSTORE, SMOVE, SPOP, SREM, SUNIONSTORE
-   ZADD, ZINCRBY, ZINTERSTORE, ZREM, ZREMRANGEBYLEX, ZUNIONSTORE, ZREMRANGEBYRANK, ZREMRANGEBYSCORE

## 操作步骤 {#section_4aj_r2u_q3n .section}

1.  登陆华为云Redis控制台，查看Redis实例的公网访问地址和端口号。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/408608/156093018248758_zh-CN.png)

2.  登录[数据传输服务控制台](https://dts.console.aliyun.com/#/home/)。
3.  单击左侧菜单栏中的**数据迁移**，打开数据迁移页面后单击右上角**创建迁移任务**。
4.  （可选）填写任务名称。

    DTS 为每个任务自动生成一个任务名称，任务名称没有唯一性要求。您可以根据需要修改任务名称，建议为任务配置具有业务意义的名称，便于后续的任务识别。

5.  填写源库和目标库信息，具体参数配置说明如下：

    |库类别|参数|说明|
    |---|--|--|
    |源库信息|实例类型|源实例类型，选择**有公网IP的自建数据库**。|
    |实例地区|选择与源 Redis 实例物理距离最近的地域，以便获得更高的迁移性能。 **说明：** 可以单击右侧**获取DTS IP段**复制对应地区的IP段，并添加到源Redis实例安全组。

 |
    |数据库类型|源数据库类型，选择**Redis**。|
    |实例模式|源Redis实例模式，选择**单机版**。|
    |主机名或IP地址|源Redis实例公网访问地址。|
    |端口|源Redis实例的端口号。|
    |数据库密码|源Redis实例的密码。|
    |目标库信息|实例类型|选择**Redis实例**。|
    |实例地区|目标实例所在的地区。|
    |Redis实例ID|目标Redis实例的实例ID。|
    |数据库密码|目标Redis实例的密码。|

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/408608/156093018248759_zh-CN.png)

6.  填写完成后单击**测试连接**，确定源库和目标库都测试通过。
7.  单击右下角**授权白名单并进入下一步**。
8.  勾选对应的迁移类型，在迁移对象框中将要迁移的库移动到已选择对象框中。

    **说明：** 

    -   为保证迁移数据的一致性，建议选择全量数据迁移+增量数据迁移。

        全量数据迁移暂不收费，增量数据迁移根据链路规格按小时收费。

    -   目前 DTS 只支持对Redis实例的整库迁移，只能选择要迁移的库，无法选择部分Key迁移。
    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/408608/156093018248760_zh-CN.png)

9.  单击**预检查并启动**，等待预检查结束。

    **说明：** 如果预检查失败，可以根据错误项的提示进行修复，然后重新启动任务。

10. 单击**下一步**，在购买配置确认对话框中，勾选**《数据传输（按量付费）服务条款》**并单击**立即购买并启动**。

    如果选择了增量迁移，那么进入增量迁移阶段后，源库的更新写入都会被DTS同步到目标Redis实例。迁移任务不会自动结束。如果您只是为了迁移，那么建议在增量迁移无延迟的状态时，源库停写几分钟，等待增量迁移再次进入无延迟状态后，停止掉迁移任务，直接将业务切换到目标Redis实例上即可。

11. 单击目标地域，查看迁移状态。

    -   全量迁移：迁移完成时，状态为已完成。
    -   全量迁移+增量迁移/增量迁移：迁移完成时，增量迁移为无延迟状态。
    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/408608/156093018248761_zh-CN.png)

    至此，完成华为云分布式缓存服务Redis迁移至阿里云云数据库Redis版的迁移任务。


