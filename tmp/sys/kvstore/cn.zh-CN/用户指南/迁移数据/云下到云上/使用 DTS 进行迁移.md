# 使用 DTS 进行迁移 {#concept_ejw_lcv_vdb .concept}

用户可以使用DTS将自建 Redis 迁移至云数据库 Redis 版。

## 自建 Redis 迁移到云 Redis 实例 { .section}

使用[数据传输 DTS](https://dts.console.aliyun.com/)可以进行 Redis 实例间的数据迁移。如果源实例为自建 Redis，那么 Redis 迁移支持增量数据同步功能，可以实现在本地应用不停写的情况下，平滑完成 Redis 数据迁移。

本节简单介绍使用 DTS 进行有公网 IP 的自建 Redis 到云 Redis 实例数据迁移的迁移流程。云 Redis 实例间的迁移过程也可以参考这个流程。

您可以通过观看以下视频了解如何通过 DTS 将 ECS 上自建 Redis 迁移至云数据库 Redis 版。

 

## 迁移类型简介 { .section}

当迁移源实例为自建 Redis 时，可以支持全量数据迁移和增量数据迁移，当迁移源实例为云 Redis 实例时，目前只支持全量数据迁移。全量数据迁移及增量数据迁移的功能及限制如下。

-   全量数据迁移

    数据传输 DTS 将自建 Redis 中现有的 Key 全部迁移到云 Redis 实例中。

-   增量数据迁移

    增量数据迁移将迁移过程中，自建 Redis 实例的更新 key 同步到云数据库 Redis。最终，自建 Redis 同云 Redis 实例进入动态数据复制的过程。通过增量数据迁移，可以实现在自建 Redis 正常提供服务的时候，平滑完成 Redis 到云 Redis 实例的数据迁移。


## 迁移功能 { .section}

Redis 增量迁移支持的命令包括：

-   APPEND
-   BITOP，BLPOP，BRPOP，BRPOPLPUSH，
-   DECR，DECRBY，DEL，
-   EVAL，EVALSHA,EXEC，EXPIRE，EXPIREAT，
-   FLUSHALL，FLUSHDB，
-   GEOADD，GETSET，
-   HDEL，HINCRBY，HINCRBYFLOAT，HMSET，HSET，HSETNX，
-   INCR，INCRBY，INCRBYFLOAT，
-   LINSERT，LPOP，LPUSH，LPUSHX，LREM，LSET，LTRIM，
-   MOVE，MSET，MSETNX，MULTI，
-   PERSIST，PEXPIRE，PEXPIREAT，PFADD，PFMERGE，PSETEX,PUBLISH
-   RENAME，RENAMENX，RESTORE,RPOP，RPOPLPUSH，RPUSH，RPUSHX，
-   SADD，SDIFFSTORE，SELECT，SET，SETBIT，SETEX，SETNX，SETRANGE，SINTERSTORE，SMOVE，SPOP，SREM，SUNIONSTORE，
-   ZADD，ZINCRBY，ZINTERSTORE，ZREM，ZREMRANGEBYLEX，ZUNIONSTORE，ZREMRANGEBYRANK，ZREMRANGEBYSCORE

## 迁移前置条件 { .section}

如果待迁移的 Redis 是通过专线接入阿里云 VPC 的自建 Redis，或是专有网络的云 Redis 实例，那么需要架设代理，进行数据转发。

为了让 DTS 能够访问专有网络 Redis 或通过专线接入阿里云的自建 Redis， 需要在 VPC 内选择一台有公网 EIP 的 ECS，并在 ECS 上部署 nginx，通过 nginx 进行代理转发。

对于专有网络的 Redis 实例，ECS 所在的 VPC 必须同专有网络 Redis 在同一个 VPC。对于通过专线接入阿里云的自建 Redis，ECS 所在的 VPC 必须为专线对端的阿里云 VPC。

下面介绍如何使用 nginx 进行 Redis 的转发配置，让 DTS 服务器可以访问专有网络内的 Redis 实例。

1.  nginx 部署

    首先在 ECS 服务器上，通过如下命令部署 nginx。

    ```
    yum install nginx
    ```

2.  nginx 转发配置

    安装完 nginx，修改 nginx 配置文件/etc/nginx/nginx.conf，设置后端监听 redis。 注释掉配置文件中 http 的相关配置，添加 tcp 的配置内容。需要注释掉的 http 配置内容如下：

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3156/15440628282791_zh-CN.png)

    需要在配置文件中添加 tcp 配置内容：

    ```
    stream{
        upstream backend{
            hash $remote_addr consistent;
             #设置后端Redis连接串和端口，失败超时时间为10s，最多尝试3次。
            server  r-bp1b294374634044.redis.rds.aliyuncs.com:6379 max_fails=3 fail_timeout=10s;
        }
        server{
            # nginx访问端口
            listen 3333;
            #指定nginx连接后端服务器的超时时间，指定为20s。       
            proxy_connect_timeout 20s;
             #距离上一次成功访问（连接或读写）后端服务器的时间超过了5分钟就判定为超时，断开此连接。
            proxy_timeout 5m;
             #将TCP连接及数据收发转向叫“backend”的后端服务器。
            proxy_pass backend;
        }
    }
    ```

    例如，需要访问的 Redis 的连接地址为：`r-bp1b294374634044.redis.rds.aliyuncs.com:6379`，nginx代理转发端口为3333，那么 tcp 相关配置如下：

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3156/15440628282792_zh-CN.png)

