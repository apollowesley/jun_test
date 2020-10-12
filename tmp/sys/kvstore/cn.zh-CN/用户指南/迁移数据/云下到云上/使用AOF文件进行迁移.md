# 使用AOF文件进行迁移 {#concept_bxn_fbv_vdb .concept}

用户可以使用redis-cli工具，通过AOF文件将自建Redis迁移到云数据库Redis版。

redis-cli是Redis原生的命令行工具。云数据库Redis版支持通过redis-cli将已有的Redis数据导入到云数据库Redis版里，实现数据的无缝迁移。另外您也可以通过[DTS导入数据](intl.zh-CN/用户指南/迁移数据/云下到云上/使用DTS进行迁移.md#)。

## 注意事项 { .section}

-   由于云数据库Redis版仅支持从阿里云内网访问，所以此操作方案仅在阿里云ECS上执行才生效。 若您的Redis不在阿里云ECS服务器上，您需要将原有的AOF文件复制到ECS上再执行以上操作。

-   redis-cli是Redis原生的命令行工具。若您在ECS上无法使用redis-cli，可以先下载安装Redis即可使用redis-cli。


## 操作步骤 { .section}

对于在阿里云ECS上自建的Redis实例，执行如下操作：

1.  开启现有Redis实例的AOF功能（如果实例已经启用AOF功能则忽略此步骤）。

    ```
    # redis-cli -h old_instance_ip -p old_instance_port config **set** appendonly yes
    ```

2.  通过AOF文件将数据导入到新的云数据库Redis版实例（假定生成的AOF文件名为 appendonly.aof）。

    ```
    # redis-cli -h aliyun_redis_instance_ip -p 6379 -a password --pipe < appendonly.aof
    ```

    **说明：** 

    如果原有的Redis实例不需要一直开启AOF，可在导入完成后通过以下命令关闭。

    ```
    # redis-cli -h old_instance_ip -p old_instance_port config **set** appendonly no
    ```


您还可以通过观看以下视频快速了解如何将ECS上自建Redis迁移至云数据库Redis版，视频时长约4分钟。

 

