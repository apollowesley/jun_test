# Instance configurations table {#reference_stj_m5x_wdb .reference}

|Parameter|Meaning|Parameter value|
|---------|-------|---------------|
|maxmemory-policy|The eviction policy when the memory exceeds the threshold value. ApsaraDB for Redis supports six data eviction policies.| -   VolatileLRU: Original data is evicted based on the LRU algorithm, but only data with an expiration time is evicted.
-   VolatileTTL: Only data with an expiration time is evicted based on the TTL values in ascending order.
-   AllKeysLRU: Original data is evicted based on the LRU algorithm.
-   VolatileRandom: Original data is evicted randomly, but only data with an expiration time is evicted.
-   AllKeysRandom: Original data is evicted randomly.
-   NoEviction: No data is evicted, and an error message is returned when new data is written.

 |
|hash-max-ziplist-entries|A hash object uses ziplist encoding only if the hash object meets both of the following conditions:-   The number of key-value pairs stored in the hash object is smaller than or equal to the value of list-max-ziplist-entries.
-   The string lengths of keys and values in all key-value pairs stored in the hash object are smaller than or equal to the value of list-max-ziplist-value.

|Default value: 512|
|hash-max-ziplist-value|A hash object uses ziplist encoding only if the hash object meets both of the following conditions:-   The number of key-value pairs stored in the hash object is smaller than or equal to the value of list-max-ziplist-entries.
-   The string lengths of keys and values in all key-value pairs stored in the hash object are smaller than or equal to the value of list-max-ziplist-value.

|Default value: 64|
|list-max-ziplist-entries|A list object uses ziplist encoding only if the list object meets both of the following conditions:-   The number of key-value pairs stored in the list object is smaller than or equal to the value of list-max-ziplist-entries.
-   The string lengths of keys and values in all key-value pairs stored in the list object are smaller than or equal to the value of list-max-ziplist-value.

|Default value: 512|
|list-max-ziplist-value|A list object uses ziplist encoding only if the list object meets both of the following conditions:-   The number of key-value pairs stored in the list object is smaller than or equal to the value of list-max-ziplist-entries.
-   The string lengths of keys and values in all key-value pairs stored in the list object are smaller than or equal to the value of list-max-ziplist-value.

|Default value: 64|
|set-max-intset-entries|When a set object meets the conditions that the number of entries is smaller than or equal to the value of set-max-intset-entries and all entries are decimal integers, the set object uses intset encoding.|Default value: 512|
|zset-max-ziplist-entries|A zset object uses ziplist encoding only if the zset object meets both of the following conditions:-   The number of key-value pairs stored in the zset object is smaller than or equal to the value of zset-max-ziplist-entries.
-   The string lengths of keys and values in all key-value pairs stored in the zset object are smaller than or equal to the value of zset-max-ziplist-value.

|Default value: 128|
|zset-max-ziplist-value|A zset object uses ziplist encoding only if the zset object meets both of the following conditions:-   The number of key-value pairs stored in the zset object is smaller than or equal to the value of zset-max-ziplist-entries.
-   The string lengths of keys and values in all key-value pairs stored in the zset object are smaller than or equal to the value of zset-max-ziplist-value.

|Default value: 64|
|notify-keyspace-events|The keyspace notifications allow clients to subscribe to channels or modes to receive events modifying Redis datasets in some way.| -   - K: The keyspace notifications. All notifications are prefixed with `\__keyspace\@_\_`.
-   - E: The keyevent notifications. All notifications are prefixed with `__keyevent@__`.
-   - g: The notifications about general commands that are non-type specific, such as DEL, EXPIRE, and RENAME.
-   - $: The string command notifications.
-   - l: The list command notifications.
-   - s: The set command notifications.
-   - h: The hash command notifications.
-   - z: The sorted set command notifications.
-   - x: The expired events. An expired event is sent when an expired key is deleted.
-   - e: The evicted events. An evicted event is sent when a key is evicted for maxmemory.
-   - A: The alias for g$lshzxe.

 |

