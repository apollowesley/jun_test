# Standard dual-replica edition {#concept_qf3_kjh_tdb .concept}

## Overview {#section_pvv_kjh_tdb .section}

A standard dual-replica instance runs in a master-replica structure. The master node provides services for your business, and the replica node works to ensure high availability \(HA\). When the master node fails, the system switches services to the replica node within 30 seconds to ensure stability of services.

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3105/15616145756584_en-US.png)

## Benefits {#section_tk1_zjh_tdb .section}

-   **Reliability**

    -   Reliable services

        In the master-replica architecture, the master node and the replica node run on different physical servers. The master node provides services for your business. You can use Redis command-line interface \(CLI\) commands and common clients to add, modify, search, and delete data in a database. When the master node fails, the proprietary HA system performs the failover operation to ensure stability of services.

    -   Reliable data

        By default, the dual-replica standard instance enables data persistence to write all data to disks. The system supports data replication. You can roll back or clone instances based on replica sets, and effectively solve issues caused by accidental operations. The system also supports zone-disaster recovery.

-   **Compatibility**

    The standard edition is developed on the basis of Redis 2.8 and 100% compatible with Redis commands. You can smoothly migrate an on-premises Redis database to the standard edition of ApsaraDB for Redis. You can also use Data Transmission Service \(DTS\) to smoothly migrate incremental Redis data.

-   **Proprietary features**

    -   HA system

        ApsaraDB for Redis encapsulates the HA system to detect failures on the master node in real time. In this way, the system can effectively solve issues such as disk I/O errors and CPU failures and perform the failover operation in time to ensure high availability of services.

    -   Master-replica replication mechanism

        Alibaba Cloud has customized the master-replica replication mechanism in ApsaraDB for Redis. You can replicate data in the format of incremental logs between the master node and the replica node. When the replication is interrupted, system performance and stability is unchanged and free from issues caused by the master-replica replication mechanism of the original Redis database.

        Some issues caused by the master-replica replication mechanism of the original Redis database are described as follows:

        -   When the replication is interrupted, the replica node runs the Partial Resynchronization \(PSYNC\) command to resynchronize partial data. During this process, the resynchronization fails. The master node has to fully synchronize .rdb files to the replica node.

        -   To fully synchronize .rdb files, the master node has to perform full replication first due to the single-thread model. As a result, the master node lags for several milliseconds or seconds.

        -   The single-thread process leads to the use of the copy-on-write \(CoW or COW\) mechanism. The mechanism utilizes memory on the master node. This may run out of memory and cause the application to exit abnormally.

        -   The replica files that the master node generates utilize disk I/O and CPU resources.

        -   The replication of GB-level files may lead to outgoing traffic bursts on the server and increase the sequential I/O throughput of disks. This affects normal response of services and cause more issues.


## Scenarios {#section_zzn_1kh_tdb .section}

-   **Compatibility with Redis protocols**

    The standard edition is fully compatible with Redis protocols, so you can smoothly migrate your business.

-   **Persistent data storage based on ApsaraDB for Redis**

    The standard edition supports data persistence, replication and recovery to ensure data reliability.

-   **Limited QPS performance**

    The original Redis database runs in a single-thread mechanism. In scenarios where QPS is 100,000 or less, we recommend that you use this edition. If you require higher performance, select a cluster edition.

-   **Redis commands are simple and contain few sorting and compute commands**

    CPU may be bottlenecked due to Redis single-thread mechanism. If you require plenty of sorting and compute operations, we recommend that you select a cluster edition.


