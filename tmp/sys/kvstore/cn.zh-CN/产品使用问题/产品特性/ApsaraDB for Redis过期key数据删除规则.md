# ApsaraDB for Redis过期key数据删除规则 {#concept_tlj_rw5_ydb .concept}

ApsaraDB for Redis有2种方式来删除已过期的key：

-   主动过期，系统后台会周期性的检测，发现已过期的key时，会将其删除。
-   被动过期，当用户访问某个key时，如果该key已经过期，则将其删除。

如果问题还未能解决，请联系[售后技术支持](https://selfservice.console.aliyun.com/ticket/createIndex.htm?spm=0.0.0.0.fMsjx2)。

