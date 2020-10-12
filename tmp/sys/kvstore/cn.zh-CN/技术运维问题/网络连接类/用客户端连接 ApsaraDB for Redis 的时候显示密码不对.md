# 用客户端连接 ApsaraDB for Redis 的时候显示密码不对 {#concept_hft_tyv_ydb .concept}

首先请确认您的 ApsaraDB for Redis 实例密码是否输入正确，如有必要可以通过控制台来修改密码。

如果您确认密码正确但用客户端连接 ApsaraDB for Redis 时显示密码不对，请检查您是否按照要求的格式输入了鉴权信息。ApsaraDB for Redis 的鉴权信息包括了（instanceId:password）两部分，请检查您在程序中是否输入了完整信息。

以 Java 代码为例，正确的代码应该是：

```
Jedis jedis = new Jedis(host, port);//鉴权信息由用户名:密码拼接而成jedis.auth(“instance_id:password”);
```

如果您在代码中只输入了 password，如下

```
Jedis jedis = new Jedis(host, port);//鉴权信息缺少了instance_idjedis.auth(“password”);//错误
```

则在连接 ApsaraDB for Redis 时会得到如下的出错信息：

```
redis.clients.jedis.exceptions.JedisDataException: ERR Authentication failed.
```

如果问题还未能解决，请联系[售后技术支持](https://selfservice.console.aliyun.com/ticket/createIndex.htm?spm=5176.7738677.0.0.6NIvMr)。

