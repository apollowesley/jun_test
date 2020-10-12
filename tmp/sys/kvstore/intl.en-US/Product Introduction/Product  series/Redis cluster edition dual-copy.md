# Redis cluster edition dual-copy {#concept_tds_4mm_tdb .concept}

## Brief Introduction {#section_fm4_tmm_tdb .section}

ApsaraDB for Redis provides cluster version instances, allowing you to easily break through Redis’ single thread bottleneck. This is the preferred solution for businesses that require large Redis capacity or high performance. Cloud database, redis The ApsaraDB for Redis cluster version has built-in data partitions and reading algorithms. The overall process is transparent to users, saving you from the headache of developing and operating Redis clusters.

## Module {#section_gm4_tmm_tdb .section}

The ApsaraDB for Redis cluster version is composed of three components: proxy servers \(service proxy\), partition servers, and configuration servers.

![](images/880_en-US_source.png)

-   **Proxy Servers**

    Single-node. A cluster structure may contain multiple proxies and the system implements load balancing and failover for the proxies.

-   **Partition Servers**

    Each partitioning server is in a dual-copy high-availability architecture. The system automatically implements the master-slave switchover in case of a fault in the master node to ensure high availability of services.

-   **Configuration Servers**

    The server is used to store cluster configuration information and partitioning policies. It currently adopts dual-copy architecture to ensure high availability.


Notes:

-   The number and configuration of the three components are specified by the system at ApsaraDB for Memcache cluster purchase and are currently not flexibly customizable. The type details can be found below:

    |Cluster version specifications|Number of proxies|Number of partitioning server|Memory size of single partitioning server|
    |------------------------------|-----------------|-----------------------------|-----------------------------------------|
    |16|8|8.|2 GB|
    |32|8.|8.|4 GB|
    |64|8.|8.|8 GB|
    |128|16|16.|8 GB|
    |256|16.|16.|16 GB|

-   Redis cluster versions expose a unified access domain name. You can visit this domain name for normal Redis access and data operations. Proxy servers, partition servers, and configuration servers do not support domain name access, so you cannot directly connect to them to perform operations. The server, spoke server, and configuration server do not provide domain name access, the user cannot connect directly to access the corresponding operation.
-   You can purchase new cluster version instances or upgrade standard version \(master/slave replication\) instances to cluster version.

## Use Scenarios {#section_jnj_r4m_tdb .section}

-   **Large data volumes**

    Redis cluster version instances can be effectively scaled to accommodate different data volumes. Compared to the standard version, the cluster version can support larger capacities of 64, 128, and 256 GB to effectively meet your data scaling needs.

-   **High QPS load**

    Standard version Redis instances cannot support high QPS and require you to use multi-node deployment to combat Redis' single thread performance bottleneck. Redis The cluster version provides 16, 32, 64, 128, 256 GB cluster configuration, provides deployment mode for 8 nodes and 16 nodes. Cluster version instances can increase the QPS load by 8 or 16 times compared with standard version instances.

-   **Throughput-intensive applications**

    Compared to standard version instances, Redis cluster version instances have looser restrictions on intranet throughput. This provides great support for businesses that read hotspot data and have high throughput requirements.

-   **Apps insensitive to Redis protocol**

    Because of the multiple components, the cluster version architecture provides more limited support for Redis protocol than the standard version. See for details.


