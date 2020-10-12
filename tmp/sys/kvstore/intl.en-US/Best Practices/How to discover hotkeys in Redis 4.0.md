# How to discover hotkeys in Redis 4.0 {#concept_pw3_snd_ggb .concept}

High performance is the most prominent feature of Redis. A robust Redis performance is crucial to ensure the service availability. A reduced Redis performance can be caused by multiple reasons. The hotkey problem is one of the most common reasons. The discovery of hotkeys is the first step to improve Redis performance. This topic describes how to use the new features of Redis 4.0 to discover the hotkeys.

## Background {#section_c2w_mqd_ggb .section}

Redis 4.0 added two data eviction strategies: allkey-lfu and volatile-lfu. You can also run the OBJECT command to obtain the access frequency of a specific key, as shown in the following figure.

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83080/156205758435190_en-US.png)

The native Redis client also added the --hotkeys option to help you discover hotkeys in your business.

**Note:** This topic describes how to discover hotkeys to optimize the performance of Redis. This topic is suitable for users who are familiar with the basic features of ApsaraDB for Redis and are seeking advanced skills. If you are not familiar with Redis, we recommend that you read [Product Overview](../../../../reseller.en-US/Product Introduction/What is ApsaraDB for Redis.md#).

## Prerequisites {#section_pc1_p5d_ggb .section}

-   You have activated an ECS instance that can interconnect with the ApsaraDB for Redis instance.
-   You have installed a Redis version later than Redis 4.0 on the ECS instance.

    **Note:** You can use the redis-cli tool based on these prerequisites.

-   The maxmemory-policy parameter of the ApsaraDB for Redis instance is set to volatile-lfu or allkeys-lfu.

    **Note:** For more information about how to modify the parameters, see [Parameter settings ](../../../../reseller.en-US/User Guide/Manage instances/Parameter settings .md#).


## Procedure {#section_dhf_ytd_ggb .section}

1.  When there is an ongoing business, use the following command to query the hotkey.

    ``` {#codeblock_z1k_8tu_um9}
    redis-cli -h r-***************.redis.rds.aliyuncs.com -a <password> --hotkeys
    ```

    **Note:** This topic uses [redis-benchmark](../../../../reseller.en-US/Product Usage/Product features/Description of redis-benchmark.md#) to simulate a scenario featuring a high volume of writes.

    |Option|Description|
    |------|-----------|
    |-h|Specifies the server hostname.|
    |-a|Specifies the password for Redis Auth.|
    |--hotkeys|Used to query hotkeys.|


## Results {#section_j3l_twd_ggb .section}

The following example shows the result of running this command.

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83080/156205758435214_en-US.png)

The summary part in the result is the hotkey.

