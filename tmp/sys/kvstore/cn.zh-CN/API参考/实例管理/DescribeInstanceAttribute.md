# DescribeInstanceAttribute {#doc_api_R-kvstore_DescribeInstanceAttribute .reference}

调用DescribeInstanceAttribute查询Redis实例的详细信息。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=DescribeInstanceAttribute&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|Action|String|否|DescribeInstanceAttribute|系统规定参数，取值：DescribeInstanceAttribute。

 |
|RegionId|String|否|cn-hangzhou|地域ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|Instances| | |实例信息的集合。

 |
|DBInstanceAttribute| | |实例信息的集合。

 |
|ArchitectureType|String|standard|指定架构类型返回实例列表：

 -   cluster（集群版）
-   standard（标准版）
-   SplitRW（读写分离版）
-   NULL（所有类型，默认值）

 |
|AuditLogRetention|String|15|审计日志中设置的日志保留时间。

 **说明：** 国际站暂不支持审计日志功能。

 |
|AvailabilityValue|String|100%|当月的可用性指标。

 |
|Bandwidth|Long|10|带宽，单位：MB/s。

 |
|Capacity|Long|1024|存储容量，单位：MB。

 |
|ChargeType|String|PostPaid|付费类型：

 -   PrePaid（预付费）
-   PostPaid（后付费）

 |
|Config|String|\{\\"EvictionPolicy\\":\\"volatile-lru\\",\\"hash-max-ziplist-entries\\":512,\\"zset-max-ziplist-entries\\":128,\\"zset-max-ziplist-value\\":64,\\"set-max-intset-entries\\":512,\\"hash-max-ziplist-value\\":64\}|实例的配置参数（JSON String），参见[参数说明](~~43885~~)。

 |
|ConnectionDomain|String|r-j6cxxxxxxxxxxxxx.redis.rds.aliyuncs.com|Redis实例的连接地址。

 |
|Connections|Long|10000|实例连接数限制，单位：个。

 |
|CreateTime|String|2019-03-06T10:42:03Z|实例创建时间，采用ISO8601表示法，并使用UTC时间。格式为：YYYY-MM-DDThh:mm:ssZ。

 |
|EndTime|String|2019-04-06T10:42:03Z|预付费实例到期时间。采用ISO8601表示法，并使用UTC时间。格式为： YYYY-MM-DDThh:mm:ssZ。

 |
|Engine|String|Redis|数据库类型。

 |
|EngineVersion|String|4.0|数据库版本：

 -   2.8
-   4.0
-   5.0

 |
|HasRenewChangeOrder|String|false|是否有未生效的续费变配订单：

 -   true（是）
-   false（否）

 |
|InstanceClass|String|redis.master.small.default|实例规格。

 |
|InstanceId|String|r-j6cxxxxxxxxxxxxx|实例ID。

 |
|InstanceName|String|apitest|实例名称。

 |
|InstanceStatus|String|Normal|实例状态。

 |
|InstanceType|String|Redis|实例类型。

 |
|IsRds|Boolean|true|是否属RDS管控：

 -   true（是）
-   false（否）

 |
|MaintainEndTime|String|22:00Z|可运维结束时间，返回格式： `HH:mmZ`，如`02:00Z`。

 |
|MaintainStartTime|String|18:00Z|可运维开始时间，返回格式：`HH:mmZ`，如`02:00Z`。

 |
|NetworkType|String|CLASSIC|网络类型：

 -   CLASSIC（经典网络）
-   VPC（VPC网络）

 |
|NodeType|String|double|节点类型：

 -   double（双节点）
-   single（单节点）

 |
|NodeType|String|double|节点类型：

 -   double（双节点）
-   single（单节点）

 |
|PackageType|String|standard|套餐类型：

 -   standard（标准套餐）
-   customized（定制套餐）

 |
|Port|Long|6379|Redis服务端口。

 |
|PrivateIp|String|xxx.xxx.xxx.222|内网IP地址。

 |
|QPS|Long|100000|理论最大QPS值。

 |
|RegionId|String|cn-hongkong|地域ID，可调用[DescribeRegions](~~61012~~)查询。

 |
|ReplicaId|String|bls-awxxxxxxxxxxxxx|副本ID。

 |
|ReplicationMode|String|master-slave|副本架构：

 -   master-slave（包括主从版和单节点版）
-   cluster（包括读写分离版与集群版）

 |
|SecurityIPList|String|127.0.0.1|IP白名单。

 |
|Tags| | |标签信息。

 |
|Tag| | |标签信息。

 |
|Key|String|tagkey|标签key。

 |
|Value|String|tagvalue|标签value。

 |
|VSwitchId|String|vsw-xxxxxxxxxxxxxxxxxxxxx|虚拟交换机ID。

 |
|VpcAuthMode|String|Open|VPC认证模式：

 -   Open（需要密码认证）
-   Close（关闭密码认证，即VPC免密）

 |
|VpcId|String|vpc-bp1cxxxxxxxxxxxxxxxxx|专有网络（VPC）的ID。

 |
|ZoneId|String|cn-hongkong-b|可用区ID，可调用[DescribeRegions](~~61012~~)查询。

 |
|RequestId|String|CA40C261-EB72-4EDA-AC57-958722162595|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DescribeInstanceAttribute
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

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

`JSON` 格式

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

## 错误码 { .section}

访问[错误中心](https://error-center.alibabacloud.com/status/product/R-kvstore)查看更多错误码。

