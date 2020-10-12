# Redis 读写分离技术解析 {#concept_qn2_yjx_wdb .concept}

## 背景 { .section}

云数据库 Redis 版不管主从版还是集群规格，slave 作为备库不对外提供服务，只有在发生 HA 的时候，slave 提升为 master 后才承担读写流量。这种架构读写请求都在 master 上完成，一致性较高，但性能受到 master 数量的限制。经常有用户数据较少，但因为流量或者并发太高而不得不升级到更大的集群规格。

为满足读多写少的业务场景，最大化节约用户成本，云数据库 Redis 版推出了读写分离规格，为用户提供透明、高可用、高性能、高灵活的读写分离服务。

## 架构 { .section}

Redis 集群模式有 redis-proxy、master、slave、HA 等几个角色。在读写分离实例中，新增 readonly slave 角色来承担读流量，slave 作为热备不提供服务，架构上保持对现有集群规格的兼容性。redis-proxy 按权重将读写请求转发到 master 或者某个 readonly slave 上；HA 负责监控 DB 节点的健康状态，异常时发起主从切换或重搭 readonly slave，并更新路由。

一般来说，根据 master 和 readonly slave 的数据同步方式，可以分为两种架构：星型复制和链式复制。

**星型复制**

星型复制就是将所有的 readonly slave 直接和 master 保持同步，每个 readonly slave 之间相互独立，任何一个节点异常不影响到其他节点，同时因为复制链比较短，readonly slave 上的复制延迟比较小。

Redis 是单进程单线程模型，主从之间的数据复制也在主线程中处理，readonly slave 数量越多，数据同步对 master 的 CPU 消耗就越严重，集群的写入性能会随着 readonly slave 的增加而降低。此外，星型架构会让 master 的出口带宽随着 readonly slave 的增加而成倍增长。master 上较高的 CPU 和网络负载会抵消掉星型复制延迟较低的优势，因此，星型复制架构会带来比较严重的扩展问题，整个集群的性能会受限于 master。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3169/3189_zh-CN.png)

**链式复制**

链式复制将所有的 readonly slave 组织成一个复制链，如下图所示，master 只需要将数据同步给 slave 和复制链上的第一个 readonly slave。

链式复制解决了星型复制的扩展问题，理论上可以无限增加 readonly slave 的数量，随着节点的增加整个集群的性能也可以基本上呈线性增长。

链式复制的架构下，复制链越长，复制链末端的 readonly slave 和 master 之间的同步延迟就越大，考虑到读写分离主要使用在对一致性要求不高的场景下，这个缺点一般可以接受。但是如果复制链中的某个节点异常，会导致下游的所有节点数据都会大幅滞后。更加严重的是这可能带来全量同步，并且全量同步将一直传递到复制链的末端，这会对服务带来一定的影响。为了解决这个问题，读写分离的 redis 都使用阿里云优化后的 binlog 复制版本，最大程度的降低全量同步的概率。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3169/3191_zh-CN.png)

结合上述的讨论和比较，redis 读写分离选择链式复制的架构。

## Redis 读写分离优势 { .section}

**透明兼容**

读写分离和普通集群规格一样，都使用了 redis-proxy 做请求转发，多 shard 时部分命令使用存在一定的限制，但从主从升级单分片读写分离，或者从集群升级到多分片的读写分离集群可以做到完全兼容。

用户和 redis-proxy 建立连接，redis-proxy 会识别出客户端连接发送过来的请求是读还是写，然后按照权重作负载均衡，将请求转发到后端不同的 DB 节点中，写请求转发给 master，读操作转发给 readonly slave（master 默认也提供读，可以通过权重控制）。

用户只需要购买读写分离规格的实例，直接使用任何客户端即可直接使用，业务不用做任何修改就可以开始享受读写分离服务带来的巨大性能提升，接入成本几乎为0。

**高可用**

高可用模块（HA）监控所有 DB 节点的健康状态，为整个实例的可用性保驾护航。master 宕机时自动切换到新主。如果某个 readonly slave 宕机，HA 也能及时感知，然后重搭一个新的 readonly slave，下线宕机节点。

除 HA 之外，redis-proxy 也能实时感知每个 readonly slave 的状态。在某个 readonly slave 异常期间，redis-proxy 会自动降低这个节点的权重，如果发现某个 readonly slave 连续失败超过一定次数以后，会暂时屏蔽异常节点，直到异常消失以后才会恢复其正常权重。

redis-proxy 和 HA 一起做到尽量减少业务对后端异常的感知，提高服务可用性。

**高性能**

对于读多写少的业务场景，直接使用集群版本往往不是最合适的方案，现在读写分离提供了更多的选择，业务可以根据场景选择最适合的规格，充分利用每一个 readonly slave 的资源。

目前单 shard 对外售卖 1 master + 1/3/5 readonly slave 多种规格（如果有更大的需求可以提工单反馈），提供 60万 QPS 和 192 MByte/s 的服务能力，在完全兼容所有命令的情况下突破单机的资源限制。后续将去掉规格限制，让用户根据业务流量随时自由的增加或减少 readonly slave 数量。

|规格|QPS|带宽|
|--|---|--|
|1 master|8-10万读写|10-48 MB|
|1 master + 1 readonly\_slave|10万写 + 10万读|20-64 MB|
|1 master + 3 readonly\_slave|10万写 + 30万读|40-128 MB|
|1 master + 5 readonly\_slave|10万写 + 50万读|60-192 MB|

**后续**

redis 主从异步复制，从 readonly slave 中可能读到旧的数据，使用读写分离需要业务可以容忍一定程度的数据不一致，后续将会给客户更灵活的配置和更大的自由，比如配置可以容忍的最大延迟时间。

