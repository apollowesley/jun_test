# Redis 4.0、codis、云数据库 Redis 版集群对比分析 {#concept_sqs_nsn_tdb .concept}

## 架构对比 { .section}

**Redis 4.0 cluster**

Redis 4.0 版本的集群是去中心化的结构，集群元数据信息分布在每个节点上，主备切换依赖于多个节点协商选主。Redis 提供了 redis-trib 工具做部署集群及运维等操作。

客户端访问散列的 db 节点需依赖 smart client，也就是客户端需要对 redis 返回的节点信息做判断选择路由等操作。例如客户端请求一个节点，如果所请求的 key 不在该节点上，客户端需要判断返回的 move 或 ask 等指令，重定向请求到对应的节点。

**codis cluster**

-   codis 由3大组件构成：

    -   codis-server：修改过源码的 redis，支持 slot、扩容迁移等
    -   codis-proxy：支持多线程，go 语言实现的内核
    -   codis Dashboard：集群管理工具
-   codis 提供 web 图形界面管理集群。
-   集群元数据存在在 zookeeper 或 etcd。
-   提供独立的组件 codis-ha 负责 redis 节点主备切换。
-   基于 proxy 的 codis，客户端对路由表变化无感知。客户端需要从 codis dashhoard 调用 `list proxy` 命令获取所有 proxy 列表，并根据自身的轮询策略决定访问哪个 proxy 节点以实现负载均衡。

**云数据库 Redis 版**

云数据库 Redis 版集群版架构图如下：

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3217/3546_zh-CN.png)

-   云数据库 Redis 版集群版由3大组件构成：
    -   redis-config : 集群管理工具，双节点，支持容灾。
    -   redis-server : 优化过源码的 redis，支持 slot、扩容迁移等。
    -   redis-proxy : 单线程，c++14 语言实现的内核，无状态，一个集群根据集群规格可包含多个 proxy 节点。
-   集群元数据存储在 meta db 上。
-   提供独立的组件 HA 负责集群的主备切换等。
-   云数据库 Redis 版集群同样基于 proxy，用户对路由信息无感知，同时提供 vip 给客户端访问，客户端只需一个连接地址即可，无须关心 proxy 访问的负载均衡等。

## 性能对比 { .section}

**压测环境**

在3台物理机上分别搭建了以上3种 Redis 集群。每台物理机千兆网卡、24核 CPU、内存189 GB。3台物理机分别跑压测工具 memtier\_benchmark、codis proxy/阿里云 proxy、redis server。Redis server 使用各种集群配套的 redis 内核。

固定 key size 32个字节，set/get 操作比例为1:10。每个线程16个客户端。连续压测5分钟，分8个、 16个、 32个、 48个、 64个线程压测。

因为 Redis 4.0集群需要额外的客户端选择节点，而 memtier\_benchmark 不支持，所以使用了 hashtag 来压测 Redis 4.0。

每个集群有8个 master db 和8个 slave db，aof 打开。aof rewrite 的最小 buffer 为64 MB。压测的对象分别为单个 redis 4.0 节点， 单个阿里云 redis-proxy， 单核的 codis-proxy， 8核的 codis-proxy。codis 使用的 go 版本为1.7.4。

压测结果图如下：

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3217/3548_zh-CN.png)

可以看出，单核的 codis-proxy 性能最弱。8核的 codis-proxy 压测没有对 key 使用 hashtag，相当于将请求分散到后端8个 db 节点上， 也可以说相当于8个阿里云的 redis-proxy，自然性能数据就比较高了。

单核的阿里云 redis-proxy 在压力够大的情况下性能逼近原生的 redis db 节点。在实际生产环境中，使用原生的 redis cluster，客户端需要实现 cluster protocol， 解析 `move`、`ask` 等指令并重定向节点，随意访问 key 可能需要两次访问操作才能完成，性能上并不能完全如单节点一样。

## 支持特性对比 { .section}

**支持的不同协议对比**

|特性|Redis 4.0 集群|codis 集群|阿里云 Redis 集群|
|--|------------|--------|------------|
|事务|支持相同 slot|不支持|支持相同 slot|
|sub/pub|支持相同 slot|不支持|支持|
|flushall|支持|不支持|支持|
|select|不支持|不支持|支持|
|mset/mget|支持相同 slot|支持|支持|

**水平扩展对比**

Redis 4.0 cluster、codis、阿里云 redis 分布式集群均实现了面对 slot 的管理，扩展的最小单元是 slot。

分布式集群水平扩展的本质是对集群节点的路由信息管理以及数据的迁移。3种集群迁移数据的最小单位均是 key。

