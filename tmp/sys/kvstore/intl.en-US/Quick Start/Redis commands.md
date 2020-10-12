# Redis commands {#concept_ztj_rpn_tdb .concept}

This topic describes Redis commands that engine versions 2.8 and 4.0 support, and unsupported and restricted Redis commands. ApsaraDB for Redis is compatible with Redis 3.0 and supports Redis 3.0 GEO commands.

## Supported Redis commands {#section_iuw_u8e_ezy .section}

|Keys|String|Hash|List|Set|SortedSet|
|----|------|----|----|---|---------|
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

|HyperLogLog|Pub/Sub|Transaction|Connection|Server|Scripting|Geo|
|-----------|-------|-----------|----------|------|---------|---|
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

**Note:** 

-   When you run the CLIENT LIST command on a Redis cluster instance, you can retrieve a list of all client connections to the specified proxy. In this list, the id, age, idle, addr, fd, name, db, multi, omem, and cmd fields are described in the same way as those in the Redis kernel. The sub and psub fields are not distinguished for the proxy, and their values are either 1 or 0 at the same time. The qbuf, qbuf-free, obl, and oll fields are not described.
-   You can run the CLIENT KILL command on a Redis cluster instance in two ways: `client kill ip:port` and `client kill addr ip:port`.

## New Redis commands for engine version 4.0 {#section_zr5_ygy_bgb .section}

|Keys|Server|
|----|------|
|UNLINK|SWAPDB|
| |MEMORY|

**Updated Redis commands for engine version 4.0**

The FLUSHALL and FLUSHDB commands support the ASYNC option.

