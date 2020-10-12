# Jedis连接池错误 {#concept_ird_fhz_xdb .concept}

使用Jedis连接池模式容易遇到无法获取连接池的错误如下所示。

```
redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
```

可以根据以以下几种原因进行分类排查。

**网络检查**

首先检查是否网络问题，可以通过`telnet host 6379`进行简单测试，连上之后`auth 密码`回车查看是否返回`+OK\r\n`，如果能够正确返回继续检查`ping`请求或者读写请求是否正常返回，操作多次排查网络问题影响。

**JedisPool连接数设置检查**

JedisPool使用的时候需要进行连接池的设置，用户在超过MaxTotal连接数的时候也会出现获取不到连接池的情况，这个时候可以在访问客户端上通过`netstat -an | grep 6379 | grep EST | wc -l`查看链接的客户端链接数目，并且比较这个数目和JedisPool配置的MaxTotal的值，如果没有明显超过或者接近就可以排除JedisPool连接池配置的影响。

**JedisPool连接池代码检查**

对于JedisPool连接池的操作，每次`getResource`之后需要调用`returnResource`或者`close`进行归还，可以查看代码是否是正确使用，代码示例如下：

```
JedisPoolConfig config = new JedisPoolConfig();
//最大空闲连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
config.setMaxIdle(200);
//最大连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
config.setMaxTotal(300);
config.setTestOnBorrow(false);
config.setTestOnReturn(false);
String host = "*.aliyuncs.com";
String password = "密码";
JedisPool pool = new JedisPool(config, host, 6379, 3000, password);
Jedis jedis = null;
try {
    jedis = pool.getResource();
    /// ... do stuff here ... for example
    jedis.set("foo", "bar");
    String foobar = jedis.get("foo");
    jedis.zadd("sose", 0, "car");
    jedis.zadd("sose", 0, "bike");
    Set<String> sose = jedis.zrange("sose", 0, -1);
} finally {
    if (jedis != null) {
        jedis.close();
    }
}
/// ... when closing your application:
pool.destroy();
```

**检查是否发生nf\_conntrack丢包**

通过`dmesg`检查客户端是否有异常。

```
nf_conntrack: table full, dropping packet
```

如果发生nf\_conntract丢包可以修改设置`sysctl -w net.netfilter.nf_conntrack_max=120000`。

**检查是否TIME\_WAIT问题**

通过`ss -s`查看time wait链接是否过多。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13764/15474311693932_zh-CN.png)

如果TIME\_WAIT过多可以修改以下参数：

```
sysctl -w net.ipv4.tcp_max_tw_buckets=180000
sysctl -w net.ipv4.tcp_tw_recycle=1
```

**检查是否DNS解析问题**

通过在/etc/hosts文件直接绑定host地址，绑定完成之后查看问题是否还存在，如果还存在则不是DNS解析问题。

```
192.168.1.1  *.redis.rds.aliyuncs.com
```

**需要帮助**

如果按照上面排查之后还有问题可以通过抓包并将报错时间点、报错信息、抓包文件发送给阿里云售后同学进行分析。抓包命令为：

```
sudo tcpdump -i eth0 tcp and port 6379 -n -nn -s 74 -w redis.cap
```

。

