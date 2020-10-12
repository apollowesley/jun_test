# Redis 4.0、codis、云数据库Redis版集群对比分析 {#concept_sqs_nsn_tdb .concept}

## 架构对比 { .section}

**Redis 4.0 cluster**

Redis 4.0版本的集群是去中心化的结构，集群元数据信息分布在每个节点上，主备切换依赖于多个节点协商选主。Redis 提供了redis-trib工具做部署集群及运维等操作。

客户端访问散列的db节点需依赖smart client，也就是客户端需要对redis返回的节点信息做判断选择路由等操作。例如客户端请求一个节点，如果所请求的key不在该节点上，客户端需要判断返回的move或ask等指令，重定向请求到对应的节点。

**codis cluster**

-   codis由3大组件构成：

    -   codis-server：修改过源码的redis，支持slot、扩容迁移等
    -   codis-proxy：支持多线程，go语言实现的内核
    -   codis Dashboard：集群管理工具
-   codis提供web图形界面管理集群。
-   集群元数据存在在zookeeper或etcd。
-   提供独立的组件codis-ha负责redis节点主备切换。
-   基于proxy的codis，客户端对路由表变化无感知。客户端需要从codis dashhoard调用`list proxy`命令获取所有proxy列表，并根据自身的轮询策略决定访问哪个proxy节点以实现负载均衡。

**云数据库Redis版**

云数据库Redis版集群版架构图如下：

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3217/15623115503546_zh-CN.png)

-   云数据库Redis版集群版由3大组件构成：
    -   redis-config : 集群管理工具，双节点，支持容灾。
    -   redis-server : 优化过源码的redis，支持slot、扩容迁移等。
    -   redis-proxy : 单线程，c++14语言实现的内核，无状态，一个集群根据集群规格可包含多个proxy节点。
-   集群元数据存储在meta db上。
-   提供独立的组件HA负责集群的主备切换等。
-   云数据库Redis版集群同样基于proxy，用户对路由信息无感知，同时提供vip给客户端访问，客户端只需一个连接地址即可，无须关心proxy访问的负载均衡等。

## 性能对比 { .section}

**压测环境**

在3台物理机上分别搭建了以上3种Redis集群。每台物理机千兆网卡、24核CPU、内存189 GB。3台物理机分别跑压测工具memtier\_benchmark、codis proxy/阿里云 proxy、redis server。Redis server使用各种集群配套的redis内核。

固定key size 32个字节，set/get操作比例为1:10。每个线程16个客户端。连续压测5分钟，分8个、 16个、 32个、 48个、 64个线程压测。

因为Redis 4.0集群需要额外的客户端选择节点，而memtier\_benchmark不支持，所以使用了hashtag来压测Redis 4.0。

每个集群有8个master db和8个slave db，aof打开。aof rewrite的最小buffer为64 MB。压测的对象分别为单个redis 4.0节点， 单个阿里云redis-proxy， 单核的codis-proxy，8核的codis-proxy。codis使用的go版本为1.7.4。

压测结果图如下：

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3217/15623115513548_zh-CN.png)

可以看出，单核的codis-proxy性能最弱。8核的codis-proxy压测没有对key使用hashtag，相当于将请求分散到后端8个db节点上， 也可以说相当于8个阿里云的redis-proxy，自然性能数据就比较高了。

单核的阿里云redis-proxy在压力够大的情况下性能逼近原生的redis db节点。在实际生产环境中，使用原生的redis cluster，客户端需要实现cluster protocol，解析`move`、`ask`等指令并重定向节点，随意访问key可能需要两次访问操作才能完成，性能上并不能完全如单节点一样。

## 支持特性对比 { .section}

**支持的不同协议对比**

|特性|Redis 4.0集群|codis集群|阿里云Redis集群|
|--|-----------|-------|----------|
|事务|支持相同slot|不支持|支持相同slot|
|sub/pub|支持相同slot|不支持|支持|
|flushall|支持|不支持|支持|
|select|不支持|不支持|支持|
|mset/mget|支持相同slot|支持|支持|

**水平扩展对比**

Redis 4.0 cluster、codis、阿里云redis分布式集群均实现了面对slot的管理，扩展的最小单元是slot。

