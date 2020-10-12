# Limits {#concept_ggf_pv5_tdb .concept}

|Item|Description|
|----|-----------|
|List data type|The number of lists is not limited. The size of each element is 512 MB or less. We recommend that the number of elements in a list is less than 8,192. The value length is 1 MB or less.|
|Set data type|The number of sets is not limited. The size of each element is 512 MB or less. We recommend that the number of elements in a set is less than 8,192. The value length is 1 MB or less.|
|Sorted set data type|The number of sorted sets is not limited. The size of each element is 512 MB or less. We recommend that the number of elements in a sorted set is less than 8,192. The value length is 1 MB or less.|
|Hash data type|The number of fields is not limited. The size of each element in a hash table is 512 MB or less. We recommend that the number of elements in a hash table is less than 8,192. The value length is 1 MB or less.|
|Number of databases \(DBs\)|Each instance supports 256 databases. **Note:** 

-   The total size of data stored in all databases depends on the memory size of an instance.
-   The system automatically assigns memory to a single DB based on the usage. The upper limit of assigned memory is the instance memory. For example, if DB 0 occupies all the memory, other databases have no data.

 |
|Supported Redis commands|For more information, see [Redis commands](reseller.en-US/Quick Start/Redis commands.md#).|
|Monitoring and alerts| ApsaraDB for Redis does not provide capacity alerts. You have to configure this feature in CloudMonitor. For more information about the configuration, see [ApsaraDB for Redis](../../../../reseller.en-US/User Guide/Cloud service monitoring/ApsaraDB for Redis.md#).

 We recommend that you set alerts for the following metrics: instance faults, instance failover, connection usage, failed operations, capacity usage, write bandwidth usage, and read bandwidth usage.

 |
|Expired data deletion policies| -   Active expiration: the system periodically detects and deletes expired keys in the background.
-   Passive expiration: the system deletes expired keys when you access these keys.

 |
|Idle connection recycling mechanism|ApsaraDB for Redis does not actively recycle idle connections to ApsaraDB for Redis. You can manage the connections.|
|Data persistence policy|ApsaraDB for Redis uses the `AOF_FSYNC_EVERYSEC` policy, and runs the fsync command at a one-second interval.|

