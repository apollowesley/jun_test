# Redis 4.0新功能介绍 {#concept_omh_hjg_tdb .concept}

云数据库Redis 4.0版是以社区4.0引擎为基础，合入了大量阿里云开发的特性，并且修复了许多bug后全新推出的售卖版本。除了拥有Redis 2.8引擎所具备的所有优势之外，还带来了下面这些新的功能特性。

## Lazyfree {#section_dtj_qdh_tdb .section}

Redis 4.0的Lazyfree机制可以避免`del`、`flushdb`、`flushall`、`rename`等命令引起的redis-server阻塞，提高服务稳定性，详情如下。

**unlink**

在Redis 4.0之前，redis执行`del`命令，只有在释放掉key的所有内存以后才会返回`OK`。如果key比较大（比如说一个hash里有1000万条数据），其他连接需要等待较长时间。为了兼容已有的`del`语义，Redis 4.0引入`unlink`命令，效果以及用法和`del`完全一样，但内存释放动作放到后台线程中执行。

```
UNLINK key [key ...]
```

**flushdb/flushall**

`flushdb/flushall`在 Redis 4.0中引入了新选项，可以指定是否使用Lazyfree的方式来清空整个内存。

```
FLUSHALL [ASYNC]
FLUSHDB [ASYNC]
```

**rename**

执行`rename oldkey newkey`时，如果newkey已经存在，redis会先删除已经存在的newkey，这也会引发上面提到的删除大key问题。如果想让redis在这种场景下也使用lazyfree的方式来删除，您可以在控制台上打开如下配置：

```
lazyfree-lazy-server-del yes/no
```

**说明：** 

该参数配置在控制台中暂未开放。

**淘汰或者逐出数据**

有些用户对数据设置过期时间，依赖Redis的淘汰机制去删除已经过期的数据，这同样也存在上面提到的问题：淘汰某个大key会导致进程CPU出现抖动。Redis 4.0提供了两个配置，可以让Redis在淘汰或者逐出数据时也使用lazyfree的方式。

```
lazyfree-lazy-eviction yes/no
lazyfree-lazy-expire yes/no
```

## 新增命令 {#section_mtj_qdh_tdb .section}

**swapdb**

`swapdb`命令会交换两个db的数据，`swapdb`执行之后用户连接db无需再执行`select`，即可看到新的数据。

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

**zlexcount**

`zlexcount`命令用于sorted set中，和`zrangebylex`类似，不同的是`zrangebylex`返回member，而`zlexcount`是返回符合条件的member个数。

**memory**

Redis 4.0之前只能通过`info memory`来了解Redis内部有限的内存信息，Redis 4.0提供了 `memory` 命令，帮助用户全面了解Redis的内存状态。

```
127.0.0.1:6379> memory help
1) "MEMORY DOCTOR                        - Outputs memory problems report"
2) "MEMORY USAGE <key> [SAMPLES <count>] - Estimate memory usage of key"
3) "MEMORY STATS                         - Show memory usage details"
4) "MEMORY PURGE                         - Ask the allocator to release memory"
5) "MEMORY MALLOC-STATS                  - Show allocator internal stats"
```

-   `memory usage`

    `usage`子命令可以查看某个key在redis内部实际占用多少内存。

    **说明：** 

    -   不光key、value需要占用内存，Redis管理这些数据还需要一部分内存。

    -   对于hash、list、set、sorted set这些类型的key，结果是采样计算的，可以通过`SAMPLES`来控制采样数量。

-   `memory stats`

    ```
    27.0.0.1:6379> memory stats
           1) "peak.allocated"    // redis从启动到现在，历史最多使用过多少内存
           2) (integer) 423995952
           3) "total.allocated"    //当前使用内存
           4) (integer) 11130320
           5) "startup.allocated"    //redis启动初始化以后占用内存
           6) (integer) 9942928
           7) "replication.backlog"    //主从复制断开重连时会用到，默认10MB
           8) (integer) 1048576
           9) "clients.slaves"    // 主从复制用到的内存
          10) (integer) 16858
          11) "clients.normal"    //普通用户客户端的读写缓冲区
          12) (integer) 49630
          13) "aof.buffer"    //aof持久化使用的缓存和aofrewrite时产生的缓存之和
          14) (integer) 3253
          15) "db.0"    //每个db的元数据所占用内存
          16) 1) "overhead.hashtable.main"
              2) (integer) 5808
              3) "overhead.hashtable.expires" //管理带过期时间的数据所额外消耗内存
              4) (integer) 104
          17) "overhead.total"    //上面提到的各项内存消耗之和
          18) (integer) 11063904
          19) "keys.count"    //当前存储的key的总量
          20) (integer) 94
          21) "keys.bytes-per-key"    //当前内存中平均每个key大小
          22) (integer) 12631
          23) "dataset.bytes"        //用户数据所占用内存(= 总内存 - redis元数据所占内存)
          24) (integer) 66416
          25) "dataset.percentage"    //100 * dataset.bytes / (total.allocated - startup.allocated)
          26) "5.5934348106384277"
          27) "peak.percentage"    // 100 * total.allocated / peak_allocated
          28) "2.6251003742218018"
          29) "fragmentation"    //内存碎片率
          30) "1.1039986610412598"
    ```

-   `memory doctor`

    主要用于给一些诊断建议，提前发现潜在问题。

    ```
    Peak memory: peak.allocated/total.allocated > 1.5，此时内存碎片率可能比较高
      High fragmentation: fragmentation > 1.4，此时碎片率比较高
      Big slave buffers: 每个slave缓冲区的平均内存超过10MB，原因可能是master写入流量过高
      Big client buffers: 普通客户端缓冲区的平均内存超过200KB，原因可能是pipeline使用不当或者Pub/Sub客户端处理消息不及时导致
    ```

-   `malloc stats` & `malloc purge`

    这两个命令用于操作jemalloc，只在使用jemalloc的时候才有效。


## LFU机制与hotkey {#section_p5j_qdh_tdb .section}

Redis 4.0新增了allkey-lfu和volatile-lfu两种数据逐出策略，同时还可以通过`object`命令来获取某个key的访问频度。

```
object freq user_key
```

基于LFU机制，用户可以使用`scan + object freq`来发现热点key，当然Redis也一起发布了更好用的工具redis-cli，使用示例如下。

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

