# Features of engine version 4.0 of ApsaraDB for Redis {#concept_omh_hjg_tdb .concept}

Alibaba Cloud has developed engine version 4.0 of ApsaraDB for Redis based on Redis 4.0 and fixed several bugs to provide you with excellent performance. Engine version 4.0 of ApsaraDB for Redis has all benefits of engine version 2.8 of ApsaraDB for Redis, and supports the following features.

## Lazyfree {#section_dtj_qdh_tdb .section}

Engine version 4.0 supports the Lazyfree feature. This feature can avoid congestion on Redis-server caused by the `DEL`, `FLUSHDB`, `FLUSHALL` and `RENAME` commands and ensure service stability. This feature is described as follows.

**UNLINK**

For engine versions earlier than 4.0, when ApsaraDB for Redis runs the `DEL` command, the command returns `OK` only after releasing the memory of the target key. If the key contains large amounts of data, for example, 10 million items of data in a hash table, other connections have to wait a long time. To be compatible with the existing `DEL` syntax, engine version 4.0 uses the `UNLINK` command. The UNLINK command has the same effect and usage as the `DEL` command, but the background thread releases memory when engine version 4.0 runs the UNLINK command.

```
UNLINK key [key ...]
```

**FLUSHDB/FLUSHALL**

The `FLUSHDB` and FLUSHALL commands in engine version 4.0 allow you to specify whether to use the Lazyfree feature to clear all memory.

```
FLUSHALL [ASYNC]
FLUSHDB [ASYNC]
```

**RENAME**

When ApsaraDB for Redis runs the `RENAME OLDKEY NEWKEY` command, if the specified new key already exists, ApsaraDB for Redis deletes the existing new key first. If the key contains large amounts of data, other connections have to wait a long time. To use the Lazyfree feature to delete the key in ApsaraDB for Redis, apply the following configuration in the console:

```
lazyfree-lazy-server-del yes/no
```

**Note:** 

This parameter is not available in the console.

**Expire or evict data**

You can specify data expiration time and allow ApsaraDB for Redis to delete expired data. However, when ApsaraDB for Redis deletes a large expired key, CPU jitter may occur. Engine version 4.0 allows you to specify whether to use the Lazyfree feature to expire or evict data.

```
lazyfree-lazy-eviction yes/no
lazyfree-lazy-expire yes/no
```

## New commands {#section_mtj_qdh_tdb .section}

**SWAPDB**

The `SWAPDB` command is used to exchange data between two databases. After ApsaraDB for Redis runs the `SWAPDB` command, you can connect to the target database and check new data directly without running the `SELECT` command.

```
127.0.0.1:6379> select 0
OK
127.0.0.1:6379> set key value0
OK
127.0.0.1:6379> select 1
OK
127.0.0.1:6379[1]> set key value1
OK
127.0.0.1:6379[1]> swapdb 0 1
OK
127.0.0.1:6379[1]> get key
"value0"
127.0.0.1:6379[1]> select 0
OK
127.0.0.1:6379> get key
"value1"
```

**ZLEXCOUNT**

The `ZLEXCOUNT` command is used for sorted sets and similar to the `ZRANGEBYLEX` command. However, the `ZRANGEBYLEX` command returns the target members, and the `ZLEXCOUNT` command returns the number of target members.

**MEMORY**

Engine versions earlier than 4.0 support the `INFO MEMORY` command to provide limited memory information. Engine version 4.0 allows you to use the `MEMORY` command to obtain comprehensive memory status of ApsaraDB for Redis.

```
127.0.0.1:6379> memory help
1) "MEMORY DOCTOR                        - Outputs memory problems report"
2) "MEMORY USAGE <key> [SAMPLES <count>] - Estimate memory usage of key"
3) "MEMORY STATS                         - Show memory usage details"
4) "MEMORY PURGE                         - Ask the allocator to release memory"
5) "MEMORY MALLOC-STATS                  - Show allocator internal stats"
```

-   `MEMORY USAGE`

    The `USAGE` child command is used to check the memory usage of a specified key in ApsaraDB for Redis.

    **Note:** 

    -   Key-value pairs in ApsaraDB for Redis use memory. ApsaraDB for Redis also uses memory when managing these key-value pairs.

    -   The memory usage of keys such as hash tables, lists, sets, and sorted sets is calculated based on sampling. The `SAMPLES` command controls the number of samples.

