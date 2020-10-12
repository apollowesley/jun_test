# JedisPool optimization {#concept_kfn_zzw_yfb .concept}

Setting JedisPool parameters helps to improve the performance of Redis. This topic describes how to use JedisPool and configure the resource pool parameters. It also provides recommended parameter configurations to optimize JedisPool.

## How to use JedisPool {#section_ug4_n2x_yfb .section}

Take Jedis 2.9.0 as an example. The Maven dependency is as follows:

```
<dependency> 
    <groupId>redis.clients</groupId> 
    <artifactId>jedis</artifactId> 
    <version>2.9.0</version>
    <scope>compile</scope> 
</dependency> 
```

Jedis manages the resource pool by using Apache Commons-pool2. When you define JedisPool, pay attention to the GenericObjectPoolConfig parameter \(resource pool\). The following example describes how to use this parameter.

```
GenericObjectPoolConfig jedisPoolConfig = new GenericObjectPoolConfig();
jedisPoolConfig.setMaxTotal(...);
jedisPoolConfig.setMaxIdle(...);
jedisPoolConfig.setMinIdle(...);
jedisPoolConfig.setMaxWaitMillis(...) ;
...
```

The initialization of JedisPool is as follows:

```
// redisHost indicates the instance IP address. redisPort indicates the instance port. redisPassword indicates the password of the instance. The timeout parameter indicates both the connection timeout and the read/write timeout.
JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisHost, redisPort, timeout, redisPasswor//d);
//Run the command as follows:
Jedis jedis = null;
try {
    jedis = jedisPool.getResource();
    // Specific commands
    jedis.executeCommand()
} catch (Exception e) {
    logger.error(e.getMessage(), e);
} finally {
    //In JedisPool mode, the Jedis resource will be returned to the resource pool.
    if (jedis ! = null) 
        jedis.close();
}
```

## Parameter description {#section_w4y_x3x_yfb .section}

The Jedis connection is a resource managed by JedisPool in the connection pool. JedisPool is a thread-safe pool of connections and can keep all resources within a manageable range. Configure the GenericObjectPoolConfig parameter properly can improve the performance of Redis and reduce the resource consumption. The following two tables describe some important parameters and provide recommendations for parameter configuration.

