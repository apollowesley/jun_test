# 广告点击数实时统计（Redis + Spark） {#task_1664088 .task}

本文将介绍使用Spark StructuredStreaming与Redis Stream实现实时广告点击数统计。

-   已创建引擎版本为5.0或以上的Redis实例，创建步骤请参见[创建实例](../../../../cn.zh-CN/快速入门/步骤1：创建实例.md#)。
-   已创建Spark集群，创建步骤请参见[分析集群管理](https://help.aliyun.com/document_detail/93900.html)。
-   Spark集群和Redis实例处于同一VPC，并且可以互相访问。

    **说明：** Redis实例和Spark集群均需在白名单中添加对方内网IP地址才能互相访问。

    -   设置Redis实例白名单请参见[设置白名单](../../../../cn.zh-CN/快速入门/步骤2：设置白名单.md#)
    -   设置Spark集群白名单请参见[设置白名单](https://help.aliyun.com/document_detail/50504.html)。
    -   如果不清楚Redis/Spark内网IP地址，可以在白名单中添加Redis和Spark所在VPC的IP地址段。

## 场景介绍 {#section_ezg_w7k_te0 .section}

某广告公司在网页上投递动态图片广告，广告的展现形式是根据热点图片动态生成的。为了收益最大化，需要统计广告的点击数来决定哪些广告可以继续投放，哪些广告需要更换。大部分的广告生命周期很短，实时获取广告的点击数可以让我们快速确定哪些广告是关键业务。基于以上诉求可以选择StructuredStreaming + Redis Stream作为解决方案。

-   Spark StructuredStreaming是Spark在2.0后推出的基于Spark SQL上的一种实时处理流数据的框架。处理时延可达毫秒级别。
-   Redis Stream是在Redis 5.0后引入的一种新的数据结构，可高速收集、存储和分布式处理数据，处理时延可达亚毫秒级别。

**说明：** 

-   Spark-Redis是X-Pack Spark发布的连接器， 用于连通Spark与Redis，该工具由四个Redis依赖的jar包组成。下载链接与安装方式请参见[Spark对接Redis](https://help.aliyun.com/document_detail/116020.html)。
-   如需了解更多Spark相关信息，请参见[Spark 基本介绍](https://help.aliyun.com/document_detail/93899.html)。
-   如需了解更多Redis Stream相关信息，请参见[Introduction to Redis Streams](https://redis.io/topics/streams-intro)。

## 业务数据链路简介 {#section_gxw_zkb_om7 .section}

![业务数据链路](images/55068_zh-CN.png "业务数据链路")

如上图所示，广告点击数据通过手机或者电脑传递到数据库中进行**数据提取**，提取后的数据经过**数据处理**计算实时的点击数，最后存储到数据库，通过**数据查询**进行统计分析，统计每个广告的点击总数。

根据数据特点，整个数据链路的数据输入输出如下：

-   输入

    针对每个点击事件我们使用asset id以及cost两个字段来表示一个广告信息，例如：

    ``` {#codeblock_71f_2vc_zvo}
    asset [asset id] cost [actual cost]
    asset aksh1hf98qwdst9q7 cost 39
    asset aksh1hf98qwdst9q8 cost 19
    ```

-   输出

    经过数据处理后，我们把结果集存储到一个数据表中，数据表可以使用SQL查询，例如：

    ``` {#codeblock_zlx_ch8_s9k}
    select asset, count from clicks order by count desc
    
    asset            count
    -----------------     -----
    aksh1hf98qwdst9q7    2392
    aksh1hf98qwdst9q8    2010
    aksh1hf98qwdst9q6    1938
    ```


## 数据处理流简介 {#section_9qu_m8n_dp4 .section}

![数据处理流](images/55091_zh-CN.png "数据处理流")

如上图所示，点击数据会存储到Redis Stream，然后StructuredStreaming对数据进行消费以及聚合处理，处理完成后将结果返回Redis，最后通过Spark SQL查询Redis进行统计分析。

|处理流|简介|
|---|--|
|数据提取|Redis Stream是Redis内置的数据结构，具备每秒百万级别的读写能力，存储的数据可以根据时间自动排序。|
|Spark-Redis连接器可以将Redis Stream作为数据源，把Redis Stream数据对接到Spark引擎。|
|数据处理|Spark-Redis连接器可以将获取的Redis Stream数据转换成Spark的DataFrames数据。|
|在StructuredStreaming处理流数据的过程中，可以对微批次数据或者整体数据进行查询。|
|数据的处理结果可以通过自定义的`writer`输出到不同的目的地，本场景中我们直接把数据输出到Redis的Hash数据结构。|
|数据查询|Spark-Redis连接器可以把Redis的数据结构映射成Spark的DataFrames，我们需要将DataFrames创建成一个临时表，表的字段映射Redis的Hash数据结构，再使用Spark-SQL进行实时的数据查询。|

## 开发步骤 {#section_uqj_5qy_07y .section}

1.  Redis Stream存储数据。 

    Redis Streams是append-only的数据结构。部署Redis Streams后可以使用redis-cli向Redis发送数据。

    **说明：** 

    -   请使用Redis 5.0以上版本的redis-cli工具。
    -   redis-cli使用方法请参见[redis-cli连接](../../../../cn.zh-CN/快速入门/步骤3：连接实例/redis-cli连接.md#)。
    向clicks发送点击数据，命令如下所示：

    ``` {#codeblock_a2l_pp9_0je}
    XADD clicks MAXLEN ~ 1000000 * asset aksh1hf98qw7tt9q7 cost 29
    ```

    ![数据提取](images/55130_zh-CN.png "数据提取")

2.  数据处理。 

    ![数据处理](images/55132_zh-CN.png "数据处理")

    在StructuredStreaming中把数据处理步骤分成3个子步骤：

    1.  从Redis Stream读取、处理数据。
        1.  使用Spark-Redis连接器创建一个SparkSession，填写Redis连接信息。

            ``` {#codeblock_vht_vk0_wmd}
            val spark = SparkSession
                  .builder()
                  .appName("StructuredStreaming on Redis")
                  .config("spark.redis.host", r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com)
                  .config("spark.redis.port", 6379)
                  .config("spark.redis.auth", redisPassword)
                  .getOrCreate()
            ```

            |参数|描述|示例|
            |--|--|--|
            |`spark.redis.host`|Redis的连接地址，查看Redis连接地址请参见[查看连接地址](../../../../cn.zh-CN/用户指南/连接管理/查看连接地址.md#)。|`r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com`|
            |`spark.redis.port`|Redis的服务端口，默认为6379。|`6379`|
            |`spark.redis.auth`|Redis的连接密码。|`redisPassword`|

        2.  在Spark中创建schema。

            例如，我们给流数据命名为“clicks”，需要将参数“stream.kes”设置为“clicks”。由于Redis Stream中的数据包含两个字段：“asset”和“cost”，所以我们要创建StructType映射这两个字段。

            ``` {#codeblock_ge0_s45_8h6}
            val clicks = spark
                  .readStream
                  .format("redis")
                  .option("stream.keys", redisTableName)
                  .schema(StructType(Array(
                    StructField("asset", StringType),
                    StructField("cost", LongType)
                  )))
                  .load()
            ```

        3.  统计每个asset的点击次数。

            **说明：** 可以创建一个DataFrames根据asset汇聚数据。

            ``` {#codeblock_a1r_sto_lw7}
            val bypass = clicks.groupBy("asset").count()
            ```

        4.  启动StructuredStreaming。

            ``` {#codeblock_1ub_06z_14e}
            val query = bypass
                  .writeStream
                  .outputMode("update")
                  .foreach(clickWriter)
                  .start()
            ```

    2.  存储数据到Redis。

        使用Redis的Java客户端Jedis连接到Redis，向Redis写数据。

        **说明：** 使用Jedis连接redis请参见[Jedis客户端](../../../../cn.zh-CN/快速入门/步骤3：连接实例/Redis客户端连接.md#section_bqv_lkc_5db)。

        ``` {#codeblock_xqz_g3f_7c2}
        class ClickForeachWriter(redisHost: String, redisPort: String, redisPassword: String) extends ForeachWriter[Row] {
        
          var jedis: Jedis = _
        
          def connect() = {
            val shardInfo: JedisShardInfo = new JedisShardInfo(redisHost, redisPort.toInt)
            shardInfo.setPassword(redisPassword)
            jedis = new Jedis(shardInfo)
          }
        
          override def open(partitionId: Long, version: Long): Boolean = {
            true
          }
        
          override def process(value: Row): Unit = {
        
            val asset = value.getString(0)
            val count = value.getLong(1)
            if (jedis == null) {
              connect()
            }
        
            jedis.hset("click:" + asset, "asset", asset)
            jedis.hset("click:" + asset, "count", count.toString)
            jedis.expire("click:" + asset, 300)
        
          }
        
          override def close(errorOrNull: Throwable): Unit = {}
        }
        ```

    3.  运行StructuredStreaming程序。

        程序完成打包后，可以通过Spark控制台提交任务，运行Spark StructuredStreaming任务。

        ``` {#codeblock_ntu_l5y_p6h}
        --class com.aliyun.spark.redis.StructuredStremingWithRedisStream
        --jars /spark_on_redis/ali-spark-redis-2.3.1-SNAPSHOT_2.3.2-1.0-SNAPSHOT.jar,/spark_on_redis/commons-pool2-2.0.jar,/spark_on_redis/jedis-3.0.0-20181113.105826-9.jar
        --driver-memory 1G 
        --driver-cores 1
        --executor-cores 1
        --executor-memory 2G
        --num-executors 1
        --name spark_on_polardb
        /spark_on_redis/structuredstreaming-0.0.1-SNAPSHOT.jar
        <Host> <Port> <Password> <Stream>
        ```

        |参数|描述|示例|
        |--|--|--|
        |Host|Redis内网连接地址。|`r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com`|
        |Port|Redis默认服务端口。|`6379`|
        |Password|Redis的连接密码。|`redisPassword`|
        |Stream|Redis的Stream名称。|`clicks`|

3.  读取Redis Hash数据库。 使用Spark SQL创建表来读取Redis Hash数据库，命令如下所示。

    ``` {#codeblock_raz_rx6_sml}
    CREATE TABLE IF NOT EXISTS clicks(asset STRING, count INT) 
    USING org.apache.spark.sql.redis 
    OPTIONS (
    'host' 'r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com',
    'port' '6379',
    'auth' 'redisPassword',
    'table' 'click'
    )
    ```

    |参数|描述|示例|
    |--|--|--|
    |`host`|Redis的内网连接地址，查看Redis连接地址请参见[查看连接地址](../../../../cn.zh-CN/用户指南/连接管理/查看连接地址.md#)。|`r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com`|
    |`port`|Redis的服务端口，默认为6379。|`6379`|
    |`auth`|Redis的连接密码。|`redisPassword`|
    |`table`|Redis的Hash表名称。|`click`|

4.  执行查询语句。 查询clicks的点击数据，查询命令如下所示：

    ``` {#codeblock_967_gtg_im9}
    select * from clicks;
    ```

    ![执行查询语句](images/55144_zh-CN.png "执行查询语句")

    Spark SQL通过Spark-Redis连接器直接查询Redis数据，统计了广告的点击数。


