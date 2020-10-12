# Features {#concept_hsb_ljz_q2b .concept}

## Flexible Architecture {#section_kft_ls4_tdb .section}

**Dual-machine hot standby architecture**

During system operations, data is synchronized between the master and slave nodes. If the master node is at fault, the system automatically switches over to the slave node in a matter of seconds. The entire process is automatic without affecting your business. The master/slave architecture guarantees the high availability of system services.

**Cluster Architecture**

Cluster instances adopt a distributed architecture, with each node working in master/slave mode. This provides automatic DR switchover and failover. You can choose from multiple cluster specifications based on your business needs, and the service allows you to scale your database performance without limit.

## Data Security {#section_e3s_yt4_tdb .section}

**Persistent data storage**

With memory + hard disk storage, the feature provides high-speed data read/write capability and meets the data persistence requirements.

**Backup and one-click recovery**

The service automatically backs up data every day, providing strong disaster tolerance capabilities. Data can be recovered to any time point within seven days for free, to prevent incorrect data operations and minimize business loss.

**Multi-layer cybersecurity protection**

-   VPC provides network isolation protection at the TCP layer.
-   The anti-DDoS feature can monitor and cleanse large-volume attacks in real time.
-   Over 1,000 IP addresses can be added to the whitelist, to directly control risks at the access source.
-   Password authentication is supported to ensure secure and reliable access.

**Deep kernel Optimization**

Alibaba Cloud’s expert team performs in-depth kernel optimization on the Redis source code, effectively preventing memory overflow and fixing security vulnerabilities to protect you throughout the service.

## High availability {#section_r1y_154_tdb .section}

**Master/slave node deployment**

Dual-copy and cluster version instances have a master node and a slave node. This prevents service interruption caused by SPOF.

**Automatic detection and recovery**

Hardware failure can automatically detect hardware failures and fail over to the slave node, restoring service in a matter of seconds.

**Resource isolation**

Instance-level resource isolation provides enhanced stability for individual services.

## Elastic scaling {#section_ttj_ys4_tdb .section}

**Data capacity scaling**

ApsaraDB for Redis supports product configurations with multiple memory specifications. You can freely upgrade the memory specification to fit your business volume.

**Business morphology scaling**

The service supports a single-node cache architecture and dual-node storage architecture to suit different business scenarios. You can flexibly switch between the standard version and dual-node version.

**Performance scaling**

The cluster architecture allows you to elastically scale data system storage space and throughput performance without restriction, breaking through the data volume and QPS performance limitations. The service can easily handle tens of thousands of read and write requests per second.

## Smart O&M {#section_uvq_ys4_tdb .section}

**Monitoring platform**

This platform provides instance information, such as CPU utilization, connection counts, and disk space usage, for real-time monitoring and alarms, so that you can check your instance status at any time.

**Visual management platform**

The management platform allows you to perform frequent and risky operations, such as instance cloning, backup, and data recovery, with a single click.

**Visual DMS Platform**

The specialized DMS data management platform supports visual data management, improving your R&D and O&M efficiency in an all-round way.

**Database kernel version management**

This feature proactively performs upgrades and quickly repairs bugs, which frees you from daily version management. It also optimizes Redis parameter configurations and maximizes the utilization of system resources.

