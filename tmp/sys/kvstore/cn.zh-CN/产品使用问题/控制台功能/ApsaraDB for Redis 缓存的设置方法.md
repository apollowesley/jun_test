# ApsaraDB for Redis 缓存的设置方法 {#concept_gtx_qvv_ydb .concept}

当您购买的缓存空间满后，系统将根据您设置的缓存策略清理过期数据，您可以在 [ApsaraDB for Redis 控制台](https://kvstore.console.aliyun.com/)的**实例列表** \> **系统参数** \> **maxmemory-policy** 设置缓存策略，默认是 VolatileLRU 按照 LRU 算法逐出原有数据，但仅逐出设置了过期时间的数据。

如问题还未解决,请联系[售后技术支持](https://selfservice.console.aliyun.com/ticket/createIndex.htm?spm=0.0.0.0.B9ykXS)。

