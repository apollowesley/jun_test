# 使用redis-port恢复数据 {#concept_dxf_qgc_dfb .concept}

除了在控制台进行数据恢复，您还可以通过阿里云OpenAPI下载备份文件，然后使用redis-port为目的数据库进行数据恢复。

## 背景信息 {#section_tyk_sq5_cfb .section}

通过调用阿里云OpenAPI，您可以查询指定日期的备份文件或者指定备份集合信息的下载地址并下载指定文件。下载成功之后使用redis-port工具将数据自动导入目标实例。所需参数如下：

-   原实例ID
-   实例备份日期
-   目标实例地址
-   目标实例的端口
-   目标实例的密码

在开始前，请通过以下链接下载工具包：

[工具包下载](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/73964/cn_zh/1536832169445/redis-rdb-auto-restore.zip)

## 操作步骤 {#section_d3x_ft5_cfb .section}

1.  通过命令安装OpenAPI依赖：

    ```
    pip install aliyun-python-sdk-core #安装阿里云OpenAPI依赖包
    ```

2.  修改config.json，acesskeyID为RAM信息中的客户自己的AcesskeyID，acesskeySecret为RAM信息中客户自己的AcesskeySecret，regionid为实例的region编号。
3.  执行脚本，请参考如下示例：

    ```
    python rdb_restore.py -c r-m5ec7db53da93674 -d 2018-07-17 -i rm-davinx-003.redis.6d6e3611-8.newtest.rdstest.aliyun-inc.com -p 6379 -a Davinx123456
    ```

    命令参数详解如下：

    ```
    #python rdb_restore.py -h
    Usage: rdb_restore.py [options]
            Example : rdb_restore.py rdb_analysis.py -c r-m5ec7db53da93674 -d 2018-07-17 -t
            rm-davinx-003.redis.6d6e3611-8.newtest.rdstest.aliyun-inc.com -p 6379 -a Davinx123456
    
    Options:
      -h, --help            show this help message and exit
      -c INSTANCE_ID, --instance_id=INSTANCE_ID
                            instance_id is instance id #原实例id，必填
      -o FILE, --output=FILE
                            Output rdb file and default path /root/ #下载的rdb文件的本地路径，选填，默认为/root/
      -b BAKID, --bakid=BAKID
                            instance backup id #具体的实例备份id，选填，对于一天有多个备份的时候选择具体备份，如果需要传入多个备份的话请用逗号隔开，如：xxx,xxxx,xxxx
      -d DAY, --day=DAY     backup day #备份的日期
      -t TARGET, --target=TARGET  target instance ip #目标实例的地址
      -p PORT, --port=PORT  target instance port #目标实例的端口
      -a AUTH, --auth=AUTH  target instance auth password #目标实例的密码
    ```

    示例脚本执行的结果如下，当出现`restore: rdb done`时数据恢复完成。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/21349/153683552311901_zh-CN.png)

    **说明：** 如果一天之内有多个备份文件的话请务必加上`-b`参数，如需传入多个备份id请用逗号隔开，如：`xxx,xxxx,xxxx`。


