# Glossary {#concept_xkx_pmm_tdb .concept}

|Terms|Description|
|-----|-----------|
|Redis|ApsaraDB for Redis is a high-performance Key-Value storage system \(cache and store \) released in compliance with the BSD open-source protocol.|
|Instance ID|An instance corresponds to a user space, and serves as the basic unit of using ApsaraDB for Redis.  ApsaraDB for Redis  limits connection quantities, bandwidth, CPU specifications, and other parameters based on the capacity specifications of individual instances.  On the console, you can view the list of IDs of the instances you have purchased.|
|Master-Slave dual-node instance|This is an ApsaraDB for Redis instance that adopts a master-slave architecture.   Master-slave dual-node instances provide limited capacity and performance.|
|High-performance cluster instance|This is an ApsaraDB for Redis instance that adopts a scalable cluster architecture.  Cluster instances have better scalability and performance, but they are also limited in functionality.|
|Connection address|This is the host address used to connect to ApsaraDB for Redis. It is displayed as a domain name,  and can be found at**Instance information** \> **Connection information**The query was found in.|
|Connect Password|Password used to connect to  ApsaraDB for Redis. The password format is Instance ID:custom password. For example, if you set the password as 1234 when you make the purchase and the allocated instance ID For Newfoundland, then the password is : 1234.|
| Expelling Policy|This is consistent with the Redis eviction policy.  For more information,  see [http://redis.io/topics/lru-cache](http://redis.io/topics/lru-cache)|
|DB|This is the abbreviation of database in Redis.  The ApsaraDB for Redis supports 256 databases numbered DB 0 to DB 255. By default, data is written to DB 0. .|

