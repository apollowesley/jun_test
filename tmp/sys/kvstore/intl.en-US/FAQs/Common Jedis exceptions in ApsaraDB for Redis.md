# Common Jedis exceptions in ApsaraDB for Redis {#concept_r54_txs_xdb .concept}

Jedis is simple to use. However, if you cannot set valid parameters such as JedisPool parameters that are suitable for your scenarios and if you use some features such as Lua and transactions in an incorrect way, some issues may occur. This topic describes how to solve these issues.

## List of exceptions {#section_m0c_psj_mjh .section}

-   [1. redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool](#section_sgn_0ax_lf3)
-   [2. redis.clients.jedis.exceptions.JedisConnectionException: Unexpected end of stream](#section_k50_qpm_mu0)
-   [3. redis.clients.jedis.exceptions.JedisDataException: ERR illegal address](#section_gmp_kwv_db7)
-   [4. redis.clients.jedis.exceptions.JedisDataException: ERR max number of clients reached](#section_g03_wtz_i03)
-   [5. redis.clients.jedis.exceptions.JedisConnectionException: java.net.SocketTimeoutException: Read timed out](#section_1qd_jdc_ig1)
-   [6. redis.clients.jedis.exceptions.JedisDataException: NOAUTH Authentication required](#section_0qr_d4w_du0)
-   [7. redis.clients.jedis.exceptions.JedisDataException: EXECABORT Transaction discarded because of previous errors](#section_zdp_sho_y4w)
-   [8. java.lang.ClassCastException: java.lang.Long cannot be cast to java.util.List](#section_qsr_5n7_usl)
-   [9. redis.clients.jedis.exceptions.JedisDataException: WRONGTYPE Operation against a key holding the wrong kind of value](#section_km5_l5n_fck)
-   [10. redis.clients.jedis.exceptions.JedisDataException: OOM command not allowed when used memory \> ‘maxmemory’](#section_nyj_w1a_fyb)
-   [11. redis.clients.jedis.exceptions.JedisDataException: LOADING Redis is loading the dataset in memory](#section_t46_w1k_i85)
-   [12. redis.clients.jedis.exceptions.JedisDataException: BUSY Redis is busy running a script. You can only call SCRIPT KILL or SHUTDOWN NOSAVE](#section_3iy_lwd_goq)
-   [13. redis.clients.jedis.exceptions.JedisConnectionException: java.net.SocketTimeoutException: connect timed out](#section_vyb_g4r_iux)
-   [14. UNKILLABLE Sorry the script already executed write commands against the dataset. You can either wait the script termination or kill the server in a hard way using the SHUTDOWN NOSAVE command](#section_qui_r07_p83)
-   [15. java.lang.NoClassDefFoundError](#section_710_3wv_7eu)
-   [16. redis.clients.jedis.exceptions.JedisDataException: ERR unknown command](#section_0v6_q4s_p9w)
-   [17. redis.clients.jedis.exceptions.JedisDataException: Please close pipeline or multi block before calling this method](#section_9pb_naf_1xg)
-   [18. redis.clients.jedis.exceptions.JedisDataException: ERR command role not support for normal user](#section_3cg_efg_stb)

## 1. Failure to obtain Jedis connections from JedisPool {#section_sgn_0ax_lf3 .section}

**1.1. Exception stack**

\(1\) The JedisPool parameter blockWhenExhausted is set to true by default.

If no Jedis connection is available in JedisPool, the Jedis client waits for a period in milliseconds as specified in the maxWaitMillis parameter. Afterward, if the Jedis connection is still not available, the Jedis client throws the following exception:

``` {#codeblock_ree_iu1_w3g}
redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
    …
Caused by: java.util.NoSuchElementException: Timeout waiting for idle object
    at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:449)
```

\(2\) The JedisPool parameter blockWhenExhausted is set to false.

When you set this parameter in this way, if no Jedis connection is available in JedisPool, the Jedis client directly throws the following exception:

``` {#codeblock_p1m_8pr_irq}
redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
    …
Caused by: java.util.NoSuchElementException: Pool exhausted
    at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:464)
```

**1.2. Exception description**

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13758/15623114803933_en-US.png)

The preceding exception occurs when the client fails to obtain any Jedis connection from JedisPool. You can set the maxTotal parameter to specify the maximum number of available connections in JedisPool. Possible causes are listed as follows:

\(1\) Connection exposure \(**a common cause**\)

The value of maxTotal is set to 8 by default. In the following code, the client has obtained eight Jedis connections from JedisPool, and not returned these connections. When the client attempts to obtain the ninth Jedis connection by using jedisPool.getResource\(\).ping\(\), the attempt fails.

``` {#codeblock_kmh_umu_dcw}
GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);
//The client borrows connections from JedisPool for eight times, but does not return these connections.
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

We recommend that you follow these code rules:

``` {#codeblock_clr_rkb_4oh}
Run the following command:
Jedis jedis = null;
try {
    jedis = jedisPool.getResource();
    // The specific service request that the client processes after obtaining a Jedis connection.
    jedis.executeCommand()
} catch (Exception e) {
    //If the command contains a key, we recommend that you also print the key in the error log. You can use the key to locate the node where the exception occurs in a cluster.
    logger.error(e.getMessage(), e);
} finally {
    //The client does not close a borrowed Jedis connection. Instead, the client returns the connection to JedisPool when you use this resource pool.
    if (jedis ! = null) 
        jedis.close();
}
```

\(2\) For a high concurrency level, the maxTotal parameter is set to a low value.

The following example describes this issue:

-   The time for running a command to obtain a resource consists of the time for borrowing and returning the resource, the time for JedisPool to run the command, and the time for network connection. The time is approximately 1 ms on average. The QPS for each connection is approximately 1,000.

-   The required QPS is 50,000.


Therefore, the total number of available connections in JedisPool is theoretically 50 \(50,000/1,000 = 50\), and you can fine-tune the actual value of maxTotal according to the theoretical value.

\(3\) Jedis connection blocking

For example, connections to Redis are blocked in the conditions such as slow query. All connections wait for a period less than the timeout value. In this case, a high concurrency level can result in insufficient resources in JedisPool.

\(4\) Jedis connection denied

When the client attempts to obtain a connection from JedisPool, JedisPool has to generate a connection due to the lack of idle connections. However, the new connection is denied, as shown in the following code:

``` {#codeblock_ngr_tvk_khv}
redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
    at redis.clients.util.Pool.getResource(Pool.java:50)
    at redis.clients.jedis.JedisPool.getResource(JedisPool.java:99)
    At testadmin. Main (testadmin. Java: 14)
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

The code at Redis. clients. jedis. connection. connect \(connection. java: 158\) indicates that the connection is a socket connection as follows:

``` {#codeblock_84v_z5t_zoy}
 socket.setSoLinger(true, 0); // Control calls close () method,
        // the underlying socket is closed
        // immediately
        // <-@wjw_add
158:  socket.connect(new InetSocketAddress(host, port), connectionTimeout);
```

**Note:** In this case, you must check whether the domain name configuration for ApsaraDB for Redis is correct and whether network conditions are normal during this period less than the timeout value.

\(4\) Other issues

**1.3. Solutions**

Based on the preceding analysis, the causes of the failure to obtain connections from JedisPool are complex. If you want to provide sufficient resources in JedisPool, increasing the value of maxTotal is not the only solution. The solution varies according to actual conditions.

**1.4. Approaches**

You can locate the issue and perform troubleshooting according to the preceding description. If the issue persists, submit a ticket.

## 2. Client buffer exception {#section_k50_qpm_mu0 .section}

**2.1. Exception stack**

``` {#codeblock_97i_136_elv}
redis.clients.jedis.exceptions.JedisConnectionException: Unexpected end of stream.
    at redis.clients.util.RedisInputStream.ensureFill(RedisInputStream.java:199)
    at redis.clients.util.RedisInputStream.readByte(RedisInputStream.java:40)
    at redis.clients.jedis.Protocol.process(Protocol.java:151)
......
```

**2.2. Exception description**

This client buffer exception may occur due to the following causes:

\(1\) Common cause: multiple threads use the same Jedis connection. Normally, one thread uses one Jedis connection. You can use JedisPool to manage Jedis connections to secure threads and avoid this issue. The following example shows that two threads share one Jedis connection:

``` {#codeblock_dgu_u3b_oo1}
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

\(2\) The client buffer is full.

ApsaraDB for Redis provides three types of client buffers:

-   Normal client buffer: receives normal commands, such as GET, SET, MSET, HGETALL, and ZRANGE.
-   Replica client buffer: synchronizes write commands from a master node during replications.
-   PUBSUB buffer: the PUBSUB command is not a normal command, so the command has an independent buffer.

You can configure the client buffer for ApsaraDB for Redis in the following way:

``` {#codeblock_ze2_zga_7xz}
client-output-buffer-limit <class> <hard limit> <soft limit> <soft seconds>
```

-   class: specifies the type of the client. Valid values: normal, slave, and pubsub.
-   hard limit: if a client uses the output buffer that is more than the value of hard limit, the system terminates the client immediately. Unit: bytes.
-   soft limit and soft seconds: if a client uses the output buffer more than the value of soft limit \(unit: bytes\) and if the client uses the output buffer for a period of time equal to the value of soft seconds \(unit: seconds\), the system terminates the client immediately.

The following example shows the buffer configuration for ApsaraDB for Redis. In the specified condition, the system terminates the client immediately and shows the exception `Unexpected end of stream`.

``` {#codeblock_xkz_rqw_qpo}
redis> config get client-output-buffer-limit
1) "client-output-buffer-limit"
2) "normal 524288000 0 0 slave 2147483648 536870912 480 pubsub 33554432 8388608 60"
```

\(3\) The Redis service automatically disconnects a long idle connection. You can check the timeout setting and JedisPool configuration to determine whether the idle connection detection is required.

**3. Solutions and approaches**

**Customer**: you can check the code of your service to make sure that you use JedisPool to manage Jedis connections and check whether multiple threads share one Jedis connection.

**Ticket**: you can submit a ticket to check whether the preceding condition \(2\) or \(3\) causes the issue. The value of timeout is 0 in ApsaraDB for Redis by default. You cannot modify this value. The value of client-output-buffer-limit is 500 MB by default. This is a value that Alibaba Cloud has optimized. If the value of the parameter is more than 500 MB, the client returns too many values. In this case, to ensure performance and stability of the service, we recommend that you optimize the application.

## 3. Invalid client address \(ApsaraDB for Redis provides a whitelist of clients\) {#section_gmp_kwv_db7 .section}

**3.1. Exception stack**

``` {#codeblock_bw3_rij_o6b}
Caused by: redis.clients.jedis.exceptions.JedisDataException: ERR illegal address
    at redis.clients.jedis.Protocol.processError(Protocol.java:117)
    at redis.clients.jedis.Protocol.process(Protocol.java:151)
    at redis.clients.jedis.Protocol.read(Protocol.java:205)
    ......
```

**3.2. Exception description**

The ApsaraDB for Redis instance has a whitelist of clients configured. The IP address of the current client that connects to the instance does not exist in the whitelist.

**3.3. Solutions**

Add the IP address of the current client to the whitelist.

**3.4. Approaches**

You can perform troubleshooting or submit a ticket.

## 4. The maximum number of client connections reached {#section_g03_wtz_i03 .section}

**4.1. Exception stack**

``` {#codeblock_ybq_aml_dlf}
redis.clients.jedis.exceptions.JedisDataException: ERR max number of clients reached
```

**4.2. Exception description**

The number of client connections is more than the value of maxclients configured on an ApsaraDB for Redis instance.

**4.3. Solutions**

You can submit a ticket to temporarily increase the value of maxclients and locate the cause of the connection burst.

**4.4. Approaches**

-   Ticket: you can submit a ticket to temporarily adjust the value of maxclients and locate the cause of the connection burst.
-   Customer: you can locate the client that most frequently connects to the ApsaraDB for Redis instance, and analyze the cause, such as JedisPool configuration.

## 5. Client read and write timeout {#section_1qd_jdc_ig1 .section}

**5.1. Exception stack**

``` {#codeblock_co4_2us_nl6}
redis.clients.jedis.exceptions.JedisConnectionException: java.net.SocketTimeoutException: Read timed out
```

**5.2. Exception description**

The possible causes of the exception are listed as follows: \(1\) The read and write timeout values are too low. \(2\) Slow query or connection blocking occurs. \(3\) The network is unstable.

**5.3. Solutions**

You can submit a ticket and provide read and write timeout values for troubleshooting purposes.

**5.4. Approaches**

You can submit a ticket for troubleshooting purposes.

## 6. Exceptions related to passwords {#section_0qr_d4w_du0 .section}

**6.1. Exception stack**

ApsaraDB for Redis has password verification configured. But a client does not provide any password in a request as shown in the following code:

``` {#codeblock_ugi_s5v_utu}
Exception in thread "main" redis.clients.jedis.exceptions.JedisDataException: NOAUTH Authentication required.
     at redis.clients.jedis.Protocol.processError(Protocol.java:127)
     at redis.clients.jedis.Protocol.process(Protocol.java:161)
     at redis.clients.jedis.Protocol.read(Protocol.java:215)
```

ApsaraDB for Redis does not have password verification configured. But a client provides a password in a request as shown in the following code:

``` {#codeblock_v87_hcf_8ee}
Exception in thread "main" redis.clients.jedis.exceptions.JedisDataException: ERR Client sent AUTH, but no password is set
     at redis.clients.jedis.Protocol.processError(Protocol.java:127)
     at redis.clients.jedis.Protocol.process(Protocol.java:161)
     at redis.clients.jedis.Protocol.read(Protocol.java:215)
```

A client provides an incorrect password in a request as shown in the following code:

``` {#codeblock_zbs_vuu_tud}
redis.clients.jedis.exceptions.JedisDataException: ERR invalid password
    at redis.clients.jedis.Protocol.processError(Protocol.java:117)
    at redis.clients.jedis.Protocol.process(Protocol.java:151)
    at redis.clients.jedis.Protocol.read(Protocol.java:205)
```

**6.2. Solutions: check whether the service has password authentication configured and whether the client provides a correct password.**

## 7. Transaction exception {#section_zdp_sho_y4w .section}

**7.1. Exception stack**

``` {#codeblock_ctp_5h3_f5f}
redis.clients.jedis.exceptions.JedisDataException: EXECABORT Transaction discarded because of previous errors
```

**7.2. Exception description**

This is a transaction exception in ApsaraDB for Redis. The transaction contains an incorrect command, such as the unknown sett command in the following code:

``` {#codeblock_50i_6g2_oyq}
127.0.0.1:6379> multi
OK
127.0.0.1:6379> sett key world
(error) ERR unknown command 'sett'
127.0.0.1:6379> incr counter
QUEUED
127.0.0.1:6379> exec
(error) EXECABORT Transaction discarded because of previous errors.
```

**7.3. Solutions and approaches**

**Customer**: you can check the code of your service for troubleshooting purposes.

## 8. Class conversion error {#section_qsr_5n7_usl .section}

**8.1. Exception stack**

``` {#codeblock_5kx_gy0_t5q}
java.lang.ClassCastException: java.lang.Long cannot be cast to java.util.List
         at redis.clients.jedis.Connection.getBinaryMultiBulkReply(Connection.java:199)
         at redis.clients.jedis.Jedis.hgetAll(Jedis.java:851)
         at redis.clients.jedis.ShardedJedis.hgetAll(ShardedJedis.java:198)
```

``` {#codeblock_cx1_6zv_pmf}
java.lang.ClassCastException: java.util.ArrayList cannot be cast to [B
         at redis.clients.jedis.Connection.getBinaryBulkReply(Connection.java:182)
         at redis.clients.jedis.Connection.getBulkReply(Connection.java:171)
         at redis.clients.jedis.Jedis.rpop(Jedis.java:1109)
         at redis.clients.jedis.ShardedJedis.rpop(ShardedJedis.java:258)
.......
```

**8.2. Exception description**

Normally, one thread uses one Jedis connection. If multiple threads share the same Jedis connection, this exception occurs. You can use JedisPool to avoid this exception. The following example shows that two thread share the same Jedis connection. The GET and HGETALL commands return different types of data.

``` {#codeblock_dpa_cnd_qg4}
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

**8.3. Solutions and approaches**

You can check the code of your service for troubleshooting purposes.

## 9. Command error {#section_km5_l5n_fck .section}

**9.1. Exception stack**

``` {#codeblock_hjk_o8q_y4r}
Exception in thread "main" redis.clients.jedis.exceptions.JedisDataException: WRONGTYPE Operation against a key holding the wrong type of value
    at redis.clients.jedis.Protocol.processError(Protocol.java:127)
    at redis.clients.jedis.Protocol.process(Protocol.java:161)
    at redis.clients.jedis.Protocol.read(Protocol.java:215)
.....
```

**9.2. Exception description**

For example, key=”hello” indicates a key of String type, but the HGETALL command returns a key of hash type. Therefore, the exception occurs.

``` {#codeblock_2s2_kgu_41o}
jedis.set("hello","world");
jedis.hgetAll("hello");
```

**9.3. Solutions and approaches**

You can check the code of your service for troubleshooting purposes.

## 10. Memory usage of ApsaraDB for Redis is more than the value of maxmemory {#section_nyj_w1a_fyb .section}

**10.1. Exception stack**

``` {#codeblock_lef_upx_9yc}
redis.clients.jedis.exceptions.JedisDataException: OOM command not allowed when used memory > 'maxmemory'.
```

**10.2. Exception description**

The memory usage of a node of ApsaraDB for Redis is more than the value of maxmemory on the instance.

**10.3. Solutions**

Possible causes are listed as follows:

-   Service data increases normally.
-   Client buffer exceptions occur due to reasons such as the improper use of the MONITOR and PUBSUB commands.
-   In cache-only scenarios, ApsaraDB for Redis has maxmemory-policy configured in an incorrect way. For example, the volatile-lru eviction policy is not configured for expired keys.

In emergency conditions, you can submit a ticket to temporarily adjust the value of maxmemory. Afterward, you can require upgrading or downgrading of the configuration.

**10.4. Approaches**

-   Customer: you can locate the cause of increased memory usage.
-   Ticket: you can submit a ticket to temporarily adjust the value of maxmemory. Afterward, you can require upgrading or downgrading the configuration.

## 11. ApsaraDB for Redis is loading persistence files {#section_t46_w1k_i85 .section}

**11.1. Exception stack**

``` {#codeblock_805_89y_fwe}
redis.clients.jedis.exceptions.JedisDataException: LOADING Redis is loading the dataset **in** memory
```

**11.2. Exception description**

When a Jedis client tries to connect to an ApsaraDB for Redis instance, the instance is loading persistence files and cannot normally process read and write requests.

**11.3. Solutions**

Normally, this exception does not occur in ApsaraDB for Redis. If this exception occurs, submit a ticket.

**4. Approaches**

You can submit a ticket for troubleshooting purposes.

## 12. Lua script timeout {#section_3iy_lwd_goq .section}

**12.1. Exception stack**

``` {#codeblock_ek1_lxc_us2}
redis.clients.jedis.exceptions.JedisDataException: BUSY Redis is busy running a script. You can only call SCRIPT KILL or SHUTDOWN NOSAVE.
```

**12.2. Exception description**

An ApsaraDB for Redis instance is running a Lua script for a period of time that is more than the value of LUA-time-limit. In this case, if a Jedis client tries to connect to the instance, the preceding exception occurs.

**12.3. Solutions**

The system displays the exception message: `You can only call SCRIPT KILL or SHUTDOWN NOSAVE`. Therefore, you can run the`SCRIPT KILL` command to terminate the Lua script.

**12.4. Approaches**

You can handle the exception by yourself. If the exception persists, submit a ticket to request technical support.

## 13. Connection timeout {#section_vyb_g4r_iux .section}

**13.1. Exception stack**

``` {#codeblock_rh1_6gj_egm}
redis.clients.jedis.exceptions.JedisConnectionException: java.net.SocketTimeoutException: connect timed out
```

**13.2. Exception description**

Possible causes are listed as follows:

-   The connection timeout value is too low.
-   The value of tcp-backlog reaches the upper limit. This fails the new connection.
-   Network failures occur between the client and the service.

**13.3. Solutions**

You can submit a ticket and provide the connection timeout value for troubleshooting purposes.

**13.4. Approaches**

You can submit a ticket for troubleshooting purposes.

**14. Lua script write timeout** 

**14.1. Exception stack**

``` {#codeblock_lfr_d63_tvd}
(error) UNKILLABLE Sorry the script already executed write commands against the dataset. You can either wait the script termination or kill the server in a hard way using the SHUTDOWN NOSAVE command.
```

**14.2. Exception description**

An ApsaraDB for Redis instance is running a Lua script for a period of time more than the value of lua-time-limit, **and has run a write command**. In this case, if a Jedis client tries to connect to the instance, the preceding exception occurs.

**14.3. Solutions**

You can submit a ticket to require emergency troubleshooting. Afterward, the administrator restarts the service or performs the failover operation on the ApsaraDB for Redis instance.

**14.4. Approaches**

You can submit a ticket for troubleshooting purposes.

## 15. Class loading error {#section_710_3wv_7eu .section}

**15.1. Exception stack**

The following example shows that required classes and methods are not available:

``` {#codeblock_1w2_ke1_23l}
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

**15.2. Exception description**

A Jedis client throws an exception to indicate that the target class is not available when running the required command. This exception is probably caused by loading multiple Jedis versions such as Jedis 2. 9. 0 and Jedis 2.6. The code runs well during compilation. However, the class loader loads an earlier Jedis version and leads to the failure to load the target class at runtime.

**15.3. Solutions**

You can exclude repeated Jedis code to solve this issue. For example, use the Maven dependency tree to remove or exclude useless dependencies.

**15.4. Approaches**

You can check the code of your service for troubleshooting purposes.

## 16. Unknown commands for the service {#section_0v6_q4s_p9w .section}

**16.1. Exception stack**

The following example shows that a client runs the GEOADD command, but the ApsaraDB for Redis service indicates that the command is unknown in the response.

``` {#codeblock_ell_2ks_cly}
redis.clients.jedis.exceptions.JedisDataException: ERR unknown command 'GEOADD'
```

**16.2. Exception description**

Possible causes of the exception are listed as follows:

-   ApsaraDB for Redis does not support some Redis commands, or only some minor versions of ApsaraDB for Redis support these Redis commands. For example, the GEOADD command is included in the GEO API for Redis 3.2.
-   The command is incorrect. The Jedis client does not support the commands that you combine directly. Instead, each API calls a required function.

**16.3. Solutions**

You can ask the administrator to check whether any ApsaraDB for Redis edition supports the required command. If so, you can upgrade to the target minor version.

**16.4. Approaches**

-   Administrator: checks whether the ApsaraDB for Redis edition supports the required command.
-   Customer: you can upgrade to the target minor version if the ApsaraDB for Redis edition supports the required command.

## 17. Improper use of a pipeline {#section_9pb_naf_1xg .section}

**17.1. Exception stack**

``` {#codeblock_265_l42_hg7}
redis.clients.jedis.exceptions.JedisDataException: Please close pipeline **or** multi **block** before calling this **method**.
```

**17.2. Exception description**

A Jedis client usually calls response.get\(\) to obtain target values before calling pipeline.sync\(\). However, the client fails to call pipeline.set\(\) before calling pipeline.sync\(\). You can run the MONITOR command to check whether any write command runs successfully. The following example shows the exception.

``` {#codeblock_iog_rkk_1dt}
Jedis jedis = new Jedis("127.0.0.1", 6379);
Pipeline pipeline = jedis.pipelined();
pipeline.set("hello", "world"); 
pipeline.set("java", "jedis");
Response<String> pipeString = pipeline.get("java");
//The GET command must follow the SYNC command. To obtain multiple values, we recommend that you use List<Object> objectList = pipeline.syncAndReturnAll();
System.out.println(pipeString.get());
//The command takes effect.
pipeline.sync();
```

The set field in the response is initialized as false. When the Jedis client obtains the analysis response, if the set field is set to false, the client reports an error.

``` {#codeblock_eh1_z32_miw}
public T get() {
  // if response has dependency response and dependency is not built,
  // build it first and no more!!
  if (dependency ! = null && dependency.set && ! dependency.built) {
    dependency.build();
  }
  if (! set) {
    throw new JedisDataException(
        "Please close pipeline or multi block before calling this method.");
  }
  if (! built) {
    build();
  }
  if (exception ! = null) {
    throw exception;
  }
  return response;
}
```

The pipeline.sync\(\) method sets the set field to true in each result value.

``` {#codeblock_yib_3cv_9lk}
public void sync() {
  if (getPipelinedResponseLength() > 0) {
    List<Object> unformatted = client.getAll();
    for (Object o : unformatted) {
      generateResponse(o);
    }
  }
}
```

generateResponse\(o\):

``` {#codeblock_zkr_cot_64c}
protected Response<? > generateResponse(Object data) {
  Response<? > response = pipelinedResponses.poll();
  If (response! = null) {
    response.set(data);
  }
  return response;
}
```

response.set\(data\);

``` {#codeblock_3q6_bzn_oi6}
public void set(Object data) {
    this.data = data;
    set = true;
}
```

**17.3. Solutions**

To parse multiple result values, we recommend that you use pipeline.syncAndReturnAll\(\). In the following example, the pipeline simulates running the HGETALL command for multiple times.

``` {#codeblock_bao_k59_9gq}
/**
* The pipeline simulates running the HGETALL command for multiple times.
* @param keyList
* @return
*/
public Map<String, Map<String, String>> mHgetAll(List<String> keyList) {
// 1. Generate the pipeline object.
Pipeline pipeline = jedis.pipelined();
// 2. The pipeline runs the command. The command does not take effect at the moment.
for (String key : keyList) {
  pipeline.hgetAll(key);
}
// 3. The Jedis client runs the command. The syncAndReturnAll() method returns the result.
List<Object> objectList = pipeline.syncAndReturnAll();
if (objectList == null || objectList.isEmpty()) {
  return Collections.emptyMap();
}
// 4. Parse the result.
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

**17.4. Approaches**

You can modify the code of your service.

## 18. Administrator commands unavailable to common users {#section_3cg_efg_stb .section}

**18.1. Exception stack**

``` {#codeblock_cxw_1ci_dgq}
redis.clients.jedis.exceptions.JedisDataException: ERR command role not support for normal user
```

**18.2. Exception description**

The required command is not available.

**18.3. Solutions**

You cannot use the command directly. To solve this issue, submit a ticket.

**18.4. Approaches**

You can check whether the command is available by reading related documents.

## Other issues: {#section_2lk_jff_j7k .section}

**1. How do I select a Jedis version?**

In principle, you can select the latest [release](https://github.com/xetorthio/jedis/releases). However, we recommend that you select a version that has been released for a certain period. A serious issue occurred in an earlier Jedis version during the release history. Jedis 2.9.0 is a stable version so far.

``` {#codeblock_zmt_inq_y6b}
<dependency> 
    <groupId>redis.clients</groupId> 
    <artifactId>jedis</artifactId> 
    <version>2.9.0</version>
    <type>jar</type> 
    <scope>compile</scope> 
</dependency> 
```

**2. Is JedisCluster in Jedis is the client of the ApsaraDB for Redis cluster edition?**

For more information, see [Comparison among Redis 4.0, Codis, and ApsaraDB for Redis clusters](reseller.en-US/FAQs/Comparison among Redis 4.0, Codis, and ApsaraDB for Redis clusters.md#)

## JedisPool parameters {#section_n8x_u65_ikv .section}

**1. Resource setting and usage**

|Number|Name|Description|Default value|Recommended value|
|------|----|-----------|-------------|-----------------|
|1|maxTotal|The maximum number of connections in the JedisPool.|8|-|
|2|maxIdle|The maximum number of idle connections in JedisPool.|8|-|
|3|minIdle|The minimum number of idle connections in JedisPool.|0|-|
|4|blockWhenExhausted|Specifies whether the client waits for the connection when no resource is available in JedisPool. The maxWaitMillis parameter takes effect only when the blockWhenExhausted parameter is set to true.|true|We recommend that you use the default value.|
|5|maxWaitMillis|Specifies the maximum time for the client to wait for the connection when no resource is available in JedisPool. Unit: milliseconds.|-1: specifies no timeout.|We recommend that you do not use the default value.|
|6|testOnBorrow|Specifies whether to use the Ping command to check the connection validity when a client borrows a connection from JedisPool. If the connection is invalid, JedisPool removes the connection.|false|We recommend that you set the parameter to false when handling large data volumes. If you set the parameter to true, the system runs the Ping command once more.|
|7|testOnReturn|Specifies whether to use the Ping command to check the connection validity when a client returns a borrowed connection to JedisPool. If the connection is invalid, JedisPool removes the connection.|false|We recommend that you set the parameter to false when handling large data volumes. If you set the parameter to true, the system runs the Ping command once more.|
|8|jmxEnabled|Specifies whether to enable Java Management Extensions \(JMX\) monitoring to monitor the utilization of Jedis clients.|true|We recommend that you set the parameter to true. The monitored application must be running during the monitoring.|

**2. Monitoring of idle resources**

This is to detect idle Jedis objects. This feature includes four parameters. You can set the testWhileIdle parameter to true to enable the feature. The parameters are described in the following table.

|Number|Name|Description|Default value|Recommended value|
|------|----|-----------|-------------|-----------------|
|1|testWhileIdle|Specifies whether to enable the monitoring of idle resources.|false|We recommend that you set the parameter to true.|
|2|timeBetweenEvictionRunsMillis|Specifies the cycle of monitoring idle resources. Unit: milliseconds.|-1: specifies that the system does not monitor idle resources.|We recommend that you customize a monitoring cycle. You can also use the default value.|
|3|minEvictableIdleTimeMillis|Specifies the minimum idle time for resources in JedisPool. The system removes the resource that has been idle for the specified period. Unit: milliseconds.|1,000 × 60 × 30 milliseconds = 30 minutes|You can customize the value as needed, or use the default value in most scenarios.|
|4|numTestsPerEvictionRun|Specifies the number of connections sampled during the monitoring of idle resources.|3|You can fine-tune the value according to the number of connections in your application. If you set the parameter to -1, JedisPool monitors all connections.|

