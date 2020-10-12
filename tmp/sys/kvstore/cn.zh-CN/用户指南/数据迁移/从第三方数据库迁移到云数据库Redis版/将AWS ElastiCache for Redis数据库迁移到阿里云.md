# 将AWS ElastiCache for Redis数据库迁移到阿里云 {#task_1715668 .task}

本文为您介绍如何将数据从AWS ElastiCache for Redis实例迁移到阿里云ApsaraDB for Redis实例。

-   已创建作为迁移目的端的云数据库Redis版实例，创建实例请参见[创建实例](../../../../cn.zh-CN/快速入门/步骤1：创建实例.md#)。
-   已创建用于运行redis-shake的ECS实例，ECS实例为64位的Linux系统，创建ECS实例请参见[创建ECS实例](https://help.aliyun.com/document_detail/25424.html)。
-   ECS实例可以访问目的Redis实例。

    **说明：** 

    -   如果ECS实例与目的Redis实例在同一可用区的VPC中，可以在Redis白名单中添加ECS内网IP，添加白名单请参见[设置白名单](../../../../cn.zh-CN/快速入门/步骤2：设置白名单.md#)。
    -   如果ECS实例与目的Redis实例不在同一可用区的VPC中，可以通过外网地址访问，详情请参见[外网连接](../../../../cn.zh-CN/快速入门/步骤3：连接实例/外网连接.md#)。

## 注意事项 {#section_e07_5sk_cd8 .section}

-   执行该操作前建议停止将数据继续写入AWS ElastiCache for Redis。
-   执行该操作前请提前做好数据备份。
-   执行该操作前需要规划业务停机时间。
-   如果要迁移的Redis实例是在服务器上自行构建的，建议您使用阿里云[数据传输服务DTS](https://help.aliyun.com/document_detail/26592.html)迁移数据。

## 从AWS ElastiCache for Redis备份和导出数据到S3存储桶 {#section_kl3_g1o_bm7 .section}

1.  创建备份到特定集群。单击左侧导航栏中的Redis，选择需要备份的**集群名称**，单击**备份**，**资源名称**里选择需要备份的集群，填写**备份名称**后单击**创建快照**。 

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83060/156741110735338_zh-CN.png)

2.  将备份文件导出到AWS S3存储桶。单击左侧导航栏中的备份，选择需要导出的备份文件，单击上方的**复制**，填写**新缓存快照标识符**名称，选择**目标S3位置**，单击右下角**复制**，导出过程将开始。 

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83060/156741110735339_zh-CN.png)

3.  您可以在S3存储桶中找到导出的RDB文件。 

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83060/156741110735448_zh-CN.png)

4.  从S3存储桶下载RDB文件。 

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83060/156741110735449_zh-CN.png)


## 操作步骤 {#section_zbs_9cp_wrd .section}

1.  登录可以连接云数据库Redis版实例（目的端Redis）的ECS。
2.  将源Redis实例的RDB下载到ECS实例。 

    **说明：** 

    -   您可以使用命令`wget <RDB文件下载链接>`将RDS文件下载至ECS实例。
    -   也可以使用MobaXterm Personal Edition客户端，通过SFTP协议将下载到本地的RDB文件上传到阿里云ECS实例。
3.  在ECS中下载[redis-shake](https://github.com/alibaba/RedisShake/releases)。 

    **说明：** 建议您下载最新发布的版本。

4.  解压redis-shake.tar.gz。 

    ``` {#codeblock_36f_t23_xsm}
    # tar -xvf redis-shake.tar.gz
    ```

    **说明：** 解压获得的redis-shake为64位Linux系统所需的二进制文件，redis-shake.conf为redis-shake的配置文件，您将在下个步骤对其进行修改。

5.  修改配置文件redis-shake.conf，restore模式涉及的主要参数说明如下。 

    |参数|说明|示例|
    |--|--|--|
    |rdb.input|备份文件（RDB文件）的路径，可使用相对路径或绝对路径。|/root/tools/RedisShake/demo.rdb|
    |target.address|目的Redis的连接地址与端口号。|`r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com:6379`|
    |target.password\_raw|目的Redis的连接密码。|`TargetPass233` **说明：** 如使用非默认账号连接云数据库Redis版实例，密码格式为`account:password`。

 |
    |target.db|要将数据恢复到的db，默认为0。例如，要将所有数据恢复到目的Redis的db10，则需将此参数的值设置为10。当该值小于0时，数据将恢复至db0。|`0`|
    |rewrite|如果目的Redis有与RDB文件中相同的key，是否覆盖，可选值：     -   true（覆盖）；
    -   false（不覆盖）。
 **说明：** 默认为true，建议对目的Redis中的有效数据进行完善的备份再执行恢复。如设置为false且存在数据冲突则会出现异常提示。

 |`true`|
    |parallel|RDB文件同步中使用的并发线程数，用于提高同步性能。 **说明：** 

    -   最小值为1
    -   最大值取决于服务器性能
    -   推荐值为64
 |64|

    **说明：** 其它参数如无特殊情况保持默认即可。

6.  使用如下命令进行迁移。 

    ``` {#codeblock_ndp_n2w_xl6}
    # ./redis-shake -type=restore -conf=redis-shake.conf
    ```

    **说明：** 此命令需在二进制文件redis-shake和配置文件redis-shake.conf所在的目录中执行，否则请在命令中指定正确的文件路径。

7.  日志中出现`restore: rdb done`表示数据恢复完成，此时按Ctrl+C退出执行即可。 

    ![](images/45611_zh-CN.png "执行示例")


