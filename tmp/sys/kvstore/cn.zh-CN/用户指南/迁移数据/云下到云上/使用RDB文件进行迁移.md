# 使用RDB文件进行迁移 {#concept_xnh_dk1_wdb .concept}

用户可以使用redis-port工具，通过RDB文件将自建Redis迁移到云数据库Redis版。

## 下载redis-port { .section}

[redis-port地址](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/85829/cn_zh/1533199526614/redis-port%282%29)

## 使用示例 { .section}

```
./redis-port  restore  --input=x/dump.rdb  --target=dst_host:dst_port   --auth=dst_password  [--filterkey="str1|str2|str3"] [--targetdb=DB] [--rewrite] [--bigkeysize=SIZE] [--logfile=REDISPORT.LOG]
```

**参数说明**

-   x/dump.rdb：自建redis的dump文件路径
-   dst\_host：云数据库redis域名
-   dst\_port：云数据库redis端口
-   dst\_password：云数据库redis密码
-   str1|str2|str3：过滤具有str1或str2或str3的key
-   DB：将要同步入云数据库redis的DB
-   rewrite：覆盖已经写入的key
-   bigkeysize=SIZE：当写入的value大于SIZE时，走大key写入模式

## 根据redis-port日志查看数据同步状态 { .section}

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3158/15471724372802_zh-CN.png)

当出现`restore: rdb done`时数据同步完成。

