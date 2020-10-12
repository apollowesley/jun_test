# 使用redis-shake在云数据库Redis版实例之间迁移 {#concept_226440 .concept}

您可以使用redis-shake的rump模式将一个云数据库Redis版实例中的数据迁移到同一阿里云账号下的另一个实例中。

## 前提条件 {#section_pi3_gr7_2eg .section}

-   已创建作为迁移目的端的云数据库Redis版实例；
-   已创建用于运行redis-shake的ECS；
-   ECS可以访问源端和目的端Redis；
-   ECS的系统为Linux。

## 背景信息 {#section_uv4_c4p_8xh .section}

redis-shake是阿里云自研的开源工具，支持对Redis数据进行解析（decode）、恢复（restore）、备份（dump）、同步（sync/rump）。在rump模式下，redis-shake以SCAN的方式从源端Redis获取全量数据，写入到目的端，实现数据迁移。这种迁移方式不依赖于SYNC或PSYNC，对Redis服务性能的影响小，支持Redis集群，可以广泛应用于自建Redis、云Redis之间的迁移。本文以将数据从一个云数据库Redis版实例迁移到另一个云数据库Redis版实例为例进行说明。

**说明：** 

-   rump模式不支持增量数据迁移，建议您先停止源端Redis的写入再进行迁移，防止数据不一致。
-   rump模式支持跨版本迁移，如2.8版本实例到4.0版本实例的迁移。
-   rump模式支持跨云迁移，但需至少有一端支持公网访问。
-   如需了解更多redis-shake相关信息，请参见[redis-shake Github主页](https://github.com/aliyun/redis-shake)或[FAQ](https://github.com/alibaba/RedisShake/wiki/%E7%AC%AC%E4%B8%80%E6%AC%A1%E4%BD%BF%E7%94%A8%EF%BC%8C%E5%A6%82%E4%BD%95%E8%BF%9B%E8%A1%8C%E9%85%8D%E7%BD%AE%EF%BC%9F)。

## 操作步骤 {#section_0ne_wlk_e5d .section}

1.  登录可以连接源端和目的端Redis的ECS。
2.  在ECS中下载[redis-shake](https://github.com/alibaba/RedisShake/releases)。

    **说明：** 建议您下载最新发布的版本。

3.  解压redis-shake.tar.gz。

    ``` {#codeblock_os5_5t1_5yd}
    # tar -xvf redis-shake.tar.gz
    ```

    **说明：** 解压获得的redis-shake为64位Linux系统所需的二进制文件，redis-shake.conf为redis-shake的配置文件，您将在下个步骤对其进行修改。

4.  修改redis-shake配置文件，rump模式涉及的主要参数的说明如下。

    |参数|说明|示例值|
    |--|--|---|
    |source.address|源端Redis的连接地址与服务端口。| `r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com` |
    |source.password\_raw|源端Redis的连接密码。| `SourcePass233` **说明：** 如使用非默认账号连接云数据库Redis版实例，密码格式为`account:password`。

 |
    |target.address|目的端Redis的连接地址与服务端口。| `r-j6cxxxxxxxxxxxxx.redis.rds.aliyuncs.com` |
    |target.password\_raw|目的端Redis的连接密码。| `TargetPass233` |
    |rewrite|如果目的Redis有与RDB文件中相同的key，是否覆盖，可选值：     -   true（覆盖）；
    -   false（不覆盖）。
 **说明：** 默认为true，建议对目的Redis中的有效数据进行完善的备份再执行恢复。如设置为false且存在数据冲突则会出现异常提示。

 | `true` |
    |scan.key\_number|每次SCAN获取的key的个数，不配置则默认为100。| `100` |
    |scan.special\_cloud|用于支持特殊版本云Redis的迁移。| `aliyun_cluster` **说明：** 该示例值适用于源端为阿里云Redis集群版实例的迁移。

 |

5.  使用如下命令进行迁移。

    ``` {#codeblock_5iu_0n5_d0a}
    # ./redis-shake -type=rump -conf=redis-shake.conf
    ```

    **说明：** 此命令需在二进制文件redis-shake和配置文件redis-shake.conf所在的目录中执行，否则请在命令中指定正确的文件路径。

    ![](images/46084_zh-CN.png "rump模式迁移示例")

    **说明：** 出现上图中的提示即表示数据迁移完成。您可以使用redis-full-check进行数据校验，确保两端数据一致，详细步骤请参见[校验迁移后的数据](cn.zh-CN/用户指南/迁移数据/校验迁移后的数据.md#)。


