# 使用memtier-benchmark测试Redis集群版性能 {#concept_esn_2tx_dgb .concept}

memtier-benchmark是一款开源的NoSQL性能测试工具，您可以使用它测试云数据库Redis版的性能。本文为您介绍mentier-benchmark的安装和使用方法。

## memtier-benchmark简介 {#section_t1p_q5x_dgb .section}

memtier-benchmark可以根据您的需求生成多种结构的数据对数据库进行压力测试，帮助您了解目标数据库的性能极限。其部分功能特性如下：

-   支持Redis和Memcached数据库测试；
-   支持多线程、多客户端测试；
-   可设置测试中的读写比例（SET: GET Ratio）；
-   可自定义测试中键的结构；
-   支持设置随机过期时间。

## 前提条件 {#section_tvx_ywx_dgb .section}

您的Linux系统已安装以下库或工具：

-   Git；
-   libevent 2.0.10或更高版本；
-   libpcre 8.x；
-   autoconf；
-   automake；
-   GNU make；
-   GCC C++ compiler。

如果缺少以上组件，您可以按照以下步骤进行安装。

**说明：** 本文中的安装环境为阿里云ECS实例中的CentOS 7.2系统，其它系统的安装方法此处不做详细介绍。

1.  安装Git。

    ```
    # yum install git
    ```

2.  安装编译所需的工具。

    ```
    # yum install autoconf automake make gcc-c++
    ```

3.  安装部分需要的库。

    ```
    # yum install pcre-devel zlib-devel libmemcached-devel
    ```

4.  如您系统中的libevent库不符合要求，下载并安装libevent-2.0.21。

    ```
    # wget https://github.com/downloads/libevent/libevent/libevent-2.0.21-stable.tar.gz
    # tar xfz libevent-2.0.21-stable.tar.gz
    # pushd libevent-2.0.21-stable
    # ./configure
    # make
    # sudo make install
    # popd
    ```

5.  设置PKG\_CONFIG\_PATH使configure能够发现前置步骤安装的库。

    ```
    # export PKG_CONFIG_PATH=/usr/local/lib/pkgconfig:${PKG_CONFIG_PATH}
    ```


## 下载和安装 {#section_vnw_lzx_dgb .section}

1.  使用Git将memtier-benchmark的源文件克隆到本地目录。

    ```
    # git clone https://github.com/RedisLabs/memtier_benchmark.git
    # cd memtier_benchmark
    ```

2.  编译和安装memtier-benchmark。

    ```
    # autoreconf -ivf
    # ./configure
    # make
    # make install
    ```


## 测试方法 {#section_hsd_zby_dgb .section}

测试命令示例及常用选项说明如下。

```
./memtier_benchmark -s r-***************.redis.rds.aliyuncs.com -p 6379 -a <password> -c 20 -d 32 --threads=10 --ratio=1:1 --test-time=1800 --select-db=10
```

|选项|说明|
|--|--|
|-s|Redis数据库的连接地址|
|-a|Redis数据库的密码|
|-c|测试中模拟连接的客户端数量|
|-d|测试使用的对象数据的大小|
|--threads|测试中使用的线程数|
|--ratio|测试命令的读写比率（SET:GET Ratio）|
|--test-time|测试时长（单位：秒）|
|--select-db|测试使用的DB数量|

**说明：** 

-   更多memtier\_benchmark帮助信息请使用`memtier_benchmark -help`命令获取。
-   受到测试主机（本文为ECS中的Linux主机）的限制，单台主机上的测试结果可能无法体现Redis的极限性能，请参见[集群版-双副本的测试环境](../../../../../cn.zh-CN/性能白皮书/集群版-双副本/测试环境.md#)获取阿里云的测试建议。

测试结果参考值以及更多测试相关信息请参见[Redis性能白皮书](../../../../../cn.zh-CN/性能白皮书/集群版-双副本/测试结果.md#)。

