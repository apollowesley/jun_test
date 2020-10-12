# 将MySQL数据迁移到Redis {#concept_978559 .concept}

使用Redis的管道传输功能，您可以将RDS for MySQL或本地MySQL的数据快速迁移到Redis中。使用其它引擎的RDS数据库也可以参照本文的方法将数据迁移到Redis中。

## 场景介绍 {#section_e26_7ln_pij .section}

在应用与数据库之间使用Redis作为缓存层，扩展传统关系型数据库的服务能力，从而优化业务的生态体系，是Redis的经典应用场景之一。将业务中的热数据保存到Redis，用户通过应用直接从Redis中快速获取常用数据，或者在交互式应用中使用Redis保存活跃用户的会话，都可以极大地降低后端关系型数据库的负载，提升用户体验。

使用Redis作为缓存首先需要将关系型数据库中的数据传输到Redis中。关系型数据库中库表结构的数据无法直接传入以键值结构保存数据的Redis数据库，迁移前需要将源端数据转换为特定的结构。这篇最佳实践以MySQL向Redis整表迁移为例，介绍如何通过原生工具进行简单高效地迁移。MySQL的表数据将通过Redis Pipeline传输并保存到Redis Hash中。

**说明：** 本文使用阿里云RDS for MySQL实例和云数据库Redis版实例作为迁移的源端和目的端，运行迁移命令的Linux环境安装在ECS实例中。三者同在一个VPC，因此可以互通。

您可以用类似的方法将其它关系型数据库中的数据迁移到Redis中。这种从源端数据库提取数据，转换格式后传入异构数据库中的方式也适用于其它异构数据库之间的数据迁移。

## 前提条件 {#section_jg5_zly_3h7 .section}

-   已创建作为源端的RDS for MySQL实例且其中已存在可供迁移的表数据。
-   已创建作为目的端的云数据库Redis版实例。
-   已创建Linux系统的ECS实例。
-   以上三个实例在同一地域的同一VPC中。
-   RDS for MySQL和Redis实例的白名单中已经放通了ECS实例的内网地址。
-   ECS中已安装了MySQL和Redis，用于进行数据的提取、转换和传输。

**说明：** 以上前提条件仅在您的环境在阿里云上时适用，如果您要在本地环境使用本文的方法，请确保用于执行迁移的Linux服务器能够连通源端的关系型数据库和目的端的Redis数据库。

## 迁移前的数据 {#section_d47_2ti_c2m .section}

本文展示的是迁移**custm\_info**库中**company**表储存的测试数据的过程。**company**表中包含的测试数据如下所示。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/790664/156232036250822_zh-CN.png)

表中共有6列，迁移后，MySQL表中id列的值将成为Redis中hash的key，其余列的列名将成为hash的field，而列的值则作为field对应的value。您可以根据实际场景调整迁移步骤中的脚本和命令。

## 迁移步骤 {#section_7d1_tdc_6o9 .section}

1.  分析源端数据结构，在ECS中创建如下的迁移脚本，保存到名为mysql\_to\_redis.sql的文件中。

    ``` {#codeblock_2kt_fhs_vhu}
    SELECT CONCAT(
      "*12\r\n", #这里的12是下方字段的数量，由MySQL表中的数据结构决定
      '$', LENGTH('HMSET'), '\r\n', #HMSET是在Redis中写入数据时使用的命令
      'HMSET', '\r\n',
      '$', LENGTH(id), '\r\n', #id是HMSET字段后的第一个字段，迁移后会成为Redis Hash中的key
      id, '\r\n',
      '$', LENGTH('name'), '\r\n', #'name'将以字符串形式传入hash中，作为其中一个field。下面的'sdate'等与它相同
      'name', '\r\n',
      '$', LENGTH(name), '\r\n', #name是一个变量，代表了MySQL表中公司的名称，迁移后会成为上一参数'name'生成的field所对应的value。下面的sdate等与它相同
      name, '\r\n',
      '$', LENGTH('sdate'), '\r\n',
      'sdate', '\r\n',
      '$', LENGTH(sdate), '\r\n',
      sdate, '\r\n',
      '$', LENGTH('email'), '\r\n',
      'email', '\r\n',
      '$', LENGTH(email), '\r\n',
      email, '\r\n',
      '$', LENGTH('domain'), '\r\n',
      'domain', '\r\n',
      '$', LENGTH(domain), '\r\n',
      domain, '\r\n',
      '$', LENGTH('city'), '\r\n',
      'city', '\r\n',
      '$', LENGTH(city), '\r\n',
      city, '\r\n'
    )
    FROM company AS c
    ```

2.  在ECS中使用如下命令迁移数据。

    ``` {#codeblock_sn3_uif_3nz}
    mysql -h <MySQL host> -P <MySQL port> -u <MySQL username> -D <MySQL database name> -p --skip-column-names --raw < mysql_to_redis.sql | redis-cli -h <Redis host> --pipe -a <Redis password>
    ```

    |选项|说明|示例值|
    |--|--|---|
    |-h|MySQL数据库的连接地址 **说明：** 这里指`mysql`之后的-h。

 |rm-bp1xxxxxxxxxxxx.mysql.rds.aliyuncs.com **说明：** 请使用Linux服务器连接MySQL数据库所需的地址。

 |
    |-P|MySQL数据库的服务端口|3306|
    |-u|MySQL数据库的用户名|testuser|
    |-D|需要迁移的MySQL表所在的库|mydatabase|
    |-p|MySQL数据库的连接密码 **说明：** 

    -   如无密码则无需设置该选项。
    -   为了提高安全性，您可以只输入-p，不在其后输入密码，执行命令后再根据命令行提示输入密码。
 |Mysqlpwd233|
    |--skip-column-names|不在查询结果中写入列名。|无需设置值。|
    |--raw|输出列的值时不进行转义。|无需设置值。|
    |-h|Redis的连接地址 **说明：** 这里指`redis-cli`之后的-h。

 |r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com **说明：** 请使用Linux服务器连接Redis数据库所需的地址。

 |
    |--pipe|使用Redis的Pipeline功能进行传输。|无需设置值。|
    |-a|Redis的连接密码 **说明：** 如无密码或不需要密码则无需设置该选项。

 |Redispwd233|

    ![](images/50826_zh-CN.png "运行示例")

    **说明：** 执行结果中的`errors`表示执行过程中的错误数，`replies`表示收到的回复数。如果`errors`为0，且`replies`与MySQL表中的记录数相同，则整表迁移成功。


## 迁移后的数据 {#section_0nn_4kx_5jx .section}

迁移完成后，一条MySQL表记录对应一条Redis Hash数据。使用HGETALL命令查询一条记录可以看到如下结果。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/790664/156232036250847_zh-CN.png)

您可以根据实际场景中需要的查询方式调整迁移方案，例如把MySQL数据中的其它列转换为hash中的key，而把id列转换为field，或者直接省略id列。

