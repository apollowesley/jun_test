# 导出命令执行结果到CSV文件 {#task_etx_s4r_ngb .task}

当您需要将Redis中的部分数据导出到外部程序中使用时，可以首先借助redis-cli将命令的执行结果输出到.csv文件中。

-   Redis实例与ECS实例可以互通；
-   ECS实例使用的系统为Linux；
-   ECS中已经安装了redis-cli。

1.  在ECS中按照如下模板使用命令： 

    ```
    redis-cli -h <host> -a <password> --csv <Redis command> > <filename>.csv
    ```

     ![](images/37860_zh-CN.png "执行示例") 

    **说明：** 

    -   `host`为Redis实例的连接地址，`password`为其认证密码，如果开启了VPC免密认证，则可如示例图中一样不输入密码。
    -   `Redis command`是需要执行的Redis命令（只支持单条命令），例如示例中的`HGETALL customer:4955`。
    -   示例中的Redis数据为测试用的虚拟数据。
2.  使用`cat ./<filename>.csv`命令查看.csv文件的内容，校验数据完整性。 

