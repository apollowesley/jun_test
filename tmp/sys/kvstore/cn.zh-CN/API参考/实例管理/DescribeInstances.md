# DescribeInstances {#doc_api_R-kvstore_DescribeInstances .reference}

调用DescribeInstances查询一个或多个Redis实例的信息。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=DescribeInstances)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|DescribeInstances|系统规定参数，取值：DescribeInstances。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|InstanceIds|String|否|r-bp1xxxxxxxxxxxxx|需要查询的实例的ID，指定实例ID时需输入。查询多个实例ID时，使用英文半角逗号（“,”）分隔各ID；置空时默认为查询该用户名下所有实例。

 |
|InstanceStatus|String|否|Normal|实例状态：

 -   Normal（正常）
-   Creating（创建中）
-   Changing（修改中）
-   Inactive（被禁用）
-   Flushing（清除中）
-   Released（已释放）
-   Transforming（转换中）
-   Unavailable（服务停止）
-   Error（创建失败）
-   Migrating（迁移中）
-   BackupRecovering（备份恢复中）
-   MinorVersionUpgrading（小版本升级中）
-   NetworkModifying（网络变更中）
-   SSLModifying（SSL变更中）
-   MajorVersionUpgrading （大版本升级中，可正常访问）

 |
|ChargeType|String|否|PostPaid|付费类型：

 -   PrePaid（预付费）
-   PostPaid（后付费）

 |
|NetworkType|String|否|CLASSIC|网络类型：

 -   CLASSIC（经典网络）
-   VPC（VPC 网络）

 |
|EngineVersion|String|否|4.0|引擎版本：

 -   2.8
-   4.0
-   5.0

 |
|InstanceClass|String|否|redis.master.small.default|实例规格，请参见[实例规格表](~~107984~~)。

 |
|VpcId|String|否|vpc-bp1cxxxxxxxxxxxxxxxxx|VPC ID。

 |
|VSwitchId|String|否|vsw-sdrxxxxxxxxxxxxxxxxxx|虚拟交换机ID。

 |
|PageNumber|Integer|否|1|实例列表的页码，起始值为1，默认值为1。

 |
|PageSize|Integer|否|10|每页最多可显示的行数，最大值为50，默认值为10。

 |
|InstanceType|String|否|Redis|引擎类型，可用值为Redis。

 |
|SearchKey|String|否|apitest|实例名称。

 |
|ArchitectureType|String|否|standard|架构类型：

 -   cluster（集群版）
-   standard（标准版）
-   SplitRW（读写分离版）
-   NULL（所有类型，默认值）

 |
|ZoneId|String|否|cn-hongkong-b|可用区ID。

 |
|Tag.N.Key|String|否|\{“key1”:”value1”,“key2”:”value2”\}|Tag Key，即实例的标签，与Tag Value相对应，可根据Tag进行查询。单次最多支持传入5组值，格式：\{"key1":"value1","key2":"value2"...\}。

 |
|Tag.N.Value|String|否|\{“key1”:”value1”,“key2”:”value2”\}|Tag Value，即实例标签的值，与Tag Key相对应。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|TotalCount|Integer|1|实例总个数。

 |
|PageNumber|Integer|1|实例列表的页码。

 |
|PageSize|Integer|30|输入时设置的每页行数。

 |
|Instances| | |由Instance组成的集合。

 |
|└ArchitectureType|String|cluster|架构类型：

 -   cluster（集群版）
-   standard（标准版）
-   SplitRW（读写分离版）
-   NULL（所有类型，默认值）

 |
|└Bandwidth|Long|96|实例带宽，单位：MB/s。

 |
|└Capacity|Long|4096|实例容量， 单位：MB。

 |
|└ChargeType|String|PostPaid|付费类型：

 -   PrePaid（预付费）
-   PostPaid（后付费）

 |
