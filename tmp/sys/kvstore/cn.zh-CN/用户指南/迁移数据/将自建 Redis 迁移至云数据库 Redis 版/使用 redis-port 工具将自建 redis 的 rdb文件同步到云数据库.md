# 使用 redis-port 工具将自建 redis 的 rdb文件同步到云数据库 {#concept_xnh_dk1_wdb .concept}

## 下载 redis-port { .section}

[redis-port地址](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/66006/cn_zh/1531121747155/redis-port%282%29)

## 使用示例 { .section}

```
./redis-port  restore  --input=x/dump.rdb  --target=dst_host:dst_port   --auth=dst_password  [--filterkey="str1|str2|str3"] [--targetdb=DB] [--rewrite] [--bigkeysize=SIZE] [--logfile=REDISPORT.LOG]
```

**参数说明**

-   x/dump.rdb：自建 redis 的 dump 文件路径
-   dst\_host：云数据库 redis 域名
-   dst\_port：云数据库 redis 端口
-   dst\_password：云数据库 redis 密码
-   str1|str2|str3：过滤具有 str1 或 str2 或 str3 的 key
-   DB：将要同步入云数据库 redis 的 DB
-   rewrite：覆盖已经写入的 key
-   bigkeysize=SIZE：当写入的 value 大于 SIZE 时，走大 key 写入模式

## 根据 redis-port 日志查看数据同步状态 { .section}

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3158/2802_zh-CN.png)

当出现`restore: rdb done`时数据同步完成。

