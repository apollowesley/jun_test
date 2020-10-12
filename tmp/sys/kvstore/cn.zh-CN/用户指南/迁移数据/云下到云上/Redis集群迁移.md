# Redis集群迁移 {#concept_tf4_bbv_vdb .concept}

使用redis-sync-manager，您可以将自建Redis集群迁移至云数据库Redis版。

## 前提条件 {#section_q3l_2xh_wfb .section}

-   需要在环境变量$PATH中定义[redis-port](intl.zh-CN/用户指南/迁移数据/云下到云上/使用redis-port进行迁移.md#)，因为redis-sync-manager在运行过程中会依赖于redis-port。
-   运行时需要注意集群的基本数据及当前并发度可能会占用的内存。
-   请保证源集群不处于slot迁移的中间态。

## 迁移工具 { .section}

-   redis-port：用于实现从单个Redis进程到目标集群的数据同步。具体操作请参见[使用redis-port进行迁移](intl.zh-CN/用户指南/迁移数据/云下到云上/使用redis-port进行迁移.md#)。
-   redis-sync-manager：用于从自建Redis集群到云数据库Redis集群版的数据同步。

## 工具下载 { .section}

[redis-sync-manager](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/94155/cn_zh/1542707688880/redis-sync-manager)

## 使用说明 { .section}

使用redis-sync-manager进行Redis集群迁移上云的命令如下：

```
./redis-sync-manager --from=src_host:src_port --target=dst_host:dst_port [--password=src_password]  [--auth=dst_password] [--filterkey="str1|str2|str3"] [--targetdb=DB] [--rewrite] [--bigkeysize=SIZE] [--logfile=REDISPORT.LOG] [--httpport=HTTPPORT] [--sync-parallel=INT] [--sync-role="master|slave"]
```

**工作原理**

redis-sync-manager和`src_host:src_port`交互，首先通过`cluster nodes`命令获取集群拓扑信息。然后基于`--sync-role`参数获得待同步分片的`IP:PORT`列表。最后调用redis-port进行数据同步（全量数据同步的并发度依赖`--sync-parallel`配置，所有分片的增量数据均会保持同步）。相关参数请参见下表。

|参数|说明|
|--|--|
|src\_host|自建Redis域名（或者IP），指定Redis集群中的一个进程的IP即可。|
|src\_port|自建Redis端口，指定对应src\_host的端口。|
|src\_password|自建Redis的密码|
|dst\_host|云数据库Redis版连接地址|
|dst\_port|云数据库Redis版端口|
|dst\_password|云数据库Redis版密码|
|str1|str2|str3|过滤具有str1或str2或str3的key。|
|DB|将同步入云数据库Redis版的目标DB。|
|rewrite|覆盖已经写入的key。|
|bigkeysize=SIZE|当写入value大于SIZE时，采用大key写入模式。|
|--logfile=REDISPORT.LOG|传递日志文件名称，对于不同的分片，程序会自动添加slot后缀。默认为`logs/redis-sync-manager.log`。|
|--sync-parallel=INT|用于支持数据同步的并发度，展示可能占据的内存。默认为`1`。|
|--sync-role="master|slave"|用于指定优先使用源集群的主库进行同步还是用从库进行同步。默认为`master`。|

## 运行Demo {#ul_lfs_1y4_k2b .section}

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/15449/15471723436883_zh-CN.png)

图片中的标注解释如下：

1.  打印每个分片的同步信息和同步状态。
2.  根据传递的`--sync-role`参数，为每个分片选择一个`IP:Port`，逐一打印出来。
3.  打印每个分片的同步状态到日志文件中。

