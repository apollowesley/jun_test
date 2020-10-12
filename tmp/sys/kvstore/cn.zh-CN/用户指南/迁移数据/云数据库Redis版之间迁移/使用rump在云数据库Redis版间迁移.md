# 使用rump在云数据库Redis版间迁移 {#task_njf_rfw_mfb .task}

使用Rump可以在同一个云数据库Redis版实例的不同数据库之间，或者不同实例的数据库之间进行数据迁移。

-   在目的Redis实例所在的VPC网络中创建了Linux系统的ECS实例；
-   在ECS实例中下载[rump](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/94155/cn_zh/1551331952215/rump)，并将其修改为可执行文件。

当前从云服务提供商迁移Redis数据存在如下的问题：

-   不支持SLAVEOF、BGSAVE等数据获取方式；
-   KEYS命令容易造成服务端阻塞，影响线上服务。

Rump工作原理：

Rump通过SCAN的方式从源Redis批量获取key列表，接着使用DUMP命令获取key内容，之后通过PTTL方式获取过期时间，最后以pipeline的方式将key RESTORE到目标实例中。

Rump迁移优势：

-   使用SCAN命令代替KEYS命令，规避KEYS命令对服务端造成阻塞；
-   可以同步任何数据类型；
-   不使用任何临时文件；
-   使用带缓冲区的channels来提高源服务器的性能；
-   使用pipeline来减少网络带宽消耗。

1.  在ECS实例中使用如下命令进行数据迁移。 

    ```
    rump -from source_addr -fromPwd source_pwd -to dest_addr -toPwd dest_pwd [-size size] [-replace]
    ```

    |参数|说明|
    |--|--|
    |source\_addr|源Redis实例地址，格式：`redis://host:port/db`。host与port均需传递，db不传递默认为0。**说明：** 如果要从AWS的cluster导出数据， 请使用cluster的master IP作为源实例地址。您可以在AWS端（例如EC2中）使用redis-cli和如下命令连接cluster并获取master IP：

    ```
#redis-cli -h <host> -p <port> cluster nodes | grep master
    ```

其中host和port分别为AWS cluster的连接地址和端口号。

|
    |source\_pwd|源Redis实例密码|
    |dest\_addr|目标Redis实例地址，格式同source\_addr。|
    |dest\_pwd|目标Redis密码|
    |size|单次同步key的数量，默认为10。|
    |-replace|传递此参数表示若目标实例存在相同key则直接覆盖。不传递则不覆盖，将打印错误信息。|


-   **示例1：将db0的数据导入db1中**

    ```
    rump -from redis://r-123456789.redis.rds.aliyuncs.com:6379/0 -fromPwd from_pass -to redis://r-123456789.redis.rds.aliyuncs.com:6379/1 -toPwd to_pass -size 100 
    
    
    ```

-   **示例2：将Redis实例A中db0的数据导入Redis实例B的db1中**

    ```
    rump -from redis://r-123456789.redis.rds.aliyuncs.com:6379/0 -fromPwd from_pass -to redis://r-999999999.redis.rds.aliyuncs.com:6379/1 -toPwd to_pass -size 100 
    
    
    ```