|└Config|String|\{\\"maxmemory-policy\\":\\"volatile-lfu\\",\\"EvictionPolicy\\":\\"volatile-lru\\",\\"hash-max-ziplist-entries\\":512,\\"zset-max-ziplist-entries\\":128,\\"zset-max-ziplist-value\\":64,\\"set-max-intset-entries\\":512,\\"hash-max-ziplist-value\\":64,\\"\#no\_loose\_disabled-commands\\":\\"flushall,flushdb\\",\\"lazyfree-lazy-eviction\\":\\"yes\\"\}|实例的参数设置情况，详情请参见[参数设置](~~43885~~)。

 |
|└ConnectionDomain|String|r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com|实例的内网连接地址。

 |
|└ConnectionMode|String|Standard|实例的访问模式，取值：

 -   Standard（标准访问模式）
-   Safe（数据库代理模式）

 |
|└Connections|Long|20000|实例的连接数限制。

 |
|└CreateTime|String|2018-11-07T08:49:00Z|实例的创建时间。

 |
|└DestroyTime|String|2019-04-28T10:03:01Z|销毁实例的时间。

 |
|└EndTime|String|2019-06-13T16:00:00Z|包年包月实例到期时间。

 |
|└EngineVersion|String|4.0|数据库版本：

 -   2.8
-   4.0
-   5.0

 |
|└HasRenewChangeOrder|String|false|是否有未生效的续费变配订单，取值：

 -   true（是）
-   false（否）

 |
|└InstanceClass|String|redis.logic.sharding.2g.2db.0rodb.4proxy.default|实例的规格。

 |
|└InstanceId|String|r-bp1xxxxxxxxxxxxx|实例的ID。

 |
|└InstanceName|String|apitest|实例的名称。

 |
|└InstanceStatus|String|Normal|实例的状态。

 |
|└InstanceType|String|Redis|实例类型。

 |
|└IsRds|Boolean|true|是否属RDS管控，取值：

 -   true（是）
-   false（否）

 |
|└NetworkType|String|CLASSIC|网络类型：

 -   CLASSIC（经典网络）
-   VPC（VPC网络）

 |
|└NodeType|String|double|节点类型。

 |
|└PackageType|String|standard|套餐类型：

 -   standard（标准套餐）
-   customized（定制套餐）

 |
|└Port|Long|6379|Redis服务端口。

 |
|└PrivateIp|String|xxx.xxx.xxx.222|内网IP地址。

 |
|└QPS|Long|100000|每秒请求数（Queries per Second）。

 |
|└RegionId|String|cn-hongkong|地域ID。

 |
|└ReplacateId|String|grr-bp1xxxxxxxxxxxxx|多活实例的逻辑ID。

 |
|└SearchKey|String|apitest|基于实例ID或者实例备注模糊搜索时使用的关键字。

 |
|└Tags| | |标签信息。

 |
|└Key|String|key1|Tag的Key。

 |
|└Value|String|value1|Tag的Value。

 |
|└UserName|String|r-bp1xxxxxxxxxxxxx|连接使用的用户名。

 |
|└VSwitchId|String|vsw-bp1xxxxxxxxxxxxxxxxxx|虚拟交换机的ID。

 |
|└VpcId|String|vpc-bp1xxxxxxxxxxxxxxxxxx|专有网络（VPC）的ID。

 |
|└ZoneId|String|cn-hongkong-b|可用区ID。

 |
