# 使用redis-shake迁移RDB文件内的数据 {#concept_188715 .concept}

使用redis-shake的restore模式，您可以将自建Redis的备份文件内的数据迁移到一个云数据库Redis版实例中，实现自建Redis上云。

## 前提条件 {#section_5yw_woo_qyl .section}

-   已创建作为迁移目的端的云数据库Redis版实例；
-   已创建用于运行redis-shake的ECS实例；
-   ECS实例可以访问目的Redis实例；
-   ECS实例的系统为Linux；
-   已将备份文件保存到ECS中。

## 背景信息 {#section_o6o_bml_4mz .section}

redis-shake是阿里云自研的开源工具，支持对Redis数据进行解析（decode）、恢复（restore）、备份（dump）、同步（sync/rump）。在restore模式下，redis-shake可以将RDB文件中保存的数据恢复到Redis实例中，实现数据恢复或者迁移，本文以将RDB文件中的数据恢复到云数据库Redis版实例中从而实现Redis上云迁移为例。

**说明：** 

-   如需了解更多redis-shake相关信息，请参见[redis-shake Github主页](https://github.com/aliyun/redis-shake)或[FAQ](https://github.com/alibaba/RedisShake/wiki/%E7%AC%AC%E4%B8%80%E6%AC%A1%E4%BD%BF%E7%94%A8%EF%BC%8C%E5%A6%82%E4%BD%95%E8%BF%9B%E8%A1%8C%E9%85%8D%E7%BD%AE%EF%BC%9F)。

## 操作步骤 {#section_qua_zm1_mx2 .section}

1.  登录可以连接云数据库Redis版实例（目的端Redis）的ECS。
2.  在ECS中下载[redis-shake](https://github.com/alibaba/RedisShake/releases)。

    **说明：** 建议您下载最新发布的版本。

3.  解压redis-shake.tar.gz。

    ``` {#codeblock_4gm_ms4_rue}
    # tar -xvf redis-shake.tar.gz
    ```

    **说明：** 解压获得的redis-shake.linux64为64位Linux系统所需的二进制文件，redis-shake.conf为redis-shake的配置文件，您将在下个步骤对其进行修改。

4.  修改配置文件redis-shake.conf，restore模式涉及的主要参数说明如下。

    |参数|说明|示例|
    |--|--|--|
    |rdb.input|备份文件（RDB文件）的路径，可使用相对路径或绝对路径。|/root/tools/RedisShake/demo.rdb|
    |target.address|目的Redis的连接地址与端口号。|`r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com:6379`|
    |source.password\_raw|目的Redis的连接密码。|`TargetPass233` **说明：** 如使用非默认账号连接云数据库Redis版实例，密码格式为`account:password`。

 |
    |target.db|要将数据恢复到的db，默认为0。例如，要将所有数据恢复到目的Redis的db10，则需将此参数的值设置为10。当该值小于0时，数据将恢复至db0。|`0`|
    |rewrite|如果目的Redis有与RDB文件中相同的key，是否覆盖，可选值：     -   true（覆盖）；
    -   false（不覆盖）。
 **说明：** 默认为true，建议对目的Redis中的有效数据进行完善的备份再执行恢复。如设置为false且存在数据冲突则会出现异常提示。

 |`true`|

    **说明：** 其它参数如无特殊情况保持默认即可。

5.  使用如下命令进行迁移。

    ``` {#codeblock_hdd_lmb_jny}
    # ./redis-shake.linux64 -type=restore -conf=redis-shake.conf
    ```

    **说明：** 此命令需在二进制文件redis-shake.linux64和配置文件redis-shake.conf所在的目录中执行，否则请在命令中指定正确的文件路径。

    ![](images/45611_zh-CN.png "执行示例")

    **说明：** 日志中出现`restore: rdb done`表示数据恢复完成，此时按Ctrl+C退出执行即可。


