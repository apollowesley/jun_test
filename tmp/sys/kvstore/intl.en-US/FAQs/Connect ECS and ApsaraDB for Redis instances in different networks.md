# Connect ECS and ApsaraDB for Redis instances in different networks {#concept_svr_jzt_zfb .concept}

If your ECS instance runs in a classic network and the ApsaraDB for Redis instance runs in a virtual private cloud \(VPC\), or the ApsaraDB for Redis instance runs in a classic network and the ECS instance runs in a VPC, you can quickly connect both instances by following the procedures in this topic.

## Background {#section_i4r_wb5_zfb .section}

A classic network and a VPC are two different subnets and cannot directly interconnect with each other. ECS and ApsaraDB for Redis instances may run in different types of networks if you have selected the wrong network type or your business architecture is changed. However, your business requires a connection between the ECS instance and the ApsaraDB for Redis instance. Then, you can change the network type of the ApsaraDB for Redis instance, or use ClassicLink to allow both instances to interconnect with each other.

**Note:** The ClassicLink-based interconnection is a temporary solution in special conditions. To achieve efficient caching in the running environment that your service requires, we recommend that you create the ECS and ApsaraDB for Redis instances in the same VPCs.

## The ECS instance in a classic network connects to the ApsaraDB for Redis instance in a VPC {#section_vh5_rzt_zfb .section}

You can establish a ClassicLink-based connection to allow the ECS instance in a classic network and the ApsaraDB for Redis instance in a VPC to interconnect with each other.

**Prerequisites**

-   The ECS and ApsaraDB for Redis instances belong to the same account and run in the same region.
-   The Classless Inter-Domain Routing \(CIDR\) block settings in the VPC must follow the rules of using the ClassicLink feature. For more information, see [ClassicLink overview](../../../../reseller.en-US/VPC network connections/ClassicLink/ClassicLink overview.md#).

**Note:** To allow the ECS and ApsaraDB for Redis instances in different regions to interconnect with each other quickly, you can migrate the ApsaraDB for Redis instance to the region where the ECS instance is located. For more information, see [Use redis-shake to migrate data under the same account](../../../../reseller.en-US/User Guide/Migrate data/Migrate between ApsaraDB Redis instances/Use redis-shake to migrate data under the same account.md#).

**Procedure**

1.  Log on to the [ApsaraDB for Redis console](https://kvstore.console.aliyun.com/).
2.  Add the internal IP address of the ECS instance to a whitelist of the ApsaraDB for Redis instance. For more information, see [Set IP whitelists](../../../../reseller.en-US/User Guide/Manage instances/Set IP whitelists.md#).
3.  [Establish a ClassicLink connection](../../../../reseller.en-US/VPC network connections/ClassicLink/Establish a ClassicLink connection.md#).
4.  Test the connection on the ECS instance.

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/73738/156801893233629_en-US.png)


## The ECS instance in a VPC connects to the ApsaraDB for Redis instance in a classic network {#section_obl_1j5_zfb .section}

In this case, you can switch the ApsaraDB for Redis instance to a VPC at the appropriate time to allow both instances to quickly interconnect with each other. For more information, see [Switch to VPC](../../../../reseller.en-US/User Guide/Manage instances/Switch to VPC.md#).

**Note:** To allow the ECS and ApsaraDB for Redis instances in different regions to interconnect with each other quickly, you can migrate the ApsaraDB for Redis instance to the region where the ECS instance is located. For more information, see [Use redis-shake to migrate data under the same account](../../../../reseller.en-US/User Guide/Migrate data/Migrate between ApsaraDB Redis instances/Use redis-shake to migrate data under the same account.md#).