**Redis cluster 水平扩展原理**

Redis 4.0 cluster 支持指定 slot 在节点中移动，也支持加入空节点后根据集群节点中已存在的 slot 分布自动进行再分布。以 redis-trib 的 move\_slot 为例解析 slot 移动的过程：

1.  调用`setslot`命令修改源、目标节点 slot 的状态。
2.  获取源节点上 slot 的 key 列表。
3.  调用`migrate`命令迁移 key，迁移过程中 redis 属于阻塞状态，只有目标节点 restore 成功后才返回。
4.  调用`setslot`命令修改源、目标节点 slot 的状态。

**迁移过程中如何保证数据的一致性？**

Redis cluster 提供迁移状态中的重定向机制，向客户端返回 ASK，客户端收到后需先发送`asking`指令到目标节点上，然后再发请求到目标节点上才可以访问。当访问的 key 满足以下全部条件时会出现重定向返回：

-   key 所属 slot 在该节点上。如不在，返回的是 MOVE。
-   slot 处于迁移状态中。
-   key 不存在。

如上所述，migrate 是一个同步阻塞型的操作，如果 key 并不为空，即使 slot 处于迁移状态，key 依然能被读写，以此保证数据的一致性。

**codis 水平扩展原理**

codis 对 slot 的再分布策略与 redis cluster 相同。codis-server 内核并没有存储 slot 的信息，也不解析 key 所在的 slot，只有在`dbadd`等操作时将对应的 key 记录到以 slot 为 key 的 dict 中。如果 key 带有 tag，则将 tag 做 crc32 运算后将 key 插入到以 crc32值为 key 的 skiplist 中。

codis Dashboard 后台起迁移状态机程序，先确保通知到所有 proxy 开始迁移，即 prepare 阶段，如有一台以上 proxy 失败，则迁移任务失败。迁移步骤与 redis cluster 类似，不同点是：

-   slot 状态信息存储在 zookeeper/etcd。
-   发送`slotsmgrttagslot`而非`migrate`指令，`slotsmgrttagslot`执行时会随机获取一个 key 迁移，如 key 带有 tag，则从上文中的 skiplist 获取所有 key 批量迁移。

**迁移过程中如何保证数据的一致性？**

codis 同样也是同步阻塞型的迁移操作。在保持数据一致性方面，因为 codis-server 内核不维护 slot 的状态，所以一致性的保证落在了 proxy 组件上。codis-proxy 在处理请求时，先判断 key 所在 slot 的状态，如 slot 处于迁移中，则向 codis-server 发起指定 key 迁移的命令，等 key 迁移完成后，codis-proxy 转向目标的 codis-server 请求。做法简单，对 redis 内核修改较少，但同时也导致迁移慢，客户端卡住的时间较久。

**阿里云 redis 水平扩展原理**

阿里云 redis 除了提供指定源、节点、slot 外，还提供按节点的容量、slot 的大小等考量参数动态分配 slot，以最小粒度影响集群可用性作为分配原则。迁移大体步骤如下：

1.  由 redis-config 计算源、目标节点、slot。
2.  redis-config 向 redis-server 发送迁移 slot 指令。
3.  redis-server 启动状态机，分批量迁移 key。
4.  redis-config 定时检查 redis-server 并更新 slot 状态。

**迁移过程中如何保证数据的一致性？**

与 codis 不同，阿里云 redis 在内核上同样维护了 slot 的信息，并且抛弃了 codis 迁移整个 slot 和 redis cluster 迁移单个 key 的做法，从内核上支持批量迁移，加快迁移速度。

阿里云 redis 迁移数据是异步的流程，不等待目标节点是否 restore 成功，由目标节点通知和源节点定时检查来验证是否成功。以此缩小同步阻塞对其他 slot 访问的影响。

同时也是因为迁移异步化，所以在保证数据一致性时，如果是写请求并且 key 存在于不在迁移的 key 列表中，走正常的写请求流程。其他数据一致性保证与 redis 4.0 cluster 相同。

阿里云 redis-server 优化了迁移大 key 的流程，详情见 [https://yq.aliyun.com/articles/64884](https://yq.aliyun.com/articles/64884) 。

## 其他 { .section}

|特性|redis 4.0|codis|阿里云 redis|
|--|---------|-----|---------|
|内核热升级|不支持|不支持|支持|
|proxy 热升级|无 proxy|不支持|支持|
|slots 槽数|16384|1024|16384|
|密码|不支持，需改 redis-trib 脚本|支持，所有组件密码必须一致|支持|

阿里云 redis 内核和 proxy 的热升级过程中均不断连接，对客户端无影响。

