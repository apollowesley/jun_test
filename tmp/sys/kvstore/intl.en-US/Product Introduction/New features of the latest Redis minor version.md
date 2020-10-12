# New features of the latest Redis minor version {#concept_gt2_g5t_tdb .concept}

ApsaraDB for Redis was created when senior Alibaba Cloud experts performed in-depth kernel optimization on the Redis service, fixed security vulnerabilities, and improved the service’s stability.  At the same time, due to the evolution of customer demand,  the ApsaraDB for Redis kernel version has been optimized to provide more product functions and support for native Redis commands.

This document will introduce the product features and functions provided by the latest ApsaraDB for Redis version. The latest version of the kernel provides product features and features. If you like the new features, you can upgrade to the latest kernel version with a single click on the console.  Upgrading the kernel version will interrupt your connection for 30 seconds, so do it during low business hours and make sure your applications have reconnect mechanisms.

## IP Whitelist {#section_gnj_gyt_tdb .section}

The standard version dual- and single-node configurations, and the cluster version al support custom whitelists.

## GEO function {#section_hnj_gyt_tdb .section}

The current version of ApsaraDB for Redis is 2.8. To keep pace with the development of the Redis open-source community, ApsaraDB for Redis already fully supports the GEO feature that comes with Redis community version 3.2. Function.

## Config Get Command {#section_inj_gyt_tdb .section}

Restrictions on the use of the Config Get command are removed.

## Support for LUA {#section_jnj_gyt_tdb .section}

Restrictions on the use of LUA scripts are removed. You can directly call this command in standard version dual- and single-node configurations.

The cluster version provides conditional support:

-   All keys must be transmitted in the KEYS array. When the redis command in redis.call/pcall is called, the key location must be KEYS array, or an error will be reported.

    ```
    `"-ERR bad lua script for redis cluster, all the keys that the script uses should be passed using the KEYS array\r\n"`
    ```

-   All keys must be in a single slot, or an error will be reported.  

```
"-ERR eval/evalsha command keys must in same slot\r\n"
```


## Client list command {#section_nnj_gyt_tdb .section}

Restrictions on the use of the Client list command are removed. You can call this command in standard version dual- and single-node configurations, but the cluster version does not support the command for the moment.

