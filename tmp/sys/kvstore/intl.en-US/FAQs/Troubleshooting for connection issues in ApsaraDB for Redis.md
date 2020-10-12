# Troubleshooting for connection issues in ApsaraDB for Redis {#concept_gm5_rgv_fgb .concept}

ApsaraDB for Redis supports multiple connection methods. This topic describes the troubleshooting for different connection issues.

## Connection issues between an ApsaraDB for Redis instance and an ECS instance {#section_rhp_cmv_fgb .section}

The ApsaraDB for Redis instance only supports connections over an internal network. Therefore, make sure that the ECS instance be connected with the ApsaraDB for Redis instance. If these instances cannot interconnect with each other, possible causes are listed as follows:

-   The ApsaraDB for Redis instance and the ECS instance do not belong to the same account. Solution:
    -   Migrate the ApsaraDB for Redis instance to the virtual private cloud \(VPC\) of the account that the ECS instance belongs to. For more information about how to migrate the ApsaraDB for Redis instance across accounts, see [Use redis-shake to migrate data from an RDB file](../../../../reseller.en-US/User Guide/Migrate data/Migrate from on-premises Redis to ApsaraDB Redis/Use redis-shake to migrate data from an RDB file.md#).
    -   [Connect an ApsaraDB for Redis instance to an ECS instance across different accounts](reseller.en-US/FAQs/Connect an ApsaraDB for Redis instance to an ECS instance across different accounts.md#).
-   The ECS instance and the ApsaraDB for Redis instance run in different regions. Solution:

    Migrate one of the instances across regions , see [Use redis-shake to migrate data under the same account](../../../../reseller.en-US/User Guide/Migrate data/Migrate between ApsaraDB Redis instances/Use redis-shake to migrate data under the same account.md#).

-   The ECS instance and the ApsaraDB for Redis instance run in different types of networks. one runs in a classic network and the other runs in a VPC. Solution:
    -   Switch the network type of the Redis instance from classic network to VPC. For more information, see [Switch to VPC](../../../../reseller.en-US/User Guide/Manage instances/Switch to VPC.md#).
    -   [Connect ECS and ApsaraDB for Redis instances in different networks](reseller.en-US/FAQs/Connect ECS and ApsaraDB for Redis instances in different networks.md#).
-   The ECS security group rules restrict the connections to the connection address and port of the ApsaraDB for Redis instance. Solution:

    [Add security group rules](../../../../reseller.en-US/Security/Security groups/Add security group rules.md#)to allow the connections to the connection address and port of the ApsaraDB for Redis instance.

-   The whitelists of the ApsaraDB for Redis instance do not include the internal IP address of the ECS instance. Solution:

    [set the IP whitelists](../../../../reseller.en-US/User Guide/Manage instances/Set IP whitelists.md#)to include the internal IP address of the ECS instance.

    **Note:** In the case of the following error: `Caused by: redis.clients.jedis.exceptions.JedisConnectionException: java.net.ConnectException: Connection refused`, check the whitelists of the ApsaraDB for Redis instance. If the whitelist settings are correct and the ECS instance can reach the ApsaraDB for Redis instance by using [ping](../../../../reseller.en-US/Technical O&M/Network Connection/Use the Ping command to check the connection between an ECS instance and an ApsaraDB for Redis instance.md#) messages, check the connection configuration in your application.

-   An abnormal behavior on the ECS instance can trigger a security policy to disable the service. Multiple ECS instances normally connect to the ApsaraDB for Redis instance. Then, one of the ECS instance has a sudden connection issue. For example, the ECS instance can reach the ApsaraDB for Redis instance by using [ping](../../../../reseller.en-US/Technical O&M/Network Connection/Use the Ping command to check the connection between an ECS instance and an ApsaraDB for Redis instance.md#) messages, but the ECS instance fails to connect to port 6379 by using the [telnet](../../../../reseller.en-US/Technical O&M/Network Connection/Use the telnet command to check the connection to the service port of ApsaraDB for Redis.md#) command. In this case, the abnormal behavior such as outbound attacks on the ECS instance may cause the service to be disabled. Solution:

    Check the ECS instance and set precise outbound rules in a security group. For example, you can define that the ECS instance can only connect to the required IP address and port such as port 6379 of the ApsaraDB for Redis instance. If the issue persists, submit a ticket to request technical support.

-   During DNS resolution, the client has these errors: `UnknownHostException` or `failed to connect: r-***************.redis.rds.aliyuncs.com could not be resolved`. Solution:

    Use the ping message or the telnet command to test the result of DNS resolution for the ApsaraDB for Redis instance. If the resolution failed, check the DNS configuration.


**Note:** If these solutions are not available due to limited conditions, submit a ticket to create an ECS instance or ApsaraDB for Redis instance again and run both instances in the same VPC.

## Connect to an ApsaraDB for Redis instance from a public network {#section_35w_15v_yls .section}

To connect to an ApsaraDB for Redis instance from an on-premises server, see [Through the Internet](../../../../reseller.en-US/Quick Start/Step 3: Connect to the instance/Through the Internet.md#).

**Note:** We recommend that you connect the ECS instance to the ApsaraDB for Redis instance over the Alibaba Cloud intranet to improve security.

## Reset the password {#section_zgc_xqw_fgb .section}

If you forgot the password for connecting to the service, [reset the password](../../../../reseller.en-US/User Guide/Manage instances/Change the password.md#) in the console.

## Connect clients to an ApsaraDB for Redis instance {#section_lfg_znw_fgb .section}

You can connect Jedis, phpredis, redis-py, C/C++, .net, node-redis, and C\# clients to an ApsaraDB for Redis instance. For more information, see [Use a Redis client](../../../../reseller.en-US/Quick Start/Step 3: Connect to the instance/Use a Redis client.md#).

You can also use the Redis command line interface \(redis-cli\) program to connect to and manage the ApsaraDB for Redis instance. For more information, see [Use redis-cli](../../../../reseller.en-US/Quick Start/Step 3: Connect to the instance/Use redis-cli.md#).

**Note:** If you failed to connect to the ApsaraDB for Redis instance by using the clients or redis-cli, check [Connection issues between the ApsaraDB for Redis instance and the ECS instance](#).

## Client connection issues {#section_rls_4pw_fgb .section}

-   [Common Jedis exceptions](reseller.en-US/FAQs/Common Jedis exceptions in ApsaraDB for Redis.md#).
-   [Common errors for cluster instances of ApsaraDB for Redis](../../../../reseller.en-US/Technical O&M/Redis cluster instance common error returns information.md#).

## Bandwidth restrictions cause connection failures {#section_ups_trw_fgb .section}

Each type of ApsaraDB for Redis instance has a maximum bandwidth configured. For more information, see [Specifications and performance](../../../../reseller.en-US/Product Introduction/Specifications and performance.md#). If network resources are sufficient, the bandwidth is unlimited for ApsaraDB for Redis instances. However, if network resources are insufficient, the specified maximum bandwidth takes effect for the instances. In the case of heavy network traffic, the maximum bandwidth restricts service requests. Solution:

-   [Analyze the memory distribution on the instance](https://partners-intl.aliyun.com/help/doc-detail/50037.html) or [use the SCAN command](reseller.en-US/FAQs/How do I search for large keys?.md#) to find large keys and optimize the ApsaraDB for Redis service.
-   Upgrade the instance type to increase the bandwidth efficiency.
-   Use the same type of cluster instance to increase the bandwidth efficiency.

## Poor or failed connections due to performance issues {#section_trw_txw_fgb .section}

The KEYS \* and HGETALL commands may affect the performance of ApsaraDB for Redis, and cause thread blocking and connection issues. Solution:

-   Check [monitoring data](../../../../reseller.en-US/User Guide/Performance monitoring/Performance monitoring.md#) to identify the cause of the issue and take the corresponding measures.
-   Query slow logs, and optimize the ApsaraDB for Redis service based on slow log details. You can [view slow logs](../../../../reseller.en-US/User Guide/Log management/View slow logs.md#) in the console or by running the SHOW LOG command.
-   Do not run the KEYS, FLUSHALL, and FLUSHDB commands when the ApsaraDB for Redis instance is running in your business environment. You can disable these commands by using the RENAME mechanism of ApsaraDB for Redis, or run the SCAN command to process data gradually.
-   If your ApsaraDB for Redis instance uses engine version 4.0, you can use the UNLINK, FLUSHALL ASYNC, and FLUSHDB ASYNC commands and related parameters in the Lazyfree feature to optimize service code.
-   [Analyze the memory distribution on the instance](https://partners-intl.aliyun.com/help/doc-detail/50037.html) or [use the SCAN command](reseller.en-US/FAQs/How do I search for large keys?.md#) to find large keys and optimize the ApsaraDB for Redis service.
-   Optimize hotkeys. If you use a cluster instance, you can [analyze hotkeys on a specified child node](../../../../reseller.en-US/Best Practices/Analyze hotkeys in a specific sub-node of a cluster instance.md#).

## Summary {#section_pgd_xdx_fgb .section}

When the connection to the ApsaraDB for Redis instance is faulty, you can follow the preceding methods and locate issues related to [environment prerequisites](#), [connection methods](#), [error messages](#), [bandwidth restrictions](#), and [performance conditions](#).

