# 将SSDB数据库迁移到云数据库Redis版 {#concept_nnm_mr1_mgb .concept}

您可以使用ssdb-port将SSDB数据库中的数据迁移到云数据库Redis版实例中。

## 背景信息 {#section_yjf_sr1_mgb .section}

**迁移原理**

ssdb-port作为从节点（replica）运行，与作为源SSDB数据库的SSDB主节点（master）同步数据，并将获取到的数据解析、转换为Redis支持的格式，发送到配置文件中指定的Redis实例，迁移过程如下图所示。

![使用ssdb-port迁移SSDB数据库到Redis的原理图](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/105448/155125974037520_zh-CN.png)

全量同步完成后，在连接关闭前，SSDB中新增的数据也会增量同步到Redis实例中。

**说明：** 

-   在SSDB中执行ssdb-port暂不支持的命令修改的数据将无法同步到Redis中。支持同步的SSDB命令请见下文。
-   如您需要支持更多命令，请[在此处](https://connect.aliyun.com/suggestion/add)提出建议。

**支持同步的命令**

-   set
-   setx
-   setnx
-   expire
-   del
-   get
-   incr
-   qpop\_front
-   qpush\_front
-   qclear
-   qtrim\_front
-   qtrim\_back
-   zset
-   zdel
-   zincr
-   multi\_zdel
-   multi\_zset
-   hset
-   hdel
-   hclear
-   multi\_hset
-   multi\_hdel
-   hincr

## 前提条件 {#section_fqg_vw1_mgb .section}

-   在目的Redis实例所在的VPC网络中创建了Linux系统的ECS实例，且可以与目的Redis连接。
-   源SSDB数据库版本为1.9.2以上。

## 操作步骤 {#section_c5w_hy1_mgb .section}

1.  在ECS实例中使用如下命令下载ssdb-port并解压。

    ```
    # wget http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/94155/cn_zh/1547627852086/ssdb-port.tar.gz
    # tar -xvf ssdb-port.tar.gz
    # cd ssdb-port
    ```

2.  使用如下命令，按照示例修改ssdb-port的配置文件。

    ```
    vi ssdb_port.conf
    ```

    ssdb\_port.conf文件示例如下，请按照注释内容修改源SSDB数据库和目的Redis实例的连接信息。

    ```
    # ssdb-server config for replica
    # MUST indent by TAB!
    
    # relative to path of this file, directory must exists
    work_dir = ./var_ssdb_port
    pidfile = ./var_ssdb_port/ssdb.pid
    
    # ssdb-port的连接信息，无需修改
    server:
        ip: 127.0.0.1
        port: 8890
        #readonly: yes
    
    replication:
        binlog: yes
            capacity: 100000000
        # Limit sync speed to *MB/s, -1: no limit
        sync_speed: -1
        slaveof:
            # to identify a master even if it moved(ip, port changed)
            # if set to empty or not defined, ip:port will be used.
            id: svc_1
            # sync|mirror, default is sync
            type: sync
            host: localhost # SSDB Master（源SSDB数据库）的连接地址
            port: 8888 # SSDB Master（源SSDB数据库）的端口
            #auth: password
            redis_host: localhost # 目的Redis实例的连接地址
            redis_port: 6379  # 目的Redis实例的端口
            redis_auth: password  # 目的Redis实例的认证密码
    
    logger:
        level: debug
        output: log_ssdb_port.txt
        rotate:
            size: 1000000000
    
    leveldb:
        # in MB
        cache_size: 500
        # in MB
        write_buffer_size: 64
        # in MB/s
        compaction_speed: 1000
        # yes|no
        compression: yes
    ```

3.  使用`./ssdb-port-2.17 ssdb_port.conf`命令开始同步。
4.  连接Redis实例检查数据同步是否已完成。

    **说明：** 您可以使用redis-cli或DMS连接到Redis进行验证数据。连接方式请参见[Redis快速入门](../../../../../cn.zh-CN/快速入门/连接实例/redis-cli连接.md#)。


**说明：** 使用hset/hget命令时，如果对象key为中文则该操作无法同步。使用其它支持的命令无此限制。

