# 支持的 Redis 命令 {#concept_ztj_rpn_tdb .concept}

云数据库 Redis 版兼容 Redis 3.0 版本，支持 Redis 3.0 的 Geo 命令。本文列出了云数据库2.8版本和4.0版本支持的 Redis 命令，以及暂未开放或受限制的命令。

## 支持的命令 { .section}

|Keys（键）|String（字符串）|Hash（哈希表）|List（列表）|Set（集合）|SortedSet（有序集合）|
|-------|-----------|---------|--------|-------|---------------|
|DEL|APPEND|HDEL|BLPOP|SADD|ZADD|
|DUMP|BITCOUNT|HEXISTS|BRPOP|SCARD|ZCARD|
|EXISTS|BITOP|HGET|BRPOPLPUSH|SDIFF|ZCOUNT|
|EXPIRE|BITPOS|HGETALL|LINDEX|SDIFFSTORE|ZINCRBY|
|EXPIREAT|DECR|HINCRBY|LINSERT|SINTER|ZRANGE|
|MOVE|DECRBY|HINCRBYFLOAT|LLEN|SINTERSTORE|ZRANGEBYSCORE|
|PERSIST|GET|HKEYS|LPOP|SISMEMBER|ZRANK|
|PEXPIRE|GETBIT|HLEN|LPUSH|SMEMBERS|ZREM|
|PEXPTREAT|GETRANGE|HMGET|LPUSHX|SMOVE|ZREMRANGEBYRANK|
|PTTL|GETSET|HMSET|LRANGE|SPOP|ZREMRANGEBYSCORE|
|RANDOMKEY|INCR|HSET|LREM|SRANDMEMBER|ZREVRANGE|
|RENAME|INCRBY|HSETNX|LSET|SREM|ZREVRANGEBYSCORE|
|RENAMENX|INCRBYFLOAT|HVALS|LTRIM|SUNION|ZREVRANK|
|RESTORE|MGET|HSCAN|RPOP|SUNIONSTORE|ZSCORE|
|SORT|MSET| |RPOPLPUSH|SSCAN|ZUNIONSTORE|
|TTL|MSETNX| |RPUSH| |ZINTERSTORE|
|TYPE|PSETEX| |RPUSHX| |ZSCAN|
|SCAN|SET| | | |ZRANGEBYLEX|
|OBJECT|SETBIT| | | |ZLEXCOUNT|
| |SETEX| | | |ZREMRANGEBYLEX|
| |SETNX| | | | |
| |SETRANGE| | | | |
| |STRLEN| | | | |

|HyperLogLog|Pub/Sub（发布/订阅）|Transaction（事务）|Connection（连接）|Server（服务器）|Scripting（脚本）|Geo（地理位置）|
|-----------|--------------|---------------|--------------|-----------|-------------|---------|
|PFADD|PSUBSCRIBE|DISCARD|AUTH|FLUSHALL|EVAL|GEOADD|
|PFCOUNT|PUBLISH|EXEC|ECHO|FLUSHDB|EVALSHA|GEOHASH|
|PFMERGE|PUBSUB|MULTI|PING|DBSIZE|SCRIPT EXISTS|GEOPOS|
| |PUNSUBSCRIBE|UNWATCH|QUIT|TIME|SCRIPT FLUSH|GEODIST|
| |SUBSCRIBE|WATCH|SELECT|INFO|SCRIPT KILL|GEORADIUS|
| |UNSUBSCRIBE| | |KEYS|SCRIPT LOAD|GEORADIUSBYMEMBER|
| | | | |CLIENT KILL| | |
| | | | |CLIENT LIST| | |
| | | | |CLIENT GETNAME| | |
| | | | |CLIENT SETNAME| | |
| | | | |CONFIG GET| | |
| | | | |MONITOR| | |
| | | | |SLOWLOG| | |

**说明：** 

