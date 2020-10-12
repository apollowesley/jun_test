# DescribeInstanceAttribute {#doc_api_R-kvstore_DescribeInstanceAttribute .reference}

You can call DescribeInstanceAttribute to query detailed information of an ApsaraDB for Redis instance.

## Debugging {#api_explorer .section}

[You can call this operation in OpenAPI Explorer without the need to manually calculate the signature. After you call the operation, OpenAPI Explorer can automatically generate SDK example code.](https://api.aliyun.com/#product=R-kvstore&api=DescribeInstanceAttribute&type=RPC&version=2015-01-01)

## Request parameters {#parameters .section}

|Parameter|Type|Required|Example|Description|
|---------|----|--------|-------|-----------|
|InstanceId|String|Yes|r-bp1xxxxxxxxxxxxx| The ID of the instance of which you want to query detailed information.

 |
|Action|String|No|DescribeInstanceAttribute| The operation that you want to perform. Set this parameter to DescribeInstanceAttribute.

 |
|AccessKeyId|String|No|Lxxxxxxxxxxxxxxw| The AccessKey ID that Alibaba Cloud provides for you to access services.

 |

## Response parameters {#resultMapping .section}

|Parameter|Type|Example|Description|
|---------|----|-------|-----------|
|Instances| | | The information of the instance.

 |
|ArchitectureType|String|standard| The architecture of the instance. Valid values:

 -   cluster: the cluster edition.
-   standard: the standard edition.
-   SplitRW: the read/write splitting edition.
-   NULL: all editions. This is the default value.

 |
|AuditLogRetention|String|15| The retention period of audit logs.

 **Note:** Currently, the audit log feature is not supported by Alibaba Cloud International Site.

 |
|AvailabilityValue|String|100%| The availability metrics in the current month.

 |
|Bandwidth|Long|10| The bandwidth of the instance. Unit: MByte/s.

 |
|Capacity|Long|1024| The storage capacity of the instance. Unit: MB.

 |
|ChargeType|String|PostPaid| The billing method of the instance. Valid values:

 -   PrePaid: subscription.
-   PostPaid: pay-as-you-go.

 |
|Config|String|\{\\"EvictionPolicy\\":\\"volatile-lru\\",\\"hash-max-ziplist-entries\\":512,\\"zset-max-ziplist-entries\\":128,\\"zset-max-ziplist-value\\":64,\\"set-max-intset-entries\\":512,\\"hash-max-ziplist-value\\":64\}| The parameter configuration of the instance, in a JSON string. For more information, see [Set parameters](~~43885~~).

 |
|ConnectionDomain|String|r-j6cxxxxxxxxxxxxx.redis.rds.aliyuncs.com| The endpoint of the instance.

 |
|Connections|Long|10000| The maximum number of connections supported by the instance.

 |
|CreateTime|String|2019-03-06T10:42:03Z| The time when the instance was created. The time follows the ISO 8601 standard and it is displayed in UTC. The time is in the yyyy-MM-ddTHH:mm:ssZ format.

 |
|EndTime|String|2019-04-06T10:42:03Z| The time when the subscription instance expires. The time follows the ISO 8601 standard and it is displayed in UTC. The time is in the yyyy-MM-ddTHH:mm:ssZ format.

 |
|Engine|String|Redis| The engine type of the instance.

 |
|EngineVersion|String|4.0| The engine version of the instance. Valid values:

 -   2.8
-   4.0
-   5.0

 |
|HasRenewChangeOrder|String|false| Indicates whether there was an order of renewal with configuration change that had not taken effect. Valid values:

 -   true
-   false

 |
|InstanceClass|String|redis.master.small.default| The instance type.

 |
|InstanceId|String|r-j6cxxxxxxxxxxxxx| The ID of the instance.

 |
|InstanceName|String|apitest| The name of the instance.

 |
|InstanceStatus|String|Normal| The status of the instance.

 |
|InstanceType|String|Redis| The engine type of the instance.

 |
|IsRds|Boolean|true| Indicates whether the instance is managed by Relational Database Service \(RDS\). Valid values:

 -   true
-   false

 |
|MaintainEndTime|String|22:00Z| The end time of the maintenance window. The time is in the `HH:mmZ` format, such as `02:00Z`.

 |
|MaintainStartTime|String|18:00Z| The start time of the maintenance window. The time is in the `HH:mmZ` format, such as `02:00Z`.

 |
|NetworkType|String|CLASSIC| The network type of the instance. Valid values:

 -   CLASSIC
-   VPC

 |
|NodeType|String|double| The node type of the instance. Valid values:

 -   double
-   single

 |
|NodeType|String|double| The node type of the instance. Valid values:

 -   double
-   single

 |
|PackageType|String|standard| The type of the package. Valid values:

 -   standard
-   customized

 |
|Port|Long|6379| The service port of the instance.

 |
|PrivateIp|String|xxx.xxx.xxx.222| The internal IP address of the instance.

 |
|QPS|Long|100000| The theoretical maximum queries per second \(QPS\).

 |
|RegionId|String|cn-hongkong| The ID of the region.

 |
|ReplicaId|String|bls-awxxxxxxxxxxxxx| The ID of the replica.

 |
|ReplicationMode|String|master-slave| The architecture of the instance.

 -   master-slave: includes the master-replica edition and Standalone edition.
-   cluster: includes the read/write splitting edition and cluster edition.

 |
|SecurityIPList|String|127.0.0.1| The IP address whitelists.

 |
|Tags| | | The tags.

 |
|Key|String|tagkey| The tag key.

 |
|Value|String|tagvalue| The tag value.

 |
|VSwitchId|String|vsw-xxxxxxxxxxxxxxxxxxxxx| The ID of the VSwitch.

 |
|VpcAuthMode|String|Open| Indicates whether password authentication is enabled for access within the VPC. Valid values:

 -   Open: Password authentication is enabled.
-   Close: Password authentication is disabled.

 |
|VpcId|String|vpc-bp1cxxxxxxxxxxxxxxxxx| The ID of the VPC.

 |
|ZoneId|String|cn-hongkong-b| The ID of the zone. You can call the [DescribeRegions](~~61012~~) operation to query the latest region list.

 |
|RequestId|String|CA40C261-EB72-4EDA-AC57-958722162595| The ID of the request.

 |

## Examples {#demo .section}

Sample request

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
? Action=DescribeInstanceAttribute
&InstanceId=r-bp1xxxxxxxxxxxxx
&<Common request parameters>

```

Sample success response

`XML` format

``` {#xml_return_success_demo}
<DescribeInstanceAttributeResponse>
  <RequestId>5553161D-11B1-43E1-9D95-523B2C82304F</RequestId>
	  <Instances>
		    <DBInstanceAttribute>
			      <Config>{"maxmemory-policy":"volatile-lfu","EvictionPolicy":"volatile-lru","hash-max-ziplist-entries":512,"zset-max-ziplist-entries":128,"zset-max-ziplist-value":64,"set-max-intset-entries":512,"hash-max-ziplist-value":64,"#no_loose_disabled-commands":"flushall,flushdb","lazyfree-lazy-eviction":"yes"}</Config>
			      <HasRenewChangeOrder>false</HasRenewChangeOrder>
			      <InstanceId>r-bp1xxxxxxxxxxxxx</InstanceId>
			      <ArchitectureType>cluster</ArchitectureType>
			      <ZoneId>cn-hangzhou-e</ZoneId>
			      <PrivateIp>xxx.xxx.xxx.224</PrivateIp>
			      <VSwitchId>vsw-bp1xxxxxxxxxxxxxxxxxx</VSwitchId>
			      <VpcId>vpc-bp1xxxxxxxxxxxxxxxxxx</VpcId>
			      <Engine>Redis</Engine>
			      <NetworkType>VPC</NetworkType>
			      <PackageType>standard</PackageType>
			      <QPS>200000</QPS>
			      <ReplicaId>grr-bp1xxxxxxxxxxxxx</ReplicaId>
			      <IsRds>true</IsRds>
			      <MaintainStartTime>18:00Z</MaintainStartTime>
			      <VpcAuthMode>Close</VpcAuthMode>
			      <ConnectionDomain>cluster40.redis.rds.aliyuncs.com</ConnectionDomain>
			      <EngineVersion>4.0</EngineVersion>
			      <InstanceName>cluster40</InstanceName>
			      <Bandwidth>96</Bandwidth>
			      <ChargeType>PostPaid</ChargeType>
			      <AuditLogRetention>30</AuditLogRetention>
			      <MaintainEndTime>22:00Z</MaintainEndTime>
			      <ReplicationMode>cluster</ReplicationMode>
			      <InstanceType>Redis</InstanceType>
			      <Tags></Tags>
			      <InstanceStatus>Normal</InstanceStatus>
			      <Port>6379</Port>
			      <InstanceClass>redis.logic.sharding.2g.2db.0rodb.4proxy.default</InstanceClass>
			      <AvailabilityValue>100.0%</AvailabilityValue>
			      <RegionId>cn-hangzhou</RegionId>
			      <NodeType>double</NodeType>
			      <CreateTime>2018-11-07T16:49:00Z</CreateTime>
			      <Capacity>4096</Capacity>
			      <SecurityIPList>172.0.0.1</SecurityIPList>
			      <Connections>20000</Connections>
		    </DBInstanceAttribute>
	  </Instances>
</DescribeInstanceAttributeResponse>
```

`JSON` format

``` {#json_return_success_demo}
{
	"RequestId":"5553161D-11B1-43E1-9D95-523B2C82304F",
	"Instances":{
		"DBInstanceAttribute":[
			{
				"Config":"{\"maxmemory-policy\":\"volatile-lfu\",\"EvictionPolicy\":\"volatile-lru\",\"hash-max-ziplist-entries\":512,\"zset-max-ziplist-entries\":128,\"zset-max-ziplist-value\":64,\"set-max-intset-entries\":512,\"hash-max-ziplist-value\":64,\"#no_loose_disabled-commands\":\"flushall,flushdb\",\"lazyfree-lazy-eviction\":\"yes\"}",
				"HasRenewChangeOrder":"false",
				"InstanceId":"r-bp1xxxxxxxxxxxxx",
				"ZoneId":"cn-hangzhou-e",
				"ArchitectureType":"cluster",
				"PrivateIp":"xxx.xxx.xxx.224",
				"VSwitchId":"vsw-bp1xxxxxxxxxxxxxxxxxx",
				"Engine":"Redis",
				"VpcId":"vpc-bp1xxxxxxxxxxxxxxxxxx",
				"NetworkType":"VPC",
				"QPS":200000,
				"PackageType":"standard",
				"ReplicaId":"grr-bp1xxxxxxxxxxxxx",
				"IsRds":true,
				"MaintainStartTime":"18:00Z",
				"VpcAuthMode":"Close",
				"ConnectionDomain":"cluster40.redis.rds.aliyuncs.com",
				"EngineVersion":"4.0",
				"InstanceName":"cluster40",
				"Bandwidth":96,
				"ChargeType":"PostPaid",
				"AuditLogRetention":"30",
				"MaintainEndTime":"22:00Z",
				"ReplicationMode":"cluster",
				"InstanceType":"Redis",
				"InstanceStatus":"Normal",
				"Tags":{
					"Tag":[]
				},
				"Port":6379,
				"InstanceClass":"redis.logic.sharding.2g.2db.0rodb.4proxy.default",
				"CreateTime":"2018-11-07T16:49:00Z",
				"NodeType":"double",
				"RegionId":"cn-hangzhou",
				"AvailabilityValue":"100.0%",
				"Capacity":4096,
				"Connections":20000,
				"SecurityIPList":"172.0.0.1"
			}
		]
	}
}
```

## Error codes {#section_ayc_b0s_9qh .section}

