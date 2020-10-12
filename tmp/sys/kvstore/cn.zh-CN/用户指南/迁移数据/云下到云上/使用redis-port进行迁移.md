# 使用redis-port进行迁移 {#concept_u3d_fj1_wdb .concept}

用户可以使用redis-port将自建Redis迁移至云数据库Redis版。

## 前提条件 {#section_i54_wj5_1gb .section}

-   在目的Redis实例所在的VPC网络中创建了Linux系统的ECS实例。
-   在ECS实例中下载[redis-port](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/85829/cn_zh/1533199526614/redis-port%282%29?spm=a2c4g.11186623.2.10.1b5447ceE6Wtwt)。
-   使用chmod u+x redis-port命令将redis-port修改为可执行文件。

## 使用示例 { .section}

```
./redis-port sync --from=src_host:src_port --password=src_password --target=dst_host:dst_port --auth=dst_password  [--filterkey="str1|str2|str3"] [--targetdb=DB] [--rewrite] [--bigkeysize=SIZE] [--logfile=REDISPORT.LOG]
```

**参数说明**

-   src\_host：自建Redis的域名（或者IP）。
-   src\_port：自建Redis的端口。
-   src\_password：自建Redis密码。
-   dst\_host：云数据库Redis版的域名。
-   dst\_port：云数据库Redis版的端口。
-   dst\_password：云数据库Redis版的密码
-   str1|str2|str3：过滤具有str1或str2或str3的key。
-   DB：将同步入阿里云Redis的DB。
-   rewrite：覆盖已经写入的key。

    **说明：** 迁移前请确认目的端与源端没有重复的key，否则迁移中会出现`target key name is busy`错误。您可以在迁移命令中设置rewrite选项以覆盖目的端中的重复key。请确保这些重复key可以被覆盖或者您已经进行了备份。

-   bigkeysize：当写入的value大于SIZE时，启动大key写入模式。

## 根据redis-port日志查看数据同步状态 { .section}

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3157/15531341242803_zh-CN.png)

当出现`sync rdb done`时全量同步完成，进入增量同步的模式。

全量同步完成后，如果持续有数据写入，则增量同步也将持续进行。上图`sync rdb done`之后的日志中，包含`+forward=1`的条目的`+nbytes`值如果大于14，则该条目为有数据的增量同步日志，用户可以此为依据判断增量同步情况，并根据实际需求选择将数据切换到云数据库 Redis 版的时间。

**说明：** 当全量同步完成后，同步源会给redis-port发送ping命令，此时会产生`+forward=1`且`+nbytes=14`的日志记录。该记录并非增量同步日志。

