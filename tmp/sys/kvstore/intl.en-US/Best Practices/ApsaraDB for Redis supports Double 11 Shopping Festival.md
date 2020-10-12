# ApsaraDB for Redis supports Double 11 Shopping Festival {#concept_o2k_l2x_wdb .concept}

## Background {#section_qx5_ngc_zij .section}

ApsaraDB for Redis works as an important support for processing surging e-commerce promotions and orders during Double 11 Shopping Festival. ApsaraDB for Redis provides multiple editions as follows: standard single-replica edition, standard dual-replica edition, and cluster edition.

The standard single-replica edition and standard dual-replica edition feature high compatibility and support Lua scripting and geographical location-based computing. The cluster edition provides large capacities and high performance, and solves the issues caused by single-server performance limits due to Redis single-thread model.

ApsaraDB for Redis works in a two-node hot standby structure by default and supports backup and recovery. Also, the Redis source code team of Alibaba Cloud constantly optimizes and upgrades the ApsaraDB for Redis service, and provides powerful security protections. This topic simplifies some scenarios of Double 11 Shopping Festival and describes the features of ApsaraDB for Redis. Actual scenarios are more complex.

## Store social relations for hundreds of millions of users in Weitao community {#section_bmx_cqb_c0k .section}

Weitao community carries social relations for hundreds of millions of Taobao users. Taobao users can specify a list of followers and merchants can maintain the data of regular customers or followers. The following figure shows the overall social relations.

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3167/15620563123169_en-US.png)

To express these social relations, a traditional relational database model requires complex business design and results in poor user experience. A cluster instance of ApsaraDB for Redis caches followers chains of Weitao community. This simplifies the storage of followers data, and ensures excellent user experience during Double 11 Shopping Festival. Hash tables store followers data of Weitao community. The following figure shows the storage structure. You can call required API operations to query the following data:

-   Whether Users A and B are followers of each other
-   List of items User A is following

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3167/15620563123171_en-US.png)

## Paginate comments to live videos in Tmall based on a cursor {#section_r17_obz_mxg .section}

When mobile users view live videos during Double 11 Shopping Festival, they can obtain more comments to the live videos in three ways:

-   Pull down for incremental comments: obtain a specified number of incremental comments from the specified position up.
-   Pull-down refresh: obtain a specified number of the latest comments.
-   Pull up for incremental comments: obtain a specified number of incremental comments from the specified position down.

The mobile live video streaming system uses ApsaraDB for Redis to optimize the business scenario. This ensures the success rate of comments to live videos and supports more than 50,000 transactions per second \(TPS\) and response time in milliseconds. The live video streaming system writes two types of data for each live video, including indexes and comments. The system writes indexes in sorted sets to sort comments, and stores the comments in hash tables. You can obtain an index ID from the indexes and retrieve a list of comments by reading the hash tables. The following figure shows the process of writing comments.

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3167/15620563133173_en-US.png)

After a user refreshes the list, the background retrieves the corresponding comments. This process is as follows:

1.  Obtain the current index ID.
2.  Retrieve the index list.
3.  Obtain the comments.

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3167/15620563133174_en-US.png)

## Sort orders in Cainiao order fulfillment center {#section_ge9_8ym_nxg .section}

After a user buys a commodity during Double 11 Shopping Festival, Cainiao warehouse and distribution system generates and processes a corresponding logistics order. The decision-making system generates an order fulfillment plan based on the order data. Therefore, the warehouse and distribution system can provide intelligent and collaborative services across each stage. The plan specifies the time for issuing the order to the warehouse, the time for outbound delivery, the time for item collection, and the time for delivering the item. The order fulfillment center provides the logistics service according to the order fulfillment plan. Due to the limited capacities of warehouses and distribution, the system processes the earliest orders in priority. Therefore, ApsaraDB for Redis sorts the orders by priority before the order fulfillment center issues them to the warehouse or for delivery.

The order fulfillment center uses ApsaraDB for Redis to sort logistics orders and determine the priorities of these orders.

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3167/15620563133175_en-US.png)