-   `MEMORY STATS`

    ```
    27.0.0.1:6379> memory stats
           1) "peak.allocated"    // The maximum memory that ApsaraDB for Redis has used since startup.
           2) (integer) 423995952
           3) "total.allocated"    //The current memory usage.
           4) (integer) 11130320
           5) "startup.allocated"    //The memory that ApsaraDB for Redis uses after startup and initialization.
           6) (integer) 9942928
           7) "replication.backlog"    //The memory of the backlog used in resuming an interrupted master-replica replication. Default value: 10 MB.
           8) (integer) 1048576
           9) "clients.slaves"    // The memory used in a master-replica replication.
          10) (integer) 16858
          11) "clients.normal"    //The memory used by read and write buffers for common clients.
          12) (integer) 49630
          13) "aof.buffer"    //The sum of the cache used for append-only file (AOF) persistence and the cache generated during the AOF rewrite operation.
          14) (integer) 3253
          15) "db. 0"    //The memory used by metadata in each database.
          16) 1) "overhead.hashtable.main"
              2) (integer) 5808
              3) "overhead.hashtable.expires" //The memory used for managing data that has TTL configured.
              4) (integer) 104
          17) "overhead.total"    //The total memory usage for the preceding items.
          18) (integer) 11063904
          19) "keys.count"    //The total number of keys in the current storage.
          20) (integer) 94
          21) "keys.bytes-per-key"    //The average size of each key in the current memory.
          22) (integer) 12631
          23) "dataset.bytes"        //The memory used by user data (= Total memory - Memory used by metadata of ApsaraDB for Redis).
          24) (integer) 66416
          25) "dataset.percentage"    //100 * dataset.bytes / (total.allocated - startup.allocated)
          26) "5.5934348106384277"
          27) "peak.percentage"    // 100 * total.allocated / peak_allocated
          28) "2.6251003742218018"
          29) "fragmentation"    //The memory fragmentation ratio.
          30) "1.1039986610412598"
    ```

-   `MEMORY DOCTOR`

    This command is used to provide diagnostics and indicate hidden issues.

    ```
    Peak memory: peak.allocated/total.allocated > 1.5. This indicates a possibly high memory fragmentation ratio.
      High fragmentation: fragmentation > 1.4. This indicates a high memory fragmentation ratio.
      Big slave buffers: the average memory for each replica buffer is more than 10 MB. This may be caused by high traffic of write operations on the master node.
      Big client buffers: the average memory for a common client buffer is more than 200 KB. This may be caused by the improper use of pipelining or caused by Pub/Sub clients that delay processing messages.
    ```

-   `MEMORY STATS` and `MALLOC PURGE`

    Both commands are valid only when you use jemalloc.


## Least Frequently Used \(LFU\) mechanism and hotkeys {#section_p5j_qdh_tdb .section}

Engine version 4.0 supports allkey-lfu and volatile-lfu data eviction policies, and allows you to use the `OBJECT` command to obtain the frequency that a specified key is used.

```
object freq user_key
```

Based on the LFU mechanism, you can use a combination of the `SCAN` and OBJECT FREQ commands to find hotkeys. You can also use the Redis command line interface \(redis-cli\) program as follows:

```
$./redis-cli --hotkeys
# Scanning the entire keyspace to find hot keys as well as
# average sizes per key type.  You can use -i 0.1 to sleep 0.1 sec
# per 100 SCAN commands (not usually needed).
[00.00%] Hot key 'counter:000000000002' found so far with counter 87
[00.00%] Hot key 'key:000000000001' found so far with counter 254
[00.00%] Hot key 'mylist' found so far with counter 107
[00.00%] Hot key 'key:000000000000' found so far with counter 254
[45.45%] Hot key 'counter:000000000001' found so far with counter 87
[45.45%] Hot key 'key:000000000002' found so far with counter 254
[45.45%] Hot key 'myset' found so far with counter 64
[45.45%] Hot key 'counter:000000000000' found so far with counter 93
-------- summary -------
Sampled 22 keys in the keyspace!
hot key found with counter: 254 keyname: key:000000000001
hot key found with counter: 254 keyname: key:000000000000
hot key found with counter: 254 keyname: key:000000000002
hot key found with counter: 107 keyname: mylist
hot key found with counter: 93  keyname: counter:000000000000
hot key found with counter: 87  keyname: counter:000000000002
hot key found with counter: 87  keyname: counter:000000000001
hot key found with counter: 64  keyname: myset
```

