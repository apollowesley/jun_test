# 如何确认ECS和Redis的网络环境是否相同 {#task_1909341 .task}

为了使ECS实例与Redis实例通过内网连接，您需要确保二者的网络类型同为经典网络，或者同为专有网络（VPC）且所属的VPC相同。

## 确认ECS实例的网络环境 {#section_mad_r3f_oft .section}

1.  登录[ECS管理控制台](https://ecs.console.aliyun.com)。
2.  在左侧导航栏选择**实例与镜像** \> **实例**。 

    ![进入ECS实例列表页](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/1513206/156691543557938_zh-CN.png)

3.  在顶部状态栏选择实例所在的地域。
4.  在实例列表页，查看目标ECS实例的网络类型栏。 
    -   如果是经典网络实例，该栏显示**经典网络**。
    -   如果是专有网络实例，该栏显示**专有网络**。请按照下方子步骤查看VPC的ID。
    1.  单击目标实例的ID。
    2.  在实例详情页的配置信息区域，查看**专有网络**的VPC ID。 

        ![查看ECS实例的VPC](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/1513206/156691543557943_zh-CN.png)


## 确认Redis实例的网络环境 {#section_dpd_9vl_gei .section}

1.  登录[Redis管理控制台](https://kvstore.console.aliyun.com/)。
2.  在界面左上方阿里云图标的右侧选择实例所在的地域。
3.  在实例列表页，查看目标Redis实例的网络类型栏。 

    -   如果是经典网络实例，该栏显示**经典网络**。
    -   如果是专有网络实例，该栏包含两行信息，第一行显示**VPC网络**，第二行显示VPC的ID。
    ![Check the network information of the Redis instance.](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/1513206/156691543557919_zh-CN.png)


## 确认ECS实例和Redis实例能否通过内网连接 {#section_qsl_4sg_bnj .section}

|场景|能否通过内网连接|不能连接的解决方法|
|--|--------|---------|
|ECS实例和Redis实例的网络类型都是经典网络。|能 **说明：** 连接前需要在Redis实例的[白名单](../../../../cn.zh-CN/快速入门/步骤2：设置白名单.md#)中添加ECS实例的内网IP。

 |无需|
|ECS实例和Redis实例的网络类型都是专有网络，并且VPC的ID相同。|能 **说明：** 连接前需要在Redis实例的[白名单](../../../../cn.zh-CN/快速入门/步骤2：设置白名单.md#)中添加ECS实例的内网IP。

 |无需|
|ECS实例和Redis实例的网络类型不同。|不能|将二者的网络类型统一转换为VPC。请参见[将ECS 迁移到VPC网络](https://help.aliyun.com/document_detail/55051.html)或[切换Redis实例的网络类型](../../../../cn.zh-CN/用户指南/实例管理/切换为专有网络.md#)。|
|ECS实例和Redis实例的网络类型都是经典网络，但所在地域不同。|不能|使用redis-shake的sync模式将Redis实例迁移到ECS实例所在地域，详情请参见[使用redis-shake进行迁移](../../../../cn.zh-CN/用户指南/数据迁移/云下到云上/使用redis-shake进行迁移.md#)。|
|ECS实例和Redis实例的网络类型都是专有网络，但VPC ID不同。|不能|使用redis-shake的sync模式将Redis实例迁移到ECS实例所在的VPC，详情请参见[使用redis-shake进行迁移](../../../../cn.zh-CN/用户指南/数据迁移/云下到云上/使用redis-shake进行迁移.md#)。|

