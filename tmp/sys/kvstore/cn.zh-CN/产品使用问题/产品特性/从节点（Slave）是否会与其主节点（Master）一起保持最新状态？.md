# 从节点（Slave）是否会与其主节点（Master）一起保持最新状态？ {#concept_nd4_bs5_ydb .concept}

主节点（Master）的更新会自动复制到其关联的从节点（Slave）。不过，鉴于Redis的异步复制技术，出于各种原因，Slave节点更新可能会落后于其Master节点。可能的原因包括，Master节点的I/O写入量超过了Slave节点同步的速度；或者Master节点和Slave节点之间有网络延迟。因此Slave节点与其Master节点之间可能存在滞后或在某一时候有一定程度上的数据不一致。

如果问题还未能解决，请联系[售后技术支持](https://selfservice.console.aliyun.com/ticket/createIndex.htm?spm=0.0.0.0.fwiOT5)。

