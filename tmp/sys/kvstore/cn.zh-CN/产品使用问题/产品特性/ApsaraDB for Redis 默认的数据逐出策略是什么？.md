# ApsaraDB for Redis 默认的数据逐出策略是什么？ {#concept_e5v_hs5_ydb .concept}

ApsaraDB for Redis 实例的默认逐出策略是 volatile-lru， 如需修改，可以登录控制台在系统参数中修改。

-   **volatile-lru**

    按照 LRU 算法逐出原有数据，但仅逐出设置了过期时间的数据。

-   **volatile-ttl**

    仅逐出设置了过期时间的数据，并且是按照 TTL 由小到大的顺序进行逐出。

-   **allkeys-lru**

    按照 LRU 算法逐出原有数据。

-   **volatile-random**

    随机逐出原有数据，但仅逐出设置了过期时间的数据。

-   **allkeys-random**

    随机逐出原有数据。

-   **noeviction**

    不逐出任何数据，新数据的写入会得到一个错误信息。


