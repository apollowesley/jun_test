# 云数据库 Redis 缓存 PHP session 变量 {#concept_jr2_3rv_ydb .concept}

1.  安装 phpredis 扩展。

    ```
    wget https://github.com/nicolasff/phpredis/archive/master.zip
    unzip master.zip 
    cd phpredis-master
    /data/apps/php5.5.0/bin/phpize  
    注：此处phpize的路径，用户需要以自己环境的路径为准
    ./configure —with-php-config=/data/apps/php5.5.0/bin/php-config
    注：此处php-config的路径，用户需要以自己环境的路径为准
    make
    make install
    ```

2.  调整 php.ini，分别针对以下三个参数进行调整。

    ```
    extension = redis.so
    session.save_handler = redis
    session.save_path = "tcp://用户redis实例的连接地址?auth=redis对应的密码"
    ```

    如下：

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13948/4260_zh-CN.png)

3.  设置完成后重启web服务。
4.  编写一个 php 生成 session 的页面验证是否保存到 redis。

    test.php 内容如下：

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13948/4261_zh-CN.png)

    通过`php test.php`执行解析该 php 页面，观察结果如下，实现保存到 redis 的需求：

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13948/4262_zh-CN.png)


如果问题还未能解决，请联系[售后技术支持](https://selfservice.console.aliyun.com/ticket/createIndex.htm?spm=0.0.0.0.dt1NrZ)。

