# Scenarios {#concept_jll_cn4_tdb .concept}

## Game Industry applications {#section_wdv_4tt_tdb .section}

Game companies can use ApsaraDB for Redis as an important part of their deployment architecture.

**Scenario 1:  Using Redis for data storage**

Game deployment architecture is relatively simple. With the main program deployed on ECS, all business data is stored in Redis as a persistent database.  ApsaraDB for Redis supports persistence functions, with primary-standby dual-machine redundant data storage.  

**Scenario 2: Using Redis as a cache to accelerate application access**

Using Redis as a cache layer will accelerate application access. The data is stored in the back-end database \(RDS \).

Reliability is critical to Redis services. If a Redis service becomes unavailable, business access may overload the backend database. ApsaraDB for Redis provides a hot standby highly available architecture that guarantees extremely high service reliability. The master node provides external services. If this node fails, the system automatically switch to  the standby node to take over the services. The entire failover process is completely transparent to users.

## E-commerce industry applications {#section_zdv_4tt_tdb .section}

In the e-commerce industry, Redis is extensively used with large volumes of data, mostly for item display, shopping recommendations, and other modules.

**Scenario: Seckill-type shopping systems**

During large-scale seckill promotions, a shopping system is overwhelmed by traffic, which far exceeds the R/W capability of common databases. The persistence function supported by ApsaraDB for Redis allows you to directly use Redis as a database system. The persistence function supported by ApsaraDB for Redis allows you to directly use Redis as a database system.Used as a database system.

**Scenario : Inventory System with a counter**

In such a system, the underlying architecture usually keeps actual data in RDS and count information in database fields. In contrast, ApsaraDB for Redis reads the counts, while RDS stores the count information. In this scenario, ApsaraDB for Redis is deployed on a physical machine,  with a underlying architecture based on SSD high-performance storage that can provide high-level data reading capabilities.

## Live video applications {#section_c2v_4tt_tdb .section}

Live video services are often reliant on Redis to store user data and relationship information.

**Dual-machine hot standby guarantees high availability**

ApsaraDB for Redis provides the hot-standby mode to maximize service availability.

**Cluster version solves performance bottlenecks**

ApsaraDB for Redis provides cluster version instances to break through the performance bottleneck of Redis single-thread mechanism. This approach can effectively cope with spikes in live video broadcast traffic and meet high performance requirements.

**Easy resizing helps cope with business peaks**

ApsaraDB for Redis can support one-click resizing. The entire upgrade process is fully transparent to you and helps you easily cope with traffic bursts.

