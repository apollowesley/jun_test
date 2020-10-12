# 什么是云数据库Redis版 {#concept_m3j_s5z_sdb .concept}

云数据库Redis版（ApsaraDB for Redis）是兼容开源Redis协议标准、提供内存加硬盘的混合存储方式的数据库服务，基于高可靠双机热备架构及可平滑扩展的集群架构，满足高读写性能场景及弹性变配的业务需求。

## 为什么选择云数据库Redis版 {#section_g1v_gk5_i1p .section}

-   云数据库Redis版支持字符串（String）、链表（List）、集合（Set）、有序集合（Sorted Set）、哈希表（Hash）、流数据（Stream）等多种数据类型，及事务（Transaction）、消息订阅与发布（Pub/Sub）等高级功能。
-   通过“内存+硬盘”的存储方式，在提供高速数据读写能力的同时满足数据持久化需求。
-   硬件和数据部署在云端，有完善的基础设施规划、网络安全保障、系统维护服务，确保用户可以专注于业务创新。

## 学习路径 {#section_fdj_6vr_8bn .section}

您可以通过学习[云数据库Redis版学习路径](https://help.aliyun.com/product/26340.html)，由浅入深地学习运维云数据库Redis版。

## 实例架构 {#section_mn9_xx7_q4t .section}

云数据库Redis版支持灵活的部署架构，提供的实例架构有标准版-双副本以及集群版-双副本，能够满足不同的业务场景。

|实例架构|说明|
|----|--|
|[Redis标准版-双副本](intl.zh-CN/产品简介/产品系列/Redis标准版-双副本.md#)|系统工作时主节点（Master）和备节点（Replica）数据实时同步，主节点故障时系统自动秒级切换，备节点接管业务，全程自动且对业务无影响，主从架构保障系统服务具有高可用性。|
|[Redis集群版-双副本](intl.zh-CN/产品简介/产品系列/Redis集群版-双副本.md#)|集群（Cluster）实例采用分布式架构，每个节点都采用一主一从的高可用架构，能够进行自动容灾切换和故障迁移。提供多种集群规格可适配不同的业务压力，无限扩展数据库性能。|

## 规格性能 {#section_5gd_1tf_p34 .section}

云数据库Redis版各版本的常见规格（如连接数上限、CPU处理能力）、性能参考等信息，请参见[规格性能](intl.zh-CN/产品简介/规格性能.md#)。

## 应用场景 {#section_d95_jm9_9px .section}

关于典型的应用场景，请参见[应用场景](intl.zh-CN/产品简介/应用场景.md#)。

## 产品定价 {#section_rjh_x9i_dpr .section}

云数据库Redis版定价请参见[详细价格信息](https://www.aliyun.com/price/product#/kvstore/detail)。

更多信息请参见[计费方式](../../../../intl.zh-CN/产品定价/计费方式.md#)、[到期/欠费与续费](../../../../intl.zh-CN/产品定价/到期__欠费与续费.md#)、[变配说明](../../../../intl.zh-CN/产品定价/变配说明.md#)。

