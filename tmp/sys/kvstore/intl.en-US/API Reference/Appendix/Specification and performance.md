# Specification and performance {#concept_gph_q34_tdb .concept}

ApsaraDB for Redis specifications vary with instance versions. This document describes specifications of each instance version and the method of testing Queries Per Second \(QPS\).

**Note:** 

The maximum bandwidth applies to the maximum upstream bandwidth and the maximum downstream bandwidth.

## Specifications {#section_hh2_5p4_tdb .section}

**Standard-dual copy**

|Specifications|Maximum number of connections|Maximum intranet bandwidth \(MB\)|Number of CPU cores|QPS|Description|
|--------------|-----------------------------|---------------------------------|-------------------|---|-----------|
|256 MB master/slave|10,000|10|1|80,000|Master-slave dual-node instances|
|1 GB master/slave|10,000|10|1|80,000|Master-slave dual-node instances|
|2 GB master/slave|10,000|16|1|80,000|Master-slave dual-node instances|
|4 GB master/slave|10,000|24|1|80,000|Master-slave dual-node instances|
|8 GB master/slave|10,000|24|1|80,000|Master-slave dual-node instances|
|16 GB master/slave|10,000|32|1|80,000|Master-slave dual-node instances|
|32 GB master/slave|10,000|32|1|80,000|Master-slave dual-node instances|
|64 GB master/slave|20,000|96|1|80,000|Master-slave dual-node instances|

**Cluster-dual copy**

|Specifications|Number of nodes|Maximum number of connections|Maximum intranet bandwidth \(MB\)|Number of CPU cores|QPS|Description|
|--------------|---------------|-----------------------------|---------------------------------|-------------------|---|-----------|
|4 GB cluster|2|20,000|96|2|160,000|High-performance cluster instances|
|8 GB cluster|2|20,000|96|2|160,000|High-performance cluster instances|
|16 GB cluster|8|80,000|384|8|640,000|High-performance cluster instances|
|32 GB cluster|8|80,000|384|8|640,000|High-performance cluster instances|
|64 GB cluster|8|80,000|384|8|640,000|High-performance cluster instances|
|128 GB cluster|16|160,,000|768|16|1,280,000|High-performance cluster instances|
|256 GB cluster|16|160000|768|16|1,280,000|High-performance cluster instances|
|512 GB cluster|32|320,000|1,536|32|2,560,000|High-performance cluster instances|

## QPS performance reference {#section_bhd_cp4_tdb .section}

**QPS performance**

|Size \(GB\)|Maximum number of connections|Maximum intranet bandwidth \(MB\)|Number of CPU cores|QPS reference value|
|-----------|-----------------------------|---------------------------------|-------------------|-------------------|
|8|10,000|24|1|80,000|

**Note:** For reference, QPS of non-cluster instances ranges from 80,000 to 100,000 and that of cluster instances ranges from 80,000 to 100,000 multiplied by the number of nodes.

## QPS test method {#section_hty_fq4_tdb .section}

 ![](http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/pic/26350/cn_zh/1475048908348/test-net.png "Network topology") 

**Cloud host specifications**

|OS|Number of CPU cores|Memory|Zone|Number of hosts|
|--|-------------------|------|----|---------------|
|Ubuntu 14.04 64-bit|1|2048 MB|China South 1|3|

**Procedure**

1.  Download the source code package for redis-2.8.19 to three ECS instances.

    ```
     $ wget http://download.redis.io/releases/redis-2.8.19.tar.gz
     $ tar xzf redis-2.8.19.tar.gz
     $ cd redis-2.8.19
     $ make
     $ make install
    ```

2.  Run the following command on the three ECS instances:

    ```
     redis-benchmark -h ***********.m.cnsza.kvstore.aliyuncs.com -p 6379 -a password -t set -c 50 -d 128 -n 25000000 -r 5000000
    ```

3.  Summarize the testing data from the three ECS instances. The QPS is the sum of the QPS of the three ECS instances.

