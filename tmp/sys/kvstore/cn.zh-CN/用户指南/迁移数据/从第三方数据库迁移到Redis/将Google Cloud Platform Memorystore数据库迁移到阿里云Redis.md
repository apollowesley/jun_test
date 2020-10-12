# 将Google Cloud Platform Memorystore数据库迁移到阿里云Redis {#concept_bhg_y25_xgb .concept}

您可以使用rump工具将GCP Memorystore实例中的数据迁移到云数据库Redis版中。

## 前提条件 {#section_ak5_4f5_xgb .section}

-   在阿里云平台创建了可以通过内网互通的ECS实例和Redis实例。
-   设置了[公网连接](../../../../../cn.zh-CN/快速入门/连接实例/公网连接.md#)，使阿里云Redis实例可以通过ECS从公网访问。

    **说明：** 设置公网连接仅为了通过公网进行迁移，迁移完成后，为了增强数据安全性，请清除公网连接的相关配置，在业务中通过内网ECS访问Redis。

-   已在GCP Compute Engine中下载了[rump](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/94155/cn_zh/1551331952215/rump)并将其设置为可执行文件。

## 迁移原理 {#section_bwn_135_xgb .section}

Rump工具通过SCAN的方式从Memorystore批量获取key列表，接着使用DUMP命令获取key内容，之后通过PTTL方式获取过期时间，最后以pipeline的方式将数据RESTORE到目的实例中。

**说明：** 该迁移方式不支持增量迁移。

## 操作步骤 {#section_gmr_rh5_xgb .section}

1.  在GCP Compute Engine中使用如下命令进行数据迁移：

    ```
    ./rump -from source_addr -fromPwd source_pwd -to dest_addr -toPwd dest_pwd [-size size] [-replace]
    ```

    |选项|说明|
    |--|--|
    |source\_addr|源Redis实例的地址，此处为GCP Memorystore实例的内网IP地址，格式为`redis://host:port/db`。Host与port为必须参数，db不设置则默认为0。|
    |source\_pwd|源Redis实例的密码，GCP Memorystore实例无密码则省略该选项。|
    |dest\_addr|目的Redis实例的地址，此处为做了公网连接设置的ECS实例的公网IP地址，格式为`redis://host:port/db`。Host与port为必须参数，db不设置则默认为0。|
    |dest\_pwd|目的Redis实例的密码，此处为阿里云Redis实例的密码。|
    |size|单次SCAN并同步的key的数量，默认为10。|
    |replace|设置此选项表示如目的实例存在相同key则直接覆盖。如未设置，迁移中如果出现重复key将显示错误信息。**说明：** 如设置了该选项，请确认目的Redis实例中的重要数据都得到了妥善备份，避免被覆盖后遗失。

|

    ![使用rump迁移Memorystore数据到阿里云Redis](images/39721_zh-CN.png "迁移示例")

2.  在云数据库Redis版中校验数据的完整性。

