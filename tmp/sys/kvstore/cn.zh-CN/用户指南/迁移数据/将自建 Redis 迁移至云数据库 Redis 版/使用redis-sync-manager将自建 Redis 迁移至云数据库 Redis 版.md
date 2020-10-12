# 使用redis-sync-manager将自建 Redis 迁移至云数据库 Redis 版 {#concept_tf4_bbv_vdb .concept}

## 工具说明 { .section}

对于Redis跨集群的数据同步，redis-port工具提供了从单个Redis进程向目标集群进行数据同步的方法。redis-sync-manager是redis-port的补充（封装），提供了从Redis集群向目标集群进行数据同步的方法。

## 下载redis-sync-manager { .section}

[redis-sync-manager地址](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/66006/cn_zh/1531115616167/redis-sync-manager)

## 使用说明 { .section}

./redis-sync-manager --from=src\_host:src\_port --target=dst\_host:dst\_port \[--auth=dst\_password\] \[--filterkey="str1|str2|str3"\] \[--targetdb=DB\] \[--rewrite\] \[--bigkeysize=SIZE\] \[--logfile=REDISPORT.LOG\] \[--httpport=HTTPPORT\] \[--sync-parallel=INT\] \[--sync-role="master|slave"\]

**常用参数**

redis-sync-manager的如下参数和redis-port的参数的用法基本一致：

-   src\_host：自建Redis 域名（或者 IP），指定Redis集群中的一个进程的IP即可。
-   src\_port：自建Redis端口，指定对应src\_host的端口。
-   dst\_host：云数据库Redis域名。
-   dst\_port：云数据库Redis端口。
-   dst\_password：云数据库Redis密码。
-   str1|str2|str3：过滤具有str1或str2或str3的key。
-   DB：将同步入云数据库Redis的DB。
-   rewrite：覆盖已经写入的key。
-   bigkeysize=SIZE：当写入的value大于SIZE 时，采用大key写入模式的阈值。

**特殊参数**

redis-sync-manager的如下参数和redis-port的参数的用法不同，用户在使用时需要注意它们之间的差异：

-   --logfile=REDISPORT.LOG：file类按照当前分片所持有的slot进行命名。
-   --httpport=HTTPPORT：port类按照同步的先后顺序依次+1。

**新增参数**

-   提供`--sync-parallel=INT`参数用于支持数据同步的并发度，展示可能占据的内存。
-   提供`--sync-role="master|slave"`参数用于指定优先用source集群的master进行同步还是用从库进行同步。
-   提供`--from-mapping=FILE`参数用于指定源cluster集群存在IP映射关系的情况下的关系指定，格式为`sourceIP:sourcePORT>destIP:destPORT`。

## 使用要求 { .section}

-   必须在环境变量$PATH中定义redis-port，因为redis-sync-manager在运行过程中会依赖于redis-port。
-   source集群不能有密码（目前暂不支持密码功能）。
-   运行时必须注意cluster的基本数据及当前并发度可能会占用的内存。

## 运行Demo {#ul_lfs_1y4_k2b .section}

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/15449/6883_zh-CN.png)

![]()

