# Use ApsaraDB for Redis to build a broadcasting channel information system {#concept_ubv_3kv_1gb .concept}

You can use ApsaraDB for Redis to build a broadcasting channel information system that has a low latency and can handle high traffic volumes.

## Background information {#section_icv_pvv_1gb .section}

The broadcasting channel is one of the key features of the live broadcasting system. Except for the broadcasting window, online users, online gifts, comments, likes, rankings, and other data generated in the live broadcast is time-limited, highly interactive, and delay-sensitive. Redis cache service is a suitable solution to handle such data.

The best practice in this topic introduces how to use ApsaraDB for Redis to build a broadcasting channel information system. This topic describes the construction methods for three information types:

-   Real-time ranking information
-   Counting information
-   Timeline information

## Real-time ranking information {#section_fnm_dxv_1gb .section}

The real-time ranking information includes an online user list, a list of online gifts, and live comments. Live comments are similar to a message ranking list that is sorted based on the message dimension. The sorted set structure in Redis is suitable to handle the real-time ranking information.

The Redis set is stored in a hash table. The time complexity of the insert, delete, edit, and search operations is O\(1\). Each member in the set is associated with a score to facilitate sorting and other operations. The following example describes the added and returned live comments to explain how the sorted set works to build a broadcasting channel information system.

-   Uses "unix timestamp + milliseconds" as the score to record the last five live comments in the user55 broadcasting channel.

    ``` {#codeblock_cmf_5ht_k4f}
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

-   Returns the last three live comments:

    ``` {#codeblock_bex_q4p_hw0}
    redis> ZREVRANGEBYSCORE user55:_danmu +inf -inf LIMIT 0 3
    1) "message5555"
    2) "message444444"
    3) "message33333"
    ```

-   Returns three live comments within the specified time period:

    ``` {#codeblock_zia_snq_6on}
    redis> ZREVRANGEBYSCORE user55:_danmu 1523959088894232 -inf LIMIT 0 3
    1) "message33333"
    2) "message222222222222"
    3) "message111111111111"
    ```


## Counting information {#section_mxk_y2w_1gb .section}

In case of the user-related data, the counting information includes the number of unread messages, followers, and fans, and the experience value. The hash structure in Redis is suitable to process this type of data. For example, the number of followers can be processed as follows:

``` {#codeblock_pdi_toz_ly8}
redis> HSET user:55 follower 5
(integer) 1
redis> HINCRBY user:55 follower 1 //The number of followers +1
(integer) 6 
redis> HGETALL user:55
1) "follow"
2) "6"
```

## Timeline information {#section_b5f_jfw_1gb .section}

The timeline information is a list of information sorted in time order. For example, the broadcaster moments and new posts. This type of information is arranged in a fixed chronological order and can be stored using a Redis list or an ordered list. The example is as follows:

``` {#codeblock_84v_hgg_unf}
redis> LPUSH user:55_recent_activitiy  '{datetime:201804112010,type:publish,title:The show starts, content:Come on}'
(integer) 1
redis> LPUSH user:55_recent_activitiy '{datetime:201804131910,type:publish,title: Ask for a leave, content: Sorry, I have plans today.}'
(integer) 2 
redis> LRANGE user:55_recent_activitiy 0 10
1) "{datetime:201804131910,type:publish,title:\xe8\xaf\xb7\xe5\x81\x87\",content:\xe6\x8a\xb1\xe6\xad\x89\xef\xbc\x8c\xe4\xbb\x8a\xe5\xa4\xa9\xe6\x9c\x89\xe4\xba\x8b\xe9\xb8\xbd\xe4\xb8\x80\xe5\xa4\xa9}"
2) "{datetime:201804112010,type:publish,title:\xe5\xbc\x80\xe6\x92\xad\xe5\x95\xa6,content:\xe5\x8a\xa0\xe6\xb2\xb9}"
```

## Related resources {#section_yww_yhw_1gb .section}

-   For more information about how to eliminate potential risks and locate business performance bottlenecks, see [Redis memory usage analysis](https://partners-intl.aliyun.com/help/doc-detail/50037.htm).
-   For more information about how to handle high concurrency, see [ApsaraDB for Redis cluster edition](../../../../reseller.en-US/Product Introduction/Product  series/Dual-replica cluster edition.md#).

