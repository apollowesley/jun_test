# Jedis常见异常汇总 {#concept_r54_txs_xdb .concept}

Jedis虽然使用起来比较简单，但是如果用户不能根据使用场景设置合理的参数（例如连接池参数），如果用户不合理地使用了一些功能（例如Lua和事务）还是会产生很多问题，本文对这些问题逐一说明：

## 例外列表 { .section}

-   [一、redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc1)
-   [二、redis.clients.jedis.exceptions.JedisConnectionException: Unexpected end of stream](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc2)
-   [三、redis.clients.jedis.exceptions.JedisDataException: ERR illegal address](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc3)
-   [四、redis.clients.jedis.exceptions.JedisDataException: ERR max number of clients reached](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc4)
-   [五、redis.clients.jedis.exceptions.JedisConnectionException: java.net.SocketTimeoutException: Read timed out](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc5)
-   [六、redis.clients.jedis.exceptions.JedisDataException: NOAUTH Authentication required](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc6)
-   [七、redis.clients.jedis.exceptions.JedisDataException: EXECABORT Transaction discarded because of previous errors](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc7)
-   [八、java.lang.ClassCastException: java.lang.Long cannot be cast to java.util.List](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc8)
-   [九、redis.clients.jedis.exceptions.JedisDataException: WRONGTYPE Operation against a key holding the wrong kind of value](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc9)
-   [十、redis.clients.jedis.exceptions.JedisDataException: OOM command not allowed when used memory \> ‘maxmemory’](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc10)
-   [十一、redis.clients.jedis.exceptions.JedisDataException: LOADING Redis is loading the dataset in memory](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc11)
-   [十二、redis.clients.jedis.exceptions.JedisDataException: BUSY Redis is busy running a script. You can only call SCRIPT KILL or SHUTDOWN NOSAVE.](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc12)
-   [十三、redis.clients.jedis.exceptions.JedisConnectionException: java.net.SocketTimeoutException: connect timed out](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc13)
-   [十四、UNKILLABLE Sorry the script already executed write commands against the dataset. You can either wait the script termination or kill the server in a hard way using the SHUTDOWN NOSAVE command.](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc14)
-   [十五、java.lang.NoClassDefFoundError](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc15)
-   [十六、redis.clients.jedis.exceptions.JedisDataException: ERR unknown command](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc16)
-   [十七、redis.clients.jedis.exceptions.JedisDataException: Please close pipeline or multi block before calling this method.](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc17)
-   [十八、redis.clients.jedis.exceptions.JedisDataException: ERR command role not support for normal user.](https://help.aliyun.com/knowledge_detail/71967.html?spm=a2c4g.11186631.2.1.EXAZqr#cc18)

## 一.无法从连接池获取到Jedis连接 { .section}

**1.异常堆栈**

\(1\) 连接池参数blockWhenExhausted = true（默认）

如果连接池没有可用Jedis连接，会等待maxWaitMillis（毫秒），如果依然没有获取到可用Jedis连接，会抛出如下异常：

```
redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
    …
Caused by: java.util.NoSuchElementException: Timeout waiting for idle object
    at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:449)
```

\(2\) 连接池参数blockWhenExhausted = false

设置该参数，如果连接池没有可用的Jedis连接，立即抛出异常：

```
redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
    …
Caused by: java.util.NoSuchElementException: Pool exhausted
    at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:464)
```

**2.异常描述**

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13758/15395701003933_zh-CN.png)

上述异常是客户端没有从连接池（最大数量maxTotal）获得可用的Jedis连接造成的，可能有如下原因：

\(1\) 连接泄露 （**较为常见**）

JedisPool默认的maxTotal=8，下面的代码从JedisPool中获取了8个Jedis资源，但是没有归还资源，当第9次尝试获取Jedis资源的时候，无法获得（jedisPool.getResource\(\).ping\(\)）

```
GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);
//向JedisPool借用8次连接，但是没有执行归还操作。
for (int i = 0; i < 8; i++) {
    Jedis jedis = null;
    try {
        jedis = jedisPool.getResource();
        jedis.ping();
    } catch (Exception e) {
        logger.error(e.getMessage(), e);
    }
}
jedisPool.getResource().ping();
```