分布式集群水平扩展的本质是对集群节点的路由信息管理以及数据的迁移。3种集群迁移数据的最小单位均是key。

**Redis cluster水平扩展原理**

Redis 4.0 cluster支持指定slot在节点中移动，也支持加入空节点后根据集群节点中已存在的slot分布自动进行再分布。以redis-trib的move\_slot为例解析slot移动的过程：

1.  调用`setslot`命令修改源、目标节点slot的状态。
2.  获取源节点上slot的key列表。
3.  调用`migrate`命令迁移 key，迁移过程中redis属于阻塞状态，只有目标节点restore成功后才返回。
4.  调用`setslot`命令修改源、目标节点slot的状态。

**迁移过程中如何保证数据的一致性？**

Redis cluster提供迁移状态中的重定向机制，向客户端返回ASK，客户端收到后需先发送`asking`指令到目标节点上，然后再发请求到目标节点上才可以访问。当访问的key满足以下全部条件时会出现重定向返回：

-   key所属slot在该节点上。如不在，返回的是MOVE。
-   slot处于迁移状态中。
-   key不存在。

如上所述，migrate是一个同步阻塞型的操作，如果key并不为空，即使slot处于迁移状态，key依然能被读写，以此保证数据的一致性。

**codis水平扩展原理**

codis对slot的再分布策略与redis cluster相同。codis-server内核并没有存储slot的信息，也不解析key所在的slot，只有在`dbadd`等操作时将对应的key记录到以slot为key的dict中。如果key带有tag，则将tag做crc32运算后将key插入到以crc32值为key的skiplist中。

codis Dashboard后台起迁移状态机程序，先确保通知到所有proxy开始迁移，即prepare阶段，如有一台以上proxy失败，则迁移任务失败。迁移步骤与redis cluster类似，不同点是：

-   slot状态信息存储在zookeeper/etcd。
-   发送`slotsmgrttagslot`而非`migrate`指令，`slotsmgrttagslot`执行时会随机获取一个key迁移，如key带有tag，则从上文中的skiplist获取所有key批量迁移。

**迁移过程中如何保证数据的一致性？**

codis同样也是同步阻塞型的迁移操作。在保持数据一致性方面，因为codis-server内核不维护slot的状态，所以一致性的保证落在了proxy组件上。codis-proxy在处理请求时，先判断key所在slot的状态，如slot处于迁移中，则向codis-server发起指定key迁移的命令，等key迁移完成后，codis-proxy转向目标的codis-server请求。做法简单，对redis内核修改较少，但同时也导致迁移慢，客户端卡住的时间较久。

**阿里云redis水平扩展原理**

阿里云redis除了提供指定源、节点、slot外，还提供按节点的容量、slot的大小等考量参数动态分配slot，以最小粒度影响集群可用性作为分配原则。迁移大体步骤如下：

1.  由redis-config计算源、目标节点、slot。
2.  redis-config向redis-server发送迁移slot指令。
3.  redis-server启动状态机，分批量迁移key。
4.  redis-config定时检查redis-server并更新slot状态。

**迁移过程中如何保证数据的一致性？**

与codis不同，阿里云redis在内核上同样维护了slot的信息，并且抛弃了codis迁移整个slot和redis cluster迁移单个key的做法，从内核上支持批量迁移，加快迁移速度。

阿里云redis迁移数据是异步的流程，不等待目标节点是否restore成功，由目标节点通知和源节点定时检查来验证是否成功。以此缩小同步阻塞对其他slot访问的影响。

同时也是因为迁移异步化，所以在保证数据一致性时，如果是写请求并且key存在于不在迁移的key列表中，走正常的写请求流程。其他数据一致性保证与redis 4.0 cluster相同。

阿里云redis-server优化了迁移大key的流程，详情见 [https://yq.aliyun.com/articles/64884](https://yq.aliyun.com/articles/64884) 。

## 其他 { .section}

|特性|redis 4.0|codis|阿里云redis|
|--|---------|-----|--------|
|内核热升级|不支持|不支持|支持|
|proxy热升级|无 proxy|不支持|支持|
|slots槽数|16384|1024|16384|
|密码|不支持，需改redis-trib脚本|支持，所有组件密码必须一致|支持|

阿里云redis内核和proxy的热升级过程中均不断连接，对客户端无影响。

