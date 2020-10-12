# What is ApsaraDB for Redis {#concept_m3j_s5z_sdb .concept}

ApsaraDB for Redis is compatible with open source Redis protocol-standard, which provides persistent memory database services, based on the highly reliable dual-machine hot-standby architecture and the cluster architecture,  this service can meet the needs of businesses that require high read/write performance and flexible capacity adjustment.

ApsaraDB for Redis supports many data types including String, List, Set, SortedSet, and Hash, and provides advanced functions such as Transactions and Pub/Sub.

Using memory + hard disk storage, ApsaraDB for Redis can meet your persistence requirements, while providing high-speed data read/write capability.

ApsaraDB for Redis supports flexible architecture deployment. The dual-copy and cluster architectures suit different business scenarios.

-   **Dual-copy architecture**: During system operation, data is synchronized between the master and slave nodes. If the master node fails, the system automatically switches over to the slave node in a matter of seconds. The entire process is automatic without affecting your services. The master/slave architecture guarantees the high availability of system services.
-   **Cluster architecture**: Cluster instances adopt a distributed architecture, with each node working in master/slave mode. This supports automatic disaster recovery failover and fault migration. You can choose from multiple cluster specifications as needed and scale database performance without limit.

ApsaraDB for Redis is used as a cloud computing service, with hardware and data deployed on Alibaba Cloud, supported by comprehensive infrastructure planning, network security protection, and system maintenance services. This service enables you to focus fully on business innovation.