|Parameter|Description|Default value|Recommendation|
|---------|-----------|-------------|--------------|
|maxTotal|The maximum number of connections that can be allocated from this pool.|8|See [Recommendations for key parameter configurations](#).|
|maxIdle|The maximum number of connections that can remain idle in the pool, without extra ones being released.|8|See [Recommendations for key parameter configurations](#).|
|minIdle|The minimum number of connections that can remain idle in the pool, without extra ones being created.|0|See [Recommendations for key parameter configurations](#).|
|blockWhenExhausted|Whether the caller has to wait when the resource pool is exhausted. The following maxWaitMillis takes effect only when this value is true.|true|We recommend that you use the default value.|
|maxWaitMillis|The maximum number of milliseconds that the caller needs to wait when no connection is available.|-1 means never timeout.|The default value is not recommended.|
|testOnBorrow|Whether the connections will be validated by using the ping command before they are borrowed from the pool. If the connection turns out to be invalid, it will be removed from the pool.|false|When the business traffic is high, we recommend that you set it to false to reduce the consumption of a ping test.|
|testOnReturn|Whether connections will be validated using the ping command before they are returned to the pool. If the connection turns out to be invalid, it will be removed from the pool.|false|When the business traffic is high, we recommend that you set it to false to reduce the consumption of a ping test.|
|jmxEnabled|Whether to enable JMX monitoring.|true|We recommand that you enable JMX monitoring. Note that your application itself also needs to be enabled.|

Idle Jedis object detection consists of the following four parameters. testWhileIdle is the switch of this feature.

|Name|Description|Default value|Recommendation|
|----|-----------|-------------|--------------|
|testWhileIdle|Whether to enable the idle resource detection.|false|true|
|timeBetweenEvictionRunsMillis|The number of milliseconds to sleep between runs of the idle object evictor thread.|-1 means no idle object evictor thread will be run.|We recommend that you set this parameter and make your own cycle selections. Or, you can use the default configuration in JedisPoolConfig.|
|minEvictableIdleTimeMillis|The minimum amount of time an object may sit idle in the pool before it is eligible for eviction by the idle object evictor \(if any\).|180000 \(30 minutes\)|The default value is suitable for most cases. You can also use the configuration in JeidsPoolConfig based on your business scenario.|
|numTestsPerEvictionRun|The number of objects to examine during each run of the idle object evictor thread \(if any\).|3|It can be changed according to the number of application connections. If this value is set to -1, the idle validation will be performed on all connections.|

For your convenience, Jedis provides JedisPoolConfig that shares some configurations with GenericObjectPoolConfig in validating idle connections.

```
public class JedisPoolConfig extends GenericObjectPoolConfig {
  public JedisPoolConfig() {
    // defaults to make your life with connection pool easier :)
    setTestWhileIdle(true);
    //
    setMinEvictableIdleTimeMillis(60000);
    //
    setTimeBetweenEvictionRunsMillis(30000);
    setNumTestsPerEvictionRun(-1);
    }
}
```

**Note:** You can view all the default values in org.apache.commons.pool2.impl.BaseObjectPoolConfig.

## Recommendations for key parameter configurations {#section_m2c_5kr_zfb .section}

maxTotal: The maximum number of connections.

To set maxTotal properly, you need to consider the following factors:

-   The Redis concurrent connections required by the business.
-   How long it takes for the client to execute the command.
-   The limit of Redis resources. For example, the product of multiplying maxTotal by the number of nodes \(applications\) must be smaller than the allowed maximum number of connections in Redis.
-   The cost of creating and releasing connections. When the number of connections created and released is high for each request, the creation and release process takes a heavy toll.

The time for running a command to obtain a resource consists of the time for borrowing and returning the resource, the time for JedisPool to run the command, and the time for network connection. If the average time for running a command to obtain a resource is about 1 ms, the QPS of a connection is about 1,.000, and the QPS expected by the business is 50000, then theoretically the required pool size is 50 \(50,000 / 1,000 = 50\).

But this is only a theoretical value. To reserve some resources, the value of the maxTotal parameter can be larger than the theoretical value. However, if this value is too large, the connections will consume too much client and server resources. On the other hand, for servers like Redis that has a high QPS, if there is a blocking of commands, even a large resource pool cannot help to solve this problem.

maxIdle and minIdle

maxIdle is the actual maximum number of connections required by the business, while maxTotal includes the number of idle connections as a surplus. If maxIdle is set too low on heavily loaded systems, `new Jedis` \(extra connections\) will be created to serve the requests. Therefore, minIdle is configured to specify the minimum number of established connections that need to be kept in the pool.

The connection pool reaches its best performance when maxTotal=maxIdle. In this way, the performance is not affected by the scaling of the connection pool. However, if the amount of concurrent connections is small or the maxTotal parameter is set too high, the connection resources will be wasted.

You can evaluate the connection pool size used by each node based on the actual total QPS and the number of clients that call Redis.

**Use monitoring tools to obtain the best value**

Using monitoring tools to obtain the best parameter value is a more reliable method. You can use JMX monitoring or other monitoring tools to discover the best value.

## FAQ {#section_kmk_tbs_zfb .section}

**Insufficient resources**

In the following two cases, you cannot obtain resources from the resource pool.

-   Timeout:

    ```
    redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
    …
    Caused by: Java. util. nosuchelementexception timeout waiting for idle object
    at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:449)
    ```

-   When you set the blockWhenExhausted to false, the time specified with borrowMaxWaitMillis will not be used and the borrowObject call will block until there is an idle connection available.

    ```
    redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
    …
    Caused by: java.util.NoSuchElementException: Pool exhausted
    at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:464)
    ```


This exception occurs not necessarily because the pool size is limited. For more information, see **[Recommendations for key parameter configurations](#)**. To solve this problem, we recommend that you check the network, parameter configurations of the resource pool, the resource pool monitoring \(JMX monitoring\), the code \( for example, the reason may be that `jedis.close()` is not executed.\), the slow queries, and DNS.

**Warm up JedisPool**

For some reasons \(such as a small timeout setting\), the project may time out after it is started. JedisPool does not create a Jedis connection in the connection pool when it defines the maximum number of resources and the minimum number of idle resources. When there is no idle connection in the pool, a `new Jedis` connection will be created. This connection will be released to the pool after it is used. However, creating a new connection and releasing it every time is a time-consuming process. Therefore, we recommend that you warm up JedisPool with the minimum number of idle connections after JedisPool is defined. The example is as follows:

```
List<Jedis> minIdleJedisList = new ArrayList<Jedis>(jedisPoolConfig.getMinIdle()); 

for (int i = 0; i < jedisPoolConfig.getMinIdle(); i++) {
    Jedis jedis = null;
    try {
        jedis = pool.getResource();
        minIdleJedisList.add(jedis);
        jedis.ping();
    } catch (Exception e) {
        logger.error(e.getMessage(), e);
    } finally {
    }
}

for (int i = 0; i < jedisPoolConfig.getMinIdle(); i++) {
    Jedis jedis = null;
    try {
        jedis = minIdleJedisList.get(i);
        jedis.close();
    } catch (Exception e) {
        logger.error(e.getMessage(), e);
    } finally {
    
    }
}
```

