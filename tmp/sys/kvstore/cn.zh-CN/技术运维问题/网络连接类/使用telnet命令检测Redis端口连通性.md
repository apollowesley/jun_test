# 使用telnet命令检测Redis端口连通性 {#concept_ygd_gpt_vgb .concept}

检查端口的连通性是排查Redis连接问题的重要步骤之一。

## 前提条件 {#section_kx1_wst_vgb .section}

ECS系统中已经安装了Telnet（Linux）或开启了Telnet客户端（Windows）。

## 背景信息 {#section_anr_5pt_vgb .section}

如果Redis服务出现了连接问题，并且[使用ping命令检测ECS与Redis之间的连接](intl.zh-CN/技术运维问题/网络连接类/使用ping命令检测ECS与Redis之间的连接.md#)成功，您需要进一步使用telnet命令检测服务端口是否可用。

## 操作步骤 {#section_tjd_15t_vgb .section}

1.  [查询Redis实例的连接地址](../../../../intl.zh-CN/用户指南/连接管理/查看连接地址.md#)。
2.  登录ECS系统并在命令行中使用如下命令。

    ```
    telnet <host> 6379
    ```

    **说明：** 

    -   命令中的`<host>`为第1步查询到的连接地址。
    -   6379为云数据库Redis版默认的端口号。
    -   Windows系统和Linux系统中都可以使用该命令。
     ![](images/39034_zh-CN.png "Linux系统执行telnet命令示例") 

     ![](images/39016_zh-CN.png "Windows系统执行telnet命令示例") 

3.  查看测试结果。请参考以下Linux系统与Windows系统结果示例。
    -   telnet连接成功显示界面：

         ![](images/39036_zh-CN.png "Linux系统telnet成功示例") 

        ![Windows系统telnet成功示例](images/39017_zh-CN.png "Window系统telnet成功示例")

    -   telnet连接失败显示界面：

        ![Linux系统telnet连接阿里云Redis失败示例](images/39045_zh-CN.png "Linux系统telnet失败示例")

        ![Windows系统telnet连接阿里云Redis失败示例](images/39044_zh-CN.png "Windows系统telnet失败示例")


## 结果分析 {#section_svh_tq5_vgb .section}

-   如果Redis连接存在问题，但可以在ECS上使用telnet连接到Redis实例，则ECS本身与Redis之间的连接无异常，请排查其它因素，例如客户端、业务代码，以及业务环境导致的Redis服务阻塞等问题。您可以参见[Redis连接问题排查与解决](../../../../intl.zh-CN/常见问题/Redis连接问题排查与解决.md#)以获得更多帮助信息。
-   如果telnet连接失败，但[使用ping命令检测ECS与Redis之间的连接](intl.zh-CN/技术运维问题/网络连接类/使用ping命令检测ECS与Redis之间的连接.md#)成功，可能是由于ECS存在异常行为（例如受恶意程序影响而攻击其它Redis的6379端口等）而被系统禁止了部分服务，此时建议您监控ECS的数据找到异常流量并加以处理，或者提交工单让阿里云工程师帮助解决。
-   如果telnet失败并提示`Name or service not known`，则可能是连接地址错误或者DNS解析出现异常，请确保连接地址正确无误后参见[此文档](intl.zh-CN/技术运维问题/网络连接类/解决因域名解析失败导致的连接问题.md#)尝试解决该类问题。
-   如果telnet失败并且使用ping命令检测ECS与Redis之间的连接也失败，请参见[Redis连接问题排查与解决](../../../../intl.zh-CN/常见问题/Redis连接问题排查与解决.md#)。

