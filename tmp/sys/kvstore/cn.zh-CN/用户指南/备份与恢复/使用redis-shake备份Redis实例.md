# 使用redis-shake备份Redis实例 {#concept_287091 .concept}

您可以使用redis-shake的dump模式将云数据库Redis版实例中的数据备份到RDB文件中。

## 前提条件 {#section_563_zrq_510 .section}

-   已创建拥有复制权限的Redis账号，创建账号操作方法请参见[账号管理](cn.zh-CN/用户指南/管理实例/账号管理.md#)。
-   云数据库Redis架构类型为标准版或单节点的读写分离版。
-   云数据库Redis版为Redis 4.0版本。
-   已创建用于运行redis-shake的ECS实例。
    -   ECS可以访问需要备份的Redis实例。
    -   ECS的系统为Linux。
    -   ECS磁盘剩余空间需大于RDB文件占用空间。

## 背景信息 {#section_tbn_j9r_xxr .section}

redis-shake是阿里云自研的开源工具，支持对Redis数据进行解析（decode）、恢复（restore）、备份（dump）、同步（sync/rump）。在dump模式下，redis-shake可以将Redis数据库的数据保存到RDB文件中，通过RDB文件可以实现数据恢复或者迁移。本文以使用dump模式备份云数据库Redis版实例的数据到RDB文件为例。

**说明：** 

-   redis-shake可通过RDB文件实现数据恢复或者迁移，详细信息请参见[使用redis-shake迁移RDB文件内的数据](cn.zh-CN/用户指南/迁移数据/云下到云上/使用redis-shake迁移RDB文件内的数据.md#)。
-   如需了解更多redis-shake相关信息，请参见[redis-shake Github主页](https://github.com/alibaba/RedisShake)或[FAQ](https://github.com/alibaba/RedisShake/wiki/%E7%AC%AC%E4%B8%80%E6%AC%A1%E4%BD%BF%E7%94%A8%EF%BC%8C%E5%A6%82%E4%BD%95%E8%BF%9B%E8%A1%8C%E9%85%8D%E7%BD%AE%EF%BC%9F)。

## 操作步骤 {#section_kd4_f56_pbk .section}

1.  登录可以连接云数据库Redis版实例（目的端Redis）的ECS。
2.  在ECS中下载[redis-shake](https://github.com/alibaba/RedisShake/releases)。

    **说明：** 建议您下载最新发布的版本。

3.  解压`redis-shake.tar.gz`。

    ``` {#codeblock_rsv_x6j_k9n}
    tar -xvf redis-shake.tar.gz
    ```

    **说明：** 解压获得的 redis-shake为64位Linux系统所需的二进制文件， redis-shake.conf为redis-shake的配置文件，您将在下个步骤对其进行修改。

4.  修改redis-shake配置文件，dump模式涉及的主要参数说明如下。

    |参数|说明|示例|
    |--|--|--|
    |source.address|源Redis的连接地址与服务端口。|xxxxxxxxxxxx.redis.rds.aliyuncs.com:6379|
    |source.password\_raw|源Redis的连接密码。|account:password|
    |rdb.output|输出的RDB文件名称。|local\_dump|

5.  使用如下命令进行迁移。

    ``` {#codeblock_1x7_mkf_e30}
    ./redis-shake -type=dump -conf=redis-shake.conf
    ```

    **说明：** 此命令需在二进制文件 redis-shake和配置文件 redis-shake.conf所在的目录中执行，否则请在命令中指定正确的文件路径。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/236144/156039577947834_zh-CN.jpg)

    **说明：** 

    -   日志中出现`execute runner[*run.CmdDump] finished!`表示RDB文件备份完成。
    -   RDB文件名称默认为`local_dump.0`，可使用`cat local_dump.0`命令确认Redis数据是否备份成功。

## 下一步（可选） {#section_4k4_5t0_uey .section}

将RDB文件恢复到redis，具体请参考[使用redis-shake迁移RDB文件内的数据](cn.zh-CN/用户指南/迁移数据/云下到云上/使用redis-shake迁移RDB文件内的数据.md#)。

