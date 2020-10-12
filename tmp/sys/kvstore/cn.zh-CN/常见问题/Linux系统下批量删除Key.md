# Linux系统下批量删除Key {#concept_yxz_hjk_2gb .concept}

您可以借助Linux的xargs和Redis的DEL指令来批量删除符合指定要求的Key，本文对此做了详细介绍。

xargs是一条Linux操作系统命令，它可以将参数列表分段传递给其他命令，以避免参数列表过长的问题。可单独使用，也可使用管道符、重定位符等与其他命令配合使用。

## 注意事项 {#section_th1_4rm_2gb .section}

-   使用KEYS命令可能会导致CPU使用率高，请在业务低峰期操作。
-   在大型数据库中使用KEYS命令会影响数据库的性能，建议在数据库的数据较少的情况下使用。

## 操作步骤 {#section_jdb_pjk_2gb .section}

1.  在Linux系统下安装Redis。

    ```
    yum install redis
    ```

2.  使用以下命令来删除当前某个数据库中符合指定要求的Key。如下方示例图中的“test\*”，表示包含了多个符合指定要求的Key，如test1、test2、test3等。

    ```
    redis-cli -h <host> -a <password> keys "<key>" | xargs redis-cli -h <host> -a <password> del
    ```

    -   <host\>：Redis的实例连接地址。
    -   <password\>：Redis实例密码。
    -   "<key\>"：某个数据库中符合指定要求的Key，如"test"。

        **说明：** 关于特定Key的匹配详解。

        w?rld：匹配world，warld和wxrld。

        w\*rld：匹配wrld和woooorld。

        w\[ae\]rld：匹配warld和werld，但是不匹配world。

        w\[^e\]rld：匹配world和warld，但是不匹配werld。

        w\[a-b\]rld：匹配warld和wbrld。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/80750/154822388534664_zh-CN.jpg)

3.  使用以下命令查看包含test的Key已经被删除。

    ```
    redis-cli -h <host> -a <password> keys "*"
    ```

    -   <host\>：Redis的实例连接地址。
    -   <password\>：Redis实例密码。
    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/80750/154822388534665_zh-CN.jpg)


