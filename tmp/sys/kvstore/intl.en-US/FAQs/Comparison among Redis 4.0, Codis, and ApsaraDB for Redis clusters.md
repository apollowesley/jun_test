# Comparison among Redis 4.0, Codis, and ApsaraDB for Redis clusters {#concept_sqs_nsn_tdb .concept}

## Architecture comparison {#section_5qe_p23_oj7 .section}

**Redis 4.0 cluster**

The Redis 4.0 cluster runs in a decentralized structure. The metadata of the cluster is distributed across nodes. During the master-replica failover operation, multiple nodes negotiate with each other and elect a master node. Redis provides the redis-trib.rb tool for cluster deployment and operations and maintenance \(O&M\).

The connection from a client to hashed database nodes is dependent on a smart client. The client evaluates and selects a route based on the node information that Redis returns. For example, if a client initiates a request for a node and the requested key is not located on this node, the client evaluates the returned MOVE or ASK rediretion and redirects the request to the corresponding node.

**Codis cluster**

-   The Codis cluster consists of the following components:

    -   Codis-server: a Redis database where the source code has been modified and that supports slots, scaling up and out, and migration.
    -   Codis-proxy: a multi-thread kernel written in Go.
    -   Codis Dashboard: a cluster management tool.
-   Codis provides a Web-based graphical interface for managing the cluster.
-   The metadata of the cluster is stored in ZooKeeper or etcd.
-   The independent module Codis-HA performs the master-replica failover operation for Redis nodes.
-   The client of proxy-based Codis is insensitive to changes of a route table. The client uses the `LIST PROXY` command from Codis Dashboard to retrieve the list of all proxy nodes. Based on the round-robin scheduling policy, the client determines the target proxy node to perform load balancing.

**ApsaraDB for Redis**

The ApsaraDB for Redis cluster edition uses the architecture as follows:

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3217/15623115553546_en-US.png)

-   The ApsaraDB for Redis cluster edition consists of the following components:
    -   Redis-config: a cluster management tool that uses a dual-node structure and that supports disaster recovery.
    -   Redis-server: a Redis database where source code has been optimized and that supports slots, scaling up and out, and migration.
    -   Redis-proxy: a single-thread stateless kernel written in C++ 14. An ApsaraDB for Redis cluster can contain multiple proxy nodes according to the cluster type.
-   The metadata of the cluster is stored on a meta database.
-   The independent high-availability \(HA\) module performs the master-replica failover operation in the cluster.
-   In the proxy-based ApsaraDB for Redis cluster, a client accesses the service based on a virtual IP address \(VIP\) that is resolved into a connection address. You are insensitive to the route information and do not need to handle load balancing for proxy nodes.

## Performance comparison {#section_ggi_gas_cnx .section}

**Stress testing environment**

Each of the preceding Redis clusters has been established on three different physical servers. Each physical server supports the gigabit network interface controller \(NIC\), 24-core CPU, and 189 GB memory. The memtier\_benchmark, Codis proxy or Alibaba Cloud proxy, and Redis server stress testing tools run on different physical servers. The Redis server uses the Redis kernel of the corresponding cluster.

The key size is 32 bytes and the ratio of set/get operations is 1:10. Each thread processes 16 clients. The stress testing for each cluster lasts five minutes in the 8-thread, 16-thread, 32-thread, 48-thread and 64-thread scenarios.

\[DO NOT TRANSLATE\]

Every cluster has eight master databases and eight replica databases. The append-only file \(AOF\) feature is enabled for these databases. The minimum buffer for an AOF rewrite operation is 64 MB. The stress testing is targeted to a single Redis 4.0 node, a single-core Redis-proxy node of ApsaraDB for Redis, a single-core Codis-proxy node, or an 8-core Codis-proxy node. Codis uses Go 1.7.4.

The stress testing results are as follows.

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3217/15623115563548_en-US.png)

According to the result, the single-core Codis-proxy delivers the poorest performance. In the stress testing for the 8-core Codis-proxy node, hashtags are not used for keys. This is equivalent to scattering the requests to eight backend database nodes, or equivalent to eight Redis-proxy nodes of ApsaraDB for Redis. Therefore, the result data indicates excellent performance.

The performance of the single-core Redis-proxy node of ApsaraDB for Redis approximates to that of the original Redis database node under sufficiently stressed conditions. In the running environment that your service requires, if you use an original Redis cluster, the client must support cluster protocols, parse the `MOVE` and `ASK` rediretions, and redirect requests to the corresponding node. If the client tries to access a key randomly, the access attempts may be repeated twice before the client can access the key. Therefore, the performance cannot be the same as that of a single-node cluster.

## Comparison of features {#section_lig_3ub_6ci .section}

**Comparison of protocols**

|Feature|Redis 4.0 cluster|Codis cluster|Alibaba Cloud ApsaraDB for Redis cluster|
|-------|-----------------|-------------|----------------------------------------|
|Transactions|Supported in the same slot|Not supported|Supported in the same slot|
|SUB/PUB|Supported in the same slot|Not supported|Supported|
|FLUSHALL|Supported|Not supported|Supported|
|SELECT|Not supported|Not supported|Supported|
|MSET/MGET|Supported in the same slot|Supported|Supported|

**Comparison of horizontal scaling**