3.  通过转发接口访问 Redis

    当完成上面的配置后，运行 nginx 即成功启动 nginx 代理服务。

    假设 nginx 部署的 ECS 服务的 EIP 为：`114.55.89.152`，那么可以直接用 redis\_cli 访问 nginx 转发端口，测试代理转发是否正常。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3156/15440628282793_zh-CN.png)

    如上图所示，可以通过访问 nginx 代理转发端口来访问 Redis 实例。


下面配置 DTS 迁移任务时，对于专有网络 Redis 实例或对于通过专线接入阿里云的自建 Redis，直接配置 nginx 代理的连接地址即可。

DTS 支持 VPC 后，对于专有网络 Redis 实例或者通过专线接入阿里云的自建 Redis 无需再架设代理。

## 迁移任务配置 { .section}

当上面的所有前置条件都配置完成后，就可以开始正式的数据迁移了。本节以将通过专线接入阿里云的自建 Redis 实例到经典网络云 Redis 实例的迁移为例，详细介绍迁移任务配置流程。

1.  进入[数据传输 DTS 控制台](https://dts.console.aliyun.com/)，点击右上角的**创建迁移任务**，开始配置迁移任务。
2.  实例连接信息配置

    这个步骤主要配置 迁移任务名称，自建 Redis 连接信息及云 Redis 实例连接信息。其中：

    -   **任务名称**

        DTS 为每个任务自动生成一个任务名称，任务名称没有唯一性要求。您可以根据需要修改任务名称，建议为任务配置具有业务意义的名称，便于后续的任务识别。

    -   **源实例信息**

        **实例类型**：选择有公网 IP 的自建数据库

        **实例区域**：对于自建 Redis，选择跟 Redis 实例物理距离最近的地域。选择的地域离 Redis 实例越近迁移性能越高。

        **数据库类型**： 选择 Redis

        **实例模式**：默认为 **单机**，后续 DTS 将支持集群模式的 Redis 实例。

        **主机名或IP地址**： 自建 Redis 实例的访问地址，如果配置了 nginx 转发，那么为 nginx 转发的访问地址。

        **端口**：自建 Redis 实例的监听端口。如果配置了 nginx 转发，那么为 nginx 转发端口

        **数据库密码**：自建 Redis 实例访问密码，为非必填项，如果自建 Redis 没有设置密码，那么可以不填。

    -   **目标实例信息** 

        **实例类型**： Redis 实例

        **实例区域**： 实例区域为云 Redis 实例所在区域

        **Redis实例ID**： 配置迁移的目标云 Redis 实例的实例ID

        **数据库密码**：访问 Redis 实例的密码

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3156/15440628282794_zh-CN.png)

3.  当连接信息配置完成后，即可点击页面右下角的 **授权白名单并进入下一步**，开始进行迁移库的选择。
4.  选择迁移对象及迁移类型

    在这个步骤中，需要配置迁移类型及迁移对象。

    -   迁移类型
    对于 Redis，DTS 支持全量数据迁移及增量数据迁移。

    如果只需要进行全量迁移，那么迁移类型选择：全量数据迁移。

    如果需要进行源库不停写迁移，那么迁移类型选择：全量数据迁移＋增量数据迁移。

    -   迁移对象
    这个步骤需要选择要迁移的库。目前 Redis 只支持整库迁移，所以只能选择要迁移的库，而不能选择要部分 Key。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3156/15440628282799_zh-CN.png)

5.  预检查

    在迁移任务正式启动之前，会先进行前置预检查，只有预检查通过后，才能成功启动迁移。

    如果预检查失败，那么可以点击具体检查项后的按钮，查看具体的失败详情，并根据失败原因修复后，重新进行预检查。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3156/15440628282800_zh-CN.png)

6.  启动迁移任务

    当预检查通过后，可以启动迁移任务，任务启动成功后，可以在任务列表中查看迁移的具体状态及迁移进度。

    至此，完成自建 Redis 到云 Redis 实例的数据迁移任务配置。


**预检查**

DTS 在启动迁移之前，会进行前置预检查，本小节简单介绍 Redis 数据迁移的预检查内容：

|检查项|检查内容|备注|
|---|----|--|
|源库连接性检查|检查 DTS 服务器跟自建 Redis 实例的连通性| 1.  检查填写信息是否有误。如果填写信息有误，请修改后重新预检查。
2.  检查端口是否允许从其他服务器连接访问。

 |
|目标库连接性检查|检查 DTS 服务器跟目标 Redis 实例的连通性|检查填写信息是否有误，如果有误请先修改后重新预检查。|
|库一对一检查|检查是否存在多个库迁移到一个库的情况|DTS 暂不支持多个库迁移到一个库，如果出现这种情况，那么请先修改任务配置后，重新预检查。|
|目标库是否为空|检查待迁移库在目标 Redis 实例中是否为空|如果检查失败，请先删除目标 Redis 实例中对应库的 Key 后，重新预检查。|
|增量拓扑冲突检查|检查目标 Redis 实例上是否有其他增量迁移任务正在运行|如果检查失败，那么需要结束其他的增量迁移任务后，重新预检查。|

