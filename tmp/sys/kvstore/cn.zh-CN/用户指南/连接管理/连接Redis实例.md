# 连接Redis实例 {#task_1715483 .task}

您可以通过客户端、redis-cli等多种方式连接Redis实例。

## 连接方式 {#section_fk8_zc3_a0w .section}

|连接工具|说明|
|----|--|
|[DMS](../../../../cn.zh-CN/快速入门/步骤3：连接实例/DMS登录云数据库.md#)|使用阿里云数据管理（DMS）连接Redis实例并管理数据。|
|[Redis客户端](../../../../cn.zh-CN/快速入门/步骤3：连接实例/Redis客户端连接.md#)|使用多种语言的客户端连接Redis实例。|
|[redis-cli](../../../../cn.zh-CN/快速入门/步骤3：连接实例/redis-cli连接.md#)|使用原生Redis自带的工具redis-cli连接Redis实例。|

## 内网连接 {#section_iwi_k7j_gag .section}

在未申请[外网连接地址](../../../../cn.zh-CN/快速入门/步骤3：连接实例/外网连接.md#)前，云数据库Redis版实例仅支持通过阿里云内网连接。ECS等产品通过内网连接Redis的前提如下：

-   ECS实例和Redis实例在相同的[地域（Region）](../../../../cn.zh-CN/通用参考/地域和可用区.md#section_ug5_k5k_xdb)；
-   ECS实例和Redis实例的[网络类型](cn.zh-CN/用户指南/实例管理/切换为专有网络.md#section_snh_bbg_vdb)都是经典网络，或者都在同一个VPC中；
-   ECS实例的内网地址在Redis实例的[白名单](../../../../cn.zh-CN/快速入门/步骤2：设置白名单.md#)中。

## 外网连接 {#section_gfq_1wg_qq7 .section}

[申请外网连接地址](../../../../cn.zh-CN/快速入门/步骤3：连接实例/外网连接.md#)后，您可以在本地主机上通过Redis实例的外网连接地址直接访问Redis实例。

## 连接问题排查 {#section_5sw_k8h_ssw .section}

-   内网连接失败请参见[Redis连接问题排查与解决](../../../../cn.zh-CN/常见问题/Redis连接问题排查与解决.md#)。
-   外网连接失败请参见[外网连接失败的解决方法](../../../../cn.zh-CN/快速入门/步骤3：连接实例/外网连接.md#section_y2n_4gp_hb5)。

