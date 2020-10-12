# 使用redis-shake进行迁移 {#concept_vsd_r1g_chb .concept}

您可以使用redis-shake的sync模式将自建Redis迁移至云数据库Redis版。

## 前提条件 {#section_i54_wj5_1gb .section}

-   已创建作为迁移目的端的云数据库Redis版实例；
-   已创建用于运行redis-shake的ECS实例；
-   ECS实例可以访问目的Redis实例；
-   ECS实例的系统为Linux。

## 背景信息 {#section_vlf_wbg_chb .section}

redis-shake是阿里云自研的开源工具，支持对Redis数据进行解析（decode）、恢复（restore）、备份（dump）、同步（sync/rump）。在sync模式下，redis-shake使用SYNC或PSYNC命令将数据从源端Redis同步到目的端Redis，支持全量数据同步和增量数据同步，增量同步在全量同步完成后自动开始。该模式支持自建Redis上云、自建Redis与云数据库Redis版的同步以及自建Redis之间的同步等场景。本文以使用sync模式将自建Redis上云为例进行说明。

**说明：** 

-   sync模式下源端Redis需支持SYNC、PSYNC命令。如源端为云数据库Redis版，需使用[有复制权限的账号](cn.zh-CN/用户指南/管理实例/账号管理.md#)连接Redis。
-   sync模式支持跨版本同步，如2.8版本实例与4.0版本实例同步。
-   云数据库Redis集群版目前无法作为sync模式的源端。
-   如需了解更多redis-shake相关信息，请参见[redis-shake Github主页](https://github.com/aliyun/redis-shake)或[FAQ](https://github.com/alibaba/RedisShake/wiki/%E7%AC%AC%E4%B8%80%E6%AC%A1%E4%BD%BF%E7%94%A8%EF%BC%8C%E5%A6%82%E4%BD%95%E8%BF%9B%E8%A1%8C%E9%85%8D%E7%BD%AE%EF%BC%9F)。

## 操作步骤 {#section_jmn_lbg_chb .section}

1.  登录可以连接云数据库Redis版实例（目的端Redis）的ECS。
2.  在ECS中下载[redis-shake](https://github.com/alibaba/RedisShake/releases)。

    **说明：** 建议您下载最新发布的版本。

3.  解压redis-shake.tar.gz。

    ``` {#codeblock_1kl_vv1_my0}
    # tar -xvf redis-shake.tar.gz
    ```

    **说明：** 解压获得的redis-shake.linux64为64位Linux系统所需的二进制文件，redis-shake.conf为redis-shake的配置文件，您将在下个步骤对其进行修改。

4.  修改redis-shake配置文件，sync模式涉及的主要参数说明如下。

    |参数|说明|示例|
    |--|--|--|
    |source.address|源Redis的连接地址与服务端口。|`xxx.xxx.1.10:6379`|
    |source.password\_raw|源Redis的连接密码。|`SourcePass233` **说明：** 如源端Redis为云数据库Redis版实例，需使用支持复制权限的账号连接，此时该参数值格式为`account:password`。

 |
    |target.address|目的Redis的连接地址与服务端口。|r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com:6379|
    |target.password\_raw|目的Redis的连接密码，此处为云数据库Redis版实例的密码。|`TargetPass233`|
    |rewrite|如果目的Redis有与RDB文件中相同的key，是否覆盖，可选值：     -   true（覆盖）；
    -   false（不覆盖）。
 **说明：** 默认为true，建议对目的Redis中的有效数据进行完善的备份再执行恢复。如设置为false且存在数据冲突则会出现异常提示。

 |`true`|
    |target.db|设置此参数将源Redis的数据迁移到目的Redis的指定DB。例如，要将所有数据迁移到目的Redis的DB10，则需将此参数的值设置为10。当该值小于0时，数据将迁移至DB0。|`0`|

5.  使用如下命令进行迁移。

    ``` {#codeblock_85b_yui_8e5}
    # ./redis-shake.linux64 -type=sync -conf=redis-shake.conf
    ```

    **说明：** 此命令需在二进制文件redis-shake.linux64和配置文件redis-shake.conf所在的目录中执行，否则请在命令中指定正确的文件路径。

6.  查看同步日志确认同步状态，当出现`sync rdb done`时，全量同步已经完成，同步进入增量阶段。

    ![使用redis-shake迁移Redis的示例](images/40848_zh-CN.png "同步日志")

    **说明：** 

    -   `sync rdb done`之后的日志中，若`+forward=0`，则此时源端没有新的数据写入，同步链路中没有增量数据正在传输，您可以以此为依据选择适当的时机将业务对接到云数据库Redis版。
    -   迁移完成后，您可以使用redis-full-check进行数据校验，确保两端数据一致，详细步骤请参见[校验迁移后的数据](cn.zh-CN/用户指南/迁移数据/校验迁移后的数据.md#)。

