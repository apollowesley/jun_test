# 使用 redis-cli 迁移数据 {#concept_bxn_fbv_vdb .concept}

redis-cli 是 Redis 原生的命令行工具。云数据库 Redis 版支持通过 redis-cli 将已有的 Redis 数据导入到云数据库 Redis 版里，实现数据的无缝迁移。另外您也可以通过[DTS 导入数据](cn.zh-CN/用户指南/迁移数据/将自建 Redis 迁移至云数据库 Redis 版/使用 DTS 迁移数据.md#)。

## 注意事项 { .section}

-   由于云数据库 Redis 版仅支持从阿里云内网访问，所以此操作方案仅在阿里云 ECS 上执行才生效。 若您的 Redis 不在阿里云 ECS 服务器上，您需要将原有的 AOF 文件复制到 ECS 上再执行以上操作。

-   redis-cli 是 Redis 原生的命令行工具。若您在 ECS 上无法使用 redis-cli，可以先下载安装 Redis 即可使用 redis-cli。


## 操作步骤 { .section}

对于在阿里云 ECS 上自建的 Redis 实例，执行如下操作：

1.  开启现有 Redis 实例的 AOF 功能（如果实例已经启用 AOF 功能则忽略此步骤）。

    ```
    # redis-cli -h old_instance_ip -p old_instance_port config **set** appendonly yes
    ```

2.  通过 AOF 文件将数据导入到新的云数据库 Redis 版实例（假定生成的 AOF 文件名为 appendonly.aof）。

    ```
    # redis-cli -h aliyun_redis_instance_ip -p 6379 -a password --pipe < appendonly.aof
    ```

    **说明：** 

    如果原有旧的 Redis 实例不需要一直开启 AOF，可在导入完成后通过以下命令关闭。

    ```
    # redis-cli -h old_instance_ip -p old_instance_port config **set** appendonly no
    ```


您还可以通过观看以下视频快速了解如何将 ECS 上自建 Redis 迁移至云数据库 Redis 版，视频时长约4分钟。

[https://cloud.video.taobao.com/play/u/3050941791/p/1/e/6/t/1/56540385.mp4](https://cloud.video.taobao.com/play/u/3050941791/p/1/e/6/t/1/56540385.mp4)

