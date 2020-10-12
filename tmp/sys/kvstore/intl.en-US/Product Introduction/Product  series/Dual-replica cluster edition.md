# Dual-replica cluster edition {#concept_tds_4mm_tdb .concept}

## Overview {#section_fm4_tmm_tdb .section}

ApsaraDB for Redis provides dual-replica cluster instances to eliminate the bottleneck of Redis single-thread model and easily process large-capacity or high-performance services. A cluster instance of ApsaraDB for Redis supports built-in data sharding and reading algorithms. These transparent algorithms relieve you from efforts for research and development \(R&D\) and operations and maintenance \(O&M\) when you use the cluster instance of ApsaraDB for Redis.

## Components {#section_gm4_tmm_tdb .section}

A dual-replica cluster instance of ApsaraDB for Redis consists of multiple proxy servers, multiple shard servers, and a configuration server.

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3106/1561614612880_en-US.png)

-   **Proxy servers**

    A proxy server runs in a single-node structure. The cluster edition contains multiple proxy servers. The system automatically performs load balancing and failover among these proxy servers.

-   **Shard servers**

    Each shard server runs in a dual-replica high-availability structure. If the master node fails, the system automatically performs the failover operation to ensure high availability of services.

-   **Configuration server**

    The configuration server is used to store cluster configurations and partitioning policies. The server runs in a dual-replica high-availability structure to ensure high availability of services.


**Cautions**

-   The system has defined the number and configuration of these components when you purchase the corresponding type of cluster edition. You cannot customize these components. The types of cluster editions are described in [Types and performance](reseller.en-US/Product Introduction/Types and performance.md#).
-   The cluster edition of ApsaraDB for Redis only exposes one domain name. You can access the ApsaraDB for Redis service and perform data operations by using this domain name. The system automatically manages the proxy servers and configuration server, so you do not need to access these servers.
-   You can purchase a cluster edition, or upgrade a standard master-replica edition to a cluster edition.

## Scenarios {#section_jnj_r4m_tdb .section}

-   **Large data volumes**

    The cluster edition of ApsaraDB for Redis supports data capacity scaling. Compared with the standard edition, the cluster edition supports a larger storage capacity of 64 GB, 128 GB, and 256 GB. You can effectively scale out the data storage structure.

-   **High-queries per second \(QPS\) applications**

    A standard edition of ApsaraDB for Redis cannot process high QPS. You can deploy multiple nodes to eliminate the performance bottleneck of Redis single-thread model. The cluster edition of ApsaraDB for Redis provides five types of cluster configurations: 16 GB, 32 GB, 64 GB, 128 GB and 256 GB, and supports 8-node and 16-node deployment modes. Therefore, the QPS performance is 8 or 16 times that of the standard edition.

-   **Throughput-intensive applications**

    Compared with the standard edition, the cluster edition provides higher throughput over an internal network. You can easily read hot data and provide high-throughput services by using the cluster edition.

-   **Applications insensitive to Redis protocols**


## FAQ {#section_fmm_nc5_vfb .section}

-   For more information about memory usage exceptions on child nodes of the cluster edition, see [How do I search for large keys?](../../../../reseller.en-US/FAQs/How do I search for large keys?.md#).
-   For more information about data distribution in memory, see [Redis memory usage analysis](https://www.alibabacloud.com/help/doc-detail/50037.html).

