# Disaster recovery {#concept_h1v_s3s_2gb .concept}

Data is the core element for most businesses. As a carrier of data, a database plays a decisive role in businesses. ApsaraDB for Redis is a high-performance key-value database system. The database stores large amounts of important data for businesses. This topic describes the mechanism of disaster recovery based on ApsaraDB for Redis.

## Evolution of the disaster recovery architecture based on ApsaraDB for Redis {#section_a1j_lys_2gb .section}

Some issues may occur when programs are running, such as software bugs, device faults, and power failures at data centers. An excellent disaster recovery mechanism can ensure data consistency and service availability in these cases. ApsaraDB for Redis improves the disaster recovery capability to ensure high availability \(HA\) of services, and provides high-availability solutions in various scenarios.

The following figure shows the evolution of the disaster recovery architecture based on ApsaraDB for Redis.

 ![](images/34878_en-US.png "Evolution of the disaster recovery architecture based on ApsaraDB for Redis")

All of these solutions are available. You can choose them as needed. The following sections describe these solutions in details.

## Single-zone high-availability mechanism {#section_u3t_f4t_2gb .section}

All types of ApsaraDB for Redis instances support the single-zone high-availability architecture. An HA monitoring module uses an independent platform architecture, and provides a high-availability mechanism across zones. Therefore, ApsaraDB for Redis serves more stably than on-premises Redis systems.

**Standard dual-replica edition**

A [standard dual-replica](intl.en-US/Product Introduction/Product  series/Redis standard dual-copy.md#) instance uses a master-replica architecture. When detecting a failure on a master node, the HA monitoring module automatically performs the failover operation. The replica node takes over services and becomes a master node, and upon recovery from the failure, the original master node works as a new replica node. The instances support data persistence by default, and allows automatic data replication. You can use the replication files to roll back or clone instances.

![](images/34891_en-US.png "High-availability architecture of the standard dual-replica edition")

**Dual-replica cluster edition**

A [dual-replica cluster](intl.en-US/Product Introduction/Product  series/Redis cluster edition dual-copy.md#) instance consists of a configuration server, multiple proxy servers, and multiple shard servers. These servers are described as follows:

-   The configuration server is a cluster management tool that provides global routing and configuration information. This server uses a triple-replica cluster architecture that follows the Raft protocol.
-   A proxy server uses a single-node architecture. A cluster edition contains multiple proxy servers. ApsaraDB for Redis performs load balancing and failover operations for all proxy servers.
-   A shard server uses a dual-replica high-availability architecture. Similar to a standard dual-replica instance, after the master node of the shard server fails, the HA module automatically performs the failover operation to ensure high availability of services, and updates information on the proxy servers and configuration server.

![](images/34890_en-US.png "High-availability architecture of the dual-replica cluster edition")

## Zone-disaster recovery mechanism {#section_h5s_zxn_fgb .section}

The standard and cluster editions support zone-disaster recovery between two data centers. You can deploy your business in a single region, and require excellent disaster recovery. In this case, you need to select a zone that supports zone-disaster recovery when you create an ApsaraDB for Redis instance. For example, you can select **Singapore Zone \(B+C\)** as shown in the following figure.

![](images/35009_en-US.png "Create a zone-disaster recovery instance")

When you create instances that run in multiple zones, the replica instance at the replica data center is the same type of instance at the master data center. The instances at master and replica data centers synchronize data to each other through a specialized replication channel.

If power or network failures occur at the master data center, the replica instance takes over services and becomes the master instance. The system calls an operation on the configuration server to update routing information for the proxy server. The system performs the failover operation at the network layer according to the routing precision. In normal conditions, the system transmits data directly to the instance at the master data center through precise Classless Inter-Domain Routing \(CIDR\) blocks. However, the master data center does not upload routing details to the backbone when failures occur. The backbone only provides lower-precision CIDR blocks of the replica data center. The system has to route requests to the replica data center during failover.

ApsaraDB for Redis optimizes Redis synchronization mechanism. Similar to global transaction identifiers \(GTIDs\) of MySQL, ApsaraDB for Redis uses global operation identifiers \(OpIDs\) to indicate synchronization offsets and uses background lock-free threads to search OpIDs. The system synchronizes the append-only file \(AOF\) binary log \(binlog\) asynchronously. You can throttle this synchronization to ensure service performance.

## Cross-region disaster recovery {#section_zhl_xdp_fgb .section}

ApsaraDB for Redis provides the Redis Global Replica solution, so multiple sites can provide services simultaneously across regions worldwide. This is applicable to synchronizations among multiple regions. Different from traditional disaster recovery solutions, multiple sites work as master nodes at the same time in this solution. Based on this architecture, your business can run in multiple regions simultaneously. Child instances of the global replica instance in these regions synchronize data to each other in real time.

**Note:** Redis Global Replica is now on trial on the Alibaba Cloud China site. Other Alibaba Cloud sites currently do not supported it.

The global replica instance for ApsaraDB for Redis consists of multiple child instances, multiple synchronization channels, and a channel manager.

-   A child instance is the basic service unit for a global replica instance. All child instances are readable and writable.
-   The synchronization channels support two-way synchronizations between child instances in real time. Based on the resumption from a breakpoint, the system supports a service interruption for a few days.
-   The channel manager controls the lifecycle of the synchronization channels, and processes the operations on child instances, such as master-replica failover and replica instance reconstruction, after a master instance fails. In this way, the global replica instance can provide high-availability services.

**Note:** Child instances perform asynchronous replications to synchronize data. This does not affect the service performance of ApsaraDB for Redis.

When you use the global replica instance for ApsaraDB for Redis, you can specify failover conditions on your application. Therefore, when failures occur in a region, your business switches services to the child instance in another region to ensure service availability.

ApsaraDB for Redis provides multiple high-availability architectures to perform instance-level, zone-level, and region-level disaster recovery. You can select the corresponding disaster recovery solution as needed.

