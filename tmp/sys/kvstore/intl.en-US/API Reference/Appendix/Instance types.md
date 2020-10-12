# Instance types {#concept_gph_q34_tdb .concept}

This topic describes the types and parameters of all ApsaraDB for Redis editions and the method of testing queries per second \(QPS\). To call the APIs related to a type of instance, use the InstanceClass parameter for the corresponding type described in this topic.

**Note:** The maximum internal network bandwidth in below tables includes the maximum upstream bandwidth and the maximum downstream bandwidth. If network resources are sufficient, the bandwidth is unlimited for ApsaraDB for Redis instances. However, if network resources are insufficient, the maximum bandwidth takes effect for the instances.

## Standard dual-replica edition {#section_hh2_5p4_tdb .section}

**Note:** A 256 MB master-replica instance uses the subscription billing method only.

|Type|InstanceClass \(used in API operations\)|Maximum concorrent connections|Maximum internal network bandwidth \(MB\)|CPU processing capacity|QPS reference value|Description|
|----|----------------------------------------|------------------------------|-----------------------------------------|-----------------------|-------------------|-----------|
|256 MB master-replica|redis.master.micro.default|10,000|10|Single-core|80,000|Master-replica instance|
|1 GB master-replica|redis.master.small.default|10,000|10|Single-core|80,000|Master-replica instance|
|2 GB master-replica|redis.master.mid.default|10,000|16|Single-core|80,000|Master-replica instance|
|4 GB master-replica|redis.master.stand.default|10,000|24|Single-core|80,000|Master-replica instance|
|8 GB master-replica|redis.master.large.default|10,000|24|Single-core|80,000|Master-replica instance|
|16 GB master-replica|redis.master. 2xlarge.default|10,000|32|Single-core|80,000|Master-replica instance|
|32 GB master-replica|redis.master. 4xlarge.default|10,000|32|Single-core|80,000|Master-replica instance|
|64 GB master-replica|redis.master. 8xlarge.default|10,000|48|Single-core|80,000|Master-replica instance|

## Standard zone-disaster recovery edition {#section_ndd_gyg_vgb .section}

**Note:** To create a zone-disaster recovery instance, you have to select the region and zone that support zone-disaster recovery, such as **China \(Hangzhou\) and Hangzhou Zones B and F.**

|Type|InstanceClass \(used in API operations\)|Maximum concorrent connections|Maximum internal network bandwidth \(MB\)|CPU processing capacity|QPS reference value|Description|
|----|----------------------------------------|------------------------------|-----------------------------------------|-----------------------|-------------------|-----------|
|Zone-disaster recovery 1 GB|redis.logic.sharding.drredissdb1g. 1db. 0rodb. 4proxy.default|40,000|10|Single-core|80,000|Master-replica zone-disaster recovery instance|
|Zone-disaster recovery 2 GB|redis.logic.sharding.drredissdb2g. 1db. 0rodb. 4proxy.default|40,000|16|Single-core|80,000|Master-replica zone-disaster recovery instance|
|Zone-disaster recovery 4 GB|redis.logic.sharding.drredissdb4g. 1db. 0rodb. 4proxy.default|40,000|24|Single-core|80,000|Master-replica zone-disaster recovery instance|
|Zone-disaster recovery 8 GB|redis.logic.sharding.drredissdb8g. 1db. 0rodb. 4proxy.default|40,000|24|Single-core|80,000|Master-replica zone-disaster recovery instance|
|Zone-disaster recovery 16 GB|redis.logic.sharding.drredissdb16g. 1db. 0rodb. 4proxy.default|40,000|32|Single-core|80,000|Master-replica zone-disaster recovery instance|
|Zone-disaster recovery 32 GB|redis.logic.sharding.drredissdb32g. 1db. 0rodb. 4proxy.default|40,000|32|Single-core|80,000|Master-replica zone-disaster recovery instance|
|Zone-disaster recovery 64 GB|redis.logic.sharding.drredissdb64g. 1db. 0rodb. 4proxy.default|40,000|48|Single-core|80,000|Master-replica zone-disaster recovery instance|

## Zone-disaster recovery cluster edition {#section_zqv_nyg_vgb .section}

