# 应用程序访问 ApsaraDB for Redis 超时后的重连机制 {#concept_cts_hzv_ydb .concept}

超时的原因可能是网络问题，也可能是其他服务器端的因素导致。

如果用户的应用程序访问 ApsaraDB for Redis 超时，则需要用户重新建立连接。如果用户设置了超时时间，由于 Redis 协议的请求和回复没有一个显示的对应关系，所以用户需要断开链接，否则将协议错乱。

需要说明的是，ApsaraDB for Redis 兼容 Redis 的各种客户端，但是有的 Redis 客户端是不具备自动重连机制的，所以用户需要在应用程序中重新建立连接。

如果问题还未能解决，请联系[售后技术支持](https://selfservice.console.aliyun.com/ticket/createIndex.htm?spm=5176.7738677.0.0.6NIvMr)。

