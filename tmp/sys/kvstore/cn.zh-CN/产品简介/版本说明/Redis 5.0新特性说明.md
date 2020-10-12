# Redis 5.0新特性说明 {#concept_186983 .concept}

云数据库Redis 5.0版本大幅度优化内核，运行更加稳定，同时新增Stream、账号管理、审计日志等多种特性，满足您更多场景下的使用需求。

## 更新说明 {#section_bb3_8xb_oh6 .section}

-   新的数据类型：流数据（Stream）。详细说明请参见[Redis Streams](https://redis.io/topics/streams-intro)。
-   新增[账号管理](../../../../cn.zh-CN/用户指南/管理实例/账号管理.md#)功能。
-   新增日志管理功能，支持[审计日志](../../../../cn.zh-CN/用户指南/日志管理/开启日志审计.md#)、[运行日志](../../../../cn.zh-CN/用户指南/日志管理/查询运行日志.md#)和[慢日志](../../../../cn.zh-CN/用户指南/日志管理/查询慢日志.md#)，您可以通过日志管理查询读写操作、敏感操作（如KEYS、FLUSHALL）和管理类命令的使用记录以及慢日志。
-   新增基于快照的[缓存分析](../../../../cn.zh-CN/用户指南/缓存分析.md#)功能。
-   新的定时器（Timers）、集群（ Cluster）和字典（Dictionary）模块的API。
-   RDB中增加LFU和LRU信息。
-   集群管理器从Ruby （redis-trib.rb）移植到了redis-cli中的C语言代码。
-   新增有序集合（Sorted Set）命令[ZPOPMIN](https://redis.io/commands/zpopmin)、[ZPOPMAX](https://redis.io/commands/zpopmax)、[BZPOPMIN](https://redis.io/commands/bzpopmin)和[BZPOPMAX](https://redis.io/commands/bzpopmax)。
-   升级Active Defragmentation至v2版本。
-   增强HyperLogLog的实现。
-   优化内存统计报告。
-   为许多有子命令的命令增加了HELP子命令。
-   提高了客户端频繁连接和断开连接时的性能表现。
-   升级Jemalloc至5.1版本。
-   新增命令[CLIENT ID](https://redis.io/commands/client-id)和[CLIENT UNBLOCK](https://redis.io/commands/client-unblock)。
-   新增了为艺术而生的[LOLWUT](http://antirez.com/news/123)命令。
-   弃用slave术语（需要API向后兼容的情况例外）。
-   对网络层进行了多处优化。
-   进行了一些Lua相关的改进。
-   新增动态HZ（Dynamic HZ）以平衡空闲CPU使用率和响应性。
-   对Redis核心代码进行了重构并在许多方面进行了改进。

**说明：** 当前仅支持创建标准版架构的Redis 5.0实例，其它版本将陆续上线。

