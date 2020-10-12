# 查看Redis实例的连接地址 {#concept_apt_fkl_5gb .concept}

您可以在Redis管理控制台上查看Redis实例的内网连接地址（域名）。

**说明：** 

-   为了提高数据安全性，云数据库Redis版目前只支持内网访问，您需要使用阿里云ECS连接Redis实例。
-   Redis实例的VIP地址在服务维护或者变动时可能发生变化，建议在业务中使用连接地址访问Redis实例，确保连接的可用性。

## 操作步骤 {#section_tnf_gkl_5gb .section}

1.  登录[Redis管理控制台](https://kvstore.console.aliyun.com/)。
2.  在界面左上方的菜单栏中选择实例所在的地域 。
3.  在实例列表页，单击目标实例ID或者其右侧**操作**栏的**管理**。
4.  在实例信息页，查看连接信息区域中的**内网连接地址（host）**。

    ![](images/38867_zh-CN.png "Redis连接地址")


