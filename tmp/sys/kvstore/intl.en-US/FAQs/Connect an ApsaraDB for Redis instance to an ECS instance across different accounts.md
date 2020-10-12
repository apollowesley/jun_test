# Connect an ApsaraDB for Redis instance to an ECS instance across different accounts {#concept_q2p_flf_2gb .concept}

You can use the Cloud Enterprise Network \(CEN\) or Express Connect service of Alibaba Cloud to connect virtual private clouds \(VPCs\) under different Alibaba Cloud accounts. Therefore, an ApsaraDB for Redis instance and an ECS instance under different Alibaba Cloud accounts can interconnect with each other over an internal network.

## Connect instances based on CEN {#section_tqd_dnf_2gb .section}

CEN allows you to create a global network for rapidly building a distributed business system with a hybrid cloud computing solution. CEN enables you to build a secure, private, and enterprise-class interconnected network between VPCs in different regions and your local data centers. CEN provides enterprise-class scalability that automatically responds to your dynamic computing requirements. You can connect resources under different Alibaba Cloud accounts based on CEN.

**Note:** 

-   If you do not deploy resources across regions and have no account requirements, we recommend that you create an ECS instance and an ApsaraDB for Redis instance in the same VPC of the same region under the same account.
-   If permitted, we recommend that you migrate the ECS and ApsaraDB for Redis instances under different accounts to the same account. For more information, see [Use redis-shake to migrate data under the same account](../../../../reseller.en-US/User Guide/Migrate data/Migrate between ApsaraDB Redis instances/Use redis-shake to migrate data under the same account.md#).

**Procedure**

1.  Based on the running environment that your service requires, select one of the CEN-based connections over the Alibaba Cloud intranet from [Use a CEN to interconnect network instances](../../../../reseller.en-US/Quick Start/Use a CEN to interconnect network instances.md#).
2.  Allow connections between an ECS instance and an ApsaraDB for Redis instance in the inbound and outbound rules of an ECS security group.

    **Note:** You can use the `ping <host>` command to view the internal IP address of the ApsaraDB for Redis instance, and add the internal IP address to the security group rules. In the command, the host parameter is the connection address of the ApsaraDB for Redis instance. For more information about how to configure security group rules, see [Configure a security group](../../../../reseller.en-US/Quick Start for Enterprise-Level Users/Configure a security group.md#).

3.  Add the internal IP address of the ECS instance to a whitelist of the ApsaraDB for Redis instance. For more information, see [Set IP whitelists](../../../../reseller.en-US/User Guide/Manage instances/Set IP whitelists.md#).
4.  Run the `ping <host>` command on the ECS instance to confirm that the instances are connected, as shown in the following example.

    ![Test the connection by using the Ping command](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/80706/156810036138517_en-US.png)

    **Note:** 

    -   If the connection fails for the first time, check the configuration of CEN first. If the configuration is correct but the instances are still disconnected, you can submit a ticket to require troubleshooting for CEN-based connections.
    -   If a connection issue occurs when the service is running, see [Troubleshooting for connection issues in ApsaraDB for Redis](reseller.en-US/FAQs/Troubleshooting for connection issues in ApsaraDB for Redis.md#).

## Connect instances based on Express Connect {#section_xqc_hpf_2gb .section}

Alibaba Cloud Express Connect helps you build private network communication channels between VPCs or between a VPC and your local data center. These channels increase network topology flexibility and enhance cross-network communication quality and security. Express Connect can also avoid unstable network quality, and prevent data theft during transmission. You can use Express Connect to connect VPCs under different accounts over an internal network.

**Procedure**

1.  Connect the VPCs under two accounts based on Express Connect. For more information, see [Interconnect two VPCs under different accounts](../../../../reseller.en-US/Peering connections/Interconnect two VPCs under different accounts.md#).
2.  Allow connections between an ECS instance and an ApsaraDB for Redis instance in the inbound and outbound rules of an ECS security group.

    **Note:** You can use the `ping <host>` command to view the internal IP address of the ApsaraDB for Redis instance, and add the internal IP address to the security group rules. In the command, the host parameter is the connection address of the ApsaraDB for Redis instance. For more information about how to configure security group rules, see [Configure a security group](../../../../reseller.en-US/Quick Start for Enterprise-Level Users/Configure a security group.md#).

3.  Add the internal IP address of the ECS instance to a whitelist of the ApsaraDB for Redis instance. For more information, see [Set IP whitelists](../../../../reseller.en-US/User Guide/Manage instances/Set IP whitelists.md#).
4.  Run the `ping <host>` command on the ECS instance to confirm that the instances are connected, as shown in the following example.

    ![Test the connection by using the Ping command](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/80706/156810036138517_en-US.png)

    **Note:** 

    -   If the connection fails for the first time, check the configuration of Express Connect first. if the configuration is correct but the instances are still disconnected, you can submit a ticket to require troubleshooting for Express Connect-based connections.
    -   If a connection issue occurs when the service is running, see [Troubleshooting for connection issues in ApsaraDB for Redis](reseller.en-US/FAQs/Troubleshooting for connection issues in ApsaraDB for Redis.md#).

