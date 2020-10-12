# Redis 小版本最新特性介绍 {#concept_gt2_g5t_tdb .concept}

云数据库 Redis 版由资深阿里云专家对内核进行深度优化，修复安全漏洞，提升服务稳定性。同时由于客户需求的不断演进，云数据库 Redis 版也会通过对内核版本的优化逐步开放一些产品功能及 Redis 的原生命令的支持。

本文介绍云数据库 Redis 版的最新版本内核提供的产品特性及功能。您可以根据相应特性在控制台上一键操作将内核版本升级至最新版本。升级内核版本会出现30s内的连接闪断，请您在业务低峰期运行，并确保应用程序具备重连机制。

## 白名单 {#section_gnj_gyt_tdb .section}

标准版-双节点、标准版-单节点、集群版都配置支持用户自定义白名单。

## GEO 功能 {#section_hnj_gyt_tdb .section}

云数据库 Redis 版目前的版本为2.8，为了跟随 Redis 开源社区的发展脚步，云数据库 Redis 版目前已经全面支持 Redis 社区3.2版本的 GEO 功能。

## Config Get 命令 {#section_inj_gyt_tdb .section}

Config Get 命令放开限制。

## LUA 支持 {#section_jnj_gyt_tdb .section}

LUA 脚本放开限制，标准版-双节点、标准版-单节点支持用户直接调用。

集群版本条件性支持：

-   所有 key 都应该由 KEYS 数组来传递，redis.call/pcall 里面调用的 redis命令，key 的位置，必须是 KEYS array, 否则直接返回 error。

    ```
    `"-ERR bad lua script for redis cluster, all the keys that the script uses should be passed using the KEYS array\r\n"`
    ```

-   所有 key，必须在1个 slot 上，否则直接返回 error。

```
"-ERR eval/evalsha command keys must in same slot\r\n"
```


## Client list 命令 {#section_nnj_gyt_tdb .section}

Client list 命令放开限制，标准版-双节点，标准版-单节点支持用户调用，集群版本暂时不支持。

