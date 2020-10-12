# Codis 集群迁移 {#concept_qh3_mpc_wfb .concept}

您可以使用 redis-sync-manager 将自建 Codis 集群迁移至云数据库 Redis 版。

## 前提条件 {#section_yp5_x2d_wfb .section}

-   需要在环境变量 $PATH 中定义 [redis-port](cn.zh-CN/用户指南/迁移数据/云下到云上/使用redis-port进行迁移.md#)，因为 redis-sync-manager 在运行过程中会依赖于 redis-port。
-   运行时需要注意 Cluster 的基本数据及当前并发度可能会占用的内存。

## 工具下载 { .section}

[redis-sync-manager](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/94155/cn_zh/1542707688880/redis-sync-manager)

## 使用说明 { .section}

使用 redis-sync-manager 进行Codis集群迁移上云的命令如下：

```
./redis-sync-manager --fromlist=addr_list --target=dst_host:dst_port [--password=src_password]  [--auth=dst_password] [--rewrite] [--bigkeysize=SIZE] [--sync-parallel=INT]
```

**工作原理**

redis-sync-manager根据`addr_list`提供的待同步分片`IP:PORT`列表。调用 redis-port 进行数据同步（全量数据同步的并发度依赖`--sync-parallel`配置，所有分片的增量数据均会保持同步）。相关参数请参见下表。

|参数|说明|
|--|--|
|addr\_list|自建 Codis 集群所有分片列表，格式为`ip1:port1,ip2:port2,ip3:port3`。|
|src\_password|自建 Codis 的密码|
|dst\_host|云数据库 Redis 版连接地址|
|dst\_port|云数据库 Redis 版端口|
|dst\_password|云数据库 Redis 版密码|
|rewrite|覆盖已经写入的Key。|
|bigkeysize=SIZE|当写入value的大于SIZE 时，采用大key写入模式。|
|--logfile=REDISPORT.LOG|传递日志文件名称，对于不同的分片，程序会自动添加地址后缀。默认为`logs/redis-sync-manager.log`。|
|--pidfile=REDISPORT.PID|传递pid文件名称，对于不同的分片，程序会自动添加地址后缀。默认为`logs/redis-sync-manager.pid`。|
|--httpport=HTTPPORT|传递监听端口的初始值，按照程序内调度redis-port的启动顺序，端口号依次加1。默认初始值为`16000`。|
|--sync-parallel=INT|用于支持数据同步的并发度，展示可能占据的内存。默认为`1`。|

## 运行Demo {#ul_lfs_1y4_k2b .section}

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/64566/154276528532569_zh-CN.png) 

