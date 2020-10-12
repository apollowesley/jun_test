# Specifications and performance {#concept_gph_q34_tdb .concept}

This topic describes the types and parameters of all ApsaraDB for Redis editions and the method of testing queries per second \(QPS\). When calling APIs related to instance types, you may need to queryInstanceClassParameters.

**Note:** 

-   The value of intranet bandwidth applies to both upstream and downstream. If network resources are sufficient, the bandwidth is unlimited for ApsaraDB for Redis instances. However, if network resources are insufficient, the intranet bandwidth takes effect for the instances.
-   The intranet bandwidth of cluster instances = the number of nodes × the intranet bandwidth of a single node \(96 MB/s \). The maximum intranet bandwidth of an instance is 2048 MB/s.
-   The maximum concorrent connections of cluster instances = the number of nodes × the maximum concorrent connections per node \(10000 \). The maximum number of maximum concorrent connections is 500000.

## Standard dual-replica edition {#section_hh2_5p4_tdb .section}

|Size \(GB\)|InstanceClass \(for calling APIs\)|New connections per second|Maximum concorrent connections|Intranet bandwidth\(MB/s\)|CPU processing capability|QPS reference value|Description|
|-----------|----------------------------------|--------------------------|------------------------------|--------------------------|-------------------------|-------------------|-----------|
|1 GB master/replica|redis.master.small.default|10,000|10,000|10|Single-core|80,000|Master-replica instance|
|2 GB master/replica|redis.master.mid.default|10,000|10,000|16|Single-core|80,000|Master-replica instance|
|4 GB master/replica|redis.master.stand.default|10,000|10,000|24|Single-core|80,000|Master-replica instance|
|8 GB master/replica|redis.master.large.default|10,000|10,000|24|Single-core|80,000|Master-replica instance|
|16 GB master/replica|redis.master.2xlarge.default|10,000|10,000|32|Single-core|80,000|Master-replica instance|
|16 GB master/replica|redis.master.4xlarge.default|10,000|10,000|32|Single-core|80,000|Master-replica instance|
|64 GB master/replica|redis.master.8xlarge.default|10,000|10,000|48|Single core|80,000|Master-replica instance|

## Standard zone-disaster recovery edition {#section_ndd_gyg_vgb .section}

**Note:** To create a zone-disaster recovery instance, you have to select the region and zone that support zone-disaster recovery, such as**China \(Hangzhou\) Zone \(G + H \)**.

|Type|InstanceClass \(for calling APIs\)|New connections per second|Maximum concorrent connections|Intranet bandwidth\(MB/s\)|CPU processing capability|QPS reference value|Description|
|----|----------------------------------|--------------------------|------------------------------|--------------------------|-------------------------|-------------------|-----------|
|1 GB zone-disaster recovery|redis.logic.sharding.drredissdb1g.1db.0rodb.4proxy.default|10,000|10,000|10|Single-core|80,000|Master/replica zone-disaster recovery instances|
|2 GB zone-disaster recovery|redis.logic.sharding.drredissdb2g.1db.0rodb.4proxy.default|10,000|10,000|16|Single-core|80,000|Master/replica zone-disaster recovery instances|
|4 GB zone-disaster recovery|redis.logic.sharding.drredissdb4g.1db.0rodb.4proxy.default|10,000|10,000|24|Single-core|80,000|Master/replica zone-disaster recovery instances|
|8 GB zone-disaster recovery|redis.logic.sharding.drredissdb8g.1db.0rodb.4proxy.default|10,000|10,000|24|Single-core|80,000|Master/replica zone-disaster recovery instances|
|16 GB zone-disaster recovery|redis.logic.sharding.drredissdb16g.1db.0rodb.4proxy.default|10,000|10.000|32|Single-core|80,000|Master/replica zone-disaster recovery instances|
|32 GB zone-disaster recovery|redis.logic.sharding.drredissdb32g.1db.0rodb.4proxy.default|10,000|10,000|32|Single-core|80,000|Master/replica zone-disaster recovery instances|
|64 GB zone-disaster recovery|redis.logic.sharding.drredissdb64g.1db.0rodb.4proxy.default|10,000|10,000|48|Single-core|80,000|Master/replica zone-disaster recovery instances|

## Dual-replica cluster edition {#section_ckk_htg_vgb .section}