|RequestId|String|1E83311F-0EE4-4922-A3BF-730B312BED56|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DescribeInstances
&RegionId=cn-hangzhou
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeInstancesResponse>
  <PageNumber>1</PageNumber>
  <TotalCount>1</TotalCount>
  <PageSize>30</PageSize>
  <RequestId>1E83311F-0EE4-4922-A3BF-730B312BED56</RequestId>
  <Instances>
    <KVStoreInstance>
      <Config>{"maxmemory-policy":"volatile-lfu","EvictionPolicy":"volatile-lru","hash-max-ziplist-entries":512,"zset-max-ziplist-entries":128,"zset-max-ziplist-value":64,"set-max-intset-entries":512,"hash-max-ziplist-value":64,"#no_loose_disabled-commands":"flushall,flushdb","lazyfree-lazy-eviction":"yes"}</Config>
      <HasRenewChangeOrder>false</HasRenewChangeOrder>
      <InstanceId>r-bp1xxxxxxxxxxxxx</InstanceId>
      <UserName>r-bp1xxxxxxxxxxxxx</UserName>
      <ZoneId>cn-hangzhou-e</ZoneId>
      <ArchitectureType>cluster</ArchitectureType>
      <PrivateIp>xxx.xxx.xxx.224</PrivateIp>
      <VSwitchId>vsw-bp1xxxxxxxxxxxxxxxxxx</VSwitchId>
      <VpcId>vpc-bp1xxxxxxxxxxxxxxxxxx</VpcId>
      <NetworkType>VPC</NetworkType>
      <PackageType>standard</PackageType>
      <QPS>200000</QPS>
      <IsRds>true</IsRds>
      <EngineVersion>4.0</EngineVersion>
      <ConnectionDomain>r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com</ConnectionDomain>
      <InstanceName>apitest</InstanceName>
      <ReplacateId>grr-bp1xxxxxxxxxxxxx</ReplacateId>
      <Bandwidth>96</Bandwidth>
      <ChargeType>PostPaid</ChargeType>
      <InstanceType>Redis</InstanceType>
      <Tags/>
      <InstanceStatus>Normal</InstanceStatus>
      <Port>6379</Port>
      <InstanceClass>redis.logic.sharding.2g.2db.0rodb.4proxy.default</InstanceClass>
      <NodeType>double</NodeType>
      <RegionId>cn-hangzhou</RegionId>
      <CreateTime>2018-11-07T08:49:00Z</CreateTime>
      <Capacity>4096</Capacity>
      <Connections>20000</Connections>
    </KVStoreInstance>
  </Instances>
</DescribeInstancesResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"PageNumber":1,
	"TotalCount":1,
	"PageSize":30,
	"RequestId":"1E83311F-0EE4-4922-A3BF-730B312BED56",
	"Instances":{
		"KVStoreInstance":[
			{
				"Config":"{\"maxmemory-policy\":\"volatile-lfu\",\"EvictionPolicy\":\"volatile-lru\",\"hash-max-ziplist-entries\":512,\"zset-max-ziplist-entries\":128,\"zset-max-ziplist-value\":64,\"set-max-intset-entries\":512,\"hash-max-ziplist-value\":64,\"#no_loose_disabled-commands\":\"flushall,flushdb\",\"lazyfree-lazy-eviction\":\"yes\"}",
				"HasRenewChangeOrder":false,
				"InstanceId":"r-bp1xxxxxxxxxxxxx",
				"UserName":"r-bp1xxxxxxxxxxxxx",
				"ArchitectureType":"cluster",
				"ZoneId":"cn-hangzhou-e",
				"PrivateIp":"xxx.xxx.xxx.224",
				"VSwitchId":"vsw-bp1xxxxxxxxxxxxxxxxxx",
				"VpcId":"vpc-bp1xxxxxxxxxxxxxxxxxx",
				"NetworkType":"VPC",
				"QPS":200000,
				"PackageType":"standard",
				"IsRds":true,
				"EngineVersion":"4.0",
				"ConnectionDomain":"r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com",
				"InstanceName":"apitest",
				"ReplacateId":"grr-bp1xxxxxxxxxxxxx",
				"Bandwidth":96,
				"ChargeType":"PostPaid",
				"InstanceType":"Redis",
				"Tags":{
					"Tag":[]
				},
				"InstanceStatus":"Normal",
				"Port":6379,
				"InstanceClass":"redis.logic.sharding.2g.2db.0rodb.4proxy.default",
				"CreateTime":"2018-11-07T08:49:00Z",
				"RegionId":"cn-hangzhou",
				"NodeType":"double",
				"Capacity":4096,
				"Connections":20000
			}
		]
	}
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|400|InvalidInstanceIds.Malformed|The Specified parameter "InstanceIds" is not valid.|InstanceIds验证失败|

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

