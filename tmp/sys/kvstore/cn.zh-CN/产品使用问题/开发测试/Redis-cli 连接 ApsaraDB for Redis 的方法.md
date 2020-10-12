# Redis-cli 连接 ApsaraDB for Redis 的方法 {#concept_zbx_bz5_ydb .concept}

Redis-cli 连接 ApsaraDB for Redis 的命令如下：

```
*redis-cli -h 实例连接地址 -a 实例id:密码*
```

**说明：** 

ApsaraDB for Redis 需要同节点的 ECS 云服务器才能连接，因为ApsaraDB for Redis 只有内网访问，因此无法从外部网络和其他节点的主机通过外网进行连接。

如问题还未解决，请联系[售后技术支持](https://selfservice.console.aliyun.com/ticket/createIndex.htm?spm=0.0.0.0.PirkRq)。