所以推荐使用的代码规范是：

```
执行命令如下：
Jedis jedis = null;
try {
    jedis = jedisPool.getResource();
    //具体的命令
    jedis.executeCommand()
} catch (Exception e) {
    //如果命令有key最好把key也在错误日志打印出来，对于集群版来说通过key可以帮助定位到具体节点。
    logger.error(e.getMessage(), e);
} finally {
    //注意这里不是关闭连接，在JedisPool模式下，Jedis会被归还给资源池。
    if (jedis != null) 
        jedis.close();
}
```

\(2\) 业务并发量大，maxTotal设置得过小了。

举个例子：

-   一次命令时间（borrow|return resource + Jedis执行命令\(含网络\) ）的平均耗时约为1ms，一个连接的QPS大约是1000

-   业务期望的QPS是50000


那么理论上需要的资源池大小是50000 / 1000 = 50个，实际maxTotal可以根据理论值进行微调。

\(3\) Jedis连接阻塞

例如Redis发生了阻塞（例如慢查询等原因），所有连接在超时时间范围内等待，并发量较大时，会造成连接池资源不足。

\(4\) Jedis连接被拒绝

从池子里拿连接，由于没有空闲连接，需要重新生成一个Jedis连接，但是连接被拒绝：

```
redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
    at redis.clients.util.Pool.getResource(Pool.java:50)
    at redis.clients.jedis.JedisPool.getResource(JedisPool.java:99)
    at TestAdmin.main(TestAdmin.java:14)
Caused by: redis.clients.jedis.exceptions.JedisConnectionException: java.net.ConnectException: Connection refused
    at redis.clients.jedis.Connection.connect(Connection.java:164)
    at redis.clients.jedis.BinaryClient.connect(BinaryClient.java:80)
    at redis.clients.jedis.BinaryJedis.connect(BinaryJedis.java:1676)
    at redis.clients.jedis.JedisFactory.makeObject(JedisFactory.java:87)
    at org.apache.commons.pool2.impl.GenericObjectPool.create(GenericObjectPool.java:861)
    at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:435)
    at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:363)
    at redis.clients.util.Pool.getResource(Pool.java:48)
    ... 2 more
Caused by: java.net.ConnectException: Connection refused
    at java.net.PlainSocketImpl.socketConnect(Native Method)
    at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:339)
    at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:200)
    at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:182)
    at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
    at java.net.Socket.connect(Socket.java:579)
    at redis.clients.jedis.Connection.connect(Connection.java:158)
    ... 9 more
```

可以从at redis.clients.jedis.Connection.connect\(Connection.java:158\)中看到实际是一个Socket连接：

```
 socket.setSoLinger(true, 0); // Control calls close () method,
        // the underlying socket is closed
        // immediately
        // <-@wjw_add
158:  socket.connect(new InetSocketAddress(host, port), connectionTimeout);
```

**说明：** **一般这种需要检查Redis的域名配置是否正确，排查该段时间网络是否正常**

\(4\) 其他问题