**Note:** To create a zone-disaster recovery instance, you have to select the region and zone that support zone-disaster recovery, such as **China \(Hangzhou\) and Hangzhou Zones B and F.**

|Type|InstanceClass \(used in API operations\)|Number of nodes|Maximum concorrent connections|Maximum internal network bandwidth \(MB\)|CPU processing capacity|QPS reference value|Description|
|----|----------------------------------------|---------------|------------------------------|-----------------------------------------|-----------------------|-------------------|-----------|
|Zone-disaster recovery 16 GB cluster|redis.logic.sharding.drredismdb16g. 8db. 0rodb. 8proxy.default|8|80,000|768|8-core|800,000|Zone-disaster recovery cluster instance|
|Zone-disaster recovery 32 GB cluster|redis.logic.sharding.drredismdb32g. 8db. 0rodb. 8proxy.default|8|80,000|768|8-core|800,000|Zone-disaster recovery cluster instance|
|Zone-disaster recovery 64 GB cluster|redis.logic.sharding.drredismdb64g. 8db. 0rodb. 8proxy.default|8|80,000|768|8-core|800,000|Zone-disaster recovery cluster instance|
|Zone-disaster recovery 128 GB cluster|redis.logic.sharding.drredismdb128g. 16db. 0rodb. 16proxy.default|16|160,000|1,536|16-core|1,600,000|Zone-disaster recovery cluster instance|
|Zone-disaster recovery 256 GB cluster|redis.logic.sharding.drredismdb256g. 16db. 0rodb. 16proxy.default|16|160,000|1,536|16-core|1,600,000|Zone-disaster recovery cluster instance|
|Zone-disaster recovery 512 GB cluster|redis.logic.sharding.drredismdb512g. 32db. 0rodb. 32proxy.default|32|320,000|3,072|32-core|3,200,000|Zone-disaster recovery cluster instance|
|Zone-disaster recovery 1 TB cluster|redis.logic.sharding.drredismdb1024g. 64db. 0rodb. 64proxy.default|64|640,000|6,144|64-core|6,400,000|Zone-disaster recovery cluster instance|
|Zone-disaster recovery 2 TB cluster|redis.logic.sharding.drredismdb2048g. 128db. 0rodb. 128proxy.default|128|1,280,000|12,288|128-core|12,800,000|Zone-disaster recovery cluster instance|
|Zone-disaster recovery 4 TB cluster|redis.logic.sharding.drredismdb4096g. 256db. 0rodb. 256proxy.default|256|2,560,000|24,576|256-core|25,600,000|Zone-disaster recovery cluster instance|

## QPS performance reference {#section_bhd_cp4_tdb .section}

**QPS performance**

|Type|Maximum concorrent connections|Maximum internal network bandwidth \(MB\)|CPU processing capacity|QPS reference value|
|----|------------------------------|-----------------------------------------|-----------------------|-------------------|
|8 GB|10,000|24|Single-core|80,000|

**Note:** 

QPS reference values of non-cluster instances range from 80,000 to 100,000. QPS reference values of cluster instances are the number of nodes multiplied by the value from 80,000 to 100,000.

## QPS testing method {#section_hty_fq4_tdb .section}

 ![](images/6123_en-US.png "Network topology")

**ECS instance type**

|Operating system|CPU|Memory|Region|Quantity|
|----------------|---|------|------|--------|
|Ubuntu 14.04 64-bit|1|2,048 MB|China \(Shenzhen\)|3|

**Procedure**

1.  Download the source code package for redis-2.8.19 to three ECS instances.

    ```
     $ wget http://download.redis.io/releases/redis-2.8.19.tar.gz
     $ tar xzf redis-2.8.19.tar.gz
     $ cd redis-2.8.19
     $ make
     $ make install
    ```

2.  Run the following command on these ECS instances at the same time.

    ```
     redis-benchmark -h ***********.m.cnsza.kvstore.aliyuncs.com -p 6379 -a password -t set -c 50 -d 128 -n 25000000 -r 5000000
    ```

3.  Summarize the testing data on these ECS instances. The total QPS is the sum of the QPS on each ECS instance.