-   集群实例下，client list 命令列出所有连接到该 proxy 的 user connection。其中，id、age、idle、addr、fd、name、db、multi、omem、cmd 字段和redis内核表达的意思一样。sub、psub 在 proxy 层没有区分，要么都为1，要么都为0。qbuf、qbuf-free、obl、oll 字段目前没有意义。
-   集群实例下，client kill 命令目前支持两种形式：`client kill ip:port`和`client kill addr ip:port`。

## 4.0版本新增 {#section_zr5_ygy_bgb .section}

|Keys（键）|Server（服务器）|
|-------|-----------|
|UNLINK|SWAPDB|
| |MEMORY|

**4.0更新命令**

FLUSHALL/FLUSHDB 新增选项 ASYNC。

**说明：** 添加 ASYNC 选项后 FLUSHALL 或 FLUSHDB 的操作将在新的线程中异步进行，不会阻塞服务。更多 Redis 4.0相关特性请参见[Redis 4.0 新功能介绍](../../../../intl.zh-CN/产品简介/Redis 4.0 新功能介绍.md#)。

## 暂未开放的命令 {#section_xgx_zxy_xdb .section}

|Keys（键）|Server（服务器）|
|-------|-----------|
|MIGRATE|BGREWRITEAOF|
| |BGSAVE|
| |CONFIG REWRITE|
| |CONFIG SET|
| |CONFIG RESETSTAT|
| |COMMAND|
| |COMMAND COUNT|
| |COMMAND GETKEYS|
| |COMMAND INFO|
| |DEBUG OBJECT|
| |DEBUG SEGFAULT|
| |LASTSAVE|
| |ROLE|
| |SAVE|
| |SHUTDOWN|
| |SLAVEOF|
| |SYNC|

## 集群实例受限制的命令 { .section}

|Keys|Strings|Lists|HyperLogLog|Transaction|Scripting|
|----|-------|-----|-----------|-----------|---------|
|RENAME|MSETNX|RPOPLPUSH|PFMERGE|DISCARD|EVAL|
|RENAMENX| |BRPOP|PFCOUNT|EXEC|EVALSHA|
|SORT| |BLPOP| |MULTI|SCRIPT EXISTS|
| | |BRPOPLPUSH| |UNWATCH|SCRIPT FLUSH|
| | | | |WATCH|SCRIPT KILL|
| | | | | |SCRIPT LOAD|

**说明：** 

-   集群实例受限命令只支持所操作 key 均分布在单个 hash slot 中的场景，没有实现多个 hash slot 数据的合并功能，因此需要用 hash tag 的方式确保要操作的 key 均分布在一个 hash slot 中。

    比如有 key1，aakey，abkey3，那么我们在存储的时候需要用 \{key\}1，aa\{key\}，ab\{key\}3 的方式存储，这样调用受限命令时才能生效。具体关于 hash tag 的用法请参见 Redis 官方文档：[http://redis.io/topics/cluster-spec](http://redis.io/topics/cluster-spec)。

-   事务之前没有使用 watch 命令且事务中都是单 key 的命令场景，不再要求所有 key 必须在同一个 slot 中，使用方式和直连 redis 完全一致。其他场景要求事务中所有命令的所有 key 必须在同一个 slot 中。

    -   多 key 命令包括：DEL、SORT、MGET、MSET、BITOP、EXISTS、MSETNX、RENAME、 RENAMENX、BLPOP、BRPOP、RPOPLPUSH、BRPOPLPUSH、SMOVE、SUNION、SINTER、SDIFF、SUNIONSTORE、SINTERSTORE、SDIFFSTORE、ZUNIONSTORE、ZINTERSTORE、 PFMERGE、PFCOUNT。
    -   不允许在事务中使用的命令包括：WATCH、UNWATCH、RANDOMKEY、KEYS、SUBSCRIBE、 UNSUBSCRIBE、PSUBSCRIBE、PUNSUBSCRIBE、PUBLISH、PUBSUB、SCRIPT、EVAL、 EVALSHA、SCAN、ISCAN、DBSIZE、ADMINAUTH、AUTH、PING、ECHO、FLUSHDB、 FLUSHALL、MONITOR、IMONITOR、RIMONITOR、INFO、IINFO、RIINFO、CONFIG、 SLOWLOG、TIME、CLIENT。

**Lua使用限制**

Lua 脚本放开限制，标准版-双节点、标准版-单节点支持用户直接调用。

集群版本条件性支持：

-   所有key都应该由 KEYS 数组来传递，redis.call/pcall 中调用的redis命令，key的位置必须是KEYS array（不能使用Lua变量替换KEYS），否则直接返回错误信息，"`-ERR bad lua script for redis cluster, all the keys that the script uses should be passed using the KEYS array\r\n`"。
-   所有key必须在1个slot上，否则返回错误信息，"`-ERR eval/evalsha command keys must be in same slot\r\n`"。
-   调用必须要带有key，否则直接返回错误信息， "`-ERR for redis cluster, eval/evalsha number of keys can't be negative or zero\r\n`"。

## 自研的集群实例命令 { .section}

-   info key 命令：查询 key 所属的 slot 和 db。Redis 原生的 info 命令中最多可以带一个可选的 section \(`info [section]`\)。目前云数据库 Redis 版的集群实例，部分命令限制所有 key 必须在同一个 slot 中，`info key` 命令方便用户查询某些 key 是否在同一个 slot 或 db 节点中。用法如下：

    ```
      127.0.0.1:6379> info key test_key
      slot:15118 node_index:0
    ```

    **说明：** 

    -   线上旧版本可能出现 `info key` 显示出来的 `node index` 和实例拓扑图的 `node index` 不一致，最新版本已经修复。

    -   `info key` 显示的 node 是指集群规格下后端的物理节点，和 select 命令中的 db 不是同一个概念。

-   iinfo 命令：用法类似于 info，用于在指定的 Redis 节点上执行 info 命令。用法如下：

```
iinfo db_idx [section]
```

    其中，db\_idx 的范围是\[0, nodecount\]，nodecount 可以通过 info 命令获取，section 的用法与官方 info 命令中的 section 一致。要了解某个 Redis 节点的 info 可以使用 iinfo 命令或者从控制台上查看实例拓扑图。

-   riinfo 命令：和 iinfo 命令类似，但只能在读写分离的模式下使用。用法中增加了一个 readonly slave 的 idx，用于指定在第几个 readonly slave 上执行 info 命令。在读写分离集群中可以用来在指定 readonly slave 上执行 info 命令。如果在非读写分离集群中使用，会返回错误。用法如下：

```
riinfo db_idx ro_slave_idx [section]
```

-   iscan 命令：在集群模式下可以在指定的 db 节点上执行 scan 命令。在 scan 命令的基础上扩展了一个参数用于指定 db\_idx， db\_idx 的范围是\[0, nodecount\]，nodecount 可以通过 info 命令获取或者从控制台上查看实例拓扑图。用法如下：

```
iscan db_idx cursor [MATCH pattern] [COUNT count]
```

-   imonitor 命令：和 iinfo、 iscan 类似，在 monitor 的基础上新增一个参数指定 monitor 执行的 db\_idx，db\_idx 的范围是\[0, nodecount\)， nodecount 可以通过 info 命令获取或者从控制台上查看实例拓扑图。用法如下：

    `imonitor db_idx`

-   rimonitor 命令：和 riinfo 类似，用于读写分离场景下，在指定的 shard 里的指定只读从库上执行 monitor 命令。用法如下：

```
rimonitor db_idx ro_slave_idx
```

    **说明：** imonitor和rimonitor请用telnet连接后执行，如需退出imonitor/rimonitor，请使用quit命令。


## 说明 { .section}

关于 Redis 命令的详细信息，请参见 [官方文档](http://redis.io/commands)。

