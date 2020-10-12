# 使用redis-shake将华为云分布式缓存服务Redis迁移至阿里云 {#concept_610650 .concept}

本文为您介绍通过redis-shake将数据从华为云分布式缓存服务Redis迁移到阿里云云数据库Redis版。

**说明：** 如果源Redis绑定了弹性公网IP，可以通过DTS迁移到阿里云，具体操作请参见[使用DTS将华为云分布式缓存服务Redis迁移至阿里云](cn.zh-CN/用户指南/迁移数据/从第三方数据库迁移到Redis/使用DTS将华为云分布式缓存服务Redis迁移至阿里云.md#)。

## 背景信息 {#section_tml_s5a_88v .section}

redis-shake是阿里云自研的开源工具，支持对Redis数据进行解析（decode）、恢复（restore）、备份（dump）、同步（sync/rump）。 本文将分别介绍使用redis-shake的rump模式和restore模式将华为云分布式缓存服务 Redis 实例迁移到阿里云云数据库Redis版。

**说明：** 

-   rump模式和restore模式均不支持增量迁移，迁移前请停止对源库写入数据。
-   如需了解更多redis-shake相关信息，请参见[redis-shake Github主页](https://github.com/alibaba/RedisShake)和[FAQ](https://github.com/alibaba/RedisShake/wiki/%E7%AC%AC%E4%B8%80%E6%AC%A1%E4%BD%BF%E7%94%A8%EF%BC%8C%E5%A6%82%E4%BD%95%E8%BF%9B%E8%A1%8C%E9%85%8D%E7%BD%AE%EF%BC%9F)。

## 前提条件 {#section_mir_f5l_2ff .section}

-   在阿里云平台创建了可以通过内网互通的ECS实例和目的Redis实例。
-   在华为云平台创建了可以通过内网互通的ECS实例和源Redis实例。
-   阿里云ECS实例操作系统为Linux。
-   使用rump模式迁移需要华为云ECS实例绑定弹性公网IP。
-   使用restore模式迁移需要对待迁移的Redis数据进行备份。

## 迁移模式说明 {#section_u91_zgt_7i8 .section}

|模式|说明|
|--|--|
|rump|redis-shake以SCAN的方式从源端Redis获取全量数据，写入到目的端，实现数据迁移。|
|restore|redis-shake可以将RDB文件中保存的数据恢复到Redis实例中，实现数据恢复或者迁移。|

## rump模式操作步骤 {#section_r2p_8kb_ynp .section}

1.  登录华为云ECS实例，将源Redis实例映射到云服务器公网。

    **说明：** 

    -   Windows操作系统的云服务器可以直接在CMD中执行如下命令：

        ``` {#codeblock_il6_slv_mvj}
        netsh interface portproxy add v4tov4 listenaddress=<华为云ECS的私网IP地址> listenport=6379 connectaddress=<源Redis的连接地址> connectport=6379
        ```

    -   Linux操作系统的云服务器设置端口映射的方式可以参考[公网连接](../../../../cn.zh-CN/快速入门/步骤3：连接实例/公网连接.md#)。
2.  在阿里云ECS中下载[redis-shake](https://github.com/alibaba/RedisShake/releases)。

    **说明：** 建议您下载最新发布的版本。

3.  解压redis-shake.tar.gz。

    ``` {#codeblock_b9s_78p_b22}
    # tar -xvf redis-shake.tar.gz
    ```

    **说明：** 解压获得的redis-shake.linux64为64位Linux系统所需的二进制文件，redis-shake.conf为redis-shake的配置文件，您将在下个步骤对其进行修改。

4.  修改redis-shake配置文件，rump模式涉及的主要参数的说明如下。

    |参数|说明|示例值|
    |--|--|---|
    |source.address|源端Redis的连接地址与服务端口。|`118.**.**.146:6379`|
    |source.password\_raw|源端Redis的连接密码。|`SourcePass233`|
    |target.address|目的端Redis的连接地址与服务端口。|`r-j6cxxxxxxxxxxxxx.redis.rds.aliyuncs.com`|
    |target.password\_raw|目的端Redis的连接密码。|`TargetPass233`|
    |rewrite|如果目的Redis有与RDB文件中相同的key，是否覆盖，可选值：     -   true（覆盖）；
    -   false（不覆盖）。
 **说明：** 默认为true，建议对目的Redis中的有效数据进行完善的备份再执行恢复。如设置为false且存在数据冲突则会出现异常提示。

 |`true`|
    |scan.key\_number|每次SCAN获取的key的个数，不配置则默认为100。|`100`|
    |qps|用于限制传输速度。 **说明：** 如果希望1秒同步不超过1000个key，可以设置为`qps=1000`。

 |`200000`|

5.  使用如下命令进行迁移。

    ``` {#codeblock_tj6_kvd_ooe}
    # ./redis-shake -type=rump -conf=redis-shake.conf
    ```

    **说明：** 此命令需在二进制文件redis-shake和配置文件redis-shake.conf所在的目录中执行，否则请在命令中指定正确的文件路径。

    ![](images/49432_zh-CN.png "rump模式迁移示例")

    **说明：** 出现上图中的提示即表示数据迁移完成。您可以使用redis-full-check进行数据校验，确保两端数据一致，详细步骤请参见[校验迁移后的数据](cn.zh-CN/用户指南/迁移数据/校验迁移后的数据.md#)。


## restore模式操作步骤 {#section_jyw_dr8_0ku .section}

1.  登录华为云分布式缓存服务控制台，单击左侧**缓存管理**。
2.  在缓存管理页面，单击需要迁移的Redis实例名称。
3.  单击**备份与恢复** \> **下载**，在下载备份文件页面单击**复制URL**。

    **说明：** 支持下载备份文件的Redis版本请参见华为云分布式缓存服务文档。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/492094/156093019549071_zh-CN.png)

4.  登录可以连接目标 Redis 实例的ECS。
5.  在ECS中下载源 Redis 实例的备份文件。

    ``` {#codeblock_cfu_v7f_bkr}
    wget -O 'redis.rdb' "https://下载文件路径”
    ```

6.  在ECS中下载[redis-shake](https://github.com/alibaba/RedisShake/releases)。

    **说明：** 建议您下载最新发布的版本。

7.  解压redis-shake.tar.gz。

    ``` {#codeblock_o4r_mze_c3a}
    tar -xvf redis-shake.tar.gz
    ```

    **说明：** 解压获得的redis-shake.linux64为64位Linux系统所需的二进制文件，redis-shake.conf为redis-shake的配置文件，您将在下个步骤对其进行修改。

8.  修改配置文件redis-shake.conf，restore模式涉及的主要参数说明如下。

    |参数|说明|示例|
    |--|--|--|
    |target.address|目的Redis的连接地址与端口号。|`r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com:6379`|
    |source.password\_raw|目的Redis的连接密码。|`TargetPass233` **说明：** 如使用非默认账号连接云数据库Redis版实例，密码格式为`account:password`。

 |
    |rdb.input|备份文件（RDB文件）的路径，可使用相对路径或绝对路径。|/root/redis.rdb|
    |target.db|要将数据恢复到的db，默认为0。例如，要将所有数据恢复到目的Redis的db10，则需将此参数的值设置为10。当该值小于0时，数据将恢复至db0。|`0`|
    |rewrite|如果目的Redis有与RDB文件中相同的key，是否覆盖，可选值：     -   true（覆盖）；
    -   false（不覆盖）。
 **说明：** 默认为true，建议对目的Redis中的有效数据进行完善的备份再执行恢复。如设置为false且存在数据冲突则会出现异常提示。

 |`true`|
    |parallel|RDB文件同步中使用的并发线程数，用于提高同步性能。 **说明：** 

    -   最小值为1
    -   最大值取决于服务器性能
    -   推荐值为64
 |`64`|

    **说明：** 其它参数如无特殊情况保持默认即可。

9.  使用如下命令进行迁移。

    ``` {#codeblock_3vb_63v_gwc}
    ./redis-shake.linux64 -type=restore -conf=redis-shake.conf
    ```

    **说明：** 此命令需在二进制文件redis-shake.linux64和配置文件redis-shake.conf所在的目录中执行，否则请在命令中指定正确的文件路径。

10. 日志中出现`restore: rdb done`表示数据恢复完成，按Ctrl+C退出执行即可。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/492094/156093019549082_zh-CN.png)


