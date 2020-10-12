# 使用 Redis 搭建视频直播间信息系统 {#concept_ubv_3kv_1gb .concept}

您可以使用云数据库 Redis 版方便快捷地构建大流量、低延迟的视频直播间消息服务。

## 背景信息 {#section_icv_pvv_1gb .section}

视频直播间作为直播系统对外的表现形式，是整个系统的核心之一。除了视频直播窗口外，直播间的在线用户、礼物、评论、点赞、排行榜等数据信息时效性高，互动性强，对系统时延有着非常高的要求，非常适合使用 Redis 缓存服务来处理。

本篇最佳实践将向您展示使用云数据库 Redis 版搭建视频直播间信息系统的示例。您将了解三类信息的构建方法：

-   实时排行类信息
-   计数类信息
-   时间线信息

## 实时排行类信息 {#section_fnm_dxv_1gb .section}

实时排行类信息包含直播间在线用户列表、各种礼物的排行榜、弹幕消息（类似于按消息维度排序的的消息排行榜）等，适合使用 Redis 中的有序集合（sorted set）结构进行存储。

Redis 集合使用空值散列表（hash table）实现，因此对集合的增删改查操作的时间复杂度都是O（1）。有序集合中的每个成员都关联一个分数（score），可以方便地实现排序等操作。下面以增加和返回弹幕消息为例对有序集合在直播间信息系统中的实际运用进行说明。

-   以 unix timestamp + 毫秒数为分值，记录 user55 的直播间增加的5条弹幕：

    ```
    redis> ZADD user55:_danmu 1523959031601166 message111111111111
    (integer) 1
    11.160.24.14:3003> ZADD user55:_danmu 1523959031601266 message222222222222
    (integer) 1
    11.160.24.14:3003> ZADD user55:_danmu 1523959088894232 message33333
    (integer) 1
    11.160.24.14:3003> ZADD user55:_danmu 1523959090390160 message444444
    (integer) 1
    11.160.24.14:3003> ZADD user55:_danmu 1523959092951218 message5555
    (integer) 1
    ```

-   返回最新的3条弹幕信息：

    ```
    redis> ZREVRANGEBYSCORE user55:_danmu +inf -inf LIMIT 0 3
    1) "message5555"
    2) "message444444"
    3) "message33333"
    ```

-   返回指定时间段内的3条弹幕信息：

    ```
    redis> ZREVRANGEBYSCORE user55:_danmu 1523959088894232 -inf LIMIT 0 3
    1) "message33333"
    2) "message222222222222"
    3) "message111111111111"
    ```


## 计数类信息 {#section_mxk_y2w_1gb .section}

计数类信息以用户相关数据为例，有未读消息数、关注数、粉丝数、经验值等等。这类消息适合以Redis中的散列（hash）结构进行存储。比如关注数可以用如下的方法处理：

```
redis> HSET user:55 follower 5
(integer) 1
redis> HINCRBY user:55 follower 1 //关注数+1
(integer) 6 
redis> HGETALL user:55
1) "follow"
2) "6"
```

## 时间线信息 {#section_b5f_jfw_1gb .section}

时间线信息是以时间为维度的信息列表，典型有主播动态、新帖等。这类信息是按照固定的时间顺序排列，可以使用列表（list）或者有序列表来存储，请参考以下示例。

```
redis> LPUSH user:55_recent_activitiy  '{datetime:201804112010,type:publish,title:开播啦,content:加油}'
(integer) 1
redis> LPUSH user:55_recent_activitiy '{datetime:201804131910,type:publish,title:请假,content:抱歉，今天有事鸽一天}'
(integer) 2
redis> LRANGE user:55_recent_activitiy 0 10
1) "{datetime:201804131910,type:publish,title:\xe8\xaf\xb7\xe5\x81\x87\",content:\xe6\x8a\xb1\xe6\xad\x89\xef\xbc\x8c\xe4\xbb\x8a\xe5\xa4\xa9\xe6\x9c\x89\xe4\xba\x8b\xe9\xb8\xbd\xe4\xb8\x80\xe5\xa4\xa9}"
2) "{datetime:201804112010,type:publish,title:\xe5\xbc\x80\xe6\x92\xad\xe5\x95\xa6,content:\xe5\x8a\xa0\xe6\xb2\xb9}"
```

## 相关资源 {#section_yww_yhw_1gb .section}

-   直播系统常见的热点 Key 问题的解决方法请参见[热点 Key 问题的发现与解决](cn.zh-CN/最佳实践/热点 Key 问题的发现与解决.md#)。
-   使用[Redis 内存分析方法](https://help.aliyun.com/knowledge_detail/50037.html)排除业务中潜在的风险点，找到业务性能瓶颈。
-   [云数据库 Redis 集群版](../../../../cn.zh-CN/产品简介/产品系列/Redis 集群版-双副本.md#)助您解决高并发问题。

