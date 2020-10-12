# 使用redis-port跨账号迁移 {#task_zgn_tk1_hfb .task}

使用redis-port工具，您可以将一个阿里云账号下云数据库Redis版实例中的数据迁移到另一个账号下的云数据库Redis版实例中。

-   在目的Redis实例所在的VPC网络中创建了Linux系统的ECS实例。
-   在ECS实例中下载[redis-port](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/85829/cn_zh/1533199526614/redis-port%282%29?spm=a2c4g.11186623.2.10.1b5447ceE6Wtwt)。

    **说明：** 如果下载后发现文件名称中redis-port之后有其它字符，请修改文件名称，删除多余字符。

-   使用`chmod u+x redis-port`命令将redis-port修改为可执行文件。
-   在redis-port所在目录下执行`mkdir logs`。

1.  登录[云数据库Redis控制台](http://http、)。
2.  在实例列表中，单击源实例的实例ID，或其右侧操作列的**管理**。
3.  在左侧导航栏中，单击**备份与恢复**。
4.  在备份文件列表中，单击目标备份文件右侧操作列的**下载**。 

    **说明：** 如需即时创建备份文件，请单击备份与恢复页右侧的**创建备份**，之后再弹出的对话框中单击**确定**。

5.  在备份文件下载对话框中单击**复制内网下载地址**。
6.  在ECS实例中使用上一步复制的地址下载备份文件。 

    **说明：** 如果Redis实例为集群实例，会根据子节点数量生成多个备份文件，此时需要全部下载。

7.  使用如下命令将全部备份文件导入新数据库。 

    ``` {#codeblock_0o4_agc_aw7}
    ./redis-port restore -i 备份文件名 -t 目的数据库域名或IP:端口 --auth='目的数据库密码' --parallel=200
    ```


当返回`restore: rdb done`时，数据导入成功，迁移完成。

