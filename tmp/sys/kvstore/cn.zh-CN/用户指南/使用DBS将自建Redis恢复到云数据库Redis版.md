# 使用DBS将自建Redis恢复到云数据库Redis版 {#concept_186707 .concept}

您可以使用阿里云数据库备份服务DBS（Database Backup Service）对自建的Redis数据库进行备份，然后将备份数据恢复到一个云数据库Redis版实例中，实现Redis数据的安全备份或者便捷上云。

数据库备份DBS是为数据库提供持续数据保护的低成本备份服务。使用DBS可以实现如下Redis相关操作：

-   备份自建Redis，支持：
    -   可通过公网访问的本地自建Redis；
    -   ECS上的自建Redis；
    -   通过专线VPN网关或智能网关等接入的自建Redis。
-   备份云数据库Redis版。
-   将自建Redis通过数据恢复的方式迁移到云数据库Redis版。

详细操作步骤请参见[此文档](https://help.aliyun.com/document_detail/114265.html)。