|Type|InstanceClass \(for calling APIs\)|Number of nodes|New connections per second|Maximum concorrent connections|Intranet bandwidth\(MB/s\)|CPU processing capability|QPS reference value|Description|
|----|----------------------------------|---------------|--------------------------|------------------------------|--------------------------|-------------------------|-------------------|-----------|
|16 GB cluster|redis.logic.sharding.2g.8db.0rodb.8proxy.default|8|50,000|80,000|768|8-core|640,000|High-performance cluster instance|
|32 GB cluster|redis.logic.sharding.4g.8db.0rodb.8proxy.default|8|50,000|80,000|768|8-core|640,000|High-performance cluster instance|
|64 GB cluster|redis.logic.sharding.8g.8db.0rodb.8proxy.default|8|50,000|80,000|768|8-core|640,000|High-performance cluster instance|
|128 Gb cluster|redis.logic.sharding.8g.16db.0rodb.16proxy.default|16|50,000|160,000|1,536|16-core|1,280,000|High-performance cluster instance|
|256 GB cluster|redis.logic.sharding.16g.16db.0rodb.16proxy.default|16|50,000|160,000|1,536|16-core|1,280,000|High-performance cluster instance|
|512 GB cluster|redis.logic.sharding.16g.32db.0rodb.32proxy.default|32|50,000|320,000|2,048|32-core|2,560,000|High-performance cluster instance|

**Note:** The number of nodes in the dual-replica cluster instance is the number of master nodes.

## Zone-disaster recovery cluster edition {#section_zqv_nyg_vgb .section}

**Note:** To create a zone-disaster recovery instance, you have to select the region and zone that support zone-disaster recovery, such as**China \(Hangzhou\) Zone \(G + H \)**.

|Type|InstanceClass \(for calling APIs\)|Number of nodes|New connections per second|Maximum concorrent connections|Intranet bandwidth\(MB/s\)|CPU processing capability|QPS reference value|Description|
|----|----------------------------------|---------------|--------------------------|------------------------------|--------------------------|-------------------------|-------------------|-----------|
|16 GB zone-disaster recovery cluster|redis.logic.sharding.drredismdb16g.8db.0rodb.8proxy.default|8|50,000|80,000|768|8-core|800,000|Zone-disaster recovery cluster instance|
|32 GB zone-disaster recovery cluster|redis.logic.sharding.drredismdb32g.8db.0rodb.8proxy.default|8|50,000|80,000|768|8-core|800,000|Zone-disaster recovery cluster instance|
|64 GB zone-disaster recovery cluster|redis.logic.sharding.drredismdb64g.8db.0rodb.8proxy.default|8|50,000|80,000|768|8-core|800,000|Zone-disaster recovery cluster instance|
|128 GB zone-disaster recovery cluster|redis.logic.sharding.drredismdb128g.16db.0rodb.16proxy.default|16|50,000|160,000|1,536|16-core|1,600,000|Zone-disaster recovery cluster instance|
|256 GB zone-disaster recovery cluster|redis.logic.sharding.drredismdb256g.16db.0rodb.16proxy.default|16|50,000|160,000|1,536|16-core|1,600,000|Zone-disaster recovery cluster instance|
|512 GB zone-disaster recovery cluster|redis.logic.sharding.drredismdb512g.32db.0rodb.32proxy.default|32|50,000|320,000|2,048|32-core|3,200,000|Zone-disaster recovery cluster instance|

## QPS performance reference {#section_bhd_cp4_tdb .section}

**QPS performance**

|Type|Maximum concorrent connections|Intranet bandwidth\(MB/s\)|CPU processing capability|QPS reference value|
|----|------------------------------|--------------------------|-------------------------|-------------------|
|8 GB|10,000|24|Single-core|80,000|

**Note:** 

QPS reference values of non-cluster instances range from 80,000 to 100,000. QPS reference values of cluster instances are the number of nodes multiplied by the value from 80,000 to 100,000.

## QPS testing method {#section_hty_fq4_tdb .section}

 ![](images/6123_en-US.png "Network topology")

**ECS instance type**

|Operating system|CPU processing capability|Memory|Region|Number of hosts|
|----------------|-------------------------|------|------|---------------|
|Ubuntu 14.04 64-bit|1-core|2048 MB|China \(Shenzhen\)|3|

**Steps**

1.  Download the source code package for redis-2.8.19 to three ECS servers.

    ``` {#codeblock_m7u_ebi_oej}
     $ wget http://download.redis.io/releases/redis-2.8.19.tar.gz
     $ tar xzf redis-2.8.19.tar.gz
     $ cd redis-2.8.19
     $ make
     $ make install
    ```

2.  Run the following command on these ECS instances at the same time.

    ``` {#codeblock_wyw_367_ler}
     redis-benchmark -h ***********.m.cnsza.kvstore.aliyuncs.com -p 6379 -a password -t set -c 50 -d 128 -n 25000000 -r 5000000
    ```

3.  Summarize the testing data on these ECS instances. The total QPS is the sum of the QPS on each ECS instance.