**Note:** Based on this option, you can run the FLUSHALL or FLUSHDB command asynchronously in a new thread. In this way, this thread cannot block the service. For more information about features of engine version 4.0, see [Features of engine version 4.0 of ApsaraDB for Redis](../../../../reseller.en-US/Product Introduction/Features of engine version 4.0 of ApsaraDB for Redis.md#).

## Unsupported Redis commands {#section_xgx_zxy_xdb .section}

|Keys|Server|
|----|------|
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

## Redis commands restricted by cluster instances {#section_ns6_u3d_utw .section}

|Keys|Strings|Lists|HyperLogLog|Transaction|Scripting|
|----|-------|-----|-----------|-----------|---------|
|RENAME|MSETNX|RPOPLPUSH|PFMERGE|DISCARD|EVAL|
|RENAMENX| |BRPOP|PFCOUNT|EXEC|EVALSHA|
|SORT| |BLPOP| |MULTI|SCRIPT EXISTS|
| | |BRPOPLPUSH| |UNWATCH|SCRIPT FLUSH|
| | | | |WATCH|SCRIPT KILL|
| | | | | |SCRIPT LOAD|

**Note:** 

-   Restricted commands only support scenarios where target keys are distributed in a single hash slot. You cannot merge data from multiple hash slots. Therefore, you need to use hash tags to distribute all target keys to only one hash slot.

    For example, when you process three keys, key1, aakey, and abkey3, you need to store them as \{key\}1, aa\{key\}, and ab\{key\}3 to effectively call restricted commands. For more information about how to use hash tags, visit [http://redis.io/topics/cluster-spec](http://redis.io/topics/cluster-spec).

-   If you do not run the WATCH command prior to a transaction, and each command in the transaction processes only one key, these keys can be in different slots. You can run these commands in the same way as you run them in a directly connected database. In other scenarios, all keys that all commands process in a transaction must be in the same slot.

    -   The commands that process multiple keys include: DEL, SORT, MGET, MSET, BITOP, EXISTS, MSETNX, RENAME, RENAMENX, BLPOP, BRPOP, RPOPLPUSH, BRPOPLPUSH, SMOVE, SUNION, SINTER, SDIFF, SUNIONSTORE, SINTERSTORE, SDIFFSTORE, ZUNIONSTORE, ZINTERSTORE, PFMERGE, and PFCOUNT.
    -   The commands that do not support transactions include: WATCH, UNWATCH, RANDOMKEY, KEYS, SUBSCRIBE, UNSUBSCRIBE, PSUBSCRIBE, PUNSUBSCRIBE, PUBLISH, PUBSUB, SCRIPT, EVAL, EVALSHA, SCAN, ISCAN, DBSIZE, ADMINAUTH, AUTH, PING, ECHO, FLUSHDB, FLUSHALL, MONITOR, IMONITOR, RIMONITOR, INFO, IINFO, RIINFO, CONFIG, SLOWLOG, TIME, and CLIENT.

**Lua restrictions**

You can directly use Lua scripts for the standard master-replica edition and the standard single-node edition.

Lua scripts support the cluster edition in the following conditions:

-   You must use KEYS arrays to pass all keys. For Redis commands in redis.call\(\) and redis.pcall\(\), keys must be KEYS arrays. You cannot replace KEYS with Lua variables. Otherwise, the system returns the following error: "`-ERR bad lua script for redis cluster, all the keys that the script uses should be passed using the KEYS array\r\n`".
-   All keys must be in the same slot. Otherwise, the system returns the following error: "`-ERR eval/evalsha command keys must be in same slot\r\n`".
-   You must use keys when running Redis commands for cluster instances. Otherwise, the system returns the following error: "`-ERR for redis cluster, eval/evalsha number of keys can't be negative or zero\r\n`".

## Proprietary Redis commands for cluster instances {#section_u95_g3x_6sn .section}

-   INFO KEY: you can run this command to query slots and databases \(DBs\) that keys belong to. The native Redis command INFO can contain only one optional section in this way: `info [section]`. When you run some commands for cluster instances of ApsaraDB for Redis, all keys must be in the same slot. The `INFO KEY` command allows you to check whether keys are in the same slot or DB. You can run this command in this way:

    ``` {#codeblock_1ne_ktk_528}
      127.0.0.1:6379> info key test_key
      slot:15118 node_index:0
    ```

    **Note:** 

    -   In earlier editions, the `INFO KEY` command may return a different `node index` from the `node index` in the topology of an instance. This issue has been fixed in the latest edition.

    -   The `INFO KEY` command returns shard servers of cluster instances. These shard servers are different from the DBs used in the SELECT command.

-   IINFO: you can run this command to specify the node of ApsaraDB for Redis to run the INFO command. This command is similar to the INFO command. You can run this command in this way:

``` {#codeblock_tgh_09t_hnj}
iinfo db_idx [section]
```

    In this command, db\_idx supports the range of \[0, nodecount\]. You can obtain the nodecount value by running the INFO command, and specify the section option in the same way as you specify this option for a Redis database. For more information about a node of ApsaraDB for Redis, you can run the IINFO command or check the instance topology in the console.

-   RIINFO: you can run this command in a similar way as you run IINFO, but only in read/write splitting scenarios. This command specifies the idx value as the identifier of the read-only replica node where you want to run the INFO command. If you use this command on instances other than read/write splitting cluster instances, the system returns an error. You can run this command in this way:

``` {#codeblock_wo7_v93_n1e}
riinfo db_idx ro_slave_idx [section]
```

-   ISCAN: you can run this command to specify the DB of a cluster where you want to run the SCAN command. This command provides the db\_idx parameter on the basis of SCAN. The db\_idx parameter supports the range of \[0, nodecount\]. You can obtain the nodecount value by running the INFO command or by checking the instance topology in the console. You can run this command in this way:

``` {#codeblock_bs3_ecr_b6a}
iscan db_idx cursor [MATCH pattern] [COUNT count]
```

-   IMONITOR: similar to IINFO and ISCAN, this parameter provides the db\_idx parameter on the basis of the MONITOR command. The db\_idx parameter specifies the node where you want to run MONITOR. The db\_idx parameter supports the range of \[0, nodecount\]. You can obtain the nodecount value by running the INFO command or by checking the instance topology in the console. You can run this command in this way:

    `imonitor db_idx`

-   RIMONITOR: similar to RIINFO, you can run this command to specify the read-only replica node in a specified shard where you want to run the MONITOR command. This command supports read/write splitting scenarios. You can run this command in this way:

``` {#codeblock_d3h_6lu_qsm}
rimonitor db_idx ro_slave_idx
```

    **Note:** Before you run IMONITOR and RIMONITOR, use telnet to verify connection conditions. To exit these commands, run the QUIT command.


## Notes {#section_2q8_60h_6lh .section}

-   For more information about Redis commands, see [Redis documents](http://redis.io/commands).
-   If the system returns the `unknown command` error when you run a supported command on a cluster instance, you need to [upgrade the minor version](../../../../reseller.en-US/User Guide/Manage instances/Upgrade a minor version.md#) in the console.