例如丢包、DNS、客户端TCP参数配置，具体可以参考：[Jedis介绍及常见问题分析](https://yq.aliyun.com/articles/73894?spm=5176.8091938.0.0.iOI0kR)

**3.解决方法**

从上述分析，可以看出这个问题的原因比较复杂，请不要简单地认为连接池不够就盲目加大maxTotal，需要具体问题具体分析。

连接池参数优化可以参考：[JedisPool资源池优化](https://yq.aliyun.com/articles/236383?spm=5176.8091938.0.0.qVO71y)

**4.处理途径**

请客户先按照上述表述进行问题定位和故障排除，如无法自行解决故障，请提交工单。

## 二、客户端缓冲区异常 { .section}

**1.异常堆栈**

```
redis.clients.jedis.exceptions.JedisConnectionException: Unexpected end of stream.
    at redis.clients.util.RedisInputStream.ensureFill(RedisInputStream.java:199)
    at redis.clients.util.RedisInputStream.readByte(RedisInputStream.java:40)
    at redis.clients.jedis.Protocol.process(Protocol.java:151)
......
```

**2.异常描述**

这个异常是客户端缓冲区异常，产生这个问题可能有三个原因：

\(1\) 常见原因：多个线程使用一个Jedis连接，正常的情况是一个线程使用一个Jedis连接，可以使用JedisPool管理Jedis连接，实现线程安全，避免出现这种情况。例如下面代码就是两个线程共用了一个Jedis连接：

```
new Thread(new Runnable() {
    public void run() {
        for (int i = 0; i < 100; i++) {
            jedis.get("hello");
        }
    }
}).start();
new Thread(new Runnable() {
    public void run() {
        for (int i = 0; i < 100; i++) {
            jedis.hget("haskey", "f");
        }
    }
}).start();
```

\(2\) 客户端缓冲区满了

Redis有三种客户端缓冲区：

-   普通客户端缓冲区\(normal\)：用于接受普通的命令，例如get、set、mset、hgetall、zrange等。
-   slave客户端缓冲区\(slave\)：用于同步master节点的写命令，完成复制。
-   发布订阅缓冲区\(pubsub\)：pubsub不是普通的命令，因此有单独的缓冲区。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13758/15395701013934_zh-CN.png)

Redis客户端缓冲区配置的格式是：

```
client-output-buffer-limit <class> <hard limit> <soft limit> <soft seconds>
```

-   class: 客户端类型：可选值为normal、slave和pubsub。

-   hard limit: 如果客户端使用的输出缓冲区大于hard limit，客户端会被立即关闭，单位为秒。

-   soft limit和soft seconds: 如果客户端使用的输出缓冲区超过了soft limit并且持续了soft limit秒，客户端会被立即关闭，单位为秒。


例如下面是一份Redis缓冲区的配置，所以当条件满足时，客户端连接会被关闭，就会出现`Unexpected end of stream`。

```
redis> config get client-output-buffer-limit
1) "client-output-buffer-limit"
2) "normal 524288000 0 0 slave 2147483648 536870912 480 pubsub 33554432 8388608 60"
```

\(3\) 长时间闲置连接会被服务端主动断开，可以查询timeout配置的设置以及自身连接池配置确定是否需要做空闲检测。

**3.解决方法和处理途径**

**客户**：排查自身代码是否使用JedisPool管理Jedis连接，是否存在并发操作Jedis的情况。

**工单**: 排查是否上述[\(2\)](#p_qwd_qct_xdb)或[\(3\)](#p_l3l_tct_xdb)原因。云数据库Redis版默认的timeout值为0， 目前不支持修改。client-output-buffer-limit默认值为500MB，为阿里云优化后的合理值。如果超过该值，说明用户返回的值过多， 出于性能和稳定性考虑， 可建议用户优化应用程序。

## 三、非法的客户端地址 （阿里云Redis提供客户端白名单功能） { .section}

**1.异常堆栈**

```
Caused by: redis.clients.jedis.exceptions.JedisDataException: ERR illegal address
    at redis.clients.jedis.Protocol.processError(Protocol.java:117)
    at redis.clients.jedis.Protocol.process(Protocol.java:151)
    at redis.clients.jedis.Protocol.read(Protocol.java:205)
    ......
```

**2.异常描述**

Redis实例配置了白名单，但当前访问Redis的客户端（IP）不在白名单中。

**3.解决方法**

添加该客户端（IP）的白名单。

**4.处理途径**

客户自行操作或者提交工单。

## 四、客户端连接数达到最大值 { .section}

**1.异常堆栈**

```
redis.clients.jedis.exceptions.JedisDataException: ERR max number of clients reached
```

**2.异常描述**

客户端连接数超过了Redis实例配置的最大maxclients。

**3.解决方法**

用户可提工单，工作人员可临时调大最大连接数，帮助用户找到连接数暴涨的原因（因为上述调整只是临时调整）。

**4.处理途径**

-   工单：临时调整最大连接数，协助定位问题。
-   客户：定位自身问题（可以定位连接最多的客户端），找到问题原因（例如连接池配置等）

## 五、客户端读写超时 { .section}

**1.异常堆栈**

```
redis.clients.jedis.exceptions.JedisConnectionException: java.net.SocketTimeoutException: Read timed out
```

**2.异常描述**

问题原因可能有如下几种：\(1\) 读写超时设置的过短。\(2\) 有慢查询或者Redis发生阻塞。\(3\) 网络不稳定。

**3.解决方法**

客户提供读写超时时间，提交工单定位相关原因。

**4.处理途径**

工单。

## 六、密码相关的异常 { .section}

**1.异常堆栈**

Redis设置了密码鉴权，客户端请求没有提供密码：

```
Exception in thread "main" redis.clients.jedis.exceptions.JedisDataException: NOAUTH Authentication required.
     at redis.clients.jedis.Protocol.processError(Protocol.java:127)
     at redis.clients.jedis.Protocol.process(Protocol.java:161)
     at redis.clients.jedis.Protocol.read(Protocol.java:215)
```

Redis没有设置密码鉴权，客户端请求中包含了密码：

```
Exception in thread "main" redis.clients.jedis.exceptions.JedisDataException: ERR Client sent AUTH, but no password is set
     at redis.clients.jedis.Protocol.processError(Protocol.java:127)
     at redis.clients.jedis.Protocol.process(Protocol.java:161)
     at redis.clients.jedis.Protocol.read(Protocol.java:215)
```

客户端传输了错误的密码：

```
redis.clients.jedis.exceptions.JedisDataException: ERR invalid password
    at redis.clients.jedis.Protocol.processError(Protocol.java:117)
    at redis.clients.jedis.Protocol.process(Protocol.java:151)
    at redis.clients.jedis.Protocol.read(Protocol.java:205)
```

**2.解决方法：弄清楚到底有没有设置密码鉴权，是否提供了正确的密码。**

## 七、事务异常 { .section}

**1.异常堆栈**

```
redis.clients.jedis.exceptions.JedisDataException: EXECABORT Transaction discarded because of previous errors
```

**2.异常描述**

这个是Redis的事务异常：事务中包含了错误的命令，例如如下sett是个不存在的命令。

```
127.0.0.1:6379> multi
OK
127.0.0.1:6379> sett key world
(error) ERR unknown command 'sett'
127.0.0.1:6379> incr counter
QUEUED
127.0.0.1:6379> exec
(error) EXECABORT Transaction discarded because of previous errors.
```

**3.解决方法和处理途径**

**客户**修复自身代码错误。

## 八、类转换错误 { .section}

**1.异常堆栈**

```
java.lang.ClassCastException: java.lang.Long cannot be cast to java.util.List
         at redis.clients.jedis.Connection.getBinaryMultiBulkReply(Connection.java:199)
         at redis.clients.jedis.Jedis.hgetAll(Jedis.java:851)
         at redis.clients.jedis.ShardedJedis.hgetAll(ShardedJedis.java:198)
```

```
java.lang.ClassCastException: java.util.ArrayList cannot be cast to [B
         at redis.clients.jedis.Connection.getBinaryBulkReply(Connection.java:182)
         at redis.clients.jedis.Connection.getBulkReply(Connection.java:171)
         at redis.clients.jedis.Jedis.rpop(Jedis.java:1109)
         at redis.clients.jedis.ShardedJedis.rpop(ShardedJedis.java:258)
.......
```

**2.异常描述**

Jedis正确的使用方法是：一个线程操作一个Jedis，如果多个线程操作同一个Jedis连接就会发生此类错误。使用JedisPool可避免此类问题。例如如下代码在两个线程并发使用了一个Jedis（get、hgetAll返回不同的类型）。

```
new Thread(new Runnable() {
    public void run() {
        for (int i = 0; i < 100; i++) {
            jedis.set("hello", "world");
            jedis.get("hello");
        }
    }
}).start();
new Thread(new Runnable() {
    public void run() {
        for (int i = 0; i < 100; i++) {
            jedis.hset("hashkey", "f", "v");
            jedis.hgetAll("hashkey");
        }
    }
}).start();
```

**3.解决方法和处理途径**

客户排查自身代码是否存在上述问题

## 九、命令使用错误 { .section}

**1.异常堆栈**

```
Exception in thread "main" redis.clients.jedis.exceptions.JedisDataException: WRONGTYPE Operation against a key holding the wrong kind of value
    at redis.clients.jedis.Protocol.processError(Protocol.java:127)
    at redis.clients.jedis.Protocol.process(Protocol.java:161)
    at redis.clients.jedis.Protocol.read(Protocol.java:215)
.....
```

**2.异常描述**

例如key=”hello”是字符串类型的键，而hgetAll返回哈希类型的键，所以出现了错误。

```
jedis.set("hello","world");
jedis.hgetAll("hello");
```

**3.解决方法和处理途径**

请客户修改自身代码错误。

## 十、Redis使用的内存超过maxmemory配置 { .section}

**1.异常堆栈**

```
redis.clients.jedis.exceptions.JedisDataException: OOM command not allowed when used memory > 'maxmemory'.
```

**2.异常描述**

Redis节点（如果是集群，则是其中一个节点）使用内存大于该实例的内存规格（maxmemory配置）。

**3.解决方法**

原因可能有如下几种：

-   业务数据正常增加
-   客户端缓冲区异常：例如monitor、pub/sub使用不当等等
-   纯缓存使用场景，但是maxmemory-policy配置有误（例如没有设置过期键的业务配置volatile-lru）

紧急处理，可以提交工单临时调整maxmeory，后续咨询客户是否升配或者调整配置。

**4.处理途径**

-   客户：找到内存增大的原因。
-   工单：协助临时调整maxmeomry，如果客户需要调整配置，可以协助解决。

## 十一、Redis正在加载持久化文件 { .section}

**1.异常堆栈**

```
redis.clients.jedis.exceptions.JedisDataException: LOADING Redis is loading the dataset **in** memory
```

**2.异常描述**

Jedis调用Redis时，如果Redis正在加载持久化文件，无法进行正常的读写。

**3.解决方法**

正常情况下，阿里云Redis不会出现这种情况，如果出现，则提交工单处理。

**4.处理途径**

工单。

## 十二、Lua脚本超时 { .section}

**1.异常堆栈**

```
redis.clients.jedis.exceptions.JedisDataException: BUSY Redis is busy running a script. You can only call** SCRIPT **KILL **or** SHUTDOWN NOSAVE.
```

**2.异常描述**

如果Redis当前正在执行Lua脚本，并且超过了lua-time-limit，此时Jedis调用Redis时，会收到上述异常。

**3.解决方法**

按照异常提示：`You can only call SCRIPT KILL or SHUTDOWN NOSAVE.`,使用`script kill`终止Lua脚本。

**4.处理途径**

客户最好自行处理，如果解决不了，值班人员可以协助操作。

## 十三 连接超时 { .section}

**1.异常堆栈**

```
redis.clients.jedis.exceptions.JedisConnectionException: java.net.SocketTimeoutException: connect timed out
```

**2.异常描述**

可能原因有如下几种：

-   连接超时设置的过短。
-   tcp-backlog满，造成新的连接失败。
-   客户端与服务端网络故障。

**3.解决方法**

客户提供连接超时时间，提交工单定位相关原因。

**4.处理途径**

工单。

**十四 Lua脚本写超时**

**1.异常堆栈**

```
(error) UNKILLABLE Sorry the script already executed write commands against the dataset. You can either wait the script termination or kill the server in a hard way using the SHUTDOWN NOSAVE command.
```

**2.异常描述**

如果Redis当前正在执行Lua脚本，并且超过了lua-time-limit，**并且已经执行过写命令**，此时Jedis调用Redis时，会收到上述异常

**3.解决方法**

提交工单做紧急处理，管理员需要重启或者切换Redis节点。

**4.处理途径**

工单。

## 十五、类加载错误 { .section}

**1.异常堆栈**

例如找不到类和方法：

```
Exception in thread "commons-pool-EvictionTimer" java.lang.NoClassDefFoundError: redis/clients/util/IOUtils
    at redis.clients.jedis.Connection.disconnect(Connection.java:226)
    at redis.clients.jedis.BinaryClient.disconnect(BinaryClient.java:941)
    at redis.clients.jedis.BinaryJedis.disconnect(BinaryJedis.java:1771)
    at redis.clients.jedis.JedisFactory.destroyObject(JedisFactory.java:91)
    at         org.apache.commons.pool2.impl.GenericObjectPool.destroy(GenericObjectPool.java:897)
    at org.apache.commons.pool2.impl.GenericObjectPool.evict(GenericObjectPool.java:793)
    at org.apache.commons.pool2.impl.BaseGenericObjectPool$Evictor.run(BaseGenericObjectPool.java:1036)
    at java.util.TimerThread.mainLoop(Timer.java:555)
    at java.util.TimerThread.run(Timer.java:505)
Caused by: java.lang.ClassNotFoundException: redis.clients.util.IOUtils
......
```

**2.异常描述**

运行时，Jedis执行命令，抛出异常，提示某个类找不到。此类问题一般都是由于加载多个jedis版本（例如jedis 2.9.0和jedis 2.6），在编译期间代码未出现问题，但类加载器在运行时加载了低版本的Jedis，造成运行时找不到类。

**3.解决方法**

通常此类问题，可以将重复的Jedis排除掉，例如利用maven的依赖树，把无用的依赖去掉或者exclusion掉。

**4.处理途径**

客户排查自身代码。

## 十六、服务端命令不支持 { .section}

**1.异常堆栈**

例如客户端执行了geoadd命令，但是服务端返回不支持此命令。

```
redis.clients.jedis.exceptions.JedisDataException: ERR unknown command 'GEOADD'
```

**2.异常描述**

该命令不能被Redis端识别，可能有两个原因：

-   社区版的一些命令，阿里云Redis的不支持，或者只在某些小版本上支持，例如geoadd是Redis 3.2添加的地理信息API。
-   命令本身是错误的，不过对于Jedis来说还好，Jedis不支持直接组装命令，每个API都有固定的函数。

**3.解决方法**

咨询是否有Redis版本支持该命令，如支持可以让客户做小版本升级。

**4.处理途径**

-   管理员：确认版本是否支持该命令。
-   客户：确认后，做小版本升级。

## 十七、pipeline错误使用 { .section}

**1.异常堆栈**

```
redis.clients.jedis.exceptions.JedisDataException: Please close pipeline **or** multi **block** before calling this **method**.
```

**2.异常描述**

在pipeline.sync\(\)执行之前，通过response.get\(\)获取值，在pipeline.sync\(\)执行前，命令没有执行（可以通过monitor做验证），下面代码就会引起上述异常。

```
Jedis jedis = new Jedis("127.0.0.1", 6379);
Pipeline pipeline = jedis.pipelined();
pipeline.set("hello", "world"); 
pipeline.set("java", "jedis");
Response<String> pipeString = pipeline.get("java");
//这个get必须在sync之后，如果是批量获取值建议直接用List<Object> objectList = pipeline.syncAndReturnAll();
System.out.println(pipeString.get());
//命令此时真正执行
pipeline.sync();
```

Jedis中Reponse的get\(\)方法，有一个判断，如果set=false就会报错，而response中的set初始化为false。

```
public T get() {
  // if response has dependency response and dependency is not built,
  // build it first and no more!!
  if (dependency != null && dependency.set && !dependency.built) {
    dependency.build();
  }
  if (!set) {
    throw new JedisDataException(
        "Please close pipeline or multi block before calling this method.");
  }
  if (!built) {
    build();
  }
  if (exception != null) {
    throw exception;
  }
  return response;
}
```

pipeline.sync\(\)会每个结果设置set=true。

```
public void sync() {
  if (getPipelinedResponseLength() > 0) {
    List<Object> unformatted = client.getAll();
    for (Object o : unformatted) {
      generateResponse(o);
    }
  }
}
```

其中generateResponse\(o\):

```
protected Response<?> generateResponse(Object data) {
  Response<?> response = pipelinedResponses.poll();
  if (response != null) {
    response.set(data);
  }
  return response;
}
```

其中response.set\(data\);

```
public void set(Object data) {
    this.data = data;
    set = true;
}
```

**3.解决方法**

对于批量结果的解析，建议使用pipeline.syncAndReturnAll\(\)来实现，下面操作模拟了批量hgetAll

```
/**
* pipeline模拟批量hgetAll
* @param keyList
* @return
*/
public Map<String, Map<String, String>> mHgetAll(List<String> keyList) {
// 1.生成pipeline对象
Pipeline pipeline = jedis.pipelined();
// 2.pipeline执行命令，注意此时命令并未真正执行
for (String key : keyList) {
  pipeline.hgetAll(key);
}
// 3.执行命令 syncAndReturnAll()返回结果
List<Object> objectList = pipeline.syncAndReturnAll();
if (objectList == null || objectList.isEmpty()) {
  return Collections.emptyMap();
}
// 4.解析结果
Map<String,Map<String, String>> resultMap = new HashMap<String, Map<String,String>>();
for (int i = 0; i < objectList.size(); i++) {
  Object object = objectList.get(i);
  Map<String, String> map = (Map<String, String>) object;
  String key = keyList.get(i);
  resultMap.put(key, map);
}
return resultMap;
}
```

**4.处理途径**

修改业务代码。

## 十八、管理员命令，普通用户不能执行 { .section}

**1.异常堆栈**

命令role不能被普通用户执行，详情可参考[暂未开放的Redis命令](../../../../cn.zh-CN/快速入门/支持的 Redis 命令.md#section_xgx_zxy_xdb)。

```
redis.clients.jedis.exceptions.JedisDataException: ERR command role not support **for** normal user
```

**2.异常描述**

该命令尚未开放。

**3.解决方法：**

不能使用该命令，如果有需求或者疑问可以联系值班人员。

**4.处理途径**

从文档中确认该命令是否开放。

## 其他问题： { .section}

**1.Jedis版本如何选择？**

原则上选择最新的[release版本](https://github.com/xetorthio/jedis/releases)，但最好选择发行一段时间后的版本，因为jedis历史上出现过一次问题较大的release版本，目前来说2.9.0比较稳定。

```
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>2.9.0</version>
    <type>jar</type>
    <scope>compile</scope>
</dependency>
```

**2.Jedis中的JedisCluster是阿里云Redis集群版的客户端吗？**

不是，请使用阿里云集群版的客户端，直接使用Jedis和JedisPool即可。因为官方集群和阿里云Redis集群是不同的架构，具体参考：[Redis 4.0、codis、云数据库 Redis 版集群对比分析](cn.zh-CN/常见问题/Redis 4.0、codis、云数据库 Redis 版集群对比分析.md#)

## 连接池参数 { .section}

**1. 资源设置和使用**

|序号|参数名|含义|默认值|使用建议|
|--|---|--|---|----|
|1|maxTotal|资源池中最大连接数|8|-|
|2|maxIdle|资源池允许最大空闲的连接数|8|-|
|3|minIdle|资源池确保最少空闲的连接数|0|-|
|4|blockWhenExhausted|当资源池用尽后，调用者是否要等待。只有当为true时，下面的maxWaitMillis才会生效|true|建议使用默认值|
|5|maxWaitMillis|当资源池连接用尽后，调用者的最大等待时间（单位为毫秒）|-1：表示永不超时|不建议使用默认值|
|6|testOnBorrow|向资源池借用连接时是否做连接有效性检测（ping），无效连接会被移除|false|业务量很大时候建议设置为false（设置为true会多一次ping的开销）。|
|7|testOnReturn|向资源池归还连接时是否做连接有效性检测（ping），无效连接会被移除|false|业务量很大时候建议设置为false（设置为true会多一次ping的开销）。|
|8|jmxEnabled|是否开启jmx监控，可用于监控|true|建议开启，但应用本身也要开启|

**2.空闲资源监测**

空闲Jedis对象检测，下面四个参数组合来完成，testWhileIdle是该功能的开关。

|序号|参数名|含义|默认值|使用建议|
|--|---|--|---|----|
|1|testWhileIdle|是否开启空闲资源监测|false|设置为true|
|2|timeBetweenEvictionRunsMillis|空闲资源的检测周期（单位为毫秒）|-1：不检测|建议设置，周期自行选择，也可以默认也可以使用下面JedisPoolConfig中的配置|
|3|minEvictableIdleTimeMillis|资源池中资源最小空闲时间（单位为毫秒），达到此值后空闲资源将被移除|1000 \* 60 \* 30 = 30分钟|可根据自身业务决定，大部分默认值即可，也可以考虑使用下面JeidsPoolConfig中的配置|
|4|numTestsPerEvictionRun|做空闲资源检测时，每次的采样数|3|可根据自身应用连接数进行微调，如果设置为-1，就是对所有连接做空闲监测|

