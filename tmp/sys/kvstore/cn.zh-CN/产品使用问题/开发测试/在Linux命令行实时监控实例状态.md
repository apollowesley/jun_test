# 在Linux命令行实时监控实例状态 {#concept_htn_dkz_ngb .concept}

除了[在控制台查看监控信息](../../../../../cn.zh-CN/用户指南/性能监控.md#)，您还可以在Linux命令行通过redis-cli的--stat选项查看实例的实时运行状况。

## 前提条件 {#section_afq_jmz_ngb .section}

-   Redis实例与ECS实例可以互通；
-   ECS实例使用的系统为Linux；
-   ECS中已经安装了redis-cli。

## 操作步骤 {#section_urs_jnz_ngb .section}

1.  未使用redis-cli连接Redis实例时，在ECS命令行使用以下命令。

    ```
    redis-cli -h <host> -a <password> --stat
    ```

    ![](images/37983_zh-CN.png "运行示例")

    **说明：** 

    -   `host`为Redis实例的连接地址，`password`为其认证密码，如果开启了VPC免密认证，则可如示例图中一样不输入密码。
    -   成功执行后，命令行将每秒输出一次实例状态，其中包含键总数、已使用的内存、请求数等信息，同时会显示这些信息与前一条结果的对比。

