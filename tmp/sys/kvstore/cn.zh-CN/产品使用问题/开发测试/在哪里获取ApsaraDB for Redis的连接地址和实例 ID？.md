# 在哪里获取ApsaraDB for Redis的连接地址和实例 ID？ {#concept_njb_drv_ydb .concept}

在用Redis客户端连接ApsaraDB for Redis的时候，需要输入连接地址（hostname）、端口号（6379）和实例ID，这些信息都可以在ApsaraDB for Redis控制台的实例信息页中获取。

## 获取步骤 {#section_nbl_xuy_hdg .section}

1.  登录[Redis管理控制台](https://kvstore.console.aliyun.com/)。
2.  在界面左上方阿里云图标的右侧选择实例所在的地域 。
3.  在实例列表页，单击目标实例ID或者其右侧**操作**栏的**管理**。

    **说明：** 在实例列表中可以查看当前地域所有实例的ID。

4.  在实例信息页，查看**基本信息**区域的**实例ID**，以及**连接信息**区域的**内网连接地址（host）**、**外网连接地址（host）**和二者对应的**端口号**。

    **说明：** Redis外网连接地址的申请方法请参见[外网连接](../../../../cn.zh-CN/快速入门/步骤3：连接实例/外网连接.md#)。

    ![](images/51078_zh-CN.png "Redis连接信息")