The Redis 4.0 cluster, Codis cluster, and ApsaraDB for Redis distributed cluster support slot-based management. A slot is the basic unit for scaling.

Horizontal scaling of a distributed cluster includes managing routing information and migrating data for cluster nodes. A key is the basic unit for data migration in these clusters.

**Principle of horizontal scaling of the Redis 4.0 cluster**

In the Redis 4.0 cluster, specified slots can move in a node. The cluster automatically redistributes slots that have been added to an empty node according to the distribution of existing slots in the nodes of the cluster. You can use the move\_slot method of redis-trib.rb to move slots in the way as follows:

1.  Call the `SETSLOT` command to modify status of source and target nodes.
2.  Retrieve the list of slots on the source node.
3.  Call the `MIGRATE` command to migrate keys. During the migration, the Redis 4.0 cluster stays in blocking status. The system indicates a successful migration only after the migrated keys are restored on the target node.
4.  Call the `SETSLOT` command to modify status of source and target nodes.

**How can the system ensure data consistency during the migration process?**

The Redis 4.0 cluster supports redirections during a migration. The system returns the ASK redirection to a client. After receiving the redirection , the client sends the `ASKING` command and then a request to the target node. Afterward, the client can connect to the target node. The system returns the redirection to the client when the target key stays in all of the following conditions:

-   The slot that the key belongs to is located on the node. If not, the system returns the MOVE redirection.
-   The slot stays in migration status.
-   The key does not exist.

Therefore, the migration in the Redis 4.0 cluster is a synchronous-blocking operation. If the key is not empty, the key is readable and writable even when the corresponding slot stays in migration status to ensure data consistency.

**Principle of horizontal scaling of the Codis cluster**

Codis redistributes slots in the same way as the Redis 4.0 cluster. The kernel of Codis-server neither stores slot information nor analyzes the slot that the target key belongs to. Codis-server records the key in the slot-key format to a dictionary that contains key-value pairs only during the `DBADD` operation. If the key contains a tag, Codis-server performs the CRC32 calculation for the tag, and adds the key in the CRC value-key format to the skip list that contains key-value pairs.

Codis Dashboard initiates the migration state machine program in the background. The program notifies all proxy nodes to start the migration in the preparation stage. If one or more proxy nodes fail, the migration fails. The migration procedure of the Codis cluster is similar to that of the Redis 4.0 cluster, except the following items:

-   The Codis cluster stores slot status in ZooKeeper or etcd.
-   The Codis cluster uses the `SLOTSMGRTTAGSLOT` command instead of the `MIGRATE` command. When running the `SLOTSMGRTTAGSLOT` command, Codis migrates a key. If the key contains a tag, Codis migrates all keys from the preceding skip list.

**How can the system ensure data consistency during the migration process?**

The migration in the Codis cluster is also a synchronous-blocking operation. The proxy module works to ensure data consistency, because the Codis-server kernel does not maintain slot status. In response to a request, the Codis-proxy node checks the status of the slot that the target key belongs to. If the slot stays in migration status, the Codis-proxy node sends the command for migrating the specified key to the target Codis-server node. At the end of key migration, the Codis-proxy node routes the request to the target Codis-server node. This migration is simple and requires few modifications to the Redis kernel. But the slow migration may cause client lag for long time.

**Principle of horizontal scaling of ApsaraDB for Redis**

ApsaraDB for Redis allows you to specify a source, a node, and a slot to distribute the slot. The service also supports dynamic distribution of slots based on parameters such as node capacity and slot size to minimize the interruption of the cluster service. The migration procedure is as follows:

1.  The Redis-config node calculates the source and target nodes and slots.
2.  The Redis-config node sends the command for migrating slots to the Redis-server node.
3.  The Redis-server node starts the state machine program and migrates multiple keys.
4.  The Redis-config node regularly checks the Redis-server node and updates the slot status.

**How can the system ensure data consistency during the migration process?**

Different from Codis, ApsaraDB for Redis maintains slot information in the kernel. Codis supports migrating a complete slot and Redis 4.0 supports migrating a single key. However, Alibaba Cloud has optimized the kernel of ApsaraDB for Redis to support multiple migrations and accelerate the migration speed.

The data migration in ApsaraDB for Redis is an asynchronous operation. The system does not check whether data has been restored on the target node before the response of successful migration. Instead, to verify that the migration is successful, the target node notifies the source node of the successful migration and the source node regularly checks the migration status on the target node. In this way, the migration can minimize the synchronous-blocking impact on the connections to other slots.

During the asynchronous migration, to ensure data consistency, ApsaraDB for Redis processes a normal write operation if the request is a write request and if the key does not exist in the list of keys for migration. Other mechanisms to ensure data consistency in ApsaraDB for Redis are the same as that in the Redis 4.0 cluster.

## Other comparisons {#section_7xa_g8i_91i .section}

|Feature|Redis 4.0|Codis|ApsaraDB for Redis|
|-------|---------|-----|------------------|
|Kernel hot upgrade|Not supported|Not supported|Supported|
|Proxy hot upgrade|No proxy|Not supported|Supported|
|Number of slots|16,384|1,024|16,384|
|Password|Not supported. You need to modify the redis-trib.rb script.|Supported. All components must use the same password.|Supported|

The kernel and proxy node of ApsaraDB for Redis support hot upgrades. Connections to the service are maintained and clients still work normally during hot upgrades.

