# 使用redis-cli查找大Key {#concept_u5s_kxc_3gb .concept}

您可以使用redis-cli的--bigkeys选项在命令行手动查找Redis中的大Key。

## 背景信息 {#section_fns_mxc_3gb .section}

当一个简单Key的Value过大或List、Hash等类型的数据中存储了大量的元素时，读取、删除这些数据的操作可能会花费过多的时间，阻塞单线程的Redis服务。此时您需要对内存结构进行优化，找出大Key并进行调整。查找大Key的方法多种多样，您可以根据业务需求选择最适合的方案。

## 前提条件 {#section_ncb_mxc_3gb .section}

-   拥有与Redis实例互通的ECS实例；
-   ECS中已经安装了原生Redis；

    **说明：** 目的为使用其自带的工具redis-cli。


## 实施步骤 {#section_atw_4xc_3gb .section}

1.  在ECS中使用以下命令：

    ``` {#codeblock_0xz_99i_cpu}
    redis-cli -h r-***************.redis.rds.aliyuncs.com -a <password> --bigkeys
    ```

    |名称|说明|
    |--|--|
    |-h|指定Redis的连接地址。|
    |-a|指定Redis的认证密码。|
    |--hotkeys|用来查询热点Key。|


## 执行结果 {#section_yss_wxc_3gb .section}

执行命令后得到的结果示例如下，在Summary中可以看到大Key的详情。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/85399/156151225235754_zh-CN.png)

## 更多信息 {#section_nmp_byc_3gb .section}

您还可以使用以下方法查找大Key：

-    [在Python脚本中使用SCAN命令查找大Key](../../../../cn.zh-CN/常见问题/如何搜索过大的key.md#)；
-    [使用rdb文件分析内存结构查找大Key](https://help.aliyun.com/knowledge_detail/50037.html)。

